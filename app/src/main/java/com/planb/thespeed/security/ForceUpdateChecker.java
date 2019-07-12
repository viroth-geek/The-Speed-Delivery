package com.planb.thespeed.security;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.planb.thespeed.model.Version;
import com.planb.thespeed.service.base.InvokeOnCompleteAsync;
import com.planb.thespeed.service.datahelper.datasource.online.VersionService;
import com.planb.thespeed.util.LoggerHelper;

import java.util.List;

/**
 * @author yeakleang.ay on 8/20/18.
 */

public class ForceUpdateChecker {

    private OnUpdateNeededListener onUpdateNeededListener;
    private Context context;

    public interface OnUpdateNeededListener {
        void onUpdateNeeded(String updateUrl);
    }

    public static Builder with(@NonNull Context context) {
        return new Builder(context);
    }

    ForceUpdateChecker(@NonNull Context context,
                       OnUpdateNeededListener onUpdateNeededListener) {
        this.context = context;
        this.onUpdateNeededListener = onUpdateNeededListener;
    }

    /**
     * check app version
     */
    public void check() {

        final String appVersion = getAppVersion(context);

        new VersionService(new InvokeOnCompleteAsync<List<Version>>() {
            @Override
            public void onComplete(List<Version> data) {
                if (data != null && !data.isEmpty()) {
                    Version version = data.get(0);
                    if (version.getUpdateRequired() == 1 && !TextUtils.equals(version.getCurrentVersion(), appVersion) && onUpdateNeededListener != null) {
                        onUpdateNeededListener.onUpdateNeeded(version.getStoreUrl());
                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        });

    }

    /**
     * get current app version
     * @param context Context
     * @return String
     */
    private String getAppVersion(Context context) {
        String result = "";

        try {
            result = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0)
                    .versionName;
            result = result.replaceAll("[a-zA-Z]|-", "");
        } catch (PackageManager.NameNotFoundException e) {
            LoggerHelper.showErrorLog("Error: ", e);
        }

        return result;
    }

    /**
     * builder class
     */
    public static class Builder {

        private Context context;
        private OnUpdateNeededListener onUpdateNeededListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder onUpdateNeeded(OnUpdateNeededListener onUpdateNeededListener) {
            this.onUpdateNeededListener = onUpdateNeededListener;
            return this;
        }

        public ForceUpdateChecker build() {
            return new ForceUpdateChecker(context, onUpdateNeededListener);
        }

        public ForceUpdateChecker check() {
            ForceUpdateChecker forceUpdateChecker = build();
            forceUpdateChecker.check();

            return forceUpdateChecker;
        }
    }
}