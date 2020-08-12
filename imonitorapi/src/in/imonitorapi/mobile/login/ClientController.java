package in.imonitorapi.mobile.login;

import java.security.NoSuchAlgorithmException;

import in.imonitor.cms.communicator.IMonitorControllerCommunicator;
import in.imonitorapi.util.LogUtil;
import in.imonitorapi.util.Hash;
import java.util.HashMap;
import javax.xml.namespace.QName;
import com.sun.jersey.api.Responses;
import com.sun.jersey.api.container.MappableContainerException;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;



public class ClientController {
	
	public static String sendToController(String serviceName,String method, String... params) throws Exception{
		String address = IMonitorControllerCommunicator.getControllerAddress();		  
		String port = IMonitorControllerCommunicator.getControllerPort();
		String protocol = IMonitorControllerCommunicator.getControllerProtocol();
		String endpoint = protocol + "://" + address + ":" + port
		+ "/imonitorcontroller/services/"+serviceName;
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL(endpoint));
		call.setOperationName(new QName(method));
		String result = (String) call.invoke(params);
		return result;
		
	}


	public static String getHashPassword(String password) {
		String strHashPwd = ""; 
		try {
			strHashPwd = Hash.getHashText(password,"MD5");
			//strHashPwd = hash.
		} catch (NoSuchAlgorithmException e) {
			//e.printStackTrace();
		}
		return strHashPwd;
	}
	
	


	
}
