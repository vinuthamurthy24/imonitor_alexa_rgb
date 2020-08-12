package in.xpeditions.jawlin.imonitor.controller.dao.entity;

public class BackupOfUploadsByImvg {
	
	private long id;
	private BackupOfAlertsFromImvg backupOfAlertsFromImvg;
	private String Filepath;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BackupOfAlertsFromImvg getBackupOfAlertsFromImvg() {
		return backupOfAlertsFromImvg;
	}

	public void setBackupOfAlertsFromImvg(BackupOfAlertsFromImvg backupOfAlertsFromImvg) {
		this.backupOfAlertsFromImvg = backupOfAlertsFromImvg;
	}

	public String getFilepath() {
		return Filepath;
	}

	public void setFilepath(String filepath) {
		Filepath = filepath;
	}

	

	
     
}
