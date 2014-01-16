package de.etherapp.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
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

        Collections.sort(padlistItems);
        
        //set values to the adapter
        PadlistBaseAdapter adapter = new PadlistBaseAdapter(this, padlistItems);
        
        //apply adapter to the ListView
        lv.setAdapter(adapter);
        
        //item click listener for ListView
        lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				PadlistItem listitem = (PadlistItem) lv.getAdapter().getItem(pos);
				
				Intent intent = new Intent();
				intent.setClassName(getPackageName(),getPackageName()+".PadContentActivity");
				System.out.println((String)listitem.getPadId());
				intent.putExtra("padid", (String)listitem.getPadId());
				startActivity(intent);
			}
		});
        
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}


