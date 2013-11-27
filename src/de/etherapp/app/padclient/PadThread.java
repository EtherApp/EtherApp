package de.etherapp.app.padclient;

import java.util.HashMap;
import java.util.List;

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
		if(method == "init"){
			
			HashMap<String, Pad> pad = new HashMap<String, Pad>();
			
			//Get list of all pad ids
			HashMap result = client.listAllPads();
			List padIds = (List) result.get("padIDs");
			
			for (Object padid : padIds) {
				//get userscount
				long usersCount = client.padUsersCount((String)padid);
				
				//get revisioncount
				System.out.println((String)padid);
				long revCount = (Long) client.getRevisionsCount((String)padid).get("revisions");
				
				
				//get lastedited
				long lastEdited = (Long) client.getLastEdited((String)padid).get("lastEdited");	
				
				pad.put((String)padid, new Pad((String)padid,usersCount,revCount,lastEdited));
			}
			
			pa.setPadList(pad);
		}
		
	}
	
	
}
