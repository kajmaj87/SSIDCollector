package kajman.ssid;

import helpers.exportHelper;

import java.io.IOException;
import java.util.List;

import kajman.ssid.model.LogModel;
import kajman.ssid.model.WifiModel;
import kajman.ssid.receivers.Recurring;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener {
	private static final String TAG = "WiFiDemo";

	TextView textStatus;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Setup UI
		textStatus = (TextView) findViewById(R.id.textStatus);
		Button buttonScan = (Button) findViewById(R.id.buttonScan);
		buttonScan.setOnClickListener(this);
		Button buttonWifi = (Button) findViewById(R.id.buttonWifis);
		buttonWifi.setOnClickListener(this);
		Button buttonLogs = (Button) findViewById(R.id.buttonLogs);
		buttonLogs.setOnClickListener(this);
		Button buttonRegister = (Button) findViewById(R.id.buttonRegister);
		buttonRegister.setOnClickListener(this);
		Log.d(TAG, "onCreate()");
	}

	public void onClick(View view) {
		
		if (view.getId() == R.id.buttonRegister) {
//			Intent mIntent = new Intent();
//			mIntent.setAction(Recurring.ACTION);
//			sendBroadcast(mIntent);
			try {
				Log.d("SSID", "Directory is: " +Environment.getExternalStorageDirectory()+"/ssid.db");
				exportHelper.exportDatabase(Environment.getExternalStorageDirectory()+"/ssid.db");
			} catch (IOException e) {
				Log.d("SSID","Error: "+e);
			}
		}
		if (view.getId() == R.id.buttonScan) {
			WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
			wifi.startScan();
		}
		if (view.getId() == R.id.buttonLogs) {
			LogModel logModel = new LogModel(this);
			textStatus.setText(logModel.toString());
			logModel.closeDb();
		}
		if (view.getId() == R.id.buttonWifis) {
			WifiModel wifiModel = new WifiModel(this);
			String temp = wifiModel.toString();
			textStatus.setText(temp);
			wifiModel.closeDb();
		}		
	}

}