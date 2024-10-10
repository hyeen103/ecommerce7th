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
 * File			: MessageClient.java
 * Notes		:
 * History		: [NO][Programmer][Description]
 *				: [20240617144824][pluto@HAPPYSTEPS.COM][CREATE: Initial Release]
 */
package com.happySteps.util.messenger;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @version 1.0.0
 * @author pluto@HAPPYSTEPS.COM
 * 
 * @since 2024-06-17
 * <p>DESCRIPTION:</p>
 * <p>IMPORTANT:</p>
 */
public class MessageClient extends Frame implements Runnable, ActionListener, WindowListener {
	
	/** Logger */
	private static Logger logger = LoggerFactory.getLogger(MessageClient.class);
	
	/** Serial version UID */
	private static final long serialVersionUID = 20170614133900L;
	
	/** Listener */
	protected Thread listener = null;
	
	/** Socket */
	protected Socket socket;
	public String ip;
	public int port;
	
	/** Input/Output Object Stream */
	protected ObjectInputStream objectInputStream;
	protected ObjectOutputStream objectOutputStream;
	
	/** UI */
	protected Button btnConnect, btnDisconnect;
	protected TextArea txtContent;
	protected TextField txtName;
	protected TextField txtMessage;
	
	/** Connection State */
	protected boolean isConnected = false;
	
	public static void main(String[] args) {
		
		String ip	= "127.0.0.1";
		int port	= 8080;
		
		if (args.length > 1 && args[0] != null && !args[0].equals("")) ip = args[0];
		if (args.length > 1 && args[1] != null && !args[1].equals("")) port = Integer.parseInt(args[1]);
		
		new MessageClient(ip, port);
	}
	
