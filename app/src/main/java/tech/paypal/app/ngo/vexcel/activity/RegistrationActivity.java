package tech.paypal.app.ngo.vexcel.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tech.paypal.app.ngo.vexcel.R;
import tech.paypal.app.ngo.vexcel.model.errorhandler.CommonBaseError;
import tech.paypal.app.ngo.vexcel.model.registration.Registration;
import tech.paypal.app.ngo.vexcel.network.config.RestClient;
import tech.paypal.app.ngo.vexcel.network.responses.RegisterResponse;

/**
 * Created by Ravikumar on 11/3/2016.
 */

public class RegistrationActivity extends AppCompatActivity {
    private Context mContext;
    private EditText firstNameField, lastNameField, emailField, userNameField, passwordField;
    private Button registerButton;
    private TextView alreadyRegisterButton;
    String firstName, lastName, username, email, password;
    private Registration registration;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        mContext = this;
        registration = new Registration();
        firstNameField = (EditText) findViewById(R.id.registerFirstnameField);
        lastNameField = (EditText) findViewById(R.id.registerLastnameField);
        emailField = (EditText) findViewById(R.id.registerEmailField);
        userNameField = (EditText) findViewById(R.id.registerUsernameField);
        passwordField = (EditText) findViewById(R.id.registerPasswordField);
        registerButton = (Button) findViewById(R.id.registerButton);
        alreadyRegisterButton = (TextView) findViewById(R.id.registerAlreadyButton);
        alreadyRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validate()) {
                    Toast.makeText(getBaseContext(), "Registration failed", Toast.LENGTH_LONG).show();
                    return;
                }

                registration.setFirst_name(firstName);
                registration.setLast_name(lastName);
                registration.setEmail(email);
                registration.setUsername(username);
                registration.setPassword(password);
                progressDialog = new ProgressDialog(mContext);
                doRegistration();
            }
        });
    }

    private void doRegistration() {
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        RestClient.setupRestClient();
        RestClient.get().register(registration, new Callback<RegisterResponse>() {
            @Override
            public void success(RegisterResponse registerResponse, Response response) {
                progressDialog.dismiss();
                Toast.makeText(mContext, "Registered successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                CommonBaseError generalError = (CommonBaseError) error.getBodyAs(CommonBaseError.class);
                if (generalError != null) {
                    Toast.makeText(mContext, "" + generalError.getError().getMessage() + "\n"
                            + generalError.getError().getDetails().getUsername(), Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public boolean validate() {
        boolean valid = true;

        firstName = firstNameField.getText().toString();
        lastName = lastNameField.getText().toString();
        username = userNameField.getText().toString();
        email = emailField.getText().toString();
        password = passwordField.getText().toString();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailField.setError("enter a valid email address");
            valid = false;
        } else {
            emailField.setError(null);
        }

        if (password.isEmpty()) {
            passwordField.setError("Password cannot be empty");
            valid = false;
        } else {
            passwordField.setError(null);
        }

        if (lastName.isEmpty()) {
            lastNameField.setError("LastName cannot be empty");
            valid = false;
        } else {
            lastNameField.setError(null);
        }

        if (firstName.isEmpty()) {
            firstNameField.setError("FirstName cannot be empty");
            valid = false;
        } else {
            firstNameField.setError(null);
        }

        if (username.isEmpty()) {
            userNameField.setError("Username cannot be empty");
            valid = false;
        } else {
            userNameField.setError(null);
        }

        return valid;
    }
}
