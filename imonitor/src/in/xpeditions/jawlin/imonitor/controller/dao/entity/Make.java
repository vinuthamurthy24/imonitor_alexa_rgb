/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.controller.dao.entity;

import in.xpeditions.jawlin.imonitor.util.LogUtil;


/**
 * @author Coladi
 *
 */
public class Make {
	private long id; // auto increment.
	private String name;
	private String number;
	private String details;
	private DeviceType deviceType;
	//kantha start
		private String linearDisplacement;
		private String bufferTime;
		//kantha end
		//bhavya start
		private String hmdTypes;
		private String acOnTime;
		private String hmdRating;
		private String Scale;
		private String NRL;
		private String NRH;
		private String WRL;
		private String WRH;
		private String FRL;
		private String FRH;
		private String stabilizationPeriod;
		private String totalTestRunTime;
		private String intervalBwnReports;
		//bhavya end
		
	//SceneController
				private String noOfKeys;
				private String pressType;
				private String keyCode;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public final String getNumber() {
		return number;
	}
	public final void setNumber(String number) {
		this.number = number;
	}
	public final DeviceType getDeviceType() {
		return deviceType;
	}
	public final void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}
	public String getLinearDisplacement() {
		String ld[] = null;
		
		if(details != null)
		{
			ld = details.split(", ");
			if(ld.length > 0) 
				return ld[0];
		}
		return linearDisplacement;
	}
	public void setLinearDisplacement(String linearDisplacement) {
		this.linearDisplacement = linearDisplacement;
		
		if(bufferTime != null)
		{
			details = linearDisplacement + ", "+bufferTime;
		}
		else
		{
			details = linearDisplacement;
		}
	}
	public String getBufferTime() {
		String bt[] = null;
		
		if(details != null)
		{
			bt = details.split(", ");
			if(bt.length > 1) 
				return bt[1];
		}
	
		
		return bufferTime;
	}
	public void setBufferTime(String bufferTime) {
		this.bufferTime = bufferTime;
		if(linearDisplacement != null)
		{
			details = linearDisplacement + ", "+bufferTime;
		}
	}
	//kantha end
	
	//bhavya start
	public String getHmdTypes() {
		String HT[] = null;
		if(details != null)
		{
			HT = details.split(", ");
			if(HT.length > 0) 
				return HT[0];
		}
		return hmdTypes;
	}
	public void setHmdTypes(String hmdTypes) {
		this.hmdTypes = hmdTypes;
		if(hmdTypes != null)
		{
			details = hmdTypes + ", "+acOnTime + ", "+hmdRating + ", "+Scale + ", "+NRL +", "+NRH +", "+WRL + ", " + WRH +", "+FRL+ ", "+FRH+", "+stabilizationPeriod + ", "+totalTestRunTime + ", "+ intervalBwnReports;
		}
		else
		{
			details = hmdTypes;
		}
	}
		public String getAcOnTime() {
	     String ac[] = null;
			
			if(details != null)
			{
				ac = details.split(", ");
				if(ac.length > 1) 
					return ac[1];
			}
			return acOnTime;
		}
		public void setAcOnTime(String acOnTime) {
			this.acOnTime = acOnTime;
			if(acOnTime != null)
			{
				details = hmdTypes + ", "+acOnTime + ", "+hmdRating + ", "+Scale + ", "+NRL +", "+NRH +", "+WRL + ", " + WRH +", "+FRL+ ", "+FRH+", "+stabilizationPeriod + ", "+totalTestRunTime + ", "+ intervalBwnReports;
			}
			else
			{
				details = acOnTime;
			}
		}
		public String getHmdRating() {
			String HR[] = null;
			if(details != null)
			{
				HR = details.split(", ");
				if(HR.length > 2) 
					return HR[2];
			}
			return hmdRating;
		}
		public void setHmdRating(String hmdRating) {
			this.hmdRating = hmdRating;
			if(hmdRating != null)
			{
				details = hmdTypes + ", "+acOnTime + ", "+hmdRating + ", "+Scale + ", "+NRL +", "+NRH +", "+WRL + ", " + WRH +", "+FRL+ ", "+FRH+", "+stabilizationPeriod + ", "+totalTestRunTime + ", "+ intervalBwnReports;
			}
			else
			{
				details = hmdRating;
			}
			
		}
		public String getScale() {
			String sc[] = null;
			if(details != null)
			{
				sc = details.split(", ");
				if(sc.length > 3) 
					return sc[3];
			}
			
			return Scale;
		}
		public void setScale(String Scale) {
			this.Scale = Scale;
			if(Scale != null)
			{
				details = hmdTypes + ", "+acOnTime + ", "+hmdRating + ", "+Scale + ", "+NRL +", "+NRH +", "+WRL + ", " + WRH +", "+FRL+ ", "+FRH+", "+stabilizationPeriod + ", "+totalTestRunTime + ", "+ intervalBwnReports;
			}
			else
			{
				details = Scale;
			}
		}
		
		public String getNRL() {
			String RLNRL[] = null;
			if(details != null)
			{
				RLNRL = details.split(", ");
				if(RLNRL.length > 4) 
					return RLNRL[4];
			}
			return NRL;
		}
		public void setNRL(String NRL) {
			this.NRL = NRL;
			if(NRL != null)
			{
				details = hmdTypes + ", "+acOnTime + ", "+hmdRating + ", "+Scale + ", "+NRL +", "+NRH +", "+WRL + ", " + WRH +", "+FRL+ ", "+FRH+", "+stabilizationPeriod + ", "+totalTestRunTime + ", "+ intervalBwnReports;
			}
			else
			{
				details = NRL;
			}
		}
		public String getNRH() {
			String RLNRH[] = null;
			if(details != null)
			{
				RLNRH = details.split(", ");
				if(RLNRH.length > 5) 
					return RLNRH[5];
			}
			
			return NRH;
		}
		public void setNRH(String NRH) {
			this.NRH = NRH;
			if(NRH != null)
			{
				details = hmdTypes + ", "+acOnTime + ", "+hmdRating + ", "+Scale + ", "+NRL +", "+NRH +", "+WRL + ", " + WRH +", "+FRL+ ", "+FRH+", "+stabilizationPeriod + ", "+totalTestRunTime + ", "+ intervalBwnReports;
			}
			else
			{
				details = NRH;
			}
		}
		public String getWRL() {
			String RLNRH[] = null;
			if(details != null)
			{
				RLNRH = details.split(", ");
				if(RLNRH.length > 6) 
					return RLNRH[6];
			}
			return WRL;
		}
		public void setWRL(String WRL) {
			this.WRL = WRL;
			if(WRL != null)
			{
				details = hmdTypes + ", "+acOnTime + ", "+hmdRating + ", "+Scale + ", "+NRL +", "+NRH +", "+WRL + ", " + WRH +", "+FRL+ ", "+FRH+", "+stabilizationPeriod + ", "+totalTestRunTime + ", "+ intervalBwnReports;
			}
			else
			{
				details = WRL;
			}
		}
		public String getWRH() {
			String RLNRH[] = null;
			if(details != null)
			{
				RLNRH = details.split(", ");
				if(RLNRH.length > 7) 
					return RLNRH[7];
			}
			return WRH;
		}
		public void setWRH(String WRH) {
			this.WRH = WRH;
			if(WRH != null)
			{
				details = hmdTypes + ", "+acOnTime + ", "+hmdRating + ", "+Scale + ", "+NRL +", "+NRH +", "+WRL + ", " + WRH +", "+FRL+ ", "+FRH+", "+stabilizationPeriod + ", "+totalTestRunTime + ", "+ intervalBwnReports;
			}
			else
			{
				details = WRH;
			}
		}
		public String getFRL() {
			String RLNRH[] = null;
			if(details != null)
			{
				RLNRH = details.split(", ");
				if(RLNRH.length > 8) 
					return RLNRH[8];
			}
			return FRL;
		}
		public void setFRL(String FRL) {
			this.FRL = FRL;
			if(FRL != null)
			{
				details = hmdTypes + ", "+acOnTime + ", "+hmdRating + ", "+Scale + ", "+NRL +", "+NRH +", "+WRL + ", " + WRH +", "+FRL+ ", "+FRH+", "+stabilizationPeriod + ", "+totalTestRunTime + ", "+ intervalBwnReports;
			}
			else
			{
				details = FRL;
			}
		}
		public String getFRH() {
			String RLNRH[] = null;
			if(details != null)
			{
				RLNRH = details.split(", ");
				if(RLNRH.length > 9) 
					return RLNRH[9];
			}
			return FRH;
		}
		public void setFRH(String FRH) {
			this.FRH = FRH;
			if(FRH != null)
			{
				details = hmdTypes + ", "+acOnTime + ", "+hmdRating + ", "+Scale + ", "+NRL +", "+NRH +", "+WRL + ", " + WRH +", "+FRL+ ", "+FRH+", "+stabilizationPeriod + ", "+totalTestRunTime + ", "+ intervalBwnReports;
			}
			else
			{
				details = FRH;
			}
		}
		public String getStabilizationPeriod() {
			String SP[] = null;
			if(details != null)
			{
				SP = details.split(", ");
				if(SP.length > 10) 
					return SP[10];
			}
			return stabilizationPeriod;
		}
		public void setStabilizationPeriod(String stabilizationPeriod) {
			this.stabilizationPeriod = stabilizationPeriod;
			if(stabilizationPeriod != null)
			{
				details = hmdTypes + ", "+acOnTime + ", "+hmdRating + ", "+Scale + ", "+NRL +", "+NRH +", "+WRL + ", " + WRH +", "+FRL+ ", "+FRH+", "+stabilizationPeriod + ", "+totalTestRunTime + ", "+ intervalBwnReports;
			}
			else
			{
				details = stabilizationPeriod;
			}
		}
		public String getTotalTestRunTime() {
			String TRT[] = null;
			if(details != null)
			{
				TRT = details.split(", ");
				if(TRT.length > 11) 
					return TRT[11];
			}
			return totalTestRunTime;
		}
		public void setTotalTestRunTime(String totalTestRunTime) {
			this.totalTestRunTime = totalTestRunTime;
			if(totalTestRunTime != null)
			{
				details = hmdTypes + ", "+acOnTime + ", "+hmdRating + ", "+Scale + ", "+NRL +", "+NRH +", "+WRL + ", " + WRH +", "+FRL+ ", "+FRH+", "+stabilizationPeriod + ", "+totalTestRunTime + ", "+ intervalBwnReports;
			}
			else
			{
				details = totalTestRunTime;
			}
		}
		public String getIntervalBwnReports() {
			String IB[] = null;
			if(details != null)
			{
				IB = details.split(", ");
				if(IB.length > 12) 
					return IB[12];
			}
			return intervalBwnReports;
		}
		public void setIntervalBwnReports(String intervalBwnReports) {
			this.intervalBwnReports = intervalBwnReports;
			if(details != null)
			{
				details = hmdTypes + ", "+acOnTime + ", "+hmdRating + ", "+Scale + ", "+NRL +", "+NRH +", "+WRL + ", " + WRH +", "+FRL+ ", "+FRH+", "+stabilizationPeriod + ", "+totalTestRunTime + ", "+ intervalBwnReports;
			}
			else
			{
				details = intervalBwnReports;
			}
		}
		
		public String getNoOfKeys() {
			return noOfKeys;
		}
		public void setNoOfKeys(String noOfKeys) {
			this.noOfKeys = noOfKeys;
		}
		public String getPressType() {
			return pressType;
		}
		public void setPressType(String pressType) {
			this.pressType = pressType;
		}
		public String getKeyCode() {
			return keyCode;
		}
		public void setKeyCode(String keyCode) {
			this.keyCode = keyCode;
		}
		
		}
		
	
	//bhavya end}

