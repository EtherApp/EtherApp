package de.etherapp.tasks;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

import org.etherpad_lite_client.EPLiteClient;

import de.etherapp.app.R;
import de.etherapp.beans.PadlistItem;
import de.etherapp.epclient.Pad;
import de.etherapp.app.GlobalConfig;
import android.os.AsyncTask;
import android.widget.TextView;


public class PadDataTask  extends AsyncTask<String, Void, String> {
	private final WeakReference<TextView> tvRef;
	private PadlistItem pli = null;
	
	public PadDataTask(TextView tv, PadlistItem pli) {
		this.tvRef = new WeakReference<TextView>(tv);
		this.pli = pli;
	}

	@Override
	//background task: retrieve data from API
	protected String doInBackground(String... method) {
		//get client from current API
		EPLiteClient client = GlobalConfig.currentApi.getClient();
		
		//depending on the "method" string given, run a method
		//write return value to Pad object
		//give return value to onPostExecute unction in order to apply it to the textview
		
		if(method[0].equals("usersCount")){
			long usersCount = client.padUsersCount(pli.getPadId());
			pli.setUsersCount(usersCount);
			return pli.getUsersCountString();
		}
		else if(method[0].equals("revCount")){
			HashMap result = client.getRevisionsCount(pli.getPadId());
			long revCount = (Long) result.get("revisions");
			pli.setRevCount(revCount);
			return pli.getRevCountString();
		}
		else if(method[0].equals("lastEdited")){
			HashMap result = client.getLastEdited(pli.getPadId());
			long lastEdited = (Long) result.get("lastEdited");
			pli.setLastEdited(lastEdited);
			return pli.getLastEditedString();
		}
		else{
			return null;
		}
	}

	@Override
	//apply String to the TextView when it has been retrieved successfully
	protected void onPostExecute(String result) {
		if (isCancelled()) {
			result = null;
		}

		if (tvRef != null) {
			TextView tv= tvRef.get();
			if (tv != null) {

				if (result != null) {
					tv.setText(result);
				} else {
					//TODO: set placeholder
				}
			}

		}
	}
}