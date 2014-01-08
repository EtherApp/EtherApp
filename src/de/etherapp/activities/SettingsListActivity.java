package de.etherapp.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.etherapp.activities.R;
import de.etherapp.adapters.APIlistBaseAdapter;
import de.etherapp.adapters.PadlistBaseAdapter;
import de.etherapp.beans.APIlistItem;
import de.etherapp.beans.PadlistItem;
import de.etherapp.epclient.Pad;
import de.etherapp.epclient.PadAPI;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class SettingsListActivity<T> extends Activity {

	ListView lv;
	List<APIlistItem> apilistItems;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings_list);
		
		startup();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings_list, menu);
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	            case R.id.action_add:
	            	Intent intent = new Intent(this,APISettingsActivity.class);
	    			intent.putExtra("selected", "");
	    			startActivity(intent);
	                return true;
	            case R.id.action_quit:
	            	this.finish();
	            	return true;
	            default:
	                return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		startup();
	}
	
	private void startup(){
		
		//get Listview we want to fill
        lv = (ListView) findViewById(R.id.apilist);
        
        //array list with all padlist items in it
        apilistItems = new ArrayList<APIlistItem>();
        
        
        //iterate through APIs and add them to the list
        for(PadAPI api : GlobalConfig.apiMap.values()){
            APIlistItem item = new APIlistItem(api);
        	apilistItems.add(item);
        }
        
        //set values to the adapter
        APIlistBaseAdapter adapter = new APIlistBaseAdapter(this, apilistItems);
        
        //apply adapter to the ListView
        lv.setAdapter(adapter); 
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				
				Intent intent = new Intent();
				intent.setClassName(getPackageName(),getPackageName()+".APISettingsActivity");
				
				APIlistItem listitem = (APIlistItem) lv.getAdapter().getItem(pos);
				System.out.println(listitem.getApiId()); //DEBUG
				
				//intent.putExtra("selected", (String)listitem.getApiId());
				intent.putExtra("selected", "30441cdf-f737-4623-9350-c2de0328cd50");
				System.out.println(listitem.getApiId());
				
				startActivity(intent);
				System.out.println("click");
			}
		});
	}

}
