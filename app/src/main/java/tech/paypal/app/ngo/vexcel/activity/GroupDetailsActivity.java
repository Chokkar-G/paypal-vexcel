package tech.paypal.app.ngo.vexcel.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import tech.paypal.app.ngo.vexcel.R;
import tech.paypal.app.ngo.vexcel.activity.resources.SpacesItemDecoration;
import tech.paypal.app.ngo.vexcel.adapter.MemberRecyclerViewAdapter;
import tech.paypal.app.ngo.vexcel.database.DatabaseHandler;
import tech.paypal.app.ngo.vexcel.model.group.Groups;
import tech.paypal.app.ngo.vexcel.model.member.MemberData;

/**
 * Created by Ravikumar on 11/9/2016.
 */

public class GroupDetailsActivity extends AppCompatActivity {
    private Context mContext;
    private Groups groupDetails;
    FloatingActionButton fabAddMember;

    private RecyclerView recyclerView;
    private DatabaseHandler dbHandler;
    private MemberRecyclerViewAdapter mAdapter;
    private ArrayList<MemberData> membersArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_details_activity);
        groupDetails = EventBus.getDefault().removeStickyEvent(Groups.class);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle("" + groupDetails.getGroupName());
        }
        mContext = this;
        dbHandler = new DatabaseHandler(mContext);
        dbHandler.open();

        recyclerView = (RecyclerView) findViewById(R.id.memberRecycleView);
        recyclerView.addItemDecoration(new SpacesItemDecoration(10));
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        fabAddMember = (FloatingActionButton) findViewById(R.id.fabAddMember);
        fabAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().postSticky(groupDetails);
                startActivity(new Intent(mContext, MemberCreationActivity.class));
            }
        });

        loadMemberDetails();
    }

    /*private void getMemberDetaitls() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        RestClient.setupRestClient();
        RestClient.get().membersList(tokenKey, groupDetails.getGroupId(), new Callback<MemberDataRestResponse>() {
            @Override
            public void success(MemberDataRestResponse memberDataRestResponse, Response response) {
                progressDialog.dismiss();
                ArrayList<ResultMember> resultMember = memberDataRestResponse.getResultMembers();
                for (ResultMember member : resultMember) {
                    dbHandler.saveMemberData(Integer.toString(member.getId()), groupDetails.getGroupId(), member.getMember(), Boolean.toString(member.getIsAdmin()),
                            Integer.toString(member.getRole()), Integer.toString(member.getStatus()));
                }
                Toast.makeText(mContext, "Successfully get members data", Toast.LENGTH_LONG).show();

                loadMemberDetails();
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                GeneralError generalError = (GeneralError) error.getBodyAs(GeneralError.class);
                if (generalError != null) {
                    Toast.makeText(mContext, "" + generalError.getError().getMessage() + "\n"
                            + generalError.getError().getDetail(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }*/

    private void loadMemberDetails() {
        membersArrayList = dbHandler.getMemberData(groupDetails.getGroupId());
        mAdapter = new MemberRecyclerViewAdapter(mContext, membersArrayList);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        loadMemberDetails();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.group_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_info:
                EventBus.getDefault().postSticky(groupDetails);
                startActivity(new Intent(mContext, GroupInfoDetailsActivity.class));
                return true;
            case R.id.action_edit:
                EventBus.getDefault().postSticky(groupDetails);
                startActivity(new Intent(mContext, GroupUpdationActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
