package in.monitor.authprovider;
import in.imonitorapi.mobile.login.OAuth2Exception;
import in.imonitorapi.mobile.login.RequestBaseVO;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
/*import com.sun.org.apache.xml.internal.security.utils.Base64;*/


import com.sun.jersey.core.util.Base64;

import Oauth2.imonitor.detail.OAuth2ErrorConstant;
public class Oauth2Response {

	

	

	
	public static String encodeURIComponent(String s) {
		String result;

		try {
			result = URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20")
					.replaceAll("\\%21", "!").replaceAll("\\%27", "'")
					.replaceAll("\\%28", "(").replaceAll("\\%29", ")")
					.replaceAll("\\%7E", "~");
		} catch (UnsupportedEncodingException e) {
			result = s;
		}

		return result;
	}

	public static String decodeURIComponent(String s) {
		if (s == null) {
			return null;
		}
		String result = null;
		try {
			result = URLDecoder.decode(s, "UTF-8");
		}
		// This exception should never occur.
		catch (UnsupportedEncodingException e) {
			result = s;
		}
		return result;
	}

	public static byte[] hexToBinary(String hex) {
		if (hex == null || hex.length() == 0) {
			return null;
		}

		byte[] ba = new byte[hex.length() / 2];
		for (int i = 0; i < ba.length; i++) {
			ba[i] = (byte) Integer
					.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return ba;
	}

	// byte[] to hex
	public static String binaryToHex(byte[] ba) {
		if (ba == null || ba.length == 0) {
			return null;
		}

		StringBuffer sb = new StringBuffer(ba.length * 2);
		String hexNumber;
		for (int x = 0; x < ba.length; x++) {
			hexNumber = "0" + Integer.toHexString(0xff & ba[x]);

			sb.append(hexNumber.substring(hexNumber.length() - 2));
		}
		return sb.toString();
	}

	public static String getHmacSha256(String str) {
		byte[] binary = null;
		try{
			MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
			sh.update(str.getBytes("UTF-8")); 
			binary = sh.digest();
		}catch(Exception e){
			e.printStackTrace(); 
		}

		return binaryToHex(binary);
	}

	/*public static String encodeBase64String(String data) {
		byte[] binary;
		try {
			binary = data.getBytes("UTF-8");
			return Base64.encode(binary);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}*/

	/*public static String decodeBase64String(String base64String) {
		try {
			byte[] binary = Base64.decode(base64String);
			return new String(binary, "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}*/

	
	/*public static String generateToken() {
		SecureRandom secureRandom;
		try {
			secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(secureRandom.generateSeed(256));
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			byte[] dig = digest.digest((secureRandom.nextLong() + "")
					.getBytes());
			return binaryToHex(dig);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}*/

	
	
	// Authorization : Basic XXXXXXXXXX
	public static String generateBasicAuthHeaderString(RequestBaseVO token) {
		try {
			String base = "";
			if (token.getClient_secret() == null
					|| token.getClient_secret() == "") {
				base = encodeURIComponent(token.getClient_id());
			} else {
				base = encodeURIComponent(token.getClient_id()) + ":"
						+ encodeURIComponent(token.getClient_secret());
			}

			return "Basic " + Base64.encode(base.getBytes("UTF-8"));

		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public static void parseBasicAuthHeader(String authHeader,
			RequestBaseVO token) throws OAuth2Exception {
		try {
			// Basic XXXXXXXXX
			String basicToken = authHeader.split(" ")[1];
			String decoded = new String(Base64.decode(basicToken), "utf-8");
			String[] temp = decoded.split(":");
			if (temp.length == 2) {
				token.setClient_id(temp[0]);
				token.setClient_secret(temp[1]);
			} else {
				token.setClient_id(temp[0]);
			}
		} catch (Exception e) {
			throw new OAuth2Exception(400, OAuth2ErrorConstant.INVALID_PARAMETER);
		}
	}

	public static String generateBearerToken(String access_token) {
		return "Bearer " + access_token;
	}

	public static String parseBearerToken(String authHeader) {
		return authHeader.split(" ")[1];
	}

	
	
	
}

