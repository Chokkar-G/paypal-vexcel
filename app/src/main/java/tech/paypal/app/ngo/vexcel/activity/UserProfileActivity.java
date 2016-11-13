package tech.paypal.app.ngo.vexcel.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tech.paypal.app.ngo.vexcel.R;
import tech.paypal.app.ngo.vexcel.constantsmodel.IConstants;
import tech.paypal.app.ngo.vexcel.database.DatabaseHandler;
import tech.paypal.app.ngo.vexcel.model.login.GeneralError;
import tech.paypal.app.ngo.vexcel.model.profile.ProfileConfig;
import tech.paypal.app.ngo.vexcel.model.profile.UserProfile;
import tech.paypal.app.ngo.vexcel.network.config.RestClient;
import tech.paypal.app.ngo.vexcel.network.responses.UpdateProfileRestResponse;

/**
 * Created by Ravikumar on 11/4/2016.
 */

public class UserProfileActivity extends AppCompatActivity {
    private Context mContext;
    private ProgressDialog progressDialog;
    private String tokenKey;
    private TextView firstNameField, lastNameField, emailIdField, userNameField, lastLoginField;
    private EditText firstNameEdit, lastNameEdit;
    private DatabaseHandler databaseHandler;
    private FloatingActionButton floatingActionButton;
    private UserProfile userProfile;
    private boolean isUpdateProfile = false;
    private ProfileConfig profileConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_activity);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle("User Profile");
        }
        mContext = this;
        tokenKey = Prefs.getString(IConstants.TOKEN_KEY, null);
        databaseHandler = new DatabaseHandler(mContext);
        databaseHandler.open();

        firstNameField = (TextView) findViewById(R.id.firstNameProfile);
        lastNameField = (TextView) findViewById(R.id.lastNameProfile);
        emailIdField = (TextView) findViewById(R.id.emailIdProfile);
        userNameField = (TextView) findViewById(R.id.userNameProfile);
        lastLoginField = (TextView) findViewById(R.id.lastLoginProfile);

        firstNameEdit = (EditText) findViewById(R.id.firstNameProfileEdit);
        lastNameEdit = (EditText) findViewById(R.id.lastNameProfileEdit);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fabUpdateUser);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isUpdateProfile) {
                    if (userProfile != null) {
                        floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_black_24dp));

                        firstNameField.setVisibility(View.GONE);
                        lastNameField.setVisibility(View.GONE);

                        firstNameEdit.setVisibility(View.VISIBLE);
                        lastNameEdit.setVisibility(View.VISIBLE);

                        firstNameEdit.setText(userProfile.getFirstName());
                        lastNameEdit.setText(userProfile.getLastName());
                        isUpdateProfile = true;
                    }
                } else {
                    floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_mode_edit_white_24dp));
                    profileConfig = new ProfileConfig();
                    String userFirstName = firstNameEdit.getText().toString();
                    String userLastName = lastNameEdit.getText().toString();
                    String userUserName = userProfile.getUserName();

                    if (userFirstName.isEmpty() || userLastName.isEmpty()) {
                        Toast.makeText(mContext, "Please enter all the details", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    profileConfig.setFirst_name(userFirstName);
                    profileConfig.setLast_name(userLastName);
                    profileConfig.setUsername(userUserName);
                    progressDialog = new ProgressDialog(mContext);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    RestClient.setupRestClient();
                    RestClient.get().userUpdateProfile(tokenKey, profileConfig, new Callback<UpdateProfileRestResponse>() {
                        @Override
                        public void success(UpdateProfileRestResponse updateProfileRestResponse, Response response) {
                            progressDialog.dismiss();
                            Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
                            databaseHandler.updateUserData(updateProfileRestResponse.getFirstName(), updateProfileRestResponse.getLastName(),
                                    updateProfileRestResponse.getEmailId());
                            userProfile = databaseHandler.getUserData();
                            updateProfile(userProfile);

                            firstNameField.setVisibility(View.VISIBLE);
                            lastNameField.setVisibility(View.VISIBLE);

                            firstNameEdit.setVisibility(View.GONE);
                            lastNameEdit.setVisibility(View.GONE);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            progressDialog.dismiss();

                        }
                    });

                }
            }
        });
        //getProfile();
        userProfile = databaseHandler.getUserData();
        updateProfile(userProfile);
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

    public void getProfile() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        RestClient.setupRestClient();
        RestClient.get().userProfile(tokenKey, new Callback<UpdateProfileRestResponse>() {
            @Override
            public void success(UpdateProfileRestResponse updateProfileRestResponse, Response response) {
                progressDialog.dismiss();
                databaseHandler.saveUserData(updateProfileRestResponse.getFirstName(), updateProfileRestResponse.getLastName(),
                        updateProfileRestResponse.getUserName(), updateProfileRestResponse.getLastLogin(), updateProfileRestResponse.getEmailId());
                userProfile = databaseHandler.getUserData();
                updateProfile(userProfile);
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

    private void updateProfile(UserProfile userProfile) {
        firstNameField.setText(userProfile.getFirstName());
        lastNameField.setText(userProfile.getLastName());
        emailIdField.setText(userProfile.getEmail());
        userNameField.setText(userProfile.getUserName());
        lastLoginField.setText(userProfile.getLastLogin());
    }
}
