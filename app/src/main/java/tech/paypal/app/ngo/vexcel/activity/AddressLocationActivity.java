package tech.paypal.app.ngo.vexcel.activity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

import tech.paypal.app.ngo.vexcel.R;
import tech.paypal.app.ngo.vexcel.util.StringUtil;


/**
 * Created by Ravikumar on 3/25/16.
 */
public class AddressLocationActivity extends AppCompatActivity implements GoogleMap.OnMapLongClickListener {
    public static final String PLACE_ID = "placeId";
    public static final String PLACE_NAME = "placeName";
    public static final String PLACE_ADDRESS = "placeAddress";
    public static final String PLACE_LATITUDE = "placeLatitude";
    public static final String PLACE_LONGITUDE = "placeLongitude";
    public static final String PLACE_CITY = "placeCity";
    public static final String PLACE_STATE = "placeState";
    public static final String PLACE_COUNTRY = "placeCountry";
    public static final String PLACE_ZIPCODE = "placeZipcode";
    private String city, state, country, zipcode;

    private GoogleMap mMap;
    private final float DEFAULT_ZOOM = 15, MIN_ZOOM = 10;
    private Marker selectedLocationMarker = null;
    private Context mContext;
    private Location lastKnownLocation;
    private static final String LOCATIONS_SLIDER_FRAGMENT_TAG = "locations_slider_fragment";

    private TextView address;
    private String placeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_location);
        mContext = this;

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle("Address");
        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            placeId = bundle.getString(PLACE_ID);
        }
        address = (TextView) findViewById(R.id.addressLocationField);
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.addressMap))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                lastKnownLocation = location;
                float zoom = mMap.getCameraPosition().zoom;
                mMap.setOnMyLocationChangeListener(null);
                if (zoom < MIN_ZOOM) {
                    zoom = DEFAULT_ZOOM;
                }
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(location.getLatitude(), location.getLongitude()),
                        zoom));
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(location.getLatitude(), location.getLongitude()));
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker());
                markerOptions.draggable(true);
                selectedLocationMarker = mMap.addMarker(markerOptions);
                readMarkerAddress(location);
            }
        });
        mMap.setOnMapLongClickListener(this);

        updateMapPadding();
        Location currentLocation = mMap.getMyLocation();
        if (currentLocation != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                    DEFAULT_ZOOM));
        }
    }

    private void updateMapPadding() {
        int l = 0;
        int t = 0;
        int r = 0;
        int b = 0;
        if (mMap == null) {
            return;
        }
        TypedValue val = new TypedValue();
        getTheme().resolveAttribute(android.R.attr.actionBarSize, val, true);
        t = TypedValue.complexToDimensionPixelSize(val.data,
                getResources().getDisplayMetrics());

        Fragment sliderFragment = getFragmentManager()
                .findFragmentByTag(LOCATIONS_SLIDER_FRAGMENT_TAG);
        if (sliderFragment != null && sliderFragment.isAdded()) {
            b = (int) getResources().getDimension(R.dimen.view_pager_height);
        }
        mMap.setPadding(l, t, r, b);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        if (selectedLocationMarker != null) {
            selectedLocationMarker.remove();
        }
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker());
        markerOptions.draggable(true);
        selectedLocationMarker = mMap.addMarker(markerOptions);
        LatLng currentLocation = markerOptions.getPosition();
        lastKnownLocation.setLatitude(currentLocation.latitude);
        lastKnownLocation.setLongitude(currentLocation.longitude);
        readMarkerAddress(lastKnownLocation);
    }

    private void readMarkerAddress(Location location) {
        try {
            Geocoder geo = new Geocoder(mContext.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.isEmpty()) {
                address.setText("Waiting for Location");
            } else {
                if (addresses.size() > 0) {
                    address.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
                    city = addresses.get(0).getLocality();
                    state = addresses.get(0).getAdminArea();
                    country = addresses.get(0).getCountryName();
                    zipcode = addresses.get(0).getPostalCode();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext, "We cannot identify your location", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_save:
                locationSuccess();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void locationSuccess() {
        Intent intent = new Intent();
        if (!StringUtil.isNullOrEmpty(placeId)) {
            intent.putExtra(PLACE_ID, placeId);
        }
        intent.putExtra(PLACE_ADDRESS, address.getText().toString());
        intent.putExtra(PLACE_LATITUDE, String.valueOf(lastKnownLocation.getLatitude()));
        intent.putExtra(PLACE_LONGITUDE, String.valueOf(lastKnownLocation.getLongitude()));
        intent.putExtra(PLACE_CITY, city);
        intent.putExtra(PLACE_STATE, state);
        intent.putExtra(PLACE_COUNTRY, country);
        intent.putExtra(PLACE_ZIPCODE, zipcode);
        setResult(RESULT_OK, intent);
        finish();
    }
}