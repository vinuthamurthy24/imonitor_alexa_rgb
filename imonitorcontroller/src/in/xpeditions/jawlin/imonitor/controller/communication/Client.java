/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.controller.communication;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * 
 */

/**
 * @author Coladi
 * 
 */
public class Client {

	/**
	 * @param args
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public static void request(String commands) throws UnknownHostException,
			IOException {
		SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory
				.getDefault();
		SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket(
				"localhost", 1900);
		OutputStream outputStream = sslsocket.getOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
		dataOutputStream.writeUTF(commands);
		InputStream inputStream = sslsocket.getInputStream();
		DataInputStream dataInputStream = new DataInputStream(inputStream);
		String readUTF = dataInputStream.readUTF();

		String property = "java.io.tmpdir";
		String tempDir = System.getProperty(property);

		String tempFileName = tempDir + "/my.properties";
		FileWriter fstream = new FileWriter(tempFileName);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(readUTF);
		out.close();

		Properties properties = new Properties();
		properties.load(new FileInputStream(new File(tempFileName)));
	}

}
