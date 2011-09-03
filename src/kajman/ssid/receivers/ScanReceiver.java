package kajman.ssid.receivers;

import java.util.ArrayList;
import java.util.List;

import kajman.ssid.model.LogModel;
import kajman.ssid.model.SettingsModel;
import kajman.ssid.model.WifiModel;
import kajman.ssid.model.entity.Settings;
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
	log.v("Recieved scan results.");
	SettingsModel settingsModel = new SettingsModel(context);
	WifiModel wifiModel = new WifiModel(context);
	  
	WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
	List<ScanResult> results = wifiManager.getScanResults();
	ArrayList<Wifi> wifis = new ArrayList<Wifi>(results.size());
	long scanNumber = settingsModel.getScanNumber();
    for (ScanResult result : results) {    	
    	wifis.add(new Wifi(result, scanNumber));
    }    
    settingsModel.setScanNumber(scanNumber+1);
    long saved = wifiModel.save(wifis);
    log.v("Scan results saved. Took "+(System.currentTimeMillis()-time)+"ms.");
    log.v("Saved "+saved+" new records.");
    Log.d("DEBUG", String.format("Saved %d in %dms. Scan number: %d",saved,System.currentTimeMillis()-time,scanNumber));
    log.closeDb();
  }

}
