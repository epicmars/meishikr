package com.sin2pi.brick.common.utils;

/**
 * Created by yinhang on 16/2/16.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static String DATE_FORMAT_STANDARD = "yyyy-MM-dd";
    public static String DATE_FORMAT_STANDARD_MONTH = "yyyy-MM";
    public static String DATE_TIME_FORMAT_STANDARD = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_TIME_FORMAT_ZH_CN = "yyyy年MM月dd日HH:mm:ss";
    public static String DATE_TIME_SYNC_DATE = "MM月dd日 HH:mm";
    public static String TIME_FORMAT_STANDARD = "HH:mm:ss";

    //
    public static String DATE_FORMAT_ZH_CN_YEAR_MONTH_DAY = "yyyy年MM月dd日";
    public static String DATE_FORMAT_ZH_CN_YEAR_MONTH_WEEK = "yyyy年MM月第W周";
    public static String DATE_FORMAT_ZH_CN_YEAR_MONTH = "yyyy年MM月";
    public static String DATE_FORMAT_ZH_CN_YEAR = "yyyy年";
    public static String DATE_FORMAT_ZH_CN_MONTH = "M月";


    public static SimpleDateFormat getDateFormat(String format) {
        return new SimpleDateFormat(format, Locale.getDefault());
    }

    // public static SimpleDateFormat getStandardDateFormat(String pattern){
    // return getFormat(DATE_FORMAT_STANDARD);
    // }

    public static Date getDate(long timeMilliseconds) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(timeMilliseconds);
        return calendar.getTime();
    }

    public static Date getDateStandard(CharSequence dateStr) {
        return getDate(DATE_FORMAT_STANDARD, dateStr);
    }

    /**
     * 是否为日期
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date getDate(String format, CharSequence str) {
        Date date = null;
        try {
            date = getDateFormat(format).parse(str.toString());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    public static String format(String format, Date date) {
        return getDateFormat(format).format(date);
    }

    /**
     * 格式化当前日期
     *
     * @param format
     * @return
     */
    public static String formatCurrentTime(String format) {
        return format(format, getDate(System.currentTimeMillis()));
    }

    /**
     *
     */
    public static String formatCurrentTimeStandard() {
        return formatCurrentTime(DATE_FORMAT_STANDARD);
    }

    /**
     * 格式化日期时间
     * @param date
     * @return
     */
    public static String formatDateTimeStandard(Date date) {
        return format(DATE_TIME_FORMAT_STANDARD, date);
    }

    public static String formatDateTimeStandard(long milliseconds) {
        Date date = new Date();
        date.setTime(milliseconds);
        return format(DATE_TIME_FORMAT_STANDARD, date);
    }

    public static String formatUnixTimeStandard(long seconds){
        Date date = new Date();
        date.setTime(seconds * 1000);
        return format(DATE_TIME_FORMAT_STANDARD, date);
    }

    /**
     * 标准格式化日期
     *
     * @param date
     * @return
     */
    public static String formatDateStandard(Date date) {
        return format(DATE_FORMAT_STANDARD, date);
    }

    public static String formatTimeStandard(Date date){
        return format(TIME_FORMAT_STANDARD, date);
    }



    public static String format(String formatFrom, String formatTo, String date) {

        return getDateFormat(formatTo).format(date);
    }

    /**
     * 标准格式化日期
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static String formatDateStandard(String formatTo, String date) {
        return format(DATE_FORMAT_STANDARD, formatTo, date);
    }
}
