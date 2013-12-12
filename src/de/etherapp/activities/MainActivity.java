package de.etherapp.activities;

import de.etherapp.app.R;
import de.etherapp.epclient.PadAPI;
import android.os.Bundle;
import android.accounts.NetworkErrorException;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {

	public static final String preference_name = "etherapp_preferences";
	public static final String preference_api = "etherapp_api"; //extended by number

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//try to restore the last preferences 
		GlobalConfig.loadConfig(this);
		GlobalConfig.selectApi(GlobalConfig.getApiCount());
		
		int apiCount = GlobalConfig.getApiCount();
		int currentApiPos = GlobalConfig.getCurrentApiPos();

		if(apiCount > -1 & currentApiPos > -1){
			if(!GlobalConfig.currentApi.isReady()){
				try {
					GlobalConfig.currentApi.init();
				}
				catch (NetworkErrorException e) {
					this.finish();
				}
				while(!GlobalConfig.currentApi.isReady()){}
			}
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
			Intent intent = new Intent(this,SettingsActivity.class);
			startActivity(intent);

			//INIT API - 
			/*pa = new PadAPI("http://fastreboot.de:9001","8EkKqoT0CR28PcRDpF311XLtspAchXuM");
	        int pos = GlobalConfig.putNewApi(pa);
	        GlobalConfig.selectApi(pos);*/
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tabbed, menu);
		return true;
	}

	@Override
	protected void onStart() {
		super.onStart();
		System.out.println("ON START");
	}

}
