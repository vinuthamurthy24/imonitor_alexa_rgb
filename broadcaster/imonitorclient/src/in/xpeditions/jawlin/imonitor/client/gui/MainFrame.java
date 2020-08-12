/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.client.gui;

import in.xpeditions.jawlin.imonitor.client.listener.DoorLockDiscoverListener;
import in.xpeditions.jawlin.imonitor.client.listener.DoorLockOpenRegisterListener;
import in.xpeditions.jawlin.imonitor.client.listener.DoorSensorDiscoverListener;
import in.xpeditions.jawlin.imonitor.client.listener.DoorSensorListener;
import in.xpeditions.jawlin.imonitor.client.listener.RegisterListener;
import in.xpeditions.jawlin.imonitor.client.listener.RoomFourCameraDiscoverListener;
import in.xpeditions.jawlin.imonitor.client.listener.RoomFourLightDiscoverListener;
import in.xpeditions.jawlin.imonitor.client.listener.RoomFourLightListener;
import in.xpeditions.jawlin.imonitor.client.listener.RoomFourPanicDiscoverListener;
import in.xpeditions.jawlin.imonitor.client.listener.RoomFourPanicListener;
import in.xpeditions.jawlin.imonitor.client.listener.RoomFourRepeaterListener;
import in.xpeditions.jawlin.imonitor.client.listener.RoomOneAcDiscoverListener;
import in.xpeditions.jawlin.imonitor.client.listener.RoomOneDimmerDiscoverListener;
import in.xpeditions.jawlin.imonitor.client.listener.RoomOneLightDiscoverListener;
import in.xpeditions.jawlin.imonitor.client.listener.RoomOneLightListener;
import in.xpeditions.jawlin.imonitor.client.listener.RoomOneSirenListener;
import in.xpeditions.jawlin.imonitor.client.listener.RoomThreeCameraDiscoverListener;
import in.xpeditions.jawlin.imonitor.client.listener.RoomThreeLightDiscoverListener;
import in.xpeditions.jawlin.imonitor.client.listener.RoomThreeLightListener;
import in.xpeditions.jawlin.imonitor.client.listener.RoomThreeMotionDetectionListener;
import in.xpeditions.jawlin.imonitor.client.listener.RoomThreeMulBatteryListener;
import in.xpeditions.jawlin.imonitor.client.listener.RoomThreeMulListener;
import in.xpeditions.jawlin.imonitor.client.listener.RoomThreeMulSensorDiscoverListener;
import in.xpeditions.jawlin.imonitor.client.listener.RoomTwoLightDiscoverListener;
import in.xpeditions.jawlin.imonitor.client.listener.RoomTwoLightListener;
import in.xpeditions.jawlin.imonitor.client.listener.RoomTwoPirListener;
import in.xpeditions.jawlin.imonitor.client.listener.RoomTwoPirSensorDiscoverListener;
import in.xpeditions.jawlin.imonitor.client.util.Constants;
import in.xpeditions.jawlin.imonitor.client.util.MessageUtil;
import in.xpeditions.jawlin.imonitor.client.util.Util;

