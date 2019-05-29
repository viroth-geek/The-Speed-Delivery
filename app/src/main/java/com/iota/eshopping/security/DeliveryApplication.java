package com.iota.eshopping.security;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.iota.eshopping.server.DatabaseHelper;
import com.iota.eshopping.service.notification.AppNotificationOpenedHandler;
import com.iota.eshopping.service.notification.AppNotificationReceivedHandler;
import com.iota.eshopping.util.NetworkConnectHelper;
import com.onesignal.OneSignal;

/**
 * Created by channarith.bong on 1/18/18.
 *
 * @author channarith.bong
 */

public class DeliveryApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initStorage();
//        checkConnection();
        // Logging set to help debug issues, remove before releasing your app.
        // OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.WARN);

        AppNotificationReceivedHandler receivedHandler = new AppNotificationReceivedHandler(this);
        receivedHandler.checkNotification();

        OneSignal.startInit(this)
                .setNotificationReceivedHandler(receivedHandler)
                .setNotificationOpenedHandler(new AppNotificationOpenedHandler(this))
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .init();
    }

    /**
     * Init SQLite data
     */
    private void initStorage() {
        SQLiteDatabase database = DatabaseHelper.getInstance(getApplicationContext()).getDatabase();
        if (database.isOpen()) {
            database.close();
        }
    }

    /**
     *
     */
    public void checkConnection() {
//        boolean isConnection = NetworkConnectHelper.getInstance().isConnectionOnline(getApplicationContext());
        String message = NetworkConnectHelper.getInstance().getMessage();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
