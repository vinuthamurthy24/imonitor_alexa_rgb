package in.imonitorapi.service.manager;


import in.imonitorapi.mobile.login.ClientController;
import in.imonitorapi.util.Constants;
import in.imonitorapi.util.LogUtil;

import java.math.BigInteger;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.thoughtworks.xstream.XStream;

@Path("/account/{customerId}")
public class CustomeraccountDetails {
	

	
	
	@GET
	@Produces("application/xml")
	public Response getCustomeraccountDetails(@HeaderParam("Client_Id") String Client_id,@HeaderParam("UserId") String UserId,@HeaderParam("password") String password,@PathParam("customerId") String id)throws Exception{
		
		XStream stream = new XStream();
		//LogUtil.info("client id: "+ Client_id+UserId+password);
		String out="<imonitor>";
		if((!Client_id.equalsIgnoreCase(Constants.TPDDL))){
			//LogUtil.info("pass");
			out+="failure</imonitor>";
		}else{
			
			String result=ClientController.sendToController("customerService","loginpasscheckapi",UserId,password,id);
			//LogUtil.info(result+"output");
			String user=ClientController.sendToController("customerService", "userdetailsapi",UserId);
			List<Object[]> list1=(List<Object[]>) stream.fromXML(user);
			String status=null;
			BigInteger bi;
			Integer mode;
			if(list1!= null){
			for(Object[] obj:list1){
			bi=(BigInteger) obj[4];
			mode=bi.intValue();
				if(mode==0){
					status="Home";
				}else if(mode==1){
					status="Stay";
				}else if(mode==2){
					status="Away";
				}
				out+="<userId>"+obj[0]+"</userId>"+"<lastlogin>"+obj[1]+"</lastlogin>"+"<gateway>"+"<macId>"+obj[2]+"</macId>"+"<status>"+obj[3]+"</status>"+"<currentmode>"+status+"</currentmode>"+"<devices>";
			}
			List<Object[]> list=(List<Object[]>) stream.fromXML(result);
			for(Object[] obj:list){
				String devicetype=(String) obj[5];
				out+="<device>"+"<generatedDeviceId>"+obj[0]+"</generatedDeviceId>"+"<name>"+obj[1]+"</name>"+"<batterystatus>"+obj[2]+"</batterystatus>"+"<enablelist>"+obj[3]+"</enablelist>"+"<modelnumber>"+obj[4]+"</modelnumber>"
						+"<type>"+obj[5]+"</type>"+"<location>"+obj[6]+"</location>"+"<commandparam>"+obj[7]+"</commandparam>"+"<switchtype>"+obj[8]+"</switchtype>";
				if(devicetype.contains(Constants.IP_CAMERA)){
					out+="<deviceconfig>"+"<pantiltcontrol>"+obj[24]+"</pantiltcontrol>"+"<controlpantilt>"+obj[25]+"</controlpantilt>"+"<controlnightvision>"+obj[26]+"</controlnightvision>"+"<presetvalue>"+obj[27]+"</presetvalue>"+"<cameraorientation>"+obj[28]+"</cameraorientation>"+"</deviceconfig>";
				}else if(devicetype.contains(Constants.Z_WAVE_AC_EXTENDER)){
					out+="<deviceconfig>"+"<pollinginterval>"+obj[16]+"</pollinginterval>"+"<fanmodevalue>"+obj[17]+"</fanmodevalue>"+"<fanmode>"+obj[18]+"</fanmode>"+"<acswing>"+obj[19]+"</acswing>"+"<aclearnvalue>"+obj[20]+"</aclearnvalue>"+"<sensedtemperature>"+obj[21]+"</sensedtemperature>"+"</deviceconfig>";
				}else if(devicetype.contains(Constants.Z_WAVE_MINIMOTE)){
					out+="<deviceconfig>"+"<buttonone>"+obj[9]+"</buttonone>"+"<buttontwo>"+obj[10]+"</buttontwo>"+"<buttonthree>"+obj[11]+"</buttonthree>"+"<buttonfour>"+obj[12]+"</buttonfour>"+"</deviceconfig>";
				}else if(devicetype.contains(Constants.Z_WAVE_LCD_REMOTE)){
					out+="<deviceconfig>"+"<f1>"+obj[13]+"</f1>"+"<f2>"+obj[14]+"</f2>"+"<f3>"+obj[15]+"</f3>"+"</deviceconfig>";
				}else if(devicetype.contains(Constants.Z_WAVE_MOTOR_CONTROLLER)){
					out+="<deviceconfig>"+"<length>"+obj[22]+"</length>"+"<mountingtype>"+obj[23]+"</mountingtype>"+"</deviceconfig>";
				}else{
					
				}
				out+="</device>";
			}
				out+="</devices>"+"</gateway>"+"</imonitor>";
				}else{
					out+="failure</imonitor>";
					}
		}
		return Response.status(200)
				.entity(out)
				.build();
		}
		
	}

