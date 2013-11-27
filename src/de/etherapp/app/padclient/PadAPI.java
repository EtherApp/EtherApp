package de.etherapp.app.padclient;

import java.util.HashMap;
import java.util.List;

import org.etherpad_lite_client.EPLiteClient;

public class PadAPI {
	private String APIKEY = null;
	private String PADURL = null;
	private EPLiteClient client = null;

	private HashMap padmap = null;
	private List padlist = null;
	
	public PadAPI(String padurl,String apikey){
		APIKEY = apikey;
		PADURL = padurl;
		client = new EPLiteClient(PADURL, APIKEY);
	}


	public void updatePads(){
		PadThread pt = new PadThread(this,"pads");
	}
	
	public HashMap getPadmap() {
		return padmap;
	}

	
	public void setPadLists(HashMap padmap) {
		this.padmap = padmap;
		padlist = (List) padmap.get("padIDs");
	}
	
	public List getPadlist() {
		return padlist;
	}

	public EPLiteClient getClient() {
		return client;
	}


	
}
