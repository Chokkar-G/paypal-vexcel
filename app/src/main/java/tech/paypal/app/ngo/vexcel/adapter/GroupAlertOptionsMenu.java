package tech.paypal.app.ngo.vexcel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import tech.paypal.app.ngo.vexcel.R;

public class GroupAlertOptionsMenu extends BaseAdapter {
    String[] titleList;
    Integer[] imageList;
    Context context;
    private static LayoutInflater inflater = null;

    public GroupAlertOptionsMenu(Context mContext, String[] uploadAlertTitleList, Integer[] uploadAlertImagelist) {
        titleList = uploadAlertTitleList;
        imageList = uploadAlertImagelist;
        context = mContext;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return titleList.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        TextView alertTextView;
        ImageView alertImageView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.upload_list_alert_dialog, null);
            holder = new ViewHolder();
            holder.alertTextView = (TextView) convertView.findViewById(R.id.upload_alert_text);
            holder.alertImageView = (ImageView) convertView.findViewById(R.id.alert_imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.alertTextView.setText(titleList[position]);
        holder.alertImageView.setImageResource(imageList[position]);
        return convertView;
    }
}