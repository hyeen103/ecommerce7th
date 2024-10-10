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
 * File			: PagingLoader.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20231201124812][pluto@HAPPYSTEPS.COM][CREATE: Initial Release]
 */
package com.happySteps.util.paging;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xpath.internal.XPathAPI;

import com.happySteps.util.Xml;

/**
 * @version 1.0.0
 * @author pluto@HAPPYSTEPS.COM
 * 
 * @since 2023-12-01
 * <p>DESCRIPTION:</p>
 * <p>IMPORTANT:</p>
 */
public class PagingLoader {
	
	/** Logger */
	private static Logger logger = LoggerFactory.getLogger(PagingLoader.class);
	
	/** Paging loader */
	private static PagingLoader instance 		= null;
	/** Paging info */
	// JRE 1.5.x
	private Map<String, PagingInfo> pagingMap 	= null;
	// JRE 1.4.x
	// private Map pagingMap = null;
	
	/**
	 * @since 2015-12-08
	 * <p>DESCRIPTION: 페이징 로더 생성자</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	private PagingLoader() {
		// JRE 1.5.x
		this.pagingMap = new HashMap<String, PagingInfo>();
		// JRE 1.4.x
		// this.pagingMap = new HashMap();
		
	}
	
	/**
	 * @return PagingLoader
	 * 
	 * @since 2015-12-08
	 * <p>DESCRIPTION: 페이징 로더 인스턴스 얻기</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	public static synchronized PagingLoader getInstance() {
		if (instance == null)
			instance = new PagingLoader();
		return instance;
	}	
	
	/**
	 * @param fileName [경로 및 파일명]
	 * @throws Exception [예외]
	 * 
	 * @since 2015-12-08
	 * <p>DESCRIPTION: 페이징 네비게이터를 지정한 스타일(fileName)로 설정</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입과 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	public void setPageNavigators(String fileName) throws Exception {
		
		try {
			this.pagingMap.clear();
			Document document = Xml.getXmlFromFile(fileName);
			NodeList nodeList = XPathAPI.selectNodeList(document, "/pageNavigator/style");
			
			for (int index = 0; index < nodeList.getLength(); index++) {
				
				Element elem = (Element)nodeList.item(index);
				String styleId = elem.getAttribute("id");
				
				if (styleId != null) {
					
					PagingInfo pageNavInfo = new PagingInfo();
					
					pageNavInfo.setContainerOpen(Xml.getNodeValue(XPathAPI.selectSingleNode(document, "/pageNavigator/style[@id='" + styleId + "']/containers/container[@name='containerOpen']/text()")));
					pageNavInfo.setContainerClose(Xml.getNodeValue(XPathAPI.selectSingleNode(document, "/pageNavigator/style[@id='" + styleId + "']/containers/container[@name='containerClose']/text()")));
					
					pageNavInfo.setButtonFirst(Xml.getNodeValue(XPathAPI.selectSingleNode(document, "/pageNavigator/style[@id='" + styleId + "']/buttons/button[@name='buttonFirst']/text()")));
					pageNavInfo.setButtonFirstLink(Xml.getNodeValue(XPathAPI.selectSingleNode(document, "/pageNavigator/style[@id='" + styleId + "']/buttons/button[@name='buttonFirstLink']/text()")));
					pageNavInfo.setButtonFirstLinkReplaceLink(((Element)XPathAPI.selectSingleNode(document, "/pageNavigator/style[@id='" + styleId + "']/buttons/button[@name='buttonFirstLink']")).getAttribute("replaceLink"));
					
					pageNavInfo.setButtonPrev(Xml.getNodeValue(XPathAPI.selectSingleNode(document, "/pageNavigator/style[@id='" + styleId + "']/buttons/button[@name='buttonPrev']/text()")));
					pageNavInfo.setButtonPrevLink(Xml.getNodeValue(XPathAPI.selectSingleNode(document, "/pageNavigator/style[@id='" + styleId + "']/buttons/button[@name='buttonPrevLink']/text()")));
					pageNavInfo.setButtonPrevLinkReplaceLink(((Element)XPathAPI.selectSingleNode(document, "/pageNavigator/style[@id='" + styleId + "']/buttons/button[@name='buttonPrevLink']")).getAttribute("replaceLink"));
					
					pageNavInfo.setButtonNext(Xml.getNodeValue(XPathAPI.selectSingleNode(document, "/pageNavigator/style[@id='" + styleId + "']/buttons/button[@name='buttonNext']/text()")));
					pageNavInfo.setButtonNextLink(Xml.getNodeValue(XPathAPI.selectSingleNode(document, "/pageNavigator/style[@id='" + styleId + "']/buttons/button[@name='buttonNextLink']/text()")));
					pageNavInfo.setButtonNextLinkReplaceLink(((Element)XPathAPI.selectSingleNode(document, "/pageNavigator/style[@id='" + styleId + "']/buttons/button[@name='buttonNextLink']")).getAttribute("replaceLink"));
					
					pageNavInfo.setButtonLast(Xml.getNodeValue(XPathAPI.selectSingleNode(document, "/pageNavigator/style[@id='" + styleId + "']/buttons/button[@name='buttonLast']/text()")));
					pageNavInfo.setButtonLastLink(Xml.getNodeValue(XPathAPI.selectSingleNode(document, "/pageNavigator/style[@id='" + styleId + "']/buttons/button[@name='buttonLastLink']/text()")));
					pageNavInfo.setButtonLastLinkReplaceLink(((Element)XPathAPI.selectSingleNode(document, "/pageNavigator/style[@id='" + styleId + "']/buttons/button[@name='buttonLastLink']")).getAttribute("replaceLink"));
					
					pageNavInfo.setPageSeparator(Xml.getNodeValue(XPathAPI.selectSingleNode(document, "/pageNavigator/style[@id='" + styleId + "']/pages/page[@name='separator']/text()")));
					
					pageNavInfo.setPageCurrent(Xml.getNodeValue(XPathAPI.selectSingleNode(document, "/pageNavigator/style[@id='" + styleId + "']/pages/page[@name='pageCurrent']/text()")));
					pageNavInfo.setPageCurrentReplacePage(((Element)XPathAPI.selectSingleNode(document, "/pageNavigator/style[@id='" + styleId + "']/pages/page[@name='pageCurrent']")).getAttribute("replacePage"));
					
					pageNavInfo.setPageLink(Xml.getNodeValue(XPathAPI.selectSingleNode(document, "/pageNavigator/style[@id='" + styleId + "']/pages/page[@name='pageLink']/text()")));
					pageNavInfo.setPageLinkReplacePage(((Element)XPathAPI.selectSingleNode(document, "/pageNavigator/style[@id='" + styleId + "']/pages/page[@name='pageLink']")).getAttribute("replacePage"));
					pageNavInfo.setPageLinkReplaceLink(((Element)XPathAPI.selectSingleNode(document, "/pageNavigator/style[@id='" + styleId + "']/pages/page[@name='pageLink']")).getAttribute("replaceLink"));
					
					this.pagingMap.put(styleId, pageNavInfo);
				}
			}
			logger.info("[" + this.getClass().getName() + ".setPageNavigators()] " + fileName + " is loaded.");
			
		}
		catch (Exception e) {
			logger.error("[" + this.getClass().getName() + ".setPageNavigators()] Error occured during load " + fileName + ".", e);
		}

	}
	
	/**
	 * @param styleId [스타일 ID]
	 * @return PagingInfo
	 * 
	 * @since 2015-12-08
	 * <p>DESCRIPTION: 지정한 스타일(styleId)로 페이징 정보 얻기</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입과 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE: <code>styleId = "front" or "admin" or "admin_image"</code></p>
	 */
	protected PagingInfo getPagingInfo(String styleId) {
		// JRE 1.5.x
		PagingInfo pageNavInfo = this.pagingMap.get(styleId);
		// JRE 1.4.x
		// PagingInfo pageNavInfo = (PagingInfo)this.pagingMap.get(styleId);
		return pageNavInfo;
	}
}
