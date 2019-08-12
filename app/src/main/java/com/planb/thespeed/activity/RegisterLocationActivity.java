package com.planb.thespeed.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.model.EncodedPolyline;
import com.planb.thespeed.R;
import com.planb.thespeed.constant.ApplicationConfiguration;
import com.planb.thespeed.constant.ConstantValue;
import com.planb.thespeed.model.DriverLocation;
import com.planb.thespeed.model.OrderDetail;
import com.planb.thespeed.model.direction.Direction;
import com.planb.thespeed.model.direction.Leg;
import com.planb.thespeed.model.direction.Route;
import com.planb.thespeed.model.direction.Step;
import com.planb.thespeed.model.enumeration.MapViewType;
import com.planb.thespeed.model.enumeration.OrderStatusType;
import com.planb.thespeed.model.form.FormForGetDirection;
import com.planb.thespeed.security.FeeCalculation;
import com.planb.thespeed.service.base.InvokeOnCompleteAsync;
import com.planb.thespeed.service.datahelper.datasource.online.FetchDriverLastLocation;
import com.planb.thespeed.service.datahelper.datasource.online.FetchDriverLocationHistoryByOrderId;
import com.planb.thespeed.service.datahelper.datasource.online.GetDirection;
import com.planb.thespeed.util.LoggerHelper;
import com.planb.thespeed.util.preference.LocationPreference;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author channarith.bong
 */
