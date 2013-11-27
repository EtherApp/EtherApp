package de.etherapp.adapters;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import de.etherapp.app.R;
import de.etherapp.beans.PadlistItem;
 
public class CustomBaseAdapter extends BaseAdapter {
    Context context;
    List<PadlistItem> padlistItems;
 
    public CustomBaseAdapter(Context context, List<PadlistItem> items) {
        this.context = context;
        this.padlistItems = items;
    }
 
    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtDesc;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
 
        LayoutInflater mInflater = (LayoutInflater)
            context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.padlist_item, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.desc);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
 
        PadlistItem padlistItem = (PadlistItem) getItem(position);
 
        holder.txtDesc.setText(padlistItem.getDesc());
        holder.txtTitle.setText(padlistItem.getTitle());
        holder.imageView.setImageResource(padlistItem.getImageId());
 
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
