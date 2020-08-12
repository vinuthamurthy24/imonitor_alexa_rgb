package in.imonitorapi.alexa.eurovigil;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;

import in.imonitorapi.alexa.efl.communicator.EflClientController;
import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.util.LogUtil;

@Path("/acceptGrant")
public class EflAcceptGrant {

	
	@GET
	@Produces("application/xml")
	public Response getTokensForCustomer(@QueryParam("code")String code,@QueryParam("token")String token)throws Exception{
		//XStream stream = new XStream();
		LogUtil.info(" code: "+code+" token "+token);
			String out="<imonitor>";
			
			
		retriveAmazonSkillToken(code,token);
			
			
		
			
		return Response.status(200)
				.entity(out)
				.build();
		
	}
	
	public void retriveAmazonSkillToken(String code, String token) {
		LogUtil.info("step 1");
		
		String LWA = "https://api.amazon.com/auth/o2/token";
		try {
			
			URL obj = new URL(LWA);
			
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Host", "api.amazon.com");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			String parameters = "grant_type=authorization_code&code="+code+"&client_id=amzn1.application-oa2-client.754289313ed64fd2a13f46dd1adc54bc&client_secret=f82ba8f35bfa767f95017290b56560fdef2cde19385b53a74edb2d685498750d";
			
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(parameters);
			wr.flush();
			wr.close();
			int responseCode = con.getResponseCode();
			
			LogUtil.info("Response Code : " + responseCode);
			//LogUtil.info(con.getHeaderField("WWW-Authenticate"));
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			LogUtil.info("Response from amazon: " + response.toString());
			JSONObject json = new JSONObject(response.toString());
			String accessToken = json.getString("access_token");
			String refreshToken = json.getString("refresh_token");
			int expires = json.getInt("expires_in");
			expires = expires/60;
			String tokenType = json.getString("token_type");
			
			String result = EflClientController.sendToController("alexaService", "saveAccessTokensFromAmazonForEFLCustomer",accessToken,refreshToken,String.valueOf(expires),tokenType,code,token);
			LogUtil.info("result: " + result);
		    
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.info("Fail to get access token from amazon: "+ e.getMessage());
		}
	}
	
}
