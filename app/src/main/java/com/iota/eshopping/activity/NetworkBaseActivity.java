package com.iota.eshopping.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.iota.eshopping.R;
import com.iota.eshopping.event.NetworkListener;
import com.iota.eshopping.util.NetworkChangeReceiver;

public class NetworkBaseActivity extends AppCompatActivity implements NetworkListener {

    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_base);
    }

    @Override
    public void onNetworkChanged() {
        if (isNetworkConnected()) {
            Toast.makeText(this, "NetWork connected", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(NetworkBaseActivity.this, BaseActivity.class));
            finish();

        } else {
            Toast.makeText(this, "NetWork disconnected!", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        networkChangeReceiver = new NetworkChangeReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkChangeReceiver);

    }

    protected boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

}
