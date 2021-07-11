import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.ooxml.util.SAXHelper;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/*
* <pre>
    사용법
    try {
        ExcelReader reader = new ExcelReader("path/*.xml");
        reader.readExcel();
    } catch (Exception e) {
        e.printStackTrace();
    }
* </pre>
 */


public class ExcelReader {

    private List<ExcelReader.Row> excelData;
    private int minColumns;
    private String excelFile;

    public ExcelReader(String excelFile) {
        this.excelFile = excelFile;
    }

    public List<ExcelReader.Row> readExcel() throws Exception {

        File xlsxFile = new File(excelFile);
        if (!xlsxFile.exists()) {
            throw new FileNotFoundException("Not found or not a file: " + xlsxFile.getPath());
        }

        try (OPCPackage opcPackage = OPCPackage.open(xlsxFile.getPath(), PackageAccess.READ)) {

            ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(opcPackage);
            XSSFReader xssfReader = new XSSFReader(opcPackage);
            StylesTable styles = xssfReader.getStylesTable();
            XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();

            // get the first sheet
            if (iter.hasNext()) {
                try (InputStream stream = iter.next()) {

                    excelData = new ArrayList<>();

                    DataFormatter formatter = new DataFormatter();
                    InputSource sheetSource = new InputSource(stream);
                    try {
                        XMLReader sheetParser = SAXHelper.newXMLReader();
                        ContentHandler handler = new XSSFSheetXMLHandler(styles, null, strings, new SheetToCSV(),
                                formatter, false);
                        sheetParser.setContentHandler(handler);
                        sheetParser.parse(sheetSource);
                    } catch (ParserConfigurationException e) {
                        throw new RuntimeException("SAX parser appears to be broken - " + e.getMessage());
                    }
                }//try
            }//if
        }//try
        return excelData;
    }

    private class SheetToCSV implements SheetContentsHandler {
        ExcelReader.Row rowData;
        private int currentRow = -1;
        private int currentCol = -1;

        @Override
        public void startRow(int rowNum) {
            rowData = new ExcelReader.Row();
            currentRow = rowNum;
            currentCol = -1;
        }

        @Override
        public void endRow(int rowNum) {
            for (int i = currentCol; i < minColumns; i++) {
                rowData.addCell(getBlankCell());
            }
            excelData.add(rowData);
        }

        @Override
        public void cell(String cellReference, String formattedValue, XSSFComment comment) {
            if (cellReference == null) {
                cellReference = new CellAddress(currentRow, currentCol).formatAsString();
            }

            int thisCol = (new CellReference(cellReference)).getCol();
            int missedCols = thisCol - currentCol - 1;
            for (int i = 0; i < missedCols; i++) {
                rowData.addCell(getBlankCell());
            }
            currentCol = thisCol;

            if (minColumns < currentCol) {
                minColumns = currentCol;
            }
            rowData.addCell(getCell(formattedValue));

        }

        @Override
        public void headerFooter(String text, boolean isHeader, String tagName) {
            //codes for reading header/footer if required
        }

        private ExcelReader.Cell getCell(String formattedValue) {

            ExcelReader.Cell cell;

            if (isNumeric(formattedValue)) {
                cell = new ExcelReader.Cell(CellType.NUMERIC, Double.parseDouble(formattedValue));
            } else if (isBoolean(formattedValue)) {
                cell = new ExcelReader.Cell(CellType.BOOLEAN, Boolean.parseBoolean(formattedValue));
            } else {
                cell = new ExcelReader.Cell(CellType.STRING, formattedValue);
            }

            return cell;
        }

        private boolean isNumeric(String value) {
            try {
                Double.parseDouble(value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        private boolean isBoolean(String value) {
            return value != null
                    && Arrays.stream(new String[]{"true", "false"}).anyMatch(b -> b.equalsIgnoreCase(value));
        }

        private ExcelReader.Cell getBlankCell() {
            return new ExcelReader.Cell(CellType.BLANK, null);
        }
    }

    class Row {
        private List<ExcelReader.Cell> cells;

        public Row() {
            this.cells = new ArrayList<>();
        }

        public List<ExcelReader.Cell> getCells() {
            return cells;
        }

        public void addCell(ExcelReader.Cell cell) {
            cells.add(cell);
        }
    }

    class Cell {
        private CellType type;
        private Object data;

        public Cell(CellType type, Object data) {
            super();
            this.type = type;
            this.data = data;
        }

        public CellType getType() {
            return type;
        }

        public String getStringCellValue() {
            return (String) data;
        }

        public double getNumericCellValue() {
            return (double) data;
        }

        public boolean getBooleanCellValue() {
            return (boolean) data;
        }
    }
}