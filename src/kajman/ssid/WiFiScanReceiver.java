package kajman.ssid;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

public class WiFiScanReceiver extends BroadcastReceiver {
  private static final String TAG = "WiFiScanReceiver";

  public WiFiScanReceiver(){
	  super();
  }

  @Override
  public void onReceive(Context context, Intent intent) {
	WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
	List<ScanResult> results = wifiManager.getScanResults();
	
    StringBuilder stringBuilder = new StringBuilder();
    for (ScanResult result : results) {    	
        stringBuilder.append(String.format("SSID: %s\nBSSID: %s\nCapabilities: %s",result.SSID,result.BSSID,result.capabilities));
    }

//    String message = String.format("%s networks found. %s is the strongest.",
//        results.size(), bestSignal.SSID);
 //   Toast.makeText(wifiDemo, stringBuilder.toString(), Toast.LENGTH_LONG).show();4
    Log.d("DEBUG","Results: "+stringBuilder.toString());

    Log.d(TAG, "onReceive() message: " + stringBuilder.toString());
  }

}
