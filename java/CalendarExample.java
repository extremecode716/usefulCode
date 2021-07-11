import java.util.Calendar;
import java.util.Scanner;

//작성자 : ExtremeCode
public class CalendarExample {
	private static Calendar cal = Calendar.getInstance(); //싱글톤, 현재시간을 가져옴.
	private static final int SEVEN_DAYS = 7;
	private static String[] arrStrDayOfWeek = { "일", "월", "화", "수", "목", "금", "토" };

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CalendarData calCurrentData = new CalendarData();
		CalendarData calInputData = new CalendarData();
		Scanner sc = new Scanner(System.in);
		String strInputYear = null;
		String strInputMon = null;

		printCurrentCalendarData(calCurrentData);

		try {
			do {
				strInputYear = null;
				strInputMon = null;
				System.out.print("Year Input : ");
				strInputYear = sc.nextLine();
				System.out.print("Month Input : ");
				strInputMon = sc.nextLine();

				printCalendar_InputYearAndMon(Integer.parseInt(strInputYear), Integer.parseInt(strInputMon),
						calInputData);
			} while (true);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			sc.close();
		}
	}

	private static void setCurrentCalendarData(CalendarData _calCurrentData) {
		cal = Calendar.getInstance();
		_calCurrentData.m_iYear = cal.get(Calendar.YEAR);
		_calCurrentData.m_iMon = cal.get(Calendar.MONTH) + 1;
		_calCurrentData.m_iMinMon = cal.getActualMinimum(Calendar.MONTH) + 1;
		_calCurrentData.m_iMaxMon = cal.getActualMaximum(Calendar.MONTH) + 1;
		_calCurrentData.m_iDay = cal.get(Calendar.DATE);
		_calCurrentData.m_iMinDay = cal.getActualMinimum(Calendar.DATE);
		_calCurrentData.m_iMaxDay = cal.getActualMaximum(Calendar.DATE);
		_calCurrentData.m_iHour = cal.get(Calendar.HOUR_OF_DAY); //24
		_calCurrentData.m_iMin = cal.get(Calendar.MINUTE);
		_calCurrentData.m_iSec = cal.get(Calendar.SECOND);

		cal.set(Calendar.DATE, _calCurrentData.m_iDay);
		_calCurrentData.m_iDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		cal.set(Calendar.DATE, 1);
		_calCurrentData.m_iDayOfStartWeek = cal.get(Calendar.DAY_OF_WEEK);
		cal.set(Calendar.DATE, _calCurrentData.m_iMaxDay);
		_calCurrentData.m_iDayOfEndWeek = cal.get(Calendar.DAY_OF_WEEK);
	}

	private static void printCurrentCalendarData(CalendarData _calCurrentData) {
		setCurrentCalendarData(_calCurrentData);

		System.out.println("Current Time\t");
		System.out.format("%d-%d-%d\n%d:%d:%d\n", _calCurrentData.m_iYear, _calCurrentData.m_iMon,  _calCurrentData.m_iDay,
				_calCurrentData.m_iHour, _calCurrentData.m_iMin, _calCurrentData.m_iSec);
	}

	private static CalendarData setCalendarData_InputYearAndMon(int _iYear, int _iMon, CalendarData _calData) {
		cal.set(Calendar.YEAR, _iYear);
		cal.set(Calendar.MONTH, _iMon - 1);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		_calData.m_iYear = cal.get(Calendar.YEAR);
		_calData.m_iMon = cal.get(Calendar.MONTH) + 1;
		_calData.m_iMinMon = cal.getActualMinimum(Calendar.MONTH) + 1;
		_calData.m_iMaxMon = cal.getActualMaximum(Calendar.MONTH) + 1;
		_calData.m_iDay = cal.get(Calendar.DATE);
		_calData.m_iMinDay = cal.getActualMinimum(Calendar.DATE);
		_calData.m_iMaxDay = cal.getActualMaximum(Calendar.DATE);
		_calData.m_iHour = cal.get(Calendar.HOUR_OF_DAY);
		_calData.m_iMin = cal.get(Calendar.MINUTE);
		_calData.m_iSec = cal.get(Calendar.SECOND);

		_calData.m_iDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		_calData.m_iDayOfStartWeek = cal.get(Calendar.DAY_OF_WEEK);
		cal.set(Calendar.DATE, _calData.m_iMaxDay);
		_calData.m_iDayOfEndWeek = cal.get(Calendar.DAY_OF_WEEK);

		return _calData;
	}

	private static CalendarData printCalendar_InputYearAndMon(int _iYear, int _iMon, CalendarData _calData) {

		setCalendarData_InputYearAndMon(_iYear, _iMon, _calData);

		System.out.println(
				"---------------------" + _calData.m_iYear + "년 " + _calData.m_iMon + "월" + "--------------------");

		for (int i = 0; i < arrStrDayOfWeek.length; ++i) {
			System.out.print(arrStrDayOfWeek[i] + "\t");
		}
		System.out.println();

		int iCountSevenDays = 0;
		for (int i = 1; i < _calData.m_iDayOfStartWeek; ++i) {
			System.out.print("\t");
			++iCountSevenDays;
		}
		for (int i = _calData.m_iMinDay; i <= _calData.m_iMaxDay; ++i) {
			System.out.print(i + "\t");
			++iCountSevenDays;
			if (iCountSevenDays == SEVEN_DAYS) {
				iCountSevenDays = 0;
				System.out.println();
			}
		}
		System.out.println();
		System.out.println("--------------------------------------------------");

		return _calData;
	}
}
