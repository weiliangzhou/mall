package com.zwl.util;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间的操作类
 */
public class DateUtil {

    public static final long OneDayMils = 24 * 60 * 60 * 1000;

    public static String date_format_yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
    public static String date_format_yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static String date_format_yyyyMMddhhmmss = "yyyyMMddhhmmss";
    public static String date_format_yyMMddHHmmss = "yyMMddHHmmss";
    public static String date_format_yyyy_MM_dd = "yyyy-MM-dd";
    public static String date_format_yyyyMMdd = "yyyyMMdd";
    public static String date_format_HHmm = "HH:mm";
    public static String data_format_yyyy_MM_dd_hh_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static String date_format_MM_slash_dd = "MM/dd";
    public static String date_format_yyyy_slash_MM = "yyyy/MM";
    public static String date_format_yyyy_point_MM_point_dd = "yyyy.MM.dd";

    static Map<String, SimpleDateFormat> formatMap = new HashMap<String, SimpleDateFormat>();
    static List<SimpleDateFormat> formatList = new ArrayList<SimpleDateFormat>();

    static {

        SimpleDateFormat yyyyMMddHHmmssSSSSdf = new SimpleDateFormat(date_format_yyyyMMddHHmmssSSS);
        SimpleDateFormat yyyyMMddHHmmssSdf = new SimpleDateFormat(date_format_yyyyMMddHHmmss);
        SimpleDateFormat yyMMddHHmmssSdf = new SimpleDateFormat(date_format_yyMMddHHmmss);
        SimpleDateFormat yyyyMMddhhmmssSdf = new SimpleDateFormat(date_format_yyyyMMddhhmmss);
        SimpleDateFormat yyyy_MM_ddSdf = new SimpleDateFormat(date_format_yyyy_MM_dd);
        SimpleDateFormat yyyyMMddSdf = new SimpleDateFormat(date_format_yyyyMMdd);
        SimpleDateFormat HHmmssSdf = new SimpleDateFormat(date_format_HHmm);
        SimpleDateFormat yyyy_MM_dd_hh_mm_ss = new SimpleDateFormat(data_format_yyyy_MM_dd_hh_mm_ss);
        SimpleDateFormat MM_slash_dd = new SimpleDateFormat(date_format_MM_slash_dd);
        SimpleDateFormat yyyy_slash_MM = new SimpleDateFormat(date_format_yyyy_slash_MM);
        SimpleDateFormat yyyy_point_MM_point_dd = new SimpleDateFormat(date_format_yyyy_point_MM_point_dd);


        formatMap.put(date_format_yyyyMMddHHmmssSSS, yyyyMMddHHmmssSSSSdf);
        formatMap.put(date_format_yyyyMMddHHmmss, yyyyMMddHHmmssSdf);
        formatMap.put(date_format_yyyyMMddhhmmss, yyyyMMddhhmmssSdf);
        formatMap.put(date_format_yyMMddHHmmss, yyMMddHHmmssSdf);
        formatMap.put(date_format_yyyy_MM_dd, yyyy_MM_ddSdf);
        formatMap.put(date_format_yyyyMMdd, yyyyMMddSdf);
        formatMap.put(data_format_yyyy_MM_dd_hh_mm_ss, yyyy_MM_dd_hh_mm_ss);
        formatMap.put(date_format_HHmm, HHmmssSdf);
        formatMap.put(date_format_MM_slash_dd, MM_slash_dd);
        formatMap.put(date_format_yyyy_slash_MM, yyyy_slash_MM);
        formatMap.put(date_format_yyyy_point_MM_point_dd, yyyy_point_MM_point_dd);

        formatList.add(yyyy_MM_dd_hh_mm_ss);
        formatList.add(yyyyMMddHHmmssSdf);
        formatList.add(yyyy_MM_ddSdf);
    }

    /**
     * 根据字符串获取时间 <br/>
     * 会根据系统支持的格式一一判断，如果解析成功则返回
     *
     * @param dateStr
     * @return
     */
    public static Date parseDateString(String dateStr) {
        if (CheckUtil.isEmpty(dateStr)) {
            return null;
        }
        try {
            for (SimpleDateFormat sdf : formatList) {
                try {
                    sdf.setLenient(false);
                    return sdf.parse(dateStr);
                } catch (Exception ex) {
                    //do nothing
                }
            }
        } catch (Exception ex) {
            return null;
        }
        return null;
    }

