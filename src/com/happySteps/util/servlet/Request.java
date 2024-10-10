/**
 * YOU ARE STRICTLY PROHIBITED TO COPY, DISCLOSE, DISTRIBUTE, MODIFY OR USE THIS PROGRAM
 * IN PART OR AS A WHOLE WITHOUT THE PRIOR WRITTEN CONSENT OF HAPPYSTEPS.COM.
 * HAPPYSTEPS.COM OWNS THE INTELLECTUAL PROPERTY RIGHTS IN AND TO THIS PROGRAM.
 * COPYRIGHT (C) 2023 HAPPYSTEPS.COM ALL RIGHTS RESERVED.
 *
 * 하기 프로그램에 대한 저작권을 포함한 지적재산권은 HAPPYSTEPS.COM에 있으며,
 * HAPPYSTEPS.COM이 명시적으로 허용하지 않는 사용, 복사, 변경 및 제 3자에 의한 공개, 배포는 엄격히 금지되며
 * HAPPYSTEPS.COM의 지적재산권 침해에 해당된다.
 * Copyright (C) 2023 HAPPYSTEPS.COM All Rights Reserved.
 *
 *
 * Program		: kr.co.himedia.ecommerce
 * Description	:
 * Environment	: JRE 1.7 or more
 * File			: Request.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20231128164828][pluto@HAPPYSTEPS.COM][CREATE: Initial Release]
 */
package com.happySteps.util.servlet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.happySteps.util.Strings;

/**
 * @version 1.0.0
 * @author pluto@HAPPYSTEPS.COM
 * 
 * @since 2023-11-28
 * <p>DESCRIPTION:</p>
 * <p>IMPORTANT:</p>
 */
public class Request {
	
	/**
	 * @param request [요청 서블릿]
	 * @throws Exception [예외]
	 * 
	 * @since 2024-06-28
	 * <p>DESCRIPTION: HttpServletRequest 객체(request)를 이용하여 Client OS 문자열 얻기</p>
	 * <p>IMPORTANT: [2024-06-28][pluto#HAPPYSTEPS.COM][TODO: 하기 로직 검증 필요]</p>
	 * <p>EXAMPLE:</p>
	 */
	public static String getOs(HttpServletRequest request) {
		
		String userAgent = request.getHeader("User-Agent");
		
		if (userAgent == null) {
			return "[UNKNOWN USER-AGENT]";
		}
		
		userAgent = userAgent.toLowerCase();
		
		String os		= "[UNKNOWN OS]";
		String version	= "[UNKNOWN VERSION]";
		
		if (userAgent.contains("windows")) {
			
			os = "Windows";
			Pattern pattern = Pattern.compile("windows nt (\\d+\\.\\d+)");
			Matcher matcher = pattern.matcher(userAgent);
			
			if (matcher.find()) {
				version = matcher.group(1);
			}
		}
		else if (userAgent.contains("mac")) {
			
			os = "MacOS";
			Pattern pattern = Pattern.compile("mac os x (\\d+[_.]\\d+([_.]\\d+)?)");
			Matcher matcher = pattern.matcher(userAgent);
			
			if (matcher.find()) {
				version = matcher.group(1).replace('_', '.');
			}
		}
		else if (userAgent.contains("x11") || userAgent.contains("nix") || userAgent.contains("nux")) {
			
			os = "Unix/Linux";
			// 리눅스의 경우 버전 정보가 User-Agent에 잘 포함되지 않습니다.
		}
		else if (userAgent.contains("android")) {
			
			os = "Android";
			Pattern pattern = Pattern.compile("android (\\d+\\.\\d+)");
			Matcher matcher = pattern.matcher(userAgent);
			
			if (matcher.find()) {
				version = matcher.group(1);
			}
		}
		else if (userAgent.contains("iphone") || userAgent.contains("ipad")) {
			
			os = "iOS";
			Pattern pattern = Pattern.compile("iphone os (\\d+_\\d+)");
			Matcher matcher = pattern.matcher(userAgent);
			if (matcher.find()) {
				version = matcher.group(1).replace('_', '.');
			}
		}
		
		return os + " " + version;
	}
	
	/**
	 * @param request [요청 서블릿]
	 * @throws Exception [예외]
	 * 
	 * @since 2024-06-28
	 * <p>DESCRIPTION: HttpServletRequest 객체(request)를 이용하여 Client Browser 문자열 얻기</p>
	 * <p>IMPORTANT: [2024-06-28][pluto#HAPPYSTEPS.COM][TODO: 하기 로직 검증 필요]</p>
	 * <p>EXAMPLE:</p>
	 */
	public static String getBrowser(HttpServletRequest request) {
		
		String browser			= request.getHeader("User-Agent");
		
		String browserDetails	= "[UNKNOWN BROWSER]" + browser;
		
		if (browser != null) {
			if (browser.contains("MSIE")) {
				browserDetails = "Internet Explorer";
			}
			else if (browser.contains("Firefox")) {
				browserDetails = "Mozilla Firefox";
			}
			else if (browser.contains("Chrome")) {
				browserDetails = "Google Chrome";
			}
			else if (browser.contains("Safari")) {
				browserDetails = "Apple Safari";
			}
			else if (browser.contains("Opera") || browser.contains("OPR")) {
				browserDetails = "Opera";
			}
		}
		
		return browserDetails.trim();
	}
	
