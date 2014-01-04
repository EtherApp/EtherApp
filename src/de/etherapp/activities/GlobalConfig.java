package de.etherapp.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import de.etherapp.epclient.PadAPI;
import de.etherapp.sql.DBHandler;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GlobalConfig {
	private static MainActivity ma;

	public static PadAPI currentApi = null;
	public static HashMap<String,PadAPI> apiList = new HashMap<String,PadAPI>();
	private static int apiCount = -1;
	private static DBHandler dbh = null;

	public static boolean selectApi(String apiid){
		PadAPI newapi = apiList.get(apiid);
		
		if(newapi != null){
			currentApi = newapi;
			return true;
		}else{
			return false;
		}
		
		//TODO: update all data, since API has changed!
	}

	
	public static long updateApi(PadAPI api){
		//do not update 
		if(currentApi.getAPIID() == api.getAPIID()){
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
        	apiList.remove(api.getAPIID());
        	apiList.put(api.getAPIID(), api);
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
        apiList.put(api.getAPIID(), api);        
        
		return retval;
	}

	public static long deleteApi(String apiid){
		if(currentApi.getAPIID() == apiid){
			return -1;
		}
		
		//get preferences and delete
		SQLiteDatabase db = dbh.getWritableDatabase();
        long retval = db.delete(DBHandler.TABLE_PADAPI, "apiid = ?", new String[] { apiid });
        db.close();
		
        //delete API from local list if deletion from DB was successful
        if(retval > 0){
    		apiList.remove(apiid);
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



	public static void loadConfig(MainActivity mac){
		ma = mac;
		//globale config
		dbh = new DBHandler(ma);
		
//		SharedPreferences glshared = ma.getSharedPreferences("etherapp_preferences", 0);
//		currentApiID = glshared.getString("currentApiID", -1);
//		apiCount = glshared.getInt("apiCount", -1);
//		System.out.println(currentApiPos + " <--> " + apiCount);
//		//api preferences
		if(apiCount > 0){
			for (int i = 0; i <= apiCount; i++) {
				SharedPreferences apipref = ma.getSharedPreferences("etherapp_api" + i, 0);
				String apiname = apipref.getString("APINAME", "");
				String padurl = apipref.getString("PADURL", "");
				String apikey = apipref.getString("APIKEY", "");
				int port = apipref.getInt("PORT", 0);
				System.out.println(apikey + " <-> " + padurl);
				if(!apikey.isEmpty() && !padurl.isEmpty() && !apiname.isEmpty() && port != 0){
					PadAPI pa = new PadAPI(apiname, padurl, port, apikey);
					String apiid = pa.getAPIID();
					if(currentApiPos == i){
						currentApi = pa;
					}
					apiList.add(i,pa);
				}
				else{
					System.out.println("del");
					apiCount--;
					if(apiCount == currentApiPos){
						currentApiPos--;
					}
					apipref.edit().clear().commit();
					
					Editor editor = glshared.edit();
					editor.putInt("currentApiPos", currentApiPos);
					editor.putInt("apiCount", apiCount);
					// und alles schreiben
					editor.commit();
				}
			}
		}
	}		
}

