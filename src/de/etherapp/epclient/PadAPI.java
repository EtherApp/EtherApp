package de.etherapp.epclient;

import java.util.HashMap;

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
		new PadThread(this);
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
