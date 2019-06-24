package com.planb.thespeed.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author yeakleang.ay
 */
public class ActivityUtils {

    /**
     * start activity with bundle
     * @param context Context
     * @param tClass Class
     * @param isClearBackStack boolean
     * @param bundle Bundle
     * @param <T> title
     */
    public static <T> void startActivity(Context context, Class<T> tClass, boolean isClearBackStack, Bundle bundle) {
        Intent intent = new Intent(context, tClass);

        if (isClearBackStack) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }

        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * start activity with bundle
     * @param context Context
     * @param tClass Class
     * @param bundle Bundle
     * @param <T> title
     */
    public static <T> void startActivity(Context context, Bundle bundle, Class<T> tClass) {
        startActivity(context, tClass, false, bundle);
    }

    /**
     * start activity without bundle
     * @param context Context
     * @param tClass Class
     * @param isClearBackStack boolean
     * @param <T> Type
     */
    public static <T> void startActivity(Context context, Class<T> tClass, boolean isClearBackStack) {
        startActivity(context, tClass, isClearBackStack, null);
    }

    /**
     * start activity without clear stack
     * @param context Context
     * @param tClass Class
     * @param <T> title
     */
    public static <T> void startActivity(Context context, Class<T> tClass) {
        startActivity(context, tClass, false);
    }
}