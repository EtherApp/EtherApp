package de.etherapp.app;

import java.util.ArrayList;
import java.util.List;
import de.etherapp.app.padclient.PadAPI;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import de.etherapp.beans.PadlistItem;
import de.etherapp.adapters.PadlistBaseAdapter;

public class MainActivity extends Activity {

	ListView lv;
    List<PadlistItem> padlistItems; 
	PadAPI pa = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//get Listview we want to fill
        lv = (ListView) findViewById(R.id.padlist);
        
        //create array list
        padlistItems = new ArrayList<PadlistItem>();
              
        pa = new PadAPI("http://fastreboot.de:9001","8EkKqoT0CR28PcRDpF311XLtspAchXuM");
        pa.updatePads();
        
        List l = null;
        while(l == null){
        	l = pa.getPadlist();  	
        }
        
        for (Object name : l) {
            PadlistItem item = new PadlistItem((String)name, 3, 10, 181718);
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
