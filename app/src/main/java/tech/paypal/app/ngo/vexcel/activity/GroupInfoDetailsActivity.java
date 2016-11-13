package tech.paypal.app.ngo.vexcel.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import de.greenrobot.event.EventBus;
import tech.paypal.app.ngo.vexcel.R;
import tech.paypal.app.ngo.vexcel.adapter.CustomListView;
import tech.paypal.app.ngo.vexcel.database.DatabaseHandler;
import tech.paypal.app.ngo.vexcel.model.group.Groups;

/**
 * Created by Ravikumar on 11/10/2016.
 */

public class GroupInfoDetailsActivity extends AppCompatActivity {
    private Context mContext;
    private Groups groupDetails;
    private ListView listView;
    private DatabaseHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_info_activity);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle("Group Info");
        }
        mContext = this;
        groupDetails = EventBus.getDefault().removeStickyEvent(Groups.class);

        dbHandler = new DatabaseHandler(mContext);
        dbHandler.open();

        listView = (ListView) findViewById(R.id.groupInfoList);
        String[] title = new String[]{"Name", "Description", "Members Count", "Address", "City", "State", "Country", "Zip Code"};
        String[] subTitle = new String[]{groupDetails.getGroupName(), groupDetails.getDesc(), groupDetails.getMemberCount(),
                groupDetails.getAddr1(), groupDetails.getCity(), groupDetails.getState(), groupDetails.getCountry(), groupDetails.getZipcode()};
        CustomListView adapter = new CustomListView(GroupInfoDetailsActivity.this, title, subTitle);
        listView.setAdapter(adapter);
    }
}
