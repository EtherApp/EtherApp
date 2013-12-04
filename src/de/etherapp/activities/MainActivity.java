package de.etherapp.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import de.etherapp.beans.PadlistItem;
import de.etherapp.epclient.Pad;
import de.etherapp.epclient.PadAPI;
import de.etherapp.adapters.PadlistBaseAdapter;
import de.etherapp.app.R;


public class MainActivity extends Activity implements OnClickListener{

	ListView lv;
    List<PadlistItem> padlistItems; 
	PadAPI pa = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//get Listview we want to fill
        lv = (ListView) findViewById(R.id.padlist);
        
        //array list with all padlist items in it
        padlistItems = new ArrayList<PadlistItem>();
        
        HashMap<String, Pad> padlist = null;
        
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
        
        
        //TESTBUTTON GO TO TEST ACTIVITY
        Button testbuttonmain = (Button)this.findViewById(R.id.testbuttonmain);
        
        testbuttonmain.setOnClickListener(this);
        
        
        
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View view) {
		Intent intent = new Intent(this, TabbedActivity.class);
        startActivity(intent);
		
	}
}
