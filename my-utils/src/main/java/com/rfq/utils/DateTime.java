package com.rfq.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

public class DateTime {
	/**
	 * 获取当前的日期yyyy-MM-dd
	 */
	public static String getDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}
	
	/**
	 * 获取当前日期形式：YYYYmmdd
	 * @return
	 */
	public static String getDateByyyyyMMdd() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}
	
	
	/**
	 * 获取当前日期形式：HH:mm:ss
	 * @return
	 */
	public static String getDateByHHmmss() {
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}

	public static String getDate(String formater) {
		return new SimpleDateFormat(formater).format(new Date());
	}

	/**
	 * 获取当前的时间yyyy-MM-dd HH:mm:ss
	 */
	public static String getTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	/**
	 * 获取当前的年份yyyy
	 */
	public static int getYear() {
		Calendar a = Calendar.getInstance();
		return a.get(Calendar.YEAR);
	}
	
	/**
	 * 获取指定日期的年
	 * @param day
	 * @return
	 */
	public static int getYear(Date day) {
		Calendar a = Calendar.getInstance();
		a.setTime(day);
		return a.get(Calendar.YEAR);
	}
	
	/**
	 * 获取月份
	 * @param day
	 * @return
	 */
	public static int getMonth(Date day) {
		Calendar a = Calendar.getInstance();
		a.setTime(day);
		return a.get(Calendar.MONTH)+1;
	}
	public static int getMonth() {
		Calendar a = Calendar.getInstance();
		return a.get(Calendar.MONTH)+1;
	}
	
	
	
	public static int getDay(Date day) {
		Calendar a = Calendar.getInstance();
		a.setTime(day);
		return a.get(Calendar.DAY_OF_MONTH);
	}

	public static String getDayPaddingZero(Date day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(day);
		int dayValue=calendar.get(Calendar.DAY_OF_MONTH);
		return String.format("%02d",dayValue);
	}

	/**
	 * 字符串转日期
	 * 
	 * @param str
	 * @return
	 */
	public static Date StrToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	
	
	/**
	 * 时间戳转日期字符串
	 * @param intvaue
	 * @return
	 */
	public static String IntegerToDate(Integer intvaue) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		long longval=(long)intvaue*1000;
		Date date = new Date(longval);
		String result= format.format(date);
		return result;

	}

	/**
	 * 字符串转时间
	 * 
	 * @param str
	 * @return
	 */
	public static Date StrToTime(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Integer StrToTimestamp(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(str);
			return (int) (date.getTime()/1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 字符串转时间
	 * 
	 * @param str,formats
	 * @return
	 */
	public static Date StrToTimeFormat(String str,String formats) {
		SimpleDateFormat format = new SimpleDateFormat(formats);
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 日期转字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String dateToString(Date date) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String str = format.format(date);
		return str;
	}

	/**
	 * 时间转字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String TimeToStr(Date time) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = format.format(time);
		return str;
	}

	/**
	 * 获取当前的日期加xxx天后yyyy-MM-dd
	 * 
	 * public static String getDate(int x) { org.joda.time.DateTime dt =
	 * org.joda.time.DateTime.now(); org.joda.time.DateTime dt2 = dt.plusDays(x);
	 * return dt2.toString("yyyy-MM-dd"); }
	 */
	/**
	 * 比较日期大小
	 * 
	 * @param date
	 * @param date1
	 * @return
	 */
	public static boolean compare(String date1) {
		Date date = new Date();
		Date d = StrToTime(getDate() + " " + date1 + ":00");
		return d.before(date);
	}

	public static boolean compareTime(String spaceTime, String endTime) {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		String str = format.format(date);
		int hours = Integer.valueOf(str.split(":")[0]);
		int mins = Integer.valueOf(str.split(":")[1]) + Integer.valueOf(spaceTime);
		if (mins / 60 > 0) {
			hours = hours + mins / 60;
			mins = mins % 60;
		}
		Calendar now = Calendar.getInstance();
		now.set(hours, mins);
		return now.before(getDate() + " " + endTime + ":00");
	}

	public static boolean TimeDifference(String alertTime, int spaceTime) {
		boolean isSend = false;
		Date d = StrToTime(getDate() + " " + alertTime + ":00");

		Calendar now = Calendar.getInstance();

		int i = (int) (now.getTimeInMillis() - d.getTime()) / 1000 / 60;

		if (i / spaceTime >= 0 || i % spaceTime < 1) {
			isSend = true;
		}
		return isSend;
	}

	/**
	 * 计算两个日期之间相差的天数 如果 -1 标示格式错误
	 * 
	 * @param startDate 较小的时间
	 * @param enddate  较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date startDate, Date enddate) {
		
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		try {
			startDate = formater.parse(formater.format(startDate));
			enddate = formater.parse(formater.format(enddate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(enddate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}
	
	public static int daysBetween(String startDate, String enddate) {
		Date start=StrToDate(startDate);
		Date end=StrToDate(enddate);
		return daysBetween(start,end);
	}

	/**
	 * 时间戳转当前时间
	 * 
	 * @param s
	 * @return
	 */
	public static String stampToTime(String s) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lt = new Long(s) * 1000;
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		return res;
	}

	
	/**
	 * 获取当前时间戳
	 * @return
	 */
	public static Integer getNowTimestamp() {
		return (int) (System.currentTimeMillis() / 1000);
	}
	
	/**
	 * 获取当前时间戳 long 
	 * @return
	 */
	public static long getNowTimestampOfLong() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * 获取时间戳
	 * @return
	 */
	public static Integer dateToTimestamp(Date date) {
		return (int) (date.getTime() / 1000);
	}
	
	public static Integer dateToTimestamp(String date) {
		Date theDate= StrToDate(date);
		if(theDate!=null)
			return (int) (theDate.getTime() / 1000);
		else
			return 0;
	}
	
	/**
	 * 获取今天凌晨的时间戳
	 * @return
	 */
	public static Integer getTodayTimestamp(){
		long current = System.currentTimeMillis();
        long zero = current/(1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
        return Integer.parseInt(zero/1000+"");
	}
	
	/**
	 * 取得指定日期增加/减少（n为负数时）n天后的日期
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date add(Date date,int n) {
		if(date == null) return null;
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(Calendar.DATE,n);
		return gc.getTime();
	}
	
	/**
	 * 取得当前日期增加/减少（n为负数时）n天后的日期
	 * @param n
	 * @return
	 */
	public static Date add(int n) {
		return add(new Date(),n);
	}
	
	/**
	 * 增加一天
	 * @param intdate
	 * @param n
	 * @return
	 */
	public static Date add(Integer intdate,int n) {
		long longval=(long)intdate*1000;
		Date date = new Date(longval);
		return add(date, n);
	}
	
	public static Date add(String dateString,int n) {
		Date date=StrToDate(dateString);
		return add(date,n);
	}
	public static Date addMonth(int n) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.add(Calendar.MONTH,n);
		return gc.getTime();
	}
	
	public static Date addYear(int n) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.add(Calendar.YEAR,n);
		return gc.getTime();
	}
	
	public static Date addYear(Date date, int n) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(Calendar.YEAR,n);
		return gc.getTime();
	}
	
	public static Date addYear(Integer intdate,int n) {
		long longval=(long)intdate*1000;
		Date date = new Date(longval);
		return addYear(date, n);
	}
	
	/**
	 * 获取指定月份的第一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getFirstDate(int year,int month) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(Calendar.YEAR, year);
		gc.set(Calendar.MONTH, month-1);
		gc.set(Calendar.DAY_OF_MONTH, 1);
		return gc.getTime();
	}
	
	/**
	 * 获取指定月份的最后一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getLastDate(int year,int month) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(Calendar.YEAR, year);
		gc.set(Calendar.MONTH, month);
		gc.set(Calendar.DAY_OF_MONTH, 1);
		gc.add(Calendar.DAY_OF_MONTH, -1);
		
		return gc.getTime();
	}
	/**
	 * 获取当前月份的二维日历数组
	 * @return
	 */
	public static ArrayList<ArrayList<Date>> getMonthDayArray() {
		GregorianCalendar gc = new GregorianCalendar();
		int year=gc.get(Calendar.YEAR);
		int month=gc.get(Calendar.MONTH)+1;
		return getMonthDayArray(year,month);
	}
	/**
	 * 获取指定月份的二维数据组
	 * @param year
	 * @param month
	 * @return
	 */
	public static ArrayList<ArrayList<Date>> getMonthDayArray(Integer year,Integer month) {
		ArrayList<ArrayList<Date>> monthArray=new ArrayList<>();
		//设置日历的显示的地区（根据自己的需要写）
        Locale.setDefault(Locale.CHINA);

        GregorianCalendar cale = new GregorianCalendar();
        cale.setFirstDayOfWeek(Calendar.SUNDAY);
        cale.set(Calendar.YEAR, year);
        cale.set(Calendar.MONTH, month-1);//月份-1
        cale.set(Calendar.DAY_OF_MONTH, 1);
        
        String strDay=new SimpleDateFormat("yyyy-MM-dd").format(cale.getTime());
       
        //1.添加本月前到周日的时间
        //获取第一天是一周中的第几天,不是周几
        int weekday = cale.get(Calendar.DAY_OF_WEEK);
        //获取一周中的第一天
        int firstDayOfWeek = cale.getFirstDayOfWeek();
        ArrayList<Date> weekArray=new ArrayList<>();
        //将日历左移到一周的第一天
        while (weekday != firstDayOfWeek) {
            cale.add(Calendar.DAY_OF_MONTH, -1);// 日期减1
            weekday = cale.get(Calendar.DAY_OF_WEEK);
            //添加日期
            weekArray.add(0,cale.getTime());
        }
        
        //2.添加本月日期
        cale.set(Calendar.YEAR, year);
        cale.set(Calendar.MONTH, month-1);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        do{
            //打印天数
            weekArray.add(cale.getTime());
            strDay=new SimpleDateFormat("yyyy-MM-dd").format(cale.getTime());
            cale.add(Calendar.DAY_OF_MONTH, 1);// 天数加1
            weekday = cale.get(Calendar.DAY_OF_WEEK);
            //一周后
            if(weekday == firstDayOfWeek) {
            	monthArray.add(weekArray);
            	weekArray=new ArrayList<>();
            }
        }while (cale.get(Calendar.MONTH) == month-1);// 到下个月结束
        
        //3.添加本周的最后跨月的几天
        while (weekday != firstDayOfWeek) {
        	 //添加日期
            strDay=new SimpleDateFormat("yyyy-MM-dd").format(cale.getTime());
            weekArray.add(cale.getTime());
            
        	cale.add(Calendar.DAY_OF_MONTH, 1);// 日期减1
            weekday = cale.get(Calendar.DAY_OF_WEEK);
           
            
        }
        //4.加入月份记录
        monthArray.add(weekArray);
        
        return monthArray;
	}
	
	/**
     * 判断时间是否在时间段前
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
	public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
	//设置当前时间
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);
		//设置开始时间
		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);
		//设置结束时间
		Calendar end = Calendar.getInstance();
		end.setTime(endTime);
		//处于开始时间之后，和结束时间之前的判断
//		if (date.after(begin) && date.before(end)) {
		if ( date.before(end)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }
	
	

	
	 /**
	 * 获取当前时间11位时间戳(秒)
	 * 
	 * @return
	 */
	public static Integer getTimestamp() {
		return (int) (System.currentTimeMillis() / 1000);
	}

	/**
	 * 计算两个日期相差的天、时、分、秒
	 * @param startTime
	 * @param endTime
	 * @param format
	 * @return
	 */
	public static String dateDiff(String startTime, String endTime,  String format) throws Exception  {
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long diff;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		// 获得两个时间的毫秒时间差异
		try {
			if(StringUtils.isNotEmpty(format)){
				// 按照传入的格式生成一个simpledateformate对象
				SimpleDateFormat sd = new SimpleDateFormat(format);
				diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
			}else{ //传入的startTime和endTime参数为时间戳
				diff = Long.valueOf(endTime) - Long.valueOf(startTime);
			}

			day = diff / nd;// 计算差多少天
			hour = diff % nd / nh + day * 24;// 计算差多少小时
			min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
			sec = diff % nd % nh % nm / ns;// 计算差多少秒
			// 输出结果
			System.out.println("时间相差：" + day + "天" + (hour - day * 24) + "小时"
					+ (min - day * 24 * 60) + "分钟" + sec + "秒。");
			System.out.println("hour=" + hour + ",min=" + min);
			StringBuffer buffer = new StringBuffer();
			if(day > 0){
				buffer.append(day + "天");
			}
			if(hour > 0){
				buffer.append((hour - day * 24) + "时");
			}
			if(min > 0){
				buffer.append((min - day * 24 * 60) + "分");
			}
			if(sec >= 0){
				buffer.append(sec + "秒");
			}
			return buffer.toString();
		} catch (Exception e) {
			throw e;
		}
	}

	

}
