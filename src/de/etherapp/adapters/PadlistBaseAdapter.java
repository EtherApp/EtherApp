package de.etherapp.adapters;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import de.etherapp.app.R;
import de.etherapp.beans.PadlistItem;
 
public class PadlistBaseAdapter extends BaseAdapter {
    Context context;
    List<PadlistItem> padlistItems;
 
    public PadlistBaseAdapter(Context context, List<PadlistItem> items) {
        this.context = context;
        this.padlistItems = items;
    }
 
    /*private view holder class*/
    private class ViewHolder {
        TextView txtPadId      = null;
        TextView txtUsersCount = null;
        TextView txtRevCount   = null;
        TextView txtLastEdited = null;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
 
        LayoutInflater mInflater = (LayoutInflater)
            context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.padlist_item, null);
            holder = new ViewHolder();
            
            holder.txtPadId      = (TextView) convertView.findViewById(R.id.txtPadId);
            holder.txtUsersCount = (TextView) convertView.findViewById(R.id.txtUsersCount);
            holder.txtRevCount	 = (TextView) convertView.findViewById(R.id.txtRevCount);
            holder.txtLastEdited = (TextView) convertView.findViewById(R.id.txtLastEdited);
           
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
 
        PadlistItem padlistItem = (PadlistItem) getItem(position);
 
        holder.txtPadId.setText(padlistItem.getPadId());
        holder.txtUsersCount.setText(padlistItem.getUsersCountString());
        holder.txtRevCount.setText(padlistItem.getRevCountString());
        holder.txtLastEdited.setText(padlistItem.getLastEditedString());
 
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
}
