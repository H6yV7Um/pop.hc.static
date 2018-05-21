package com.jd.help.utils;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by yfxialiang on 2018/5/15.
 */
public class DateUtils {
    private final static Logger logger = LogManager.getLogger(DateUtils.class);


    public final static String FULLHOURTIME = " 23:59:59";
    private final static String dateformat = "yyyy-MM-dd";
    private final static String dateNOLineformat = "yyyyMMdd";
    private final static String dateformatYyyyMMddHH = "yyyyMMddHH";
    private final static String dateHourMinuteformat = "yyyy-MM-dd HH:mm";
    private final static String dateHourMinuteSecondformat = "yyyy-MM-dd HH:mm:ss";
    private final static String dateHourMinuteSecondformatSSS = "yyyy-MM-dd HH:mm:ss SSS";
    private final static String dateHourMinuteSecondNOLineformat = "yyyyMMddHHmmss";
    public final static String HH_MM = "HH:mm";
    public final static String DD_HH_MM = "DD��HHСʱmm��";
    private static final Map<String, ThreadLocal<SimpleDateFormat>> pool = new HashMap<String, ThreadLocal<SimpleDateFormat>>();
    private static final Object lock = new Object();

    public static SimpleDateFormat getDateFormat(String pattern) {
        ThreadLocal<SimpleDateFormat> tl = pool.get(pattern);
        if (tl == null) {
            synchronized (lock) {
                tl = pool.get(pattern);
                if (tl == null) {
                    final String p = pattern;
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        protected synchronized SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(p);
                        }
                    };
                    pool.put(p, tl);
                }
            }
        }
        return tl.get();
    }

    /**
     * �������ʱ��long����
     *
     * @return
     */
    public static long getNowTimeLong() {
        return System.currentTimeMillis();
    }

    /**
     * ��õ�ǰ����Date��
     * @return
     */
    public static Date getNowDate() {
        Date date = new Date();
        String dateString = getDateFormat(dateformat).format(date);
        try {
            date = getDateFormat(dateformat).parse(dateString);
        } catch (ParseException e) {
            logger.error("ParseException", e);
        }
        return date;
    }

    /**
     * ��õ�ǰ����long����
     *
     * @return
     */
    public static long getNowDateLong() {
        return getNowDate().getTime();
    }

    /**
     * ��õ�ǰ����long����
     *
     * @return
     */
    public static long getDateLong(String dateString) {
        Date date = null;
        try {
            date = getDateFormat(dateformat).parse(dateString);
        } catch (ParseException e) {
            logger.error("ParseException", e);
        }
        return date.getTime();
    }

    /**
     * ��ø���ʱ��long����
     *
     * @return
     */
    public static long getTimeLong(String time) {
        Date date = null;
        try {
            date = getDateFormat(dateHourMinuteSecondformat).parse(time);
        } catch (ParseException e) {
            logger.error("ParseException", e);
        }
        return date.getTime();
    }


    /**
     * ���date and week
     *
     * @param beginTime
     * @return
     */
    public static DateweekBean getDateWeekBean(Date beginTime) {
        Calendar calBegin = Calendar.getInstance();
        // ʹ�ø����� Date ���ô� Calendar ��ʱ��
        calBegin.setTime(beginTime);
        //��ӵ�һ��
        DateweekBean bean = new DateweekBean();
        bean.setDate(DateUtils.getDateString(beginTime));
        bean.setWeek(getWeekDay(calBegin.get(Calendar.DAY_OF_WEEK)));
        bean.setIspast(ispast(getDateString(beginTime)));
        return bean;
    }

    /**
     * ��ȡ��ǰ���ڣ���������
     *
     * @return
     */
    public static String getNowDateStringNOLine() {
        return getDateFormat(dateNOLineformat).format(new Date());
    }

    /**
     * ��ȡָ�����ڣ���������
     *
     * @return
     */
    public static String getDateStringNOLine(Date date) {
        return getDateFormat(dateNOLineformat).format(date);
    }

    /**
     *
     * getter of datestringyyyymmddhh
     * @param date
     *
     **/
    public static String getDateStringYyyyMMddHH(Date date) {
        return getDateFormat(dateformatYyyyMMddHH).format(date);
    }

    /**
     * ��ȡ��ǰʱ��
     *
     * @return
     */
    public static String getNowString() {
        return getDateFormat(dateHourMinuteSecondformat).format(new Date());
    }

    /**
     * ��ȡ��ǰʱ�䣬��������
     *
     * @return
     */
    public static String getNowStringNOLine() {
        return getDateFormat(dateHourMinuteSecondNOLineformat).format(new Date());
    }

    /**
     * ��ȡdate����������
     *
     * @return
     */
    public static String getNowStringNOLine(Date date) {
        return getDateFormat(dateHourMinuteSecondNOLineformat).format(date);
    }

    /**
     * ��ȡ��ǰ����
     *
     * @return
     */
    public static String getNowDateString() {
        return getDateFormat(dateformat).format(new Date());
    }

    /**
     * ��ȡʱ���ַ���
     *
     * @param time
     * @return
     */
    public static String getTimeString(Date time) {
        return getDateFormat(dateHourMinuteSecondformat).format(time);
    }

    /**
     * ��ȡʱ���ַ���
     *
     * @param time
     * @return
     */
    public static String getTimeStringSSS(Date time) {
        return getDateFormat(dateHourMinuteSecondformatSSS).format(time);
    }
    /**
     * ��ȡ�����ַ���
     *
     * @param time
     * @return
     */
    public static String getDateString(Date time) {
        return getDateFormat(dateformat).format(time);
    }

    /**
     * ��ȡ���ڸ�ʽyyyy-MM-dd
     *
     * @param time
     * @return
     */
    public static Date getDateDate(String time) {
        Date d = null;
        try {
            d = getDateFormat(dateformat).parse(time);
        } catch (ParseException e) {
            logger.error("ʱ���ʽת������", e);
        }
        return d;
    }

    /**
     * ��ȡ���ڸ�ʽ yyyy-MM-dd hh:mm:ss
     *
     * @param time
     * @return
     * @throws java.text.ParseException
     */
    public static Date getTimeDate(String time) {
        Date d = null;
        try {
            d = getDateFormat(dateHourMinuteSecondformat).parse(time);
        } catch (ParseException e) {
            logger.error("��ʽת������", e);
        }
        return d;
    }


    /**
     * ��ȡ���ڸ�ʽ yyyyMMddHHmmss
     * @param time
     * @return
     * @throws java.text.ParseException
     */
    public static Date getTimeDate2(String time) {
        Date d = null;
        try {
            d = getDateFormat(dateHourMinuteSecondNOLineformat).parse(time);
        } catch (ParseException e) {
            logger.error("��ʽת������", e);
        }
        return d;
    }

    /**
     * ��ȡ���ڸ�ʽ yyyy-MM-dd hh:mm
     *
     * @param time
     * @return
     * @throws java.text.ParseException
     */
    public static Date getTimeDateNoSecond(String time) {
        Date d = null;
        try {
            d = getDateFormat(dateHourMinuteformat).parse(time);
        } catch (ParseException e) {
            logger.error("��ʽת������", e);
        }
        return d;
    }

    public static String getTimeStringNoSecond(Date time) {
        return getDateFormat(dateHourMinuteformat).format(time);
    }

    /**
     * ��ȡHH:MM��ʽʱ��
     * @param date
     * @return
     */
    public static String getTimeHHMM(Long date){
        return getDateFormat(HH_MM).format(new Date(date));
    }
    /**
     * ��ȡ xx��xxСʱxx�� ��ʽʱ��
     * @return
     */
    public static String formatDuring(long mss) {
        long days = mss / (60 * 60 * 24);
        long hours = (mss % (60 * 60 * 24)) / (60 * 60);
        long minutes = (mss % (60 * 60)) / (60);
        //long seconds = (mss % (1000 * 60)) / 1000;
        return days + "��" + hours + "Сʱ" + minutes + "��";
    }


    /**
     * ��ȡһ����֮ǰ������
     *
     * @param time
     * @return
     */
    public static Date getLastMonthTime(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTime();
    }

    /**
     * ��ȡһָ������֮ǰ��ʱ��
     *
     * @param
     * @return
     * @throws java.text.ParseException
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - day);
        return calendar.getTime();
    }
    /**
     * ��ȡһָ������֮ǰ��ʱ��
     *
     * @param
     * @return
     * @throws java.text.ParseException
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + day);
        return calendar.getTime();
    }

    /**
     * ��ȡһָ��������֮���ʱ��
     * @param d ָ������
     * @param millSeconds ָ������
     * @return
     */
    public static Date getDateAfterNms(Date d, int millSeconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.MILLISECOND, millSeconds);
        return calendar.getTime();
    }

    /**
     * ��ȡһָ������֮ǰ��ʱ��
     *
     * @param
     * @return
     * @throws java.text.ParseException
     */
    public static Date getYearAfter(Date d, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + year);
        return calendar.getTime();
    }

    /**
     *
     * getter of datedatebefore
     * @param d
     * @param day
     *
     **/
    public static Date getDateDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     *
     * getter of yesterdaytime
     *
     **/
    public static Date getYesterdayTime() {
        return new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
    }

    /**
     * ��ȡĳ��ȫʱ�䣬date+" 23:59:59"
     *
     * @param date
     * @return
     */
    public static String getFullDayString(String date) {
        return date + FULLHOURTIME;
    }

    /**
     * ��ѯһ��ʱ������������
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    public static List<Date> findDates(Date beginTime, Date endTime) {
        List lDate = new ArrayList();
        lDate.add(beginTime);
        Calendar calBegin = Calendar.getInstance();
        // ʹ�ø����� Date ���ô� Calendar ��ʱ��
        calBegin.setTime(beginTime);
        Calendar calEnd = Calendar.getInstance();
        // ʹ�ø����� Date ���ô� Calendar ��ʱ��
        calEnd.setTime(endTime);
        // ���Դ������Ƿ���ָ������֮��
        while (endTime.after(calBegin.getTime())) {
            // ���������Ĺ���Ϊ�����������ֶ���ӻ��ȥָ����ʱ����
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }

    /**
     * ��ѯ����ʱ��Ĳ�
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    public static Long datesDiff(Date beginTime, Date endTime) {
        long diff = beginTime.getTime() - endTime.getTime();//�����õ��Ĳ�ֵ��΢�뼶��
        return diff;
    }

    /**
     * ��ѯһ��ʱ�����������ں�����
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    public static List<DateweekBean> findDatesAndWeeks(Date beginTime, Date endTime) {
        List lDate = new ArrayList();
        Calendar calBegin = Calendar.getInstance();
        // ʹ�ø����� Date ���ô� Calendar ��ʱ��
        calBegin.setTime(beginTime);
        //��ӵ�һ��
        DateweekBean bean = new DateweekBean();
        bean.setDate(DateUtils.getDateString(beginTime));
        bean.setWeek(getWeekDay(calBegin.get(Calendar.DAY_OF_WEEK)));
        bean.setIspast(DateUtils.ispast(DateUtils.getDateString(beginTime)));
        lDate.add(bean);
        Calendar calEnd = Calendar.getInstance();
        // ʹ�ø����� Date ���ô� Calendar ��ʱ��
        calEnd.setTime(endTime);
        // ���Դ������Ƿ���ָ������֮��
        while (endTime.after(calBegin.getTime())) {
            // ���������Ĺ���Ϊ�����������ֶ���ӻ��ȥָ����ʱ����
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            DateweekBean tempBean = new DateweekBean();
            tempBean.setDate(DateUtils.getDateString(calBegin.getTime()));
            tempBean.setWeek(getWeekDay(calBegin.get(Calendar.DAY_OF_WEEK)));
            tempBean.setIspast(DateUtils.ispast(DateUtils.getDateString(calBegin.getTime())));
            lDate.add(tempBean);
        }
        return lDate;
    }

    /**
     * ��ȡ����ʱ���չʾ��ʽ�磺2015��2�µ�2��
     *
     * @return
     */
    public static String getNextWeekDateName() {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 7);
        int year = cal.get(cal.YEAR);
        int month = cal.get(cal.MONTH) + 1;
        int week = cal.get(cal.DAY_OF_WEEK_IN_MONTH);
        return year + "��" + month + "�µ�" + week + "��";
    }

    /**
     * ���������һ
     *
     * @return
     */
    public static Date getNextWeekMonday() {
        Calendar cal = Calendar.getInstance();
        int n = 1;
        cal.add(Calendar.DATE, n * 7);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    /**
     * �����������
     *
     * @return
     */
    public static Date getNextWeekSunday() {
        Calendar cal = Calendar.getInstance();
        int n = 2;
        cal.add(Calendar.DATE, n * 7);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return cal.getTime();
    }

    /**
     * �������ʱ���
     *
     * @return
     */
    public static List<DateweekBean> getNextWeekDates() {

        List<DateweekBean> dates = new ArrayList();
        Calendar cal = Calendar.getInstance();
        int n = 1;
        cal.add(Calendar.DATE, n * 7);
        for (int i = Calendar.MONDAY; i <= Calendar.SATURDAY; i++) {
            DateweekBean bean = new DateweekBean();
            cal.set(Calendar.DAY_OF_WEEK, i);
            bean.setDate(getDateFormat(dateformat).format(cal.getTime()));
            bean.setWeek(getWeekDay(i));
            bean.setIspast(DateUtils.ispast(getDateFormat(dateformat).format(cal.getTime())));
            dates.add(bean);
        }
        cal.add(Calendar.DATE, n * 7);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        DateweekBean bean = new DateweekBean();
        bean.setDate(getDateFormat(dateformat).format(cal.getTime()));
        bean.setWeek(getWeekDay(Calendar.SUNDAY));
        bean.setIspast(DateUtils.ispast(getDateFormat(dateformat).format(cal.getTime())));
        dates.add(bean);
        return dates;
    }

    /**
     * ���week day
     *
     * @param i
     * @return
     */
    public static String getWeekDay(int i) {
        String day;
        switch (i) {
            case 1:
                day = "������";
                break;
            case 2:
                day = "����һ";
                break;
            case 3:
                day = "���ڶ�";
                break;
            case 4:
                day = "������";
                break;
            case 5:
                day = "������";
                break;
            case 6:
                day = "������";
                break;
            case 7:
                day = "������";
                break;
            default:
                day = "������";
                break;
        }
        return day;
    }

    /**
     *
     * @param date
     *
     **/
    public static boolean ispast(String date) {
        long nowdate = getNowDateLong();
        long datelong = getDateLong(date);
        if (nowdate > datelong)
            return true;
        else
            return false;
    }

    /**
     *
     * @param date
     * @param day
     *
     **/
    public static boolean ispast(Date date, int day) {
        long nowdate = System.currentTimeMillis();
        long datelong = date.getTime();
        long pastTime = day * 24 * 60 * 60 * 1000L;
        if (nowdate > (datelong + pastTime))
            return true;
        else
            return false;
    }

    /**
     * ͨ����������ȡʱ��
     *
     * @return
     */
    public static Date getDateByMills(Long mills) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);
        return calendar.getTime();
    }

    /**
     * ��ȡʱ��d��mills����ǰ��date
     * @param d
     * @param mills
     * @return
     */
    public static Date getDateBeforeMills(Date d,int mills){
        Long dateMills = d.getTime();
        Long millsBefore = dateMills - mills;
        return getDateByMills(millsBefore);
    }

    /**
     * ��ȡsolr�������ʱ�䣬��ǰ����ʱ������8Сʱ
     * @param date
     * @return
     */
    public static Date getSolrIndexTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, 8);
        return calendar.getTime();
    }

    /**
     * ��ȡsolr��ѯ��ʱ��
     * @param time
     * @return
     */
    public static String getSolrQueryTime(Long time){
        return DateFormatUtils.format(time,
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    }
    /**
     * ��ȡsolr��ѯ��ʱ��
     * @param time
     * @return
     */
    public static String getSolrQueryTime(String time){
        try {
            return DateFormatUtils.format(getDateFormat("yyyy-MM-dd HH:mm:ss").parse(time),
                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * ��ñ�Сʱ�Ŀ�ʼʱ�䣬��2012-01-01 01:00:00
     *
     * @return
     */
    public static  Date getCurrentHourStartTime(Date date) {
        Date now = null;
        try {
            now = getDateFormat(dateHourMinuteSecondNOLineformat).parse(getDateFormat(dateformatYyyyMMddHH).format(date)+"0000");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * ��ñ�Сʱ�Ľ���ʱ�䣬��2012-01-01 23:59:59
     *
     * @return
     */
    public static  Date getCurrentHourEndTime(Date date) {
        Date now = new Date();
        try {
            now = getDateFormat(dateHourMinuteSecondNOLineformat).parse(getDateFormat(dateformatYyyyMMddHH).format(date)+"5959");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }



    /**
     * ��ñ���Ŀ�ʼʱ�䣬��2012-01-01 01:00:00
     *
     * @return
     */
    public static  Date getCurrentDayStartTime(Date date) {
        Date now = null;
        try {
            now = getDateFormat(dateHourMinuteSecondNOLineformat).parse(getDateFormat(dateNOLineformat).format(date)+"000000");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * ��ñ���Ľ���ʱ�䣬��2012-01-01 23:59:59
     *
     * @return
     */
    public static  Date getCurrentDayEndTime(Date date) {
        Date now = new Date();
        try {
            now = getDateFormat(dateHourMinuteSecondNOLineformat).parse(getDateFormat(dateNOLineformat).format(date)+"235959");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * ��ȡ��һ������Ŀ�ʼʱ��
     * @param now
     * @param interval ����
     * @return
     */
    public static Date getPrevSectionBegin(Date now,int interval){
        return new Date(now.getTime() - (now.getTime() - DateUtils.getCurrentHourStartTime(now).getTime()) % (TimeUnit.MINUTES.toMillis(interval))-TimeUnit.MINUTES.toMillis(interval));
    }

    /**
     * ��ȡ��һ������Ľ���ʱ��
     * @param now
     * @param interval ����
     * @return
     */
    public static Date getPrevSectionEnd(Date now , int interval){
        return new Date(now.getTime()-(now.getTime()-DateUtils.getCurrentHourStartTime(now).getTime())%(TimeUnit.MINUTES.toMillis(interval))-TimeUnit.SECONDS.toMillis(1));
    }

    /**
     * ��ȡ����Ŀ�ʼʱ��
     * @param now
     * @param interval ����
     * @return
     */
    public static Date getSectionBegin(Date now,int interval){
        return new Date(now.getTime() - (now.getTime() - DateUtils.getCurrentHourStartTime(now).getTime()) % (TimeUnit.MINUTES.toMillis(interval)));
    }

    /**
     * ��ȡ����Ľ���ʱ��
     * @param now
     * @param interval ����
     * @return
     */
    public static Date getSectionEnd(Date now , int interval){
        return new Date(now.getTime()-(now.getTime()-DateUtils.getCurrentHourStartTime(now).getTime())%(TimeUnit.MINUTES.toMillis(interval))+TimeUnit.MINUTES.toMillis(6)-TimeUnit.SECONDS.toMillis(1));
    }

    /**
     * LONGתDATE
     * @param dateTime
     * @return
     */
    public static Date getDateFromLong(Long dateTime) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(dateTime);
        return c.getTime();
    }

    /**
     * ��̬ʱ��ת��
     * @param time
     * @return
     */
    public static String getActivityTime(String time){
        StringBuilder builder = new StringBuilder();
        try {
            Date  d =  getDateFormat(dateHourMinuteSecondformat).parse(time);
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            int month = c.get(Calendar.MONTH) + 1;
            int day = c.get(Calendar.DAY_OF_MONTH);
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            builder.append(month);
            builder.append("��");
            builder.append(day);
            builder.append("��");
            builder.append(hour);
            builder.append(":");
            if(minute == 0){
                builder.append("00");
            }else{
                builder.append(minute);
            }
        } catch (ParseException e) {
            logger.error("DateUtil->getActivityTime error",e);
        }
        return builder.toString();
    }
}
