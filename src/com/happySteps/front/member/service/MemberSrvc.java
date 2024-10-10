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
 * File			: MemberSrvc.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20240625105426][one4027one#himedia.co.kr][CREATE: Initial Release]
 */
package com.happySteps.front.member.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.happySteps.front.member.dao.MemberDao;
import com.happySteps.front.member.dao.StatisticMemberDao;
import com.happySteps.front.member.dao.TermAgreeDao;
import com.happySteps.front.member.dto.MemberDto;
import com.happySteps.front.member.dto.StatisticMemberDto;
import com.happySteps.front.member.dto.TermAgreeDto;

/**
 * @version 1.0.0
 * @author one4027one#himedia.co.kr
 * 
 * @since 2024-08-01
 * <p>DESCRIPTION:</p>
 * <p>IMPORTANT:</p>
 */
@Service("com.happySteps.front.member.service.MemberSrvc")
public class MemberSrvc {
	
	@Inject
	MemberDao memberDao;
	
	@Inject
	TermAgreeDao termAgreeDao;
	
	@Inject
	StatisticMemberDao statisticMemberDao;
	
	public int selectIdDuplicate(MemberDto memberDto) {
		return memberDao.selectIdDuplicate(memberDto);
	}
	public int selectNickDuplicate(MemberDto memberDto) {
		return memberDao.selectNickDuplicate(memberDto);
	}
	
	@Transactional("txFront")
	public boolean insert(MemberDto memberDto) {
		
		int result = 0;
		
		// 신규 회원 번호(seq_mbr)
		memberDto.setSeq_mbr(memberDao.sequence());
		memberDto.setRegister(memberDto.getSeq_mbr());
		
		// 회원 정보
		result += memberDao.insertMaster(memberDto);
		result += memberDao.insertDetail(memberDto);
		
		// 회원(2개) 
		if (result == 2) return true;
		else {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
	}
	
	/*
	 * @Transactional("txFront") public boolean insert(MemberDto memberDto, String[]
	 * arrTermAgreement, String post) {
	 * 
	 * int result = 0;
	 * 
	 * // 신규 회원 번호(seq_mbr) memberDto.setSeq_mbr(memberDao.sequence());
	 * memberDto.setRegister(memberDto.getSeq_mbr());
	 * 
	 * // 회원 정보 result += memberDao.insertMaster(memberDto); result +=
	 * memberDao.insertDetail(memberDto);
	 * 
	 * // 약관 정보 TermAgreeDto termAgreeDto = new TermAgreeDto();
	 * 
	 * for (int loop = 0; loop < 3; loop++) {
	 * 
	 * termAgreeDto.setSeq_trm_agr(termAgreeDao.sequence());
	 * termAgreeDto.setSeq_mbr(memberDto.getSeq_mbr()); termAgreeDto.setSeq_trm(loop
	 * + 1); termAgreeDto.setFlg_agr(arrTermAgreement[loop]);
	 * termAgreeDto.setRegister(memberDto.getSeq_mbr());
	 * 
	 * result += termAgreeDao.insert(termAgreeDto); }
	 * 
	 * // 통계 정보 // [2024-08-13][pluto@himedia.co.kir][TODO: 추후 통계 정보가 확장될 경우 로직
	 * 개선필요] StatisticMemberDto statisticMemberDto = new StatisticMemberDto();
	 * statisticMemberDto.setSeq_mbr(memberDto.getSeq_mbr());
	 * statisticMemberDto.setPost(post); result +=
	 * statisticMemberDao.insert(statisticMemberDto);
	 * 
	 * // 회원(2개) + 약관(3개) + 통계(1개) if (result == 2 + 3 + 1) return true; else {
	 * TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); return
	 * false; } }
	 */
	
	public MemberDto selectPasswd(MemberDto memberDto) {
		return memberDao.selectPasswd(memberDto);
	}
	
	@Transactional("txFront")
	public boolean updatePasswd(MemberDto memberDto) {
		
		int result = memberDao.updatePasswd(memberDto);
		
		if (result == 1) return true;
		else {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
	}
	
	@Transactional("txFront")
	public boolean update(MemberDto memberDto) {
				
		if (memberDao.updateMaster(memberDto) == 1 && memberDao.updateDetail(memberDto) == 1) return true;
		else {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
	}
	
	public MemberDto select(MemberDto memberDto) {
		return memberDao.select(memberDto);
	}
}