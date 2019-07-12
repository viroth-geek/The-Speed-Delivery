package com.planb.thespeed.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.planb.thespeed.R;
import com.planb.thespeed.constant.ConstantValue;
import com.planb.thespeed.model.Address;
import com.planb.thespeed.model.CustomAttribute;
import com.planb.thespeed.model.Customer;
import com.planb.thespeed.model.Login;
import com.planb.thespeed.model.PhoneNumber;
import com.planb.thespeed.model.TokenPhoneNumber;
import com.planb.thespeed.model.UserSecure;
import com.planb.thespeed.security.UserAccount;
import com.planb.thespeed.server.DatabaseHelper;
import com.planb.thespeed.service.base.InvokeOnCompleteAsync;
import com.planb.thespeed.service.datahelper.datasource.offine.address.FetchAddressDAO;
import com.planb.thespeed.service.datahelper.datasource.online.FetchAddressList;
import com.planb.thespeed.service.datahelper.datasource.online.FetchCustomer;
import com.planb.thespeed.service.datahelper.datasource.online.FetchTokenByPhone;
import com.planb.thespeed.util.DateUtil;
import com.planb.thespeed.util.ExceptionUtils;
import com.planb.thespeed.util.InputHelper;
import com.planb.thespeed.util.LoggerHelper;
import com.planb.thespeed.util.NetworkConnectHelper;
import com.planb.thespeed.util.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author channarith.bong
 */
