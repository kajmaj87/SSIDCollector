package kajman.ssid;


import java.io.IOException;

import kajman.ssid.helpers.ExportHelper;
import kajman.ssid.model.LogModel;
import kajman.ssid.model.RawQuery;
import kajman.ssid.model.WifiModel;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
		Button buttonExport = (Button) findViewById(R.id.buttonExport);
		buttonExport.setOnClickListener(this);
		Button buttonImport = (Button) findViewById(R.id.buttonImport);
		buttonImport.setOnClickListener(this);
		Log.d(TAG, "onCreate()");
	}

	public void onClick(View view) {
		
		if (view.getId() == R.id.buttonExport) {
			try {
				Log.d("SSID", "Directory is: " +Environment.getExternalStorageDirectory()+"/ssid.db");
				if(ExportHelper.exportDatabaseTo(Environment.getExternalStorageDirectory()+"/ssid.db")){
					Toast.makeText(this, "Export succesful", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(this, "Export failed", Toast.LENGTH_SHORT).show();
				}
			} catch (IOException e) {
				Toast.makeText(this, "Export encountered an error", Toast.LENGTH_SHORT).show();
				Log.d("SSID","Error: "+e);
			}
		}
		if (view.getId() == R.id.buttonImport) {
			try {
				Log.d("SSID", "Directory is: " +Environment.getExternalStorageDirectory()+"/ssid.db");
				if(ExportHelper.importDatabaseFrom(Environment.getExternalStorageDirectory()+"/ssid.db")){
					Toast.makeText(this, "Import succesful", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(this, "Import failed", Toast.LENGTH_SHORT).show();
				}
			} catch (IOException e) {
				Toast.makeText(this, "Import encountered an error", Toast.LENGTH_SHORT).show();
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
		RawQuery rawQuery = new RawQuery(this);
	    switch (item.getItemId()) {
	    case R.id.menu_date_time:
	        //newGame();
	        return true;
	    case R.id.menu_wifi:
	        //showHelp();
	        return true;
	    case R.id.menu_item_top_names:	    	
	    	textStatus.setText(rawQuery.toString(rawQuery.fetchTopNames()));
	    	return true;
	    case R.id.menu_item_bssid:
	    	textStatus.setText(rawQuery.toString(rawQuery.fetchWifisByBSSID()));
	    	return true;
	    case R.id.menu_item_name:
	    	textStatus.setText(rawQuery.toString(rawQuery.fetchWifisByName()));
	    	return true;
	    case R.id.menu_item_records_by_scan:
	    	textStatus.setText(rawQuery.toString(rawQuery.fetchRecordsByScanNumner()));
	    	return true;
	    case R.id.menu_item_channels:
	    	textStatus.setText(rawQuery.toString(rawQuery.fetchByChannels()));
	    	return true;	
	    case R.id.menu_item_dates:
	    	textStatus.setText(rawQuery.toString(rawQuery.fetchRecordsByDate()));
	    	return true;
	    case R.id.menu_item_hours:
	    	textStatus.setText(rawQuery.toString(rawQuery.fetchRecordsByHour()));
	    	return true;
	    case R.id.menu_item_weekdays:
	    	textStatus.setText(rawQuery.toString(rawQuery.fetchRecordsByWeekday()));
	    	return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
}