package com.iota.eshopping.fragment.page;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.iota.eshopping.activity.PrivacyPolicyActivity;
import com.iota.eshopping.constant.ApplicationConfiguration;
import com.iota.eshopping.constant.ConstantValue;
import com.iota.eshopping.R;
import com.iota.eshopping.service.datahelper.datasource.online.FetchWebPage;

/**
 * @author channarith.bong
 */
public class AboutFragment extends Fragment implements View.OnClickListener {

    private static final String KEY_PAGE_ABOUT = ApplicationConfiguration.PAGE_ABOUT;

    private TextView txt_term;
    private TextView txt_policy;
    private TextView txtVersion;
    private WebView web_view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        txt_term = view.findViewById(R.id.txt_term);
        txt_policy = view.findViewById(R.id.txt_policy);
        txtVersion = view.findViewById(R.id.txt_version);
        web_view = view.findViewById(R.id.web_view);
        txt_policy.setOnClickListener(this);
        txt_term.setOnClickListener(this);

        try {
            txtVersion.setText(String.format("Version %s", getContext().getPackageManager()
                    .getPackageInfo(getContext().getPackageName(), 0).versionName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        loadWebContent();
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), PrivacyPolicyActivity.class);
        if (txt_term.equals(v)) {
            intent.putExtra(ConstantValue.URL_KEY, ApplicationConfiguration.PAGE_TERMS_CONDITIONS);
            intent.putExtra(ConstantValue.TITLE, "Term and Condition");
            startActivity(intent);
        } else if (txt_policy.equals(v)) {
            intent.putExtra(ConstantValue.URL_KEY, ApplicationConfiguration.PAGE_PRIVACY_POLICY);
            intent.putExtra(ConstantValue.TITLE, "Privacy Policy");
            startActivity(intent);
        }
    }

    /**
     *
     *
     */
    private void loadWebContent() {
        new FetchWebPage(KEY_PAGE_ABOUT, new FetchWebPage.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(String content) {
                if (content != null) {
                    web_view.loadData(content, "text/html", "UTF-8");
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }
}
