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
 * File			: LoginWeb.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20240618160606][pluto@HAPPYSTEPS.COM][CREATE: Initial Release]
 */
package com.happySteps.front.login.controller;

import java.util.Properties;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.happySteps.front.common.Common;
import com.happySteps.front.login.dto.LoginDto;
import com.happySteps.front.login.service.LoginSrvc;
import com.happySteps.front.member.dto.MemberDto;
import com.happySteps.util.Datetime;
import com.happySteps.util.security.HSwithSHA;
import com.happySteps.util.security.SKwithAES;
import com.happySteps.util.servlet.Request;

/**
 * @version 1.0.0
 * @author pluto@HAPPYSTEPS.COM
 * 
 * @since 2024-06-18
 * <p>DESCRIPTION: 로그인 컨트롤러</p>
 * <p>IMPORTANT:</p>
 */
@Controller("com.happySteps.front.login.controller.LoginWeb")
public class LoginWeb extends Common {
	
	@Autowired
	Properties staticProperties;
	
	@Inject
	LoginSrvc loginSrvc;
	
	
	
	/** Logger */
	private static Logger logger = LoggerFactory.getLogger(LoginWeb.class);
	
	/**
	 * @param request [요청 서블릿]
	 * @param response [응답 서블릿]
	 * @return ModelAndView
	 * 
	 * @since 2024-06-21
	 * <p>DESCRIPTION:</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	@RequestMapping(value = "/front/login/logout.web")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView("redirect:/error.web");
		
		try {
			HttpSession session = request.getSession(false);
			
			// [2024-10-07][hyeen103@HAPPYSTEPS.COM][TODO: 세션 만료 시 처리 추가]
			String name		= (String) session.getAttribute("NAME");
			String dt_login	= (String) session.getAttribute("DT_LOGIN");
			session.invalidate();
			
			request.setAttribute("script"	, "alert('" + dt_login + "에 로그인한 " + name + "님 안녕히 가세요.')");
			request.setAttribute("redirect"	, "/front/");
			
			mav.setViewName("forward:/servlet/result.web");
		}
		catch (Exception e) {
			logger.error("[" + this.getClass().getName() + ".logout()] " + e.getMessage(), e);
		}
		finally {}
		
		return mav;
	}
	
	/**
	 * @param request [요청 서블릿]
	 * @param response [응답 서블릿]
	 * @return ModelAndView
	 * 
	 * @since 2024-06-21
	 * <p>DESCRIPTION:</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	@RequestMapping(value = "/front/login/loginForm.web")
	public ModelAndView loginForm(HttpServletRequest request, HttpServletResponse response, String url) {
		
		ModelAndView mav = new ModelAndView("redirect:/error.web");
		
		try {
			mav.addObject("url", url);
			
			mav.setViewName("front/login/loginForm");
		}
		catch (Exception e) {
			logger.error("[" + this.getClass().getName() + ".loginForm()] " + e.getMessage(), e);
		}
		finally {}
		
		return mav;
	}
	
	/**
	 * @param request [요청 서블릿]
	 * @param response [응답 서블릿]
	 * @param loginDto [로그인 빈]
	 * @return ModelAndView
	 * 
	 * @since 2024-06-18
	 * <p>DESCRIPTION:</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	@RequestMapping(value = "/front/login/loginProc.web", method = RequestMethod.POST)
	public ModelAndView loginProc(HttpServletRequest request, HttpServletResponse response, LoginDto loginDto) {
		
		ModelAndView mav = new ModelAndView("redirect:/error.web");
		
		try {
			
			/** [2024-07-01][pluto@HAPPYSTEPS.COM][TODO: 추후 로그인 접속 정보 개발 예정]
			LoginAccessDto loginAccessDto = new LoginAccessDto();
			
			loginAccessDto.setDt_login(Datetime.getNow("yyyy-MM-dd HH:mm:ss"));
			loginAccessDto.setIp(Request.getRemoteAddr(request));
			loginAccessDto.setOs(Request.getOs(request));
			loginAccessDto.setAgent(Request.getBrowser(request));
			loginAccessDto.setRefer(Strings.getString(request.getHeader("referer")));
			
			logger.debug("---------------------------------");
			logger.debug(request.getHeader("User-Agent"));
			logger.debug("---------------------------------");
			logger.debug(loginAccessDto.getDt_login());
			logger.debug(loginAccessDto.getIp());
			logger.debug(loginAccessDto.getOs());
			logger.debug(loginAccessDto.getAgent());
			logger.debug(loginAccessDto.getRefer());
			logger.debug("---------------------------------");
			*/
			
			//logger.debug("암호화 전: " + loginDto.getEmail());
			//logger.debug("암호화 전: " + loginDto.getPasswd());
			
			// 대칭키 암호화(AES-256)
			String staticKey	= staticProperties.getProperty("front.enc.user.aes256.key", "[UNDEFINED]");
			SKwithAES aes		= new SKwithAES(staticKey);
			
			loginDto.setId(aes.encode(loginDto.getId()));
			//logger.debug("암호화 후(Email): " + loginDto.getEmail());
			
			MemberDto memberDto = loginSrvc.exist(loginDto);
			
			if (memberDto != null
					&& HSwithSHA.encode(loginDto.getPasswd()).equals(memberDto.getPasswd())) {
				//logger.debug("이메일과 암호가 일치함");
				
				/** 최종 접속한 IP와 시간을 업데이트 */
				memberDto.setLast_ip(Request.getRemoteAddr(request));
				
				if (!loginSrvc.updateLast(memberDto))
					logger.info("[ERROR] 최종 접속한 IP와 시간을 업데이트에 실패하였습니다!");
				
				/** 정상적인 회원일 경우 세션에 이름과 아이디를 저장 */
				HttpSession session = request.getSession(true);
				//logger.debug(memberDto.getMbr_nm());
				//logger.debug(memberDto.getSeq_mbr() + "");
				//logger.debug(memberDto.getPasswd());
				//logger.debug("복호화 후(Name): " + aes.decode(memberDto.getMbr_nm()));
				//logger.debug("복호화 후(Email): " + aes.decode(memberDto.getEmail()));
				session.setAttribute("SEQ_MBR", Integer.toString(memberDto.getSeq_mbr()));
				session.setAttribute("NAME", aes.decode(memberDto.getMbr_nm()));
				session.setAttribute("ID", aes.decode(memberDto.getId()));
				session.setAttribute("DT_LOGIN", Datetime.getNow("yyyy-MM-dd HH:mm:ss"));
				
				request.setAttribute("script", "alert('" + session.getAttribute("NAME")
												+ "님이 "
												+ session.getAttribute("DT_LOGIN")
												+ "에 로그인하였습니다."
												+ "')");
			}
			else {
				// logger.debug("해당 회원이 없거나 암호가 일치하지 않음");
				request.setAttribute("script", "alert('아이디와 비밀번호를 확인하세요!')");
			}
			
			request.setAttribute("redirect"	, "/front/");
			
			mav.setViewName("forward:/servlet/result.web");
		}
		catch (Exception e) {
			logger.error("[" + this.getClass().getName() + ".loginProc()] " + e.getMessage(), e);
		}
		finally {}
		
		return mav;
	}
}