public class RegisterLocationActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    protected static final String TAG = ConstantValue.TAG;
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;


    private final static String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key";
    private final static String LOCATION_KEY = "location-key";
    private final static String LAST_UPDATED_TIME_STRING_KEY = "last-updated-time-string-key";

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int REQUEST_CHECK_SETTINGS = 10;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 2;
    private static final int MAP_ZOOM_LEVEL_I = 15;
    private static final int MAP_ZOOM_LEVEL_II = 17;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;
    private Boolean mRequestingLocationUpdates;
    private String mLastUpdateTime;
    private List<Address> addressList;

    private GoogleMap mMap;
    private GoogleMap.OnCameraIdleListener onCameraIdleListener;
    private Location location;
    private Button btn_confirm_location;
    private TextView txt_location_detection;
    private TextView txt_location_detection_name;
    private Toolbar toolbar;
    private ImageButton btnMyLocation;
    private ImageView imgMapMarker;
    private View container_float_loading;
    private View container_button;

    private MapViewType mapViewType;
    private LatLng storeLatLng;
    private MenuItem searchMenuItem;
    private String storeName;
    private OrderDetail orderDetail;
    private Timer refreshDriverLocationTimer;
    private MarkerOptions driverMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().hasExtra(ConstantValue.LATITUDE) && getIntent().hasExtra(ConstantValue.LONGITUDE)) {
            double lat = (double) getIntent().getSerializableExtra(ConstantValue.LATITUDE);
            double lon = (double) getIntent().getSerializableExtra(ConstantValue.LONGITUDE);
            storeLatLng = new LatLng(lat, lon);
        }

        setContentView(R.layout.activity_register_location);

        toolbar = findViewById(R.id.toolbar);
        btn_confirm_location = findViewById(R.id.btn_confirm_location);
        txt_location_detection = findViewById(R.id.txt_location_detection);
        txt_location_detection_name = findViewById(R.id.txt_location_detection_name);
        btnMyLocation = findViewById(R.id.fab);
        container_float_loading = findViewById(R.id.container_float_loading);
        container_button = findViewById(R.id.container_button);

        imgMapMarker = findViewById(R.id.img_map_marker);

        mRequestingLocationUpdates = false;
        mLastUpdateTime = "";

        if (getIntent().hasExtra(ConstantValue.ORDER_DETAIL)) {
            orderDetail = (OrderDetail) getIntent().getSerializableExtra(ConstantValue.ORDER_DETAIL);

            if (orderDetail.getStatus().equalsIgnoreCase(OrderStatusType.PROCESSING.toString().toLowerCase())) {
                driverMarker = new MarkerOptions();
                refreshDriverLocationTimer = new Timer();
                refreshDriverLocationTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        fetchDriverLastLocation(orderDetail.getId());
                    }
                }, 0, 5000);
            } else if (orderDetail.getStatus().equalsIgnoreCase(OrderStatusType.COMPLETE.toString().toLowerCase())) {
                fetchDriverLocationHistory(orderDetail.getId());
            }

        }
        initMap(savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                LinearLayout info = new LinearLayout(RegisterLocationActivity.this);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(RegisterLocationActivity.this);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(RegisterLocationActivity.this);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });

        mMap.setOnCameraIdleListener(onCameraIdleListener);
        if (location != null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, MAP_ZOOM_LEVEL_I));
            storeLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            drawRoute(latLng);
        } else {
            LatLng latLng = new LatLng(ConstantValue.DEFAULT_LAT, ConstantValue.DEFAULT_LNT);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, MAP_ZOOM_LEVEL_I));
            drawRoute(latLng);
        }

        mMap.setOnMapClickListener(latLng -> {
            if (location == null) {
                location = new Location(ConstantValue.LOCATION);
            }
            location.setLatitude(latLng.latitude);
            location.setLongitude(latLng.longitude);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLng.latitude, latLng.longitude), MAP_ZOOM_LEVEL_II));
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        searchMenuItem = menu.findItem(R.id.action_search);
        if (mapViewType == MapViewType.VIEW_ONLY) {
            searchMenuItem.setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (LocationPreference.getLocation(this) == null) {
                Toast.makeText(this, "Unable to load nearby store. Please confirm your location.", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
            }
            finish();
        } else if (item.getItemId() == R.id.action_search) {
            searchLocation();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (LocationPreference.getLocation(this) == null) {
            Toast.makeText(this, "Unable to load nearby store. Please confirm your location.", Toast.LENGTH_SHORT).show();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setButtonsEnabledState();
                    startLocationUpdates();
                } else {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        mRequestingLocationUpdates = false;
                        Toast.makeText(RegisterLocationActivity.this, "To enable the function of this application please enable location permission of the application from the setting screen of the terminal.", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                }
                break;

            case PLACE_AUTOCOMPLETE_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Place place = PlaceAutocomplete.getPlace(this, data);
                    if (place != null) {
                        if (place.getName() != null || place.getLatLng() != null) {
                            Location location = new Location(place.getName().toString());
                            location.setLongitude(place.getLatLng().longitude);
                            location.setLatitude(place.getLatLng().latitude);
                            mapAnimator(location);
                            new MapLocationTask().doInBackground(place);
                        }
                    }
                } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                    Status status = PlaceAutocomplete.getStatus(this, data);
                    LoggerHelper.showErrorLog(status.getStatusMessage());
//                    Toast.makeText(this, "Error: " + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
                } else if (resultCode == RESULT_CANCELED) {
                    LoggerHelper.showDebugLog("Place search has been canceled.");
                }
                break;
            case ConstantValue.HOME_CALLING_CODE:
                finish();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
        isPlayServicesAvailable(this);
        checkMapViewType();
        // Within {@code onPause()}, we pause location updates, but leave the
        // connection to GoogleApiClient intact.  Here, we resume receiving
        // location updates if the user has requested them.

        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop location updates to save battery, but don't disconnect the GoogleApiClient object.
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        stopLocationUpdates();
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (refreshDriverLocationTimer != null) {
            refreshDriverLocationTimer.cancel();
        }
        super.onDestroy();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LoggerHelper.showDebugLog("onConnected");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (mCurrentLocation == null) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
            updateUI();
        }

        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
