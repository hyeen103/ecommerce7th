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
 * File			: EmailCmpn.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20240124110510][pluto@HAPPYSTEPS.COM][CREATE: Initial Release]
 */
package com.happySteps.common.component;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import  com.happySteps.common.dto.EmailDto;

/**
 * @version 1.0.0
 * @author pluto@HAPPYSTEPS.COM
 * 
 * @since 2024-01-24
 * <p>DESCRIPTION:</p>
 * <p>IMPORTANT:</p>
 */
@Component("com.happySteps.common.component.EmailCmpn")
public class EmailCmpn {
	
	/** Logger */
	private static Logger logger = LoggerFactory.getLogger(EmailCmpn.class);
	
	@Autowired
	private MessageSourceAccessor dynamicProperties;
	
	@Autowired
	private JavaMailSender mailSender;

	/**
	 * @param emailDto [메시지 빈(Bean)]
	 * @throws Exception
	 * @return Boolean
	 * 
	 * @since 2016-05-26
	 * <p>DESCRIPTION:</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	public Boolean send(EmailDto emailDto) throws Exception {
		
		// 발신자 메일(네이버는 필수 설정이 필요하며 SMTP 계정과 동일 메일만 허용 vs. 구글은 설정된 SMTP 계정으로만 자동 설정됨)
		emailDto.setSender(dynamicProperties.getMessage("email.sender.mail", "[UNDEFINED]"));
		boolean result = false;
		
		try {
			MimeMessage mimeMessage		= mailSender.createMimeMessage();
			MimeMessageHelper helper	= new MimeMessageHelper(mimeMessage, false, "UTF-8");
			
			helper.setFrom(new InternetAddress(emailDto.getSender(), dynamicProperties.getMessage("email.sender.name", "[UNDEFINED]")));
			helper.setTo(emailDto.getTo());
			helper.setSubject(emailDto.getSubject());
			helper.setText(emailDto.getMessage(), true);
			
			// 실제 발송
			if (dynamicProperties.getMessage("email.sender.isSending", "[UNDEFINED]").equals("true")) {
				mailSender.send(mimeMessage);
				// 발송 로그: [서비스 일련번호][메일 수신자(0)][메일 제목][상용 vs. 개발]
				logger.info("[COM.PLUTOZONE.MESSAGE.EMAIL] "
						+ "[" + emailDto.getTo()[0] + "...]"
						+ "[" + emailDto.getSubject() + "]"
						+ "[N/A]" + "\n");
			}
			else logger.info("[COM.PLUTOZONE.MESSAGE.EMAIL] "
					+ "[" + emailDto.getTo()[0] + "...]"
					+ "[" + emailDto.getSubject() + "]"
					+ "[Test Success!]" + "\n");
			
			result = true;
			
		}
		catch (Exception e) {
			logger.error("[" + this.getClass().getName() + ".send()] " + e.getMessage(), e);
		}
		
		return result;
	}

}
