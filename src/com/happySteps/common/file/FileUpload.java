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
 * File			: FileUpload.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20240808130001][pluto@HAPPYSTEPS.COM][CREATE: Initial Release]
 *				: [20240923173500][pluto@HAPPYSTEPS.COM][REPORT: MultipartFile does not support files with size 0, so Controller needs to handle files with size 0.]
 */
package com.happySteps.common.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.happySteps.util.Datetime;
import com.happySteps.common.dto.FileUploadDto;

/**
 * @version 1.0.0
 * @author pluto@HAPPYSTEPS.COM
 * 
 * @since 2024-08-08
 * <p>DESCRIPTION:</p>
 * <p>IMPORTANT:</p>
 */
public class FileUpload {
	
	/** Logger */
	private static Logger logger = LoggerFactory.getLogger(FileUpload.class);
	
	/**
	 * @param fileUploadDto [파일 업로드 빈(Bean)]
	 * @param pathBase [베이스 경로]
	 * @param maxSize [최대 사이즈]
	 * @param allowedExt [허용 확장자]
	 * @param count [파일 갯수]
	 * @return LinkedList
	 * 
	 * @since 2015-12-23
	 * <p>DESCRIPTION: 파일 빈(Bean, fileUploadDto), 루트경로(pathBase), 최대크기(maxSize), 허용된 확장자(allowedExt) 및 개수(count) 형태로 업로드</p>
	 * <p>IMPORTANT: 특정 파일(예: 멤버십 카드 이미지)일 경우 JSP/Java(Bean)에서 INPUT NAME으로 수정 제어(등록/수정/삭제 등)할 것</p>
	 * <p>EXAMPLE:<br>
	 * 업로드할 파일이 4개인 경우<br>
	 * 1) String pathBase		= "backoffice.upload.project";<br>
	 * 2) String maxSize		= "backoffice.file.max10MB";<br>
	 * 3) String allowedExt 	= "backoffice.file.extension.doc";<br>
	 * <br>
	 * LinkedList uploadResult 	= FileUpload.upload(fileUploadDto, pathBase, maxSize, allowedExt);<br>
	 * String resultID			= (String)((Hashtable)uploadResult.getLast()).get("resultID");<br>
	 * if (!resultID.equals("success")) {<br>
	 * 		// Failure<br>
	 * }<br>
	 * else {<br>
	 * 	Hashtable hashtable				= (Hashtable)uploadResult.getLast();<br>
	 * 	String fileSrcName1				= (String)hashtable.get("INPUT_NAME1_fileSrcName");<br>
	 * 	String fileSveNameRelative1		= (String)hashtable.get("INPUT_NAME1_fileSveNameRelative");<br>
	 * 	String fileSveSize1				= (String)hashtable.get("INPUT_NAME1_fileSveSize");<br>
	 * 	String fileSrcName2				= (String)hashtable.get("INPUT_NAME2_fileSrcName");<br>
	 * 	String fileSveNameRelative2		= (String)hashtable.get("INPUT_NAME2_fileSveNameRelative");<br>
	 * 	String fileSveSize2				= (String)hashtable.get("INPUT_NAME2_fileSveSize");<br>
	 * 	String fileSrcName3				= (String)hashtable.get("INPUT_NAME3_fileSrcName");
	 * 	String fileSveNameRelative3		= (String)hashtable.get("INPUT_NAME3_fileSveNameRelative");<br>
	 * 	String fileSveSize3				= (String)hashtable.get("INPUT_NAME3_fileSveSize");<br>
	 * 	String fileSrcName4				= (String)hashtable.get("INPUT_NAME4_fileSrcName");<br>
	 * 	String fileSveNameRelative4		= (String)hashtable.get("INPUT_NAME4_fileSveNameRelative");<br>
	 * 	String fileSveSize4				= (String)hashtable.get("INPUT_NAME4_fileSveSize");<br>
	 * }<br>
	 * </p>
	 */
	public static LinkedList<Object> upload(FileUploadDto fileUploadDto, String pathBase, String maxSize, String allowedExt, int count) {

		LinkedList<Object> resultList	= new LinkedList<Object>();
		LinkedList<Object> savedList	= new LinkedList<Object>();
		Hashtable<String, String> ht	= new Hashtable<String, String>();	
		
		// Default
		ht.put("resultID"			, "success");
		ht.put("fileSrcName"		, "");
		ht.put("fileSveNameRelative", "");
		ht.put("fileSveSize"		, "0");
		resultList.add(ht);
		
		String resultID				= "success";
		String fileSrcName			= "";
		String fileSveName			= "";
		String fileSveNameRelative	= "";
		long fileSveSize			= 0;
		
		try {
			
			boolean isExtension		= false;
			boolean isSize			= false;
			
			List<MultipartFile> files 		= fileUploadDto.getFiles();
			
			if (null != files && files.size() > 0 && files.size() == count) {
				
				for (MultipartFile multipartFiles : files) {
					
					// Initialize
					resultID			= "success";
					fileSrcName			= "";
					fileSveName			= "";
					fileSveNameRelative	= "";
					fileSveSize			= 0;
					
					// File exist
					if (!multipartFiles.isEmpty()) {
						
						fileSrcName = multipartFiles.getOriginalFilename();
						fileSveName = getfileSveName(fileSrcName);
						fileSveSize = multipartFiles.getSize();
						
						isExtension	= isExtension(fileSrcName, allowedExt);
						isSize 		= fileSveSize > Long.parseLong(maxSize) ? false : true;
						
						// Check Extension
						if (!isExtension) {
							ht.put("resultID", "허용되지 않은 확장자입니다!");
							ht.put("fileSrcName", fileSrcName);
							ht.put("fileSveNameRelative", fileSveNameRelative);
							ht.put("fileSveSize", Long.toString(fileSveSize));
							
							resultList.add(ht);
							break;
						}
						// Check Size
						else if (!isSize) {
							ht.put("resultID", "파일 크기를 초과하였습니다!");
							ht.put("fileSrcName", fileSrcName);
							ht.put("fileSveNameRelative", fileSveNameRelative);
							ht.put("fileSveSize", Long.toString(fileSveSize));
							
							resultList.add(ht);
							break;
						}
						else {
							String folderName		= getFolderName(pathBase);
							fileSveNameRelative		= folderName + File.separator + fileSveName;
							
							InputStream is 			= multipartFiles.getInputStream();
							FileOutputStream fos 	= new FileOutputStream(pathBase + File.separator + fileSveNameRelative);
							FileCopyUtils.copy(is, fos);
							is.close();
							fos.close();
							
							ht.put("resultID", "success");
							ht.put(multipartFiles.getName() + "_fileSrcName", fileSrcName);
							ht.put(multipartFiles.getName() + "_fileSveNameRelative", fileSveNameRelative);
							ht.put(multipartFiles.getName() + "_fileSveSize",  Long.toString(fileSveSize));
							
							resultList.add(ht);
							savedList.add(fileSveNameRelative);
						}
					}
					// [2024-09-23][pluto@HAPPYSTEPS.COM][REPORT: MultipartFile does not support files with size 0, so Controller needs to handle files with size 0.]
					// File is not attached or size is 0
					else {
						ht.put("resultID", "success");
						ht.put(multipartFiles.getName() + "_fileSrcName", fileSrcName);
						ht.put(multipartFiles.getName() + "_fileSveNameRelative", fileSveNameRelative);
						ht.put(multipartFiles.getName() + "_fileSveSize",  Long.toString(fileSveSize));
						resultList.add(ht);
					}
					// logger.debug("ht.size()=" + ht.size());
				}
			}
		}
		catch (Exception e) {
			logger.error("[com.plutozone.common.file.FileUpload.upload()] " + e.getMessage(), e);
		}
		finally {
			if (!resultID.equals("success") ) {
				for (int i = 0; i < savedList.size(); i++) {
					deleteFile(pathBase, (String)savedList.get(i));
				}
			}
		}
		
		return resultList;
	}
	
