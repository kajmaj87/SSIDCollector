package kajman.ssid.model;

import kajman.ssid.model.entity.Settings;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class SettingsModel extends DbModel {

	public SettingsModel(Context context) {
		super(context);
	}

	public void setFirstRun(boolean firstRun){
		setBoolean(Settings.FIRST_RUN, firstRun);
	}
	
	public void setScanNumber(long scanNumber){
		setLong(Settings.LAST_SCAN, scanNumber);
	}
	
	private void setLong(String key, long l){
		ContentValues values = new ContentValues();
		values.put(Settings.Columns.KEY, key);
		values.put(Settings.Columns.VALUE, l);
		if(getDb().update(Settings.TABLE_NAME, values, 
				   Settings.Columns.KEY + " = '"+key+"'",null)==0){
			getDb().insert(Settings.TABLE_NAME, "", values);
		}
	}
	
	private long getLong(String key){
		Cursor cursor = getDb().query(Settings.TABLE_NAME, new String[] {Settings.Columns.VALUE},
			      Settings.Columns.KEY + " = '"+Settings.LAST_SCAN+"'", null, null, null, null);
		long result = -1;
		if(cursor.moveToNext()){
			result = cursor.getLong(cursor.getColumnIndex(Settings.Columns.VALUE));
			cursor.close();
		}
		return result;
	}
	
	private void setBoolean(String key, boolean value){
		ContentValues values = new ContentValues();
		values.put(Settings.Columns.KEY, key);
		values.put(Settings.Columns.VALUE, value);
		if(getDb().update(Settings.TABLE_NAME, values, 
					   Settings.Columns.KEY + " = '"+key+"'",null)==0){
		   getDb().insert(Settings.TABLE_NAME, "", values);
		}
	}
	
	
	
	public long getScanNumber(){
		return getLong(Settings.LAST_SCAN);
	}
	
}
