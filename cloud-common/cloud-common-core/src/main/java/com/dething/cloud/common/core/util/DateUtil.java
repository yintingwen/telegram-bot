package com.dething.cloud.common.core.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	private static int array[][] = {{1,2,3},{4,5,6},{7,8,9},{10,11,12}};  
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String HH_MM_SS = "HH:mm:ss";
	public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	/**
	 * 格式化日期和时间
	 * @param date
	 * @param pattren
	 * @return
	 */
	public static String format(Date date, String pattren){
		return DateFormatUtils.format(date,pattren);
	}
	
	/**
	 * 格式化日期：yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static String date(Date date){
		return DateFormatUtils.format(date,YYYY_MM_DD);
	}
	
	/**
	 * 获取当前季度201401
	 * @return
	 * @throws ParseException
	 */
	public static String getCurrentQuarter() throws ParseException {
		Calendar today = Calendar.getInstance();
		return format(today, "yyyy")+getQuarter(today);
	}
	
	/**
	 * 获取季度01
	 * @return
	 * @throws ParseException
	 */
	public static int getQuarter(Calendar time)  {
		Calendar today = time;
		return today.get(Calendar.MONTH) / 3 + 1;
	}
	
	
	public static String format(Calendar fCal, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return (formatter.format(fCal.getTime()));
	}
	
	
	/**
	 * 获取上个季度开始时间
	 * @return
	 */
	public Calendar getLastSeasonStart() {
		Calendar cal = Calendar.getInstance();
		int season = DateUtil.getQuarter(cal);
		int start_month = array[season-1][0];  
		System.out.println(start_month);
		cal.set(Calendar.MONTH, start_month-1);
		cal.add(Calendar.MONTH, -3);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal;
	}
	
	
	
	/**
	 * 格式化日期时间：yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String dateTime(Date date){
		return DateFormatUtils.format(date,YYYY_MM_DD_HH_MM_SS);
	}
	
	/**
	 * 字符串转换时间
	 * @param date
	 * @param pattren
	 * @return
	 */
	public static Date parse2Date(String date,String pattren){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattren);
		try {
			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			throw new IllegalArgumentException("字符串转换为时间出现异常",e);
		}
	}
	
	/**
	 * 时间比较
	 * @param frist
	 * @param second
	 * @return
	 * 			等于0：frist=second<br>
	 * 			小于0：frist&ltsecond <br>
	 * 			大于0：frist&gtsecond<br>
	 */
	public static int compareDate(Date frist, Date second){
		Calendar calendarFrist = Calendar.getInstance();
		Calendar calendarSecond = Calendar.getInstance();
		calendarFrist.setTime(frist);
		calendarSecond.setTime(second);
		return calendarFrist.compareTo(calendarSecond);
	}

	public static Date getHourTime(int clock){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, clock);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		Date clockTime = calendar.getTime();

		return clockTime;
	}

	public static Date increaseDay(Date date, int days){
		Calendar calendar = Calendar.getInstance();

		// 将 Calendar 对象设置为指定的日期
		calendar.setTime(date);

		// 增加一天
		calendar.add(Calendar.DAY_OF_MONTH, days);

		// 获取增加一天后的 Date 对象
		Date newDate = calendar.getTime();

		return newDate;
	}
	

}
