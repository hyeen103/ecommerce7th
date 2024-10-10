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
 * File			: SystemCtrl.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20231128164312][pluto@HAPPYSTEPS.COM][CREATE: Initial Release]
 */
package com.happySteps.common.controller;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.happySteps.common.dto.SystemErrorDto;
import com.happySteps.util.Datetime;
import com.happySteps.util.servlet.Request;

/**
 * @version 1.0.0
 * @author pluto@HAPPYSTEPS.COM
 * 
 * @since 2023-11-28
 * <p>DESCRIPTION:</p>
 * <p>IMPORTANT:</p>
 */
@Controller("com.happySteps.common.controller.SystemCtrl")
public class SystemCtrl {
	
	/** Logger */
	private static Logger logger = LoggerFactory.getLogger(SystemCtrl.class);
	
	@Autowired
	Properties staticProperties;
	
	/**
	 * @param request [요청 서블릿]
	 * @param response [응답 서블릿]
	 * @param systemErrorDto [시스템 에러 빈(Bean)]
	 * @return ModelAndView
	 * 
	 * @since 2015-12-22
	 * <p>DESCRIPTION: 시스템 에러 발생 시 로깅 후 에러 페이지로 이동</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입 및 형식을 만족하여야 한다.<br>web.xml에 에러 페이지가 설정되어야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	@RequestMapping(value = "/error.web")
	public ModelAndView errorProcWeb(HttpServletRequest request, HttpServletResponse response, SystemErrorDto systemErrorDto) {
		
		ModelAndView mav = new ModelAndView();
		
		try {
			
			if (systemErrorDto.getCode().equals("400")) {
				systemErrorDto.setCode_desc("BAD REQUEST");
			}
			else if (systemErrorDto.getCode().equals("401")) {
				systemErrorDto.setCode_desc("UNAUTHORIZED");
			}
			else if (systemErrorDto.getCode().equals("403")) {
				systemErrorDto.setCode_desc("ACCESS DENITED");
			}
			else if (systemErrorDto.getCode().equals("404")) {
				systemErrorDto.setCode_desc("FILE NOT FOUND");
			}
			else if (systemErrorDto.getCode().equals("405")) {
				systemErrorDto.setCode_desc("METHOD NOT ALLOWED");
			}
			else if (systemErrorDto.getCode().equals("408")) {
				systemErrorDto.setCode_desc("REQUEST TIMEOUT");
			}
			else if (systemErrorDto.getCode().equals("415")) {
				systemErrorDto.setCode_desc("FORMAT NOT SUPPORTE");
			}
			else if (systemErrorDto.getCode().equals("500")) {
				systemErrorDto.setCode_desc("INTERNAL SERVER ERROR");
			}
			else if (systemErrorDto.getCode().equals("503")) {
				systemErrorDto.setCode_desc("SERVICE UNAVAILABLE");
			}
			else if (systemErrorDto.getCode().equalsIgnoreCase("exception")) {
				systemErrorDto.setCode_desc("EXCEPTION");
			}
			else if (systemErrorDto.getCode().equalsIgnoreCase("servletException")) {
				systemErrorDto.setCode_desc("SERVLET EXCEPTION");
			}
			else {
				systemErrorDto.setCode_desc("UNKNOWN");
			}
			
			logger.error("# REGION ***************************" + staticProperties.getProperty("common.logging.system.error", "[UNDEFINED]") + "***************************");
			logger.error("# CODE: " + systemErrorDto.getCode());
			logger.error("# DESC: " + systemErrorDto.getCode_desc());
			logger.error("# IP  : " + Request.getRemoteAddr(request, staticProperties.getProperty("common.server.web", "[UNDEFINED]")));
			logger.error("# TIME: " + Datetime.getNow("yyyy-MM-dd HH:mm:ss"));
			logger.error("# END REGION ***********************" + staticProperties.getProperty("common.logging.system.error", "[UNDEFINED]") + "***************************\n");
			
			mav.setViewName("common/error-web");
		}
		catch (Exception e) {
			logger.error("[" + this.getClass().getName() + ".errorProcWeb()] " + e.getMessage(), e);
		}
		finally {}
		
		return mav;
	}
	
	/**
	 * @param request [요청 서블릿]
	 * @param response [응답 서블릿]
	 * @return ModelAndView
	 * 
	 * @since 2015-12-22
	 * <p>DESCRIPTION: 시스템 접근 반려 발생 시 로깅 후 반려 페이지로 이동</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입 및 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	@RequestMapping(value = "/reject.web")
	public ModelAndView rejectProcWeb(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView("redirect:/error.web");
		
		try {
			// **************************************************************************
			// Mode
			// **************************************************************************
			((HttpServletRequest)request).setAttribute("MODE", staticProperties.getProperty("common.mode", "[UNDEFINED]"));
			
			mav.setViewName("common/reject-web");
		}
		catch (Exception e) {
			logger.error("[" + this.getClass().getName() + ".rejectProcWeb()] " + e.getMessage(), e);
		}
		finally {}
		
		return mav;
	}
}
