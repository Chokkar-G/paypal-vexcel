package tech.paypal.app.ngo.vexcel.activity.customer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tech.paypal.app.ngo.vexcel.R;
import tech.paypal.app.ngo.vexcel.activity.AddressLocationActivity;
import tech.paypal.app.ngo.vexcel.constantsmodel.IConstants;
import tech.paypal.app.ngo.vexcel.database.DatabaseHandler;
import tech.paypal.app.ngo.vexcel.model.customers.Customer;
import tech.paypal.app.ngo.vexcel.model.login.GeneralError;
import tech.paypal.app.ngo.vexcel.network.config.RestClient;

/**
 * Created by Ravikumar on 11/8/2016.
 */

public class CustomerCreationActivity extends AppCompatActivity {
    private Context mContext;
    private EditText customerName, customerEmailId, customerPhNo, customerAddress, groupAddressField, groupCityField, groupStateField, groupCountryField, groupZipcodeField;
    private String cusValue, cusMail, cusMobile, cusAdd, address, city, state, country, zipcode, publicGroup;
    private Customer customer;
    private ProgressDialog progressDialog;
    private String tokenKey;
    private DatabaseHandler dbHandler;
    private boolean isPublic = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_create_activity);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle("Add New Customer");
        }
        mContext = this;
        dbHandler = new DatabaseHandler(mContext);
        dbHandler.open();

        tokenKey = Prefs.getString(IConstants.TOKEN_KEY, null);
        customerName = (EditText) findViewById(R.id.name_customer);
        customerEmailId = (EditText) findViewById(R.id.email_id_customer);
        customerPhNo = (EditText) findViewById(R.id.phonenumber_cus);
        customerAddress = (EditText) findViewById(R.id.address_cus);

        customer = new Customer();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String addressText = data.getStringExtra(AddressLocationActivity.PLACE_ADDRESS);
                String latitudeText = data.getStringExtra(AddressLocationActivity.PLACE_LATITUDE);
                String longitudeText = data.getStringExtra(AddressLocationActivity.PLACE_LONGITUDE);
                String cityText = data.getStringExtra(AddressLocationActivity.PLACE_CITY);
                String stateText = data.getStringExtra(AddressLocationActivity.PLACE_STATE);
                String countryText = data.getStringExtra(AddressLocationActivity.PLACE_COUNTRY);
                String zipcodeText = data.getStringExtra(AddressLocationActivity.PLACE_ZIPCODE);
                if (addressText == null)
                    addressText = "";
                if (cityText == null)
                    cityText = "";
                if (stateText == null)
                    stateText = "";
                if (countryText == null)
                    countryText = "";
                if (zipcodeText == null)
                    zipcodeText = "";

                latitudeText = latitudeText.substring(0, 8);
                longitudeText = latitudeText.substring(0, 8);
                customerPhNo.setText(latitudeText);
                customerAddress.setText(longitudeText);
                groupAddressField.setText(addressText);
                groupCityField.setText(cityText);
                groupStateField.setText(stateText);
                groupCountryField.setText(countryText);
                groupZipcodeField.setText(zipcodeText);
            }
        }
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
                addCustomer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addCustomer() {

        cusValue = customerName.getText().toString();
        cusMail = customerEmailId.getText().toString();
        cusMobile = customerPhNo.getText().toString();
        cusAdd = customerAddress.getText().toString();


        if (cusValue.isEmpty() || cusMail.isEmpty() || cusMobile.isEmpty() || cusAdd.isEmpty() ) {
            Toast.makeText(mContext, "Please enter all the details", Toast.LENGTH_SHORT).show();
            return;
        }
        customer.setName(cusValue);
        customer.setEmailId(cusMail);
        customer.setPhoneNumber(cusMobile);
        customer.setAddress(cusAdd);

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        RestClient.setupRestClient();
        RestClient.get().createCustomer(customer, new Callback<Customer>() {

            @Override
            public void success(Customer customer, Response response) {
                Toast.makeText(mContext, "Customer Created Successfully" , Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
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
