package de.etherapp.tasks;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

import org.etherpad_lite_client.EPLiteClient;
import org.etherpad_lite_client.EPLiteException;

import de.etherapp.activities.GlobalConfig;
import de.etherapp.app.R;
import de.etherapp.beans.PadlistItem;
import de.etherapp.epclient.Pad;
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
	// Actual download method, run in the task thread
	protected String doInBackground(String... method) {
		EPLiteClient client = GlobalConfig.currentApi.getClient();
		
		if(method[0].equals("usersCount")){
			long usersCount = 0;
			try{
				usersCount = client.padUsersCount(pli.getPadId());
			}
			catch(EPLiteException e){
				System.out.println(e);
				return null;
			}
			pli.setUsersCount(usersCount);
			return pli.getUsersCountString();
		}
		else if(method[0].equals("revCount")){
			HashMap result = null;
			try{
				result = client.getRevisionsCount(pli.getPadId());
			}
			catch(EPLiteException e){
				System.out.println(e);
				return null;
			}
			long revCount = (Long) result.get("revisions");
			pli.setRevCount(revCount);
			return pli.getRevCountString();
		}
		else if(method[0].equals("lastEdited")){
			HashMap result = null;
			try{
				result = client.getLastEdited(pli.getPadId());
			}
			catch(EPLiteException e){
				System.out.println(e);
				return null;
			}
			long lastEdited = (Long) result.get("lastEdited");
			pli.setLastEdited(lastEdited);
			return pli.getLastEditedString();
		}
		else{
			return null;
		}
	}

	@Override
	// Once the image is downloaded, associates it to the imageView
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
					//set placeholder
				}
			}

		}
	}
}