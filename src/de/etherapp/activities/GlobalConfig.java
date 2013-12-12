package de.etherapp.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import de.etherapp.epclient.PadAPI;

public class GlobalConfig {
	private static MainActivity ma;

	private static int currentApiPos = -1;
	public  static PadAPI currentApi;
	private static List<PadAPI> apiList = new ArrayList<PadAPI>();
	private static int apiCount = -1;

	public static boolean selectApi(int pos){
		if(pos > apiCount){
			return false;
		}
		else{
			currentApiPos = pos;
			currentApi = apiList.get(pos);	
			return true;
		}
	}

	public static int putNewApi(PadAPI api){
		apiList.add(++apiCount,api);

		SharedPreferences apipref = ma.getSharedPreferences("etherapp_api" + apiCount , 0);
		Editor editor = apipref.edit();
		editor.putString("APIKEY", api.getAPIKEY());
		editor.putString("PADURL", api.getPADURL());
		// und alles schreiben
		editor.commit();

		SharedPreferences.Editor gleditor = ma.getSharedPreferences("etherapp_preferences", 0).edit();
		gleditor.putInt("apiCount", apiCount);
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
		currentApiPos = glshared.getInt("currentApiPos", -1);
		apiCount = glshared.getInt("apiCount", -1);
		System.out.println(currentApiPos + " <--> " + apiCount);
		//api preferences
		if(apiCount > -1){
			for (int i = 0; i <= apiCount; i++) {
				SharedPreferences apipref = ma.getSharedPreferences("etherapp_api" + i, 0);
				String apikey = apipref.getString("APIKEY", "");
				String padurl = apipref.getString("PADURL", "");
				System.out.println(apikey + " <-> " + padurl);
				if(!apikey.isEmpty() && !padurl.isEmpty()){
					PadAPI pa = new PadAPI(padurl, apikey);
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

