/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.client.util;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Coladi
 *
 */
public class Util {

	public static JLabel DEVICE_DISCOVERY_LABEL;
	public static Map<String, String> transactionIdDevice = new HashMap<String, String>();
	public static Map<String, String> generateDevieIdDevice = new HashMap<String, String>();
	public static Map<String, JButton> deviceIdButton = new HashMap<String, JButton>();
	public static Map<String, String> deviceGenerateDevieId = new HashMap<String, String>();
	
	public static void handleLightButtonClick(JButton jButton,String action) {
		try{
		JPanel jPanel = (JPanel) jButton.getParent();
		if(action.equals(Constants.DEVICE_UP)){
			jButton.setText(Constants.LIGHT_OFF);
			jPanel.setBackground(Color.WHITE);
		} else if(action.equals(Constants.DEVICE_DOWN)) {
			jButton.setText(Constants.LIGHT_ON);
			jPanel.setBackground(Color.GRAY);
		}
		}catch (Exception e) {
//			Don't worry about it ...
			e.printStackTrace();
		}
	}
	public static Map<String,String> convertToMapFromCommand(String command){
		Map<String,String> map=new HashMap<String, String>();
		String[]items;
		String[] eachItems;
		items=command.split(System.getProperty("line.separator"));
		for(int i=0;i<items.length;i++){
			eachItems=items[i].split("=");
			map.put(eachItems[0], eachItems[1]);
		}
		return map;
	}

}
