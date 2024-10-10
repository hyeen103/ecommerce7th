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
 * File			: Datetime.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20231128164856][pluto@HAPPYSTEPS.COM][CREATE: Initial Release]
 */
package com.happySteps.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @version 1.0.0
 * @author pluto@HAPPYSTEPS.COM
 * 
 * @since 2023-11-28
 * <p>DESCRIPTION:</p>
 * <p>IMPORTANT:</p>
 */
public class Datetime {
	
	/*
	public static void main(String[] args) {
		System.out.println("현재 시각은 " + Datetime.getNow("yyyy-MM-dd HH:mm:ss"));
	}
	*/
	
	/**
	 * @param datetimeFormat [날짜/시간 형식]
	 * @return String
	 * 
	 * @since 2015-04-12
	 * <p>DESCRIPTION: 현재 날짜/시각을 지정한 형식(datetimeFormat)의 문자열로 얻기</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입 및 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE: <code>datetimeFormat = "yyyy-MM-dd" or "yyyy-MM-dd HH:mm:ss" or "yyyy-MM-dd HH:mm:ss SSS"</code></p>
	 */
	public static String getNow(String datetimeFormat) {
		
		Calendar currentDate		= Calendar.getInstance();
		SimpleDateFormat formatter	= new SimpleDateFormat(datetimeFormat);
		
		return formatter.format(currentDate.getTime());
	}

}