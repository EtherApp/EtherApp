package de.etherapp.app;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import de.etherapp.beans.PadlistItem;
import de.etherapp.adapters.PadlistBaseAdapter;

public class MainActivity extends Activity {

	ListView lv;
    List<PadlistItem> padlistItems;
	
	public static final String[] titles = new String[] { "netzak", "protokolle", "netze2", "netze", "uet", "flegl", "netzman" };
	 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//get Listview we want to fill
        lv = (ListView) findViewById(R.id.padlist);
        
        
        padlistItems = new ArrayList<PadlistItem>();
        for (int i = 0; i < titles.length; i++) {
        	PadlistItem item = new PadlistItem(titles[i], 3, 10, 111781718);
        	padlistItems.add(item);
        }
 
        PadlistBaseAdapter adapter = new PadlistBaseAdapter(this, padlistItems);
        lv.setAdapter(adapter);
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
