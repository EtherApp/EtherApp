package de.etherapp.epclient;

import java.util.HashMap;

import org.etherpad_lite_client.EPLiteClient;

import android.accounts.NetworkErrorException;

public class PadAPI {
	private String APINAME = null;
	private String APIKEY = null;
	private String PADURL = null;
	private EPLiteClient client = null;
	private boolean ready = false;
	private boolean error = false;
	
	private HashMap<String, Pad> padhash = null;
	
	public PadAPI(String apiname,String padurl,String apikey){
		this.APINAME = apiname;
		this.APIKEY = apikey;
		this.PADURL = padurl;
		client = new EPLiteClient(PADURL, APIKEY);
	}

	public void init() throws NetworkErrorException{
		new PadThread(this);
		while(!error && !ready){}
		if(error){
			throw new NetworkErrorException("Connection Error");
		}
	}
	
	public void setPadList(HashMap<String,Pad> pad){
		if(pad != null){
			ready = true;
			padhash = pad;
		}
		else{
			error = true;
		}
	}
	
	public HashMap<String, Pad> getPadList() {
		return padhash;
	}
	
	public EPLiteClient getClient() {
		return client;
	}

	public boolean isReady(){
		return ready;
	}

	public String getAPIKEY() {
		return APIKEY;
	}

	public String getAPIURL() {
		return PADURL;
	}

	public String getAPINAME() {
		return APINAME;
	}
}
