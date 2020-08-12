/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.client.listener;

import in.xpeditions.jawlin.imonitor.client.communicator.MessageReader;
import in.xpeditions.jawlin.imonitor.client.communicator.MessageSender;
import in.xpeditions.jawlin.imonitor.client.util.Constants;
import in.xpeditions.jawlin.imonitor.client.util.KeepAliver;
import in.xpeditions.jawlin.imonitor.client.util.MessageUtil;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 * @author Coladi
 * 
 */
public class RegisterListener implements MouseListener {

	Map<String, JButton>buttonMap;
	private JTextField ipTextField;
	private JTextField portTextField;
	private JTextField macIdTextField;

	public RegisterListener(JTextField ipTextField, JTextField portTextField, JTextField macIdTextField) {
		this.ipTextField = ipTextField;
		this.portTextField = portTextField;
		this.macIdTextField = macIdTextField;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			
			String address = this.ipTextField.getText();
			String sPort = this.portTextField.getText();
			MessageUtil.macId = this.macIdTextField.getText();
			
			int port = Integer.parseInt(sPort);
			
			SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory
					.getDefault();
			SSLSocket primarySocket = (SSLSocket) sslsocketfactory.createSocket(
					address, port);
//			Socket primarySocket = new Socket(address, port);
			
			OutputStream outputStream = primarySocket.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(
					outputStream);
//			1. Send IMVG register message.
			String registerMessage = MessageUtil.createRegisterMessage();
			dataOutputStream.writeUTF(registerMessage);
			InputStream inputStream = primarySocket.getInputStream();
			DataInputStream dataInputStream = new DataInputStream(inputStream);
			String readUTF = dataInputStream.readUTF();
			System.out.println(readUTF);
			String property = "java.io.tmpdir";
			String tempDir = System.getProperty(property);
			String tempFileName = tempDir + "/my.properties";
			FileWriter fstream = new FileWriter(tempFileName);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(readUTF);
			out.close();

			Properties properties = new Properties();
			properties.load(new FileInputStream(new File(tempFileName)));
			String ipnPort = properties.getProperty("PRIMARY_SERVER_IP_PORT");
			String[] ipnPortArray = ipnPort.split(":");
			int eventPort = Integer.parseInt(ipnPortArray[1]);
			String primaryIP = ipnPortArray[0];
			
//			2. Connect to the event server
			SSLSocket eventSocket = (SSLSocket) sslsocketfactory.createSocket(
					primaryIP, eventPort);
//			Socket eventSocket = new Socket(primaryIP, eventPort);
//			Set the message writter.
			OutputStream outputStream2 = eventSocket.getOutputStream();
			DataOutputStream dataOutputStream2 = new DataOutputStream(
					outputStream2);
			MessageSender.setDataOutputStream(dataOutputStream2);
//			First send the iMVG Up message.
			String deviceAlertMessage = MessageUtil.createDeviceAlertMessage(MessageUtil.macId, Constants.IMVG_UP);
			try {
				MessageSender.sendMessage(deviceAlertMessage);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
//			Listening for messages.
			InputStream inputStream2 = eventSocket.getInputStream();
			DataInputStream dataInputStream2 = new DataInputStream(inputStream2);
			MessageReader messageReader = new MessageReader(dataInputStream2);
			Thread t = new Thread(messageReader);
			t.start();
			
//			KEEP_ALIVE 
			KeepAliver keepAliver = new KeepAliver();
			Thread aliveThread = new Thread(keepAliver);
			aliveThread.start();
			
		} catch (Exception er) {
			er.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {

	}

}
