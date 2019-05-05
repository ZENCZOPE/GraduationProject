package com.cms.util;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.SystemException;


import org.apache.log4j.Logger;
import org.springframework.web.util.HtmlUtils;

public  final class   StringUtil {
	private static final Logger logger = Logger.getLogger(StringUtil.class);

	  public static final String FILE_SEPARATOR_REG = File.separator + File.separator;
	  public static final String FILE_PACKAGE_SEPARATOR = "\\.";
	  private static Pattern SQL_VARIABLE_PATTERN = Pattern.compile(":(\\S*)");
	  private static Pattern DEFAULT_VARIABLE_PATTERN = Pattern.compile(":(\\w*)");
	  private static final String FILE_PRE_CLASSPATH = "classpath:";
	  private static final String FILE_PRE_WEBROOT = "webroot:";

	  public static String convertEmptyWhenNull(String obj)
	  {
	    if (obj == null) {
	      return "";
	    }
	    return obj.toString();
	  }

	  public static boolean isPositiveNum(String str)
	  {
	    Pattern pattern = null;
	    if (str == null) {
	      return false;
	    }
	    pattern = Pattern.compile("^\\d+$");
	    Matcher isNum = pattern.matcher(str);
	    if (!isNum.matches()) {
	      return false;
	    }
	    return true;
	  }

	  public static boolean isEqual(Object obj1, Object obj2)
	  {
	    if ((obj1 == null) && (obj2 == null))
	      return true;
	    if (obj1 == null)
	      return false;
	    if (obj2 == null) {
	      return false;
	    }
	    return obj1.equals(obj2);
	  }

	  public static boolean isEmptyOrNull(Object str)
	  {
	    if (("".equals(str)) || (null == str)) {
	      return true;
	    }
	    return false;
	  }

	  public static boolean isSpaceOrNull(String str)
	  {
	    if ((null == str) || ("".equals(str.trim()))) {
	      return true;
	    }
	    return false;
	  }

	  public static String formatStr(Object str, String defaultStr)
	  {
	    if (isNull(str)) {
	      if (isNull(defaultStr)) {
	        return "";
	      }
	      return defaultStr;
	    }
	    return str.toString();
	  }

	  public static boolean isNotEmptyOrNull(Object str)
	  {
	    if ((!"".equals(str)) && (null != str)) {
	      return true;
	    }
	    return false;
	  }

	  public static boolean isNull(Object str)
	  {
	    if (("".equals(str)) || (null == str)) {
	      return true;
	    }
	    return false;
	  }

	  public static boolean isNumeric(String str)
	  {
	    if (isSpaceOrNull(str)) {
	      return false;
	    }
	    Pattern pattern = null;
	    pattern = Pattern.compile("^-?\\d+$");
	    Matcher isNum = pattern.matcher(str);
	    if (!isNum.matches()) {
	      return false;
	    }
	    return true;
	  }

	  public static boolean regularValid(String str, String regex)
	  {
	    Pattern p = Pattern.compile(regex);
	    Matcher m = p.matcher(str);
	    if (!m.matches()) {
	      return false;
	    }
	    return true;
	  }

	  public static String[] splitToArr(String str, String separate)
	  {
	    if (isEmptyOrNull(str)) {
	      return new String[0];
	    }
	    return str.split(separate);
	  }

	/*  public static String formatFilePath(String inPath)
	  {
	    if (logger.isDebugEnabled()) {
	      logger.debug("调用【formatFilePath】方法开始，入参： [inPath]=" + inPath);
	    }
	    String realFilePath = "";
	    if ((isNotEmptyOrNull(inPath)) && (inPath.startsWith("classpath:"))) {
	      ClassLoader classLoader = StringUtil.class.getClassLoader();
	      String fileClassPath = inPath.substring(10, inPath.length());
	      URL url = classLoader.getResource(fileClassPath);
	      if (url == null) {
	        throw new CMSException("调用失败");
	      }

	      realFilePath = url.getFile();
	    }
	    else if ((isNotEmptyOrNull(inPath)) && (inPath.startsWith("webroot:"))) {
	      String webRoot = FilenameMatcherResolver.getRealAppPath();
	      if (!webRoot.endsWith(File.separator)) {
	        webRoot = webRoot + File.separator;
	      }
	      realFilePath = webRoot + inPath.substring(8, inPath.length());
	    } else {
	      realFilePath = inPath;
	    }

	    realFilePath = realFilePath.replaceAll("\\\\", FILE_SEPARATOR_REG);
	    realFilePath = realFilePath.replaceAll("/", FILE_SEPARATOR_REG);
	    try
	    {
	      realFilePath = URLDecoder.decode(realFilePath, "utf-8");
	    } catch (UnsupportedEncodingException e) {
	      logger.error("路径转换编码错误: ", e);
	    }
	    if (logger.isDebugEnabled()) {
	      logger.debug("调用【formatFilePath】方法结束，出参： [realFilePath]=" + realFilePath);
	    }
	    return realFilePath;
	  }

	  public static String formatDirPath(String inPath)
	  {
	    if (logger.isDebugEnabled()) {
	      logger.debug("调用【formatDirPath】方法开始，入参： [inPath]=" + inPath);
	    }
	    String realFilePath = formatFilePath(inPath);
	    if (!realFilePath.endsWith(FILE_SEPARATOR_REG)) {
	      realFilePath = realFilePath + FILE_SEPARATOR_REG;
	    }
	    if (logger.isDebugEnabled()) {
	      logger.debug("调用【formatDirPath】方法结束，出参： [realFilePath]=" + realFilePath);
	    }
	    return realFilePath;
	  }*/

