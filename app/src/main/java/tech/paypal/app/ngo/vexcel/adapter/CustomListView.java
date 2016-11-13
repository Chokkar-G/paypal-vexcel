package tech.paypal.app.ngo.vexcel.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import tech.paypal.app.ngo.vexcel.R;

/**
 * Created by Ravikumar on 11/10/2016.
 */

public class CustomListView extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] titleText;
    private final String[] subTitleText;

    public CustomListView(Activity context, String[] titleText, String[] imageId) {
        super(context, R.layout.list_member_card, titleText);
        this.context = context;
        this.titleText = titleText;
        this.subTitleText = imageId;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_member_card, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.memberNameAdapter);
        TextView txtSubTitle = (TextView) rowView.findViewById(R.id.adminAdapter);
        txtTitle.setText(titleText[position]);
        txtSubTitle.setText(subTitleText[position]);
        return rowView;
    }
}
