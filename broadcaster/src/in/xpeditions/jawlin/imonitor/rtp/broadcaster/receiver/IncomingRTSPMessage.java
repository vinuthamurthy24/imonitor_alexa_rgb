/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.rtp.broadcaster.receiver;

import in.xpeditions.jawlin.imonitor.rtp.broadcaster.util.Constants;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * @author Coladi
 *
 */
public class IncomingRTSPMessage {
	
	private String message;
	private String method;
	private String session;
	private String seqNo;
	private String url;
	private int methodRep;
	
	public IncomingRTSPMessage(String rtspMessage) {
		if(rtspMessage != null){
			try{
			this.message = rtspMessage;
//			Realplayer is sending OPTIONS request (not first time) in between some other characters.
//			Doing some workaround to correct it.
			if(rtspMessage.contains(Constants.OPTIONS)){
				rtspMessage = extractProperMessage(Constants.OPTIONS);
			}
			String[] split = rtspMessage.split(Constants.CRLF);
			String methodLine = split[0];
			StringTokenizer tokens = new StringTokenizer(methodLine);
			this.method = tokens.nextToken();
			this.methodRep = findMethodRep(this.method);
			this.url = tokens.nextToken();
			for (int i = 0; i < split.length; i++) {
				String messageLine = split[i];
//				Extracting the sequence no:
				if(messageLine.startsWith("CSeq")){
					tokens = new StringTokenizer(messageLine);
					tokens.nextToken(); // Skip CSeq.
					this.seqNo = tokens.nextToken();
				}
			}
			}catch(NoSuchElementException ex){
				this.methodRep = Constants.METHOD_INVALID;
			}
		}
	}

	private String extractProperMessage(String options) {
		int index = this.message.indexOf(options);
		return this.message.substring(index);
	}

	private int findMethodRep(String method) {
		if(method.compareTo(Constants.OPTIONS) == 0){
			return Constants.OPTIONS_REP;
		}
		if(method.compareTo(Constants.DESCRIBE) == 0){
			return Constants.DESCRIBE_REP;
		}
		if(method.compareTo(Constants.SETUP) == 0){
			return Constants.SETUP_REP;
		}
		if(method.compareTo(Constants.PLAY) == 0){
			return Constants.PLAY_REP;
		}
		if(method.compareTo(Constants.TEARDOWN) == 0){
			return Constants.TEARDOWN_REP;
		}
		return Constants.METHOD_INVALID;
	}

	public String getMessage() {
		return message;
	}

	public String getMethod() {
		return method;
	}

	public String getSession() {
		return session;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public int getMethodRep() {
		return methodRep;
	}

	public String getUrl() {
		return url;
	}
	
}
