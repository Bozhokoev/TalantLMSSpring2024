package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
    private final static String PATTERN_SECOND = "yyyy/MM/dd";
    private final static String PATTERN = "MM/dd/yyyy";

    private DateUtils() {
        throw new RuntimeException();
    }


    public static long toTimeStamp(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+12"));
        SimpleDateFormat ldf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        Date d1 = null;
        try {
            d1 = ldf.parse(sdf.format(date));
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        assert d1 != null;
        return d1.getTime();
    }

    public static String fromTimeStamp(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat(DatePatterns.TEST_RAIL_PATTERN.getPattern());
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        Date date = new Date(timestamp);
        return sdf.format(date);
    }


    public static Date generateDate(int day, Month month, int year) {
        LocalDate of = LocalDate.of(year, month, day);
        String format = of.format(DateTimeFormatter.ofPattern(PATTERN));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN);
        Date parse;
        try {
            parse = simpleDateFormat.parse(of.format(dateTimeFormatter));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        System.out.println(parse);
        return parse;
    }

    public static Date generateDate(int day, int month, int year) {
        // Создаем SimpleDateFormat с нужным паттерном
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN);
        // Формируем строку в формате паттерна
        String dateString = String.format("%02d/%02d/%04d", month, day, year);
        // Парсим строку в Date с помощью SimpleDateFormat
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    public static String yesterday(DatePatterns patterns) {
        Date date = asDate(LocalDate.now().minusDays(1));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patterns.getPattern());
        return simpleDateFormat.format(date);
    }


    public static String toDay(DatePatterns patterns) {
        Date date = asDate(LocalDate.now());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patterns.getPattern());
        return simpleDateFormat.format(date);
    }


    public static String nextYear(DatePatterns patterns) {
        Date date = asDate(LocalDate.now().plusYears(1));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patterns.getPattern());
        return simpleDateFormat.format(date);
    }

    public static String futureTo(DatePatterns patterns, int years) {
        Date date = asDate(LocalDate.now().plusYears(years));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patterns.getPattern());
        return simpleDateFormat.format(date);
    }

//    public static void main(String[] args) {
//        System.out.println(generateDate());
//    }

    public static String to(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);
        return simpleDateFormat.format(date);
    }

    public static String to(Date date, DatePatterns patterns) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patterns.getPattern());
        return simpleDateFormat.format(date);
    }

    public static String convertDateToString(Date date) {
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return localDateTime.format(dateTimeFormatter);
    }


    public static Date toDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date parse = formatter.parse(date);
            return parse;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date toDate(String date, DatePatterns patterns) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date parse = formatter.parse(date);
            return parse;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    public static String getCurrentDateInCustomFormat() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return currentDate.format(customFormat);
    }

}