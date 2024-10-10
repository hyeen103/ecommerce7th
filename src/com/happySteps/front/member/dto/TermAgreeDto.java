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
 * File			: TermAgreeDto.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20240807162645][pluto@HAPPYSTEPS.COM][CREATE: Initial Release]
 */
package com.happySteps.front.member.dto;

/**
 * @version 1.0.0
 * @author pluto@HAPPYSTEPS.COM
 * 
 * @since 2024-08-07
 * <p>DESCRIPTION:</p>
 * <p>IMPORTANT:</p>
 */
public class TermAgreeDto {

	private int		seq_trm_agr	= 0;
	private int		seq_mbr		= 0;
	private int		seq_trm		= 0;
	private String	flg_agr		= "";
	private String	dt_reg		= "";
	private int		register	= 0;
	
	
	public int getSeq_trm_agr() {
		return seq_trm_agr;
	}
	public void setSeq_trm_agr(int seq_trm_agr) {
		this.seq_trm_agr = seq_trm_agr;
	}
	public int getSeq_mbr() {
		return seq_mbr;
	}
	public void setSeq_mbr(int seq_mbr) {
		this.seq_mbr = seq_mbr;
	}
	public int getSeq_trm() {
		return seq_trm;
	}
	public void setSeq_trm(int seq_trm) {
		this.seq_trm = seq_trm;
	}
	public String getFlg_agr() {
		return flg_agr;
	}
	public void setFlg_agr(String flg_agr) {
		this.flg_agr = flg_agr;
	}
	public String getDt_reg() {
		return dt_reg;
	}
	public void setDt_reg(String dt_reg) {
		this.dt_reg = dt_reg;
	}
	public int getRegister() {
		return register;
	}
	public void setRegister(int register) {
		this.register = register;
	}
}