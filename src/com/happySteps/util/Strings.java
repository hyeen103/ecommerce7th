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
 * File			: Strings.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20231124123147][pluto@HAPPYSTEPS.COM][CREATE: Initial Release]
 */
package com.happySteps.util;

import java.util.StringTokenizer;

/**
 * @version 1.0.0
 * @author pluto@HAPPYSTEPS.COM
 * 
 * @since 2023-11-24
 * <p>DESCRIPTION:</p>
 * <p>IMPORTANT:</p>
 */
public class Strings {
	
	/**
	 * @param string [문자열]
	 * @param mode [모드: left or right(DEFAULT)]
	 * @param count [마스킹 수]
	 * @param letter [마스킹 문자]
	 * @return String
	 * 
	 * @since 2020-03-20
	 * <p>DESCRIPTION:</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입과 형식을 만족하여야 한다.<br>객체가 NULL일 경우 빈 문자열을 반환한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	public static String getMasking(String string, String mode, int count, String letter) {
		
		String returnString = "";
		
		int stringLength = string.length();
		
		String letterAll = "";
		for (int loop = 0; loop < count; loop++) {
			letterAll += letter;
		}
		
		if (mode.equals("left")) {
			if (stringLength >= count) {
				
				returnString = string.substring(count, stringLength);
				returnString = letterAll + returnString;
			}
			else {
				returnString = letterAll;
			}
		}
		else {
			if (stringLength >= count) {
				
				returnString = string.substring(0, stringLength - count);
				returnString = returnString + letterAll;
			}
			else {
				returnString = letterAll;
			}
		}
		
		return returnString;
	}
	
	/**
	 * @param string [문자열]
	 * @param isSpace [공백 여부]
	 * @param isNumber [숫자 여부]
	 * @param isAlphabet [영문자 여부]
	 * @param isHangul [한글 여부]
	 * @return String
	 * 
	 * @since 2018-05-21
	 * <p>DESCRIPTION: 원하는 문자셋만 문자열로 얻기</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입과 형식을 만족하여야 한다.<br>객체가 NULL일 경우 빈 문자열을 반환한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	public static String getString(String string, boolean isSpace, boolean isNumber, boolean isAlphabet, boolean isHangul) {
		
		String result = null;
		
		int HANGUL_UNICODE_START	= 0xAC00;		// 한글 범위의 시작 코드값
		int HANGUL_UNICODE_END		= 0xD7A3;		// 한글 범위의 마지막 코드값
		
		int length			= string.length();
		// string			= string.toUpperCase();		// 대문자로 변환
		
		StringBuffer stringBuffer = new StringBuffer();
		
		for (int loop = 0; loop < length; loop++) { 
		
			char syllable = string.charAt(loop);
			
			if ((syllable >= HANGUL_UNICODE_START && syllable <= HANGUL_UNICODE_END && isHangul)
					|| (syllable >= '0' && syllable <= '9' && isNumber)
					|| (syllable >= 'a' && syllable <= 'z' && isAlphabet) || (syllable >= 'A' && syllable <= 'Z' && isAlphabet)
					|| (syllable >= ' ' && isSpace))
			{
				stringBuffer.append(syllable);
			}
		}
		
		if (stringBuffer.length() > 0) result = stringBuffer.toString();
		
		return result;
	}
	
	/**
	 * @param object [오브젝트]
	 * @return String
	 * 
	 * @since 2015-08-20
	 * <p>DESCRIPTION: 객체를 문자열로 얻기</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입과 형식을 만족하여야 한다.<br>객체가 NULL일 경우 빈 문자열을 반환한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	public static String getString(Object object) {

		if (object == null) {
			return "";
		}
		else {
			return (String) object;
		}
	}
	
	/**
	 * @param object [오브젝트]
	 * @param defaultValue [기본값]
	 * @return String
	 * 
	 * @since 2015-08-20
	 * <p>DESCRIPTION: 객체를 문자열로 얻기</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입과 형식을 만족하여야 한다.<br>객체가 NULL일 경우 지정한 문자열(defaultValue)로 반환한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	public static String getString(Object object, String defaultValue) {

		if (object == null)
			return defaultValue;
		else if (((String) object).length() < 1)
			return defaultValue;
		else
			return (String) object;
	}
	
	/**
	 * @param string [문자열]
	 * @param oldString [치환 전 문자열]
	 * @param newString [치환 후 문자열]
	 * @return String
	 * 
	 * @since 2015-08-20
	 * <p>DESCRIPTION: 문자열(string)에서 특정 문자열(oldString)을 특정 문자열(newString)로 치환하여 얻기</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입 및 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	public static String replace(String string, String oldString, String newString) {
		
		String returnValue = new String();
		int position = 0;
		int begin = 0;
		
		position = string.indexOf(oldString);
		
		if (position == -1) {
			return string;
		}
		else {
			while (position != -1) {
				returnValue = returnValue + string.substring(begin, position) + newString;
				begin = position + oldString.length();
				position = string.indexOf(oldString, begin);
			}
			
			returnValue = returnValue + string.substring(begin);
			
			return returnValue;
		}
	}
	
	/**
	 * @param string [문자열]
	 * @param splitter [구분자]
	 * @return String[]
	 * 
	 * @since 2015-08-20
	 * <p>DESCRIPTION:문자열(string)을 구분자(splitter)로 나누어 문자열 배열로 얻기</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입 및 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	@Deprecated
	public static String[] getArraybySplitter(String string, String splitter) {
		
		String array[] = null;
		
		if (string != null) {
			StringTokenizer stringtokenizer = new StringTokenizer(string, splitter);
			int i = 0;
			array = new String[i = stringtokenizer.countTokens()];
			for (int j = 0; j < i; j++) {
				array[j] = stringtokenizer.nextToken();
			}
		}
		
		return array;
	}
	
	/**
	 * @param string [문자열]
	 * @param regex [구분자]
	 * @return String[]
	 * 
	 * @since 2018-08-10
	 * <p>DESCRIPTION:문자열(string)을 구분자(regex)로 나누어 문자열 배열로 얻기</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입 및 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:
	 * String[] array = Strings.split("aaa++++++bbb+++ccc", "\\+\\+\\+");<br>
	 * </p>
	 */
	public static String[] split(String string, String regex) {
		
		String[] array = null;
		
		if (string != null) {
			array = string.split(regex);
		}
		
		return array;
	}
}
