package com.planb.thespeed.service.notification;

import android.content.Context;

import com.planb.thespeed.model.Notification;
import com.planb.thespeed.server.DatabaseHelper;
import com.planb.thespeed.service.datahelper.datasource.offine.localnotification.FetchNotificationDAO;
import com.planb.thespeed.util.DateUtil;
import com.planb.thespeed.util.LoggerHelper;
import com.onesignal.OSNotification;
import com.onesignal.OneSignal;

import org.json.JSONObject;

/**
 * @author yeakleang.ay on 4/17/2018.
 */
public class AppNotificationReceivedHandler implements OneSignal.NotificationReceivedHandler {

    private Context mContext;

    private FetchNotificationDAO notificationDAO;

    public AppNotificationReceivedHandler(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void notificationReceived(OSNotification notification) {
        JSONObject data = notification.payload.additionalData;
        String notificationID = notification.payload.notificationID;
        String title = notification.payload.title;
        String body = notification.payload.body;
        String smallIcon = notification.payload.smallIcon;
        String largeIcon = notification.payload.largeIcon;
        String bigPicture = notification.payload.bigPicture;
        String smallIconAccentColor = notification.payload.smallIconAccentColor;
        String sound = notification.payload.sound;
        String ledColor = notification.payload.ledColor;
        int lockScreenVisibility = notification.payload.lockScreenVisibility;
        String groupKey = notification.payload.groupKey;
        String groupMessage = notification.payload.groupMessage;
        String fromProjectNumber = notification.payload.fromProjectNumber;
        String rawPayload = notification.payload.rawPayload;

        String receiveDate = DateUtil.getCurrent() + ", " + DateUtil.getCurrentTime();

        LoggerHelper.showDebugLog("Data: " + data.toString());

        String customKey;
        LoggerHelper.showDebugLog("Notification ID : " + notificationID + ", Title : " + title + ", Body : " + body);
        Notification mNotification = new Notification();
        mNotification.setTitle(title);
        mNotification.setBody(body);
        mNotification.setImageUrl(largeIcon);
        mNotification.setDate(receiveDate);
        mNotification.setData(data.toString());
        notificationDAO.insert(mNotification);
    }

    /**
     *
     */
    public void checkNotification() {
        if (checkNotificationDB(mContext)) {
            if (notificationDAO.getListNotification() != null) {
                if (notificationDAO.getListNotification().size() > 0) {
                    LoggerHelper.showDebugLog("DB - Notification Has In Database.");
                    return;
                }
            }
        }
    }

    /**
     * Check SQLite database
     *
     * @return
     */
    private boolean checkNotificationDB(Context context) {
        try {
            notificationDAO = new FetchNotificationDAO(DatabaseHelper.getInstance(mContext).getDatabase());
            LoggerHelper.showDebugLog("Notification Table Check OK!");
            return true;
        } catch (Exception e) {
            LoggerHelper.showErrorLog("Notification Table Error " + e.getMessage());
            return false;
        }
    }
}
