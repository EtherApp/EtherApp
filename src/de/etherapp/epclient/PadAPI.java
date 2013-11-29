package de.etherapp.epclient;

import java.util.HashMap;
import java.util.List;

import org.etherpad_lite_client.EPLiteClient;

public class PadAPI {
	private String APIKEY = null;
	private String PADURL = null;
	private EPLiteClient client = null;
	
	private HashMap<String, Pad> padhash = null;
	
	public PadAPI(String padurl,String apikey){
		APIKEY = apikey;
		PADURL = padurl;
		client = new EPLiteClient(PADURL, APIKEY);
		init();
	}

	
	private void init(){
		HashMap result = client.listAllPads();
		List padIds = (List) result.get("padIDs");

		Pad pad = null;
		for (Object padid : padIds) {
			pad = new Pad((String)padid);
			padhash.put((String)padid, pad);
		}
	}
	
	public void setPadList(HashMap<String,Pad> pad) {
		padhash = pad;
	}
	
	public HashMap<String, Pad> getPadList() {
		return padhash;
	}

	
	public EPLiteClient getClient() {
		return client;
	}


	
}
