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
 * File			: TermAgreeDao.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20240807163239][pluto@HAPPYSTEPS.COM][CREATE: Initial Release]
 */
package com.happySteps.front.member.dao;

import org.springframework.stereotype.Repository;

import com.happySteps.front.common.dao.BaseDao;
import com.happySteps.front.member.dto.TermAgreeDto;

/**
 * @version 1.0.0
 * @author pluto@HAPPYSTEPS.COM
 * 
 * @since 2024-08-07
 * <p>DESCRIPTION:</p>
 * <p>IMPORTANT:</p>
 */
@Repository("com.happySteps.front.member.dao.TermAgreeDao")
public class TermAgreeDao extends BaseDao {
	
	public int sequence() {
		return sqlSessionFront.selectOne("com.happySteps.front.mybatis.member.TermAgree.sequence");
	}
	
	public int insert(TermAgreeDto termAgreeDto) {
		return sqlSessionFront.insert("com.happySteps.front.mybatis.member.TermAgree.insert", termAgreeDto);
	}
}