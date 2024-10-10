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
 * File			: PagingHandler.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20231201124722][pluto@HAPPYSTEPS.COM][CREATE: Initial Release]
 */
package com.happySteps.util.paging;

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
public class PagingHandler {
	
	/** Logger */
	private static Logger logger = LoggerFactory.getLogger(PagingHandler.class);
	
	/** Links per page default */
	private final static int LINE_PER_PAGE = 10;
	
	/**
	 * @param currentPage [현재 페이지]
	 * @param linePerPage [페이지 당 목록 수]
	 * @param totalLine [전체 목록 수]
	 * @param scriptFunction [스크립트 함수명]
	 * @throws Exception [예외]
	 * @return String
	 * 
	 * @since 2015-09-02
	 * <p>DESCRIPTION: 지정한 형태로 페이징</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입과 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	public static String getPageNavigator(int currentPage, int linePerPage, long totalLine, String scriptFunction) throws Exception {
		return getPageNavigator(null, currentPage, linePerPage, totalLine, null, scriptFunction, null);
	}
	
	/**
	 * @param currentPage [현재 페이지]
	 * @param linePerPage [페이지 당 목록 수]
	 * @param totalLine [전체 목록 수]
	 * @param pageURL [페이지 URL]
	 * @param pageString [페이지 문자열]
	 * @throws Exception [예외]
	 * @return String 
	 * 
	 * @since 2015-09-02
	 * <p>DESCRIPTION: 지정한 형태로 페이징</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입과 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	public static String getPageNavigator(int currentPage, int linePerPage, long totalLine, String pageURL, String pageString) throws Exception {
		return getPageNavigator(null, currentPage, linePerPage, totalLine, pageURL, pageString);
	}
	
	/**
	 * @param styleId [스타일 ID]
	 * @param currentPage [현재 페이지]
	 * @param linePerPage [페이지 당 목록 수]
	 * @param totalLine [전체 목록 수]
	 * @param scriptFunction [스크립트 함수]
	 * @throws Exception [예외]
	 * @return String
	 * 
	 * @since 2015-09-02
	 * <p>DESCRIPTION: 지정한 형태로 페이징</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입과 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	public static String getPageNavigator(String styleId, int currentPage, int linePerPage, long totalLine, String scriptFunction) throws Exception {
		return getPageNavigator(styleId, currentPage, linePerPage, totalLine, null, scriptFunction, null);
	}
	
	/**
	 * @param styleId [스타일 ID]
	 * @param currentPage [현재 페이지]
	 * @param linePerPage [페이지 당 목록 수]
	 * @param totalLine [전체 목록 수]
	 * @param pageURL [페이지 URL]
	 * @param pageString [페이지 문자열]
	 * @throws Exception [예외]
	 * @return String
	 * 
	 * @since 2015-09-02
	 * <p>DESCRIPTION: 지정한 형태로 페이징</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입과 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	public static String getPageNavigator(String styleId, int currentPage, int linePerPage, long totalLine, String pageURL, String pageString) throws Exception {
		
		int pos1, pos2;
		pos1 = pageURL.indexOf(pageString + "=");
		
		if (pos1 > -1) {
			pos2 = pageURL.indexOf("&", pos1);
			if (pos2 > -1)
				pageURL = pageURL.substring(0, pos1) + pageURL.substring(pos2 + 1);
			else
				pageURL = pageURL.substring(0, pos1);
		}
		
		if ((pageURL.length() > 1) && (pageURL.substring(pageURL.length() - 1, pageURL.length()).equals("&")))
			pageURL = pageURL.substring(0, pageURL.length() - 1);
		
		pos1 = pageURL.indexOf("?");
		if (pos1 < 0)
			pageURL = pageURL + "?";
		
		String pages = getPageNavigator(styleId, currentPage, linePerPage, totalLine, pageURL, null, pageString);
		
		return pages.replaceAll("\\?&", "?");
	}
	
	/**
	 * @param styleId [스타일 ID]
	 * @param currentPage [현재 페이지]
	 * @param linePerPage [페이지 당 목록 수]
	 * @param totalLine [전체 목록 수]
	 * @param pageURL [페이지 URL]
	 * @param scriptFunction [스크립트 함수]
	 * @param pageString [페이지 문자열]
	 * @throws Exception [예외]
	 * @return String
	 * 
	 * @since 2015-09-02
	 * <p>DESCRIPTION: 지정한 형태로 페이징 문자열 얻기</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입과 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	private static String getPageNavigator(String styleId, int currentPage, int linePerPage, long totalLine, String pageURL, String scriptFunction, String pageString) throws Exception {
		
		if (styleId == null)
			styleId = "default";
		
		PagingLoader loader		= PagingLoader.getInstance();
		PagingInfo pageNavInfo	= loader.getPagingInfo(styleId);
		
		
		if (pageNavInfo == null) {
			logger.error("[com.plutozone.util.paging.PagingHandler.getPageNavigator()] Can not found page style '" + styleId + "'.");
			return null;
		}
		
		StringBuffer pages = new StringBuffer();
		
		try {
			
			int totalPages = (int)Math.ceil((double)totalLine / (double)linePerPage);
		
			if (totalPages < 1)
				totalPages = 1;
			
			if ((currentPage > 0) && (currentPage <= totalPages) && (linePerPage > 0) && (linePerPage < 101)) {
				int pageCount2 = (int)Math.ceil((double)currentPage / (double)LINE_PER_PAGE) * LINE_PER_PAGE;
				int pageCount1 = pageCount2 - LINE_PER_PAGE + 1;
				
				if (pageCount2 > totalPages)
					pageCount2 = totalPages;
				
				pages.append(pageNavInfo.getContainerOpen());
		
				if (currentPage == 1)
					pages.append(pageNavInfo.getButtonFirst());
				else {
					if (pageURL != null)
						pages.append(pageNavInfo.getButtonFirstLink().replaceFirst(pageNavInfo.getButtonFirstLinkReplaceLink(), pageURL + "&" + pageString + "=1"));
					else
						pages.append(pageNavInfo.getButtonFirstLink().replaceFirst(pageNavInfo.getButtonFirstLinkReplaceLink(), "javascript:" + scriptFunction + "(1)"));
				}
				
				if (currentPage <= 10)
					pages.append(pageNavInfo.getButtonPrev());
				else {
					if (pageURL != null)
						pages.append(pageNavInfo.getButtonPrevLink().replaceFirst(pageNavInfo.getButtonPrevLinkReplaceLink(), pageURL + "&" + pageString + "=" + (int)(Math.ceil((double)currentPage / 10) * 10 - 19)));
					else
						pages.append(pageNavInfo.getButtonPrevLink().replaceFirst(pageNavInfo.getButtonPrevLinkReplaceLink(), "javascript:" + scriptFunction + "(" + (int)(Math.ceil((double)currentPage / 10) * 10 - 19) + ")"));
				}
				
				for (int count = pageCount1; count <= pageCount2; count++) {
					
					if (count == currentPage)
						pages.append(pageNavInfo.getPageCurrent().replaceFirst(pageNavInfo.getPageCurrentReplacePage(), String.valueOf(count)));
					else {
						String pageLink2 = pageNavInfo.getPageLink().replaceFirst(pageNavInfo.getPageLinkReplacePage(), String.valueOf(count));
						if (pageURL != null)
							pages.append(pageLink2.replaceFirst(pageNavInfo.getPageLinkReplaceLink(), pageURL + "&" + pageString + "=" + count));
						else
							pages.append(pageLink2.replaceFirst(pageNavInfo.getPageLinkReplaceLink(), "javascript:" + scriptFunction + "(" + count + ")"));
					}
					
					if (count < pageCount2) {
						if (pageNavInfo.getPageSeparator() != null)
							pages.append(pageNavInfo.getPageSeparator());
					}
				}
				
				if (Math.ceil((double)currentPage / 10) == Math.ceil((double)totalPages / 10))
					pages.append(pageNavInfo.getButtonNext());
				else {
					if (pageURL != null)
						pages.append(pageNavInfo.getButtonNextLink().replaceFirst(pageNavInfo.getButtonNextLinkReplaceLink(), pageURL + "&" + pageString + "=" + (int)(Math.ceil((double)currentPage / 10) * 10 + 1)));
					else
						pages.append(pageNavInfo.getButtonNextLink().replaceFirst(pageNavInfo.getButtonNextLinkReplaceLink(), "javascript:" + scriptFunction + "(" + (int)(Math.ceil((double)currentPage / 10) * 10 + 1) + ")"));
				}
				
				if (currentPage == totalPages)
					pages.append(pageNavInfo.getButtonLast());
				else {
					if (pageURL != null)
						pages.append(pageNavInfo.getButtonLastLink().replaceFirst(pageNavInfo.getButtonLastLinkReplaceLink(), pageURL + "&" + pageString + "=" + totalPages));
					else
						pages.append(pageNavInfo.getButtonLastLink().replaceFirst(pageNavInfo.getButtonLastLinkReplaceLink(), "javascript:" + scriptFunction + "(" + totalPages + ")"));
				}
				pages.append(pageNavInfo.getContainerClose());
			}
			
		}
		catch (Exception e) {
			logger.error("[com.plutozone.util.paging.PagingHandler.getPageNavigator()] Error occured during generate page navigator: " + e.getMessage(), e);
			throw new Exception("[com.plutozone.util.paging.PagingHandler.getPageNavigator()] Error occured during generate page navigator: " + e.getMessage());
		}
		
		return pages.toString();
	}
}
