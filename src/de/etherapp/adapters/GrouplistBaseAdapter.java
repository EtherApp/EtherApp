package de.etherapp.adapters;

import java.util.List;

import org.etherpad_lite_client.EPLiteException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import de.etherapp.activities.GlobalConfig;
import de.etherapp.activities.R;
import de.etherapp.beans.GrouplistItem;


/*
 * Base adapter for showing API list
 * instance is being created in activity
 */
public class GrouplistBaseAdapter extends BaseAdapter implements OnClickListener{
	
	Context context;
    List<GrouplistItem> grouplistItems; //list with all group list items (GrouplistItem) in it
    int clickedPos;
 
    public GrouplistBaseAdapter(Context context, List<GrouplistItem> items) {
        this.context = context;
        this.grouplistItems = items;
    }
 
    private class ViewHolder {
        TextView txtGroup = null;
        ImageButton delbtn     = null;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
 
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.grouplist_item, null);
            holder = new ViewHolder();
            
            holder.txtGroup = (TextView) convertView.findViewById(R.id.txtGroup);
            holder.delbtn = (ImageButton) convertView.findViewById(R.id.btnDeleteGroup);
            holder.delbtn.setOnClickListener(this);
            
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
 
        //item to fill with values
        GrouplistItem grouplistItem = (GrouplistItem) getItem(position);
        
        //set values to list item
        holder.txtGroup.setText(grouplistItem.getGroup());
        
       	//delete button
        holder.delbtn.setId(position); //give it the list position (the current list item is recognized by the position)
       	
        holder.delbtn.setOnClickListener(new OnClickListener() { //implement a "flying" click listener

        	@Override
        	public void onClick(View v) {
        		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        		clickedPos = v.getId();

        		//get grouplistItem where we can find the desired group
        		GrouplistItem item = (GrouplistItem)getItem(clickedPos);

        		//set dialog title
        		alertDialogBuilder.setTitle("Delete Group");

        		//set dialog message
        		alertDialogBuilder.setMessage("Group \"" + item.getGroup() + "\" will be deleted. Continue?");
        		alertDialogBuilder.setCancelable(false);

        		alertDialogBuilder.setPositiveButton("Delete",new DialogInterface.OnClickListener() {
        			public void onClick(DialogInterface dialog, int id) {
        				//get list item from given list position
        				GrouplistItem item = grouplistItems.get(clickedPos);

        				//delete group online
        				try{
        					final String group = item.getGroup();
        					GlobalConfig.currentApi.getClient().deleteGroup(group);
        					//delete from local list
        					//grouplistItems.remove(clickedPos); //not needed because whole activity is being reloaded in next step

        					GlobalConfig.ma.runOnUiThread(new Runnable(){
        						public void run(){
        							Toast.makeText(GlobalConfig.ma.getApplicationContext(), "Group \"" + group + "\"deleted", Toast.LENGTH_LONG).show();
        						}
        					});

        					//restart MainActivity
        					//TODO: Only reload list or inner activity
        					GlobalConfig.ma.recreate();

        				}catch(EPLiteException e){
        					System.out.println(e);
        					GlobalConfig.ma.runOnUiThread(new Runnable(){
        						public void run(){
        							Toast.makeText(GlobalConfig.ma.getApplicationContext(), "Error! Could not delete group.", Toast.LENGTH_LONG).show();
        						}
        					});
        				}


        			}
        		});

        		alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        			public void onClick(DialogInterface dialog, int id) {
        				//if this button is clicked, just close the dialog box and do nothing
        				dialog.cancel();
        			}
        		});

        		//create alert dialog
        		AlertDialog alertDialog = alertDialogBuilder.create();

        		//show it
        		alertDialog.show();
        	} 
        });

        return convertView;
    }
 
    @Override
    public int getCount() {
        return grouplistItems.size();
    }
 
    @Override
    public Object getItem(int position) {
        return grouplistItems.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return grouplistItems.indexOf(getItem(position));
    }
    @Override
    public void onClick(View v) {
    	// TODO Auto-generated method stub
    }
	
}
