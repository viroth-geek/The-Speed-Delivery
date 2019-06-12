package com.iota.eshopping.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.iota.eshopping.R;
import com.iota.eshopping.constant.ApplicationConfiguration;
import com.iota.eshopping.constant.ConstantValue;
import com.iota.eshopping.model.Address;
import com.iota.eshopping.model.CustomAttribute;
import com.iota.eshopping.model.Customer;
import com.iota.eshopping.model.CustomerPhoneNumber;
import com.iota.eshopping.model.PhoneNumber;
import com.iota.eshopping.model.TokenPhoneNumber;
import com.iota.eshopping.model.phone.PhoneResponse;
import com.iota.eshopping.security.UserAccount;
import com.iota.eshopping.server.DatabaseHelper;
import com.iota.eshopping.service.base.InvokeOnCompleteAsync;
import com.iota.eshopping.service.datahelper.datasource.offine.address.FetchAddressDAO;
import com.iota.eshopping.service.datahelper.datasource.online.FetchAddressList;
import com.iota.eshopping.service.datahelper.datasource.online.FetchTokenByPhone;
import com.iota.eshopping.service.datahelper.datasource.online.FetchUserByPhone;
import com.iota.eshopping.util.ExceptionUtils;
import com.iota.eshopping.util.LoggerHelper;
import com.iota.eshopping.util.Utils;

import java.util.concurrent.TimeUnit;

public class VerificationCodeActivity extends AppCompatActivity {

    private EditText etCode;
    private String mPhoneNumber;
    private TextView tvInformation;
    private TextView tvCodeCountdown;
    private TextView tvResendCode;
    private View container_float_loading;
    private View parentPanel;

    private UserAccount userAccount;
    private Customer customer = new Customer();
    private FetchAddressDAO db;

