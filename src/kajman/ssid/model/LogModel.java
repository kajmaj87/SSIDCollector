package kajman.ssid.model;

import kajman.ssid.model.entity.LogEntry;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class LogModel extends DbModel {

	public LogModel(Context context) {
		super(context);
	}
	
	public void save(LogEntry input){
		ContentValues values = new ContentValues();
        values.put(LogEntry.Columns.TYPE,input.getType());
        values.put(LogEntry.Columns.DATE,input.getDate());
        values.put(LogEntry.Columns.CONTENT,input.getContent());
        getDb().insert(LogEntry.TABLE_NAME, "", values);
	}
	
	public String toString(){
		Cursor cursor = getDb().query(LogEntry.TABLE_NAME, 
				null, LogEntry.Columns.DATE+ " > '" + (System.currentTimeMillis()-60*60*1000)+"'", null, null, null, LogEntry.Columns.DATE + " DESC");
		StringBuilder stringBuilder = new StringBuilder();
		while(cursor.moveToNext()){
			stringBuilder.append(getLogEntry(cursor).toString()+"\n");
		}
		cursor.close();
		return stringBuilder.toString();
	}
	
	public int deleteOlderThan(long time){
		return getDb().delete(LogEntry.TABLE_NAME, LogEntry.Columns.DATE + " < '"+time+"'", null);		
	}
	
	public LogEntry getLogEntry(Cursor cursor){
		LogEntry logEntry = new LogEntry();
		logEntry.setContent(cursor.getString(cursor.getColumnIndex(LogEntry.Columns.CONTENT)));
		logEntry.setDate(cursor.getLong(cursor.getColumnIndex(LogEntry.Columns.DATE)));
		logEntry.setType(cursor.getInt(cursor.getColumnIndex(LogEntry.Columns.TYPE)));
		return logEntry;
	}
	
	private void enterMessage(int type, String message){
		save(new LogEntry(type,message));
	}
	
	public void d(String message){
		enterMessage(LogEntry.TYPE_DEBUG, message);
	}
	
	public void v(String message){
		enterMessage(LogEntry.TYPE_VERBOSE, message);
	}
	
	public void i(String message){
		enterMessage(LogEntry.TYPE_INFO, message);
	}
	
	public void e(String message){
		enterMessage(LogEntry.TYPE_ERROR, message);
	}

}
