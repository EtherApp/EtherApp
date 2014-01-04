package de.etherapp.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import de.etherapp.epclient.PadAPI;

public class GlobalConfig {
	private static MainActivity ma;

	private static String currentApiID = null;
	public  static PadAPI currentApi;
	public static HashMap<String,PadAPI> apiList = new HashMap<String,PadAPI>();
	private static int apiCount = -1;

	public static boolean selectApi(int pos){
		if(pos > apiCount){
			return false;
		}
		else{
			currentApiPos = pos;
			currentApi = apiList.get(pos);
			
			SharedPreferences.Editor gleditor = ma.getSharedPreferences("etherapp_preferences", 0).edit();
			gleditor.putInt("currentApiPos", currentApiPos);
			// und alles schreiben
			gleditor.commit();
			return true;
		}
	}

	
	public static boolean updateApi(int pos,String apiname,String apiurl,String apikey){
		PadAPI api = apiList.get(pos);

		SharedPreferences apipref = ma.getSharedPreferences("etherapp_api" + apiCount , 0);
		Editor editor = apipref.edit();
		editor.putString("APINAME", apiname);
		editor.putString("PADURL", apiurl);
		editor.putString("APIKEY", apikey);
		// und alles schreiben
		editor.commit();
		return true;
	}
	
	public static int putNewApi(PadAPI api){
		apiList.add(++apiCount,api);

		SharedPreferences apipref = ma.getSharedPreferences("etherapp_api" + apiCount , 0);
		Editor editor = apipref.edit();
		editor.putString("APINAME", api.getAPINAME());
		editor.putString("PADURL", api.getAPIURL());
		editor.putString("APIKEY", api.getAPIKEY());
		// und alles schreiben
		editor.commit();

		SharedPreferences.Editor gleditor = ma.getSharedPreferences("etherapp_preferences", 0).edit();
		gleditor.putInt("apiCount", apiCount);
		if(apiCount == 0){
			gleditor.putInt("currentApiPos", 0);
		}
		// und alles schreiben
		gleditor.commit();
		return apiCount;
	}

	public static void deleteApi(int pos){
		//delete api
		apiList.remove(pos);
		apiCount--;

		//get preferences and delete
		SharedPreferences apipref = ma.getSharedPreferences("etherapp_api" + pos , 0);
		apipref.edit().clear().commit();
		
		SharedPreferences.Editor gleditor = ma.getSharedPreferences("etherapp_preferences", 0).edit();
		gleditor.putInt("apiCount", apiCount);
		// und alles schreiben
		gleditor.commit();
	}

	public static int getApiCount() {
		return apiCount;
	}

	public static int getCurrentApiPos() {
		return currentApiPos;
	}

	public static void loadConfig(MainActivity mac){
		ma = mac;
		//globale config
		SharedPreferences glshared = ma.getSharedPreferences("etherapp_preferences", 0);
		currentApiID = glshared.getString("currentApiID", -1);
		apiCount = glshared.getInt("apiCount", -1);
		System.out.println(currentApiPos + " <--> " + apiCount);
		//api preferences
		if(apiCount > -1){
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

