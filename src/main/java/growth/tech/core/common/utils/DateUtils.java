package growth.tech.core.common.utils;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/29 15:17
 */

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Date;

public class DateUtils {

    /** 日期转换 pattern 们 */
    public static final String DATE_PATTERN_COMPLETE = "yyyy-MM-dd HH:mm:ss SSS";
    public static final String DATE_PATTERN_COMMON = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN_SHORT_DATE1 = "yyyyMMdd";
    public static final String DATE_PATTERN_SHORT_DATE2 = "yyyy-MM-dd";

    /**
     * 日期转换
     *
     * @param date 日期
     * @return yyyy-MM-dd HH:mm:ss SSS
     */
    public static String formatDate(Date date) {
        return formatDate(date, DATE_PATTERN_COMPLETE);
    }

    /**
     * 按照指定 pattern 日期转换
     *
     * @param date    日期
     * @param pattern 指定pattern
     * @return 日期字符串
     */
    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        return FastDateFormat.getInstance(pattern).format(date);
    }

    /**
     * 使用 yyyy-MM-dd HH:mm:ss SSS 解析字符串日期
     *
     * @param dateStr yyyy-MM-dd HH:mm:ss SSS
     * @return Date
     */
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, DATE_PATTERN_COMPLETE);
    }

    /**
     * 使用指定 pattern 解析字符串日期
     *
     * @param dateStr 字符串日期
     * @param pattern 日期pattern
     * @return Date
     */
    @SneakyThrows
    public static Date parseDate(String dateStr, String pattern) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        return FastDateFormat.getInstance(pattern).parse(dateStr);
    }

}
