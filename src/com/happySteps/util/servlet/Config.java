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
 * File			: Config.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20231201124233][pluto@HAPPYSTEPS.COM][CREATE: Initial Release]
 */
package com.happySteps.util.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.happySteps.util.paging.PagingLoader;
import com.happySteps.util.property.PropertyLoader;

/**
 * @version 1.0.0
 * @author pluto@HAPPYSTEPS.COM
 * 
 * @since 2023-12-01
 * <p>DESCRIPTION:</p>
 * <p>IMPORTANT:</p>
 */
public class Config extends HttpServlet {
	
	/** Serial version UID */
	private static final long serialVersionUID = 20110801000000L;
	
	/** Logger */
	private static Logger logger = LoggerFactory.getLogger(Config.class);
	
	/**
	 * @param request [요청 서블릿]
	 * @param response [응답 서블릿]
	 * @throws ServletException
	 * @throws IOException
	 * 
	 * @since 2011-08-01
	 * <p>DESCRIPTION: Get은 Post로 처리</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입 및 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	/**
	 * @param request [요청 서블릿]
	 * @param response [응답 서블릿]
	 * @throws ServletException
	 * @throws IOException
	 * 
	 * @since 2011-08-01
	 * <p>DESCRIPTION: Post 처리</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입 및 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		init(getServletConfig());
	}
	
	/**
	 * @param config [설정 서블릿]
	 * @throws ServletException [서블릿 예외]
	 * 
	 * @since 2015-12-14
	 * <p>DESCRIPTION: web.xml의 Servlet 설정값으로 초기화</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입 및 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	public void init(ServletConfig config) throws ServletException {

		super.init(config);

		try {
			// Log4J
			String log4jFileName = this.getInitParameter("log4j");
			if ((log4jFileName != null) && (log4jFileName.length() > 0)) {
				
				try {
					PropertyConfigurator.configure(getServletContext().getRealPath(log4jFileName));
					logger.info("[" + this.getClass().getName() + ".init()] " + getServletContext().getRealPath(log4jFileName) + " is loaded.");
				}
				catch (Exception e) {
					logger.error("[" + this.getClass().getName() + ".init()] Can't load " + getServletContext().getRealPath(log4jFileName) + ".", e);
					throw new Exception("[" + this.getClass().getName() + ".init()] Can't load " + getServletContext().getRealPath(log4jFileName) + ".");
				}
			}
			
			// Property
			String propertyFileNames = this.getInitParameter("property");
			if ((propertyFileNames != null) && (propertyFileNames.length() > 0)) {
				String[] propertyFileName = propertyFileNames.split(",");
				for (int index = 0; index < propertyFileName.length; index++) {
					propertyFileName[index] = getServletContext().getRealPath(propertyFileName[index].trim());
				}
				PropertyLoader.getInstance().setProperties(propertyFileName);
			}
			
			// Page navigator
			String pageNavigatorFileName = this.getInitParameter("pageNavigator");
			if ((pageNavigatorFileName != null) && (pageNavigatorFileName.length() > 0))
				PagingLoader.getInstance().setPageNavigators(getServletContext().getRealPath(pageNavigatorFileName));
		}
		catch (Exception e) {
			logger.error("[" + this.getClass().getName() + ".init()] " + e.getMessage(), e);
		}
	}
}