	/**
	 * @param request [요청 서블릿]
	 * @throws Exception [예외]
	 * 
	 * @since 2024-06-28
	 * <p>DESCRIPTION: HttpServletRequest 객체(request)를 이용하여 Client IP 문자열 얻기</p>
	 * <p>IMPORTANT: [2024-06-28][pluto#HAPPYSTEPS.COM][TODO: 하기 로직 검증 필요]</p>
	 * <p>EXAMPLE:</p>
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		
		String ipAddress = "[UNKNOWN IP]";
		
		ipAddress = request.getHeader("X-Forwarded-For");
		
		if (ipAddress == null || ipAddress.isEmpty() || "[UNKNOWN IP]".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		
		if (ipAddress == null || ipAddress.isEmpty() || "[UNKNOWN IP]".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		
		if (ipAddress == null || ipAddress.isEmpty() || "[UNKNOWN IP]".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("HTTP_CLIENT_IP");
		}
		
		if (ipAddress == null || ipAddress.isEmpty() || "[UNKNOWN IP]".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		
		if (ipAddress == null || ipAddress.isEmpty() || "[UNKNOWN IP]".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
		}
		
		return ipAddress.trim();
	}
	
	/**
	 * @param request [요청 서블릿]
	 * @param type [타입(mobile or pc)]
	 * @return boolean
	 * 
	 * @since 2018-10-05
	 * <p>DESCRIPTION: 디바이스 확인</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입 및 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	public static boolean isDevice(HttpServletRequest request, String type) {
		
		boolean result = false;
		
		String devices[] = null;
		
		if (type.equalsIgnoreCase("mobile")) {
			devices = Strings.split("iPhoneAndroid", "");
		}
		else if (type.equalsIgnoreCase("pc")) {
			devices = Strings.split("Windows NTJava", "");
		}
		
		String agent = request.getHeader("User-Agent");
		
		for (int nLoop = 0; nLoop < devices.length; nLoop++) {
			//System.out.println(agent);
			if (agent.indexOf(devices[nLoop]) > -1) result = true;
		}
		
		return result;
	}
	
	/**
	 * @param request [요청 서블릿]
	 * @param key [세션 키]
	 * @param defaultValue [기본값]
	 * @param state [상태]
	 * @throws Exception [예외]
	 * @return String
	 * 
	 * @since 2016-01-15
	 * <p>DESCRIPTION: HttpServletRequest 객체(request)를 이용하여 특정 세션(key)의 값 문자열 얻기</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입을 만족하여야 한다.<br />[주의] 세션에 저장 시 값은 문자열로만 저장해야 한다.<br />문자열이 NULL일 경우 지정한 문자열(defaultValue)로 반환한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	public static String getSession(HttpServletRequest request, String key, String defaultValue, boolean state) throws Exception {
		
		String value = defaultValue;
		
		HttpSession session = ((HttpServletRequest) request).getSession(state);
		
		if (session != null)	value = (String) session.getAttribute(key);
		if (value == null)		value = defaultValue;
		
		return value;
	}
	
	/**
	 * @param request [요청 서블릿]
	 * @return String
	 * 
	 * @since 2017-07-03
	 * <p>DESCRIPTION: HttpServletRequest 객체(request)를 이용하여 Full URL(Query String 포함) 문자열 얻기</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	public static String getFullURL(HttpServletRequest request) {
		
		String uri			= ((HttpServletRequest)request).getRequestURI();
		String queryString	= request.getQueryString();
		
		if (queryString == null || queryString == "") {
			return uri;
		} 
		else {
			return uri + "?" + queryString;
		}
	}
	
	/**
	 * @param request [요청 서블릿]
	 * @param webServer [웹 서버]
	 * @return String
	 * @throws Exception [예외]
	 * 
	 * @since 2015-08-20
	 * <p>DESCRIPTION: HttpServletRequest 객체(request)를 이용하여 Client IP 문자열 얻기</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입을 만족하여야 한다.<br>문자열이 NULL일 경우 빈 문자열을 반환한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	public static String getRemoteAddr(HttpServletRequest request, String webServer) {
		
		String returnValue = "[UNKNOWN IP]";
		
		if (webServer.equalsIgnoreCase("nginx")) {
			returnValue = request.getHeader("X-Forwarded-For");
			if (returnValue == null || returnValue.equals("")) returnValue = request.getRemoteAddr();
		}
		else if (webServer.equalsIgnoreCase("apache"))		returnValue = request.getRemoteAddr();
		else if (webServer.equalsIgnoreCase("tomcat"))		returnValue = request.getRemoteAddr();
		else returnValue = "[UNDEFINED AGENT]";
		
		return returnValue.trim();
	}

}