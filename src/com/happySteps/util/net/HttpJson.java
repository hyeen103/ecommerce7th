/**
 * YOU ARE STRICTLY PROHIBITED TO COPY, DISCLOSE, DISTRIBUTE, MODIFY OR USE THIS PROGRAM
 * IN PART OR AS A WHOLE WITHOUT THE PRIOR WRITTEN CONSENT OF HAPPYSTEPS.COM.
 * HAPPYSTEPS.COM OWNS THE INTELLECTUAL PROPERTY RIGHTS IN AND TO THIS PROGRAM.
 * COPYRIGHT (C) 2024 HAPPYSTEPS.COM ALL RIGHTS RESERVED.
 *
 * 하기 프로그램에 대한 저작권을 포함한 지적재산권은 HAPPYSTEPS.COM에 있으며,
 * HAPPYSTEPS.COM이 명시적으로 허용하지 않는 사용, 복사, 변경 및 제 3자에 의한 공개, 배포는 엄격히 금지되며
 * HAPPYSTEPS.COM의 지적재산권 침해에 해당된다.
 * Copyright (C) 2024 HAPPYSTEPS.COM All Rights Reserved.
 *
 *
 * Program		: kr.co.himedia.ecommerce
 * Description	:
 * Environment	: JRE 1.7 or more
 * File			: HttpJson.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20240829125245][pluto@HAPPYSTEPS.COM][CREATE: Initial Release]
 */
package com.happySteps.util.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @version 1.0.0
 * @author pluto@HAPPYSTEPS.COM
 * 
 * @since 2024-08-29
 * <p>DESCRIPTION:</p>
 * <p>IMPORTANT:</p>
 */
public class HttpJson {
	
	/** URL */
	private URL url = null;
	
	/**
	 * @param url [요청할 경로]
	 * @throws MalformedURLException 
	 * 
	 * @since 2015-09-02
	 * <p>DESCRIPTION: HTTP Client 유틸리티 클래스 생성자</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입과 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	public HttpJson(String url) throws MalformedURLException {
		this.url = new URL(url);
	}
	
	/**
	 * @param requestJSON [요청 JSON]
	 * @param timeout [타임아웃(초단위)]
	 * @return String
	 * @throws IOException 
	 * 
	 * @since 2015-09-02
	 * <p>DESCRIPTION: JSON 문자열(requestJSON)로 요청(POST 방식)하여 문자열로 응답 얻기</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입과 형식을 만족하여야 한다.<br>타임아웃(timeout) 값을 지정할 수 있다.</p>
	 * <p>EXAMPLE:</p>
	 */
	public String connectPost(String requestJSON, int timeout) throws IOException {
	
		String responseJSON = "";
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		conn.setConnectTimeout(timeout);
		conn.setReadTimeout(timeout);
		
		conn.setRequestMethod("POST");
		conn.setRequestProperty("content-type", "application/json; charset=UTF-8");
		conn.setRequestProperty("accept", "application/json");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		
		OutputStream os	= conn.getOutputStream();
		os.write(requestJSON.getBytes("UTF-8"));
		os.flush();
		os.close();
		
		if (conn.getResponseCode() != 200) {
			/** 필요 시 에러 상세는 별도 확인할 것 */
			throw new RuntimeException("HTTP Error Code: " + conn.getResponseCode());
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream()), "UTF-8"));
		
		String line = "";
		while ((line = br.readLine()) != null) {
			responseJSON += line;
		}
		
		br.close();
		conn.disconnect();
		
		return responseJSON;
	}
}
