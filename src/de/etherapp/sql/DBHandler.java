package de.etherapp.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 4;
 
    //database Name
    private static final String DATABASE_NAME = "etherapp";
    
    //tables
    public static final String TABLE_PADAPI = "ea_padapi";
    public static final String TABLE_PREF = "ea_pref";
 
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    //Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db){
    	//create SQL tables
    	db.execSQL("CREATE TABLE " + TABLE_PADAPI + "(" +
    		"apiid VARCHAR(64) PRIMARY KEY," +
    		"apiname VARCHAR(255) NOT NULL," +
    		"apiurl VARCHAR(255) NOT NULL," +
    		"port INT NOT NULL," +
    		"apikey VARCHAR(255) NOT NULL," +
    		"timestamp UNIX_TIMESTAMP" +
    	");");
    	
    	db.execSQL("CREATE TABLE " + TABLE_PREF + "(" +
        	"name VARCHAR(255) PRIMARY KEY," +
        	"value VARCHAR(255) NOT NULL" +
        ");");
    	
    	//insert initial key for current API information
    	db.execSQL("INSERT INTO " + TABLE_PREF + " VALUES (" +
            	"'currentApi'," +
    			"'none'" +
        ");");
    	
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PADAPI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PREF);
 
        //Create tables again
        onCreate(db);
    }

}
