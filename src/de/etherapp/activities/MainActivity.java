package de.etherapp.activities;

import de.etherapp.app.R;
import de.etherapp.epclient.PadAPI;
import android.os.Bundle;
import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.content.SharedPreferences;
import android.content.res.Resources.NotFoundException;

public class MainActivity extends TabActivity {
	
	public static final String preference_name = "etherapp_preferences";
	public static final String preference_api = "etherapp_api"; //extended by number

	PadAPI pa = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		//try to restore the last preferences 
		SharedPreferences glshared = getSharedPreferences(preference_name, 0);

		//String ip = preferences.getString("IP", "");
		int currentApiPos = glshared.getInt("currentApiPos", -1);
		int apiCount = glshared.getInt("apiCount", -1);

		System.out.println(apiCount + " ???? " + currentApiPos);
		if(apiCount > -1 & currentApiPos > -1){
			//load active api from prefrences
			System.out.println(apiCount + " +++++++ " + currentApiPos);
			
			SharedPreferences apishared = getSharedPreferences(preference_api + currentApiPos, 0);
			// null -.-
			String apikey = apishared.getString("APIKEY", "");
			String padurl = apishared.getString("PADURL", "");
			pa = new PadAPI(padurl, apikey);
			int pos = GlobalConfig.putNewApi(pa);
			GlobalConfig.selectApi(pos);
	        try {
				GlobalConfig.currentApi.init();
			} catch (NetworkErrorException e) {
				this.finish();
			}
	        while(!GlobalConfig.currentApi.isReady()){}
			
			//TabHost that will contain the Tabs
			TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);

			//get tab, set tab name and activity to be loaded, add tab to tabhost
			TabSpec tab1 = tabHost.newTabSpec("padlist");
			tab1.setIndicator("Pads");
	        tab1.setContent(new Intent(this,PadlistActivity.class));
	        tabHost.addTab(tab1);
			
	        TabSpec tab2 = tabHost.newTabSpec("testtab");
	        tab2.setIndicator("Groups");
	        tab2.setContent(new Intent(this,TestTabActivity.class));
	        tabHost.addTab(tab2);
	        
		}
		else{
			System.out.println(apiCount + " ------- " + currentApiPos);
			//Intent intent = new Intent(this,SettingsActivity.class);
		    //startActivity(intent);
			
	        //INIT API - 
	        pa = new PadAPI("http://fastreboot.de:9001","8EkKqoT0CR28PcRDpF311XLtspAchXuM");
	        int pos = GlobalConfig.putNewApi(pa);
	        GlobalConfig.selectApi(pos);
	        
	        try {
				GlobalConfig.currentApi.init();
			} catch (NetworkErrorException e) {
				this.finish();
			}
	        
	        while(!GlobalConfig.currentApi.isReady()){}
			
			//TabHost that will contain the Tabs
			TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);

			//get tab, set tab name and activity to be loaded, add tab to tabhost
			TabSpec tab1 = tabHost.newTabSpec("padlist");
			tab1.setIndicator("Pads");
	        tab1.setContent(new Intent(this,PadlistActivity.class));
	        tabHost.addTab(tab1);
			
	        TabSpec tab2 = tabHost.newTabSpec("testtab");
	        tab2.setIndicator("Groups");
	        tab2.setContent(new Intent(this,TestTabActivity.class));
	        tabHost.addTab(tab2);
		}
	}

	@Override
	public void onResume() 
	{
		super.onResume();
	}

	@Override
	public void onPause() 
	{
		super.onPause();
	}
	
	public void onStop()
	{
		super.onStop();
		SharedPreferences preferences = getSharedPreferences(preference_name, 0);
	    SharedPreferences.Editor editor = preferences.edit();
	    editor.putInt("currentApiPos", GlobalConfig.getCurrentApiPos());
	    editor.putInt("apiCount", GlobalConfig.getApiCount());
	      
	    // und alles schreiben
	    editor.commit();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tabbed, menu);
		return true;
	}

}
