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

public class TabbedActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tabbed);
		
		// create the TabHost that will contain the Tabs
		TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
		
		TabSpec tab1 = tabHost.newTabSpec("First Tab");
        TabSpec tab2 = tabHost.newTabSpec("Second Tab");
        TabSpec tab3 = tabHost.newTabSpec("Third tab");
		
		
     // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
         tab1.setIndicator("Tab1");
         tab1.setContent(new Intent(this,MainActivity.class));
         
         tab2.setIndicator("Tab2");
         tab2.setContent(new Intent(this,Tab2Activity.class));

         tab3.setIndicator("Tab3");
         tab3.setContent(new Intent(this,Tab3Activity.class));
         
         /** Add the tabs  to the TabHost to display. */
         tabHost.addTab(tab1);
         tabHost.addTab(tab2);
         tabHost.addTab(tab3);
        
        
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tabbed, menu);
		return true;
	}

}
