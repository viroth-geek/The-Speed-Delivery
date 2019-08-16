package com.planb.thespeed.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.planb.thespeed.R;
import com.planb.thespeed.constant.ConstantValue;
import com.planb.thespeed.model.AddressByStreetString;
import com.planb.thespeed.model.Customer;
import com.planb.thespeed.model.modelForView.Address;
import com.planb.thespeed.model.modelForView.CreateAddress;
import com.planb.thespeed.model.modelForView.CreateAddressByStreetString;
import com.planb.thespeed.security.UserAccount;
import com.planb.thespeed.server.DatabaseHelper;
import com.planb.thespeed.service.datahelper.datasource.offine.address.FetchAddressDAO;
import com.planb.thespeed.service.datahelper.datasource.online.AddNewAddress;
import com.planb.thespeed.service.datahelper.datasource.online.AddNewAddressByStreetString;
import com.planb.thespeed.service.datahelper.datasource.online.UpdateAddressByStreetString;
import com.planb.thespeed.util.PhoneNumberField;

import java.util.Arrays;
import java.util.List;

/**
 * @author yeakleang.ay
 */
public class AddAddressActivity extends AppCompatActivity {

    private TextView txt_first_name;
    private TextView txt_last_name;
    private PhoneNumberField txt_phone_number;
    private TextView txt_street;
    private EditText txtStreet1;
    private TextView txt_city;
    private TextView txt_province;
    private TextView txt_country;
    private Button btn_save;
    private CheckBox chkDefaultBilling;
    private CheckBox chkDefaultShipping;
    private Toolbar toolbar;
    private FrameLayout loadingLayout;
    private com.planb.thespeed.model.Address address;
    private com.planb.thespeed.model.AddressByStreetString addressByStreetString;
    private com.planb.thespeed.model.AddressByStreetString addressTemp;

