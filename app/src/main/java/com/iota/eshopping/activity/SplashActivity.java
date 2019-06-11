package com.iota.eshopping.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.iota.eshopping.R;
import com.iota.eshopping.constant.ConstantValue;
import com.iota.eshopping.util.NetworkConnectHelper;
import com.iota.eshopping.util.preference.LocationPreference;

/**
 * @author channarith.bong
 */
public class SplashActivity extends AppCompatActivity {

    protected boolean active = true;
    protected int splashTime = 2500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        LocationPreference.clear(this);

        Thread splashTread = new Thread() {

            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (active && (waited < splashTime)) {
                        sleep(100);
                        if (active) {
                            waited += 100;
                        }
                    }
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    finish();
                    if (active) {
                        boolean isConnect = NetworkConnectHelper.getInstance().isConnectionOnline(getApplicationContext());
                        if (isConnect) {
                            Intent intent = new Intent(SplashActivity.this, BaseActivity.class);
                            intent.putExtra(ConstantValue.TAG, NetworkConnectHelper.getInstance().getMessage());
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(SplashActivity.this, AppErrorHandlerActivity.class);
                            intent.putExtra(ConstantValue.TAG, NetworkConnectHelper.getInstance().getMessage());
                            startActivity(intent);
                        }
                    }
                    interrupt();
                }
            }
        };
        splashTread.start();
    }
}
