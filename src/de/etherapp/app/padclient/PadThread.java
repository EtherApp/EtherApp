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
			
			HashMap<String, pad> pad = new HashMap<String, pad>();
			
			//Get list of all pad ids
			HashMap result = client.listAllPads();
			List padIds = (List) result.get("padIDs");
			
			for (Object padid : padIds) {
				//get userscount
				long usersCount = client.padUsersCount((String)padid);
				
				//get revisioncount
				long revCount = (Long) client.getRevisionsCount((String)padid).get("revisions");	
				
				//get lastedited
				long lastEdited = (Long) client.getRevisionsCount((String)padid).get("lastEdited");	
				
				pad.put((String)padid, new pad((String)padid,usersCount,revCount,lastEdited));
			}
			
			pa.setPadList(pad);
		}
		
	}
	
	
}