//        updateUI();
    }

    @Override
    public void onConnectionSuspended(int i) {
        // The connection to Google Play service was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        LoggerHelper.showDebugLog("Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        LoggerHelper.showDebugLog("Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    /**
     *
     */
    private void checkMapViewType() {
        if (getIntent().hasExtra(ConstantValue.MAP_VIEW_TYPE)) {
            mapViewType = (MapViewType) getIntent().getExtras().get(ConstantValue.MAP_VIEW_TYPE);
            if (mapViewType != null) {
                switch (mapViewType) {
                    case VIEW_ONLY:
                        container_button.setVisibility(View.GONE);
                        getSupportActionBar().setTitle("Order Location");
                        imgMapMarker.setVisibility(View.GONE);
                        btnMyLocation.setVisibility(View.GONE);
                        new MapTask().execute(new LatLng(location.getLatitude(), location.getLongitude()));
                        break;
                    case SEARCHABLE:
                        container_button.setVisibility(View.VISIBLE);
                        btnMyLocation.setVisibility(View.VISIBLE);
                        if (searchMenuItem != null) {
                            searchMenuItem.setVisible(true);
                        }
                        break;
                }
            }
        }
    }

    /**
     * Search by google location service
     */
    private void searchLocation() {
        try {
            // Search only in Cambodia 'KH',
            // Search result for 'Address, Establishment'
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setCountry("KH")
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ESTABLISHMENT)
                    .build();

            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .setFilter(typeFilter)
                    .build(this);

            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
            //PLACE_AUTOCOMPLETE_REQUEST_CODE is integer for request code
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            LoggerHelper.showErrorLog("Google Play :" + e.getMessage());
            Toast.makeText(this, "Cannot search location", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Prepare toolbar
     */
    private void configureToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    /**
     * Get location from other activity.
     *
     * @return location
     */
    private Location getLocationFromIntent() {

        com.planb.thespeed.model.Address address;

        if (getIntent().hasExtra(ConstantValue.LOCATION)) {
            location = (Location) getIntent().getExtras().get(ConstantValue.LOCATION);
        }

        if (getIntent().hasExtra(ConstantValue.ADDRESS)) {
            address = (com.planb.thespeed.model.Address) getIntent().getExtras().get(ConstantValue.ADDRESS);
        }
        else {
            address = LocationPreference.getLocation(this);
        }

        if (location == null) {
            if (mCurrentLocation != null) {
                LoggerHelper.showDebugLog("Current Location: " + mCurrentLocation.toString());
                location = new Location(ConstantValue.LOCATION);
                location.setLatitude(mCurrentLocation.getLatitude());
                location.setLongitude(mCurrentLocation.getLongitude());
            } else if (address != null) {
                location = new Location(ConstantValue.LOCATION);
                location.setLatitude(address.getLatitude());
                location.setLongitude(address.getLongitude());
            }
        }

        return location;
    }


    /**
     * Setting moving camera in map
     */
    private void configureCameraIdle() {
        onCameraIdleListener = () -> {
            if (mapViewType != MapViewType.VIEW_ONLY) {
                checkLocationAddress(null);
                final LatLng latLng = mMap.getCameraPosition().target;
                location = new Location(ConstantValue.LOCATION);
                location.setLatitude(latLng.latitude);
                location.setLongitude(latLng.longitude);
                new MapTask().execute(latLng);
            }
        };
    }

    /**
     * init google map v2
     *
     * @param savedInstanceState Bundle
     */
    private void initMap(Bundle savedInstanceState) {
        // Set Location
        location = getLocationFromIntent();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        configureToolbar();
        configureCameraIdle();

        updateValuesFromBundle(savedInstanceState);
        buildGoogleApiClient();

        btn_confirm_location.setOnClickListener( v -> {
            Intent returnIntent = new Intent(String.valueOf(ConstantValue.MAP_DATA_TAG_CODE));
            if (addressList != null) {
                if (!addressList.isEmpty()) {
                    if (addressList.size() > 0) {
                        returnIntent.putExtra(ConstantValue.ADDRESS, addressList.get(0));
                        Address address = addressList.get(0);
                        com.planb.thespeed.model.Address add = new com.planb.thespeed.model.Address();
                        com.planb.thespeed.model.AddressByStreetString addressByStreetString = new com.planb.thespeed.model.AddressByStreetString();

                        add.setLatitude(address.getLatitude());
                        add.setLongitude(address.getLongitude());
                        add.setStreet(new ArrayList<>(Arrays.asList(address.getFeatureName(),address.getCountryName(),address.getCountryName())));
                        add.setAddressLine(address.getAddressLine(0));
                        add.setCountryCode(address.getCountryCode());
                        add.setCity(address.getAdminArea());
                        add.setPostcode(address.getPostalCode());
                        add.setCountryName(address.getCountryName());

                        addressByStreetString.setLatitude(address.getLatitude());
                        addressByStreetString.setLongitude(address.getLongitude());
                        addressByStreetString.setAddressLine(address.getAddressLine(0));
                        addressByStreetString.setCountryCode(address.getCountryCode());
                        addressByStreetString.setCity(address.getAdminArea());
                        addressByStreetString.setPostcode(address.getPostalCode());
                        addressByStreetString.setCountryName(address.getCountryName());

                        Intent resultIntent = new Intent();
                        resultIntent.putExtra(ConstantValue.ADDRESS, add);
                        resultIntent.putExtra(ConstantValue.ADDRESS_BY_STREET_STRING, addressByStreetString);

                        if (getIntent().hasExtra(ConstantValue.EDIT_ADDRESS)) {
                            setResult(ConstantValue.GET_EDITED_ADDRESS, resultIntent);
                            finish();
                            return;
                        }

                        if (getIntent().hasExtra(ConstantValue.SAVE_ADDRESS)) {
                            Intent intent = new Intent(RegisterLocationActivity.this, AddAddressActivity.class);
                            intent.putExtra(ConstantValue.ADDRESS, add);
                            intent.putExtra(ConstantValue.ADDRESS_BY_STREET_STRING, addressByStreetString);
                            RegisterLocationActivity.this.startActivityForResult(intent, ConstantValue.HOME_CALLING_CODE);
                            return;
                        }

                        if (storeLatLng != null) {
                            double distance = FeeCalculation.meterDistanceBetweenPoints(storeLatLng, new LatLng(address.getLatitude(), address.getLongitude()));
                            if (distance >= ApplicationConfiguration.MAX_DISTANT_ALLOW) {
                                Toast.makeText(RegisterLocationActivity.this, "Distance is far than " + ApplicationConfiguration.MAX_DISTANT_ALLOW + "KM. Delivery is not available. Please choose another location.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } else {
                            Log.d("initMap:", "initMap: 1" + add.toString());
                            LocationPreference.saveLocation(RegisterLocationActivity.this, add);
                        }

                        if (getIntent().hasExtra(ConstantValue.FROM_BASKET)) {
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();
                            return;
                        }
                        Log.d("initMap:", "initMap: 2" + add.toString());
                        LocationPreference.saveLocation(RegisterLocationActivity.this, add);

                    }
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            } else {
                LoggerHelper.showErrorLog("Empty Address!");
            }
        });

        btnMyLocation.setOnClickListener(v -> {
            checkLocationPermission();
            mapAnimator();
        });
    }

    /**
     *
     */
    private void mapAnimator() {
        if (mMap != null && mCurrentLocation != null) {
            mMap.animateCamera(CameraUpdateFactory.
                    newLatLngZoom(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()), MAP_ZOOM_LEVEL_II));
            configureCameraIdle();
        }
    }

    /**
     *
     */
    private void mapAnimator(Location location) {
        if (mMap != null && location != null) {
            mMap.animateCamera(CameraUpdateFactory.
                    newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), MAP_ZOOM_LEVEL_II));
            configureCameraIdle();
        }
    }

    /**
     * Update value from Bundle
     *
     * @param savedInstanceState Bundle
     */
    private void updateValuesFromBundle(Bundle savedInstanceState) {
        LoggerHelper.showDebugLog("Updating values from bundle");
        if (savedInstanceState != null) {
            if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        REQUESTING_LOCATION_UPDATES_KEY);
                setButtonsEnabledState();
            }

            if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
                mCurrentLocation = savedInstanceState.getParcelable(LOCATION_KEY);
            }
            if (savedInstanceState.keySet().contains(LAST_UPDATED_TIME_STRING_KEY)) {
                mLastUpdateTime = savedInstanceState.getString(LAST_UPDATED_TIME_STRING_KEY);
            }
            updateUI();
        }
    }

    /**
     * Build Google API for app
     */
    protected synchronized void buildGoogleApiClient() {
        LoggerHelper.showDebugLog("Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    /**
     * Request the location
     */
    protected void createLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Start up location update
     */
    private void startLocationUpdates() {
        LoggerHelper.showDebugLog("startLocationUpdates");
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(locationSettingsResult -> {
            final Status status = locationSettingsResult.getStatus();

            switch (status.getStatusCode()) {
                case LocationSettingsStatusCodes.SUCCESS:
                    if (ContextCompat.checkSelfPermission(RegisterLocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, RegisterLocationActivity.this);
                        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    }
                    break;
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    try {
                        status.startResolutionForResult(RegisterLocationActivity.this, REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException e) {
                    }
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    break;
            }
        });
    }

    /**
     *
     */
    private void setButtonsEnabledState() {
        btnMyLocation.setVisibility(View.VISIBLE);
    }

    /**
     * Change view for update info
     */
    private void updateUI() {
        if (location != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), MAP_ZOOM_LEVEL_I));
            return;
        }
        if (mCurrentLocation == null) {
            return;
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (mMap != null) {
                    mMap.setMyLocationEnabled(false);
                    mMap.getUiSettings().setMyLocationButtonEnabled(false);
                    mMap.getUiSettings().setMapToolbarEnabled(false);
                }
                return;
            }
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()), MAP_ZOOM_LEVEL_I));
    }

    /**
     * @param locationDetail String
     */
    private void checkLocationAddress(String locationDetail) {
        txt_location_detection.setVisibility(locationDetail == null ? View.GONE : View.VISIBLE);

        if (locationDetail == null) {
            container_float_loading.setVisibility(View.VISIBLE);
        } else {
            container_float_loading.setVisibility(View.GONE);
            txt_location_detection.setText(locationDetail);
        }
    }


    /**
     * Stop update location
     */
    protected void stopLocationUpdates() {
        LoggerHelper.showDebugLog("stopLocationUpdates");
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    /**
     * Check is google play in mobile
     *
     * @param context Context
     */
    public static void isPlayServicesAvailable(Context context) {
        // Google Play Service APK
        int resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            GoogleApiAvailability.getInstance().getErrorDialog((Activity) context, resultCode, 2).show();
        }
    }

    /**
     * @param savedInstanceState Bundle
     */
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, mRequestingLocationUpdates);
        savedInstanceState.putParcelable(LOCATION_KEY, mCurrentLocation);
        savedInstanceState.putString(LAST_UPDATED_TIME_STRING_KEY, mLastUpdateTime);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Fetch driver location history
     * @param orderId Long
     */
    private void fetchDriverLocationHistory(Long orderId) {
        new FetchDriverLocationHistoryByOrderId(orderId, new InvokeOnCompleteAsync<List<DriverLocation>>() {
            @Override
            public void onComplete(List<DriverLocation> driverLocations) {
                List<LatLng> paths = new ArrayList<>();
                for (int index = 0; index < driverLocations.size(); index++) {
                    DriverLocation driverLocation = driverLocations.get(index);
                    paths.add(new LatLng(driverLocation.getLatitude(), driverLocation.getLongitude()));

                    if (index == 0) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(driverLocation.getLatitude(), driverLocation.getLongitude())).title("Start Location").snippet("At: " + driverLocation.getCreateAt()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    }
                    else if (index == driverLocations.size() - 1) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(driverLocation.getLatitude(), driverLocation.getLongitude())).title("End Location").snippet("At: " + driverLocation.getCreateAt()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    }

                }
                drawDriverLocationRoute(paths);
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(RegisterLocationActivity.this, "Cannot get driver location", Toast.LENGTH_SHORT).show();
                LoggerHelper.showErrorLog("Error: ", e);
            }
        });
    }

    /**
     * Fetch driver last location
     * @param orderId Long
     */
    private void fetchDriverLastLocation(Long orderId) {
        new FetchDriverLastLocation(orderId, new InvokeOnCompleteAsync<List<DriverLocation>>() {
            @Override
            public void onComplete(List<DriverLocation> driverLocations) {
                if (driverLocations.size() > 0) {
                    DriverLocation driverLocation = driverLocations.get(0);
                    LoggerHelper.showDebugLog(driverLocation.toString());
                    driverMarker.position(new LatLng(driverLocation.getLatitude(), driverLocation.getLongitude()));
                    driverMarker.title("Your driver");
                    driverMarker.snippet("Last seen: " + driverLocation.getCreateAt());
                    driverMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    mMap.addMarker(driverMarker);
                }
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(RegisterLocationActivity.this, "Cannot draw driver route.", Toast.LENGTH_SHORT).show();
                refreshDriverLocationTimer.cancel();
                LoggerHelper.showErrorLog("Error: ", e);
            }
        });
    }

    /**
     * Draw driver route
     * @param paths List<LatLng>
     */
    private void drawDriverLocationRoute(List<LatLng> paths) {
        PolylineOptions polylineOptions = new PolylineOptions().addAll(paths).color(Color.CYAN).width(10);
        mMap.addPolyline(polylineOptions);
    }

    /**
     * Run in Background Threat for search location name
     */
    @SuppressLint("StaticFieldLeak")
    class MapTask extends AsyncTask<LatLng, Integer, List<Address>> {

        String placeName;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            checkLocationAddress(null);
            txt_location_detection_name.setVisibility(View.GONE);
        }

        @Override
        protected List<Address> doInBackground(LatLng... latLngs) {
            try {
                LatLng latLng = latLngs[0];
                Geocoder geocoder = new Geocoder(RegisterLocationActivity.this);
                addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                return addressList;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Address> addressList) {
            super.onPostExecute(addressList);
            if (addressList != null && addressList.size() > 0) {
                String locality = addressList.get(0).getAddressLine(0);
                placeName = addressList.get(0).getSubLocality();
                if (!locality.isEmpty()) {
                    checkLocationAddress(locality);
                }
                if (placeName != null) {
                    txt_location_detection_name.setVisibility(View.VISIBLE);
                    txt_location_detection_name.setText(placeName);
                }
            } else {
                checkLocationAddress("Cannot get location!");
            }
        }
    }

    /**
     * Run in Background Threat for search location name
     */
    @SuppressLint("StaticFieldLeak")
    class MapLocationTask extends AsyncTask<Place, Integer, List<Address>> {

        String placeName;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            txt_location_detection_name.setVisibility(View.GONE);
            checkLocationAddress(null);
        }

        @Override
        protected List<Address> doInBackground(Place... places) {
            try {
                Place place = places[0];
                placeName = place.getName().toString();
                Geocoder geocoder = new Geocoder(RegisterLocationActivity.this);
                addressList = geocoder.getFromLocationName(place.getName().toString(), 1);
                return addressList;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Address> addressList) {
            super.onPostExecute(addressList);
            if (addressList != null && addressList.size() > 0) {
                String locality = addressList.get(0).getAddressLine(0);
                placeName = addressList.get(0).getSubLocality();
                if (!locality.isEmpty()) {
                    checkLocationAddress(locality);
                }
                if (placeName != null) {
                    txt_location_detection_name.setVisibility(View.VISIBLE);
                    txt_location_detection_name.setText(placeName);
                }
            } else {
                checkLocationAddress("Cannot get location!");
            }
        }
    }

    /**
     * Prepare for get direction
     * @param source LatLng
     * @param destination LatLng
     * @return FormForGetDirection
     */
    private FormForGetDirection prepareForGetDirection(LatLng source, LatLng destination) {
        FormForGetDirection formForGetDirection = new FormForGetDirection();
        formForGetDirection.setSourceLatitude(source.latitude);
        formForGetDirection.setSourceLongitude(source.longitude);
        formForGetDirection.setDestinationLatitude(destination.latitude);
        formForGetDirection.setDestinationLongitude(destination.longitude);
        return  formForGetDirection;
    }

    /**
     * Draw path on google map
     *
     * @param destination LatLng
     */
    private void drawRoute(final LatLng destination) {

        if (storeLatLng == null) {
            return;
        }

        if (orderDetail == null) {
            if (getIntent().hasExtra(ConstantValue.STORE_NAME)) {
                this.storeName = getIntent().getStringExtra(ConstantValue.STORE_NAME);
            } else {
                this.storeName = "Store Here";
            }
        } else {
            String storeNames = orderDetail.getStoreName();
            if (storeNames != null) {
                String[] strings = storeNames.split("\n");
                if (strings.length > 1) {
                    storeName = strings[1];
                }
                else {
                    storeName = storeNames;
                }
            } else {
                this.storeName = "Store Here";
            }
        }

        new GetDirection(prepareForGetDirection(storeLatLng, destination), new InvokeOnCompleteAsync<List<Direction>>() {
            @Override
            public void onComplete(List<Direction> directions) {
                List<LatLng> paths = new ArrayList<>();
                for (Direction direction : directions) {
                    if (direction.getRoutes() != null && direction.getRoutes().size() > 0) {
                        for (Route route : direction.getRoutes()) {
                            if (route.getLegs() != null) {
                                for (Leg leg : route.getLegs()) {
                                    mMap.addMarker(new MarkerOptions().position(storeLatLng).title(storeName == null || storeName.isEmpty() ? "Store Here" : storeName).snippet(leg.getStartAddress()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                                    mMap.addMarker(new MarkerOptions().position(destination).title("Order Location").snippet(leg.getEndAddress() + "\nDistance from store: " + leg.getDistance().getText() + " - " + leg.getDuration().getText()));
                                    if (leg.getSteps() != null) {
                                        for (Step step : leg.getSteps()) {
                                            EncodedPolyline polyline = new EncodedPolyline(step.getPolyline().getPoints());
                                            List<com.google.maps.model.LatLng> coordinate = polyline.decodePath();
                                            for (com.google.maps.model.LatLng latLng : coordinate) {
                                                paths.add(new LatLng(latLng.lat, latLng.lng));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (paths.size() > 0) {
                    PolylineOptions polylineOptions = new PolylineOptions().addAll(paths).color(Color.CYAN).width(10);
                    mMap.addPolyline(polylineOptions);
                }
            }

            @Override
            public void onError(Throwable e) {
                LoggerHelper.showErrorLog("Error: ", e);
            }
        });
    }

    /**
     * check location permission
     */
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(this)
                        .setTitle("Location Permission")
                        .setMessage("Need Permission")
                        .setPositiveButton("OK", (dialogInterface, i) -> {
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(RegisterLocationActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        }
    }
}