	  /*public static String replace(String inStr, String searchString, String replacement)
	  {
	    return StringUtils.replace(inStr, searchString, replacement);
	  }*/

	  public static String flushLeft(char c, long length, String content)
	  {
	    String str = "";
	    String cs = "";
	    if (content.length() > length)
	      str = content;
	    else {
	      for (int i = 0; i < length - content.length(); i++) {
	        cs = cs + c;
	      }
	    }
	    str = cs + content;
	    return str;
	  }

	  public static String flushRight(char c, long length, String content)
	  {
	    String str = "";
	    String cs = "";
	    if (content.length() > length)
	      str = content;
	    else {
	      for (int i = 0; i < length - content.length(); i++) {
	        cs = cs + c;
	      }
	    }
	    str = content + cs;
	    return str;
	  }

	  public static String getPYString(String str)
	  {
	    String tempStr = "";
	    for (int i = 0; i < str.length(); i++) {
	      char c = str.charAt(i);

	      if ((c >= '!') && (c <= '~')) {
	        tempStr = tempStr + String.valueOf(c);
	      }
	      else {
	        tempStr = tempStr + getPYChar(String.valueOf(c));
	      }
	    }
	    return tempStr;
	  }

	  public static String getMoneyStr(double value)
	  {
	    DecimalFormat df = new DecimalFormat("##.##");
	    Double fmtValue = Double.valueOf(Double.parseDouble(df.format(value)) + 0.001D);
	    df = new DecimalFormat("##.###");
	    String tempStr = df.format(fmtValue);
	    return tempStr.substring(0, tempStr.length() - 1);
	  }

	  public static String replaceDftVariable(String variableSql, Map<String, Object> variableValues)
	  {
	    StringBuffer sb = new StringBuffer();

	    Matcher matcher = null;
	    matcher = SQL_VARIABLE_PATTERN.matcher(variableSql);
	    while (matcher.find()) {
	      String variableName = matcher.group(1);
	      String variableValue = String.valueOf(variableValues.get(variableName));
	      variableValue = variableValue.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", "\\\\\\$");
	      if ((!variableValue.startsWith("'")) || (!variableValue.endsWith("'"))) {
	        variableValue = "'" + variableValue + "'";
	      }
	      matcher.appendReplacement(sb, variableValue);
	    }
	    matcher.appendTail(sb);

	    return sb.toString();
	  }

	  public static String replaceDftChkNullVariable(String variableSql, Map<String, Object> variableValues)
	    throws SystemException
	  {
	    StringBuffer sb = new StringBuffer();

	    return sb.toString();
	  }

	  public static List<String> findDftVariable(String variableSql)
	  {
	    List variableList = new ArrayList();

	    Matcher matcher = null;
	    matcher = SQL_VARIABLE_PATTERN.matcher(variableSql);
	    while (matcher.find()) {
	      String variableName = matcher.group(1);
	      variableList.add(variableName);
	    }

	    return variableList;
	  }

	  public static String getPYChar(String c)
	  {
	    if (c.trim().length() == 0) {
	      return c;
	    }
	    byte[] array = new byte[2];
	    array = String.valueOf(c).getBytes();
	    int i = (short)(array[0] - 0 + 256) * 256 + (short)(array[1] - 0 + 256);

	    if (i < 45217)
	      return "*";
	    if (i < 45253)
	      return "A";
	    if (i < 45761)
	      return "B";
	    if (i < 46318)
	      return "C";
	    if (i < 46826)
	      return "D";
	    if (i < 47010)
	      return "E";
	    if (i < 47297)
	      return "F";
	    if (i < 47614)
	      return "G";
	    if (i < 48119)
	      return "H";
	    if (i < 49062)
	      return "J";
	    if (i < 49324)
	      return "K";
	    if (i < 49896)
	      return "L";
	    if (i < 50371)
	      return "M";
	    if (i < 50614)
	      return "N";
	    if (i < 50622)
	      return "O";
	    if (i < 50906)
	      return "P";
	    if (i < 51387)
	      return "Q";
	    if (i < 51446)
	      return "R";
	    if (i < 52218)
	      return "S";
	    if (i < 52698)
	      return "T";
	    if (i < 52980)
	      return "W";
	    if (i < 53689)
	      return "X";
	    if (i < 54481)
	      return "Y";
	    if (i < 55290) {
	      return "Z";
	    }
	    return "*";
	  }

