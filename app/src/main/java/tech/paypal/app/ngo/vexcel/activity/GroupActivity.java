package tech.paypal.app.ngo.vexcel.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tech.paypal.app.ngo.vexcel.R;
import tech.paypal.app.ngo.vexcel.activity.resources.SpacesItemDecoration;
import tech.paypal.app.ngo.vexcel.adapter.GroupAlertOptionsMenu;
import tech.paypal.app.ngo.vexcel.adapter.GroupRecyclerViewAdapter;
import tech.paypal.app.ngo.vexcel.constantsmodel.IConstants;
import tech.paypal.app.ngo.vexcel.database.DatabaseHandler;
import tech.paypal.app.ngo.vexcel.model.group.Groups;
import tech.paypal.app.ngo.vexcel.model.login.GeneralError;
import tech.paypal.app.ngo.vexcel.network.config.RestClient;
import tech.paypal.app.ngo.vexcel.network.responses.EmptyForgotResponse;
import tech.paypal.app.ngo.vexcel.network.responses.GroupRestResponse;
import tech.paypal.app.ngo.vexcel.network.responses.MemberDataRestResponse;
import tech.paypal.app.ngo.vexcel.network.responses.group.Result;
import tech.paypal.app.ngo.vexcel.network.responses.member.ResultMember;

/**
 * Created by Ravikumar on 11/4/2016.
 */

public class GroupActivity extends AppCompatActivity {
    private Context mContext;
    private RecyclerView recyclerView;
    private DatabaseHandler dbHandler;
    private ProgressDialog progressDialog;
    private String tokenKey;
    private FloatingActionButton floatingActionButton;
    private GroupRecyclerViewAdapter mAdapter;
    private ArrayList<Groups> groupsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_activity);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle("Groups");
        }
        mContext = this;
        dbHandler = new DatabaseHandler(mContext);
        dbHandler.open();

        tokenKey = Prefs.getString(IConstants.TOKEN_KEY, null);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fabAddGroup);
        recyclerView = (RecyclerView) findViewById(R.id.groupRecycleView);
        recyclerView.addItemDecoration(new SpacesItemDecoration(10));
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, GroupCreationActivity.class));
            }
        });

        getGroupDetaitls();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getGroupDetaitls() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        RestClient.setupRestClient();
        RestClient.get().groupsList(tokenKey, new Callback<GroupRestResponse>() {
            @Override
            public void success(GroupRestResponse groupRestResponse, Response response) {
                progressDialog.dismiss();
                if (groupRestResponse != null) {
                    ArrayList<Result> results = groupRestResponse.getResults();
                    if (results != null) {
                        for (Result result : results) {
                            dbHandler.saveGroupData(Integer.toString(result.getId()), result.getName(), result.getDescription(),
                                    Integer.toString(result.getMembersCount()), Double.toString(result.getLatitude()),
                                    Double.toString(result.getLongitude()), result.getAddressLine1(), result.getAddressLine2(),
                                    result.getCity(), result.getState(), result.getCountry(), Integer.toString(result.getZipCode()),
                                    Boolean.toString(result.getIsPublic()), Boolean.toString(result.getIsActive()));
                        }
                    }
                }
                loadGroupDetails();
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
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        loadGroupDetails();
    }

    private void loadGroupDetails() {
        groupsArrayList = dbHandler.getGroupDataList();
        mAdapter = new GroupRecyclerViewAdapter(mContext, groupsArrayList);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnClickListener(new GroupRecyclerViewAdapter.OnClickListener() {
            @Override
            public void onClick(View view, final int viewPosition) {
                getMemberDetails(groupsArrayList.get(viewPosition));

            }
        });

        mAdapter.setOnLongClickListener(new GroupRecyclerViewAdapter.OnLongClickListener() {
            @Override
            public void onLongClick(View view, final int position) {
                String optionTitleList[] = {"Delete", "Update"};
                Integer optionsImageList[] = {R.drawable.ic_delete_black_24dp, R.drawable.ic_update_black_24dp};
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View convertView = inflater.inflate(R.layout.popup_listview, null);
                ListView listView = (ListView) convertView.findViewById(R.id.dialog_list);
                GroupAlertOptionsMenu groupAlertOptionsMenu = new GroupAlertOptionsMenu(mContext, optionTitleList, optionsImageList);
                listView.setAdapter(groupAlertOptionsMenu);
                TextView title = new TextView(mContext);
                title.setText(R.string.actionName);
                title.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                title.setPadding(10, 10, 10, 10);
                title.setGravity(Gravity.CENTER);
                title.setTextColor(getResources().getColor(R.color.white));
                title.setTextSize(20);
                alertDialogBuilder.setCustomTitle(title);
                alertDialogBuilder.setView(convertView);
                final AlertDialog alertDialog = alertDialogBuilder.show();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                        if (pos == 0) {
                            deleteGroupData(Integer.parseInt(groupsArrayList.get(position).getGroupId()), position);
                        } else if (pos == 1) {
                            EventBus.getDefault().postSticky(groupsArrayList.get(position));
                            startActivity(new Intent(mContext, GroupUpdationActivity.class));
                        }
                        alertDialog.dismiss();
                    }
                });
            }
        });
    }

    private void getMemberDetails(final Groups groups) {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        RestClient.setupRestClient();
        RestClient.get().membersList(tokenKey, groups.getGroupId(), new Callback<MemberDataRestResponse>() {
            @Override
            public void success(MemberDataRestResponse memberDataRestResponse, Response response) {
                progressDialog.dismiss();
                ArrayList<ResultMember> resultMember = memberDataRestResponse.getResultMembers();
                for (ResultMember member : resultMember) {
                    dbHandler.saveMemberData(Integer.toString(member.getId()), groups.getGroupId(), member.getMember(), Boolean.toString(member.getIsAdmin()),
                            Integer.toString(member.getRole()), Integer.toString(member.getStatus()));
                }
                EventBus.getDefault().postSticky(groups);
                startActivity(new Intent(mContext, GroupDetailsActivity.class));
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                GeneralError generalError = (GeneralError) error.getBodyAs(GeneralError.class);
                if (generalError != null) {
                    Toast.makeText(mContext, "" + generalError.getError().getMessage(), Toast.LENGTH_LONG).show();
                    wantMember(groups);
                }
            }
        });
    }

    private void wantMember(final Groups groups) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
        builder1.setMessage("You want to subscribe this group?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Subscribe",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        EventBus.getDefault().postSticky(groups);
                        Intent intent = new Intent(mContext, MemberCreationActivity.class);
                        intent.putExtra("subscribe", "subscribe");
                        startActivity(intent);
                    }
                });

        builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void deleteGroupData(final int groupId, final int position) {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        RestClient.setupRestClient();
        RestClient.get().groupDatasDelete(tokenKey, Integer.toString(groupId), new Callback<EmptyForgotResponse>() {
            @Override
            public void success(EmptyForgotResponse emptyForgotResponse, Response response) {
                progressDialog.dismiss();
                groupsArrayList.remove(position);
                dbHandler.deleteGroup(groupId);
                mAdapter.notifyDataSetChanged();
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
    }
}