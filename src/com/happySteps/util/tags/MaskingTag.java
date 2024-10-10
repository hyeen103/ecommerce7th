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
 * File			: MaskingTag.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20240105150210][pluto@HAPPYSTEPS.COM][CREATE: Initial Release]
 */
package com.happySteps.util.tags;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version 1.0.0
 * @author pluto@plutozone.com
 * 
 * @since 2017-04-20
 * <p>DESCRIPTION: 마스킹 태그 유틸리티 클래스</p>
 * <p>IMPORTANT:</p>
 */
public class MaskingTag extends TagSupport {
	
	/** Serial version UID */
	private static final long serialVersionUID = 20170420174900L;
	
	/** Logger */
	private static Logger logger = LoggerFactory.getLogger(MaskingTag.class);
	
	/** Masking Count */
	private int count = 0;
	
	/** Masking Letter */
	private String letter = "";
	
	/** Mode */
	private String mode	 = "";
	
	/** Text */
	private String text = "";
	
	
	/**
	 * @throws JspException [JSP 예외]
	 * 
	 * @since 2015-12-14
	 * <p>DESCRIPTION: 입력 받은 문자열을 원하는 갯수만큼 지정한 문자로 좌측/우측/랜덤하게 치환한다.</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입 및 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	public int doEndTag() throws JspException {
		
		String returnString = "";
		
		try {
			int countValue		= getCount();
			String letterValue	= getLetter();
			String modeValue	= getMode();
			String textValue	= getText();
			
			int textLength = textValue.length();
				
			String letterAll	= "";
			for (int loop = 0; loop < countValue; loop++) {
				letterAll += letterValue;
			}
			
			if (modeValue.equals("left")) {
				if (textLength >= countValue) {
					
					returnString = textValue.substring(countValue, textLength);
					returnString = letterAll + returnString;
				}
				else {
					returnString = letterAll;
				}
			}
			else if (modeValue.equals("right")) {
				if (textLength >= countValue) {
					
					returnString = textValue.substring(0, textLength - countValue);
					returnString = returnString + letterAll;
				}
				else {
					returnString = letterAll;
				}
			}
			else {
				Random random = new Random();
				List<Integer> positionList = new ArrayList<Integer>();
				for (int loop = 0; loop < countValue; loop++) {
					
					if (loop >= textLength)
						break;
					while (true) {
						Integer next = random.nextInt(textLength);
						if (!positionList.contains(next)) {
							positionList.add(next);
							break;
						}
					}
				}
				
				for (int loop = 0; loop < textLength; loop++) {
					if (positionList.contains(loop)) {
						returnString = returnString + letterValue;
					}
					else {
						returnString = returnString + textValue.charAt(loop);
					}
				}
			}
			
			pageContext.getOut().print(returnString);
		}
		catch (Exception e) {
			logger.error("[" + this.getClass().getName() + ".doEndTag()] Error occurred while process 'dotcom:page' tag: " + e.getMessage(), e);
		}
		
		return SKIP_BODY;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getLetter() {
		return letter;
	}
	public void setLetter(String letter) {
		this.letter = letter;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
}