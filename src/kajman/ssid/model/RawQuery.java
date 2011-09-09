package kajman.ssid.model;

import kajman.ssid.model.entity.Wifi;
import android.content.Context;
import android.database.Cursor;

public class RawQuery extends DbModel {

	// select count(*) as number, a.ssid from wifi as a join (select _id,
	// bssid, ssid from wifi group by bssid) as b on a._id = b._id group by
	// a.ssid having number >1 order by number;
	private static final String FETCH_TOP_NAMES = 
			String.format("select count(*) as n, a.%s from %s as a " +
			"join (select %s, %s, %s from %s group by %s) as b " +
			"on a.%s = b.%s group by a.%s having n > 0 order by n",
			Wifi.Columns.SSID, Wifi.TABLE_NAME,
			Wifi.Columns._ID, Wifi.Columns.BSSID, Wifi.Columns.SSID, Wifi.TABLE_NAME, Wifi.Columns.BSSID,
			Wifi.Columns._ID,Wifi.Columns._ID,Wifi.Columns.SSID);
	
	protected RawQuery(Context context) {
		super(context);
	}

	public Cursor fetchTopNames() {		
		return getDb().rawQuery(FETCH_TOP_NAMES, null);
	}
	
//	public String toString(Cursor cursor){
//		if(cursor!=null && cursor.moveToFirst()){
//			StringBuilder stringBuilder = new StringBuilder();
//			
//		}else{
//			return "No data to display";
//		}
//	}
}