    private Handler handler;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;

    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    private TokenPhoneNumber tokenPhoneNumber = new TokenPhoneNumber();
    private TokenPhoneNumber.Token token = new TokenPhoneNumber.Token();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verication_code);

        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        etCode = findViewById(R.id.edit_text_pin_code);
        tvInformation = findViewById(R.id.txt_information);
        tvCodeCountdown = findViewById(R.id.txt_code_countdown);
        tvResendCode = findViewById(R.id.txt_resend_code);
        parentPanel = findViewById(R.id.parentPanel);
        container_float_loading = findViewById(R.id.container_float_loading);

        mVerificationId = getIntent().getStringExtra(ApplicationConfiguration.VERIFICATION_ID);
        mPhoneNumber = getIntent().getStringExtra(ApplicationConfiguration.PHONE_NUMBER);

        db = new FetchAddressDAO(DatabaseHelper.getInstance(this).getDatabase());
        tvInformation.setText("We have sent you an SMS with the code to +855 " + mPhoneNumber);

        handler = new Handler();
        handler.post(new CountdownTask());

        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 6) {
                    Utils.hideKeyboard(VerificationCodeActivity.this);
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, charSequence.toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tvResendCode.setOnClickListener(view -> {
            etCode.setText("");
            FireBasePhoneAuthentication("+855" + mPhoneNumber);
            tvResendCode.setVisibility(View.GONE);
        });
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        userAccount = new UserAccount(this);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        PhoneNumber phoneNumber = new PhoneNumber();
                        phoneNumber.setPhoneNumber(mPhoneNumber);
                        PhoneNumber.CustomerPhone customerPhone = new PhoneNumber.CustomerPhone();
                        customerPhone.setPhoneNumber(phoneNumber);
                        requestToken(customerPhone);
                    }
                })
                .addOnFailureListener(this, e -> {


                    View inflater = getLayoutInflater().inflate(R.layout.customer_toast, findViewById(R.id.toastLayout));

                    CardView cardView = inflater.findViewById(R.id.card_view);
                    TextView title = inflater.findViewById(R.id.toast_title);
                    ImageView icon = inflater.findViewById(R.id.toast_image);

                    cardView.setCardBackgroundColor(Color.RED);
                    title.setText("Invalid verification code entered.");

                    Toast toast = new Toast(this);
                    toast.setGravity(Gravity.BOTTOM, 0, 30);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(inflater);
                    toast.show();

                });
    }

    private void requestToken(PhoneNumber.CustomerPhone customerPhone) {
        container_float_loading.setVisibility(View.VISIBLE);
        new FetchTokenByPhone(customerPhone, new FetchTokenByPhone.ILoginOnCompleteAsync() {
            @Override
            public void onComplete(PhoneResponse phoneResponse) {
                String status = phoneResponse.getStatus();

                if (status.equals(ApplicationConfiguration.SUCCESS)) {
                    try {
                        if (userAccount.assignToken(phoneResponse.getPhone().getRpToken())) {
                            token.setToken(phoneResponse.getPhone().getRpToken());
                            tokenPhoneNumber.setToken(token);
                            requestCustomerInfo(tokenPhoneNumber);
                        }

                    } catch (Exception e) {
                        container_float_loading.setVisibility(View.GONE);
                        Log.d(ApplicationConfiguration.TAG, e.getMessage());
                        //Snackbar.make(parentPanel, "Logged fail: " + ExceptionUtils.translateExceptionMessage(e), Snackbar.LENGTH_LONG).show();
                    }

                } else if (status.equals(ApplicationConfiguration.REGISTER)) {
                    Intent intent = new Intent(VerificationCodeActivity.this, SignupActivity.class);
                    intent.putExtra(ApplicationConfiguration.REGISTER_BY_PHONE_NUMBER, ApplicationConfiguration.REGISTER_BY_PHONE_NUMBER);
                    intent.putExtra(ApplicationConfiguration.PHONE_NUMBER, mPhoneNumber);
                    startActivity(intent);
                }

            }

            @Override
            public void onError(Throwable e) {
                container_float_loading.setVisibility(View.GONE);
                Log.d(ApplicationConfiguration.TAG, "Getting token is error " + e.getMessage());
                //Snackbar.make(parentPanel, "Logged fail: " + ExceptionUtils.translateExceptionMessage(e), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Request customer token
     *
     * @param token String
     */
    private void requestCustomerInfo(final TokenPhoneNumber token) {

        new FetchUserByPhone(token, new FetchUserByPhone.ILoginOnCompleteAsync() {
            @Override
            public void onComplete(CustomerPhoneNumber customerPhoneNumber) {

//                if (customerPhoneNumber.getStatus().equals(ApplicationConfiguration.EMAIL_EXISTED)) {
//                View inflater = getLayoutInflater().inflate(R.layout.customer_toast, findViewById(R.id.toastLayout));
//                CardView cardView = inflater.findViewById(R.id.card_view);
//                TextView title = inflater.findViewById(R.id.toast_title);
//                ImageView icon = inflater.findViewById(R.id.toast_image);
//
//                cardView.setCardBackgroundColor(R.color.red);
//                title.setText("Email already existed. Please try again");
//
//                Toast toast = new Toast(VerificationCodeActivity.this);
//                toast.setGravity(Gravity.BOTTOM, 0, 30);
//                toast.setDuration(Toast.LENGTH_SHORT);
//                toast.setView(inflater);
//                toast.show();

//                } else {

                customer.setId(customerPhoneNumber.getCustomer().getId());
                customer.setEntityId(customerPhoneNumber.getCustomer().getEntityId());
                customer.setWebsiteId(customerPhoneNumber.getCustomer().getWebsiteId());
                customer.setRpToken(token.getToken().getToken());
                customer.setPhonenumber(customerPhoneNumber.getCustomer().getPhonenumber());
                customer.setAddresses(customerPhoneNumber.getCustomer().getAddresses());
                customer.setCreatedAt(customerPhoneNumber.getCustomer().getCreatedAt());
                customer.setCreatedIn(customerPhoneNumber.getCustomer().getCreatedIn());
                customer.setFirstname(customerPhoneNumber.getCustomer().getFirstname());
                customer.setLastname(customerPhoneNumber.getCustomer().getLastname());
                customer.setGroupId(customerPhoneNumber.getCustomer().getGroupId());
                customer.setDisableAutoGroupChange(customerPhoneNumber.getCustomer().getDisableAutoGroupChange());
                customer.setPhonenumber(customerPhoneNumber.getCustomer().getPhonenumber());
                customer.setEmail(customerPhoneNumber.getCustomer().getEmail());
                customer.setUpdateAt(customerPhoneNumber.getCustomer().getUpdateAt());
                customer.setCreatedIn(customerPhoneNumber.getCustomer().getCreatedIn());
                customer.setPrefix(customerPhoneNumber.getCustomer().getPrefix());
                customer.setSuffix(customerPhoneNumber.getCustomer().getSuffix());
                customer.setTaxvat(customerPhoneNumber.getCustomer().getTaxvat());
                customer.setDob(customerPhoneNumber.getCustomer().getDob());
                customer.setRewardUpdateNotification(customerPhoneNumber.getCustomer().getRewardUpdateNotification());
                customer.setRewardWarningNotification(customerPhoneNumber.getCustomer().getRewardWarningNotification());
                customer.setRpTokenCreatedAt(customerPhoneNumber.getCustomer().getRpTokenCreatedAt());
                customer.setStoreId(customerPhoneNumber.getCustomer().getStoreId());
                customer.setDefaultShipping(customerPhoneNumber.getCustomer().getDefaultShipping());
                customer.setDefaultBilling(customerPhoneNumber.getCustomer().getDefaultBilling());

                customerPhoneNumber.setCustomer(customer);
                if (customerPhoneNumber.getCustomer() != null) {
                    if (userAccount.insertCustomer(customerPhoneNumber.getCustomer())) {
                        Log.d(ApplicationConfiguration.TAG, "user account saved " + userAccount.getCustomerToken()
                                + "-" + userAccount.getCustomer().getFirstname() + "- " + userAccount.getCustomer().getPhonenumber());

                        //Snackbar.make(parentPanel, "Logged success!", Snackbar.LENGTH_LONG).show();

                        syncAddressList(customerPhoneNumber.getCustomer().getId());
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                    } else {
                        Snackbar.make(parentPanel, "Sorry, please try again.", Snackbar.LENGTH_LONG).show();

                    }
                    container_float_loading.setVisibility(View.GONE);
                }
//                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(ApplicationConfiguration.TAG, "Error fetching customer infor " + e.getMessage());
                Snackbar.make(parentPanel, "You logged fail: " + ExceptionUtils.translateExceptionMessage(e), Snackbar.LENGTH_LONG).show();
                LoggerHelper.showErrorLog("409, Login Page: ", e);
                container_float_loading.setVisibility(View.GONE);
            }
        });

//        new FetchCustomer(token, new FetchCustomer.InvokeOnCompleteAsync() {
//            @Override
//            public void onComplete(Customer customer) {
//                if (customer != null) {
//                    customer.setRpToken(token);
//                    if (userAccount.insertCustomer(customer)) {
//                        Snackbar.make(parentPanel, "Logged success!", Snackbar.LENGTH_LONG).show();
//                        syncAddressList(customer.getId());
//                        Intent returnIntent = new Intent();
//                        setResult(Activity.RESULT_OK, returnIntent);
//                        finish();
//
//                    } else {
//                        Snackbar.make(parentPanel, "Sorry, please try again.", Snackbar.LENGTH_LONG).show();
//                    }
//                } else {
//                    Snackbar.make(parentPanel, "Sorry, please try again.", Snackbar.LENGTH_LONG).show();
//                }
//                container_float_loading.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d(ApplicationConfiguration.TAG, "Error fetching customer infor " + e.getMessage());
//                Snackbar.make(parentPanel, "You logged fail: " + ExceptionUtils.translateExceptionMessage(e), Snackbar.LENGTH_LONG).show();
//                LoggerHelper.showErrorLog("409, Login Page: ", e);
//                container_float_loading.setVisibility(View.GONE);
//            }
//        });

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void FireBasePhoneAuthentication(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                30L,
                TimeUnit.SECONDS,
                this,
                mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential credential) {

                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(VerificationCodeActivity.this, "code failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken token) {
                        mVerificationId = verificationId;
                        mResendToken = token;
                        handler.post(new CountdownTask());

                    }
                });
    }

    private class CountdownTask implements Runnable {
        public Handler handler;
        long start = 0;
        long elapse = 0;

        public CountdownTask() {
            tvCodeCountdown.setVisibility(View.VISIBLE);
            handler = new Handler();
            start = System.currentTimeMillis();
        }

        @Override
        public void run() {
            elapse = System.currentTimeMillis() - start;
            if (elapse <= 30000) {
                tvCodeCountdown.setText(getString(R.string.sms_can_be_resend_in) + " " + (30 - (elapse / 1000)));
                handler.postDelayed(this, 1000);
            } else {
                tvResendCode.setVisibility(View.VISIBLE);
                tvCodeCountdown.setVisibility(View.GONE);
                handler.removeCallbacksAndMessages(null);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tokenPhoneNumber = null;
        token = null;
        customer = null;
        userAccount = null;
        db = null;
    }
}
