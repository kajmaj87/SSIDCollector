package kajman.ssid.model.entity;

import android.net.wifi.ScanResult;
import android.provider.BaseColumns;

public class Wifi {

	private String bssid, ssid, capabilities;
	private int channel;
	private long scanNumber, date;
	
	public static final String TABLE_NAME = "wifi";
	
	public static final class Columns implements BaseColumns{		
		public static final String BSSID = "bssid",
								   SSID = "ssid",
								   CAPABILITIES = "capabilities",
								   CHANNEL = "channel",
								   SCAN_NUMBER = "scan_number",
								   DATE = "date";		
	}
	
	public Wifi(){
		
	}
	
	public Wifi(ScanResult result, long scanNumber){
		bssid = result.BSSID;
		ssid  = result.SSID;
		capabilities = result.capabilities;
		channel = result.frequency;
		date = System.currentTimeMillis();
		this.scanNumber=scanNumber;
	}

	public String getBssid() {
		return bssid;
	}

	public void setBssid(String bssid) {
		this.bssid = bssid;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public String getCapabilities() {
		return capabilities;
	}

	public void setCapabilities(String capabilities) {
		this.capabilities = capabilities;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public long getScanNumber() {
		return scanNumber;
	}

	public void setScanNumber(long scanNumber) {
		this.scanNumber = scanNumber;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return String.format("%s %s", bssid,ssid);
	}

	
}
