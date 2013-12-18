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

	TabHost tabHost = null;
	TabSpec tab1 = null;
	TabSpec tab2 = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//TabHost that will contain the Tabs
		tabHost = (TabHost)findViewById(android.R.id.tabhost);
		
		//try to restore the last preferences
		GlobalConfig.loadConfig(this);

		int apiCount = GlobalConfig.getApiCount();
		//GlobalConfig.deleteApi(GlobalConfig.getApiCount());
		int currentApiPos = GlobalConfig.getCurrentApiPos();

		
		if(apiCount > -1 && currentApiPos > -1){ //there is a config we could use
			startup();
		}
		else{ // no config get settings from user
			Intent intent = new Intent(this,APISettingsActivity.class);
			intent.putExtra("selected", "-1");
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
		
		//tabHost.getTabWidget().removeView(tabHost.getTabWidget().getChildAt(0));
		tabHost.getTabWidget().removeAllViews();

		//get tab, set tab name and activity to be loaded, add tab to tabhost
		tab1 = tabHost.newTabSpec("padlist");
		tab1.setIndicator("Pads");
		tab1.setContent(new Intent(this,PadlistActivity.class));
		tabHost.addTab(tab1);

		tab2 = tabHost.newTabSpec("testtab");
		tab2.setIndicator("TESTTAB");
		tab2.setContent(new Intent(this,TestTabActivity.class));
		tabHost.addTab(tab2);
	}
}
