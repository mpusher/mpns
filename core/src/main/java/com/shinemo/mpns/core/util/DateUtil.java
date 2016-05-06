package com.shinemo.mpns.core.util;

import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {

	private static final String YEAR_MONTH = "yyyyMMdd";

    public static Date getFutureHour(Date date, int hour) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }
    
    public static Date getFutureDate(Date date, int day) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, day);
        return calendar.getTime();
    }


	public static Date dateFormat(String date) {
		return dateFormat(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static Date dateFormat(String date, String dateFormat) {
		if (date == null || "".equals(date))
			return null;
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		if (date != null) {
			try {
				return format.parse(date);
			} catch (Exception ex) {}
		}
		return null;
	}

	/**
	 * 获取当前小时内多少秒
	 * @param date
	 * @return
	 */
	public static int getSecondOfDay(Date date){
		Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int hour = ca.get(Calendar.HOUR_OF_DAY);
        int minite = ca.get(Calendar.MINUTE);
        int second = ca.get(Calendar.SECOND);
        return hour*60*60+minite*60+second;
	}
	
	public static String getYearAndMonth(Date date){
		SimpleDateFormat format = new SimpleDateFormat(YEAR_MONTH);
		return format.format(date);
	}

    public static int hourFromDay(Date date){
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int hour = ca.get(Calendar.HOUR);
        return hour;
    }
    
    public static int hourOfDay(Date date){
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int hour = ca.get(Calendar.HOUR_OF_DAY);
        return hour;
    }
    
    public static void main(String[] args) {
		System.out.println(getFutureDate(new Date(), 3));
	}

    
}
