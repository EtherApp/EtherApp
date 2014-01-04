package de.etherapp.activities;

import java.util.ArrayList;
import java.util.List;

import de.etherapp.activities.R;
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
	    			intent.putExtra("selected", "-1");
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
		List valueList = new ArrayList<String>();
		List<PadAPI> lp = GlobalConfig.apiList;
		
		for (int i = 0; i < lp.size(); i++) {
			valueList.add(i);
		}
		
		ListAdapter adapter = new ArrayAdapter<T>(getApplicationContext(), android.R.layout.simple_list_item_1, valueList);
		final ListView lv = (ListView)findViewById(R.id.apilist);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
				Intent intent = new Intent();
				intent.setClassName(getPackageName(),getPackageName()+".APISettingsActivity");
				intent.putExtra("selected", lv.getAdapter().getItem(arg2).toString());
				startActivity(intent);
				System.out.println("click");
			}
		});
	}

}
