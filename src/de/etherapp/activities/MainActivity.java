package de.etherapp.activities;

import de.etherapp.activities.R;
import android.os.Bundle;
import android.accounts.NetworkErrorException;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {

	public TabHost tabHost = null;
	TabSpec tab1 = null;
	TabSpec tab2 = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
		startup();
	}
	
	private void startup(){
		if(!GlobalConfig.currentApi.isReady()){
			try {
				GlobalConfig.currentApi.init();
			}
			catch (NetworkErrorException e) {
				this.finish();
			}
			while(!GlobalConfig.currentApi.isReady()){}
		}
		
		
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
