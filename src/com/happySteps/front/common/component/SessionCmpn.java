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
 * File			: SessionCmpn.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20240806124928][pluto@HAPPYSTEPS.COM][CREATE: Initial Release]
 */
package com.happySteps.front.common.component;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.happySteps.util.Datetime;
import com.happySteps.util.servlet.Request;

/**
 * @version 1.0.0
 * @author pluto@HAPPYSTEPS.COM
 * 
 * @since 2024-08-06
 * <p>DESCRIPTION:</p>
 * <p>IMPORTANT:</p>
 */
@Component("com.happySteps.front.common.component.SessionCmpn")
public class SessionCmpn {
	
	/** Logger */
	private static Logger logger = LoggerFactory.getLogger(SessionCmpn.class);
	
	@Autowired
	private Properties staticProperties;
	
	/**
	 * @param request [요청 서블릿]
	 * @return boolean
	 * 
	 * @since 2017-07-03
	 * <p>DESCRIPTION:</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	public boolean isSession(HttpServletRequest request) {
		
		boolean isExist = true;
		
		String fullURL = Request.getFullURL(request);
		
		try {
			String seq_user		= Request.getSession(request, "SEQ_MBR", "", false);
			String dt_login		= Request.getSession(request, "DT_LOGIN", "", false);
			String ip			= Request.getRemoteAddr(request, staticProperties.getProperty("common.server.web", "[UNDEFINED]"));
			
			String prefixSession = staticProperties.getProperty("front.logging.session", "[UNDEFINED]");
			
			if (seq_user == null || seq_user.equals("")
					|| dt_login == null || dt_login.equals("")) {
				
				logger.error("# REGION ***************************" + prefixSession + "***************************");
				logger.error("# DENY DESC: SESSION DOES NOT EXIST");
				logger.error("# IP       : " + ip);
				logger.error("# URL      : " + fullURL);
				logger.error("# TIME     : " + Datetime.getNow("yyyy-MM-dd HH:mm:ss"));
				logger.error("# END REGION ***********************" + prefixSession + "***************************\n");
				
				isExist = false;
			}
		}
		catch (Exception e) {
			logger.error("[" + this.getClass().getName() + ".isSession()] " + e.getMessage(), e);
		}
		
		return isExist;
	}
}
