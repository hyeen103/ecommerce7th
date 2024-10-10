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
 * File			: SKwithAES.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20231211151204][pluto@HAPPYSTEPS.COM][CREATE: Initial Release]
 */
package com.happySteps.util.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidAlgorithmParameterException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * @version 1.0.0
 * @author pluto@HAPPYSTEPS.COM
 * 
 * @since 2023-12-11
 * <p>DESCRIPTION: AES 암호화 유틸리티 클래스(Base64 인코딩 포함)</p>
 * <p>IMPORTANT: Required Java Cryptography Extension(JCE) Unlimited Strength Jurisdiction Policy File(http://www.oracle.com/technetwork/java/javase/downloads/index.html)</p>
 */
public class SKwithAES {
	
	public static byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
	
	/** Security Key */
	private String securityKey = null;	
	
	/**
	 * @param key [키]
	 * 
	 * @since 2015-12-21
	 * <p>DESCRIPTION: 생성자로 키(key)를 사용</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입과 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	public SKwithAES(String key) {
		this.securityKey = key;
	}
	
	/**
	 * @param plainString [평문]
	 * @param encoding [인코딩]
	 * @throws UnsupportedEncodingException [미지원 인코딩]
	 * @throws NoSuchAlgorithmException [알고리즘 부재]
	 * @throws NoSuchPaddingException [패딩 부재]
	 * @throws InvalidKeyException [유효하지 않은 키]
	 * @throws InvalidAlgorithmParameterException [유효하지 않은 알고리즘 파라미터]
	 * @throws IllegalBlockSizeException [블럭 사이트 예외]
	 * @throws BadPaddingException [패딩 예외]
	 * @return String
	 * 
	 * @since 2015-12-09
	 * <p>DESCRIPTION: 평문(plainString)을 해당 인코딩(encoding)으로 암호화(Base64 인코딩 포함)된 문자열로 얻기</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입과 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:<br>
	 * <code>String key = "12345678901234567890123456789012";</code><br>
	 * <code>SKwithAES aes = new SKwithAES(key);</code><br>
	 * <code>String encodeText = aes.encode("pluto@plutozone.com", "UTF-8");</code>
	 * </p>
	 */
	public String encode(String plainString, String encoding) throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		
		if (encoding.equals(null) || encoding.equals("")) encoding = "UTF-8";
		
		byte[] textBytes = plainString.getBytes(encoding);
	
		AlgorithmParameterSpec ivSpec	= new IvParameterSpec(ivBytes);
		SecretKeySpec newKey			= new SecretKeySpec(securityKey.getBytes(encoding), "AES");
		Cipher cipher					= null;
		cipher							= Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
		
		return Base64.encodeBase64String(cipher.doFinal(textBytes));
	}
	
	/**
	 * @param plainString [평문]
	 * @throws UnsupportedEncodingException [미지원 인코딩]
	 * @throws NoSuchAlgorithmException [알고리즘 부재]
	 * @throws NoSuchPaddingException [패딩 부재]
	 * @throws InvalidKeyException [유효하지 않은 키]
	 * @throws InvalidAlgorithmParameterException [유효하지 않은 알고리즘 파라미터]
	 * @throws IllegalBlockSizeException [블럭 사이즈 예외]
	 * @throws BadPaddingException [패딩 예외]
	 * @return String
	 * 
	 * @since 2016-02-23
	 * <p>DESCRIPTION: 평문(plainString)을 암호화(UTF-8 기준, Base64 인코딩 포함)된 문자열로 얻기</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입과 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:<br>
	 * <code>String key = "12345678901234567890123456789012";</code><br>
	 * <code>SKwithAES aes = new SKwithAES(key);</code><br>
	 * <code>String encodeText = aes.encode("pluto@plutozone.com", "UTF-8");</code>
	 * </p>
	 */
	public String encode(String plainString) throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		return encode(plainString, "UTF-8");
	}
	
	
	/**
	 * @param encryptedString [암호문]
	 * @param encoding [인코딩]
	 * @throws UnsupportedEncodingException [미지원 인코딩]
	 * @throws NoSuchAlgorithmException [알고리즘 부재]
	 * @throws NoSuchPaddingException [패딩 부재]
	 * @throws InvalidKeyException [유효하지 않은 키]
	 * @throws InvalidAlgorithmParameterException [유효하지 않은 알고리즘 파라미터]
	 * @throws IllegalBlockSizeException [블럭 사이즈 예외]
	 * @throws BadPaddingException [패딩 예외]
	 * @return String
	 * 
	 * @since 2015-12-09
	 * <p>DESCRIPTION: 암호화된 문자열(encryptedString)을 해당 인코딩(encoding)으로 복호화(Base64 인코딩 포함)된 문자열로 얻기</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입과 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:<br>
	 * <code>String key = "12345678901234567890123456789012";</code><br>
	 * <code>SKwithAES aes = new SKwithAES(key);</code><br>
	 * <code>String decodeText = aes.decode("ZpJiIRYO8YFJWb0WLxkbz37XGqwWfx82G0DX0pxmNWc=", "UTF-8");</code>
	 * </p>
	 */
	public String decode(String encryptedString, String encoding) throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		
		byte[] textBytes = Base64.decodeBase64(encryptedString);
		
		AlgorithmParameterSpec ivSpec	= new IvParameterSpec(ivBytes);
		SecretKeySpec newKey			= new SecretKeySpec(securityKey.getBytes(encoding), "AES");
		Cipher cipher					= Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
		
		return new String(cipher.doFinal(textBytes), encoding);
	}
	
	/**
	 * @param encryptedString [암호문]
	 * @throws UnsupportedEncodingException [미지원 인코딩]
	 * @throws NoSuchAlgorithmException [알고리즘 부재]
	 * @throws NoSuchPaddingException [패딩 부재]
	 * @throws InvalidKeyException [유효하지 않은 키]
	 * @throws InvalidAlgorithmParameterException [유효하지 않은 알고리즘 파라미터]
	 * @throws IllegalBlockSizeException [블럭 사이즈 예외]
	 * @throws BadPaddingException [패딩 예외]
	 * @return String
	 * 
	 * @since 2016-02-23
	 * <p>DESCRIPTION: 암호화된 문자열(encryptedString)을 복호화(UTF-8기준, Base64 인코딩 포함)된 문자열로 얻기</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입과 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:<br>
	 * <code>String key = "12345678901234567890123456789012";</code><br>
	 * <code>SKwithAES aes = new SKwithAES(key);</code><br>
	 * <code>String decodeText = aes.decode("ZpJiIRYO8YFJWb0WLxkbz37XGqwWfx82G0DX0pxmNWc=", "UTF-8");</code>
	 * </p>
	 */
	public String decode(String encryptedString) throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		return decode(encryptedString, "UTF-8");
	}
	
	/** Example code */
	/*
	public static void main(String[] args) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
		
		// AES-256
		String key = "12345678901234567890123456789012";
		// AES-128
		// String key = "1234567890123456";
		
		String plainText	= "";
		String encodeText	= "";
		String decodeText	= "";
		
		SKwithAES aes = new SKwithAES(key);
		
		// Encrypt
		plainText  = "pluto@plutozone.com";
		encodeText = aes.encode(plainText, "UTF-8");
		System.out.println("Encode ["+ encodeText + "]");
		 
		// Decrypt
		decodeText = aes.decode("TQLIdoDOrD0ohIYrsT2Oe0Panxi9jmaZEzAOG+/NJXU=", "UTF-8");
		System.out.println("Decode ["+ decodeText + "]");
	}
	*/
}
