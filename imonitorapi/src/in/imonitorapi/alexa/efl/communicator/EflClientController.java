package in.imonitorapi.alexa.efl.communicator;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import in.imonitor.cms.communicator.IMonitorControllerCommunicator;
import in.imonitorapi.util.LogUtil;

public class EflClientController {
	
	
	public static String sendToController(String serviceName,String method, String... params) throws Exception{
		String address = IMonitorControllerCommunicator.getControllerAddress();		  
		String port = IMonitorControllerCommunicator.geteflControllerPort();
		String protocol = IMonitorControllerCommunicator.getControllerProtocol();
		String endpoint = protocol + "://" + address + ":" + port
		+ "/imonitorcontroller/services/"+serviceName;
		LogUtil.info("end point: " + endpoint);
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new java.net.URL(endpoint));
		call.setOperationName(new QName(method));
		String result = (String) call.invoke(params);
		return result;
		
	}

}
