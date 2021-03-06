package com.planb.thespeed.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.planb.thespeed.fragment.page.AboutFragment;
import com.planb.thespeed.fragment.page.DeliveryAddressFragment;
import com.planb.thespeed.fragment.page.HomeFragment;
import com.planb.thespeed.fragment.page.MyDetailFragment;
import com.planb.thespeed.fragment.page.MyOrderFragment;
import com.planb.thespeed.fragment.page.NotificationFragment;
import com.planb.thespeed.fragment.page.PaymentMethodFragment;
import com.planb.thespeed.model.Address;
import com.planb.thespeed.model.CustomAttribute;
import com.planb.thespeed.model.Customer;
import com.planb.thespeed.model.UserPlayerId;
import com.planb.thespeed.model.enumeration.SocialType;
import com.planb.thespeed.model.form.FormSocialUser;
import com.planb.thespeed.model.form.SocialLoginForm;
import com.planb.thespeed.security.ForceUpdateChecker;
import com.planb.thespeed.security.UserAccount;
import com.planb.thespeed.server.DatabaseHelper;
import com.planb.thespeed.service.base.InvokeOnCompleteAsync;
import com.planb.thespeed.service.datahelper.datasource.offine.address.FetchAddressDAO;
import com.planb.thespeed.service.datahelper.datasource.online.FetchAddressList;
import com.planb.thespeed.service.datahelper.datasource.online.FetchCustomer;
import com.planb.thespeed.service.datahelper.datasource.online.SaveUserPlayerId;
import com.planb.thespeed.service.datahelper.datasource.online.SocialLoginService;
import com.planb.thespeed.service.location.LocationService;
import com.planb.thespeed.util.AlertUtils;
import com.planb.thespeed.util.ExceptionUtils;
import com.planb.thespeed.util.LoggerHelper;
import com.onesignal.OSPermissionSubscriptionState;
import com.onesignal.OneSignal;

import org.json.JSONException;

import java.util.Arrays;
import java.util.List;

/**
 * @author channarith.bong
 */
