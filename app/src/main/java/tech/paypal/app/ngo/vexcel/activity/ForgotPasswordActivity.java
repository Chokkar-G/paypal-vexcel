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
import tech.paypal.app.ngo.vexcel.model.forgotpassword.ForgotPassword;
import tech.paypal.app.ngo.vexcel.model.login.GeneralError;
import tech.paypal.app.ngo.vexcel.network.config.RestClient;
import tech.paypal.app.ngo.vexcel.network.responses.EmptyForgotResponse;
import tech.paypal.app.ngo.vexcel.permission.Utility;

/**
 * Created by Ravikumar on 11/3/2016.
 */

public class ForgotPasswordActivity extends AppCompatActivity {
    private TextView knowPasswordButton;
    private EditText forgotEmailField;
    private Context mContext;
    private Button resetButton;
    private ForgotPassword forgotPassword;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password_activity);
        mContext = this;

        forgotPassword = new ForgotPassword();
        forgotEmailField = (EditText) findViewById(R.id.forgotEmailField);
        resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = Utility.checkPermission(mContext);
                if (result) {
                    String email = forgotEmailField.getText().toString();
                    if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        forgotEmailField.setError("enter a valid email address");
                        return;
                    }
                    forgotPassword.setEmail(email);
                    progressDialog = new ProgressDialog(mContext);
                    progressDialog.setMessage("Logging in...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    RestClient.setupRestClient();
                    RestClient.get().resetUserLogin(forgotPassword, new Callback<EmptyForgotResponse>() {
                        @Override
                        public void success(EmptyForgotResponse emptyForgotResponse, Response response) {
                            progressDialog.dismiss();
                            Toast.makeText(mContext, "Success", Toast.LENGTH_LONG).show();
                            finish();
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
        });
        knowPasswordButton = (TextView) findViewById(R.id.know_password);
        knowPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
