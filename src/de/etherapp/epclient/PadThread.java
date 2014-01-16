package de.etherapp.epclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.etherpad_lite_client.EPLiteClient;
import org.etherpad_lite_client.EPLiteException;

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
		HashMap<String, Pad> pads = new HashMap<String, Pad>();
			
		//Get list of all pad ids
		try{
			HashMap result = client.listAllPads();
			List padIds = (List) result.get("padIDs");
			
			result = client.listAllGroups();
			ArrayList<String> groups = (ArrayList<String>) result.get("groupIDs");
			
			for (Object padid : padIds) {
				pads.put((String)padid, new Pad((String)padid));
			}
			pa.setLists(pads,groups);
		}
		catch(EPLiteException e){
			System.out.println(e);
			pa.setLists(null,null);
		}

	}
}
