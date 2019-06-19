package com.iota.eshopping.fragment.page;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.iota.eshopping.R;
import com.iota.eshopping.activity.RegisterLocationActivity;
import com.iota.eshopping.adapter.AddressListRecyclerAdapter;
import com.iota.eshopping.constant.ConstantValue;
import com.iota.eshopping.model.Address;
import com.iota.eshopping.model.Customer;
import com.iota.eshopping.model.singleton.Singleton;
import com.iota.eshopping.server.DatabaseHelper;
import com.iota.eshopping.service.base.InvokeOnCompleteAsync;
import com.iota.eshopping.service.datahelper.datasource.offine.address.FetchAddressDAO;
import com.iota.eshopping.service.datahelper.datasource.online.FetchAddressList;
import com.iota.eshopping.util.LoggerHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author channarith.bong
 * @author viroth.ty
 */
public class DeliveryAddressFragment extends Fragment{

    private RecyclerView list_address;
    private FetchAddressDAO db;

    private FrameLayout flLoadingContainer;
    private ProgressBar pbLoading;
    private TextView tvLoadingLabel;

    private List<Address> listAddress = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delivery_address, container, false);

        Log.d("ooooo", "here onCreateView");

        FloatingActionButton floatingActionButton = view.findViewById(R.id.fab);
        list_address = view.findViewById(R.id.list_address);
        list_address.setLayoutManager(new LinearLayoutManager(getActivity()));
        list_address.setHasFixedSize(true);

        flLoadingContainer = view.findViewById(R.id.container_float_loading);
        pbLoading = view.findViewById(R.id.delivery_address_loading);
        tvLoadingLabel = view.findViewById(R.id.txt_container_loading_label);

        checkDB();
        getListAddress();
        bindData();

        floatingActionButton.setOnClickListener(v -> showMap());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkAddressList();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ConstantValue.INTENT_ACTIVITY_TAG_CODE) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    getAddressFromMap(data);
                    break;
                case Activity.RESULT_CANCELED:
                    break;
            }
        }
    }

    /**
     * Get Address form MAP
     */
    private void getAddressFromMap(Intent data) {
        android.location.Address address = data.getParcelableExtra(ConstantValue.ADDRESS);
        if (address != null) {
            Location location = new Location(ConstantValue.LOCATION);
            location.setLatitude(address.getLatitude());
            location.setLongitude(address.getLongitude());
            bindData();
        }
    }

    /**
     * Check SQLite database
     */
    private void checkDB() {
        try {
            db = new FetchAddressDAO(DatabaseHelper.getInstance(getActivity()).getDatabase());
        } catch (Exception e) {
            LoggerHelper.showErrorLog("Address Table Error " + e.getMessage());
        }
    }

    /**
     * fetch address list from server
     */
    private void checkAddressList(){
        new FetchAddressList(Singleton.userId, new InvokeOnCompleteAsync<Customer>() {
            @Override
            public void onComplete(Customer data) {
                if (data.getAddresses().size() > 0) {
                    flLoadingContainer.setVisibility(View.GONE);
                    AddressListRecyclerAdapter recyclerAdapter = new AddressListRecyclerAdapter(getContext(), data.getAddresses(), db);
                    list_address.setAdapter(recyclerAdapter);
                } else {
                    pbLoading.setVisibility(View.GONE);
                    tvLoadingLabel.setText(R.string.empty_delivery_address);
                }

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
            }
        });
    }

//    /**
//     * Convert form Google Address to Project Address
//     *
//     * @param address
//     * @return
//     */
//    private com.iota.eshopping.model.Address addToLocalAddress(android.location.Address address) {
//        com.iota.eshopping.model.Address localAddress =
//                new com.iota.eshopping.model.Address();
//
//        // Postcode cannot or rarely get from Google Map, so set a default value if it NULL
//        String PostCode = address.getPostalCode() == null ? ConstantValue.POST_CODE : address.getPostalCode();
//
//        List<String> streets = new ArrayList<>();
//        streets.add(address.getAddressLine(0));
//        localAddress.setAddressLine(address.getAddressLine(0));
//        localAddress.setCity(address.getLocality());
//        localAddress.setStreet(streets);
//        localAddress.setPostcode(PostCode);
//        localAddress.setCountryName(address.getCountryName());
//        localAddress.setCountryCode(address.getCountryCode());
//        localAddress.setLatitude(address.getLatitude());
//        localAddress.setLongitude(address.getLongitude());
//        return localAddress;
//    }

    /**
     * Get data from database and server
     */
    private void bindData() {
        AddressListRecyclerAdapter recyclerAdapter = new AddressListRecyclerAdapter(getContext(), listAddress, db);
        list_address.setAdapter(recyclerAdapter);
    }

    /**
     * Get list address
     */
    private void getListAddress() {
        List<Address> addressList = null;
        try {
            addressList = db.getListAddress();
            if (addressList.size() > 0) {
                flLoadingContainer.setVisibility(View.GONE);
            } else {
                flLoadingContainer.setVisibility(View.VISIBLE);
                pbLoading.setVisibility(View.GONE);
                tvLoadingLabel.setText("Empty address list");
            }
        } catch (Exception e) {
            LoggerHelper.showErrorLog("Local Address List Error : " + e.getMessage());
        }
        listAddress = addressList;
    }

    /**
     * Show map for register location
     */
    private void showMap() {
        Intent intent = new Intent(getActivity(), RegisterLocationActivity.class);
        intent.putExtra(ConstantValue.SAVE_ADDRESS, true);
        startActivityForResult(intent, ConstantValue.INTENT_ACTIVITY_TAG_CODE);
    }

}