	  public static String obj2Str(Object target, String defaultValue)
	  {
	    String value = defaultValue;
	    if ((target != null) && (!target.equals("null"))) {
	      value = String.valueOf(target);
	    }
	    return value;
	  }

	  public static String obj2Str(Object target)
	  {
	    return obj2Str(target, "");
	  }

	  public static boolean contains(String str, String search)
	  {
	    if ((str == null) || (search == null)) {
	      return false;
	    }
	    return str.contains(search);
	  }

	  public static String htmlEscape(String inStr)
	  {
	    return HtmlUtils.htmlEscape(inStr);
	  }

	  public static String htmlUnescape(String inStr)
	  {
	    return HtmlUtils.htmlUnescape(inStr);
	  }

	  public static String charsetConvert(String conversion, String charset)
	    throws SystemException
	  {
	    String returnStr = "";

	    return returnStr;
	  }

	  public static Long[] convertLongArray(String[] sArray)
	  {
	    if ((sArray == null) || (sArray.length == 0)) {
	      return null;
	    }

	    Long[] lArray = new Long[sArray.length];

	    return lArray;
	  }

	  public static String maxStr(String oldStr, int maxLen)
	  {
	    if ((oldStr == null) || ("".equals(oldStr))) {
	      return "";
	    }
	    if (oldStr.length() > maxLen)
	      oldStr = oldStr.substring(0, maxLen);
	    return oldStr;
	  }

	  public static String trimSymbol(String inStr)
	  {
	    if ((inStr == null) || ("".equals(inStr.trim()))) {
	      return "";
	    }
	    return inStr.replaceAll("\\D", "");
	  }

	  public static String transStrComma(String str)
	  {
	    if (!"".equals(str)) {
	      str = str.substring(0, str.length() - 1);
	    }
	    return str;
	  }

	  public static String str2Fix(Object target, int fixedLong)
	  {
	    if (target == null) {
	      target = obj2Str(target);
	    }
	    return str2Fix(target.toString(), " ", fixedLong);
	  }

	  public static String str2Fix(String target, String defaultValue, int fixedLong)
	  {
	    return str2Fix(target, defaultValue, fixedLong, 0);
	  }

	  public static String str2Fix(String target, String defaultValue, int fixedLong, int direction)
	  {
	    if (target == null) {
	      target = "";
	    }
	    int len = target.length();
	    if (len == fixedLong)
	      return target;
	    if (len < fixedLong) {
	      StringBuilder sb = new StringBuilder();
	      for (int i = 0; i < fixedLong - len; i++) {
	        sb.append(defaultValue);
	      }
	      if (direction == 0) {
	        return target + sb.toString();
	      }
	      return sb.toString() + target;
	    }

	    if (direction == 0) {
	      return target.substring(0, fixedLong);
	    }
	    return target.substring(target.length() - fixedLong);
	  }

	  public static String strByte2Fix(String target, String defaultValue, int fixedLong)
	  {
	    if (target == null) {
	      target = "";
	    }
	    int len = target.getBytes().length;
	    if (len == fixedLong)
	      return target;
	    if (len < fixedLong) {
	      StringBuilder sb = new StringBuilder();
	      sb.append(target);
	      for (int i = 0; i < fixedLong - len; i++) {
	        sb.append(defaultValue);
	      }
	      return sb.toString();
	    }
	    return target;
	  }

	  public static String obj2Talbe(String objectName)
	    throws Exception
	  {
	    StringBuffer sb = new StringBuffer();
	    if ((objectName != null) && (objectName.length() > 0)) {
	      for (int i = 0; i < objectName.length(); i++) {
	        char c = objectName.charAt(i);
	        if (Character.isUpperCase(c))
	          sb.append("_").append(c);
	        else if (Character.isLowerCase(c))
	          sb.append(Character.toUpperCase(c));
	        else {
	          throw new CMSException( "输入对象名不合规范，必须全为字母" );
	        }
	      }
	      return sb.substring(1);
	    }
	    throw new CMSException("输入对象名不合规范" );
	  }

	  public static String table2Obj(String objectName)
	    throws Exception
	  {
	    StringBuffer sb = new StringBuffer();
	    if ((objectName != null) && (objectName.length() > 0)) {
	      for (int i = 0; i < objectName.length(); i++) {
	        char c = objectName.charAt(i);
	        if (Character.isUpperCase(c))
	          sb.append("_").append(c);
	        else if (Character.isLowerCase(c))
	          sb.append(Character.toUpperCase(c));
	        else {
	          throw new CMSException( "输入对象名不合规范，必须全为字母" );
	        }
	      }
	      return sb.substring(1);
	    }
	    throw new CMSException("输入对象名不合规范" );
	  }
}
