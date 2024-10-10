/**
 * YOU ARE STRICTLY PROHIBITED TO COPY, DISCLOSE, DISTRIBUTE, MODIFY OR USE THIS PROGRAM
 * IN PART OR AS A WHOLE WITHOUT THE PRIOR WRITTEN CONSENT OF HIMEDIA.CO.KR.
 * HIMEDIA.CO.KR OWNS THE INTELLECTUAL PROPERTY RIGHTS IN AND TO THIS PROGRAM.
 * COPYRIGHT (C) 2024 HIMEDIA.CO.KR ALL RIGHTS RESERVED.
 *
 * 하기 프로그램에 대한 저작권을 포함한 지적재산권은 com.happySteps에 있으며,
 * himedia.co.kr이 명시적으로 허용하지 않는 사용, 복사, 변경 및 제 3자에 의한 공개, 배포는 엄격히 금지되며
 * himedia.co.kr의 지적재산권 침해에 해당된다.
 * Copyright (C) 2024 com.happySteps All Rights Reserved.
 *
 *
 * Program		: com.happySteps
 * Description	:
 * Environment	: JRE 1.7 or more
 * File			: SaleDao.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [202410010900][hyeen103#gmail.com][CREATE: Initial Release]
 */
package com.happySteps.front.sale.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.happySteps.front.common.dao.BaseDao;
import com.happySteps.front.common.dto.PagingDto;
import com.happySteps.front.sale.dto.SaleDto;




/**
 * @version 1.0.0
 * @author hyeen103@gmail.com
 * 
 * @since 202410010900
 * <p>DESCRIPTION:</p>
 * <p>IMPORTANT:</p>
 */
@Repository("com.happySteps.front.sale.dao.SaleDao")
public class SaleDao extends BaseDao {

	public int count(PagingDto pagingDto) {
		return sqlSessionFront.selectOne("com.happySteps.front.mybatis.sale.Sale.count", pagingDto);
	}
	
	/**
	 * @param pagingDto [페이징 빈]
	 * @return List<BoardDto>
	 * 
	 * @since 2024-07-04
	 * <p>DESCRIPTION: 카테고리 shop 목록</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	public List<SaleDto> list(PagingDto pagingDto) {
		
		return sqlSessionFront.selectList("com.happySteps.front.mybatis.sale.Sale.list", pagingDto);
	}

	public SaleDto select(SaleDto saleDto) {
		return sqlSessionFront.selectOne("com.happySteps.front.mybatis.sale.Sale.select", saleDto);
	}
	
	public List<SaleDto> search(SaleDto saleDto) {
		return sqlSessionFront.selectList("com.happySteps.front.mybatis.sale.Sale.search", saleDto);
	}
}
