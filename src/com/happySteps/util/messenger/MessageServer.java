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
 * File			: MessageServer.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20240617143615][pluto@HAPPYSTEPS.COM][CREATE: Initial Release]
 */
package com.happySteps.util.messenger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * @version 1.0.0
 * @author pluto@HAPPYSTEPS.COM
 * 
 * @since 2024-06-17
 * <p>DESCRIPTION:</p>
 * <p>IMPORTANT:</p>
 */
public class MessageServer {
	
	/** Logger */
	private static Logger logger = LoggerFactory.getLogger(MessageServer.class);
	
	/** Server */
	ServerSocket server		= null;
	
	/** Client */
	public Socket client	= null;
	
	public static void main(String args[]) {
		
		int port = 8080;
		
		if (args.length > 0 && args[0] != null && !args[0].equals("")) port = Integer.parseInt(args[0]);
		
		new MessageServer(port);
	}
	
	/**
	 * @param port [포트]
	 * 
	 * @since 2017-06-14
	 * <p>DESCRIPTION:</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	public MessageServer(int port) {
		try {
			server = new ServerSocket(port);
			
			logger.info("---------------------------------------------------------------------------");
			logger.info("com.plutozone.util.messenger.MessageServer has started at " + port + " port");
			logger.info("---------------------------------------------------------------------------");
			
			while (true) {
				client = server.accept();
				logger.info("A new client has connected... " + client.getInetAddress().getHostAddress() +" at com.plutozone.util.messenger.MessageServer");
				
				ClientHandler clientHandler = new ClientHandler(client);
				clientHandler.start();
			}
		}
		catch (IOException e) {
			logger.error("[" + this.getClass().getName() + ".MessageServer()] " + e.getMessage(), e);
		}
	}
}
