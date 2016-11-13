package tech.paypal.app.ngo.vexcel.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tech.paypal.app.ngo.vexcel.R;
import tech.paypal.app.ngo.vexcel.constantsmodel.IConstants;
import tech.paypal.app.ngo.vexcel.database.DatabaseHandler;
import tech.paypal.app.ngo.vexcel.model.group.Groups;
import tech.paypal.app.ngo.vexcel.model.login.GeneralError;
import tech.paypal.app.ngo.vexcel.model.member.MemberCreateData;
import tech.paypal.app.ngo.vexcel.model.profile.UserProfile;
import tech.paypal.app.ngo.vexcel.network.config.RestClient;
import tech.paypal.app.ngo.vexcel.network.responses.member.ResultMember;


/**
 * Created by Ravikumar on 11/9/2016.
 */

public class MemberCreationActivity extends AppCompatActivity {
    private Context mContext;
    private EditText memberField;
    private Spinner rolesField;
    private SwitchCompat switchCompat;
    private DatabaseHandler dbHandler;
    private ProgressDialog progressDialog;
    private ArrayList<String> rolesCategories = new ArrayList<String>();
    private int rolePosition = 0;
    private int statusPosition = 0;
    private boolean isAdmin;
    private MemberCreateData memberCreateData;
    private String tokenKey;
    private Groups groupDetails;
    private String subscribeMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_create_layout);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle("Member");
        }
        mContext = this;
        groupDetails = EventBus.getDefault().removeStickyEvent(Groups.class);
        tokenKey = Prefs.getString(IConstants.TOKEN_KEY, null);
        dbHandler = new DatabaseHandler(mContext);
        dbHandler.open();

        memberField = (EditText) findViewById(R.id.memberField);
        rolesField = (Spinner) findViewById(R.id.rolesField);
        switchCompat = (SwitchCompat) findViewById( R.id.switchAdmin);
        rolesCategories.add("Director");
        rolesCategories.add("Secretary");
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Log.d("switch_compat", isChecked + "");
                isAdmin = isChecked;
            }
        });
        memberCreateData = new MemberCreateData();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            subscribeMember = extras.getString("subscribe");
            if (subscribeMember.equals("subscribe")) {
                UserProfile userProfile = dbHandler.getUserData();
                memberField.setText(userProfile.getUserName());
                rolesField.setEnabled(false);
                switchCompat.setEnabled(false);
                isAdmin = false;
                statusPosition = 0;
            } else {
                rolesField.setEnabled(true);
                switchCompat.setEnabled(true);
            }
        } else {
            rolesField.setEnabled(true);
            switchCompat.setEnabled(true);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rolesCategories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        rolesField.setAdapter(dataAdapter);

        rolesField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                rolePosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.group_create_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_add:
                addMember();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addMember() {
        String userMember = memberField.getText().toString();
        if (userMember.isEmpty()) {
            Toast.makeText(mContext, "Please enter member", Toast.LENGTH_SHORT).show();
            return;
        }
        if (subscribeMember != null) {
            statusPosition = 0;
        } else
            statusPosition = 1;

        memberCreateData.setMember(userMember);
        memberCreateData.setIs_admin(Boolean.toString(isAdmin));
        memberCreateData.setRole(Integer.toString(rolePosition));
        memberCreateData.setStatus(Integer.toString(statusPosition));
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        RestClient.setupRestClient();
        RestClient.get().membersCreate(tokenKey, groupDetails.getGroupId(), memberCreateData, new Callback<ResultMember>() {
            @Override
            public void success(ResultMember resultMember, Response response) {
                progressDialog.dismiss();
                Toast.makeText(mContext, "Success", Toast.LENGTH_LONG).show();
                dbHandler.saveMemberData(Integer.toString(resultMember.getId()), groupDetails.getGroupId(), resultMember.getMember(),
                        Boolean.toString(resultMember.getIsAdmin()), Integer.toString(resultMember.getRole()), Integer.toString(resultMember.getStatus()));
                if (subscribeMember != null) {
                    finish();
                } else {
                    startActivity(new Intent(mContext, GroupDetailsActivity.class));
                }
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
