import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {

    public static Timestamp timestampOf(LocalDateTime time) {
        return time == null ? null : Timestamp.valueOf(time);
    }

    public static LocalDateTime dateTimeOf(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }

    public static LocalDate localDateOf(String yyyyMMdd) {
        return LocalDate.parse(yyyyMMdd, DateTimeFormatter.BASIC_ISO_DATE);
    }

    public static LocalDateTime nowLocalDateTime(){
        return LocalDateTime.now();
    }
    public static LocalDate nowLocalDate(){
        return LocalDate.now();
    }

    /*
        월:1~일:7
     */
    public static int getDayOfWeek(LocalDate date) {
        return date == null ? 0 : date.getDayOfWeek().getValue();
    }

    public static int getYear(LocalDate date) {
        return date == null ? 0 : date.getYear();
    }

    public static int getMonthValue(LocalDate date) {
        return date == null ? 0 : date.getMonthValue();
    }

    public static LocalDate plusDays(LocalDate date, Long day) {
        return date.plusDays(day);
    }

    public static LocalDate plusWeeks(LocalDate date, Long week) {
        return date.plusWeeks(week);
    }

    public static LocalDate plusYears(LocalDate date, Long year) {
        return date.plusYears(year);
    }

    /*
    날짜 변경
     */
    public static LocalDate withLocalDate(LocalDate date, String yyyyMMdd) {
        if (yyyyMMdd.length() != 8)
            return date;

        try {
            int year = Integer.parseInt(yyyyMMdd.substring(0, 4));
            int month = Integer.parseInt(yyyyMMdd.substring(4, 6));
            int day = Integer.parseInt(yyyyMMdd.substring(6));

            date.withDayOfMonth(day);
            date.withMonth(month);
            date.withYear(year);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String toString(LocalDateTime time) {
        return time == null ? null : time.toString();
    }

    public static String toString(LocalDate date) {
        return date == null ? null : date.toString();
    }

    public static Integer date2int(LocalDate date) {
        if (date == null) {
            return 0;
        }
        return date.getYear() * 10000 + date.getMonth().getValue() * 100 + date.getDayOfMonth();
    }

    public static Integer time2int(LocalTime time) {
        if (time == null) {
            return 0;
        }
        return time.getHour() * 10000 + time.getMinute() * 100 + time.getSecond();
    }

    public static LocalDate int2date(Integer dateint) {
        if (dateint == null) {
            return null;
        }
        int year = dateint / 10000;
        int month = dateint / 100 - year * 100;
        int day = dateint % 100;
        return LocalDate.of(year, month, day);
    }

    public static LocalTime int2time(Integer timeint) {
        if (timeint == null) {
            return null;
        }
        int hour = timeint / 10000;
        int min = timeint / 100 - hour * 100;
        int sec = timeint % 100;
        return LocalTime.of(hour, min, sec);
    }


}