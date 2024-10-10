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
 * File			: PropertyLoader.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20231201124622][pluto@HAPPYSTEPS.COM][CREATE: Initial Release]
 */
package com.happySteps.util.property;

import java.io.FileInputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version 1.0.0
 * @author pluto@HAPPYSTEPS.COM
 * 
 * @since 2023-12-01
 * <p>DESCRIPTION:</p>
 * <p>IMPORTANT:</p>
 */
public class PropertyLoader {
	
	/** Logger */
	private static Logger logger					= LoggerFactory.getLogger(PropertyLoader.class);
	/** Property Loader */
	private static PropertyLoader propertyLoader	= null;
	/** Properties */
	private Properties properties					= null;
	
	/**
	 * @since 2011-08-01
	 * <p>DESCRIPTION: 생성자(Constructor)</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입 및 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	private PropertyLoader() {
		this.properties = new Properties();
	}
	
	/**
	 * @return synchronized
	 * 
	 * @since 2011-08-01
	 * <p>DESCRIPTION: 인스턴스 얻기(Get instance)</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입 및 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	public static synchronized PropertyLoader getInstance() {
		if (propertyLoader == null)
			propertyLoader = new PropertyLoader();
		return propertyLoader;
	}
	
	/**
	 * @param file [파일]
	 * @throws Exception [예외]
	 * 
	 * @since 2011-08-01
	 * <p>DESCRIPTION: 속성 지정(Set properties)</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입 및 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	public void setProperties(String[] file) throws Exception {
		
		this.properties.clear();
		
		for (int count = 0; count < file.length; count++) {
			FileInputStream fileInputStream = null;
			try {
				fileInputStream = new FileInputStream(file[count]);
				properties.load(fileInputStream);
				fileInputStream.close();
				logger.info("[" + this.getClass().getName() + ".setProperties()] " + file[count] + "is loaded.");
				
			}
			catch (Exception e) {
				logger.error("[" + this.getClass().getName() + ".setProperties()] Can't load " + file[count] + ": " + e.getMessage(), e);
			}
			finally {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			}
		}
	}
	
	/**
	 * @return Properties
	 * 
	 * @since 2011-08-01
	 * <p>DESCRIPTION: 속성 얻기(Get Properties)</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입 및 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	protected Properties getProperties() {
		return this.properties;
	}
}
