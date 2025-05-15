package vn.salary.calculator.shared;

import vn.salary.calculator.shared.exception.ErrorCode;
import vn.salary.calculator.shared.exception.ErrorCodeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class DateUtils {

    private DateUtils() {
    }

    private static final class Constants {
        private static final ZoneId ZONE_ID_HCM = ZoneId.of("Asia/Ho_Chi_Minh");
        private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        private static final DateTimeFormatter DD_MM_YYYY_HH_MM_SS = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        private static final DateTimeFormatter DD_MM_YYYY = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        private static final DateTimeFormatter YYYY_NN_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    }

    public static LocalDateTime now() {
        return LocalDateTime.now(Constants.ZONE_ID_HCM);
    }

    public static ZonedDateTime zonedNow() {
        return ZonedDateTime.now(Constants.ZONE_ID_HCM);
    }

    public static LocalDateTime atStartOfCurrentDay() {
        return zonedNow().toLocalDateTime().with(LocalTime.MIN);
    }

    public static LocalDateTime atEndOfCurrentDay() {
        return zonedNow().toLocalDateTime().with(LocalTime.MAX);
    }

    public static Long toMilli(LocalDateTime value) {
        return value.atZone(Constants.ZONE_ID_HCM).toInstant().toEpochMilli();
    }

    public static LocalDateTime atStart(Long value) {
        return of(value).with(LocalTime.MIN);
    }

    public static LocalDateTime atEnd(Long value) {
        return of(value).with(LocalTime.MAX);
    }

    public static LocalDateTime atEndOfDay() {
        return now().with(LocalTime.MAX);
    }

    public static LocalDateTime atStartOfDay() {
        return now().with(LocalTime.MIN);
    }

    public static LocalDateTime atStartOfDay(LocalDateTime value) {
        return value.with(LocalTime.MIN);
    }

    public static LocalDateTime atEndOfDay(LocalDateTime value) {
        return value.with(LocalTime.MAX);
    }

    public static LocalDateTime atStartOfDay(LocalDate value) {
        return value.atStartOfDay().with(LocalTime.MIN);
    }

    public static LocalDateTime atEndOfDay(LocalDate value) {
        return value.atStartOfDay().with(LocalTime.MAX);
    }

    public static LocalDateTime of(Long value) {
        return Instant.ofEpochMilli(value).atZone(Constants.ZONE_ID_HCM).toLocalDateTime();
    }

    public static LocalDateTime of(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), Constants.ZONE_ID_HCM);
    }

    public static Date toJavaUtilDate(LocalDateTime value) {
        return Date.from(value.atZone(Constants.ZONE_ID_HCM).toInstant());
    }

    public static String format(LocalDateTime time) {
        if (time == null) {
            return "";
        }
        return Constants.DD_MM_YYYY.format(time);
    }

    public static String format(LocalDate time, String pattern) {
        if (time == null) {
            return "";
        }
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String format(LocalDateTime time, String pattern) {
        if (time == null) {
            return "";
        }
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String format(Long time) {
        if (time == null) return null;

        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), Constants.ZONE_ID_HCM);
        return dateTime.format(Constants.DD_MM_YYYY);
    }

    public static String format(String time) {
        if (time == null) return null;

        OffsetDateTime offsetDateTime = OffsetDateTime.parse(time);
        return offsetDateTime.format(Constants.DD_MM_YYYY);
    }

    public static String formatDate(String time, String patternIn, String patternOut) {
        if (time == null) return null;

        SimpleDateFormat dateFormat = new SimpleDateFormat(patternIn);
        SimpleDateFormat formatter = new SimpleDateFormat(patternOut);
        Date date = null;
        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            throw new ErrorCodeException(ErrorCode.UNKNOWN_ERROR, e.getMessage());
        }
        return formatter.format(date);
    }

}