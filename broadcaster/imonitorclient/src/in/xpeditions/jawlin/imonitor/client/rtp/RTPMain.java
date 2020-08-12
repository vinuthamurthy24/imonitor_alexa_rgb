/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.client.rtp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Coladi
 *
 */
public class RTPMain {

	final static String CRLF = "\r\n";
	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket streamingSocket = new Socket("localhost", 3900);
		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(streamingSocket.getOutputStream()));
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(streamingSocket.getInputStream()));
		String readLine = bufferedReader.readLine();
		while(!readLine.isEmpty()){
			System.out.println(readLine);
			readLine = bufferedReader.readLine();
		}
		String message = "";
		message += "RTSP/1.0 200 OK" + CRLF;
		message += "Server: QTSS(IFI)" + CRLF;
		message += "Cseq: 2" + CRLF;
		message += "Content-Type: rtsp://192.168.0.1/<imvgid>/<ipcamid>.live" + CRLF;
		message += "Content-length: 91" + CRLF;
		message += CRLF;
		message += "o=- 00019200204601407700 9410 IN IP4 192.168.0.101" + CRLF;
		message += "a=control:trackID=1" + CRLF;
		message += "a=x-framerate:10" + CRLF;

		bufferedWriter.write(message);
		bufferedWriter.flush();
		readLine = bufferedReader.readLine();
		while(!readLine.isEmpty()){
			System.out.println(readLine);
			readLine = bufferedReader.readLine();
		}
		
		message = "";
		message += "RTSP/1.0 200 OK" + CRLF;
		message += "Server: QTSS(IFI)/v88" + CRLF;
		message += "Cseq: 2" + CRLF;
		message += "Session: 1234567890;timeout=60" + CRLF;
		message += "Transport: rtp/avp;source=129.240.65.208;server_port=9000-9001;client_port=9000-9001" + CRLF;
		message += CRLF;
		
		bufferedWriter.write(message);
		bufferedWriter.flush();
		readLine = bufferedReader.readLine();
		while(!readLine.isEmpty()){
			System.out.println(readLine);
			readLine = bufferedReader.readLine();
		}
		message = "";
		message += "RTSP/1.0 200 OK" + CRLF;
		message += "Server: QTSS(IFI)/v88" + CRLF;
		message += "Cseq: 6" + CRLF;
		message += "Session: 537103309" + CRLF;
		message += CRLF;
		
		bufferedWriter.write(message);
		bufferedWriter.flush();
		while(!readLine.isEmpty()){
			System.out.println(readLine);
			readLine = bufferedReader.readLine();
			System.out.println(readLine);
		}
		
		
		message = "$02AA";
		bufferedWriter.write(message);
		bufferedWriter.flush();
		System.out.println(message);
//		while(true){
//		}
	}

}
