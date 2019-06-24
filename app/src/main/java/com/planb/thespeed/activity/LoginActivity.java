package com.planb.thespeed.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.planb.thespeed.R;
import com.planb.thespeed.constant.ConstantValue;
import com.planb.thespeed.constant.entity.FacebookAccessScope;
import com.planb.thespeed.model.Address;
import com.planb.thespeed.model.CustomAttribute;
import com.planb.thespeed.model.Customer;
import com.planb.thespeed.model.Login;
import com.planb.thespeed.model.enumeration.SocialType;
import com.planb.thespeed.model.form.FormSocialUser;
import com.planb.thespeed.model.form.SocialLoginForm;
import com.planb.thespeed.security.UserAccount;
import com.planb.thespeed.server.DatabaseHelper;
import com.planb.thespeed.service.ClientTokenService;
import com.planb.thespeed.service.base.InvokeOnCompleteAsync;
import com.planb.thespeed.service.datahelper.datasource.offine.address.FetchAddressDAO;
import com.planb.thespeed.service.datahelper.datasource.online.FetchAddressList;
import com.planb.thespeed.service.datahelper.datasource.online.FetchCustomer;
import com.planb.thespeed.service.datahelper.datasource.online.SocialLoginService;
import com.planb.thespeed.util.ExceptionUtils;
import com.planb.thespeed.util.InputHelper;
import com.planb.thespeed.util.LoggerHelper;
import com.planb.thespeed.util.NetworkConnectHelper;
import com.planb.thespeed.widget.ForgetPasswordAlertDialog;

import org.json.JSONException;

import java.util.Arrays;

/**
 * @author channarith.bong
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btn_log_in, btn_sign_up, btn_continue_phone;
    private EditText edt_email_address, edt_password;
    private View container_float_loading;
    private UserAccount userAccount;
    private View parentPanel;

    private ImageButton btnShowHidePassword;

    private FetchAddressDAO db;

    private CallbackManager callbackManager;

    private LoginButton facebookLoginButton;
    private Button btn_facebook_login;

    private GoogleSignInClient mGoogleSignInClient;
    //    private SignInButton googleSignInButton;
    private Button btn_google_login;

    private Button btn_forget_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        parentPanel = findViewById(R.id.parentPanel);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        container_float_loading = findViewById(R.id.container_float_loading);
        btn_log_in = findViewById(R.id.btn_log_in);
        btn_sign_up = findViewById(R.id.btn_sign_up);
        btn_continue_phone = findViewById(R.id.btn_continue_phone);

        edt_email_address = findViewById(R.id.edt_email_address);
        edt_password = findViewById(R.id.edt_password);

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

        btn_forget_password = findViewById(R.id.btn_forget_password);

        btn_forget_password.setOnClickListener(this);
        btn_log_in.setOnClickListener(this);
        btn_sign_up.setOnClickListener(this);
        btn_continue_phone.setOnClickListener(this);

        db = new FetchAddressDAO(DatabaseHelper.getInstance(this).getDatabase());

        checkIntent();

        configureFacebookLogin();
        configureGoogleSignIn();
    }

    /**
     * configure facebook login
     */
    private void configureFacebookLogin() {
        callbackManager = CallbackManager.Factory.create();

        facebookLoginButton = findViewById(R.id.login_button);
        facebookLoginButton.setReadPermissions(Arrays.asList(FacebookAccessScope.PUBLIC_PROFILE, FacebookAccessScope.EMAIL));
        facebookLoginButton.setVisibility(View.GONE);
        // If you are using in a fragment, call facebookLoginButton.setFragment(this);

        // Callback registration
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (loginResult != null) {
                    GraphRequest request = GraphRequest.newMeRequest(
                            loginResult.getAccessToken(), (jsonObject, graphResponse) -> {
                                try {
                                    FormSocialUser formSocialUser = new FormSocialUser();
                                    formSocialUser.setSocialID(jsonObject.getString("id"));
                                    formSocialUser.setFirstName(jsonObject.getString("first_name"));
                                    formSocialUser.setLastName(jsonObject.getString("last_name"));
                                    formSocialUser.setEmail(jsonObject.getString("email"));
                                    formSocialUser.setSocialType(SocialType.FACEBOOK.getValue());

                                    loginWithSocialAccount(prepareSocialLoginForm(formSocialUser));
                                } catch (JSONException e) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setTitle("Message");
                                    builder.setMessage("Problem while retrieve facebook data. Please sign up with your email.");
                                    builder.setPositiveButton("OK", (dialog, which) -> {
                                        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                                        startActivity(intent);
                                        finish();
                                    });
                                    builder.create().show();
                                }


                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,first_name,last_name,email");
                    request.setParameters(parameters);
                    request.executeAsync();
                }
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                LoggerHelper.showErrorLog("Error: " + exception);
            }
        });

        btn_facebook_login = findViewById(R.id.btn_facebook_login);
        btn_facebook_login.setOnClickListener(this);
    }

    /**
     * configure google sign in
     */
    private void configureGoogleSignIn() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        // Set the dimensions of the sign-in button.
