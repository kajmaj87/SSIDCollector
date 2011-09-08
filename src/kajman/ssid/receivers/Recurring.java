package kajman.ssid.receivers;

import java.sql.Date;

import kajman.ssid.model.LogModel;
import kajman.ssid.model.SettingsModel;
import kajman.ssid.model.WifiModel;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;

public class Recurring extends BroadcastReceiver {

	public static final String ACTION = "kajman.ssid.receiver.recurring";
	
	private static final long mDelays[] = {5*1000, 60*1000, 5*60*1000, 15*60*1000};
	
	@Override
	public void onReceive(Context context, Intent intent) {
		WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		wifi.startScan();
		WifiModel wifiModel = new WifiModel(context);
		LogModel log = new LogModel(context);
		SettingsModel settingsModel = new SettingsModel(context);
		
		long delay= getDelayByScanDifferance(settingsModel.getScanNumber()-wifiModel.fetchLastScanNumber());
		String message = "Next check in "+delay+"ms (at "+(new Date(System.currentTimeMillis()+delay)).toLocaleString()+")";
		log.v(message);
		wifiModel.closeDb();
		settingsModel.closeDb();
		log.closeDb();
		Log.d("DEBUG",message);
		Intent mIntent = new Intent();
		mIntent.setAction(ACTION);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+delay, sender);		
	}
	
	private long getDelayByScanDifferance(long diff){
		Log.d("DEBUG", "Diff is "+diff);
		if(diff<12){
			return mDelays[0];
		}else if(diff<17){
			return mDelays[1];
		}else if(diff<20){
			return mDelays[2];
		}else{
			return mDelays[3];
		}
	}

}