public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_log_in;
    private Button btn_create_account;
    private EditText etPhoneNumber;
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmailAddress;
    private EditText etPassword;
    private RelativeLayout progressBar;

    private View containerFloatLoading;
    private View parentPanel;
    private ImageButton btnShowHidePassword;

    private UserAccount userAccount;
    private FetchAddressDAO db;

    private String mRegisterType;

    private TokenPhoneNumber tokenPhoneNumber = new TokenPhoneNumber();
    private TokenPhoneNumber.Token token = new TokenPhoneNumber.Token();

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
        btnShowHidePassword = findViewById(R.id.btn_show_hide_password);
        btnShowHidePassword.setAlpha(0.25f);
        btnShowHidePassword.setOnClickListener(view -> {
            TransformationMethod transformationMethod = etPassword.getTransformationMethod();
            if (transformationMethod == null) {
                btnShowHidePassword.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {
                btnShowHidePassword.setImageResource(R.drawable.ic_visibility_black_24dp);
                etPassword.setTransformationMethod(null);
            }
        });
        btn_log_in.setOnClickListener(this);

        progressBar = findViewById(R.id.loading_progress_bar);

        btn_create_account = findViewById(R.id.btn_create_account);
        btn_create_account.setOnClickListener(this);

        containerFloatLoading = findViewById(R.id.container_float_loading);
        etPhoneNumber = findViewById(R.id.edt_phone_number);
        etFirstName = findViewById(R.id.edt_first_name);
        etLastName = findViewById(R.id.edt_last_name);
        etEmailAddress = findViewById(R.id.edt_email_address);
        etPassword = findViewById(R.id.edt_password);

        if (getIntent().getExtras() != null) {
            mRegisterType = getIntent().getStringExtra(ConstantValue.REGISTER_BY_PHONE_NUMBER);
            etPhoneNumber.setText(getIntent().getStringExtra(ConstantValue.PHONE_NUMBER));
        }
        //if register by phone
        if (mRegisterType != null) {
            etPhoneNumber.setVisibility(View.VISIBLE);
            etPassword.setVisibility(View.GONE);
            btnShowHidePassword.setVisibility(View.GONE);
        }

        db = new FetchAddressDAO(DatabaseHelper.getInstance(this).getDatabase());

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
            Utils.hideKeyboard(this);
            progressBar.setVisibility(View.VISIBLE);
            boolean isConnect = NetworkConnectHelper.getInstance().isConnectionOnline(getApplicationContext());
            if (isConnect) {
                if (mRegisterType != null) {
                    PhoneNumber.CustomerPhone customerPhone = getValueFromViewByPhone();
                    requestTokenByPhone(customerPhone);

                } else {
                    if (getValueFromView() != null) {
                        UserSecure user = getValueFromView();
                        checkUserAccount(user);
                    }
                }
            } else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Internet disconnected!. Try again", Toast.LENGTH_SHORT).show();
            }

        } else if (btn_log_in.equals(v)) {
            Utils.hideKeyboard(this);
            progressBar.setVisibility(View.GONE);
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private PhoneNumber.CustomerPhone getValueFromViewByPhone() {
        boolean hasError = false;

        if (etPhoneNumber.getText().toString().isEmpty()) {
            etFirstName.setError("First name cannot be empty");
            hasError = true;
        }
        if (etFirstName.getText().toString().isEmpty()) {
            etFirstName.setError("First name cannot be empty");
            hasError = true;
        }
        if (etLastName.getText().toString().isEmpty()) {
            etLastName.setError("Last name cannot be empty");
            hasError = true;
        }
        if (!etEmailAddress.getText().toString().equals("")) {
            if (!isEmailValid(etEmailAddress.getText().toString())) {
                etEmailAddress.setError("Email invalid");
                hasError = true;
            }
        }

        if (hasError) {
            return null;
        }

        PhoneNumber.CustomerPhone customerPhone = new PhoneNumber.CustomerPhone();
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setPhoneNumber(etPhoneNumber.getText().toString());
        phoneNumber.setFirstName(etFirstName.getText().toString());
        phoneNumber.setLastName(etLastName.getText().toString());
        phoneNumber.setEmail(etEmailAddress.getText().toString());

        customerPhone.setPhoneNumber(phoneNumber);
        return customerPhone;
    }

    /**
     * @return UserSecure
     */
    private UserSecure getValueFromView() {

        boolean hasError = false;
        if (etFirstName.getText().toString().isEmpty()) {
            etFirstName.setError("First name cannot be empty");
            hasError = true;
        }
        if (etLastName.getText().toString().isEmpty()) {
            etLastName.setError("Last name cannot be empty");
            hasError = true;
        }
        if (etEmailAddress.getText().toString().isEmpty()) {
            etEmailAddress.setError("Email cannot be empty");
            hasError = true;
        }
        if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError("Password cannot be empty");
            hasError = true;
        } else if (etPassword.getText().length() < 8) {
            etPassword.setError("Password must be at least 8 characters");
            hasError = true;
        }

        if (hasError) {
            return null;
        }

        UserSecure userSecure = new UserSecure();
        Customer customer = new Customer();
        if (etPhoneNumber.getVisibility() == View.VISIBLE) {
            customer.setPhonenumber(etPhoneNumber.getText().toString());
        }
        customer.setEmail(etEmailAddress.getText().toString());
        customer.setFirstname(etFirstName.getText().toString());
        customer.setLastname(etLastName.getText().toString());
        customer.setCreatedAt(DateUtil.getCurrent());
        customer.setUpdateAt(DateUtil.getCurrent());
        userSecure.setCustomer(customer);
        if (mRegisterType == null) {
            userSecure.setPassword(etPassword.getText().toString());
        }
        return userSecure;
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
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
                progressBar.setVisibility(View.GONE);
                if (customer != null) {
                    userAccount.insertCustomer(customer);
                    settingProcessBar(true, "Your registration success!");
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    Login login = new Login(user.getCustomer().getEmail(), user.getPassword());
                    intent.putExtra(ConstantValue.USER_SEC, login);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onError(Throwable e) {
                progressBar.setVisibility(View.GONE);
                Snackbar.make(parentPanel, "Check your password or some information again.", Snackbar.LENGTH_LONG).show();
                LoggerHelper.showErrorLog(" Register " + e.getMessage());
            }
        });
    }


    /**
     * @param user UserSecure
     */
    private void requestTokenByPhone(final PhoneNumber.CustomerPhone user) {
        new FetchTokenByPhone(user, new FetchTokenByPhone.ILoginOnCompleteAsync() {
            @Override
            public void onComplete(String token) {
                if (token.equals(ConstantValue.EMAIL_EXISTED)) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SignupActivity.this, token, Toast.LENGTH_LONG).show();
                } else {
                    progressBar.setVisibility(View.GONE);
                    userAccount = new UserAccount(SignupActivity.this);
                    if (userAccount.assignToken(token)) {
                        requestCustomerInfo(token);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                progressBar.setVisibility(View.GONE);
                Log.d(ConstantValue.TAG_LOG, "register error" + e.getMessage());
            }
        });
    }

    /**
     * Request customer token
     *
     * @param token String
     */
    private void requestCustomerInfo(final String token) {
        new FetchCustomer(token, new FetchCustomer.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(Customer customer) {
                if (customer != null) {
                    customer.setRpToken(token);
                    if (userAccount.insertCustomer(customer)) {
                        Snackbar.make(parentPanel, "Logged success!", Snackbar.LENGTH_LONG).show();
                        syncAddressList(customer.getId());
                        Intent returnIntent = new Intent(SignupActivity.this, BaseActivity.class);
                        startActivity(returnIntent);
                        finish();

                    } else {
                        Snackbar.make(parentPanel, "Sorry, please try again.", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(parentPanel, "Sorry, please try again.", Snackbar.LENGTH_LONG).show();
                }
                containerFloatLoading.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                //Snackbar.make(parentPanel, "You logged fail: " + ExceptionUtils.translateExceptionMessage(e), Snackbar.LENGTH_LONG).show();
                Toast.makeText(SignupActivity.this, "You logged fail: " + ExceptionUtils.translateExceptionMessage(e), Toast.LENGTH_SHORT).show();
                LoggerHelper.showErrorLog("409, Login Page: ", e);
                containerFloatLoading.setVisibility(View.GONE);
            }
        });
    }

    /**
     * @param userId Long
     */
    private void syncAddressList(Long userId) {
        db.deleteAll();
        new FetchAddressList(userId, new InvokeOnCompleteAsync<Customer>() {
            @Override
            public void onComplete(Customer customer) {
                for (Address address : customer.getAddresses()) {
                    if (address.getCustomAttributes() != null) {
                        for (CustomAttribute customAttribute : address.getCustomAttributes()) {
                            if (customAttribute.getAttributeCode().equals(ConstantValue.LONGITUDE)) {
                                address.setLongitude(Double.valueOf(customAttribute.getValue().toString()));
                            }
                            if (customAttribute.getAttributeCode().equals(ConstantValue.LATITUDE)) {
                                address.setLatitude(Double.valueOf(customAttribute.getValue().toString()));
                            }
                            if (address.getLongitude() != null && address.getLatitude() != null) {
                                LoggerHelper.showDebugLog("====> Saved =========== ");
                                db.insert(address);
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                LoggerHelper.showErrorLog("Error: ", e);
            }
        });
    }

    /**
     * @param isShow  Boolean
     * @param message String
     */
    private void settingProcessBar(Boolean isShow, String message) {
        InputHelper.hideKeyboard(this);
        TextView txt = containerFloatLoading.findViewById(R.id.txt_container_loading_label);
        ProgressBar loading_cycle_i = containerFloatLoading.findViewById(R.id.loading_cycle_ii);
        containerFloatLoading.setVisibility(isShow ? View.VISIBLE : View.GONE);
        if (message != null) {
            loading_cycle_i.setVisibility(View.GONE);
            txt.setText(message);
        } else {
            loading_cycle_i.setVisibility(View.VISIBLE);
            txt.setText(R.string.loading);
        }
    }

}
