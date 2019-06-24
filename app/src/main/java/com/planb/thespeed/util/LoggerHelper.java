package com.planb.thespeed.util;

import android.util.Log;

import com.planb.thespeed.constant.ApplicationConfiguration;
import com.planb.thespeed.constant.ConstantValue;

/**
 * Created by Chanrith on 2/21/2018.
 *
 * @author channarith.bong
 */

public class LoggerHelper {

    /**
     * Log for debug
     *
     * @param logMessage
     */
    public static void showDebugLog(String logMessage) {
        if (ApplicationConfiguration.DEVELOPER_MODE) {
            Log.d(ConstantValue.TAG, " >> " + logMessage);
        }
    }

    /**
     * Log Red Line for error
     *
     * @param logMessage
     */
    public static void showErrorLog(String logMessage) {
        showErrorLog(logMessage, null);
    }

    /**
     * @param logMessage
     * @param e
     */
    public static void showErrorLog(String logMessage, Throwable e) {
        if (ApplicationConfiguration.DEVELOPER_MODE) {
            Log.e(ConstantValue.TAG, " Error > " + logMessage);
            if (e != null) {
                e.printStackTrace();
            }
        }
    }
}
