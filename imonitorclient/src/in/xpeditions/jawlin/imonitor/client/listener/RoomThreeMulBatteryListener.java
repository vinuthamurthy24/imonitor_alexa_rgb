/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.client.listener;

import in.xpeditions.jawlin.imonitor.client.communicator.MessageSender;
import in.xpeditions.jawlin.imonitor.client.util.Constants;
import in.xpeditions.jawlin.imonitor.client.util.MessageUtil;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JButton;

public class RoomThreeMulBatteryListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent arg0) {
		String deviceAlertMessage = MessageUtil.createBatteryStatusMessage(Constants.ROOM3MUL);
		try {
			MessageSender.sendMessage(deviceAlertMessage);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
