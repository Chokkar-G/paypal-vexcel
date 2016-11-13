package tech.paypal.app.ngo.vexcel.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tech.paypal.app.ngo.vexcel.R;
import tech.paypal.app.ngo.vexcel.constantsmodel.IConstants;
import tech.paypal.app.ngo.vexcel.database.DatabaseHandler;
import tech.paypal.app.ngo.vexcel.model.group.GroupCreateModel;
import tech.paypal.app.ngo.vexcel.model.login.GeneralError;
import tech.paypal.app.ngo.vexcel.network.config.RestClient;
import tech.paypal.app.ngo.vexcel.network.responses.group.Result;

/**
 * Created by Ravikumar on 11/8/2016.
 */

public class GroupCreationActivity extends AppCompatActivity {
    private Context mContext;
    private EditText groupNameField, groupDescField, groupLatitudeField, groupLongitudeField, groupAddressField, groupCityField, groupStateField, groupCountryField, groupZipcodeField;
    private String groupName, description, latitude, longitude, address, city, state, country, zipcode, publicGroup;
    private GroupCreateModel groupCreateModel;
    private ProgressDialog progressDialog;
    private SwitchCompat switchCompat;
    private String tokenKey;
    private DatabaseHandler dbHandler;
    private Button locationMap;
    private boolean isPublic = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_create_activity);

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
        groupNameField = (EditText) findViewById(R.id.groupName);
        groupDescField = (EditText) findViewById(R.id.groupDesc);
        groupLatitudeField = (EditText) findViewById(R.id.latitude);
        groupLongitudeField = (EditText) findViewById(R.id.longitude);
        groupAddressField = (EditText) findViewById(R.id.address);
        groupCityField = (EditText) findViewById(R.id.groupCity);
        groupStateField = (EditText) findViewById(R.id.groupState);
        groupCountryField = (EditText) findViewById(R.id.country);
        groupZipcodeField = (EditText) findViewById(R.id.zipcode);
        locationMap = (Button) findViewById(R.id.locationMap);
        switchCompat = (SwitchCompat) findViewById(R.id.switchPublic);

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Log.d("switch_compat", isChecked + "");
                isPublic = isChecked;
            }
        });
        groupCreateModel = new GroupCreateModel();

        locationMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle(R.string.gpsProvider);  // GPS not found
                    builder.setMessage(R.string.enableGPS); // Want to enable?
                    builder.setPositiveButton(R.string.yesText, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    });
                    builder.setNegativeButton(R.string.noText, null);
                    builder.create().show();
                    return;
                } else {
                    Intent intent = new Intent(mContext, AddressLocationActivity.class);
                    startActivityForResult(intent, 1);
                }
            }
        });
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
                groupLatitudeField.setText(latitudeText);
                groupLongitudeField.setText(longitudeText);
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
                addGroup();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addGroup() {
        groupName = groupNameField.getText().toString();
        description = groupDescField.getText().toString();
        latitude = groupLatitudeField.getText().toString();
        longitude = groupLongitudeField.getText().toString();
        address = groupAddressField.getText().toString();
        city = groupCityField.getText().toString();
        state = groupStateField.getText().toString();
        country = groupCountryField.getText().toString();
        zipcode = groupZipcodeField.getText().toString();
        publicGroup = Boolean.toString(isPublic);

        latitude = "12.9416";
        longitude = "80.2362";
        if (groupName.isEmpty() || description.isEmpty() || latitude.isEmpty() || longitude.isEmpty() || address.isEmpty() ||
                city.isEmpty() || state.isEmpty() || country.isEmpty() || zipcode.isEmpty()) {
            Toast.makeText(mContext, "Please enter all the details", Toast.LENGTH_SHORT).show();
            return;
        }
        groupCreateModel.setName(groupName);
        groupCreateModel.setDescription(description);
        groupCreateModel.setLatitude(latitude);
        groupCreateModel.setLongitude(longitude);
        groupCreateModel.setAddress_line_1(address);
        groupCreateModel.setCity(city);
        groupCreateModel.setState(state);
        groupCreateModel.setCountry(country);
        groupCreateModel.setZip_code(zipcode);
        groupCreateModel.setIs_public(publicGroup);

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        RestClient.setupRestClient();
        RestClient.get().groupCreate(tokenKey, groupCreateModel, new Callback<Result>() {
            @Override
            public void success(Result result, Response response) {
                progressDialog.dismiss();
                if (result != null) {
                    dbHandler.saveGroupData(Integer.toString(result.getId()), result.getName(), result.getDescription(),
                            Integer.toString(result.getMembersCount()), Double.toString(result.getLatitude()),
                            Double.toString(result.getLongitude()), result.getAddressLine1(), result.getAddressLine2(),
                            result.getCity(), result.getState(), result.getCountry(), Integer.toString(result.getZipCode()),
                            Boolean.toString(result.getIsPublic()), Boolean.toString(result.getIsActive()));
                }
                startActivity(new Intent(mContext, GroupActivity.class));
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
