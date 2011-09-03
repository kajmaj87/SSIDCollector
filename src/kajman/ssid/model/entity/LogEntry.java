package kajman.ssid.model.entity;

import android.provider.BaseColumns;

public class LogEntry {

	public static final int TYPE_DEBUG = 1,
							TYPE_INFO = 2,
							TYPE_VERBOSE = 3,
							TYPE_ERROR = 4;
	
	private int type;
	private long date;
	private String content;
	
	public static final class Columns implements BaseColumns{		
		public static final String TYPE = "type",
								   CONTENT = "content",
								   DATE = "date";		
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