    /**
     * 获取固定格式的时间串
     *
     * @param format 格式
     * @param date   时间
     * @return 如果格式是不支持的格式或者参数有误，那么返回“”，而不是null
     */
    public static String getFormatString(String format, Date date) {
        if (CheckUtil.isEmpty(format)) {
            return "";
        }
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = formatMap.get(format);
        if (sdf == null) {
            return "";
        }
        return sdf.format(date);
    }

    /**
     * 这里做了个小处理，如果时分秒均为0，那么就不显示
     *
     * @param format
     * @param date
     * @return
     */
    public static String getFormatStringRemoveZero(String format, Date date) {
        if (CheckUtil.isEmpty(format)) {
            return "";
        }
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = formatMap.get(format);
        if (sdf == null) {
            return "";
        }
        String dateStr = sdf.format(date);
        if (format.equals("yyyy-MM-dd HH:mm:ss")) {
            return dateStr.replaceAll(" 00:00:00", "");
        }
        if (format.equals("yyyy-MM-dd HH:mm")) {
            return dateStr.replaceAll(" 00:00", "");
        }
        if (format.equals("yyyyMMddHHmmss")) {
            return dateStr.replaceAll("000000$", "");
        }
        if (format.equals("yyyy年M月d日 HH:mm")) {
            return dateStr.replaceAll(" 00:00$", "");
        }

        return dateStr;
    }

    /**
     * 这里如果时分秒都没有，那么会自动补全23：59：59
     *
     * @param format
     * @param date
     * @return
     */
    public static String getFormatStringAndRoof(String format, Date date) {
        if (CheckUtil.isEmpty(format)) {
            return "";
        }
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = formatMap.get(format);
        if (sdf == null) {
            return "";
        }
        String dateStr = sdf.format(date);
        if (format.equals("yyyy-MM-dd HH:mm:ss")) {
            return dateStr.replaceAll(" 00:00:00", " 23:59:59");
        }
        if (format.equals("yyyyMMddHHmmss")) {
            return dateStr.replaceAll("000000$", "235959");
        }
        return dateStr;
    }

    /**
     * 去掉date的时分秒属性
     *
     * @param date
     * @return 去掉时分秒的时间
     */
    public static Date floor(Date date) {
        if (date == null) {
            return null;
        }
        return new Date(date.getTime() - DateUtil.getMargin(date));
    }

    /**
     * 获取mils（毫秒数）对应的那天午夜的时间
     *
     * @param mils
     * @return
     */
    public static long floor(long mils) {
        return mils - DateUtil.getMargin(mils);
    }


    /**
     * 把一个时间的时分秒属性设置为23:59:59
     *
     * @param date
     * @return 设置时分秒之后的时间
     */
    public static Date roof(Date date) {
        if (date == null) {
            return null;
        }
        return new Date(date.getTime() - DateUtil.getMargin(date) + OneDayMils - 1);
    }

    /**
     * 取得系统当前日期
     */
    public static Date getCurrentTime() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 获取格式化过后的系统当前时间
     *
     * @return
     */
    public static Date getFormatCurrentTime() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取时间毫秒数
     *
     * @return
     */
    public static Long getTimeMillis(Date date) {
        if (null == date) {
            return 0L;
        }
        return date.getTime();
    }

    /**
     * 获取当前时间的毫秒数
     *
     * @return
     */
    public static Long getCurrentTimeMillis() {
        return getTimeMillis(getCurrentTime());
    }

    /**
     * 获取日期距离零点的毫秒数:参数time类型
     *
     * @param time
     * @return
     */
    public static long getMarginForTime(Time time) {
        if (time != null) {
            return getMargin(time.getTime());
        }
        return 0;
    }

    /**
     * 获取日期距离零点的毫秒数
     *
     * @param date
     * @return
     */
    public static long getMargin(Date date) {
        return getMargin(date.getTime());
    }

