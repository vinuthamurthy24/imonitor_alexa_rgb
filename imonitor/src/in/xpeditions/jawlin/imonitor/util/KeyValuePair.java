/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.util;

/**
 * @author Asmodeus
 *
 */
public class KeyValuePair {
	private String key;
	private String value;
	public KeyValuePair(String key, String value) {
		this.key = key;
		this.value = value;
	}
	public KeyValuePair() {
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
