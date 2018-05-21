package com.jd.mongodbclient.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * ����ת���Ĺ�����
 * @author lishuai
 * @version 1.0
 * @since 1.0
 *
 */
public class DateUtil {
  public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException
  {
    Date currentDate = DateUtil.createDate("2010-8-20 12:01:01",YYYYMMDD_HHMMSS);
    Date d =  DateUtil.getAfterHoursDate(currentDate,1);
    System.out.println(DateUtil.format(d, YYYYMMDD_HHMMSS));
  }
  private static final Logger logger = Logger.getLogger(DateUtil.class);
  public static final String YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
  public static final String YYYYMMDD = "yyyy-MM-dd";
  public static final String YYYYMMDD_000000 = "yyyy-MM-dd 00:00:00";
  public static Date getAfterHoursDate(Date currentDate,int hours)
  {
    Date returnDate = null;
    Calendar c = Calendar.getInstance();
    c.setTime(currentDate);
    c.add(Calendar.HOUR, hours);
    Date d = c.getTime();
    SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD_HHMMSS);
    try {
      returnDate = sdf.parse(sdf.format(d));
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return returnDate;
  }
  /**
   * ��ȡһ��ʱ�䷶Χ�ڵ���������
   * @param beginDate ��ʼ����
   * @param endDate ��������
   * @return
   */
  public static List<Date> getDates(Date beginDate,Date endDate)
  {
    beginDate = formatToDate(beginDate,YYYYMMDD);
    endDate = formatToDate(endDate,YYYYMMDD);
    List<Date> dates = new ArrayList<Date>();;
    if(endDate.compareTo(beginDate)>0)
    {
      Date index = formatToDate(beginDate,YYYYMMDD);
      while(index.compareTo(endDate)<=0){
        dates.add(index);
        index = getTomorrow(index);
      }
    }
    else if(endDate.compareTo(beginDate)==0){
      dates.add(formatToDate(beginDate,YYYYMMDD));
    }
    return dates;
  }
  
  
  /**
   * ��ȡ�������ڵ�ǰһ����
   * @param currentDate ��������
   * @return �������ڵ�ǰһ���µ�����
   */
  public static Date getDateBeforeMonth(Date currentDate)
  {
    Calendar c = Calendar.getInstance();
    c.setTime(currentDate);
    c.add(Calendar.MONTH, -1);
    Date d = c.getTime();
    SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
    return createDate(sdf.format(d),YYYYMMDD);
  }
  /**
   * ��ȡ�������ڵ�ǰһ��
   * @param currentDate ��������
   * @return �������ڵ�ǰһ��
   */
  public static Date getYesterday(Date currentDate)
  {
    Calendar c = Calendar.getInstance();
    c.setTime(currentDate);
    c.add(Calendar.DATE, -1);
    Date d = c.getTime();
    SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
    return createDate(sdf.format(d),YYYYMMDD);
  }
  /**
   * ��ȡ�������ڵĺ�һ��
   * @param currentDate ��������
   * @return �������ڵ�ǰһ��
   */
  public static Date getTomorrow(Date currentDate)
  {
    Calendar c = Calendar.getInstance();
    c.setTime(currentDate);
    c.add(Calendar.DATE, 1);
    Date d = c.getTime();
    SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
    return createDate(sdf.format(d),YYYYMMDD);
  }
  /**
   * ��ȡ�������ڵĵ��µ�һ��
   * @param currentDate ��������
   * @return �������ڵĵ��µ�һ��
   */
  public static Date getFirstDayOfMonth(Date currentDate)
  {
    Calendar c = Calendar.getInstance();
    c.setTime(currentDate);
    c.set(Calendar.DATE, 1);
    Date d = c.getTime();
    SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
    return createDate(sdf.format(d),YYYYMMDD);
  }
  /**
   * ��ʱ���ʽ���ַ�����ת��ΪDate
   * @param dateFormat exp: 2010-7-22
   * @return java.util.Date
   */
  public static Date createDate(String dateString,String dateFormat)
  {
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
    Date d = null;
    try {
      d = sdf.parse(dateString);
    } catch (ParseException e) {
      logger.error("�ַ���ת��ΪDateʧ��,[string=" + dateString + "]", e);
    }
    return d;
  }
  /**
   * ��ʱ��ת��Ϊָ����ʽ���ַ���
   * @author lishuai
   * @param date ��������
   * @param format ���ڸ�ʽ
   * @return �ַ�����ʽ������
   */
  public static String format(Date date,String format)
  {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(date);
  }
  public static Date formatToDate(Date date,String format)
  {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return createDate(sdf.format(date),format);
  }

  /**
   * ��ȡ��ǰ���� yyyy-MM-dd
   *
   * @return
   */
  public static String getNowDate() {
    SimpleDateFormat df = new SimpleDateFormat(YYYYMMDD);
    return df.format(new Date());
  }

  public static String formatDate(Date date){
    if(null == date){
      return "";
    }
    return format(date, YYYYMMDD);
  }
}
