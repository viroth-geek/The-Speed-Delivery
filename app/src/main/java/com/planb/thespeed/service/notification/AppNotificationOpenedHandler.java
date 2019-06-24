package com.planb.thespeed.service.notification;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.planb.thespeed.activity.BaseActivity;
import com.planb.thespeed.constant.ConstantValue;
import com.planb.thespeed.constant.entity.NotificationDataKey;
import com.planb.thespeed.model.OrderDetail;
import com.planb.thespeed.security.UserAccount;
import com.planb.thespeed.service.datahelper.datasource.online.FetchOrderDetails;
import com.planb.thespeed.util.LoggerHelper;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @author yeakleang.ay on 4/17/2018.
 */

public class AppNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {

    private Context mContext;

    public AppNotificationOpenedHandler(Context context) {
        this.mContext = context;
    }

    // This fires when a notification is opened by tapping on it.
    @Override
    public void notificationOpened(OSNotificationOpenResult result) {
        JSONObject data = result.notification.payload.additionalData;

        try {
            String incrementId = data.getString(NotificationDataKey.ORDER_INCREMENT_ID);
            if (incrementId != null) {
                UserAccount userAccount = new UserAccount(mContext);
                if (userAccount.getCustomer() != null) {
                    loadOrderDetail(userAccount.getCustomer().getId(), incrementId);
                }
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            boolean isNewVersionAvailable = data.getBoolean(NotificationDataKey.IS_NEW_VERSION_AVAILABLE);
            if (isNewVersionAvailable) {
                final String appPackageName = mContext.getPackageName(); // getPackageName() from Context or Activity object
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                } catch (android.content.ActivityNotFoundException e) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(mContext, BaseActivity.class);
        intent.putExtra(ConstantValue.NOTIFICATION, ConstantValue.NOTIFICATION);
        mContext.startActivity(intent);
    }

    /**
     * Load order detail
     * @param customerId customerId
     * @param incrementId incrementId
     */
    private void loadOrderDetail(Long customerId, String incrementId) {
        new FetchOrderDetails(customerId, incrementId, null, new FetchOrderDetails.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(List<OrderDetail> orderDetails) {
                bindView(orderDetails);
            }

            @Override
            public void onError(Throwable e) {
                LoggerHelper.showDebugLog("Error: " + e);
            }
        });
    }

    /**
     * bind data
     * @param orderDetails List of OrderDetail
     */
    private void bindView(List<OrderDetail> orderDetails) {
        if (orderDetails.size() > 0) {
            Intent intent = new Intent(mContext, BaseActivity.class);
            intent.putExtra(ConstantValue.ITEMS, orderDetails.get(0));
            intent.putExtra(ConstantValue.NOTIFICATION, ConstantValue.NOTIFICATION);
            mContext.startActivity(intent);
        }
    }
}
