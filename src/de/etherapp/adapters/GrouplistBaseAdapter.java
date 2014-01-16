package de.etherapp.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import de.etherapp.activities.R;
import de.etherapp.beans.GrouplistItem;


/*
 * Base adapter for showing API list
 * instance is being created in activity
 */
public class GrouplistBaseAdapter extends BaseAdapter implements OnClickListener{
	
	Context context;
    List<GrouplistItem> grouplistItems; //list with all group list items (GrouplistItem) in it
 
    public GrouplistBaseAdapter(Context context, List<GrouplistItem> items) {
        this.context = context;
        this.grouplistItems = items;
    }
 
    private class ViewHolder {
        TextView txtGroup = null;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
 
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.grouplist_item, null);
            holder = new ViewHolder();
            
            holder.txtGroup = (TextView) convertView.findViewById(R.id.txtGroup);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
 
        //item to fill with values
        GrouplistItem grouplistItem = (GrouplistItem) getItem(position);
        
        //set values to list item
        holder.txtGroup.setText(grouplistItem.getGroup());
        
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

	}
}
