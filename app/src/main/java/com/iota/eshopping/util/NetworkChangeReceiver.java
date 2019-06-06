package com.iota.eshopping.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.iota.eshopping.event.NetworkListener;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private NetworkListener networkListener;

    public NetworkChangeReceiver() {
    }

    public NetworkChangeReceiver(NetworkListener listener) {
        networkListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "Network Change", Toast.LENGTH_SHORT).show();
        try {
            networkListener.onNetworkChanged();
        } catch (Exception e) {
        }
    }
}
