package de.etherapp.activities;

import de.etherapp.app.R;
import de.etherapp.app.R.layout;
import de.etherapp.app.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tabbed, menu);
		return true;
	}

}
