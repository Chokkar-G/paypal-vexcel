package tech.paypal.app.ngo.vexcel.activity.laundry;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tech.paypal.app.ngo.vexcel.R;
import tech.paypal.app.ngo.vexcel.activity.GroupActivity;
import tech.paypal.app.ngo.vexcel.activity.LoginActivity;
import tech.paypal.app.ngo.vexcel.activity.UserProfileActivity;
import tech.paypal.app.ngo.vexcel.adapter.CustomListView;
import tech.paypal.app.ngo.vexcel.constantsmodel.IConstants;
import tech.paypal.app.ngo.vexcel.database.DatabaseHandler;
import tech.paypal.app.ngo.vexcel.model.customers.Customer;
import tech.paypal.app.ngo.vexcel.model.login.GeneralError;
import tech.paypal.app.ngo.vexcel.model.profile.UserProfile;
import tech.paypal.app.ngo.vexcel.network.config.RestClient;
import tech.paypal.app.ngo.vexcel.network.responses.EmptyForgotResponse;

/**
 * Created by Ravikumar on 11/4/2016.
 */

public class LaundryMainActivity extends AppCompatActivity {
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private Toolbar toolbar;
    private IProfile profile;
    private Context mContext;
    private String userNameInfo;
    private ProgressDialog progressDialog;
    private String tokenKey;
    private DatabaseHandler databaseHandler;
    private ListView listView;
    private UserProfile userProfile;
    String[] title = new String[]{"Gym", "Swimming", "Theatres"};
    String[] subTitle = new String[]{"Gymnastic Place", "Both place", "Entertainment Place"};
    private static final String TAG = "LaundryMainActivity";
    private RecyclerView recyclerView;
    private CustomerRecyclerViewAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        databaseHandler = new DatabaseHandler(mContext);
        databaseHandler.open();
        userNameInfo = Prefs.getString(IConstants.USER_LOGGED, null);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView = (ListView) findViewById(R.id.resourceListview);
        tokenKey = Prefs.getString(IConstants.TOKEN_KEY, null);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.groupRecycleView);

        userProfile = databaseHandler.getUserData();

        profile = new ProfileDrawerItem().withEmail("").
                withIcon(R.drawable.profile).withIdentifier(1).withSetSelected(true)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) {
                                intent = new Intent(mContext, UserProfileActivity.class);
                            }
                            if (intent != null) {
                                startActivity(intent);
                            }
                        }
                        return false;
                    }
                });

        buildHeader(false, savedInstanceState);
        drawerSetup(savedInstanceState);

        getCustomerDetails();

    }

    SectionDrawerItem sectionGroupDrawerItem = new SectionDrawerItem().withName("Users").withIdentifier(11).withDivider(false).withTextColor(Color.parseColor("#FF5722"));

    private void drawerSetup(Bundle savedInstanceState) {
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        sectionGroupDrawerItem,
                        new PrimaryDrawerItem().withName("Customers").withIcon(getResources().getDrawable(R.drawable.ic_supervisor_account_black_24dp)).withIdentifier(1).withSelectable(true),
                        new PrimaryDrawerItem().withName("User Info").withIcon(getResources().getDrawable(R.drawable.ic_person_black_24dp)).withIdentifier(2).withSelectable(true)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) {
                                intent = new Intent(mContext, CustomerListActivity.class);
                            } else if (drawerItem.getIdentifier() == 2) {
                                intent = new Intent(mContext, UserProfileActivity.class);
                            }
                            if (intent != null) {
                                startActivity(intent);
                            }
                        }
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();
    }

    private void buildHeader(boolean compact, Bundle savedInstanceState) {
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withSelectionListEnabledForSingleProfile(false)
                .withHeaderBackground(R.drawable.header)
                .withCompactStyle(compact)
                .addProfiles(profile)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logoutUser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logoutUser() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Logging out...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        RestClient.setupRestClient();
        RestClient.get().logout(tokenKey, "Dummy", new Callback<EmptyForgotResponse>() {
            @Override
            public void success(EmptyForgotResponse emptyForgotResponse, Response response) {
                progressDialog.dismiss();
                logoutIntent();
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                GeneralError generalError = (GeneralError) error.getBodyAs(GeneralError.class);
                if (generalError != null) {
                    Toast.makeText(mContext, "" + generalError.getError().getMessage() + "\n"
                            + generalError.getError().getDetail(), Toast.LENGTH_LONG).show();
                }
                logoutIntent();
            }
        });
    }

    private void logoutIntent() {
        Prefs.putString(IConstants.TOKEN_KEY, null);
        databaseHandler.clearTables();
        Intent selectionIntent = new Intent(mContext, LoginActivity.class);
        selectionIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(selectionIntent);
        finish();
    }

    public void getCustomerDetails() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        RestClient.setupRestClient();
        RestClient.get().getCustomersList(new Callback<List<Customer>>() {


            @Override
            public void success(List<Customer> customers, Response response) {
                Log.i(TAG , "Customer List" +customers);
                loadCustomerDetails(customers);
                progressDialog.dismiss();


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

    private void loadCustomerDetails(List<Customer> customersArrayList) {
//        groupsArrayList = dbHandler.getGroupDataList();
        Log.i(TAG , "cUSTOMER lIST" + customersArrayList);
        mAdapter = new CustomerRecyclerViewAdapter(mContext, customersArrayList);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnClickListener(new CustomerRecyclerViewAdapter.OnClickListener() {
            @Override
            public void onClick(View view, final int viewPosition) {
//                getMemberDetails(customersArrayList.get(viewPosition));

            }
        });

//        mAdapter.setOnLongClickListener(new GroupRecyclerViewAdapter.OnLongClickListener() {
//            @Override
//            public void onLongClick(View view, final int position) {
//                String optionTitleList[] = {"Delete", "Update"};
//                Integer optionsImageList[] = {R.drawable.ic_delete_black_24dp, R.drawable.ic_update_black_24dp};
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
//                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View convertView = inflater.inflate(R.layout.popup_listview, null);
//                ListView listView = (ListView) convertView.findViewById(R.id.dialog_list);
//                GroupAlertOptionsMenu groupAlertOptionsMenu = new GroupAlertOptionsMenu(mContext, optionTitleList, optionsImageList);
//                listView.setAdapter(groupAlertOptionsMenu);
//                TextView title = new TextView(mContext);
//                title.setText(R.string.actionName);
//                title.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                title.setPadding(10, 10, 10, 10);
//                title.setGravity(Gravity.CENTER);
//                title.setTextColor(getResources().getColor(R.color.white));
//                title.setTextSize(20);
//                alertDialogBuilder.setCustomTitle(title);
//                alertDialogBuilder.setView(convertView);
//                final AlertDialog alertDialog = alertDialogBuilder.show();
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
//                        if (pos == 0) {
//                            deleteGroupData(Integer.parseInt(groupsArrayList.get(position).getGroupId()), position);
//                        } else if (pos == 1) {
//                            EventBus.getDefault().postSticky(groupsArrayList.get(position));
//                            startActivity(new Intent(mContext, GroupUpdationActivity.class));
//                        }
//                        alertDialog.dismiss();
//                    }
//                });
//            }
//        });
    }
}