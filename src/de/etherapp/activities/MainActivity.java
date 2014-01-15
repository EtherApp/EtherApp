package de.etherapp.activities;

import de.etherapp.activities.R;
import de.etherapp.beans.APIlistItem;
import android.os.Bundle;
import android.os.StrictMode;
import android.accounts.NetworkErrorException;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {

	public TabHost tabHost = null;
	TabSpec tab1 = null;
	TabSpec tab2 = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//allow network on main thread... *laughing*
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		
		//TabHost that will contain the Tabs
		tabHost = (TabHost)findViewById(android.R.id.tabhost);
		
		//try to restore the last preferences
		if(GlobalConfig.loadConfig(this)){
			//recent config has been loaded - start now
			startup();
		}else{
			//no API has been created yet - directly switching to API creation dialog
			Intent intent = new Intent(this, APISettingsActivity.class);
			intent.putExtra("selected", ""); //put empty string to intend creation of new API
			startActivity(intent);	
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	            case R.id.action_settings:
	            Intent i = new Intent(this, SettingsListActivity.class);
	            startActivity(i);
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
		System.out.println("restart");
		
		startup();
	}
	

	private void startup(){
		boolean flag = false;
		
		if(!GlobalConfig.currentApi.isReady()){
			try {
				GlobalConfig.currentApi.init();
			}
			catch (NetworkErrorException e) {
				
				runOnUiThread(new Runnable(){
					  public void run(){
						  Toast.makeText(getApplicationContext(), "Could not load recently selected Etherpad. Please select another API.", Toast.LENGTH_LONG).show();
					  }
				});
				
				flag = true;
				
				//show settings activity (e.g. so that user can select another API)
				Intent intent = new Intent();
				intent.setClassName(getPackageName(),getPackageName()+".SettingsListActivity");
				startActivity(intent);
			}
			
			//only do this if no error occured before
			if(!flag){
				//wait until API is finished with laoding pad list
				while(!GlobalConfig.currentApi.isReady()){}
			}
		}
		
		//only do this if no error occured before
		if(!flag){
			
			//TAB INITIALIZATION
			tabHost.getTabWidget().removeAllViews();

			//get tab, set tab name and activity to be loaded, add tab to tabhost
			tab1 = tabHost.newTabSpec("padlist");
			tab1.setIndicator(getString(R.string.tab_pads));
			tab1.setContent(new Intent(this,PadlistActivity.class));
			tabHost.addTab(tab1);

			tab2 = tabHost.newTabSpec("testtab");
			tab2.setIndicator(getString(R.string.tab_test));
			tab2.setContent(new Intent(this,TestTabActivity.class));
			tabHost.addTab(tab2);
		}
	}
}
