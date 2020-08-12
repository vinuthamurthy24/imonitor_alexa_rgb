package in.xpeditions.jawlin.imonitor.server.core;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.xml.rpc.ServiceException;

import org.apache.axis.utils.IOUtils;

import com.thoughtworks.xstream.XStream;

import in.xpeditions.generic.util.LogUtil;
import in.xpeditions.jawlin.imonitor.server.command.inft.CommandExecutor;
import in.xpeditions.jawlin.imonitor.server.communicator.ImonitorControllerCommunicator;
import in.xpeditions.jawlin.imonitor.server.util.Constants;
import in.xpeditions.jawlin.imonitor.server.util.IMonitorUtil;
import in.xpeditions.jawlin.imonitor.server.util.KeyValuePair;

public class ServerOneCameraClient implements Runnable{

	private Socket socket;
	private CommandExecutor commandExecutor;
	private String imvgId;
	private long timeOut = 60000;
	private String deviceId;
	BufferedImage image;
	private String ruleId;
	boolean connection=true; 
	int total = 0;
	int b =0;
	final InputStream dis;
	final DataOutputStream dos;
	ByteArrayOutputStream stream = new ByteArrayOutputStream();
	private String timeStamp;

	public ServerOneCameraClient(Socket socket, InputStream dis, DataOutputStream dos) {
		this.socket = socket;
		this.dis = dis;
		this.dos = dos;
		
		 Date date = new Date();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); 
		 timeStamp = sdf.format(date);
    	 LogUtil.info("formatted date:"+ timeStamp);
	}

	@Override
	public void run() {

		Queue<KeyValuePair> extractCommandQueue = null;
		LogUtil.info("first message receiving "  );
		
		try {
			
			        /*Message format
			         * IMVG_ID=30:ae:a4:43:bd:d0
					DEVICE_ID=30:ae:a4:43:bd:d0-00:95:69:24:31:30
					RULE_ID=2037*/

			
			DataInputStream dataInputStream = new DataInputStream(dis);
			String messageFromClient = dataInputStream.readUTF();
			LogUtil.info("Message From Client:\n" + messageFromClient);
			extractCommandQueue = IMonitorUtil
					.commandToQueue(messageFromClient);
			imvgId = IMonitorUtil.commandId(extractCommandQueue,
					Constants.IMVG_ID);
			deviceId = IMonitorUtil.commandId(extractCommandQueue,
					Constants.DEVICE_ID);
			ruleId = IMonitorUtil.commandId(extractCommandQueue,
					Constants.RULE_ID);
			
			/* byte[] deviceBuffer = new byte[50];
			 dis.read(deviceBuffer);
			 deviceId = new String(deviceBuffer);
      	     LogUtil.info("device Info : " + deviceId);*/
      	     dos.writeUTF("Success");
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.info("exception while reading data: " + e.getMessage());
		}
		
	//	while(connection) {
		int nRead;
		byte[] data = new byte[1024];
		LogUtil.info("start reading");
		try {
			while ((nRead = dis.read(data, 0, data.length)) != -1) {
				stream.write(data, 0, nRead);
			 	
			}
			stream.flush();
			byte[] byteArray = stream.toByteArray();
			LogUtil.info("buffer size: " + byteArray.length);

			String customerId = getCustomerIdByDeviceId();
			String imageName = generateFileName()+".jpeg";
		//	String filePath =  ImonitorControllerCommunicator.getImvgUploadPath()+customerId+"/"+generateFileName()+".jpeg";
			String filePath =  "/home/demoftp/"+customerId+"/images/"+imageName;
			LogUtil.info("file path: " + filePath);
			try {
        		        		 
        		 BufferedImage image = ImageIO.read( new ByteArrayInputStream( byteArray ) );
        		 if(image == null) {
        			 LogUtil.info("Image is null" );
        		 }
        		
        		 ImageIO.write(image, "jpeg", new File(filePath));
        		 
        		 //Updating to Controller
        		 updateImagePathtoController(customerId,imageName,timeStamp);
        		 
        		 connection=false;
        		 LogUtil.info("Image created" );
			} catch (Exception e) {
				System.out.println("Exception ocurred while creating image -->" + e.getMessage() + "<--");
				connection =false;
			}
			
			



		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	//	}



	}


	public String generateFileName() {

		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) 
					(random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		String generatedString = buffer.toString();

		LogUtil.info("generated file name" + generatedString);
		return generatedString;
	}

public String getCustomerIdByDeviceId() throws MalformedURLException, RemoteException, ServiceException {
	String customer = null;
	XStream stream = new XStream();
	String xml = stream.toXML(this.imvgId);
	customer = IMonitorUtil.sendToController("customerService", "getCustomerIdByDeviceId", xml);
	LogUtil.info("customer Id is : " + customer);
	
	return customer;
	
}

public void updateImagePathtoController(String customerId,String imageName,String timeStamp) {
	
	try {
		IMonitorUtil.sendToController("alertsFromImvgService", "upDateImageFTPSuccessForDevice", customerId,this.deviceId,imageName,timeStamp,this.ruleId);
	
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ServiceException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}


}
