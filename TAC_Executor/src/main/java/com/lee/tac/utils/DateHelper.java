package com.lee.tac.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * author zhao lee
 */
public class DateHelper {
    /**
     *获取当前日期
     *
     * @param pattern Dateformat pattern
     * @return String
     */
    public static String getCurrentDate(String pattern){
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format( now );
    }

    /**
     *获取当前月
     *
     * @return String
     */
    public static Integer getSpecifiedMonth(int offset){
        Calendar c = Calendar.getInstance();
        Date now = new Date();
        c.setTime(now);
        c.add(Calendar.MONTH,offset);
        String month = new SimpleDateFormat("yyyy-MM").format(c.getTime());
        return Integer.valueOf(month.split("-")[1]);
    }

    /**
     *获取当前13位时间戳
     *
     * @return String
     */
    public static String getCurrentStamp(){
        long currentTime=System.currentTimeMillis();
        return String.valueOf(currentTime);
    }
    /**
     *获取当前10位时间戳
     *
     * @return String
     */
    public static String getCurrentShortStamp(){
        long currentTime=System.currentTimeMillis();
        return String.valueOf(currentTime/1000);
    }
    /**
     *获取特定10位的时间戳
     *
     * @return String
     */
    public static String getSpecifiedStamp(String specified,String pattern){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = simpleDateFormat.parse(specified);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long currentTime=date.getTime();
        return String.valueOf(currentTime/1000);
    }

    /**
     *获取特定13位的时间戳
     *
     * @return String
     */
    public static String getSpecifiedLongStamp(String specified,String pattern){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = simpleDateFormat.parse(specified);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(date.getTime());
    }
    /**
     *  Calender类获得指定日期加几天
     *
     * @param d  day
     * @param pattern format "yyyy-MM-dd HH:mm" or "yyyy-MM-dd"
     * @return
     */
    public static String getSpecifiedDayAfter(String specified,int d, String pattern) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat(pattern).parse(specified);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + d);
        String dayAfter = new SimpleDateFormat(pattern).format(c.getTime());
        return dayAfter;
    }
    /**
     *  Calender类获得指定日期加N月
     *
     * @return
     */
    public static String getSpecifiedHourAfter(String specified,int hour, String pattern) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat(pattern).parse(specified);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        c.add(Calendar.HOUR,hour);
        String dayAfter = new SimpleDateFormat(pattern).format(c.getTime());
        return dayAfter;
    }
    /**
     *  Calender类获得指定日期加N月
     *
     * @return
     */
    public static String getSpecifiedMinuteAfter(String specified,int minute, String pattern) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat(pattern).parse(specified);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        c.add(Calendar.MINUTE,minute);
        String dayAfter = new SimpleDateFormat(pattern).format(c.getTime());
        return dayAfter;
    }

    /**
     *  Calender类获取某月第一天
     *
     * @return
     */
    public static String getMonthFirstDay(int offset) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, offset);
        c.set(Calendar.DAY_OF_MONTH,1);
        String first = format.format(c.getTime());
        return first;
    }

    /**
     *  Calender类获取某月最后一天
     *
     * @return
     */
    public static String getMonthLastDay(int offset) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, offset);
        cale.set(Calendar.DAY_OF_MONTH, cale.getActualMaximum(Calendar.DAY_OF_MONTH));
        String lastDay = format.format(cale.getTime());
        return lastDay;
    }

    /**
     * 判断是否是周末
     * @return
     */
    public static boolean isWeekend(String specified,String pattern){
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat(pattern).parse(specified);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int week=c.get(Calendar.DAY_OF_WEEK)-1;
        if(week ==6 || week==0){//0代表周日，6代表周六
            return true;
        }
        return false;
    }

    /**
     *
     * @param st 区间开始时间
     * @param et 区间结束时间
     * @param current 当前时间
     * @return 判断是否在这区间内
     */
    public static boolean isBetween(String st, String et, String current, String pattern){
        if(Long.valueOf(getSpecifiedStamp(current,pattern))<Long.valueOf(getSpecifiedStamp(et,pattern))&&
                Long.valueOf(getSpecifiedStamp(current,pattern))>Long.valueOf(getSpecifiedStamp(st,pattern))){
            return true;
        }else{
            return false;
        }
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(getMonthFirstDay(-2));
        System.out.println(getMonthLastDay(-2));
        System.out.println(getSpecifiedMonth(-2));

    }

}
