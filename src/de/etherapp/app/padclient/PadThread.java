package de.etherapp.app.padclient;

import java.util.HashMap;

import org.etherpad_lite_client.EPLiteClient;

public class PadThread extends Thread{

	private PadAPI pa = null;
	private String method = null;
	private HashMap hm = null;
	private EPLiteClient client = null;
		
	public PadThread(PadAPI pa,String method){
		this.pa = pa;
		this.method = method;
		this.client = pa.getClient();
		this.start();
	}
	
	public void run(){
		if(method == "pads"){
			pa.setPadLists(client.listAllPads());
		}
		
	}
	
	
}
