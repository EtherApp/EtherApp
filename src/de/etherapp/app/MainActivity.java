package de.etherapp.app;

import java.util.ArrayList;
import java.util.List;

import de.etherapp.app.padclient.PadAPI;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//get Listview we want to fill
        lv = (ListView) findViewById(R.id.padlist);
        
        //create array list
        ArrayList<String> padlist = new ArrayList<String>();
              
        PadAPI pa = new PadAPI("http://fastreboot.de:9001","8EkKqoT0CR28PcRDpF311XLtspAchXuM");
        pa.updatePads();
        
        List l = null;
        while(l == null){
        	l = pa.getPadlist();  	
        }
        
        for (Object name : l) {
            padlist.add((String)name);
		}
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, padlist);
        lv.setAdapter(arrayAdapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}
