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
import android.widget.TextView;
import android.widget.ImageView;
import de.etherapp.activities.GlobalConfig;
import de.etherapp.activities.R;
import de.etherapp.beans.APIlistItem;
import de.etherapp.tasks.PadDataTask;

/*
 * Base adapter for showing API list
 * instance is being created in activity
 */
public class APIlistBaseAdapter extends BaseAdapter implements OnClickListener{
	
	Context context;
    List<APIlistItem> apilistItems; //list with all api list items (APIlistItem) in it
 
    public APIlistBaseAdapter(Context context, List<APIlistItem> items) {
        this.context = context;
        this.apilistItems = items;
    }
 
    private class ViewHolder {
        TextView txtApiId = null;
        ImageView imgApiSelected = null;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
 
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.apilist_item, null);
            holder = new ViewHolder();
            
            holder.txtApiId = (TextView) convertView.findViewById(R.id.txtApiId);
            holder.imgApiSelected = (ImageView) convertView.findViewById(R.id.imgApiSelected);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
 
        //item to fill with values
        APIlistItem apilistItem = (APIlistItem) getItem(position);
        
        //set values to list item
        holder.txtApiId.setText(apilistItem.getApiName());

        //if this is the currently selected API, show icon
        if(apilistItem.getApiId() == GlobalConfig.currentApi.getAPIID()){
        	holder.imgApiSelected.setVisibility(View.VISIBLE);
        }
        
        return convertView;
    }
 
    @Override
    public int getCount() {
        return apilistItems.size();
    }
 
    @Override
    public Object getItem(int position) {
        return apilistItems.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return apilistItems.indexOf(getItem(position));
    }

	@Override
	public void onClick(View v) {

	
	}
}
