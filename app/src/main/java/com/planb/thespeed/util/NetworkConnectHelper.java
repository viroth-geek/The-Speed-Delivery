package com.planb.thespeed.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by channarith.bong on 3/1/18.
 *
 * @author channarith.bong
 */
public class NetworkConnectHelper {

    private static NetworkConnectHelper instance;

    private String message = "";

    /**
     *
     */
    public static NetworkConnectHelper getInstance() {
        if (instance == null) {
            instance = new NetworkConnectHelper();
        }
        return instance;
    }

    /**
     * @param context
     * @return
     */
    public boolean isConnectionOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm != null ? cm.getActiveNetworkInfo() : null;
        boolean isConnect = false;

        if (activeNetwork == null) {
            message = "Network is offline.";
        } else {
            switch (activeNetwork.getState()) {
                case CONNECTED:
                    switch (activeNetwork.getType()) {
                        case ConnectivityManager.TYPE_WIFI:
                            message = "Network connection by using WIFI";
                            break;
                        case ConnectivityManager.TYPE_MOBILE:
                            message = "Network connection by using Mobile Data";
                            break;
                        case ConnectivityManager.TYPE_VPN:
                            message = "Network connection by using VPN";
                            break;
                    }
                    isConnect = true;
                    break;
                case CONNECTING:
                    message = "Network is connecting";
                    isConnect = true;
                    break;
                default:
                    message = "Network is offline.";

            }
        }
        LoggerHelper.showDebugLog(message);
        return isConnect;
    }

    /**
     * @return
     */
    public String getMessage() {
        return message;
    }
}
