package in.xpeditions.jawlin.imonitor.util;

import java.util.HashMap;
import java.util.Map;

public class ModbusConstants {
	
	
	
	/*Modbus error response code mapping
	 * 2nd July
	 * Author Naveen
	 */
	public static Map<Object, String> errormap = new HashMap<Object,String>();
	  {
		  errormap.put("A0","External protection device");
		  errormap.put("A1","PCB defect");
		  errormap.put("A2","Interlock for fan motor");
		  errormap.put("A3","Abnormal drain water level");
		  errormap.put("A4","Abnormal temp for heat exchanger(1)");
		  errormap.put("A5","Abnormal temp for heat exchanger(2)");
		  errormap.put("A6","Fan motor lock, overload");
		  errormap.put("A7","Malfunction of air swing motor");
		  errormap.put("A8","Ac input over current");
		  errormap.put("A9","Malfunction of moving part ofelectric expansion valve");
		  errormap.put("AA","Abnormal temp of heater");
		  errormap.put("AH","Air cleaner defect, dirty");
		  errormap.put("AC","Kindling process fault");
		  errormap.put("AJ","Capacity determination device");
		  errormap.put("AE","Not enough water supply");
		  errormap.put("AF","Malfunction of humidifier");
	
		  errormap.put("C0","Thermistor fault");
		  errormap.put("C1","");
		  errormap.put("C2","");
		  errormap.put("C3","Malfunction of drain water level sensor");
		  errormap.put("C4","Malfunction of thermistor for heat exchanger(1)");
		  errormap.put("C5","Malfunction of thermistor for heat exchanger(2)");
		  errormap.put("C6", "Fan motor lock, overload sensor error");
		  errormap.put("C7","Error of lock sensor of air swing motor");
		  errormap.put("C8","Malfunction of AC input sensor");
		  errormap.put("C9","Malfunction of thermistor for suction air");
		  errormap.put("CA","Malfunction of thermistor for supply air");
		  errormap.put("CH","Malfuction of dirt sensor");
		  errormap.put("CC","Malfunction of humidity sensor");
		  errormap.put("CJ","Malfunction of thermistor (for remote)");
		  errormap.put("CE","Malfuction of radiation sensor");
		  errormap.put("CF","Malfuction of high pressure switch");
	 
		  errormap.put("E0","Actuation of safety device");
		  errormap.put("E1","PCB defect");
		  errormap.put("E2","");
		  errormap.put("E3","High presure switch");
		  errormap.put("E4", "Low pressure switch");
		  errormap.put("E5", "Compressor over load");
		  errormap.put("E6","Compressor overload, overcurrent, lock");
		  errormap.put("E7", "Fan motor overload, lock");
		  errormap.put("E8","AC input over current");
		  errormap.put("E9","Malfunction of moving part of EEV");
		  errormap.put("EA","");
		  errormap.put("EH","Pump over current, lock");
		  errormap.put("EC","Abnormal water temp");
		  errormap.put("EJ","Actuation of additional protective device");
		  errormap.put("EE", "Abnormal drain water level");
		  errormap.put("EF","Error of heat accumdation unit");
	 
	 
		  errormap.put("H0","Thermistor fault");
		  errormap.put("H1","Malufanction of air thermistor");
		  errormap.put("H2","Malufanction of senser for power supply");
		  errormap.put("H3","Malfunction of high pressure switch");
		  errormap.put("H4","Malfunction of low pressure switch");
		  errormap.put("H5","Malfunction of sensor for compressor over load");
		  errormap.put("H6","Malfunction of sensor for comp overload, over current");
		  errormap.put("H7","Malfunction of sensor for fan motor overload, over current");
		  errormap.put("H8","Malfunction of AC input sensor");
		  errormap.put("H9","Malfunction of thermistor for ambient temp");
		  errormap.put("HA","Malfunction of thermistor for discharge air");
		  errormap.put("HH","Malfunction of sensor for pump over current, lock");
		  errormap.put("HC","Malfunction of sensor for boiled water");
		  errormap.put("HJ","");
		  errormap.put("HE","Malfunction of drain water level sensor");
		  errormap.put("HF","Alarm for heat accumdation unit");
	 
	 
		  errormap.put("F0","No1 & 2,Actuation of common safety device");
		  errormap.put("F1","Actuation of safety device in No1 cycle");
		  errormap.put("F2","Actuation of safety device in No2 cycle");
		  errormap.put("F3","Abnormal discharge pipe temp.");
		  errormap.put("F4","");
		  errormap.put("F5","");
		  errormap.put("F6","Abnormal temp for heat exchanger(1)");
		  errormap.put("F7","");
		  errormap.put("F8","");
		  errormap.put("F9","");
		  errormap.put("FA","Abnormal discharge pressure");
		  errormap.put("FH","Abnormal high oil temp");
		  errormap.put("FC","Abnormal suction pressure");
		  errormap.put("FJ","");
		  errormap.put("FE","Abnormal oil pressure");
		  errormap.put("FF","Abnormal oil level");
	 
	 
		  errormap.put("J0","Malufanction of refrigerant temperature sensor");
		  errormap.put("J1","Malufanction of pressure sensor");
		  errormap.put("J2","Malufanction of senser for power supply");
		  errormap.put("J3","Malfunction of discharge pipe sensor");
		  errormap.put("J4","Malfunction of low pressure saturation temp sensor");
		  errormap.put("J5","Malfunction of thermistor for suction pipe");
		  errormap.put("J6","Malfunction of thermistor for heat exchanger(1)");
		  errormap.put("J7","Malfunction of thermistor for heat exchanger(2)");
		  errormap.put("J8","Malfunction of thermistor for liquid pipe");
		  errormap.put("J9","Malfunction of thermistor for gas pipe");
		  errormap.put("JA","Discharge pressure sensor");
		  errormap.put("JH","Oil temp sensor");
		  errormap.put("JC","Suction pressure sensor");
		  errormap.put("JJ","");
		  errormap.put("JE","Oil pressure sensor");
		  errormap.put("JF","Oil level sensor");
		  
	
		  errormap.put("L0","Inverter fault");
		  errormap.put("L1","");
		  errormap.put("L2","");
		  errormap.put("L3","Abnormal temp for switch box");
		  errormap.put("L4","Inverter radiating fin temp rise");
		  errormap.put("L5","Inverter DC output over current");
		  errormap.put("L6","Inverter AC output over current");
		  errormap.put("L7","Total input over current");
		  errormap.put("L8","Inverter thermostat sensor, comp over load");
		  errormap.put("L9","Inverter stall prevention, comp lock");
		  errormap.put("LA","Power transistor fault");
		  errormap.put("LH","");
		  errormap.put("LC","Malfunction of transmission between inverter and contral");
		  errormap.put("LJ","");
		  errormap.put("LE", "");
		  errormap.put("LF","");
	
		  errormap.put("P0","Gas shortage");
		  errormap.put("P1", "Loose phase,unbalance,inverter overripple protection");
		  errormap.put("P2","");
		  errormap.put("P3","Malfunction of switch box temp sensor");
		  errormap.put("P4","Malfunction of sensor for inverter radiating fin temp rise");
		  errormap.put("P5","Malfunction of sensor for inverter DC output");
		  errormap.put("P6","Malfunction of sensor for inverter AC or DC output");
		  errormap.put("P7","Malfunction of sensor for total input amps");
		  errormap.put("P8","");
		  errormap.put("P9","");
		  errormap.put("PA","");
		  errormap.put("PH","");
		  errormap.put("PC","");
		  errormap.put("PJ","Capacity determination device");
		  errormap.put("PE","");
		  errormap.put("PF","");
	
		  errormap.put("U0","Gas shortage");
		  errormap.put("U1","Reverse phase");
		  errormap.put("U2","Power supply failure");
		  errormap.put("U3","Signal transmission error");
		  errormap.put("U4","Signal transmission error between IDU & ODU");
		  errormap.put("U5","Signal transmission error between IDU & remote");
		  errormap.put("U6","Signal transmission error between IDU");
		  errormap.put("U7","Signal transmission error between ODU");
		  errormap.put("U8", "Signal transmission error between remote");
		  errormap.put("U9","Signal transmission error between IDU & ODU in same system");
		  errormap.put("UA","Excessive number of IDU");
		  errormap.put("UH","Address duplication of IDU");
		  errormap.put("UC","Address duplication of central remote");
		  errormap.put("UJ","Signal transmission error between IDU & option");
		  errormap.put("UE","Signal transmission error between central remote");
		  errormap.put("UF","Miss wiring piping");
	
		  errormap.put("M0","");
		  errormap.put("M1","PCB defect for Centerrized controller");
		  errormap.put("M2","");
		  errormap.put("M3","");
		  errormap.put("M4","");
		  errormap.put("M5","");
		  errormap.put("M6","");
		  errormap.put("M7","");
		  errormap.put("M8","Transmission error of centerrized controller");
		  errormap.put("M9","");
		  errormap.put("MA","Improper combination of optional controllers for centerized");
		  errormap.put("MH","");
		  errormap.put("MC","Address duplication, improper setting for central remote");
		  errormap.put("MJ","");
		  errormap.put("ME","");
		  errormap.put("MF","");
	
	
	
	
		  errormap.put(30,"");
		  errormap.put(31,"Indoor humidity sensor fault");
		  errormap.put(32,"Outdoor humidity sensor fault");
		  errormap.put(33,"Malfunction of thermistor of supply air");
		  errormap.put(34,"Malfunction of thermistor of circulate air");
		  errormap.put(35,"Malfunction of thermistor of outdoor ambient air");
		  errormap.put(36,"Malfunction of thermistor of remote");
		  errormap.put(37,"");
		  errormap.put(38,"");
		  errormap.put(39,"");
		  errormap.put(40,"Malufanction of humidifier valve");
		  errormap.put(41,"");
		  errormap.put(42,"Malufanction of cold water valve");
		  errormap.put(43,"Malufanction of hot water valve");
		  errormap.put(44,"Malufanction of heat exchanger of");
		  errormap.put(45,"Malufanction of heat exchanger of");
		  errormap.put(46,"");
		  errormap.put(47,"");
		  errormap.put(48,"");
		  errormap.put(49,"");
		  errormap.put(50,"");
		  errormap.put(51,"Fan motor over load for air supply");
		  errormap.put(52,"Fan motor over load for air circulation");
		  errormap.put(53,"Inverter fault for air inlet side");
		  errormap.put(54,"Inverter fault for air outlet side");
		  errormap.put(55,"");
		  errormap.put(56,"");
		  errormap.put(57,"");
		  errormap.put(58,"");
		  errormap.put(59,"");
		  errormap.put(60,"Malufanction lump");
		  errormap.put(61,"PCB defect");
		  errormap.put(62,"Ozone density");
		  errormap.put(63,"Dirt sensor");
		  errormap.put(64,"Sensor for indoor air temp");
		  errormap.put(65,"Sensor for outdoor air temp");
		  errormap.put(66,"");
		  errormap.put(67,"");
		  errormap.put(68,"Malfunction of high voltage side");
		  errormap.put("3A","Malfunction of sensor for water leakage(1)");
		  errormap.put("3H","Malfunction of sensor for water leakage(2)");
		  errormap.put("3C","Malfunction of sensor for sweat");
		  errormap.put("3J","");
		  errormap.put("3E","");
		  errormap.put("3F","");
		  errormap.put("4A","");
		  errormap.put("4H","");
		  errormap.put("4C","");
		  errormap.put("4J","");
		  errormap.put("4E","");
		  errormap.put("4F","");
		  errormap.put("5A","");
		  errormap.put("5H","");
		  errormap.put("5C","");
		  errormap.put("5J","");
		  errormap.put("5E","");
		  errormap.put("5F","");
		  errormap.put("6A","Damper");
		  errormap.put("6H","Door swith open");
		  errormap.put("6C","Humidifier element");
		  errormap.put("6J","High effeciency filter");
		  errormap.put("6E","");
		  errormap.put("6F","Easy remote controller");
		  
	  } 
	

}
