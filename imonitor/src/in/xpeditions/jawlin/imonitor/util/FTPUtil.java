/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.util;

import java.io.InputStream;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

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
		String folder ="";
		ChannelSftp channelSftp = (ChannelSftp) channel;
		for (int i = 0; i < folders.length; i++) {
			 folder = folders[i];
			boolean isExists = FTPUtil.isFileExists(channelSftp, folder);
			if(!isExists){
				channelSftp.mkdir(folder);
			}
		}
		channelSftp.disconnect();
		session.disconnect();
	}
	
	public static boolean isFileExist(String ftpIp, int ftpPort,
			String ftpUserName, String ftpPassword, String fileName) throws JSchException, SftpException {
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
		boolean IsFileExits=FTPUtil.isFileExists(channelSftp, fileName);
		channelSftp.disconnect();
		session.disconnect();
		return IsFileExits;
	}

	private static boolean isFileExists(ChannelSftp channelSftp, String fileName) {
		try {
			channelSftp.stat(fileName);
		} catch (SftpException e) {
			return false;
		}
		return true;
	}


	public static void uploadFile(String ftpIp, int ftpPort,
			String ftpUserName, String ftpPassword, InputStream inputFile,
			String fileNamePath) throws JSchException, SftpException {
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
		channelSftp.put(inputFile, fileNamePath);
		channelSftp.disconnect();
		session.disconnect();
	}


	public static void removeFile(String ftpIp, int ftpPort,
			String ftpUserName, String ftpPassword, String filePath) throws JSchException, SftpException {
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
		channelSftp.rm(filePath);
		channelSftp.disconnect();
		session.disconnect();
	}
	
	public static void removeFolder(String ftpIp, int ftpPort,
			String ftpUserName, String ftpPassword, String ImvgFolder, String FolderName) throws JSchException, SftpException {
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
		channelSftp.cd(ImvgFolder);
		channelSftp.rmdir(FolderName);
		channelSftp.disconnect();
		session.disconnect();
	}
	
	
}
