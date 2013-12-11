package de.etherapp.activities;

import java.util.ArrayList;
import java.util.List;

import de.etherapp.epclient.PadAPI;

public class GlobalConfig {
		private static int currentApiPos = -1;
		public static PadAPI currentApi;
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
			apiList.add(api);
			apiCount++;
			currentApiPos = apiList.indexOf(api);
			return currentApiPos;
		}

		public static int getApiCount() {
			return apiCount;
		}

		public static int getCurrentApiPos() {
			return currentApiPos;
		}
		
		
}