import java.awt.Color;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class MainFrame extends JFrame{

	private static final long serialVersionUID = -2529904980409175884L;


	public MainFrame() {
		super("iMVG :: Demo Client...");
		JPanel room1 = new JPanel();
		JPanel room2 = new JPanel();
		JPanel room3 = new JPanel();
		JPanel room4 = new JPanel();
		JPanel door1 = new JPanel();
		JPanel door2 = new JPanel();
		JPanel registerpanel = new JPanel();
		JPanel temp = new JPanel();



		JButton rDoor = new JButton("D Door");
		rDoor.setBounds(20, 30, 120, 20);
		MouseListener doorSensorDiscoverListener = new DoorSensorDiscoverListener();
		rDoor.addMouseListener(doorSensorDiscoverListener);
		
		JButton jbdoor = new JButton("Open Door");
		jbdoor.setBounds(20, 70, 120, 20);
		MouseListener doorSensorListener = new DoorSensorListener();
		jbdoor.addMouseListener(doorSensorListener);

		JButton room1light = new JButton(Constants.LIGHT_ON);
		room1light.setBounds(10, 75, 120, 20);
		Util.deviceIdButton.put(Constants.ROOM1BULB, room1light);
		MouseListener roomOneLightListener = new RoomOneLightListener();
		room1light.addMouseListener(roomOneLightListener);
		JButton room1Siren = new JButton("D Siren");
		MouseListener roomOneSirenListener = new RoomOneSirenListener();
		room1Siren.addMouseListener(roomOneSirenListener);
		room1Siren.setBounds(10, 52, 120, 20);
		
		JButton room1LightDiscover = new JButton("D Light");
		room1LightDiscover.setBounds(10, 5, 120, 20);
		MouseListener roomOneLightDiscoverListener = new RoomOneLightDiscoverListener();
		room1LightDiscover.addMouseListener(roomOneLightDiscoverListener);
		JButton room1DimmerDiscover = new JButton("D Dimmer");
		room1DimmerDiscover.setBounds(10, 30, 120, 20);
		MouseListener roomOneDimmerDiscoverListener = new RoomOneDimmerDiscoverListener();
		room1DimmerDiscover.addMouseListener(roomOneDimmerDiscoverListener);
//		A/C in room 1
		JButton dAC = new JButton("D AC");
		dAC.setBounds(10,100, 120,20);
		MouseListener roomOneAcDiscoverListener = new RoomOneAcDiscoverListener();
		dAC.addMouseListener(roomOneAcDiscoverListener);
		
		JButton room2light = new JButton(Constants.LIGHT_ON);
		room2light.setBounds(10, 75, 120, 20);
		Util.deviceIdButton.put(Constants.ROOM2BULB, room2light);
		MouseListener roomTwoLightListener = new RoomTwoLightListener();
		room2light.addMouseListener( roomTwoLightListener );
		
		JButton room2PirUp = new JButton(Constants.PIR_UP);
		room2PirUp.setBounds(10, 98, 120, 20);
		MouseListener roomTwoPirListener = new RoomTwoPirListener();
		room2PirUp.addMouseListener( roomTwoPirListener );
		
		JButton room2PIRSensor = new JButton("D PIR Sensr");
		MouseListener roomTwoPirSensorDiscoverListener = new RoomTwoPirSensorDiscoverListener();
		room2PIRSensor.addMouseListener(roomTwoPirSensorDiscoverListener);
		room2PIRSensor.setBounds(10, 52, 120, 20);
		
		JButton room2LightDiscover = new JButton("D Light");
		room2LightDiscover.setBounds(10, 5, 120, 20);
		room2LightDiscover.addMouseListener(new RoomTwoLightDiscoverListener());
		JButton room2FanDiscover = new JButton("D Dimmer");
		room2FanDiscover.setBounds(10, 30, 120, 20);

		JButton room3light = new JButton(Constants.LIGHT_ON);
		room3light.setBounds(10, 75, 120, 20);
		Util.deviceIdButton.put(Constants.ROOM3BULB, room3light);
		MouseListener roomThreeLightListener = new RoomThreeLightListener();
		room3light.addMouseListener(roomThreeLightListener );

		JButton room3MulUp = new JButton(Constants.PIR_UP);
		room3MulUp.setBounds(10, 98, 120, 20);
		MouseListener roomThreeMulListener = new RoomThreeMulListener();
		room3MulUp.addMouseListener( roomThreeMulListener );
		
		JButton room3MulBattery = new JButton("Battery !");
		room3MulBattery.setBounds(10, 120, 120, 20);
		MouseListener roomThreeMulBatteryListener = new RoomThreeMulBatteryListener();
		room3MulBattery.addMouseListener( roomThreeMulBatteryListener );
		
		JButton room3MulSensor = new JButton("D Mul Sensr");
		MouseListener roomThreeMulSensorDiscoverListener = new RoomThreeMulSensorDiscoverListener();
		room3MulSensor.addMouseListener(roomThreeMulSensorDiscoverListener);
		room3MulSensor.setBounds(10, 52, 120, 20);
		
		JButton room3LightDiscover = new JButton("D Light");
		room3LightDiscover.setBounds(10, 5, 120, 20);
		room3LightDiscover.addMouseListener(new RoomThreeLightDiscoverListener());
		JButton room3CameraDiscover = new JButton("D Camera");
		room3CameraDiscover.setBounds(10, 30, 120, 20);
		room3CameraDiscover.addMouseListener(new RoomThreeCameraDiscoverListener());

		JButton motionDetect = new JButton("Motion");
		motionDetect.setBounds(10, 142, 120, 20);
		MouseListener roomThreeMotionDetectionListener = new RoomThreeMotionDetectionListener();
		motionDetect.addMouseListener(roomThreeMotionDetectionListener);
		
		JButton room4light = new JButton(Constants.LIGHT_ON);
		room4light.setBounds(10, 75, 120, 20);
		Util.deviceIdButton.put(Constants.ROOM4BULB, room4light);
		MouseListener roomFourLightListener = new RoomFourLightListener();
		room4light.addMouseListener(roomFourLightListener);
		
		JButton room4PanicD = new JButton("D Panic");
		room4PanicD.setBounds(10, 100, 120, 20);
		MouseListener roomFourPanicDiscoverListener = new RoomFourPanicDiscoverListener();
		room4PanicD.addMouseListener(roomFourPanicDiscoverListener);
		
		JButton room4Panic = new JButton("Panic");
		room4Panic.setBounds(10, 125, 120, 20);
		MouseListener roomFourPanicListener = new RoomFourPanicListener();
		room4Panic.addMouseListener(roomFourPanicListener);
		
		JButton room4Repeater = new JButton("D Repeater");
		MouseListener roomFourRepeaterListener = new RoomFourRepeaterListener();
		room4Repeater.addMouseListener(roomFourRepeaterListener);
		room4Repeater.setBounds(10, 52, 120, 20);
		
		JButton room4LightDiscover = new JButton("D Light");
		room4LightDiscover.setBounds(10, 5, 120, 20);
		room4LightDiscover.addMouseListener(new RoomFourLightDiscoverListener());
		JButton room4CameraDiscover = new JButton("D Camera");
		room4CameraDiscover.addMouseListener(new RoomFourCameraDiscoverListener());
		room4CameraDiscover.setBounds(10, 30, 120, 20);

		room1.add(room1light);
		room1.add(room1LightDiscover);
		room1.add(room1DimmerDiscover);
		room1.add(room1Siren);
		room1.add(dAC);
		room1.setLayout(null);
		room1.setBounds(20, 50, 150, 200);
		room1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		room1.setBackground(Color.GRAY);
		room1.setVisible(true);
		this.add(room1);

		room2.add(room2light);
		room2.add(room2LightDiscover);
		room2.add(room2FanDiscover);
		room2.add(room2PIRSensor);
		room2.add(room2PirUp);
		room2.setLayout(null);
		room2.setBounds(400, 50, 150, 200);
		room2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		room2.setBackground(Color.GRAY);
		room2.setVisible(true);
		this.add(room2);

		room3.add(room3light);
		room3.add(room3LightDiscover);
		room3.add(room3CameraDiscover);
		room3.add(room3MulSensor);
		room3.add(room3MulUp);
		room3.add(room3MulBattery);
		room3.add(motionDetect);
		room3.setLayout(null);
		room3.setBounds(400, 270, 150, 200);
		room3.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		room3.setBackground(Color.GRAY);
		room3.setVisible(true);
		this.add(room3);

		room4.add(room4light);
		room4.add(room4LightDiscover);
		room4.add(room4CameraDiscover);
		room4.add(room4Repeater);
		room4.add(room4PanicD);
		room4.add(room4Panic);
		room4.setLayout(null);
		room4.setBounds(20, 270, 150, 200);
		room4.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		room4.setBackground(Color.GRAY);
		room4.setVisible(true);
		this.add(room4);

		door1.add(jbdoor);
		door1.add(rDoor);
		door1.setLayout(null);
		door1.setBounds(200, 0, 150, 120);
		door1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		door1.setVisible(true);
		this.add(door1);

		//DOOR_LOCK_CLOSE
		JButton doorNumLock = new JButton();
		doorNumLock.setBounds(20, 10, 120, 20);
		doorNumLock.setText("D Door Lock");
		MouseListener doorLockDiscoverListener = new DoorLockDiscoverListener();
		doorNumLock.addMouseListener(doorLockDiscoverListener);
		
		JButton doorNumLockOpen = new JButton();
		doorNumLockOpen.setBounds(20, 40, 120, 20);
		doorNumLockOpen.setText("Open Door");
		MouseListener doorLockOpenRegisterListener = new DoorLockOpenRegisterListener();
		doorNumLockOpen.addMouseListener(doorLockOpenRegisterListener);
		
		JTextField inputParamText = new JTextField("40");
		inputParamText.setBounds(20, 65, 120, 20);
		MessageUtil.inputParamText = inputParamText;
		
		door2.add(doorNumLock);
		door2.add(doorNumLockOpen);
		door2.add(inputParamText);
		door2.setLayout(null);
		door2.setBounds(200, 121, 150, 120);
		door2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		door2.setVisible(true);
		this.add(door2);
		
//		Registration part starts here.
		
		JLabel macIdLabel = new JLabel("Mac Id");
		macIdLabel.setBounds(20, 30, 50, 20);
		registerpanel.add(macIdLabel);
		
		JTextField macIdTextField = new JTextField();
		macIdTextField.setBounds(75, 30, 120, 20);
		macIdTextField.setText(MessageUtil.macId);
		registerpanel.add(macIdTextField);
		
		JLabel ipLabel = new JLabel("Address");
		ipLabel.setBounds(20, 60, 50, 20);
		registerpanel.add(ipLabel);
		
		JTextField ipTextField = new JTextField();
		ipTextField.setBounds(75, 60, 120, 20);
		ipTextField.setText("192.168.2.5");
		registerpanel.add(ipTextField);
		
		JLabel portLabel = new JLabel("Port");
		portLabel.setBounds(20, 90, 50, 20);
		registerpanel.add(portLabel);
		
		JTextField portTextField = new JTextField();
		portTextField.setBounds(75, 90, 120, 20);
		portTextField.setText("1900");
		registerpanel.add(portTextField);
		
		JButton register = new JButton("Register");
		register.setBounds(5, 120, 100, 20);
		MouseListener registerListener = new RegisterListener(ipTextField, portTextField, macIdTextField);
		register.addMouseListener(registerListener);
		
		registerpanel.add(register);
		registerpanel.setLayout(null);
		registerpanel.setBounds(177, 400, 210, 160);
		registerpanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		registerpanel.setVisible(true);
		this.add(registerpanel);
//		Registration part ends here.

//		Starting Device Discovery mode.
		JLabel deviceDiscoveryLabel = new JLabel("Device Discovery Mode");
		deviceDiscoveryLabel.setBounds(10, 10, 150, 10);
		deviceDiscoveryLabel.setForeground(Color.red);
		this.add(deviceDiscoveryLabel);
		Util.DEVICE_DISCOVERY_LABEL = deviceDiscoveryLabel;
		deviceDiscoveryLabel.setVisible(false);
		
		this.add(temp);
		this.setVisible(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
}
