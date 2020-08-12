/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.client.listener;

import in.xpeditions.jawlin.imonitor.client.communicator.MessageSender;
import in.xpeditions.jawlin.imonitor.client.util.Constants;
import in.xpeditions.jawlin.imonitor.client.util.MessageUtil;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class RoomThreeLightDiscoverListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		String deviceDiscoveredMessage = MessageUtil.createDeviceDiscoveredMessage(Constants.ROOM3BULB, Constants.Z_WAVE_SWITCH, "Model 1");
		try {
			MessageSender.sendMessage(deviceDiscoveredMessage);
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
