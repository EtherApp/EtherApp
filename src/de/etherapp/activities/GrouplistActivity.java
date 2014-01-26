package de.etherapp.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.etherapp.activities.R;
import de.etherapp.adapters.GrouplistBaseAdapter;
import de.etherapp.beans.GrouplistItem;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class GrouplistActivity extends Activity{

	ListView lv;
    List<GrouplistItem> grouplistItems; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grouplist);
		
		//get Listview we want to fill
        lv = (ListView) findViewById(R.id.grouplist);
        
        //array list with all padlist items in it
        grouplistItems = new ArrayList<GrouplistItem>();
        
        ArrayList<String> grouplist = GlobalConfig.currentApi.getGroupList();
        
        //iterate through pads and add them to the list
        for (String group : grouplist) {
        	GrouplistItem item = new GrouplistItem(group);
        	grouplistItems.add(item);
        }

        Collections.sort(grouplistItems);
        
        //set values to the adapter
        GrouplistBaseAdapter adapter = new GrouplistBaseAdapter(this, grouplistItems);
        
        //apply adapter to the ListView
        lv.setAdapter(adapter);
        
        //item click listener for ListView
        lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				
				//get PadlistItem
				//GrouplistItem listitem = (GrouplistItem) lv.getAdapter().getItem(pos);
				
				//start activity
//				Intent intent = new Intent();
//				intent.setClassName(getPackageName(),getPackageName()+".PadContentActivity");
//				System.out.println((String)listitem.getPadId());
//				intent.putExtra("padid", (String)listitem.getPadId());
//				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.group, menu);
		return true;
	}

}