	/**
	 * @param port [포트]
	 * 
	 * @since 2017-06-14
	 * <p>DESCRIPTION:</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	public MessageClient(String ip, int port) {
		
		super("Messenger GUI(Java AWT) Client Version 1.0.0");
		
		this.ip		= ip;
		this.port	= port;
		
		Panel conPanel		= new Panel();
		Label lbl			= new Label("Name: ");
		txtName				= new TextField("Your Name");
		txtName.selectAll();
		
		btnConnect = new Button("Connect");
		btnConnect.addActionListener(this);
		
		btnDisconnect = new Button("Disconnect");
		btnDisconnect.addActionListener(this);
		
		conPanel.add(lbl);
		conPanel.add(txtName);
		conPanel.add(btnConnect);
		conPanel.add(btnDisconnect);
		
		txtContent = new TextArea();
		txtContent.setEditable(false);
		
		txtMessage = new TextField();
		txtMessage.addActionListener(this);
		
		setLayout(new BorderLayout());
		add("North", conPanel);
		add("Center", txtContent);
		add("South", txtMessage);
		
		addWindowListener(this);
		setSize(500, 400);
		setVisible(true);
		
		setChatMode(false);
	}
	
	/**
	 * @param mode [모드]
	 * 
	 * @since 2017-06-14
	 * <p>DESCRIPTION:</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	protected void setChatMode(boolean mode) {
		if (mode == true) {
			btnConnect.setEnabled(false);
			btnDisconnect.setEnabled(true);
			txtName.setEditable(false);
			txtMessage.setEditable(true);
		}
		else {
			btnConnect.setEnabled(true);
			btnDisconnect.setEnabled(false);
			txtName.setEditable(true);
			txtMessage.setEditable(false);
		}
	}
	
	/**
	 * @since 2017-06-14
	 * <p>DESCRIPTION:</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	public synchronized void stop() {
		if (listener != null) {
			listener = null;
			try {
				socket.close();
				objectOutputStream.close();
			}
			catch (IOException e) {
				logger.error("[" + this.getClass().getName() + ".stop()] " + e.getMessage(), e);
			}
		}
	}
	
	/**
	 * @since 2017-06-14
	 * <p>DESCRIPTION:</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	public void run() {
		MessageObject messageObject = null;
		
		while (isConnected) {
			try {
				messageObject = (MessageObject) objectInputStream.readObject();
				txtContent.append("[" + messageObject.getName() + "]" + messageObject.getMessage() + "\r\n");
			}
			catch (Exception e) {
				logger.error("[" + this.getClass().getName() + ".run()] " + e.getMessage(), e);
			}
		}
	}
	
	/**
	 * @param actionEvent [액션 이벤트]
	 * 
	 * @since 2017-06-14
	 * <p>DESCRIPTION:</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent actionEvent) {
		
		if (actionEvent.getSource() == btnConnect) {
			try {
				socket = new Socket(ip, port);
				objectInputStream = new ObjectInputStream(socket.getInputStream());
				objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			}
			catch (IOException e) {
				logger.error("[" + this.getClass().getName() + ".actionPerformed().btnConnect] " + e.getMessage(), e);
				return;
			}
			
			String message = "************** " + txtName.getText() + " has arrived! **************";
			MessageObject content = new MessageObject(1, txtName.getText(), message);
			broadcast(content);
			
			txtMessage.setText("");
			isConnected = true;
			listener = new Thread(this);
			listener.start();
			setChatMode(true);
		}
		else if (actionEvent.getSource() == btnDisconnect) {
			String message = "************** " + txtName.getText() + " has exit! **************";
			MessageObject co = new MessageObject(-1, txtName.getText(), message);
			try {
				objectOutputStream.writeObject(co);
				objectOutputStream.flush();
			}
			catch (IOException e) {
				
				logger.error("[" + this.getClass().getName() + ".actionPerformed().btnDisconnect1st] " + e.getMessage(), e);
				try {
					objectOutputStream.close();
					objectInputStream.close();
					socket.close();
				}
				catch (Exception ioe) {
					logger.error("[" + this.getClass().getName() + ".actionPerformed().btnDisconnect2nd] " + e.getMessage(), ioe);
				}
			}
			isConnected = false;
			if (listener != null) listener.stop();
			setChatMode(false);
		}
		else if (actionEvent.getSource() == txtMessage) {
			if (txtMessage.getText().equals("")) return;
			
			MessageObject content = new MessageObject(1, txtName.getText(), txtMessage.getText());
			broadcast(content);
			txtMessage.setText("");
		}
	}
	
	/**
	 * @param windowEvent [윈도우 이벤트]
	 * 
	 * @since 2017-06-14
	 * <p>DESCRIPTION:</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	@SuppressWarnings("deprecation")
	public void windowClosing(WindowEvent windowEvent) {
		
		String message = "************** " + txtName.getText() + " has exit! **************";
		MessageObject co = new MessageObject(-1, txtName.getText(), message);
		try {
			objectOutputStream.writeObject(co);
			objectOutputStream.flush();
		}
		catch (IOException e) {
			logger.error("[" + this.getClass().getName() + ".windowClosing()1st] " + e.getMessage(), e);
			try {
				objectOutputStream.close();
				objectInputStream.close();
				socket.close();
			}
			catch (Exception ioe) {	
				logger.error("[" + this.getClass().getName() + ".windowClosing()2nd] " + e.getMessage(), ioe);
			}
		}
		
		if (listener != null) listener.stop();
		setVisible(false);
		dispose();
		System.exit(0);
	}
	
	/**
	 * @param messageObject [메시지 객체]
	 * 
	 * @since 2017-06-14
	 * <p>DESCRIPTION:</p>
	 * <p>IMPORTANT:</p>
	 * <p>EXAMPLE:</p>
	 */
	protected void broadcast(MessageObject messageObject) {
		try {
			objectOutputStream.writeObject(messageObject);
			objectOutputStream.flush();
		}
		catch (IOException e) {
			logger.error("[" + this.getClass().getName() + ".broadcast()] " + e.getMessage(), e);
		}
	}
	
	public void windowActivated(WindowEvent evt) {}
	public void windowClosed(WindowEvent evt) {}
	public void windowDeactivated(WindowEvent evt) {}
	public void windowDeiconified(WindowEvent evt) {}
	public void windowIconified(WindowEvent evt) {}
	public void windowOpened(WindowEvent evt) {}
}
