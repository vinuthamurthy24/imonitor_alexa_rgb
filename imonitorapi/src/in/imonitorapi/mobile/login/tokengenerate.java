package in.imonitorapi.mobile.login;

import in.imonitorapi.util.LogUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/resquest_token")
public class tokengenerate {

	 @GET
	 @Produces(MediaType.TEXT_XML)
	public static String generateRequestToken() {
		SecureRandom secureRandom;
		try {
			secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(secureRandom.generateSeed(256));
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			
			byte[] dig = digest.digest((secureRandom.nextLong() + "")
					.getBytes());
			
			LogUtil.info("this is response: "+dig);
			String Rtoken=binaryToHex(dig);
			/*StoreAccessToken.saveToken(Rtoken);*/
			return "<ims>Access_token="+binaryToHex(dig)+"</ims>";
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	 
	 
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
}
