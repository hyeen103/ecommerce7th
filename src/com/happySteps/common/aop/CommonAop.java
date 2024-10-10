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
 * File			: CommonAop.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20240624110234][pluto@HAPPYSTEPS.COM][CREATE: Initial Release]
 */
package com.happySteps.common.aop;

import java.text.DecimalFormat;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version 1.0.0
 * @author pluto@HAPPYSTEPS.COM
 * 
 * @since 2023-12-04
 * <p>DESCRIPTION:</p>
 * <p>IMPORTANT:</p>
 */
@Aspect
public class CommonAop {
	
	/** Logger */
	private static Logger logger = LoggerFactory.getLogger(CommonAop.class);
	
	/**
	 * @since 2015-12-22
	 * <p>DESCRIPTION: com.plutozone 하위 패키지 내의 모든 컨트롤러 파일의 메소드(* com.plutozone..*Web.*(..) || * com.plutozone..*Api*(..))에 실행 시간(timeTarget())을 설정(전체 인수 포함)</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:<br>
	 * <code>@Pointcut("execution(* com.plutozone.backoffice.config.dao.ManagerDao.*(..))")</code><br>
	 * <code>@Pointcut("execution(* com.plutozone..*Dao.*(..))")</code><br>
	 * <code>@Pointcut("execution(* com.plutozone..controller.*Ctrl.*(..)) || execution(* com.plutozone..service.*Impl.*(..)) || execution(* com.plutozone..dao.*Dao.*(..))")</code><br>
	 * </p>
	 */
	@Pointcut("execution(* com.github.ecommerce7th.jupiter..*Web.*(..)) || execution(* com.github.ecommerce7th.jupiter..*Api.*(..))")
	private void controllerTarget(){}
	
	/**
	 * @param joinPoint [JOIN POINT]
	 * @throws Throwable [Throwable]
	 * @return Object
	 * 
	 * @since 2015-12-22
	 * <p>DESCRIPTION: 상기 지정된 모든 메소드의 실행 시간 추적 로깅(logger)</p>
	 * <p>IMPORTANT: 파라미터는 데이터 타입 및 형식을 만족하여야 한다.</p>
	 * <p>EXAMPLE:</p>
	 */
	@Around("controllerTarget()")
	public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
		
		/** 메소드 실행 전 공통 기능 */
		long startTime		= System.currentTimeMillis();				// 시작 시각
		String methodName	= joinPoint.getSignature().toString();		// 호출되는 메소드명
		
		/**  메소드 실행 */
		Object returnValue 	= joinPoint.proceed(); 						// 메소드 호출(returnValue는 해당 메소드의 실행 결과)
		
		/** 메소드 실행 후 공통 기능 */
		long endTime		= System.currentTimeMillis();				// 종료 시작
		long executeTime	= endTime - startTime;						// 실행 시간(초): 종료 시작 - 시작 시각
		
		DecimalFormat df = new DecimalFormat("#,###0.000");
		if (executeTime / 1000.0 >= 3) {
			logger.warn("[%EXECUTE_TIME(Over 3s)%][" + methodName + "] " + df.format((executeTime / 1000.0)) + "s");
		}
		else {
			// logger.debug("[%EXECUTE_TIME%][" + methodName + "] " + df.format((executeTime / 1000.0)) + "s");
		}
		
		return returnValue;
	}
}