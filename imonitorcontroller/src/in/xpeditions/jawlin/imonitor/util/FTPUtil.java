/* Copyright © 2012 iMonitor Solutions India Private Limited */
package in.xpeditions.jawlin.imonitor.util;
/**
 * 
 */


import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.ChannelSftp.LsEntry;

/**
 * @author Coladi
 *
 */
public class FTPUtil {

	public static void createFolders(String ftpIp, int ftpPort,
			String ftpUserName, String ftpPassword, String... folders) throws JSchException, SftpException {
		JSch jSch = new JSch();
		Session session = jSch.getSession(ftpUserName, ftpIp, ftpPort);
		session.setPassword(ftpPassword);
		Properties config = new Properties(); 
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.connect();
		Channel channel = session.openChannel("sftp");
		channel.connect();
		ChannelSftp channelSftp = (ChannelSftp) channel;
		for (int i = 0; i < folders.length; i++) {
			String folder = folders[i];
			boolean isExists = FTPUtil.isFileExists(channelSftp, folder);
			if(!isExists){
				channelSftp.mkdir(folder);
				channelSftp.chmod(511, folder);
			}
		}
		channelSftp.disconnect();
		session.disconnect();
	}
	
	private static boolean isFileExists(ChannelSftp channelSftp, String fileName) {
		try {
			channelSftp.stat(fileName);
		} catch (SftpException e) {
			return false;
		}
		return true;
	}
	
	public static void deleteFolders(String ftpIp, int ftpPort,
			String ftpUserName, String ftpPassword, String filePath) throws JSchException {
		try {
			JSch jSch = new JSch();
			Session session = jSch.getSession(ftpUserName, ftpIp, ftpPort);
			session.setPassword(ftpPassword);
			Properties config = new Properties(); 
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			Channel channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp channelSftp = (ChannelSftp) channel;
			removeDirectoryTree(filePath, channelSftp);
			channelSftp.disconnect();
			session.disconnect();
			} catch (SftpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//}
	}

	private static void removeDirectoryTree(String filePath,
			ChannelSftp channelSftp) throws SftpException {
		Vector<LsEntry> ls = channelSftp.ls(filePath);
		for (LsEntry lsEntry : ls) {
			String filename = lsEntry.getFilename();
			if(!filename.equalsIgnoreCase(".") && !filename.equalsIgnoreCase("..")){
				if(lsEntry.getAttrs().isDir()){
					removeDirectoryTree(filePath + "/" + filename, channelSftp);
				}else{
					channelSftp.rm(filePath + "/" + filename);					
				}
			}
			
		}
		channelSftp.rmdir(filePath);
	}
}
