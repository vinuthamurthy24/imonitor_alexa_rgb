/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.manager;

import in.xpeditions.jawlin.imonitor.controller.dao.entity.CameraConfiguration;
import in.xpeditions.jawlin.imonitor.controller.dao.entity.H264CameraConfiguration;
import in.xpeditions.jawlin.imonitor.controller.log.util.LogUtil;

/**
 * @author Coladi
 * 
 */
public class CameraConfigurationManager {

	private DaoManager daoManager = new DaoManager();

	public boolean saveCameraConfiguration(
			CameraConfiguration cameraConfiguration) {
		boolean result = false;
		try {
			daoManager.save(cameraConfiguration);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//sumit start: Camera Orientation Feature
	public CameraConfiguration getCameraConfiguration(long id) {
		CameraConfiguration cameraConfiguration = (CameraConfiguration) daoManager.get(id, CameraConfiguration.class);
		return cameraConfiguration;
	}

	public H264CameraConfiguration getH264CameraConfigurationById(long id){
		H264CameraConfiguration h264CameraConfiguration = (H264CameraConfiguration) daoManager.get(id, H264CameraConfiguration.class);
		return h264CameraConfiguration;
	}
	
	public boolean updateH264CameraConfiguration(H264CameraConfiguration h264CameraConfiguration){
		boolean result = false;
		try {
			daoManager.update(h264CameraConfiguration);
			result = true;
		} catch (Exception e) {
			LogUtil.info("Error while updating H264 Camera Configuration. "+e.getMessage());
		}
		return result;
	}
	//sumit end: Camera Orientation Feature

	public boolean updateCameraConfiguration(
			CameraConfiguration cameraConfiguration) {
		boolean result = false;
		try {
			daoManager.update(cameraConfiguration);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
}
