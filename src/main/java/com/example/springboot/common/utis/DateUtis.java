package com.example.springboot.common.utis;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.springboot.common.dto.DateTo;

/**
 * 
* @author 作者 zhangsiming: 
* @version 创建时间：2018年10月25日 上午9:35:28 
* 	类说明:时间转换处理方式公共方法类
 */
public class DateUtis {
		/**
		 * 定义时间类型常量
		 */
		//时间格式HH:mm
		public static String HH_MM="HH:mm";
		//时间格式 HH:ss
		public static String HH_MM_SS="HH:mm:ss";
		//时间格式 yyyy-MM-dd HH:mm:ss
		public final static String DATETIME = "yyyy-MM-dd HH:mm:ss";
		//时间格式 yyyyMM
		public static String YYYYMM = "yyyyMM";
		 //时间格式 yyyy-MM
	    public static String YYYY_MM = "yyyy-MM";
		// 时间格式 yyyyMMdd
		public static String YYYYMMDD = "yyyyMMdd";
		//时间格式 yyyy-MM-dd
		public static String YYYY_MM_DD = "yyyy-MM-dd";
		// 时间格式 yyyyMMddHHmmss
		public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
		//时间格式 yyyy/MM/dd HH:mm:ss(使用24小时制格式化日期)
		public static String TWENTY_FOUR_HOURS= "yyyy/MM/dd HH:mm:ss";
		// 时间格式 yyyy-MM-dd HH:mm:ss.SSS
		public static String MILLISECOND = "yyyy-MM-dd HH:mm:ss.SSS";
		
		/**
		 *  根据时间戳生成对应时间（yyyy-MM-dd HH:mm:ss）
		 * @param time
		 * @return
		 */
		public static String loadTime(long time) {
			Calendar inCalendar = Calendar.getInstance();
			inCalendar.setTimeInMillis(time);
			return new SimpleDateFormat(DATETIME).format(inCalendar.getTime());
		}
		
