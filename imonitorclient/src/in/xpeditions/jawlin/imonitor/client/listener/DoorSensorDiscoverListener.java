/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.client.listener;

import in.xpeditions.jawlin.imonitor.client.communicator.MessageSender;
import in.xpeditions.jawlin.imonitor.client.util.Constants;
import in.xpeditions.jawlin.imonitor.client.util.MessageUtil;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

/**
 * @author Coladi
 *
 */
public class DoorSensorDiscoverListener implements MouseListener {

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		String deviceDiscoveredMessage = MessageUtil.createDeviceDiscoveredMessage(Constants.DOOR_1, Constants.Z_WAVE_DOOR_SENSOR, "Model 1");
		try {
			MessageSender.sendMessage(deviceDiscoveredMessage);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