    /**
     * 获取日期距离零点的毫秒数
     *
     * @param timeInMils 毫秒数
     * @return
     */
    public static long getMargin(long timeInMils) {
        return (timeInMils + 8 * 60 * 60 * 1000) % (OneDayMils);//这里要加8小时，因为时区问题
    }

    /**
     * 获取距离明天的秒数
     *
     * @return
     */
    public static int getTomorrowMarginSeconds() {
        Date nowDate = DateUtil.getCurrentTime();
        long nowTimeMils = nowDate.getTime();
        long tomorrowTimeMils = DateUtil.floor(DateUtil.addDate(nowDate, 1)).getTime();
        int seconds = (int) (tomorrowTimeMils - nowTimeMils) / 1000;
        return seconds;
    }

    /**
     * 日期增加或者减少多少天
     *
     * @param date  日期
     * @param month 月份增量，当month大于0时，为日期增加month个月，当month小于0时，为日期减少month月
     * @return
     */
    public static Date addMonth(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + month);
        return calendar.getTime();
    }

    /**
     * 日期增加或者减少多少天
     *
     * @param date 日期
     * @param day  日期增量，当day大于0时，为日期增加day天，当day小于0时，为日期减少day天
     * @return
     */
    public static Date addDate(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + day);
        return calendar.getTime();
    }

    /**
     * 时间增加或者减少多少小时
     *
     * @param date 日期
     * @param hour 小时增量，当hour大于0时，为时间增加hour小时，当hour小于0时，为日期减少hour小时
     * @return
     */
    public static Date addHour(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + hour);
        return calendar.getTime();
    }


    /**
     * 获取本周第一天0点时间点
     *
     * @return
     */
    public static Date getThisWeekBeginTimes() {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取本月第一天0点时间点
     *
     * @return
     */
    public static Date getThisMonthBeginTimes() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取当前月份前几个月的开始时间
     *
     * @param month 当month > 0 向后推移, 当month < 0 向前推移
     * @return
     */
    public static Date getMonthBeginTimes(int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.MONTH, month);
        return cal.getTime();
    }

    /**
     * 根据传入的时间， 返回时间是在当前时间的什么时候
     *
     * @param date
     * @return 5天前，1小时前，2天后
     */
    public static String getTextByDate(Date date) {
        if (date == null) {
            return null;
        }

        long curTime = System.currentTimeMillis();
        long dateTime = date.getTime();

        if (dateTime > curTime) {
            if ((dateTime - curTime) <= 60 * 1000) {
                return "马上";
            }
            long minutes = (dateTime - curTime) / (60 * 1000);
            if (minutes < 60) {//一小时以内
                return minutes + "分钟后";
            }
            long margin = 24 * 60 * 60 * 1000 - getMargin(curTime);//当前距离当天结束还有多少毫秒
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            if ((dateTime - curTime) <= margin) {//是否是在当天
                if (calendar.get(Calendar.AM_PM) == Calendar.AM) {//早上
                    return "早上" + calendar.get(Calendar.HOUR_OF_DAY) + "点";
                } else {
                    int hour_of_day = calendar.get(Calendar.HOUR_OF_DAY);
                    if (hour_of_day == 12) {
                        return "中午12点";
                    } else if (hour_of_day >= 18) {
                        return "晚上" + (hour_of_day - 12) + "点";
                    } else {
                        return "下午" + (hour_of_day - 12) + "点";
                    }
                }
            }
            //是否是昨天
            if ((dateTime - curTime) <= (margin + OneDayMils)) {
                return "明天";
            }
            //是否是前天
            if ((dateTime - curTime) <= (margin + 2 * OneDayMils)) {
                return "后天";
            }
            //显示几月几号
            return (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DATE) + "日";
        } else {
            if ((curTime - dateTime) <= 60 * 1000) {
                return "刚刚";
            }
            long minutes = (curTime - dateTime) / (60 * 1000);
            if (minutes < 60) {//一小时以内
                return minutes + "分钟前";
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            if ((curTime - dateTime) <= getMargin(curTime)) {//是否是在当天
                if (calendar.get(Calendar.AM_PM) == Calendar.AM) {//早上
                    return "早上" + calendar.get(Calendar.HOUR_OF_DAY) + "点";
                } else {
                    int hour_of_day = calendar.get(Calendar.HOUR_OF_DAY);
                    if (hour_of_day == 12) {
                        return "中午12点";
                    } else if (hour_of_day >= 18) {
                        return "晚上" + (hour_of_day - 12) + "点";
                    } else {
                        return "下午" + (hour_of_day - 12) + "点";
                    }
                }
            }
            //是否是昨天
            if ((curTime - dateTime) <= (getMargin(curTime) + OneDayMils)) {
                return "昨天";
            }
            //是否是前天
            if ((curTime - dateTime) <= (getMargin(curTime) + OneDayMils)) {
                return "前天";
            }
            //显示几月几号
            return (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DATE) + "日";

        }
    }

    /**
     * 根据传入的时间，返回今天，明天，昨天或者""
     *
     * @param date
     * @return
     */
    public static String getDateText(Date date) {
        if (date == null) {
            return null;
        }
        Date today = new Date();
        long interval = getDateInterval(today, date);
        if (interval == 0) {
            return "今天";
        } else if (interval == 1) {
            return "明天";
        } else if (interval == -1) {
            return "昨天";
        } else {
            return null;
        }

    }

    /**
     * 获取endDate和startDate之间的天数差
     *
     * @return 两者同一天，返回0， endDate早于startDate，返回负值
     */
    public static long getDateInterval(Date startDate, Date endDate) {
        long startTime = startDate.getTime() - DateUtil.getMargin(startDate.getTime());
        long endTime = endDate.getTime() - DateUtil.getMargin(endDate.getTime());

        if (endTime >= startTime) {
            return (endTime - startTime) / (OneDayMils);
        } else {
            return 0 - (startTime - endTime) / (OneDayMils);
        }
    }

    /**
     * 获取中文展示格式
     *
     * @param date
     * @return
     */
    public static String getChineseFormat(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        StringBuilder sb = new StringBuilder();
        sb.append(cal.get(Calendar.YEAR)).append("年")
                .append(cal.get(Calendar.MONTH) + 1).append("月")
                .append(cal.get(Calendar.DATE)).append("号");
        sb.append(cal.get(Calendar.HOUR_OF_DAY)).append("点");
        if (cal.get(Calendar.MINUTE) != 0) {
            sb.append(cal.get(Calendar.MINUTE)).append("分");
            if (cal.get(Calendar.SECOND) != 0) {
                sb.append(cal.get(Calendar.SECOND)).append("秒");
            }
        }
        return sb.toString();
    }


    /**
     * 比较两个日期是否相等
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isDateEqual(Date date1, Date date2) {
        if (date1 == null && date2 == null) return true;
        if (date1 != null && date2 == null) return false;
        if (date1 == null && date2 != null) return false;
        return date1.getTime() == date2.getTime();
    }


    /**
     * 获取日期是星期几, 周日为1，周一为2，周六为7, 参照Calendar.DAY_OF_WEEK
     *
     * @param date
     * @return
     */
    public static int getCalendarWeekdayNum(Date date) {
        if (date == null) {
            return -1;
        }
        long timeMargin = date.getTime() - DateUtil.getMargin(date) - 1463241600000L;//这个数字是2015.5.18号， 周日

        if (timeMargin >= 0) {
            long margin = (timeMargin / OneDayMils) % 7;
            return (int) margin + 1;
        } else {
            long margin = (Math.abs(timeMargin) / OneDayMils) % 7;
            if (margin == 0) margin = 7;
            return (int) (Calendar.SUNDAY + 7 - margin);
        }
    }

    /**
     * 获取我们中国人熟悉的周几， 周一是1， 周二是2
     *
     * @param date
     * @return
     */
    public static int getChineseWeekdayNum(Date date) {
        int weekdayNum = getCalendarWeekdayNum(date) - 1;
        if (weekdayNum == 0) {
            return 7;
        }
        return weekdayNum;
    }


    /**
     * 返回：星期一，星期二这种字串
     *
     * @param date
     * @return
     */
    public static String getWeekStr(Date date) {
        if (date == null) return null;
        int weekdayNum = getCalendarWeekdayNum(date);
        switch (weekdayNum) {
            case Calendar.MONDAY:
                return "周一";
            case Calendar.TUESDAY:
                return "周二";
            case Calendar.WEDNESDAY:
                return "周三";
            case Calendar.THURSDAY:
                return "周四";
            case Calendar.FRIDAY:
                return "周五";
            case Calendar.SATURDAY:
                return "周六";
            case Calendar.SUNDAY:
                return "周日";
            default:
                return null;
        }
    }

    /**
     * 返回中文格式的月份
     *
     * @param month 格式为201511
     * @return 十一月 2015
     */
    public static String getChineseMonth(String month) {
        if (month == null || month.length() != 6) {
            return "";
        }
        String monthNum = month.substring(4, 6);
        int monthNumInt = Integer.parseInt(monthNum);

        StringBuilder sb = new StringBuilder();
        switch (monthNumInt) {
            case 1:
                sb.append("一月");
                break;
            case 2:
                sb.append("二月");
                break;
            case 3:
                sb.append("三月");
                break;
            case 4:
                sb.append("四月");
                break;
            case 5:
                sb.append("五月");
                break;
            case 6:
                sb.append("六月");
                break;
            case 7:
                sb.append("七月");
                break;
            case 8:
                sb.append("八月");
                break;
            case 9:
                sb.append("九月");
                break;
            case 10:
                sb.append("十月");
                break;
            case 11:
                sb.append("十一月");
                break;
            case 12:
                sb.append("十二月");
                break;
            default:
                break;
        }
        sb.append(" ").append(month.substring(0, 4));
        return sb.toString();
    }


    /**
     * 把日期转成秒
     *
     * @param date
     * @return
     */
    public static long getSeconds(Date date) {
        if (date == null) {
            return -1;
        }
        return date.getTime() / 1000;
    }

    /**
     * 把毫秒转成秒
     *
     * @param milSecond
     * @return
     */
    public static long getSecondsByMil(long milSecond) {

        return milSecond / 1000;
    }

    /**
     * 设置时间为 当天的 0时0分0秒
     *
     * @param date 要设置的时间
     * @return 返回 设置的时间初始化 时分秒为当天的开始时间
     */
    public static Date getDateDayStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 设置时间为 当天的 23时59分59秒
     *
     * @param date 要设置的时间
     * @return 返回 设置的时间初始化 为当天的结束时间
     */
    public static Date getDateDayEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }
    public static void main(String[] args) {

//        System.out.println(1463328000000L - OneDayMils);
//        long now = Long.valueOf("1463328000000");
//        System.out.println(DateUtil.getWeekStr(new Date(now)));
//        System.out.println(now - DateUtil.getMargin(now) - 3 * 24 * 60 * 60 * 1000);

//        Date date=new Date();
//        Date beginDate = DateUtil.floor(date);//找到今天凌晨
//        System.out.println(beginDate.getTime() - 24 * 60 * 60 * 1000L);
//
//		System.out.println(DateUtil.getFormatString(DateUtil.date_format_yyyyMMddHHmmss, getThisWeekBeginTimes()));
//		System.out.println(DateUtil.getFormatString(DateUtil.date_format_yyyyMMddHHmmss, getThisMonthBeginTimes()));
//		System.out.println(DateUtil.getFormatString(DateUtil.date_format_yyyyMMddHHmmss, getLastMonthBeginTimes()));

        System.out.println("本周:" + DateUtil.getFormatString(DateUtil.date_format_yyyyMMddHHmmss, getThisWeekBeginTimes()));
        System.out.println("本月:" + DateUtil.getFormatString(DateUtil.date_format_yyyyMMddHHmmss, getThisMonthBeginTimes()));
        System.out.println("上月:" + DateUtil.getFormatString(DateUtil.date_format_yyyyMMddHHmmss, DateUtil.getMonthBeginTimes(-7)));

        Date nowTime = DateUtil.getCurrentTime();
        System.out.println(nowTime);
        System.out.println(nowTime.getTime());
        System.out.println(getFormatCurrentTime().getTime());

        System.out.println(DateUtil.getFormatString(DateUtil.date_format_yyyyMMddHHmmssSSS, new Date()));

        System.out.println(DateUtil.addHour(DateUtil.getCurrentTime(), -(24 * 366)));


    }
}