public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, ForceUpdateChecker.OnUpdateNeededListener {

    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    private Button btn_log_in;
    private Button btn_sign_up;
    private TextView txt_user_name;
    private TextView txt_user_email;
    private NavigationView navigationView;
    private MenuItem searchMenuItem;

    private Snackbar snackbar;

    private UserAccount userAccount;

    private CallbackManager callbackManager;

    private LoginButton facebookLoginButton;
    private Button btn_facebook_login;

    private GoogleSignInClient mGoogleSignInClient;
    //    private SignInButton googleSignInButton;
    private Button btn_google_login;

    private boolean isShowAds = true;
    private Customer customer;
    private FetchAddressDAO fetchAddressDAO;

    private String fragmentName;
    private HomeFragment homeFragment;

    private boolean isCanBroadCast = false;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fetchAddressDAO = new FetchAddressDAO(DatabaseHelper.getInstance(this).getDatabase());

        initToolbar();

        if (getIntent().hasExtra(ConstantValue.NOTIFICATION) && checkUserData()) {
            if (getIntent().hasExtra(ConstantValue.ITEMS)) {
                Intent intent = new Intent(this, MyOrderDetailActivity.class);
                intent.putExtra(ConstantValue.ITEMS, getIntent().getSerializableExtra(ConstantValue.ITEMS));
                intent.putExtra(ConstantValue.HOME_CALLING, true);
                startActivity(intent);
            }
            else {
                checkNewNotification();
            }
        } else {
            checkToHome();
        }

        configureFacebookLogin();
        configureGoogleSignIn();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (searchMenuItem != null) {
            searchMenuItem.setVisible(false);
        }
        switch (id) {
            case R.id.nav_find_product:
                checkToHome();
                break;
            case R.id.nav_order:
                checkToMyOrder();
                break;
            case R.id.nav_my_detail:
                checkToMyDetail();
                break;
            case R.id.nav_payment:
                checkPaymentMethod();
                break;
            case R.id.nav_notification:
                checkNewNotification();
                break;
            case R.id.nav_address:
                addMoreAddress();
                break;
            case R.id.nav_help:
                showAbout();
                break;
            case R.id.nav_log_out:
                checkToLogout();
                break;
            default:
                checkToHome();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isShowAds) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            searchMenuItem = menu.findItem(R.id.action_search);
            if (fragmentName == null || HomeFragment.class.getSimpleName().equals(fragmentName)) {
                searchMenuItem.setVisible(true);
            } else {
                searchMenuItem.setVisible(false);
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == android.R.id.home) {
            if (!isShowAds) {
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (isShowAds) {
            toggle.syncState();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        toolbar.setTitle(R.string.splash_screen_desc);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (HomeFragment.class.getSimpleName().equals(fragmentName)) {
            AlertUtils.showConfirmDialog(this, "Message", "Are you sure to exit?", "Yes", (dialogInterface, i) -> BaseActivity.super.onBackPressed());
        } else {
            checkToHome();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        drawViewSideMenu();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ForceUpdateChecker.with(this).onUpdateNeeded(this).check();

        if (isCanBroadCast) {
//            if (homeFragment != null) {
//                if (homeFragment.getProgressBar().getVisibility() == View.GONE) {
//                    homeFragment.getProgressBar().setVisibility(View.VISIBLE);
//                }
//            }
            this.startService(new Intent(this, LocationService.class));
            isCanBroadCast = false;
        }
    }

    /**
     * Prepare view for setup Toolbar
     */
    private void initToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            checkUserData();
            initDrawerView();
        }
    }

    /**
     * Init and redraw view drawer for user as guest and signed
     */
    private void initDrawerView() {
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu_drawer);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.inflateHeaderView(R.layout.nav_header_main_loged);
        navigationView.inflateHeaderView(R.layout.nav_header_main);
        txt_user_email = navigationView.getHeaderView(0).findViewById(R.id.txt_user_email);
        txt_user_name = navigationView.getHeaderView(0).findViewById(R.id.txt_user_name);
        btn_sign_up = navigationView.getHeaderView(1).findViewById(R.id.btn_sign_up);
        btn_log_in = navigationView.getHeaderView(1).findViewById(R.id.btn_log_in);
        drawViewSideMenu();
    }

    /**
     * Draw View in Side Menu
     */
    private void drawViewSideMenu() {
        if (checkUserData()) {
            navigationView.getHeaderView(0).setVisibility(View.VISIBLE);
            navigationView.getHeaderView(1).setVisibility(View.GONE);
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.menu_drawer);

            if (customer != null) {
                String name = customer.getFirstname() + " " + customer.getLastname();
                String mail = customer.getEmail();
                txt_user_name.setText(name);
                txt_user_email.setText(mail);

                saveUserPlayerId(prepareUserPlayerId(customer.getId().toString()));
                syncAddressList(customer.getId());
            }
        } else {
            navigationView.getHeaderView(0).setVisibility(View.GONE);
            navigationView.getHeaderView(1).setVisibility(View.VISIBLE);
            navigationView.getMenu().clear();

            btn_sign_up.setOnClickListener(v -> checkToSignup());
            btn_log_in.setOnClickListener(v -> checkToLogin());
        }
    }

    /**
     * save user player id
     * @see UserPlayerId
     * @param userPlayerId UserPlayerId
     */
    private void saveUserPlayerId(UserPlayerId userPlayerId) {
        new SaveUserPlayerId(userPlayerId, new SaveUserPlayerId.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(List<UserPlayerId> userPlayerIds) {
                LoggerHelper.showDebugLog("===> Save user player id successfully" + userPlayerId.toString());
            }

            @Override
            public void onError(Throwable e) {
                LoggerHelper.showErrorLog("Error 329: ", e);
            }
        });
    }

    /**
     * prepare for UserPlayerId
     * @param userId
     * @return
     */
    private UserPlayerId prepareUserPlayerId(String userId) {
        UserPlayerId userPlayerId = new UserPlayerId();
        OSPermissionSubscriptionState status = OneSignal.getPermissionSubscriptionState();

        LoggerHelper.showDebugLog("====> " + status.getSubscriptionStatus().getUserId());

        userPlayerId.setPlayerId(status.getSubscriptionStatus().getUserId());
        userPlayerId.setUserId(userId);
        userPlayerId.setDeviceType(0);
        return userPlayerId;
    }

    /**
     * Check if Ready Logged
     */
    private boolean checkUserData() {
        UserAccount userAccount = new UserAccount(this);
        customer = userAccount.getCustomer();
        return customer != null;
    }

    /**
     * Menu for Home Activity
     */
    private void checkToHome() {
        toolbar.setTitle(R.string.find_food);
        homeFragment = new HomeFragment();
        if (searchMenuItem != null) {
            searchMenuItem.setVisible(true);
        }
        displaySelectedFragment(homeFragment);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LoggerHelper.showDebugLog("Granted");
                    BaseActivity.this.requestGps();
                } else {
                    LoggerHelper.showDebugLog("Not Granted");
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        Toast.makeText(this, "To enable the function of this application please enable location permission of the application from the setting screen of the terminal.", Toast.LENGTH_SHORT).show();
                    } else {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                    }
                }
                break;
            }
        }
    }

    /**
     *
     */
    public void requestGps() {
        if (homeFragment.getLoadingProgressBar() != null) {
            homeFragment.getLoadingProgressBar().setVisibility(View.VISIBLE);
        }
        LocationManager manager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (manager != null) {
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                // Ask the user to enable GPS
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("The Speed");
                builder.setMessage("Would you like to enable GPS?");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(i);
                    isCanBroadCast = true;
                });
                builder.setNegativeButton("No", (dialog, which) -> homeFragment.getBtn_selected_location().performClick());
                builder.create().show();
            } else {
                this.startService(new Intent(this, LocationService.class));
                isCanBroadCast = false;
            }
        }
    }

    /**
     * Menu for My Order Activity
     */
    private void checkToMyOrder() {
        toolbar.setTitle(R.string.my_order);
        MyOrderFragment myOrderFragment = new MyOrderFragment();
        displaySelectedFragment(myOrderFragment);
    }

    /**
     * Menu for My Detail Activity
     */
    private void checkToMyDetail() {
        toolbar.setTitle(R.string.my_detail);
        MyDetailFragment myDetailFragment = new MyDetailFragment();
        displaySelectedFragment(myDetailFragment);
    }

    /**
     * Menu for LogOut Activity
     */
    private void checkToLogout() {
        logout();
        checkToHome();
        drawViewSideMenu();
    }

    /**
     * logout
     */
    private void logout() {
        UserAccount userAccount = new UserAccount(this);
        userAccount.deleteCustomer();
        fetchAddressDAO.deleteAll();

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
     * Menu for Login Activity
     */
    private void checkToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, ConstantValue.LOG_IN_CODE);
    }

    /**
     * Menu for Sign Up Activity
     */
    private void checkToSignup() {
        toolbar.setTitle(R.string.about);
        Intent intent = new Intent(this, SigninActivity.class);
        startActivityForResult(intent, ConstantValue.SIGN_UP_CODE);
    }

    /**
     * Menu for About Activity
     */
    private void showAbout() {
        toolbar.setTitle(R.string.about);
        AboutFragment aboutFragment = new AboutFragment();
        displaySelectedFragment(aboutFragment);
    }

    /**
     * Menu for Payment Method
     */
    private void checkPaymentMethod() {
        toolbar.setTitle(R.string.payment_method);
        PaymentMethodFragment paymentMethodFragment = new PaymentMethodFragment();
        displaySelectedFragment(paymentMethodFragment);
    }

    /**
     * Add more modelForView address
     */
    private void addMoreAddress() {
        toolbar.setTitle(R.string.deliver_address);
        DeliveryAddressFragment deliveryAddressFragment = new DeliveryAddressFragment();
        displaySelectedFragment(deliveryAddressFragment);
    }

    /**
     * Check for new notification
     */
    private void checkNewNotification() {
        toolbar.setTitle(R.string.notification);
        NotificationFragment notificationFragment = new NotificationFragment();
        displaySelectedFragment(notificationFragment);
    }

    /**
     * Loads the specified fragment to the frame
     *
     * @param fragment Fragment
     */
    private void displaySelectedFragment(Fragment fragment) {
//        if (fragmentName == null || !fragment.getClass().getSimpleName().equals(fragmentName)) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();
            fragmentName = fragment.getClass().getSimpleName();
//        }
    }

    /**
     * @param userId Long
     */
    private void syncAddressList(Long userId) {
        fetchAddressDAO.deleteAll();
        new FetchAddressList(userId, new InvokeOnCompleteAsync<Customer>() {
            @Override
            public void onComplete(Customer customer) {
                for (Address address : customer.getAddresses()) {
                    if (address.getCustomAttributes() != null) {
                        for (CustomAttribute customAttribute : address.getCustomAttributes()) {
                            if (ConstantValue.LONGITUDE.equals(customAttribute.getAttributeCode())) {
                                address.setLongitude(Double.valueOf(customAttribute.getValue().toString()));
                            }
                            if (ConstantValue.LATITUDE.equals(customAttribute.getAttributeCode())) {
                                address.setLatitude(Double.valueOf(customAttribute.getValue().toString()));
                            }
                            if (address.getLongitude() != null && address.getLatitude() != null) {
                                fetchAddressDAO.insert(address);
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
            Toast.makeText(this, "Unable to login with google account. Please try another way.", Toast.LENGTH_SHORT).show();
            LoggerHelper.showErrorLog("signInResult:failed code=", e);
        }
    }

    /**
     * configure facebook login
     */
    private void configureFacebookLogin() {
        callbackManager = CallbackManager.Factory.create();

        facebookLoginButton = navigationView.getHeaderView(1).findViewById(R.id.login_button);
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
                                    AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
                                    builder.setTitle("Message");
                                    builder.setMessage("Error while retrieve facebook data. Please sign up with your email.");
                                    builder.setPositiveButton("OK", (dialog, which) -> {
                                        Intent intent = new Intent(BaseActivity.this, SigninActivity.class);
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
                Toast.makeText(BaseActivity.this, "Failed login with facebook: " + exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                LoggerHelper.showErrorLog("Error: ", exception);
            }
        });

        btn_facebook_login = navigationView.getHeaderView(1).findViewById(R.id.btn_facebook_login);
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

        btn_google_login = navigationView.getHeaderView(1).findViewById(R.id.btn_google_login);
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
     * login with social account
     *
     * @param socialLoginForm SocialLoginForm
     */
    private void loginWithSocialAccount(SocialLoginForm socialLoginForm) {
        showSnackBarLoading();
        new SocialLoginService(socialLoginForm, new SocialLoginService.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(String token) {
                if (token != null) {
                    if (userAccount == null) {
                        userAccount = new UserAccount(BaseActivity.this);
                    }
                    if (userAccount.assignToken(token)) {
                        requestCustomerInfo(token);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                logout();
                LoggerHelper.showErrorLog("Login Page: ", e);
                Toast.makeText(BaseActivity.this, "Error: " + ExceptionUtils.translateExceptionMessage(e), Toast.LENGTH_SHORT).show();
                snackbar.dismiss();
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
                        Toast.makeText(BaseActivity.this, "Logged success!", Toast.LENGTH_SHORT).show();
                        drawViewSideMenu();

                    } else {
                        Toast.makeText(BaseActivity.this, "Sorry, please try again.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BaseActivity.this, "Sorry, please try again.", Toast.LENGTH_SHORT).show();
                }
                snackbar.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(BaseActivity.this, "You logged fail: " + ExceptionUtils.translateExceptionMessage(e), Toast.LENGTH_SHORT).show();
                LoggerHelper.showErrorLog("409, Base Activity: ", e);
                snackbar.dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (btn_facebook_login.equals(view)) {
            facebookLoginButton.performClick();
        } else if (btn_google_login.equals(view)) {
            signInWithGoogle();
        }
    }

    /**
     * show snack bar
     */
    private void showSnackBarLoading() {

        // Create the Snackbar
        snackbar = Snackbar.make(navigationView.getHeaderView(1), "", Snackbar.LENGTH_LONG);

        // Get the Snackbar's layout view
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();

        LayoutInflater inflater = LayoutInflater.from(this);

        // Inflate our custom view
        View snackView = inflater.inflate(R.layout.custom_snack_bar, null);

        //If the view is not covering the whole snackbar layout, add this line
        layout.setPadding(0, 0, 0, 0);

        // Add the view to the Snackbar's layout
        layout.addView(snackView, 0);
        // Show the Snackbar

        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);

        snackbar.show();
    }

    @Override
    public void onUpdateNeeded(String updateUrl) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("New version available")
                .setMessage("Please, update app to new version to continue.")
                .setPositiveButton("Update",
                        (dialog1, which) -> redirectStore(updateUrl))
                .setCancelable(false)
                .create();
        dialog.show();
    }

    /**
     * redirect to play store
     * @param updateUrl String
     */
    private void redirectStore(String updateUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
