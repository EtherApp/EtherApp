package de.etherapp.epclient;

import java.util.HashMap;
import java.util.List;

import org.etherpad_lite_client.EPLiteClient;

public class PadThread extends Thread{

	private PadAPI pa = null;
	private HashMap hm = null;
	private EPLiteClient client = null;
		
	public PadThread(PadAPI pa){
		this.pa = pa;
		this.client = pa.getClient();
		this.start();
	}
	
	public void run(){			
		HashMap<String, Pad> pad = new HashMap<String, Pad>();
			
		//Get list of all pad ids
		HashMap result = client.listAllPads();
		List padIds = (List) result.get("padIDs");
			
		for (Object padid : padIds) {
			pad.put((String)padid, new Pad((String)padid));
		}
		pa.setPadList(pad);
	}
}
