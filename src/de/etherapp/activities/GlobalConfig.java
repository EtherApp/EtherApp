package de.etherapp.activities;

import java.util.HashMap;

import org.etherpad_lite_client.EPLiteException;

import android.content.ContentValues;
import de.etherapp.epclient.PadAPI;
import de.etherapp.sql.DBHandler;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class GlobalConfig {
	public static MainActivity ma;

	public static PadAPI currentApi = null;
	public static HashMap<String,PadAPI> apiMap = new HashMap<String,PadAPI>();
	private static int apiCount = -1;
	private static DBHandler dbh = null;

	public static boolean selectApi(String apiid){
		PadAPI newapi = apiMap.get(apiid);
		
		if(newapi != null){
			//assign locally
			currentApi = newapi;
			
			//update in database
			SQLiteDatabase db = dbh.getWritableDatabase();
			db.execSQL("UPDATE " + DBHandler.TABLE_PREF + " SET value = '" + currentApi.getAPIID() + "' WHERE name = 'currentApi';");
			db.close();
			
			return true;
		}else{
			return false;
		}
		
		//TODO: update all data, since API has changed!
	}

	
	
	
	public static long updateApi(PadAPI api){
		//do not update 
		if(currentApi.getAPIID().equals(api.getAPIID())){
			return -1;
		}
		
		//open DB
		SQLiteDatabase db = dbh.getWritableDatabase();
		
		//define values
        ContentValues val = new ContentValues();
        val.put("apiid",   api.getAPIID());
        val.put("apiname", api.getAPINAME());
        val.put("apiurl",  api.getAPIURL());
        val.put("apikey",  api.getAPIKEY());
        val.put("port",    api.getPORT());
        
        //update row in DB
        long retval = (long)db.update(DBHandler.TABLE_PADAPI, val, " apiid = ?", new String[] { api.getAPIID() });	
		
        //replace PadAPI object in local API list
        if(retval > 0){
        	apiMap.remove(api.getAPIID());
        	apiMap.put(api.getAPIID(), api);
        }
		
		return retval;
	}
	
	public static long putNewApi(PadAPI api){
		//open DB
		SQLiteDatabase db = dbh.getWritableDatabase();
		
		//define values
		ContentValues val = new ContentValues();
        val.put("apiid",   api.getAPIID());
        val.put("apiname", api.getAPINAME());
        val.put("apiurl",  api.getAPIURL());
        val.put("apikey",  api.getAPIKEY());
        val.put("port",    api.getPORT());
        val.put("timestamp", (System.currentTimeMillis() / 1000));
 
        long retval = db.insert(DBHandler.TABLE_PADAPI, null, val); //insert row
        db.close(); //close DB connection
		
        //insert into local API list
        apiMap.put(api.getAPIID(), api);
        
        //TODO: Was insert successful?
        
		return retval;
	}

	public static long deleteApi(String apiid){
		//do not delete current API
		if(currentApi.getAPIID().equals(apiid)){
			return -1;
		}
		
		//get preferences and delete
		SQLiteDatabase db = dbh.getWritableDatabase();
        long retval = db.delete(DBHandler.TABLE_PADAPI, "apiid = ?", new String[] { apiid });
        db.close();
		
        //delete API from local list if deletion from DB was successful
        if(retval > 0){
    		apiMap.remove(apiid);
        }
        
        return retval;
	}

	public static int getApiCount() {
		SQLiteDatabase db = dbh.getReadableDatabase();
	    Cursor cursor= db.rawQuery("SELECT COUNT (*) FROM " + DBHandler.TABLE_PADAPI, null);
	    cursor.moveToFirst();
	    int count = cursor.getInt(0);
	    cursor.close();
	    
	    return count;
	}



	public static boolean loadConfig(MainActivity mac){
		ma  = mac;
		dbh = new DBHandler(ma);
		
		if(getApiCount() > 0){
			Cursor cursor = null;
			String lastapiid = null;
			
			String selectQuery = "SELECT apiname,apiurl,port,apikey,apiid FROM " + DBHandler.TABLE_PADAPI;
			
	        SQLiteDatabase db = dbh.getReadableDatabase();
	        cursor = db.rawQuery(selectQuery, null);
	 
	        if(cursor.moveToFirst()){
	            do{
	                PadAPI newapi = new PadAPI(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4));
	                apiMap.put(newapi.getAPIID(), newapi);
	                lastapiid = newapi.getAPIID();
	            }while (cursor.moveToNext());
	        }
		
	        //select recently selected API
	        cursor = db.rawQuery("SELECT value FROM " + DBHandler.TABLE_PREF + " WHERE name = 'currentApi' LIMIT 1;", null);
	        if(cursor.moveToFirst() && !cursor.getString(0).isEmpty()){
        		selectApi(cursor.getString(0));
	        }else{
	        	selectApi(lastapiid);
	        }

	        db.close();
	        
			return true;
			
		}else{
			return false; //no API exists
			
		}
	}
}

