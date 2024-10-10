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
 * File			: FileDownloadView.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20240808141801][pluto@HAPPYSTEPS.COM][CREATE: Initial Release]
 */
package com.happySteps.common.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

/**
 * @version 1.0.0
 * @author pluto@HAPPYSTEPS.COM
 * 
 * @since 2024-08-08
 * <p>DESCRIPTION:</p>
 * <p>IMPORTANT:</p>
 */
public class FileDownloadView extends AbstractView {
	
	/**
	 * @param map [Map]
	 * @param request [요청 서블릿]
	 * @param response [응답 서블릿]
	 * @throws Exception
	 * 
	 * @since 2015-12-23
	 * <p>DESCRIPTION: 해당 뷰 생성</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입 및 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	@Override
	protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		super.setContentType("application/octet-stream");
		
		File file			= (File)map.get("file");
		String filename		= (String)map.get("filename");
		long filesize		= (Long)map.get("filesize");
		
		response.setContentType(getContentType());
		response.setContentLength((int)filesize);
		// 한글인 경우 글자가 깨지므로 깨지지 않도록 인코딩 설정
		filename = URLEncoder.encode(filename,"UTF-8");
		// 파일명에 공백이 있는 경우 +로 바뀌는 것 방지하기
		filename = filename.replaceAll("\\+","%20");
		// 변환된 파일명이 다운로드 창에 보이도록 설정
		response.setHeader("Content-Disposition","attachment;filename=" + filename);
		
		// 실제로 다운로드 하기(파일복사)
		// 클라이언트에 파일을 출력하기 위한 스트림
		OutputStream os		= response.getOutputStream();
		// 다운로드할 파일을 읽기위한 스트림
		FileInputStream fis	= new FileInputStream(file);
		// 파일 복사하기(클라이언트에 다운로드하기)
		FileCopyUtils.copy(fis,os);
		os.close();
		fis.close();
	}
}