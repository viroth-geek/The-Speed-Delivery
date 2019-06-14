package com.iota.eshopping.activity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.iota.eshopping.R;
import com.iota.eshopping.constant.ConstantValue;
import com.iota.eshopping.model.Address;
import com.iota.eshopping.model.CustomAttribute;
import com.iota.eshopping.model.Customer;
import com.iota.eshopping.model.Login;
import com.iota.eshopping.model.PhoneNumber;
import com.iota.eshopping.model.TokenPhoneNumber;
import com.iota.eshopping.model.UserSecure;
import com.iota.eshopping.security.UserAccount;
import com.iota.eshopping.server.DatabaseHelper;
import com.iota.eshopping.service.base.InvokeOnCompleteAsync;
import com.iota.eshopping.service.datahelper.datasource.offine.address.FetchAddressDAO;
import com.iota.eshopping.service.datahelper.datasource.online.FetchAddressList;
import com.iota.eshopping.service.datahelper.datasource.online.FetchCustomer;
import com.iota.eshopping.service.datahelper.datasource.online.FetchTokenByPhone;
import com.iota.eshopping.util.DateUtil;
import com.iota.eshopping.util.ExceptionUtils;
import com.iota.eshopping.util.InputHelper;
import com.iota.eshopping.util.LoggerHelper;
import com.iota.eshopping.util.NetworkConnectHelper;

/**
 * @author channarith.bong
 */
public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_log_in;
    private Button btn_create_account;
    private EditText etPhoneNumber;
    private EditText edt_first_name;
    private EditText edt_last_name;
    private EditText edt_email_address;
    private EditText edt_password;

    private View container_float_loading;
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
            TransformationMethod transformationMethod = edt_password.getTransformationMethod();
            if (transformationMethod == null) {
                btnShowHidePassword.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                edt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {
                btnShowHidePassword.setImageResource(R.drawable.ic_visibility_black_24dp);
                edt_password.setTransformationMethod(null);
            }
        });
        btn_log_in.setOnClickListener(this);

        btn_create_account = findViewById(R.id.btn_create_account);
        btn_create_account.setOnClickListener(this);

        container_float_loading = findViewById(R.id.container_float_loading);
        etPhoneNumber = findViewById(R.id.edt_phone_number);
        edt_first_name = findViewById(R.id.edt_first_name);
        edt_last_name = findViewById(R.id.edt_last_name);
        edt_email_address = findViewById(R.id.edt_email_address);
        edt_password = findViewById(R.id.edt_password);

        if (getIntent().getExtras() != null) {
            mRegisterType = getIntent().getStringExtra(ConstantValue.REGISTER_BY_PHONE_NUMBER);
            etPhoneNumber.setText(getIntent().getStringExtra(ConstantValue.PHONE_NUMBER));
        }
        //if register by phone
        if (mRegisterType != null) {
            etPhoneNumber.setVisibility(View.VISIBLE);
            edt_password.setVisibility(View.GONE);
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
            boolean isConnect = NetworkConnectHelper.getInstance().isConnectionOnline(getApplicationContext());
            if (isConnect) {
                if (mRegisterType != null) {
                    PhoneNumber.CustomerPhone customerPhone = new PhoneNumber.CustomerPhone();
                    PhoneNumber phoneNumber = new PhoneNumber();

                    phoneNumber.setPhoneNumber(etPhoneNumber.getText().toString());
                    phoneNumber.setFirstName(edt_first_name.getText().toString());
                    phoneNumber.setLastName(edt_last_name.getText().toString());
                    phoneNumber.setEmail(edt_email_address.getText().toString());
                    phoneNumber.setEmail(edt_email_address.getText().toString());

                    customerPhone.setPhoneNumber(phoneNumber);
                    requestTokenByPhone(customerPhone);

                } else {
                    if (getValueFromView() != null) {
                        UserSecure user = getValueFromView();
                        checkUserAccount(user);
                    }
                }
            } else {
                Toast.makeText(this, "Internet disconnected!. Try again", Toast.LENGTH_SHORT).show();
            }

        } else if (btn_log_in.equals(v)) {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    /**
     * @return UserSecure
     */
    private UserSecure getValueFromView() {

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
        if (etPhoneNumber.getVisibility() == View.VISIBLE) {
            customer.setPhonenumber(etPhoneNumber.getText().toString());
        }
        customer.setEmail(edt_email_address.getText().toString());
        customer.setFirstname(edt_first_name.getText().toString());
        customer.setLastname(edt_last_name.getText().toString());
        customer.setCreatedAt(DateUtil.getCurrent());
        customer.setUpdateAt(DateUtil.getCurrent());
        userSecure.setCustomer(customer);
        if (mRegisterType == null) {
            userSecure.setPassword(edt_password.getText().toString());
        }
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
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
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
     * @param user UserSecure
     */
    private void requestTokenByPhone(final PhoneNumber.CustomerPhone user) {
        new FetchTokenByPhone(user, new FetchTokenByPhone.ILoginOnCompleteAsync() {
            @Override
            public void onComplete(String token) {

                if (token.equals(ConstantValue.EMAIL_EXISTED)) {
                    Toast.makeText(SignupActivity.this, token, Toast.LENGTH_SHORT).show();
                } else {
                    userAccount = new UserAccount(SignupActivity.this);
                    if (userAccount.assignToken(token)) {
                        requestCustomerInfo(token);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
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
                container_float_loading.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                //Snackbar.make(parentPanel, "You logged fail: " + ExceptionUtils.translateExceptionMessage(e), Snackbar.LENGTH_LONG).show();
                Toast.makeText(SignupActivity.this, "You logged fail: " + ExceptionUtils.translateExceptionMessage(e), Toast.LENGTH_SHORT).show();
                LoggerHelper.showErrorLog("409, Login Page: ", e);
                container_float_loading.setVisibility(View.GONE);
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