    private Customer customer;
    private FetchAddressDAO db;
    private Boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_address);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (isEdit) {
                getSupportActionBar().setTitle("Edit Address");
            } else {
                getSupportActionBar().setTitle("Add New Address");
            }
        }
        loadingLayout = findViewById(R.id.container_float_loading);
        txt_first_name = findViewById(R.id.txt_first_name);
        txt_last_name = findViewById(R.id.txt_last_name);
        txt_phone_number = findViewById(R.id.txt_phone_number);
        txt_street = findViewById(R.id.txt_street);
        txtStreet1 = findViewById(R.id.txt_street_1);
        txt_city = findViewById(R.id.txt_city);
        txt_province = findViewById(R.id.txt_province);
        txt_country = findViewById(R.id.txt_country);
        btn_save = findViewById(R.id.btn_save_address);
        chkDefaultBilling = findViewById(R.id.chk_default_billing);
        chkDefaultShipping = findViewById(R.id.chk_default_shipping);
        addressTemp = new AddressByStreetString();

        db = new FetchAddressDAO(DatabaseHelper.getInstance(this).getDatabase());

        bindData();
        btn_save.setOnClickListener(view -> {
            btn_save.setVisibility(View.GONE);
            loadingLayout.setVisibility(View.VISIBLE);
            if (isEdit) {
                Log.d("onCreate:", "onCreate: "+ "true");
                updateAddress(addressByStreetString);
                db.updateAddress(address);
            }else {
                saveAddressByStreetString(addressByStreetString);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isEdit) {
            getMenuInflater().inflate(R.menu.menu_map, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        else if (item.getItemId() == R.id.action_show_map) {
            Intent intent = new Intent(this, RegisterLocationActivity.class);
            intent.putExtra(ConstantValue.ADDRESS, address);
            intent.putExtra(ConstantValue.EDIT_ADDRESS, true);
            startActivityForResult(intent, ConstantValue.GET_EDITED_ADDRESS);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ConstantValue.GET_EDITED_ADDRESS) {
            if (data != null && data.hasExtra(ConstantValue.ADDRESS)) {
                address = (com.planb.thespeed.model.Address) data.getSerializableExtra(ConstantValue.ADDRESS);
//                addressByStreetString = (com.planb.thespeed.model.AddressByStreetString) data.getSerializableExtra(ConstantValue.ADDRESS_BY_STREET_STRING);
                addressTemp = (com.planb.thespeed.model.AddressByStreetString) data.getSerializableExtra(ConstantValue.ADDRESS_BY_STREET_STRING);

                addressByStreetString.setLatitude(addressTemp.getLatitude());
                addressByStreetString.setLongitude(addressTemp.getLongitude());
                addressByStreetString.setCity(addressTemp.getCity());
                addressByStreetString.setCountryCode(addressTemp.getCountryCode());
                addressByStreetString.setCountryName(addressTemp.getCountryName());
                addressByStreetString.setAddressLine(addressTemp.getAddressLine());
                addressByStreetString.setStreet(addressTemp.getAddressLine());
                Log.d("onActivityResult:", "onActivityResult: " + addressByStreetString);
                Log.d("onActivityResult:", "onActivityResult: " + addressTemp);
                txt_street.setText(address.getAddressLine());
                txt_city.setText(address.getCity());
                txt_province.setText(address.getCity());
                txt_country.setText(address.getCountryName() != null ? address.getCountryName() : "Cambodia");
            }
        }
    }

    /**
     *
     */
    private void bindData() {
        isEdit = getIntent().getBooleanExtra(ConstantValue.EDIT_ADDRESS, false);
        UserAccount userAccount = new UserAccount(this);
        customer = userAccount.getCustomer();

        address = (com.planb.thespeed.model.Address) getIntent().getSerializableExtra(ConstantValue.ADDRESS);
        addressByStreetString = (com.planb.thespeed.model.AddressByStreetString) getIntent().getSerializableExtra(ConstantValue.ADDRESS_BY_STREET_STRING);

        Log.d("bindData:", "bindData: "+ addressByStreetString);
        if (isEdit){
            txt_street.setText(address.getStreet().get(0));
            txt_first_name.setText(address.getFirstname());
            txt_last_name.setText(address.getLastname());
            txt_phone_number.setText(address.getTelephone());
            chkDefaultShipping.setChecked(address.getDefaultShipping() != null ? address.getDefaultShipping() : false);
            chkDefaultBilling.setChecked(address.getDefaultBilling() != null ? address.getDefaultBilling() : false);
        } else {
            txt_first_name.setText(userAccount.getCustomer().getFirstname());
            txt_last_name.setText(userAccount.getCustomer().getLastname());
            txt_phone_number.setText(userAccount.getCustomer().getPhonenumber());
            Log.d("bindData:", "bindData: phone Number" + userAccount.getCustomer().getPhonenumber());
            txt_street.setText(address.getAddressLine());
        }
        txt_city.setText(address.getCity());
        txt_province.setText(address.getCity());
        txt_country.setText(address.getCountryName() != null ? address.getCountryName() : "Cambodia");

        if (isEdit) {
            toolbar.setTitle("Update Address");
            btn_save.setText(R.string.btn_title_update);
        }

    }

    /**
     * @param addressByStreetString com.iota.eshopping.model.AddressByStreetString
     */
    private void saveAddressByStreetString(AddressByStreetString addressByStreetString) {
        if (!txt_first_name.getText().toString().isEmpty() && !txt_last_name.getText().toString().isEmpty() && !txt_phone_number.getText().toString().isEmpty()){
            addressByStreetString.setStreet(txt_street.getText().toString());
            addressByStreetString.setCustomerId(customer.getId());
            addressByStreetString.setFirstname(txt_first_name.getText().toString());
            addressByStreetString.setLastname(txt_last_name.getText().toString());
            addressByStreetString.setTelephone(txt_phone_number.getText().toString());
            addressByStreetString.setDefaultBilling(chkDefaultBilling.isChecked());
            addressByStreetString.setDefaultShipping(chkDefaultBilling.isChecked());

//            Log.d("saveAddressByStreetString:" , "saveAddressByStreetString: " + addressByStreetString);
            new AddNewAddressByStreetString(prepareDataByStreetString(addressByStreetString), new AddNewAddressByStreetString.InvokeOnCompleteAsync() {
                @Override
                public void onComplete(List<com.planb.thespeed.model.modelForView.AddressByStreetString> addresses) {
                    loadingLayout.setVisibility(View.GONE);
                    btn_save.setVisibility(View.VISIBLE);
                    address.setStreet(Arrays.asList(address.getAddressLine()));
                    db.insertByStreetString((AddressByStreetString) addresses);
                    finish();
                }

                @Override
                public void onError(Throwable e) {
                    loadingLayout.setVisibility(View.GONE);
                    btn_save.setVisibility(View.VISIBLE);
                    address.setStreet(Arrays.asList(addressByStreetString.getAddressLine()));
                    db.insert(address);
                    finish();
                }
            });
        }else {
            if (txt_first_name.getText().toString().isEmpty()){
                txt_first_name.setError("First name require.");
            }
            if (txt_last_name.getText().toString().isEmpty()){
                txt_last_name.setError("Last name require.");
            }
            if (txt_phone_number.getText().toString().isEmpty()){
                txt_phone_number.setError("Phone number require.");
            }
            loadingLayout.setVisibility(View.GONE);
            btn_save.setVisibility(View.VISIBLE);
        }
    }

    /**
     *
     * @param address com.iota.eshopping.model.Address
     */
    private void saveAddress(com.planb.thespeed.model.Address address) {
        address.setStreet(Arrays.asList(address.getAddressLine()));
        address.setCustomerId(customer.getId());
        address.setFirstname(txt_first_name.getText().toString());
        address.setLastname(txt_last_name.getText().toString());
        address.setTelephone(txt_phone_number.getText().toString());
        address.setDefaultBilling(chkDefaultBilling.isChecked());
        address.setDefaultShipping(chkDefaultBilling.isChecked());

        new AddNewAddress(prepareData(address), new AddNewAddress.InvokeOnCompleteAsync() {

            @Override
            public void onComplete(List<Address> addresses) {

                Toast.makeText(AddAddressActivity.this, "Save Successfully!", Toast.LENGTH_SHORT).show();
                loadingLayout.setVisibility(View.GONE);
                btn_save.setVisibility(View.VISIBLE);
                db.insert(address);
                finish();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(AddAddressActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                loadingLayout.setVisibility(View.GONE);
                btn_save.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     *
     * @param address com.iota.eshopping.model.Address
     */
    private void updateAddress(com.planb.thespeed.model.AddressByStreetString address) {
        address.setCustomerId(customer.getId());
        address.setFirstname(txt_first_name.getText().toString());
        address.setLastname(txt_last_name.getText().toString());
        address.setTelephone(txt_phone_number.getText().toString());
        address.setDefaultBilling(chkDefaultBilling.isChecked());
        address.setDefaultShipping(chkDefaultBilling.isChecked());
//        Log.d("setCustomerId:", "setCustomerId: "+ address.getCustomerId());
//        Log.d("updateAddress:", "updateAddress: "+ prepareDataByStreetString(addressByStreetString));
        new UpdateAddressByStreetString(prepareDataByStreetString(addressByStreetString), new UpdateAddressByStreetString.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(List<com.planb.thespeed.model.modelForView.AddressByStreetString> addresses) {
                Toast.makeText(AddAddressActivity.this, "Update Successfully!", Toast.LENGTH_SHORT).show();
                loadingLayout.setVisibility(View.GONE);
                btn_save.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(AddAddressActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                loadingLayout.setVisibility(View.GONE);
                btn_save.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     *
     * @param address com.iota.eshopping.model.Address
     * @return CreateAddress
     */
    private CreateAddress prepareData(com.planb.thespeed.model.Address address) {
        CreateAddress createAddress = new CreateAddress();
        Address add = new Address();
        add.setAddressId(address.getId());
        add.setFirstName(address.getFirstname());
        add.setLastName(address.getLastname());
        add.setCustomerId(address.getCustomerId());
        add.setPostCode(address.getPostcode());
        add.setCity(address.getCity());
        add.setStreets(address.getStreet());
        add.setTelephone(address.getTelephone());
        add.setCompany("");
        add.setDefaultBilling(address.getDefaultBilling());
        add.setDefaultShipping(address.getDefaultShipping());
        add.setLatitude(address.getLatitude());
        add.setLongitude(address.getLongitude());
        createAddress.setAddress(add);
        return createAddress;
    }

    /**
     * @param address com.iota.eshopping.model.Address
     * @return CreateAddress
     */
    private CreateAddressByStreetString prepareDataByStreetString(AddressByStreetString address) {
        CreateAddressByStreetString createAddress = new CreateAddressByStreetString();
        com.planb.thespeed.model.modelForView.AddressByStreetString add = new com.planb.thespeed.model.modelForView.AddressByStreetString();
        add.setAddressId(address.getId());
        add.setFirstName(address.getFirstname());
        add.setLastName(address.getLastname());
        add.setCustomerId(address.getCustomerId());
        add.setPostCode(address.getPostcode());
        add.setCity(address.getCity());
        add.setStreet(address.getStreet());
        add.setTelephone(address.getTelephone());
        add.setCompany("");
        add.setDefaultBilling(address.getDefaultBilling());
        add.setDefaultShipping(address.getDefaultShipping());
        add.setLatitude(address.getLatitude());
        add.setLongitude(address.getLongitude());
        createAddress.setAddressByStreetString(add);
        return createAddress;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}