		/**
		 *  根据时间戳和对应的时间格式生成对应的时间
		 * @param time
		 * @param format
		 * @return
		 */
		public static String loadTime(long time,String format) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(time);
			return new SimpleDateFormat(format).format(calendar.getTime());
		}
		
		/**
		 * 根据时间和日期格式获取时间搓
		 * @param date
		 * @param pattern
		 * @return
		 */
		public static long loadTime(String date,String pattern) {
			SimpleDateFormat format	=	new SimpleDateFormat(pattern);
			try {
				return format.parse(date).getTime();
			} catch (ParseException e) {
				return 0;
			}
		}
		
		/**
		 * 获取前i天的日期字符串  格式：yyyy-MM-dd
		 * @param i
		 * @return
		 */
		public static String getYesterday(Integer i){
	        return getYesterday(i,YYYY_MM_DD);
	    }
	    
	    /**
	     * 根据入参格式获取前i天的日期字符串  格式：入参
	     * @param i
	     * @return
	     */
		public static String getYesterday(Integer i,String format){
	        Calendar calendar = Calendar.getInstance();
	        calendar.add(Calendar.DATE, i);
	        Date date = calendar.getTime();
	        DateFormat df = new SimpleDateFormat(format);
	        return df.format(date);
	    }
	    
	    
		/**
		 * 根据yyyy-MM-dd HH:mm:ss格式的时间获取时间戳
		 * @param date
		 * @return
		 */
		public static long getTime(String date) {
			SimpleDateFormat format = new SimpleDateFormat(DATETIME);
			try {
				return format.parse(date).getTime();
			} catch (ParseException e) {
				return 0;
			}
		}


		/**
		 * string  转换成date
		 * 格式 yyyy-MM-dd HH:mm:ss
		 * @param str
		 * @return
		 * @throws ParseException
		 */
		public static Date stringToDateTime(String str) throws ParseException {
			SimpleDateFormat sdf = new SimpleDateFormat(DATETIME);//小写的mm表示的是分钟
			return sdf.parse(str);
		}

		/**
		 * 根据传入格式的字符串时间转换为日期
		 * @param date
		 * @param format
		 * @return
		 */
		public static Date parse(String date, String format) {
			try {
				return new SimpleDateFormat(format).parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * 根据传入格式的日期转换为字符串时间
		 * @param date
		 * @param format
		 * @return
		 */
		public static String format(Date date, String format) {
			return new SimpleDateFormat(format).format(date);
		}

		/**
		 * string  转换成date
		 * 格式 yyyy-MM-dd
		 * @param str
		 * @return
		 * @throws ParseException
		 */
		public static Date stringToDate(String str) throws ParseException {
			SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
			return sdf.parse(str);
		}

		/**
		 * 返回年月日   指定格式,eg -->2066-04-18
		 *
		 * @return
		 */
		public static String getMonthAndDay(String format) {
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			return year + format + month + format + day;
		}

		/**
		 * 返回年月日   指定格式,eg -->2066-04-18-10
		 *
		 * @return
		 */
		public static String getMonthDayAndHour(String format) {
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			return year + format + month + format + day + format + hour;
		}


		/**
		 * yyyy-mm-dd hh:mm:ss 类型的日期返回date类型
		 *
		 * @param date
		 * @return
		 * @throws ParseException
		 */
		public static Date getDateTimeFromString(String date){
			SimpleDateFormat format=new SimpleDateFormat(DATETIME);
			try {
				return format.parse(date);
			} catch (Exception e) {
				return null;
			}
		}

		/**
		 * yyyy-mm-dd 类型的日期返回date类型
		 * @param date
		 * @return
		 * @throws ParseException
		 */
		public static Date getDateFromString(String date){
			SimpleDateFormat format=new SimpleDateFormat(YYYY_MM_DD);
			try {
				return format.parse(date);
			} catch (Exception e) {
				return null;
			}
		}
		public static Date getTimeFromString(String date){
			SimpleDateFormat format=new SimpleDateFormat(HH_MM);
			try {
				return format.parse(date);
			} catch (Exception e) {

				return null;
			}
		}

		/**
		 * yyyy-mm-dd hh:mm:ss 类型的日期返回date类型
		 *
		 * @param date
		 * @return
		 * @throws ParseException
		 */
		public static Date getDateFromString1(String date) {
			SimpleDateFormat format = new SimpleDateFormat(TWENTY_FOUR_HOURS);
			try {
				return format.parse(date);
			} catch (Exception e) {
				return null;
			}
		}

		/**
		 * 返回yyyy-mm-dd hh:mm:ss 类型的日期
		 *
		 * @param date
		 * @return
		 */
		public static String getStringFromDate(Date date) {
			SimpleDateFormat format = new SimpleDateFormat(DATETIME);
			return format.format(date);
		}
		public static String getStringDate(Date date) {
			SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD);
			return format.format(date);
		}

		public static String getStringFromTime(Date date) {
			SimpleDateFormat format = new SimpleDateFormat(HH_MM_SS);
			return format.format(date);
		}
		public static String getSrreingTime(Date date) {
			SimpleDateFormat format = new SimpleDateFormat(HH_MM);
			return format.format(date);
		}
		/**
		 * 返回自定义 类型的日期
		 *
		 * @param date
		 * @return
		 */
		public static String getStringFromDate(Date date,String simple) {
			SimpleDateFormat format = new SimpleDateFormat(simple);
			return format.format(date);
		}
		/**
		 * 向后滚动time毫秒的时间
		 *
		 * @param date
		 * @param time
		 * @return
		 */
		public static Date rollDate(Date date, long time) {
			long bofore = date.getTime();
			return new Date(bofore + time);

		}

		/**
		 * 返回yyyyMMdd格式的日期字符串
		 *
		 * @param date
		 * @return
		 */
		public static String getStringFromDate2(Date date) {
			SimpleDateFormat format = new SimpleDateFormat(YYYYMMDD);
			return format.format(date);
		}

		
		/**
		 * yyyy-mm-dd hh:mm:ss   时间格式为空转换
		 *
		 * @param date
		 * @return
		 */
		public static String dateTimeToString(Date date) {
			if (date == null || date.equals("null")) {
				return "";
			}
			SimpleDateFormat sdf = new SimpleDateFormat(DATETIME);
			return sdf.format(date);
		}

		/**
		 * 将制定日期 返回格式HH:mm:ss 的字符串
		 */
		public static String timeToString(Date date) {
			if (date == null || date.equals("null")) {
				return "";
			}
			SimpleDateFormat sdf = new SimpleDateFormat(HH_MM_SS);
			return sdf.format(date);
		}
		/**
		 * yyyy-mm-dd 时间格式为空转换
		 *
		 * @param date
		 * @return
		 */
		public static String dateToString(Date date) {
			if (date == null || date.equals("null")) {
				return "";
			}
			SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
			return sdf.format(date);
		}

		/**
		 * yyyy-mm-dd hh:mm:ss   时间格式为空转换
		 *
		 * @param date
		 * @return
		 */
		public static String dateToShortString(Date date) {
			if (date == null || date.equals("null")) {
				return "";
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			return sdf.format(date);
		}

		/**
		 * 根据 年、月 获取对应的月份 的 天数
		 */
		public static int getDaysByYearMonth(int year, int month) {
			Calendar a = Calendar.getInstance();
			a.set(Calendar.YEAR, year);
			a.set(Calendar.MONTH, month - 1);
			a.set(Calendar.DATE, 1);
			a.roll(Calendar.DATE, -1);
			int maxDate = a.get(Calendar.DATE);
			return maxDate;
		}
		/**
		 * yyyy-mm-dd 时间格式化
		 *
		 * @param date
		 * @return
		 */
		public static Date dateToDate(Date date) throws ParseException {
			if (date == null || date.equals("null")) {
				return null;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
			return sdf.parse(sdf.format(date));
		}
		public static Date dateToDates(Date date) throws ParseException {
			SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
			return sdf.parse(sdf.format(date));
		}
		/**
		 * @description: 2个时间比较 跨天自动累加
		 * @create: 2018-05-25 13:43
		 **/
		public static DateTo dateSpan(Date time,Date startTime, Date endTime) throws ParseException {
			String start=dateToString(time);
			DateTo dateTo =new DateTo();
			// 开始时间
			Calendar startNow = Calendar.getInstance();
			startNow.setTime(startTime);
			// 结束时间
			Calendar endNow = Calendar.getInstance();
			endNow.setTime(endTime);
			SimpleDateFormat sdf = new SimpleDateFormat(DATETIME);
			Date startDate=sdf.parse(start+" "+startNow.get(Calendar.HOUR_OF_DAY)+":"+startNow.get(Calendar.MINUTE)+":00");
			dateTo.setStartTime(startDate);
			String end = dateToString(time);
			if(startNow.get(Calendar.HOUR_OF_DAY)!=endNow.get(Calendar.HOUR_OF_DAY)){
				endNow.add(Calendar.MINUTE, -1);
			}else{
				if(startNow.get(Calendar.MINUTE)!=endNow.get(Calendar.MINUTE)){
					endNow.add(Calendar.MINUTE, -1);
				}
			}
			if(startNow.get(Calendar.HOUR_OF_DAY)>endNow.get(Calendar.HOUR_OF_DAY)){
				end=dateToString(getFetureDate(time,1));
			}else if(startNow.get(Calendar.HOUR_OF_DAY)==endNow.get(Calendar.HOUR_OF_DAY)) {
				if(startNow.get(Calendar.MINUTE)>endNow.get(Calendar.MINUTE)){
					end=dateToString(getFetureDate(time,1));
				}
			}
			// 结束时间
			Date endDate=sdf.parse(end+" "+endNow.get(Calendar.HOUR_OF_DAY)+":"+endNow.get(Calendar.MINUTE)+":59");
			dateTo.setEndTime(endDate);
			return dateTo;
		}
		public static Boolean dateCompareIntersection(List<DateTo> list,Date startTime, Date endTime) {
			Calendar startNow = Calendar.getInstance();
			startNow.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,startTime.getHours(),startTime.getMinutes(),0);
			startNow.set(Calendar.MILLISECOND, 0);
			Calendar endNow = Calendar.getInstance();
			endNow.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,endTime.getHours(),endTime.getMinutes(),0);
			endNow.set(Calendar.MILLISECOND, 0);
			if(startNow.get(Calendar.HOUR_OF_DAY)>endNow.get(Calendar.HOUR_OF_DAY)){
				endNow.add(Calendar.DATE,1);
			}else if(startNow.get(Calendar.HOUR_OF_DAY)==endNow.get(Calendar.HOUR_OF_DAY)) {
				if (startNow.get(Calendar.MINUTE) > endNow.get(Calendar.MINUTE)) {
					endNow.add(Calendar.DATE, 1);
				}
			}
			for(DateTo dateTo:list){
				Calendar listStart = Calendar.getInstance();
				listStart.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,dateTo.getStartTime().getHours(),dateTo.getStartTime().getMinutes(),0);
				listStart.set(Calendar.MILLISECOND, 0);
				Calendar listend = Calendar.getInstance();
				listend.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,dateTo.getEndTime().getHours(),dateTo.getEndTime().getMinutes(),0);
				listend.set(Calendar.MILLISECOND, 0);
				if(listStart.get(Calendar.HOUR_OF_DAY)>listend.get(Calendar.HOUR_OF_DAY)){
					listend.add(Calendar.DATE,1);
				}else if(listStart.get(Calendar.HOUR_OF_DAY)==listend.get(Calendar.HOUR_OF_DAY)) {
					if (listStart.get(Calendar.MINUTE) > listend.get(Calendar.MINUTE)) {
						listend.add(Calendar.DATE, 1);
					}
				}
				//时间判断是否 存在交集
				if((startNow.getTimeInMillis()<listStart.getTimeInMillis()) && endNow.getTimeInMillis()>listStart.getTimeInMillis()){
					return true;
				}else if((startNow.getTimeInMillis()>listStart.getTimeInMillis())&& startNow.getTimeInMillis()<listend.getTimeInMillis()){
					return true;
				}else if((startNow.getTimeInMillis()==listStart.getTimeInMillis())|| endNow.getTimeInMillis()==listend.getTimeInMillis()){
					return false;
				}
			}
			return false;
		}
		public static Boolean dateCompareInclude (DateTo date,Date startTime, Date endTime){
			Calendar startNow = Calendar.getInstance();
			startNow.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,startTime.getHours(),startTime.getMinutes(),0);
			startNow.set(Calendar.MILLISECOND, 0);
			Calendar endNow = Calendar.getInstance();
			endNow.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,endTime.getHours(),endTime.getMinutes(),0);
			endNow.set(Calendar.MILLISECOND, 0);
			if(startNow.get(Calendar.HOUR_OF_DAY)>endNow.get(Calendar.HOUR_OF_DAY)){
				endNow.add(Calendar.DATE,1);
			}else if(startNow.get(Calendar.HOUR_OF_DAY)==endNow.get(Calendar.HOUR_OF_DAY)) {
				if (startNow.get(Calendar.MINUTE) > endNow.get(Calendar.MINUTE)) {
					endNow.add(Calendar.DATE, 1);
				}
			}
			Calendar start = Calendar.getInstance();
			start.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,date.getStartTime().getHours(),date.getStartTime().getMinutes(),0);
			start.set(Calendar.MILLISECOND, 0);
			Calendar end = Calendar.getInstance();
			end.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,date.getEndTime().getHours(),date.getEndTime().getMinutes(),0);
			end.set(Calendar.MILLISECOND, 0);
			if(start.get(Calendar.HOUR_OF_DAY)>end.get(Calendar.HOUR_OF_DAY)){
				end.add(Calendar.DATE,1);
			}else if(start.get(Calendar.HOUR_OF_DAY)==end.get(Calendar.HOUR_OF_DAY)) {
				if (start.get(Calendar.MINUTE) > end.get(Calendar.MINUTE)) {
					end.add(Calendar.DATE, 1);
				}
			}
			if(start.compareTo(startNow)>0&&start.compareTo(endNow)>0){
				startNow.add(Calendar.DATE, 1);
				endNow.add(Calendar.DATE, 1);
			}
			//时间判断是否 包含
			if(start.compareTo(startNow)!=1 && end.compareTo(endNow)!=-1){
				return true;
			}
			return false;
		}

		/**
		 * 获取未来 第 past 天的日期
		 * @param past
		 * @return
		 */
		public static Date getFetureDate(Date date,int past) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
			Date today = calendar.getTime();
			return today;
		}

		/**
		 * 获取一秒
		 */
		public static Date getFetureSecond(Date date,int past) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + past);
			Date today = calendar.getTime();
			return today;
		}
}
