package com.cms.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.xmlbeans.impl.util.Base64;





public class CookieUtil {
			public static Cookie makeCookie(String arg1,String arg2,HttpServletRequest request) throws UnsupportedEncodingException {
				String value=URLEncoder.encode(arg2, "UTF-8");
				Cookie cookie =new Cookie(arg1, value);
				cookie.setMaxAge(-1);
				cookie.setPath(request.getContextPath());
				return cookie;
			}
			
			public static String getUserRole(Cookie[] cookies) throws UnsupportedEncodingException {
				
				return openCookie(cookies, "loginRole");
			}
			
			public static String getUserName(Cookie[] cookies) throws UnsupportedEncodingException {
				
				return openCookie(cookies, "loginName");
			}
			
			public static int getUserId(Cookie[] cookies) throws UnsupportedEncodingException {
				
				return Integer.parseInt(openCookie(cookies, "loginId"));
			}
			private static String openCookie(Cookie[] cookies,String x) throws UnsupportedEncodingException{
				String data=null;
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals(x)) {
						String aString=cookie.getValue();
						//byte[] bytes= Base64.decode(aString.getBytes());
						 data=URLDecoder.decode(aString, "UTF-8");
						//data=new String(bytes, "UTF-8");
					}
				}
				return data;
			}
			public static void main(String[] args) {
				String aString=Base64.encode("周培".getBytes()).toString();
				System.out.println(aString);
			}
}
