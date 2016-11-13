package tech.paypal.app.ngo.vexcel.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tech.paypal.app.ngo.vexcel.R;
import tech.paypal.app.ngo.vexcel.activity.laundry.AddingNewLaundry;
import tech.paypal.app.ngo.vexcel.constantsmodel.IConstants;
import tech.paypal.app.ngo.vexcel.database.DatabaseHandler;
import tech.paypal.app.ngo.vexcel.model.login.GeneralError;
import tech.paypal.app.ngo.vexcel.model.login.Login;
import tech.paypal.app.ngo.vexcel.network.config.RestClient;
import tech.paypal.app.ngo.vexcel.network.responses.LoginRestResponse;
import tech.paypal.app.ngo.vexcel.network.responses.UpdateProfileRestResponse;

public class LoginActivity extends AppCompatActivity {
    private TextView registrationButton, forgotPasswordButton;
    private EditText userEmail, userPassword;
    private Button loginClick;
    private Context mContext;
    String email, password;
    private Login login;
    private ProgressDialog progressDialog;
    private static final String USERNAME = "tester88";
    private static final String PASSWORD = "password";
    private String tokenKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mContext = this;
        userEmail = (EditText) findViewById(R.id.userEmail);
        userPassword = (EditText) findViewById(R.id.userPassword);
        registrationButton = (TextView) findViewById(R.id.registrationButton);
        forgotPasswordButton = (TextView) findViewById(R.id.forgotLink);
        login = new Login();
        databaseHandler = new DatabaseHandler(mContext);
        databaseHandler.open();
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        loginClick = (Button) findViewById(R.id.loginButton);

        loginClick.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,AddingNewLaundry.class);
                startActivity(intent);
             }
        });

//        loginClick.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                boolean result = Utility.checkPermission(mContext);
//                if (result) {
//                    if (!validate()) {
//                        onLoginFailed();
//                        return;
//                    }
//
//                    login.setUsername(email);
//                    login.setPassword(password);
//                    progressDialog = new ProgressDialog(mContext);
//                    doLogin();
//                }
//            }
//        });
//        tokenKey = Prefs.getString(IConstants.TOKEN_KEY, null);
//        if (tokenKey != null && !tokenKey.isEmpty()) {
//            nextIntent();
//        }
    }

    private void doLogin() {
        progressDialog.setMessage("Logging in...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        RestClient.setupRestClient();
        RestClient.get().login(login, new Callback<LoginRestResponse>() {
            @Override
            public void success(LoginRestResponse loginRestResponse, Response response) {
                progressDialog.dismiss();
                tokenKey = "Token " + loginRestResponse.getToken();
                Prefs.putString(IConstants.TOKEN_KEY, tokenKey);
                Prefs.putString(IConstants.USER_LOGGED, email);
                Toast.makeText(mContext, "Success = " + tokenKey, Toast.LENGTH_LONG).show();
                getProfile();
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

    private void nextIntent() {
        Intent selectionIntent = new Intent(mContext, MainActivity.class);
        startActivity(selectionIntent);
        finish();
    }

    private DatabaseHandler databaseHandler;

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
                nextIntent();
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

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;

        userEmail.setText(USERNAME);
        userPassword.setText(PASSWORD);
        email = userEmail.getText().toString();
        password = userPassword.getText().toString();

        if (email.isEmpty()) {// || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            userEmail.setError("enter a valid email address");
            valid = false;
        } else {
            userEmail.setError(null);
        }

        if (password.isEmpty()) {
            userPassword.setError("Password cannot be empty");
            valid = false;
        } else {
            userPassword.setError(null);
        }

        return valid;
    }
}