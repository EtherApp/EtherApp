package de.etherapp.app;

import java.util.ArrayList;
import java.util.List;

import de.etherapp.epclient.PadAPI;

public class GlobalConfig {
		private static int currentApiPos;
		public static PadAPI currentApi;
		private static List<PadAPI> apiList = new ArrayList<PadAPI>();		
		
		public static void selectApi(int pos){
			currentApiPos = pos;
			currentApi = apiList.get(pos);
		}
		
		public static int putNewApi(PadAPI api){
			apiList.add(api);
			currentApiPos = apiList.indexOf(api);
			return currentApiPos;
		}
}

