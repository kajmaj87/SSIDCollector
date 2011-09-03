package kajman.ssid.model;

import kajman.ssid.model.entity.LogEntry;
import kajman.ssid.model.entity.Settings;
import kajman.ssid.model.entity.Wifi;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbModel {

	private static final String DATABASE_NAME = "ssid.db";
	private static final int DATABASE_VERSION = 1;
	
	protected DbHelper mDbHelper;
	
	private class DbHelper extends SQLiteOpenHelper {
		
	    public DbHelper(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }
	 
	    @Override
	    public void onCreate(SQLiteDatabase db) {
	    	//WIFI TABLE
	    	String sql = String.format(
	    			"CREATE TABLE %s " +
	    			"(%s INTEGER PRIMARY KEY, " +
	    			"%s TEXT, %s TEXT, %s INTEGER, " +
	    			"%s INTEGER, %s INTEGER, %s TEXT);",
	    			Wifi.TABLE_NAME,  
	    			Wifi.Columns._ID, 
	    			Wifi.Columns.BSSID, Wifi.Columns.CAPABILITIES, Wifi.Columns.CHANNEL,
	    			Wifi.Columns.DATE, Wifi.Columns.SCAN_NUMBER, Wifi.Columns.SSID);
	    	db.execSQL(sql);
	    	//LOG ENTRIES TABLE
	    	sql = String.format(
	    			"CREATE TABLE %s " +
	    			"(%s INTEGER PRIMARY KEY, " +
	    			"%s TEXT, %s INTEGER, %s INTEGER);",
	    			LogEntry.TABLE_NAME,
	    			LogEntry.Columns._ID,
	    			LogEntry.Columns.CONTENT, LogEntry.Columns.DATE, LogEntry.Columns.TYPE);
	    	db.execSQL(sql);
	    	//SETTINGS TABLE
	    	sql = String.format(
	    			"CREATE TABLE %s " +
	    			"(%s INTEGER PRIMARY KEY, " +
	    			"%s TEXT, %s TEXT);",
	    			Settings.TABLE_NAME,
	    			Settings.Columns._ID,
	    			Settings.Columns.KEY, Settings.Columns.VALUE);
	    	db.execSQL(sql);
	    }
	 
	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        // TODO Auto-generated method stub
	 
	    }
	 
	}
	
	
	
	protected DbModel(Context context){
		mDbHelper = new DbHelper(context);
	}
	
	protected SQLiteDatabase getDb(){
		return mDbHelper.getWritableDatabase();
	}
	
	public void closeDb(){
		mDbHelper.close();
	}
	
}
