package com.planb.thespeed.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.planb.thespeed.R;
import com.planb.thespeed.constant.ConstantValue;
import com.planb.thespeed.model.Customer;
import com.planb.thespeed.model.Login;
import com.planb.thespeed.model.UserSecure;
import com.planb.thespeed.security.UserAccount;
import com.planb.thespeed.service.datahelper.datasource.online.FetchCustomer;
import com.planb.thespeed.util.DateUtil;
import com.planb.thespeed.util.ExceptionUtils;
import com.planb.thespeed.util.InputHelper;
import com.planb.thespeed.util.LoggerHelper;

/**
 * @author channarith.bong
 */
public class SigninActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_log_in;
    private Button btn_create_account;
    private View container_float_loading;
    private View parentPanel;

    private UserAccount userAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        parentPanel = findViewById(R.id.parentPanel);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Sign up");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        btn_log_in = findViewById(R.id.btn_log_in);
        btn_log_in.setOnClickListener(this);

        btn_create_account = findViewById(R.id.btn_create_account);
        btn_create_account.setOnClickListener(this);

        container_float_loading = findViewById(R.id.container_float_loading);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (btn_create_account.equals(v)) {
            if (getValueFromView() != null) {
                UserSecure user = getValueFromView();
                checkUserAccount(user);
            }
        } else if (btn_log_in.equals(v)) {
            Intent intent = new Intent(SigninActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    /**
     * @return UserSecure
     */
    private UserSecure getValueFromView() {
        EditText edt_first_name = findViewById(R.id.edt_first_name);
        EditText edt_last_name = findViewById(R.id.edt_last_name);
        EditText edt_email_address = findViewById(R.id.edt_email_address);
        EditText edt_password = findViewById(R.id.edt_password);

        boolean hasError = false;
        if (edt_first_name.getText().toString().isEmpty()) {
            edt_first_name.setError("First name cannot be empty");
            hasError = true;
        }
        if (edt_last_name.getText().toString().isEmpty()) {
            edt_last_name.setError("Last name cannot be empty");
            hasError = true;
        }
        if (edt_email_address.getText().toString().isEmpty()) {
            edt_email_address.setError("Email cannot be empty");
            hasError = true;
        }
        if (edt_password.getText().toString().isEmpty()) {
            edt_password.setError("Password cannot be empty");
            hasError = true;
        } else if (edt_password.getText().length() < 8) {
            edt_password.setError("Password must be at least 8 characters");
            hasError = true;
        }

        if (hasError) {
            return null;
        }

        UserSecure userSecure = new UserSecure();
        Customer customer = new Customer();
        customer.setEmail(edt_email_address.getText().toString());
        customer.setFirstname(edt_first_name.getText().toString());
        customer.setLastname(edt_last_name.getText().toString());
        customer.setCreatedAt(DateUtil.getCurrent());
        customer.setUpdateAt(DateUtil.getCurrent());
        userSecure.setCustomer(customer);
        userSecure.setPassword(edt_password.getText().toString());
        return userSecure;
    }

    /**
     * Check user account before go to sign new account
     *
     * @param user UserSecure
     */
    private void checkUserAccount(UserSecure user) {
        userAccount = new UserAccount(this);
        requestAddNewCustomer(user);
    }

    /**
     * @param user UserSecure
     */
    private void requestAddNewCustomer(final UserSecure user) {
        new FetchCustomer(user, new FetchCustomer.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(Customer customer) {
                if (customer != null) {
                    userAccount.insertCustomer(customer);
                    settingProcessBar(true, "Your registration success!");
                    Intent intent = new Intent(SigninActivity.this, LoginActivity.class);
                    Login login = new Login(user.getCustomer().getEmail(), user.getPassword());
                    intent.putExtra(ConstantValue.USER_SEC, login);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onError(Throwable e) {
                Snackbar.make(parentPanel, "Your registration fail: " + ExceptionUtils.translateExceptionMessage(e), Snackbar.LENGTH_LONG).show();
                LoggerHelper.showErrorLog(" Register " + e.getMessage());
            }
        });
    }

    /**
     * @param isShow Boolean
     * @param message String
     */
    private void settingProcessBar(Boolean isShow, String message) {
        InputHelper.hideKeyboard(this);
        TextView txt = container_float_loading.findViewById(R.id.txt_container_loading_label);
        ProgressBar loading_cycle_i = container_float_loading.findViewById(R.id.loading_cycle_ii);
        container_float_loading.setVisibility(isShow ? View.VISIBLE : View.GONE);
        if (message != null) {
            loading_cycle_i.setVisibility(View.GONE);
            txt.setText(message);
        } else {
            loading_cycle_i.setVisibility(View.VISIBLE);
            txt.setText(R.string.loading);
        }
    }

}
