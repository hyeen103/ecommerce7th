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
 * File			: EmailDto.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20240124110713][pluto@HAPPYSTEPS.COM][CREATE: Initial Release]
 */
package com.happySteps.common.dto;

/**
 * @version 1.0.0
 * @author pluto@HAPPYSTEPS.COM
 * 
 * @since 2024-01-24
 * <p>DESCRIPTION:</p>
 * <p>IMPORTANT:</p>
 */
public class EmailDto {
	
	private String sender			= "";		// 발신자
	private String[] to				= null;		// 수신자들	[2021-06-25][pluto@brightsoft.co.kr][REPORT: 메일만 배열 사용 가능]
	private String[] cc				= null;		// 참조자들	[2021-06-25][pluto@brightsoft.co.kr][REPORT: 메일만 사용 가능]
	private String[] bc				= null;		// 숨은자들	[2021-06-25][pluto@brightsoft.co.kr][REPORT: 메일만 사용 가능]
	private String subject			= "";		// 제목		[2021-06-25][pluto@brightsoft.co.kr][REPORT: 메일 또는 LMS 등만 사용 가능]
	private String message			= "";		// 메시지
	
	
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String[] getTo() {
		return to;
	}
	public void setTo(String[] to) {
		this.to = to;
	}
	public String[] getCc() {
		return cc;
	}
	public void setCc(String[] cc) {
		this.cc = cc;
	}
	public String[] getBc() {
		return bc;
	}
	public void setBc(String[] bc) {
		this.bc = bc;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
