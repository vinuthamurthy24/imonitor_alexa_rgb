/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.client.listener;

import in.xpeditions.jawlin.imonitor.client.communicator.MessageSender;
import in.xpeditions.jawlin.imonitor.client.util.Constants;
import in.xpeditions.jawlin.imonitor.client.util.MessageUtil;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JButton;

public class RoomTwoPirListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		JButton jButton = (JButton) e.getSource();
		String currentState = jButton.getText();
		String action = currentState.equals(Constants.PIR_UP) ? Constants.DEVICE_UP: Constants.DEVICE_DOWN;
		jButton.setText(currentState.equalsIgnoreCase(Constants.PIR_UP) ? Constants.PIR_DOWN : Constants.PIR_UP);
		String deviceAlertMessage = MessageUtil.createDeviceAlertMessage(Constants.ROOM2PIR, action);
		try {
			MessageSender.sendMessage(deviceAlertMessage);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
