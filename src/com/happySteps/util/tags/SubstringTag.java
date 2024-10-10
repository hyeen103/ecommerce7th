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
 * File			: SubstringTag.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20240105150157][pluto@HAPPYSTEPS.COM][CREATE: Initial Release]
 */
package com.happySteps.util.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version 1.0.0
 * @author pluto@plutozone.com
 * 
 * @since 2015-12-14
 * <p>DESCRIPTION: 서브스트링 태그 유틸리티 클래스</p>
 * <p>IMPORTANT:</p>
 */
public class SubstringTag extends TagSupport {
	
	/** Serial version UID */
	private static final long serialVersionUID = 20150225000004L;
	
	/** Logger */
	private static Logger logger = LoggerFactory.getLogger(SubstringTag.class);

	private String text = "";
	private int length;
	
	/**
	 * @throws JspException [JSP 예외]
	 * 
	 * @since 2015-12-14
	 * <p>DESCRIPTION: End Tag 처리</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입 및 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	public int doEndTag() throws JspException {
		
		String returnString = "";
		
		try {
			String value	= getText();
			int size		= getLength();
			
			int byteLen = 0;
			int len = value.length();
			int blen = value.getBytes().length;
			
			if (blen <= size) {
				returnString = value;
			}
			else {
				for (int i = 0; i < len; i++) {
					String temp = value.charAt(i) + "";
					byteLen += temp.getBytes().length;
					
					if (byteLen <= size) {
						returnString = returnString + temp;
					}
					else {
						returnString += "...";
						break;
					}
				}
			}
			
			pageContext.getOut().print(returnString);
			
		} catch (Exception e) {
			logger.error("[" + this.getClass().getName() + ".doEndTag()] Error occurred while process 'dotcom:page' tag: " + e.getMessage(), e);
		}
		
		return SKIP_BODY;
	}
	
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}