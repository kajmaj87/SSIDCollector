package kajman.ssid.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbModel {

	public class DbHelper extends SQLiteOpenHelper {
		
		private static final String DATABASE_NAME = "ssid.db";
		private static final int DATABASE_VERSION = 1;
		
	    public DbHelper(Context context, String name, CursorFactory factory,
	            int version) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }
	 
	    @Override
	    public void onCreate(SQLiteDatabase db) {
	        // TODO Auto-generated method stub
	 
	    }
	 
	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        // TODO Auto-generated method stub
	 
	    }
	 
	}
	
}
