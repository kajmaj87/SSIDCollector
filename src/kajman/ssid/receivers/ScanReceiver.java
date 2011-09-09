package kajman.ssid.receivers;

import java.util.ArrayList;
import java.util.List;

import kajman.ssid.model.LogModel;
import kajman.ssid.model.SettingsModel;
import kajman.ssid.model.WifiModel;
import kajman.ssid.model.entity.Wifi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

public class ScanReceiver extends BroadcastReceiver {

  public ScanReceiver(){
	  super();
	  Log.d("DEBUG", "Wifi scan receiver created.");
  }

  @Override
  public void onReceive(Context context, Intent intent) {
	LogModel log = new LogModel(context);
	long time = System.currentTimeMillis();
	WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
	List<ScanResult> results = wifiManager.getScanResults();
	if(results==null){
		log.e("No scan results! Leaving.");
		log.closeDb();
		return;
	}
	SettingsModel settingsModel = new SettingsModel(context);
	WifiModel wifiModel = new WifiModel(context);
	  
	
	ArrayList<Wifi> wifis = new ArrayList<Wifi>(results.size());
	long scanNumber = settingsModel.getScanNumber();
	if(scanNumber%200 == 0){
		log.v("Deleting log entries older then 24h.");
		long now=System.currentTimeMillis();
		log.v("Deleted: "+log.deleteOlderThan(now-(24l*60l*60l*1000l))+" entries.");
		log.v("Took: "+(System.currentTimeMillis()-now)+"ms.");
	}
    for (ScanResult result : results) {    	
    	wifis.add(new Wifi(result, scanNumber));
    }    
    settingsModel.setScanNumber(scanNumber+1);
    settingsModel.closeDb();
    long saved = wifiModel.save(wifis);
    //very aggresive scanning after finding new wifi
    if(System.currentTimeMillis() - wifiModel.fetchLastScanTime() < 15000){
    	wifiManager.startScan();
    }
    wifiModel.closeDb();
    log.v("Scan "+scanNumber+" saved. Took "+(System.currentTimeMillis()-time)+"ms.");
    if(saved>0) log.v("Saved "+saved+" new records.");
    Log.d("DEBUG", String.format("Saved %d in %dms. Scan number: %d",saved,System.currentTimeMillis()-time,scanNumber));
    log.closeDb();
  }

}