//        googleSignInButton = findViewById(R.id.sign_in_button);
//        googleSignInButton.setSize(SignInButton.SIZE_STANDARD);
//        googleSignInButton.setOnClickListener(this);

        btn_google_login = findViewById(R.id.btn_google_login);
        btn_google_login.setOnClickListener(this);
    }

    /**
     * sign in with google
     */
    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, ConstantValue.GOOGLE_SIGN_IN);
    }

    /**
     * handle after sign click
     *
     * @param completedTask Task<GoogleSignInAccount>
     */
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
//            LoggerHelper.showDebugLog("Account: " + account.toJson());

            FormSocialUser formSocialUser = new FormSocialUser();
            formSocialUser.setSocialID(account.getId());
            formSocialUser.setFirstName(account.getFamilyName() == null || account.getFamilyName().isEmpty() ? account.getGivenName() : account.getFamilyName());
            formSocialUser.setLastName(account.getGivenName());
            formSocialUser.setEmail(account.getEmail());
            formSocialUser.setSocialType(SocialType.GOOGLE.getValue());

            loginWithSocialAccount(prepareSocialLoginForm(formSocialUser));

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            LoggerHelper.showErrorLog("signInResult:failed code=", e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ConstantValue.GOOGLE_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
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
        if (btn_sign_up.equals(v)) {
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
            finish();
        } else if (btn_log_in.equals(v)) {
            boolean isConnect = NetworkConnectHelper.getInstance().isConnectionOnline(getApplicationContext());
            if (isConnect) {
                prepareBeforeLogin();
            } else {
                Toast.makeText(this, "Internet disconnected!. Try again", Toast.LENGTH_SHORT).show();
            }
        } else if (btn_facebook_login.equals(v)) {
            facebookLoginButton.performClick();
        } else if (btn_google_login.equals(v)) {
            signInWithGoogle();
        } else if (btn_forget_password.equals(v)) {
            showForgetPasswordDialog();
        }else if (btn_continue_phone.equals(v)){
            Intent intent = new Intent(this, BaseActivity.class);
            intent.putExtra("CONTINUE_PHONE", "phone");
            startActivity(intent);
            finish();
        }
    }

    /**
     * show forget password dialog
     */
    private void showForgetPasswordDialog() {
        ForgetPasswordAlertDialog forgetPasswordAlertDialog = new ForgetPasswordAlertDialog();
        forgetPasswordAlertDialog.show(getSupportFragmentManager(), "ForgetPassword");
    }

    /**
     * login with social account
     *
     * @param socialLoginForm SocialLoginForm
     */
    private void loginWithSocialAccount(SocialLoginForm socialLoginForm) {
        container_float_loading.setVisibility(View.VISIBLE);
        new SocialLoginService(socialLoginForm, new SocialLoginService.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(String token) {
                if (token != null) {
                    if (userAccount == null) {
                        userAccount = new UserAccount(LoginActivity.this);
                    }
                    if (userAccount.assignToken(token)) {
                        requestCustomerInfo(token);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                logout();
                container_float_loading.setVisibility(View.GONE);
                LoggerHelper.showErrorLog("Login Page", e);
                Toast.makeText(LoginActivity.this, ": Cannot login with facebook", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * @param formSocialUser FormSocialUser
     * @return SocialLoginForm
     */
    private SocialLoginForm prepareSocialLoginForm(FormSocialUser formSocialUser) {
        return new SocialLoginForm(formSocialUser);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * logout
     */
    private void logout() {
        UserAccount userAccount = new UserAccount(this);
        userAccount.deleteCustomer();
        db.deleteAll();

        logoutFromFacebook();
        logoutFromGoogle();

    }

    /**
     * logout from facebook
     */
    private void logoutFromFacebook() {
        LoginManager.getInstance().logOut();
    }

    /**
     * Logout from google
     */
    private void logoutFromGoogle() {
        mGoogleSignInClient.revokeAccess();
        mGoogleSignInClient.signOut();
    }


    /**
     * Get value from Intent
     */
    private void checkIntent() {
        Login login = (Login) getIntent().getSerializableExtra(ConstantValue.USER_SEC);
        if (login != null) {
            edt_email_address.setText(login.getUsername());
            edt_password.setText(login.getPassword());
        }
    }

    /**
     * Check user store in local DB
     */
    private void prepareBeforeLogin() {
        userAccount = new UserAccount(this);
        if (container_float_loading.getVisibility() == View.GONE) {
            if (getValueFromView() != null) {
                requestToken(getValueFromView());
            }
        }
    }

    /**
     * Get value form input fields
     *
     * @return Login
     */
    private Login getValueFromView() {
        String email = edt_email_address.getText().toString();
        String password = edt_password.getText().toString();

        boolean hasError = false;
        if (email.isEmpty()) {
            edt_email_address.setError("Email cannot be empty");
            hasError = true;
        }
        if (password.isEmpty()) {
            edt_password.setError("Password cannot be empty");
            hasError = true;
        }

        if (hasError) {
            return null;
        }

        return new Login(email, password);
    }

    /**
     * Get customer token
     *
     * @param login Login
     */
    private void requestToken(Login login) {
        InputHelper.hideKeyboard(this);
        container_float_loading.setVisibility(View.VISIBLE);
        ClientTokenService.getInstance().generateToken(login, new ClientTokenService.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(String token) {
                if (token != null) {
                    if (userAccount.assignToken(token)) {
                        requestCustomerInfo(token);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                container_float_loading.setVisibility(View.GONE);
                Snackbar.make(parentPanel, "Logged fail: " + ExceptionUtils.translateExceptionMessage(e), Snackbar.LENGTH_LONG).show();
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
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_OK, returnIntent);
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
                Snackbar.make(parentPanel, "You logged fail: " + ExceptionUtils.translateExceptionMessage(e), Snackbar.LENGTH_LONG).show();
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
}
