package com.iota.eshopping.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iota.eshopping.R;
import com.iota.eshopping.constant.ConstantValue;
import com.iota.eshopping.event.ISaveAddress;
import com.iota.eshopping.fragment.page.DeliveryAddressFragment;
import com.iota.eshopping.model.Customer;
import com.iota.eshopping.model.modelForView.Address;
import com.iota.eshopping.model.modelForView.CreateAddress;
import com.iota.eshopping.security.UserAccount;
import com.iota.eshopping.server.DatabaseHelper;
import com.iota.eshopping.service.datahelper.datasource.offine.address.FetchAddressDAO;
import com.iota.eshopping.service.datahelper.datasource.online.AddNewAddress;
import com.iota.eshopping.service.datahelper.datasource.online.UpdateAddress;
import com.iota.eshopping.util.PhoneNumberField;

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

    private com.iota.eshopping.model.Address address;

    private Customer customer;

    private FetchAddressDAO db;

    private Boolean isEdit = false;

    private ISaveAddress iSaveAddress;

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof DeliveryAddressFragment) {
            iSaveAddress = (ISaveAddress) fragment;
        }
    }


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

        db = new FetchAddressDAO(DatabaseHelper.getInstance(this).getDatabase());

        bindData();

        btn_save.setOnClickListener(view -> {

            btn_save.setVisibility(View.GONE);
            loadingLayout.setVisibility(View.VISIBLE);
            if (isEdit) {
                updateAddress(address);
                db.updateAddress(address);
            } else {
                if (!txt_first_name.getText().toString().equals("") && !txt_last_name.getText().toString().equals("") && !txt_phone_number.getText().toString().equals("")) {
                    saveAddress(address);
                } else {
                    btn_save.setVisibility(View.VISIBLE);
                    loadingLayout.setVisibility(View.GONE);
                    Toast.makeText(this, "Please input all requirement.", Toast.LENGTH_LONG).show();
                }
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
        } else if (item.getItemId() == R.id.action_show_map) {
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
                address = (com.iota.eshopping.model.Address) data.getSerializableExtra(ConstantValue.ADDRESS);
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

        address = (com.iota.eshopping.model.Address) getIntent().getSerializableExtra(ConstantValue.ADDRESS);

        if (address.getAddressLine() != null) {
            String[] addressStreet = address.getAddressLine().split(" / ");
            address.setStreet(Arrays.asList(addressStreet));
        }

        if (!address.getStreet().isEmpty() && address.getStreet().size() >= 2) {
            txt_street.setText(address.getStreet().get(0));
            txtStreet1.setText(address.getStreet().get(1));
        } else {
            txt_street.setText(address.getAddressLine());
        }

        txt_first_name.setText(address.getFirstname());
        txt_last_name.setText(address.getLastname());
        txt_phone_number.setText(address.getTelephone());
        chkDefaultShipping.setChecked(address.getDefaultShipping() != null ? address.getDefaultShipping() : false);
        chkDefaultBilling.setChecked(address.getDefaultBilling() != null ? address.getDefaultBilling() : false);

        txt_city.setText(address.getCity());
        txt_province.setText(address.getCity());
        txt_country.setText(address.getCountryName() != null ? address.getCountryName() : "Cambodia");

        if (isEdit) {
            toolbar.setTitle("Update Address");
            btn_save.setText(R.string.btn_title_update);
        }

    }

    /**
     * @param address com.iota.eshopping.model.Address
     */
    private void saveAddress(com.iota.eshopping.model.Address address) {

        address.setStreet(Arrays.asList(address.getAddressLine(), txtStreet1.getText().toString()));
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

                if (addresses.size() > 0) {
                    db.insert(address);
                }
                setResult(ConstantValue.HOME_CALLING_CODE);

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
     * @param address com.iota.eshopping.model.Address
     */
    private void updateAddress(com.iota.eshopping.model.Address address) {

        address.setCustomerId(customer.getId());
        address.setFirstname(txt_first_name.getText().toString());
        address.setLastname(txt_last_name.getText().toString());
        address.setTelephone(txt_phone_number.getText().toString());
        address.setDefaultBilling(chkDefaultBilling.isChecked());
        address.setDefaultShipping(chkDefaultBilling.isChecked());

        new UpdateAddress(prepareData(address), new UpdateAddress.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(List<Address> addresses) {
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
     * @param address com.iota.eshopping.model.Address
     * @return CreateAddress
     */
    private CreateAddress prepareData(com.iota.eshopping.model.Address address) {
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

}
