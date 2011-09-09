package kajman.ssid.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class ExportHelper {
	public static String DB_FILEPATH = "/data/data/kajman.ssid/databases/ssid.db";

	/**
	 * Copies the database file at the specified location over the current
	 * internal application database.
	 * */
	public static boolean exportDatabaseTo(String dbPath) throws IOException {

	    // Close the SQLiteOpenHelper so it will commit the created empty
	    // database to internal storage.
	    //close();
	    File newDb = new File(dbPath);
	    File oldDb = new File(DB_FILEPATH);
	    if (oldDb.exists()) {
	        copyFile(new FileInputStream(oldDb), new FileOutputStream(newDb));
	        // Access the copied database so SQLiteHelper will cache it and mark
	        // it as created.
	        //getWritableDatabase().close();
	        return true;
	    }
	    return false;
	}
	
	public static boolean importDatabaseFrom(String dbPath) throws IOException {

	    // Close the SQLiteOpenHelper so it will commit the created empty
	    // database to internal storage.
	    //close();
	    File newDb = new File(DB_FILEPATH);
	    File oldDb = new File(dbPath);
	    if (oldDb.exists()) {
	        copyFile(new FileInputStream(oldDb), new FileOutputStream(newDb));
	        // Access the copied database so SQLiteHelper will cache it and mark
	        // it as created.
	        //getWritableDatabase().close();
	        return true;
	    }
	    return false;
	}
	
	public static void copyFile(FileInputStream fromFile, FileOutputStream toFile) throws IOException {
        FileChannel fromChannel = null;
        FileChannel toChannel = null;
        try {
            fromChannel = fromFile.getChannel();
            toChannel = toFile.getChannel();
            fromChannel.transferTo(0, fromChannel.size(), toChannel);
        } finally {
            try {
                if (fromChannel != null) {
                    fromChannel.close();
                }
            } finally {
                if (toChannel != null) {
                    toChannel.close();
                }
            }
        }
    }
}


