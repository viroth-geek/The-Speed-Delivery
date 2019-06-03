package com.iota.eshopping.service.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.iota.eshopping.constant.ConstantValue;
import com.iota.eshopping.util.LoggerHelper;

import org.greenrobot.eventbus.EventBus;

/**
 * @author yeakleang.ay on 7/10/18.
 */

public class LocationService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        LoggerHelper.showDebugLog("onCreate");
        buildGoogleApiClient();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LoggerHelper.showDebugLog("onDestroy");
        stopLocationUpdate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LoggerHelper.showDebugLog("onStartCommand");

        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        } else {
            startLocationUpdate();
        }

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        LoggerHelper.showDebugLog("Location Changed: " + location);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LoggerHelper.showDebugLog("onConnected: " + bundle);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            postLocation(null);
            return;
        }

        startLocationUpdate();

        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(task -> {
                    Location location = task.getResult();
                    postLocation(location);
                    if (location != null) {
                        LoggerHelper.showDebugLog("Latitude: " + location.getLatitude());
                        LoggerHelper.showDebugLog("Longitude: " + location.getLongitude());
                        stopLocationUpdate();
                    }
                })
                .addOnFailureListener(error -> {
                    postLocation(null);
                    LoggerHelper.showErrorLog(error.getMessage());
                });

    }

    /**
     * post location
     * @param location Location
     */
    private void postLocation(Location location) {
        LoggerHelper.showDebugLog("postLocation: " + location);
        Intent intent = new Intent();
        if (location != null) {
            intent.putExtra(ConstantValue.LONGITUDE, location.getLongitude());
            intent.putExtra(ConstantValue.LATITUDE, location.getLatitude());
        } else {
            intent.putExtra(ConstantValue.LONGITUDE, 0d);
            intent.putExtra(ConstantValue.LATITUDE, 0d);
        }
        EventBus.getDefault().post(intent);
        stopLocationUpdate();
    }

    @Override
    public void onConnectionSuspended(int i) {
        LoggerHelper.showErrorLog("onConnectionSuspended: " + i);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        LoggerHelper.showErrorLog("onConnectionFailed: " + connectionResult.toString());
    }

    @SuppressLint("RestrictedApi")
    private void initLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    /**
     * Start update location
     */
    private void startLocationUpdate() {
        initLocationRequest();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            postLocation(null);
            LoggerHelper.showErrorLog("Permission not granted.");
            return;
        }
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(task -> {
                    Location location = task.getResult();
                    postLocation(location);
                    if (location != null) {
                        LoggerHelper.showDebugLog("Latitude: " + location.getLatitude());
                        LoggerHelper.showDebugLog("Longitude: " + location.getLongitude());
                    }
                })
                .addOnFailureListener(error -> {
                    postLocation(null);
                    LoggerHelper.showErrorLog(error.getMessage());
                });
    }

    /**
     * Stop update location
     */
    private void stopLocationUpdate() {
        if (mGoogleApiClient.isConnected()){
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    /**
     * buildGoogleApiClient
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }
}

