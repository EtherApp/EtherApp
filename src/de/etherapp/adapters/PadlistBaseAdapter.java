package de.etherapp.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.etherpad_lite_client.EPLiteException;

import android.app.Activity;
import android.app.ActionBar.OnNavigationListener;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import de.etherapp.activities.GlobalConfig;
import de.etherapp.activities.R;
import de.etherapp.beans.APIlistItem;
import de.etherapp.beans.PadlistItem;
import de.etherapp.epclient.Pad;
import de.etherapp.tasks.PadDataTask;

/*
 * Base adapter for showing padlist
 * instance is being created in activity
 */
public class PadlistBaseAdapter extends BaseAdapter implements OnClickListener{
    Context context;
    List<PadlistItem> padlistItems; //list with all padlist items (PadlistItem) in it
    int clickedPos;
 
    public PadlistBaseAdapter(Context context, List<PadlistItem> items) {
        this.context = context;
        this.padlistItems = items;
    }
 
    private class ViewHolder {
        TextView txtPadId      = null;
        TextView txtUsersCount = null;
        TextView txtRevCount   = null;
        TextView txtLastEdited = null;
        ImageButton delbtn     = null;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
 
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.padlist_item, null);
            holder = new ViewHolder();
            
            holder.txtPadId      = (TextView) convertView.findViewById(R.id.txtPadId);
            holder.txtUsersCount = (TextView) convertView.findViewById(R.id.txtUsersCount);
            holder.txtRevCount	 = (TextView) convertView.findViewById(R.id.txtRevCount);
            holder.txtLastEdited = (TextView) convertView.findViewById(R.id.txtLastEdited);
            holder.delbtn = (ImageButton) convertView.findViewById(R.id.btnDeletePad);
            holder.delbtn.setOnClickListener(this);
            
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
         
        //item to fill with values
        PadlistItem padlistItem = (PadlistItem) getItem(position);
        
        //set values to list item
        holder.txtPadId.setText(padlistItem.getPadName());  

        //start loaders for asynchronous loading of metadata
       	new PadDataTask(holder.txtUsersCount, padlistItem).execute("usersCount");
       	new PadDataTask(holder.txtRevCount, padlistItem).execute("revCount");
       	new PadDataTask(holder.txtLastEdited, padlistItem).execute("lastEdited");
        
       	//delete button
        holder.delbtn.setId(position); //give it the list position (the current list item is recognized by the position)
       	
        holder.delbtn.setOnClickListener(new OnClickListener() { //implement a "flying" click listener

       		@Override
        	public void onClick(View v) {
        		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        		clickedPos = v.getId();
        		
        		//get padlistItem where we can find the desired pad
        		PadlistItem item = (PadlistItem)getItem(clickedPos);
   		
        		//set dialog title
        		alertDialogBuilder.setTitle("Delete pad");
         
        		//set dialog message
        		alertDialogBuilder.setMessage("Pad \"" + item.getPadName() + "\" will be deleted. Continue?");
        		alertDialogBuilder.setCancelable(false);
        		
        		alertDialogBuilder.setPositiveButton("Delete",new DialogInterface.OnClickListener() {
        			public void onClick(DialogInterface dialog, int id) {
        				//get list item from given list position
        				PadlistItem item = padlistItems.get(clickedPos);
        				
        				//delete pad online
        				try{
        					GlobalConfig.currentApi.getClient().deletePad(item.getPadId());
        				}catch(EPLiteException e){
        					System.out.println(e);
        				}
        				
        				//delete from local list
        				//padlistItems.remove(clickedPos); //not needed because whole activity is being reloaded in next step
        				
        				//restart MainActivity
        				//TODO: Only reload list or inner activity
        				GlobalConfig.ma.recreate();
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
        return padlistItems.size();
    }
 
    @Override
    public Object getItem(int position) {
        return padlistItems.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return padlistItems.indexOf(getItem(position));
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		System.out.println("Click on adapter");
		
	}
	
	
}
