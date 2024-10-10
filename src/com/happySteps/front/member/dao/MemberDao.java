/**
 * YOU ARE STRICTLY PROHIBITED TO COPY, DISCLOSE, DISTRIBUTE, MODIFY OR USE THIS PROGRAM
 * IN PART OR AS A WHOLE WITHOUT THE PRIOR WRITTEN CONSENT OF HIMEDIA.CO.KR.
 * HIMEDIA.CO.KR OWNS THE INTELLECTUAL PROPERTY RIGHTS IN AND TO THIS PROGRAM.
 * COPYRIGHT (C) 2024 HIMEDIA.CO.KR ALL RIGHTS RESERVED.
 *
 * 하기 프로그램에 대한 저작권을 포함한 지적재산권은 himedia.co.kr에 있으며,
 * himedia.co.kr이 명시적으로 허용하지 않는 사용, 복사, 변경 및 제 3자에 의한 공개, 배포는 엄격히 금지되며
 * himedia.co.kr의 지적재산권 침해에 해당된다.
 * Copyright (C) 2024 himedia.co.kr All Rights Reserved.
 *
 *
 * Program		: kr.co.himedia.ecommerce
 * Description	:
 * Environment	: JRE 1.7 or more
 * File			: MemberDao.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20240625100333][pluto#himedia.co.kr][CREATE: Initial Release]
 */
package com.happySteps.front.member.dao;

import org.springframework.stereotype.Repository;

import com.happySteps.front.common.dao.BaseDao;
import com.happySteps.front.member.dto.MemberDto;

/**
 * @version 1.0.0
 * @author hyeen103#gmail.com
 * 
 * @since 2024-08-01
 * <p>DESCRIPTION:</p>
 * <p>IMPORTANT:</p>
 */
@Repository("com.happySteps.front.member.dao.MemberDao")
public class MemberDao extends BaseDao {
	
	public int selectIdDuplicate(MemberDto memberDto) {
		return sqlSessionFront.selectOne("com.happySteps.front.mybatis.member.Member.selectIdDuplicate", memberDto);
	}
	public int selectNickDuplicate(MemberDto memberDto) {
		return sqlSessionFront.selectOne("com.happySteps.front.mybatis.member.Member.selectNickDuplicate", memberDto);
	}
	
	public int sequence() {
		return sqlSessionFront.selectOne("com.happySteps.front.mybatis.member.Member.sequence");
	}
	
	public int insertMaster(MemberDto memberDto) {
		return sqlSessionFront.insert("com.happySteps.front.mybatis.member.Member.insertMaster", memberDto);
	}
	
	public int insertDetail(MemberDto memberDto) {
		return sqlSessionFront.insert("com.happySteps.front.mybatis.member.Member.insertDetail", memberDto);
	}
	
	public int updateMaster(MemberDto memberDto) {
		return sqlSessionFront.update("com.happySteps.front.mybatis.member.Member.updateMaster", memberDto);
	}
	
	public int updateDetail(MemberDto memberDto) {
		return sqlSessionFront.update("com.happySteps.front.mybatis.member.Member.updateDetail", memberDto);
	}
		
	public MemberDto select(MemberDto memberDto) {
		return sqlSessionFront.selectOne("com.happySteps.front.mybatis.member.Member.select", memberDto);
	}
	
	public MemberDto selectPasswd(MemberDto memberDto) {
		return sqlSessionFront.selectOne("com.happySteps.front.mybatis.member.Member.selectPasswd", memberDto);
	}
	
	public int updatePasswd(MemberDto memberDto) {
		return sqlSessionFront.update("com.happySteps.front.mybatis.member.Member.updatePasswd", memberDto);
	}
}