import com.coreland.framework.core.log.Logger;
import com.coreland.framework.core.log.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 * 
 * @author peter
 * @Date Jun 21, 2018 4:59:22 PM
 */
public class DateUtil {
	private final static Logger log = LoggerFactory.getLogger(DateUtil.class);

	public final static long ONE_DAY_SECONDS = 86400;
	public final static long ONE_HOUR_SECONDS = 60 * 60;
	public final static long ONE_MINUTE_SECONDS = 60;

	/*
	 * private static DateFormat dateFormat = null; private static DateFormat
	 * longDateFormat = null; private static DateFormat dateWebFormat = null;
	 */
	public final static String shortFormat = "yyyyMMdd";
	public final static String longFormat = "yyyyMMddHHmmss";
	public final static String webFormat = "yyyy-MM-dd";
	public final static String timeFormat = "HHmmss";
	public final static String monthFormat = "yyyyMM";
	public final static String chineseDtFormat = "yyyy年MM月dd日";
	public final static String newFormat = "yyyy-MM-dd HH:mm:ss";
	public final static String noSecondFormat = "yyyy-MM-dd HH:mm";
	public final static long ONE_DAY_MILL_SECONDS = 86400000;

	public static DateFormat getNewDateFormat(String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);

		df.setLenient(false);
		return df;
	}

	public static String format(Date date, String format) {
		if (date == null) {
			return "";
		}

		return new SimpleDateFormat(format).format(date);
	}

	public static Date parseDateNoTime(String sDate) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(shortFormat);

		if ((sDate == null) || (sDate.length() < shortFormat.length())) {
			throw new ParseException("length too little", 0);
		}

		if (!StringUtils.isNumeric(sDate)) {
			throw new ParseException("not all digit", 0);
		}

		return dateFormat.parse(sDate);
	}

	public static Date parseDateNoTime(String sDate, String format) throws ParseException {
		if (StringUtils.isBlank(format)) {
			throw new ParseException("Null format. ", 0);
		}

		DateFormat dateFormat = new SimpleDateFormat(format);

		if ((sDate == null) || (sDate.length() < format.length())) {
			throw new ParseException("length too little", 0);
		}

		return dateFormat.parse(sDate);
	}

	public static Date parseDateNoTimeWithDelimit(String sDate, String delimit) throws ParseException {
		sDate = sDate.replaceAll(delimit, "");

		DateFormat dateFormat = new SimpleDateFormat(shortFormat);

		if ((sDate == null) || (sDate.length() != shortFormat.length())) {
			throw new ParseException("length not match", 0);
		}

		return dateFormat.parse(sDate);
	}

	public static Date parseDateLongFormat(String sDate) {
		DateFormat dateFormat = new SimpleDateFormat(longFormat);
		Date d = null;

		if ((sDate != null) && (sDate.length() == longFormat.length())) {
			try {
				d = dateFormat.parse(sDate);
			} catch (ParseException ex) {
				return null;
			}
		}

		return d;
	}

	public static Date parseDateNewFormat(String sDate) {
		DateFormat dateFormat = new SimpleDateFormat(newFormat);
		Date d = null;
		dateFormat.setLenient(false);
		if ((sDate != null) && (sDate.length() == newFormat.length())) {
			try {
				d = dateFormat.parse(sDate);
			} catch (ParseException ex) {
				return null;
			}
		}
		return d;
	}

	/**
	 * 
	 * 
	 * @param sDate
	 * @return
	 */
	public static Date parseDateWebFormat(String sDate) {
		DateFormat dateFormat = new SimpleDateFormat(webFormat);
		Date d = null;

		if ((sDate != null) && (sDate.length() == webFormat.length())) {
			try {
				d = dateFormat.parse(sDate);
			} catch (ParseException ex) {
				return null;
			}
		}

		return d;
	}

	/**
	 * 计算当前时间几小时之后的时间
	 * 
	 * @param date
	 * @param hours
	 * 
	 * @return
	 */
	public static Date addHours(Date date, long hours) {
		return addMinutes(date, hours * 60);
	}

	/**
	 * 计算当前时间几分钟之后的时间
	 * 
	 * @param date
	 * @param minutes
	 * 
	 * @return
	 */
	public static Date addMinutes(Date date, long minutes) {
		return addSeconds(date, minutes * 60);
	}

	/**
	 * @param date1
	 * @param secs
	 * 
	 * @return
	 */

	public static Date addSeconds(Date date1, long secs) {
		return new Date(date1.getTime() + (secs * 1000));
	}

	/**
	 * 判断输入的字符串是否为合法的小时
	 * 
	 * @param hourStr
	 * 
	 * @return true/false
	 */
	public static boolean isValidHour(String hourStr) {
		if (!StringUtil.isEmpty(hourStr) && StringUtil.isNumeric(hourStr)) {
			int hour = new Integer(hourStr).intValue();

			if ((hour >= 0) && (hour <= 23)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 判断输入的字符串是否为合法的分或秒
	 * 
	 * @param str
	 * 
	 * @return true/false
	 */
	public static boolean isValidMinuteOrSecond(String str) {
		if (!StringUtil.isEmpty(str) && StringUtil.isNumeric(str)) {
			int hour = new Integer(str).intValue();

			if ((hour >= 0) && (hour <= 59)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 取得新的日期
	 * 
	 * @param date1 日期
	 * @param days  天数
	 * 
	 * @return 新的日期
	 */
	public static Date addDays(Date date1, long days) {
		return addSeconds(date1, days * ONE_DAY_SECONDS);
	}

	public static String getTomorrowDateString(String sDate) throws ParseException {
		Date aDate = parseDateNoTime(sDate);

		aDate = addSeconds(aDate, ONE_DAY_SECONDS);

		return getDateString(aDate);
	}

	public static String getLongDateString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(longFormat);

		return getDateString(date, dateFormat);
	}

	public static String getNewFormatDateString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(newFormat);
		return getDateString(date, dateFormat);
	}

	public static String getDateString(Date date, DateFormat dateFormat) {
		if (date == null || dateFormat == null) {
			return null;
		}

		return dateFormat.format(date);
	}

	public static String getYesterDayDateString(String sDate) throws ParseException {
		Date aDate = parseDateNoTime(sDate);

		aDate = addSeconds(aDate, -ONE_DAY_SECONDS);

		return getDateString(aDate);
	}

	/**
	 * @return 当天的时间格式化为"yyyyMMdd"
	 */
	public static String getDateString(Date date) {
		DateFormat df = getNewDateFormat(shortFormat);

		return df.format(date);
	}

	public static String getWebDateString(Date date) {
		DateFormat dateFormat = getNewDateFormat(webFormat);

		return getDateString(date, dateFormat);
	}

	/**
	 * 取得“X年X月X日”的日期格式
	 * 
	 * @param date
	 * 
	 * @return
	 */
	public static String getChineseDateString(Date date) {
		DateFormat dateFormat = getNewDateFormat(chineseDtFormat);

		return getDateString(date, dateFormat);
	}

	public static String getTodayString() {
		DateFormat dateFormat = getNewDateFormat(shortFormat);

		return getDateString(new Date(), dateFormat);
	}

	public static String getTodayWebString() {

		DateFormat dateFormat = getNewDateFormat(webFormat);

		return getDateString(new Date(), dateFormat);
	}

	public static Date getTodayWebDate() throws ParseException {

		return parseDateNoTime(getWebTodayString(), webFormat);
	}

	public static String getTimeString(Date date) {
		DateFormat dateFormat = getNewDateFormat(timeFormat);

		return getDateString(date, dateFormat);
	}

	public static String getBeforeDayString(int days) {
		Date date = new Date(System.currentTimeMillis() - (ONE_DAY_MILL_SECONDS * days));
		DateFormat dateFormat = getNewDateFormat(shortFormat);

		return getDateString(date, dateFormat);
	}

	public static String getBeforeDayString(int days, String format) {
		Date date = new Date(System.currentTimeMillis() - (ONE_DAY_MILL_SECONDS * days));
		DateFormat dateFormat = getNewDateFormat(format);

		return getDateString(date, dateFormat);
	}

	/**
	 * 取得两个日期间隔秒数（结束日期-开始日期）
	 * 
	 * @param startDate 开始日期
	 * @param endDate   结束日期
	 * 
	 * @return 间隔秒数
	 */
	public static long getDiffMillisecond(Date startDate, Date endDate) {
		Calendar sysDate = new GregorianCalendar();

		sysDate.setTime(endDate);

		Calendar failDate = new GregorianCalendar();

		failDate.setTime(startDate);
		return (sysDate.getTimeInMillis() - failDate.getTimeInMillis());
	}

	/**
	 * 取得两个日期间隔秒数（日期1-日期2）
	 * 
	 * @param one 日期1
	 * @param two 日期2
	 * 
	 * @return 间隔秒数
	 */
	public static long getDiffSeconds(Date one, Date two) {
		Calendar sysDate = new GregorianCalendar();

		sysDate.setTime(one);

		Calendar failDate = new GregorianCalendar();

		failDate.setTime(two);
		return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / 1000;
	}

	/**
	 * 取得两个时间间隔的分钟数
	 * 
	 * @param one 大的时间
	 * @param two 小的时间
	 * @return
	 */
	public static long getDiffMinutes(Date one, Date two) {
		Calendar sysDate = new GregorianCalendar();

		sysDate.setTime(one);

		Calendar failDate = new GregorianCalendar();

		failDate.setTime(two);
		return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / (60 * 1000);
	}

	/**
	 * 取得两个日期的间隔天数
	 * 
	 * @param one
	 * @param two
	 * 
	 * @return 间隔天数
	 */
	public static long getDiffDays(Date one, Date two) {
		Calendar sysDate = new GregorianCalendar();

		sysDate.setTime(one);

		Calendar failDate = new GregorianCalendar();

		failDate.setTime(two);
		return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / (24 * 60 * 60 * 1000);
	}

	/**
	 * 计算两个日期之前的时间差值
	 * 
	 * @param one 开始时间
	 * @param two 结束时间
	 * @return d:hh:mm:ss
	 */
	public static String getDiffTime(Date one, Date two) {
		String time = "";
		long totalSeconds = getDiffSeconds(one, two);

		long day = totalSeconds / ONE_DAY_SECONDS;
		totalSeconds = totalSeconds - day * ONE_DAY_SECONDS;

		long hours = totalSeconds / ONE_HOUR_SECONDS;
		totalSeconds = totalSeconds - hours * ONE_HOUR_SECONDS;

		long minutes = totalSeconds / ONE_MINUTE_SECONDS;
		totalSeconds = totalSeconds - minutes * ONE_MINUTE_SECONDS;

		time += totalSeconds < 0 ? "（超时）" : "";
		time += day > 0 ? String.valueOf(Math.abs(day)) + "天" : "";
		time += String.valueOf(Math.abs(hours)) + "时" + String.valueOf(Math.abs(minutes)) + "分";

		return time;
	}

	public static String getBeforeDayString(String dateString, int days) {
		Date date;
		DateFormat df = getNewDateFormat(shortFormat);

		try {
			date = df.parse(dateString);
		} catch (ParseException e) {
			date = new Date();
		}

		date = new Date(date.getTime() - (ONE_DAY_MILL_SECONDS * days));

		return df.format(date);
	}

	/**
	 * 是否为有效的短日期格式
	 * 
	 * @param strDate 指定的日期(yyyyMMdd)
	 * @return
	 */
	public static boolean isValidShortDateFormat(String strDate) {
		if (strDate.length() != shortFormat.length()) {
			return false;
		}

		try {
			Integer.parseInt(strDate); // ---- 避免日期中输入非数字 ----
		} catch (Exception NumberFormatException) {
			return false;
		}

		DateFormat df = getNewDateFormat(shortFormat);

		try {
			df.parse(strDate);
		} catch (ParseException e) {
			return false;
		}

		return true;
	}

	/**
	 * 是否为有效的短日期格式
	 * 
	 * @param strDate   指定的日期格式
	 * @param delimiter 去掉的多余字符
	 * @return
	 */
	public static boolean isValidShortDateFormat(String strDate, String delimiter) {
		String temp = strDate.replaceAll(delimiter, "");

		return isValidShortDateFormat(temp);
	}

	/**
	 * 判断表示时间的字符是否为符合yyyyMMddHHmmss格式
	 * 
	 * @param strDate
	 * @return
	 */
	public static boolean isValidLongDateFormat(String strDate) {
		if (strDate.length() != longFormat.length()) {
			return false;
		}

		try {
			Long.parseLong(strDate); // ---- 避免日期中输入非数字 ----
		} catch (Exception NumberFormatException) {
			return false;
		}

		DateFormat df = getNewDateFormat(longFormat);

		try {
			df.parse(strDate);
		} catch (ParseException e) {
			return false;
		}

		return true;
	}

	/**
	 * 判断表示时间的字符是否为符合yyyyMMddHHmmss格式
	 * 
	 * @param strDate
	 * @param delimiter
	 * @return
	 */
	public static boolean isValidLongDateFormat(String strDate, String delimiter) {
		String temp = strDate.replaceAll(delimiter, "");

		return isValidLongDateFormat(temp);
	}

	public static String getShortDateString(String strDate) {
		return getShortDateString(strDate, "-|/");
	}

	public static String getShortDateString(String strDate, String delimiter) {
		if (StringUtil.isBlank(strDate)) {
			return null;
		}

		String temp = strDate.replaceAll(delimiter, "");

		if (isValidShortDateFormat(temp)) {
			return temp;
		}

		return null;
	}

	/**
	 * 获取当前时间本月第一天日期，格式为: yyyyMMdd ∗ @return
	 */
	public static String getShortFirstDayOfMonth() {
		return time(shortFormat);
	}

	/**
	 * 获取当前时间的年月日 ∗ @return
	 */
	public static String getWebTodayString() {
		DateFormat df = getNewDateFormat(webFormat);

		return df.format(new Date());
	}

	/**
	 * 获取当前时间本月第一天日期，格式为: yyyy-MM-dd ∗ @return
	 */
	public static String getWebFirstDayOfMonth() {
		return time(webFormat);
	}

	private static String time(String format) {
		Calendar cal = Calendar.getInstance();
		Date dt = new Date();

		cal.setTime(dt);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		DateFormat df = getNewDateFormat(format);
		System.out.println(cal.getTime());
		return df.format(cal.getTime());
	}

	public static String convert(String dateString, DateFormat formatIn, DateFormat formatOut) {
		try {
			Date date = formatIn.parse(dateString);

			return formatOut.format(date);
		} catch (ParseException e) {
			log.warn("convert() --- orign date error: " + dateString);
			return "";
		}
	}

	public static String convert2WebFormat(String dateString) {
		DateFormat df1 = getNewDateFormat(shortFormat);
		DateFormat df2 = getNewDateFormat(webFormat);

		return convert(dateString, df1, df2);
	}

	public static String convert2ChineseDtFormat(String dateString) {
		DateFormat df1 = getNewDateFormat(shortFormat);
		DateFormat df2 = getNewDateFormat(chineseDtFormat);

		return convert(dateString, df1, df2);
	}

	public static String convertFromWebFormat(String dateString) {
		DateFormat df1 = getNewDateFormat(shortFormat);
		DateFormat df2 = getNewDateFormat(webFormat);

		return convert(dateString, df2, df1);
	}

	public static boolean webDateNotLessThan(String date1, String date2) {
		DateFormat df = getNewDateFormat(webFormat);

		return dateNotLessThan(date1, date2, df);
	}

	/**
	 * 判断字符串时间的先后
	 * 
	 * @param date1
	 * @param date2
	 * @param format 格式化
	 * 
	 * @return
	 */
	public static boolean dateNotLessThan(String date1, String date2, DateFormat format) {
		try {
			Date d1 = format.parse(date1);
			Date d2 = format.parse(date2);

			if (d1.before(d2)) {
				return false;
			} else {
				return true;
			}
		} catch (ParseException e) {
			log.debug("dateNotLessThan() --- ParseException(" + date1 + ", " + date2 + ")");
			return false;
		}
	}

	/**
	 * 格式化时间，格式为：yyyy年MM月dd日HH:mm:ss
	 * 
	 * @param today ∗ @return
	 */
	public static String getEmailDate(Date today) {
		String todayStr;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");

		todayStr = sdf.format(today);
		return todayStr;
	}

	/**
	 * 格式化时间，格式为：MM月dd日HH:mm
	 * 
	 * @param today ∗ @return
	 */
	public static String getSmsDate(Date today) {
		String todayStr;
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日HH:mm");

		todayStr = sdf.format(today);
		return todayStr;
	}

	public static String formatTimeRange(Date startDate, Date endDate, String format) {
		if ((endDate == null) || (startDate == null)) {
			return null;
		}

		String rt = null;
		long range = endDate.getTime() - startDate.getTime();
		long day = range / DateUtils.MILLIS_PER_DAY;
		long hour = (range % DateUtils.MILLIS_PER_DAY) / DateUtils.MILLIS_PER_HOUR;
		long minute = (range % DateUtils.MILLIS_PER_HOUR) / DateUtils.MILLIS_PER_MINUTE;

		if (range < 0) {
			day = 0;
			hour = 0;
			minute = 0;
		}

		rt = format.replaceAll("dd", String.valueOf(day));
		rt = rt.replaceAll("hh", String.valueOf(hour));
		rt = rt.replaceAll("mm", String.valueOf(minute));

		return rt;
	}

	/**
	 * 格式化时间，格式为：yyyyMM
	 * 
	 * @param date 日期 ∗ @return
	 */
	public static String formatMonth(Date date) {
		if (date == null) {
			return null;
		}

		return new SimpleDateFormat(monthFormat).format(date);
	}

	/**
	 * 获取系统日期的前一天日期，返回Date
	 * 
	 * @return
	 */
	public static Date getBeforeDate() {
		Date date = new Date();

		return new Date(date.getTime() - (ONE_DAY_MILL_SECONDS));
	}

	/**
	 * 获得指定时间当天起点时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayBegin(Date date) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		df.setLenient(false);

		String dateString = df.format(date);

		try {
			return df.parse(dateString);
		} catch (ParseException e) {
			return date;
		}
	}

	/**
	 * 判断参date上min分钟后，是否小于当前时间，小于返回true，不小于返回false
	 * 
	 * @param date
	 * @param min
	 * @return
	 */
	public static boolean dateLessThanNowAddMin(Date date, long min) {
		return addMinutes(date, min).before(new Date());

	}

	/**
	 * 是否在当前时间以前
	 * 
	 * @param date 指定日期 ∗ @return true/false
	 */
	public static boolean isBeforeNow(Date date) {
		if (date == null)
			return false;
		return date.compareTo(new Date()) < 0;
	}

	public static void main(String[] args) {
		System.out.println(isBeforeNow(new Date()));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			System.out.println(formatTimeRange(df.parse("2019-04-02 14:33:34"), new Date(), "yyyy-MM-dd HH:mm:ss"));
			System.out.println(new SimpleDateFormat("yyyy-MM-dd").parseObject("2019-11-26"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(getWebFirstDayOfMonth());
		System.out.println(getShortFirstDayOfMonth());
	}

	/**
	 * 格式化为 yyyy-MM-dd HH:mm
	 * 
	 * @param date 日期 ∗ @return 格式化后的字符串
	 */
	public static String getNoSecondFormat(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(noSecondFormat);

		return dateFormat.format(date);
	}

	/**
	 * 字符串转为日期， 字符串格式为 yyyy-MM-dd HH:mm
	 * 
	 * @param sDate 日期 ∗ @return 日期
	 */
	public static Date parseNoSecondFormat(String sDate) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(noSecondFormat);

		if ((sDate == null) || (sDate.length() < noSecondFormat.length())) {
			throw new ParseException("length too little", 0);
		}

		if (!StringUtils.isNumeric(sDate)) {
			throw new ParseException("not all digit", 0);
		}

		return dateFormat.parse(sDate);
	}

	/**
	 * 计算快捷查询时间周期的开始、结束日期
	 * 
	 * @param quick 快捷查询时间类型 today:今天 yesterday:昨天 week:最近一周 month:最近一月
	 *              threeMonthes:最近三月 halfYear:最近半年 year:最近一年
	 * 
	 * @return quickDateMap.put("startDate", startDate); quickDateMap.put("endDate",
	 *         endDate);
	 */
	public static Map<String, String> parseQuickDate(String quick) {
		Map<String, String> quickDateMap = new HashMap<String, String>();
		String startDate = null;
		String endDate = null;

		/**
		 * 如果快捷查询不为null，则计算相应的时间段
		 */
		if (quick != null) {
			if ("today".equals(quick)) {
				// 今天
				startDate = DateUtil.getWebTodayString();
				endDate = startDate;

			} else if ("yesterday".equals(quick)) {
				// 昨天
				int yesterday = 1;

				startDate = DateUtil.convert2WebFormat(DateUtil.getBeforeDayString(yesterday));
				endDate = startDate;

			} else if ("week".equals(quick)) {
				// 最近一周
				int daysOfWeek = 6;

				startDate = DateUtil.convert2WebFormat(DateUtil.getBeforeDayString(daysOfWeek));
				endDate = DateUtil.getWebTodayString();
			} else if ("month".equals(quick)) {
				// 最近一月
				int daysOfMonth = 31;

				startDate = DateUtil.convert2WebFormat(DateUtil.getBeforeDayString(daysOfMonth));
				endDate = DateUtil.getWebTodayString();
			} else if ("threeMonthes".equals(quick)) {
				// 最近三月
				int daysOfThreeMonth = 90;

				startDate = DateUtil.convert2WebFormat(DateUtil.getBeforeDayString(daysOfThreeMonth));
				endDate = DateUtil.getWebTodayString();
			} else if ("halfYear".equals(quick)) {
				// 最近半年
				int daysOfHalfYear = 183;

				startDate = DateUtil.convert2WebFormat(DateUtil.getBeforeDayString(daysOfHalfYear));
				endDate = DateUtil.getWebTodayString();
			} else if ("year".equals(quick)) {
				// 最近一年
				int daysOfYear = 365;

				startDate = DateUtil.convert2WebFormat(DateUtil.getBeforeDayString(daysOfYear));
				endDate = DateUtil.getWebTodayString();
			}
		}

		quickDateMap.put("startDate", startDate);
		quickDateMap.put("endDate", endDate);

		return quickDateMap;

	}

	/**
	 * 月份倒推
	 * 
	 * @param date   当前日期
	 * @param length 可为正负数
	 * @return
	 */
	public static Date getNewMonth(Date date, int length) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, length);
		return calendar.getTime();
	}

	/**
	 * 年份倒推
	 * 
	 * @param date   当前日期
	 * @param length 可为正负数
	 * @return
	 */
	public static Date getNewYear(Date date, int length) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, length);
		return calendar.getTime();
	}

	/**
	 * 获取当前时间的毫秒数
	 * 
	 * @return
	 */
	public static long getMillisecondForNow() {
		return new Date().getTime();
	}

	/**
	 * 取得当月最后一天
	 * 
	 * @return
	 */
	public static int getLastDayOfMonth() {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 根据身份证号计算年龄
	 * 
	 * @param identityCard 身份证号
	 * @return
	 */
	public static int calculateAge(String identityCard) {
		if (identityCard == null || "".equals(identityCard) || identityCard.length() < 18) {
			return 0;
		}

		String year = identityCard.substring(6, 10);
		int intYear = Integer.parseInt(year);
		int currentYear = Integer.parseInt(format(new Date(), "yyyy"));

		if (intYear > currentYear) {
			return 0;
		}

		return currentYear - intYear;
	}

	/**
	 * @Description: 是否为周末
	 * @param date
	 * @param      @return 参数说明
	 * @return boolean 返回类型
	 */
	public static boolean isWeekend(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
	}

}
