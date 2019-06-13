package com.iota.eshopping.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import com.iota.eshopping.R;
import com.iota.eshopping.constant.ConstantValue;
import com.iota.eshopping.service.datahelper.datasource.online.FetchWebPage;
import com.iota.eshopping.util.ExceptionUtils;

/**
 * @author channarith.bong
 */
public class PrivacyPolicyActivity extends AppCompatActivity {

    private WebView webView;
    private String method;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        webView = findViewById(R.id.webview);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initInfo();

        if (getIntent().getStringExtra(ConstantValue.VERIFICATION_METHOD) != null) {
            method = getIntent().getStringExtra(ConstantValue.VERIFICATION_METHOD);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Check the information about Title and Body
     */
    private void initInfo() {
//        String title = getIntent().getStringExtra(ConstantValue.TITLE);
//        String body = getIntent().getStringExtra(ConstantValue.BODY);
        String key = getIntent().getStringExtra(ConstantValue.URL_KEY);

//        if (title != null) {
//            txt_title.setText(title);
//        }
//        if (body != null) {
//            //webView.loadUrl(body);
//        }
        if (key != null) {
            loadWebContent(key);
        }
    }

    /**
     *
     */
    private void loadWebContent(String key) {
        new FetchWebPage(key, new FetchWebPage.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(String content) {
                if (content != null) {
                    webView.loadData(content, "text/html", "UTF-8");
                }
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(PrivacyPolicyActivity.this, "Error: " + ExceptionUtils.translateExceptionMessage(e), Toast.LENGTH_SHORT).show();
            }
        });
    }
    
}
