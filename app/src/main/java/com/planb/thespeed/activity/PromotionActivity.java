package com.planb.thespeed.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.planb.thespeed.R;
import com.planb.thespeed.constant.ApplicationConfiguration;
import com.planb.thespeed.service.datahelper.datasource.online.FetchWebPage;
import com.planb.thespeed.util.LoggerHelper;

/**
 * @author channarith.bong
 */
public class PromotionActivity extends AppCompatActivity {

    private WebView webView;
    private LinearLayout defaultLayout;
    private LinearLayout webViewLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);

        Toolbar toolbar = findViewById(R.id.toolbar);
        webView = findViewById(R.id.webview);

        defaultLayout = findViewById(R.id.default_promotion_view);
        webViewLayout = findViewById(R.id.web_promotion_view);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initInfo();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     *
     */
    private void initInfo() {
        loadWebContent(ApplicationConfiguration.PAGE_SPECIAL_PROMOTION);
    }

    /**
     *
     */
    private void loadWebContent(String key) {
        new FetchWebPage(key, new FetchWebPage.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(String content) {
                if (content != null && !content.isEmpty()) {
                    webViewLayout.setVisibility(View.VISIBLE);
                    defaultLayout.setVisibility(View.GONE);
                    webView.loadData(content, "text/html", "UTF-8");
                }
                else {
                    webViewLayout.setVisibility(View.GONE);
                    defaultLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Throwable e) {
//                Toast.makeText(PromotionActivity.this, "Error" + ExceptionUtils.translateExceptionMessage(e), Toast.LENGTH_SHORT).show();
                LoggerHelper.showErrorLog("Error Promotion Activity: ", e);
                webViewLayout.setVisibility(View.GONE);
                defaultLayout.setVisibility(View.VISIBLE);
            }
        });
    }
}