	/**
	 * @param pathBase [베이스 경로]
	 * @return String
	 * 
	 * @since 2015-12-23
	 * <p>DESCRIPTION: 폴더명(yyyy / MM / dd / HH 형태) 얻기</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입 및 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	public static String getFolderName(String pathBase) {
		
		String folderName	= Datetime.getNow("yyyy")
								+ File.separator + Datetime.getNow("MM")
								+ File.separator + Datetime.getNow("dd")
								+ File.separator + Datetime.getNow("HH");
								
		// String folderName	= Datetime.getNow("yyyyMMddHH");
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
	public static String getfileSveName(String fileSrcName) {
		
		String fileSveName	= fileSrcName;
		String fileExt		= fileSveName.substring(fileSveName.lastIndexOf("."));
		// fileSveName		= Datetime.getNow("yyyyMMddHHmmss") + "_" + UUID.randomUUID() + fileExt.toLowerCase();
		fileSveName			= UUID.randomUUID() + fileExt.toLowerCase();
		
		return fileSveName;
	}	
	
	/**
	 * @param fileSrcName [파일 원본명]
	 * @param allowedExt [허용 확장자]
	 * @return boolean
	 * 
	 * @since 2015-12-23
	 * <p>DESCRIPTION: 파일 확장자 허용 여부</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입 및 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	public static boolean isExtension(String fileSrcName, String allowedExt) {
		String extension		= getFileExtention(fileSrcName);
		boolean isExtension		= false;
		int nExtension			= allowedExt.indexOf(extension);
		
		if (nExtension >= 0) {
			isExtension = true;
		}
		return isExtension;
	}
	
	/**
	 * @param fileSrcName [파일 원본명]
	 * @return boolean
	 * 
	 * @since 2015-12-23
	 * <p>DESCRIPTION: 파일 확장자 얻기</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입 및 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	public static String getFileExtention(String fileSrcName) {
		return fileSrcName.substring(fileSrcName.lastIndexOf(".") + 1).toLowerCase();
	}
	
	/**
	 * @param pathBase [베이스 경로]
	 * @param fileSveNameRelative [저장 파일명]
	 * @return boolean
	 * 
	 * @since 2015-12-23
	 * <p>DESCRIPTION: 파일 삭제</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입 및 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	public static boolean deleteFile (String pathBase, String fileSveNameRelative) {
		
		File file = new File(pathBase + File.separator + fileSveNameRelative);
		
		if(file.delete()) {
			return true;
		}
		else {
			return false;
		}
	}
}