package com.cms.util;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * 日期格式转换类
 *
 * @author linql 创建 2012-2-21
 * @author sunhao 添加dateCompare比较日期 & 获取数据库时间
 * @version 1.0 Copyright(c) 北京思特奇信息技术股份有限公司
 */

public final class DateUtil {
	 /**
     * 日志输出
     */
    private static final Log log = LogFactory.getLog(DateUtil.class);

    /**
     * 主机时间与数据库时间的差值
     */
    private static Long hostDbTimeMinus = null;

    // 构造类时候直接初始化系统时间
    static {
        hostDbTimeMinus = new Long(0);
        //TODO 后期需要考虑打开的问题
//        refreshSysDate();
    }


    /**
     * 年
     */
    public static int YEAR = 1;

    /**
     * 月
     */
    public static int MONTH = 2;

    /**
     * 周
     */
    public static int WEEK = 3;

    /**
     * 日
     */
    public static int DAY = 5;

    /**
     * 小时
     */
    public static int HOUR = 10;

    /**
     * 分钟
     */
    public final static int MINUTE = 12;

    /**
     * 秒
     */
    public final static int SECOND = 13;

    /**
     * 毫秒
     */
    public final static int MILLISECOND = 14;

    /**
     * 创建一个新的实例 DateUtil.
     */
    private DateUtil() {

    }

