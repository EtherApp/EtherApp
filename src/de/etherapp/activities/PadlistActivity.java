package de.etherapp.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import de.etherapp.beans.PadlistItem;
import de.etherapp.epclient.Pad;
import de.etherapp.adapters.PadlistBaseAdapter;
import de.etherapp.activities.R;


public class PadlistActivity extends Activity {

	ListView lv;
    List<PadlistItem> padlistItems; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_padlist);
		
		//get Listview we want to fill
        lv = (ListView) findViewById(R.id.padlist);
        
        //array list with all padlist items in it
        padlistItems = new ArrayList<PadlistItem>();
        
        HashMap<String, Pad> padlist = null;

        padlist = GlobalConfig.currentApi.getPadList();
        
        //iterate through pads and add them to the list
        for (Pad thispad : padlist.values()) {
        	PadlistItem item = new PadlistItem(thispad);
        	padlistItems.add(item);
        }

        //set values to the adapter
        PadlistBaseAdapter adapter = new PadlistBaseAdapter(this, padlistItems);
        
        //apply adapter to the ListView
        lv.setAdapter(adapter);        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
