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
 * File			: Files.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20231221144331][pluto@HAPPYSTEPS.COM][CREATE: Initial Release]
 */
package com.happySteps.util;

import java.io.File;

import java.util.UUID;

/**
 * @version 1.0.0
 * @author pluto@HAPPYSTEPS.COM
 * 
 * @since 2023-12-21
 * <p>DESCRIPTION:</p>
 * <p>IMPORTANT:</p>
 */
public class Files {
	
	/**
	 * @param pathBase [베이스 경로]
	 * @return String
	 * 
	 * @since 2015-12-23
	 * <p>DESCRIPTION: 폴더명(yyyyMMddHH 형태) 얻기</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입 및 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	public static String getFolderName(String pathBase) {
		
		String folderName	= Datetime.getNow("yyyyMMdd");
		String pathSave		= pathBase + File.separator + folderName;
		
		File dirExists 	= new File(pathSave);
		if(!dirExists.exists()) {
			dirExists.mkdirs();
		}
		
		return folderName;
	}
	
	/**
	 * @param fileSrcName [파일 원본명]
	 * @return String
	 * 
	 * @since 2015-12-23
	 * <p>DESCRIPTION: 파일명(원본 파일명 + yyyyMMddHHmmss 형태) 얻기</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입 및 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	public static String getFileSveName(String fileSrcName) {
		
		String fileSveName	= fileSrcName;
		String fileExt		= fileSveName.substring(fileSveName.lastIndexOf("."));
		// fileSveName		= Datetime.getNow("yyyyMMddHHmmss") + "_" + UUID.randomUUID() + fileExt.toLowerCase();
		fileSveName			= UUID.randomUUID() + fileExt.toLowerCase();
		
		return fileSveName;
	}
	
	/**
	 * @param fileName [파일명]
	 * @return String
	 * 
	 * @since 2011-08-01
	 * <p>DESCRIPTION:</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	public static String getFileExtention(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
	}
}
