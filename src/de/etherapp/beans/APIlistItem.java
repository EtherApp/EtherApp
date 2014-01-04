package de.etherapp.beans;
import de.etherapp.epclient.PadAPI;

public class APIlistItem {
	public PadAPI api;
 
	public APIlistItem(PadAPI api) {
		this.api = api;
    }

	public PadAPI getAPI(){
		return api;
	}
	
	public String getApiName() {
		return api.getAPINAME();
	}
}
