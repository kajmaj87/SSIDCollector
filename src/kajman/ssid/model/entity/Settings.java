package kajman.ssid.model.entity;

import android.provider.BaseColumns;

public class Settings {

	public static final String TABLE_NAME = "settings";
	
	public static final String LAST_SCAN = "last_scan";
	public static final String FIRST_RUN = "first_run";
	
	public static final class Columns implements BaseColumns{		
		public static final String KEY = "key",
								   VALUE = "value";	
	}
	
	
}
