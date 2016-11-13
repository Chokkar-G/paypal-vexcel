package tech.paypal.app.ngo.vexcel.activity.laundry;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import tech.paypal.app.ngo.vexcel.model.login.GeneralError;
import tech.paypal.app.ngo.vexcel.model.profile.UserProfile;
import tech.paypal.app.ngo.vexcel.network.config.RestClient;
import tech.paypal.app.ngo.vexcel.network.responses.EmptyForgotResponse;

/**
 * Created by Chokkar on 11/4/2016.
 */

public class MainLaundryActivity extends AppCompatActivity {
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

        userProfile = databaseHandler.getUserData();

        profile = new ProfileDrawerItem().withEmail(userProfile.getFirstName() + " " + userProfile.getLastName()).
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

        CustomListView adapter = new CustomListView(MainLaundryActivity.this, title, subTitle);
        listView.setAdapter(adapter);
    }

    SectionDrawerItem sectionGroupDrawerItem = new SectionDrawerItem().withName("Users").withIdentifier(11).withDivider(false).withTextColor(Color.parseColor("#FF5722"));

    private void drawerSetup(Bundle savedInstanceState) {
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        sectionGroupDrawerItem,
                        new PrimaryDrawerItem().withName("Groups").withIcon(getResources().getDrawable(R.drawable.ic_supervisor_account_black_24dp)).withIdentifier(1).withSelectable(true),
                        new PrimaryDrawerItem().withName("User Info").withIcon(getResources().getDrawable(R.drawable.ic_person_black_24dp)).withIdentifier(2).withSelectable(true)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) {
                                intent = new Intent(mContext, GroupActivity.class);
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
}