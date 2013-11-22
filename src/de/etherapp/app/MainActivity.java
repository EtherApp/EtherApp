package de.etherapp.app;

import java.util.ArrayList;

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
        padlist.add("netze1");
        padlist.add("protolol");
        padlist.add("netze2");
        padlist.add("uet");
        padlist.add("komplexlabor");
        padlist.add("netzak");
        padlist.add("netzak2");
        padlist.add("swe");
        padlist.add("NZ1-Switching");
        padlist.add("blabla");
        padlist.add("foobar");
        padlist.add("loremipsum");
        
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_2, padlist);
        lv.setAdapter(arrayAdapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}