    /**
     * 将Date型转换为Timestamp类型
     *
     * @param date 日期
     * @return 时间撮
     */
    public static java.sql.Timestamp convertToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return new java.sql.Timestamp(date.getTime());
        }
    }

    /**
     * 将日期对象转换为yyyy-MM-dd HH:mm:ss格式字符串
     *
     * @param date 时间对象
     * @return yyyy-MM-dd HH:mm:ss格式字符串
     */
    public static String toStringYmdHmsWthH(Date date) {
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
    }

    /**
     * 将日期对象转换为yyyy-MM-dd HH:mm:ss SSS格式字符串
     *
     * @param date 时间对象
     * @return yyyy-MM-dd HH:mm:ss SSS格式字符串
     */
    public static String toStringYmdHmsWthHS(Date date) {
        return (new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(date);
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss格式字符串转换为日期对象
     *
     * @param dateStr yyyy-MM-dd HH:mm:ss格式字符串
     * @return 日期对象
     * @throws ParseException 
     */
    public static Date toDateYmdHmsWthH(String dateStr) throws ParseException {
        
            return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(dateStr);
        
    }

    /**
     * 将日期对象转换为yyyy-MM-dd格式字符串
     *
     * @param date 时间对象
     * @return yyyy-MM-dd格式字符串
     */
    public static String toStringYmdWthH(Date date) {
        return (new SimpleDateFormat("yyyy-MM-dd")).format(date);
    }

    /**
     * 将日期对象转换为yyyy/MM/dd格式字符串
     *
     * @param date 时间对象
     * @return yyyy/MM/dd格式字符串
     */
    public static String toStringYmdWthB(Date date) {
        return (new SimpleDateFormat("yyyy/MM/dd")).format(date);
    }

    /**
     * 将yyyy-MM-dd格式字符串转换为日期对象
     *
     * @param dateStr yyyy-MM-dd格式字符串
     * @return 日期对象
     * @throws ParseException 
     */
    public static Date toDateYmdWthH(String dateStr) throws ParseException {
        
            return (new SimpleDateFormat("yyyy-MM-dd")).parse(dateStr);
       
    }

    /**
     * 将日期对象转换为yyyyMMddHHmmss格式字符串
     *
     * @param date 时间对象
     * @return yyyyMMddHHmmss格式字符串
     */
    public static String toStringYmdHms(Date date) {
        return (new SimpleDateFormat("yyyyMMddHHmmss")).format(date);
    }

    /**
     * 将yyyyMMddHHmmss格式字符串转换为日期对象
     *
     * @param dateStr yyyyMMddHHmmss格式字符串
     * @return 日期对象
     * @throws ParseException 
     */
    public static Date toDateYmdHms(String dateStr) throws ParseException {
        
            SimpleDateFormat dfm = new SimpleDateFormat("yyyyMMddHHmmss");
            return dfm.parse(dateStr);
       
    }

    /**
     * 将日期对象转换为yyyyMMdd格式字符串
     *
     * @param date 时间对象
     * @return yyyyMMdd格式字符串
     */
    public static String toStringYmd(Date date) {
        return (new SimpleDateFormat("yyyyMMdd")).format(date);
    }

    /**
     * 将日期对象转换为yyMMdd格式字符串
     *
     * @param date 时间对象
     * @return yyMMdd格式字符串
     */
    public static String toStringYYmd(Date date) {
        return (new SimpleDateFormat("yyMMdd")).format(date);
    }

    /**
     * 将yyyyMMdd格式字符串转换为日期对象
     *
     * @param dateStr yyyyMMdd格式字符串
     * @return 日期对象
     * @throws ParseException 
     */
    public static Date toDateYmd(String dateStr) throws ParseException {
        
            return (new SimpleDateFormat("yyyyMMdd")).parse(dateStr);
       
    }

    /**
     * 将日期对象转换为yyyyMM格式字符串
     *
     * @param date 时间对象
     * @return yyyyMM格式字符串
     */
    public static String toStringYm(Date date) {
        return (new SimpleDateFormat("yyyyMM")).format(date);
    }

    /**
     * 将yyyyMM格式字符串转换为日期对象
     *
     * @param dateStr yyyyMM格式字符串
     * @return 日期对象
     * @throws ParseException 
     */
    public static Date toDateYm(String dateStr) throws ParseException {
     
            return (new SimpleDateFormat("yyyyMM")).parse(dateStr);
       
    }

    /**
     * 将日期对象转换为yyyy格式字符串
     *
     * @param date 时间对象
     * @return yyyy格式字符串
     */
    public static String toStringY(Date date) {
        return (new SimpleDateFormat("yyyy")).format(date);
    }

    /**
     * 将yyyy格式字符串转换为日期对象
     *
     * @param dateStr yyyy格式字符串
     * @return 日期对象
     * @throws ParseException 
     */
    public static Date toDateY(String dateStr) throws ParseException {
      
            return (new SimpleDateFormat("yyyy")).parse(dateStr);
       
    }

    /**
     * 将日期对象转换为yyyy年MM月dd日格式字符串
     *
     * @param date 时间对象
     * @return yyyyMM格式字符串
     */
    public static String toStringYmdwithChinese(Date date) {
        return (new SimpleDateFormat("yyyy年MM月dd日")).format(date);
    }

    /**
     * 将yyyy-MM-dd HH24:mm:ss格式字符串转换为日期对象
     *
     * @param dateStr yyyy-MM-dd HH24:mm:ss格式字符串
     * @return 日期对象
     * @throws ParseException 
     * @deprecated 请使用toDateYmdHmsWthH方法
     */
    @Deprecated
    public static Date toDateYmdH24msWthH(String dateStr) throws ParseException {
        return toDateYmdHmsWthH(dateStr);
    }

    /**
     * 将yyyy-MM-dd HH24:mi:ss格式字符串转换为日期对象
     *
     * @param dateStr yyyy-MM-dd HH24:mi:ss格式字符串
     * @return 日期对象
     * @throws ParseException 
     * @deprecated 请使用toDateYmdHmsWthH方法
     */
    @Deprecated
    public static Date toDateYmdH24misWthH(String dateStr) throws ParseException {
        return toDateYmdH24msWthH(dateStr);
    }

    /**
     * 将日期对象转换为YYYYMMDDHH24MISS格式字符串
     *
     * @param date 时间对象
     * @return YYYYMMDDHH24MISS格式字符串
     * @deprecated 请使用toStringYmdHms方法
     */
    @Deprecated
    public static String toStringYmdH24ms(Date date) {
        return toStringYmdHms(date);
    }

    /**
     * 将YYYYMMDDHH24MISS格式字符串转换为日期对象
     *
     * @param dateStr YYYYMMDDHH24MISS格式字符串<br/>
     * @return 日期对象
     * @throws ParseException 
     * @deprecated 请使用toDateYmdHms方法
     */
    @Deprecated
    public static Date toDateYmdH24ms(String dateStr) throws ParseException {
        return toDateYmdHms(dateStr);
    }

    /**
     * 将日期对象转换为yyyy年MM月dd日HH24时MI分SS秒格式字符串
     *
     * @param date 时间对象
     * @return yyyyMM格式字符串
     */
    public static String toStringYmdwithsfm(Date date) {
        return (new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒")).format(date);
    }

    /**
     * 比较当前日期(数据库日期)是否大于指定日期
     *
     * @param str 待比较日期参数
     * @return boolean
     * @throws java.text.ParseException
     */
    public static boolean dateCompare(Object str) throws ParseException {
        boolean bea = false;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String isDate = simpleDateFormat.format(getSysDate());

        Date date1 = null;
        Date date0;
        
            if (str instanceof Date) {
                date1 = simpleDateFormat.parse(DateUtil.toStringYmdWthH((Date) str));
            }
            if (str instanceof String) {
                date1 = simpleDateFormat.parse((String) str);
            }
            date0 = simpleDateFormat.parse(isDate);
            if (date0.after(date1)) {
                bea = true;
            }
        
        return bea;
    }

    /**
     * 比较两个日期的大小
     * <p/>
     * <pre>
     * 1、日期参数为空表示无穷小
     * </pre>
     *
     * @param inDate1 第一个日期参数
     * @param inDate2 第二个日期参数
     * @return 处理结果 0:相等, -1:inDate1<inDate2, 1:inDate1>inDate2
     * @throws
     */
    /*public static int dateCompare(Date inDate1, Date inDate2) {
        return dateCompare(inDate1, inDate2, SECOND);
    }*/

    /**
     * 比较日期大小
     *
     * @param inDate1 日期1
     * @param inDate2 日期2
     * @param unit    比较精度 年：1 ;月：2; 周：3; 日：5; 时：10; 分：12;秒：13
     * @return 处理结果 0:相等, -1:inDate1<inDate2, 1:inDate1>inDate2
     */
 /*   public static int dateCompare(Date inDate1, Date inDate2, int unit) {
        // 字符空验证
        if (inDate1 == null && inDate2 == null) {
            // 两个日期都为空返回相等
            return 0;
        } else if (inDate1 == null) {
            // 日期1为空，日期2不为空返回-1
            return -1;
        } else if (inDate2 == null) {
            // 日期1不为空，日期为空返回2
            return 1;
        }
        return DateUtil.truncate(inDate1, unit).compareTo(DateUtil.truncate(inDate2, unit));
    }*/

    /**
     * 比较日期大小,计算两个日期的差值（单位：秒级）
     *
     * @param inDate1 日期1
     * @param inDate2 日期2
     * @return 处理结果 0:相等, -1:inDate1或inDate2为空, n:两个日期的时间差
     */
    public static int dateCompareDiff(Date inDate1, Date inDate2) {
    	
    	int differ = 0;
        // 字符空验证
        if (inDate1 == null && inDate2 == null) {
            // 两个日期都为空返回相等
        	differ= 0;
        } else if (inDate1 == null) {
            // 日期1为空，日期2不为空返回-1
        	differ= -1;
        } else if (inDate2 == null) {
            // 日期1不为空，日期为空返回2
        	differ= -1;
        }
        
        long dtime = inDate1.getTime() - inDate2.getTime();
        differ = new Long(dtime/1000).intValue();
        differ = Math.abs(differ);
        return differ;
    }
    
    /**
     * 调整时间, 按照需要调整的单位调整时间
     * <p/>
     * <pre>
     * 例如:保留到日期的年change("20120511154440", DateUtil.YEAE, 2);返回：20140511154440<br/>
     * </pre>
     *
     * @param inDate 传入日志
     * @param unit   调整单位 年：1 ;月：2; 周：3; 日：5; 时：10; 分：12;秒：13
     * @param amount 调整数量
     * @return 调整后的日期
     */
    public static Date change(Date inDate, int unit, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inDate);
        calendar.add(unit, amount);
        return calendar.getTime();
    }

    /**
     * 按照精度要求对日期进行舍取
     * <p/>
     * <pre>
     * 例如:保留到日期的年truncate("20120511154440", DateUtil.YEAE);返回：20120101000000<br/>
     * </pre>
     *
     * @param inDate 输入日期
     * @param unit   单位 年：1 ;月：2;日：5; 时：10; 分：12;秒：13
     * @return 处理后的日期
     */
    /*public static Date truncate(Date inDate, int unit) {
        return DateUtils.truncate(inDate, unit);
    }
*/
    /**
     * 按照指定的党委获取指定日志的最后值
     * <p/>
     * <pre>
     * 例如:保留到日期的年last("20120511154440", DateUtil.YEAE);返回：20121231235959<br/>
     * </pre>
     *
     * @param inDate 输入日期
     * @param unit   单位 年：1 ;月：2;日：5; 时：10; 分：12;秒：13
     * @return 处理后的日期
     */
  /*  public static Date last(Date inDate, int unit) {
        Date tempDate = truncate(inDate, unit);
        tempDate = change(tempDate, unit, 1);
        return change(tempDate, DateUtil.MILLISECOND, -1);

    }*/

    /**
     * 比较两个日期的大小
     * <p/>
     * <pre>
     * 1、日期参数为空表示无穷小
     * </pre>
     *
     * @param inDate1 第一个日期参数
     * @param inDate2 第二个日期参数
     * @param unit    单位 年：1 ;月：2;日：5; 时：10; 分：12;秒：13
     * @return 处理结果 0:相等, -1:inDate1<inDate2, 1:inDate1>inDate2
     * @throws
     */
/*    public static int truncateComare(Date inDate1, Date inDate2, int unit) {
        if (inDate1 == null && inDate2 == null) {
            // 两个日期都为空返回相等
            return 0;
        } else if (inDate1 == null) {
            // 日期1为空，日期2不为空返回-1
            return -1;
        } else if (inDate2 == null) {
            // 日期1不为空，日期为空返回2
            return 1;
        } else {
            Date tempDate1 = truncate(inDate1, unit);
            Date tempDate2 = truncate(inDate2, unit);
            return tempDate1.compareTo(tempDate2);
        }

    }*/

    /**
     * 将日期格式化为指定的格式
     *
     * @param date    日期
     * @param pattern 格式化模式
     * @return
     */
   /* public static String format(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }*/

    /**
     * 获取数据库当前时间
     * 根据主机时间与主机时间与系统时间的差值获取统一时间
     *
     * @return 数据库时间
     */
    public static Date getSysDate() {
        // 根据主机时间及及

        return new Date(new Date().getTime() + hostDbTimeMinus);
    }

    /**
     * 更新主机时间与数据库统一时间的差值
     *
     * @param minusTime
     */
    public static void updateHostDbTimeMinus(long minusTime) {
        synchronized (hostDbTimeMinus) {
            hostDbTimeMinus = minusTime;
        }
    }

    /**
     * 同步系统时间
     */
   /* public static void refreshSysDate() {
        IBaseDao baseDao = null;
        try {
            baseDao = (IBaseDao) LocalContextFactory.getInstance().getBean("baseDao");
        } catch (Exception e) {
            log.info("获取baseDao数据源数据源失败", e);
        }
        long minusTime = 0l;
        if (baseDao == null) {
            log.info("未注册baseDao数据源，程序时间将直接使用主机时间");
        } else {
            try {
                Date hostDate = new Date();
                Date sysDate = (Date) baseDao.queryForObject("CommonDao.getSysTm");
                minusTime = sysDate.getTime() - hostDate.getTime();
            } catch (Exception e) {
                log.info("获取数据库时间异常", e);
            }
        }
        updateHostDbTimeMinus(minusTime);
    }*/
}
