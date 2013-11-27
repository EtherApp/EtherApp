package de.etherapp.app.padclient;

import java.util.HashMap;

import org.etherpad_lite_client.EPLiteClient;

public class PadAPI {
	private String APIKEY = null;
	private String PADURL = null;
	private EPLiteClient client = null;
	
	private HashMap<String, pad> padhash = null;
	
	public PadAPI(String padurl,String apikey){
		APIKEY = apikey;
		PADURL = padurl;
		client = new EPLiteClient(PADURL, APIKEY);
		init();
	}

	
	public void init(){
		new PadThread(this,"init");
	}
	
	public void updatePads(){
		new PadThread(this,"pads");
	}
	
	public void setPadList(HashMap<String,pad> pad) {
		padhash = pad;
	}
	
	public HashMap<String, pad> getPadList() {
		return padhash;
	}

	
	public EPLiteClient getClient() {
		return client;
	}


	
}
