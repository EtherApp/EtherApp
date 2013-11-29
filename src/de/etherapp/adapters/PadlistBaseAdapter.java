package de.etherapp.adapters;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ActionBar.OnNavigationListener;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TextView;
import de.etherapp.app.R;
import de.etherapp.beans.PadlistItem;
import de.etherapp.tasks.PadDataTask;

/*
 * Base adapter for showing padlist
 * instance is being created in activity
 */
public class PadlistBaseAdapter extends BaseAdapter implements OnClickListener{
    Context context;
    List<PadlistItem> padlistItems; //list with all padlist items (PadlistItem) in it
 
    public PadlistBaseAdapter(Context context, List<PadlistItem> items) {
        this.context = context;
        this.padlistItems = items;
    }
 
    private class ViewHolder {
        TextView txtPadId      = null;
        TextView txtUsersCount = null;
        TextView txtRevCount   = null;
        TextView txtLastEdited = null;
        ImageButton delbtn = null;
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

		//how to get the item?
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				
		// set title
		alertDialogBuilder.setTitle("Delete $PADNAME");
 
		// set dialog message
		alertDialogBuilder.setMessage("Are you sure?");
		alertDialogBuilder.setCancelable(false);
		
		alertDialogBuilder.setPositiveButton("Delete",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			}
		});
			
		alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// if this button is clicked, just close
				// the dialog box and do nothing
				dialog.cancel();
			}
		});
 
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
 
		// show it
		alertDialog.show();
	}
}
