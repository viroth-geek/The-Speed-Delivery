package com.planb.thespeed.fragment.page;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.planb.thespeed.R;
import com.planb.thespeed.activity.RegisterLocationActivity;
import com.planb.thespeed.adapter.AddressListRecyclerAdapter;
import com.planb.thespeed.constant.ConstantValue;
import com.planb.thespeed.model.Address;
import com.planb.thespeed.server.DatabaseHelper;
import com.planb.thespeed.service.datahelper.datasource.offine.address.FetchAddressDAO;
import com.planb.thespeed.util.LoggerHelper;

import java.util.List;

/**
 * @author channarith.bong
 */
public class DeliveryAddressFragment extends Fragment {

    private RecyclerView list_address;

    private FetchAddressDAO db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delivery_address, container, false);

        FloatingActionButton floatingActionButton = view.findViewById(R.id.fab);
        list_address = view.findViewById(R.id.list_address);
        list_address.setLayoutManager(new LinearLayoutManager(getActivity()));
        list_address.setHasFixedSize(true);
        floatingActionButton.setOnClickListener(v -> showMap());
        checkDB();
        bindData();
        return view;
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
        AddressListRecyclerAdapter recyclerAdapter = new AddressListRecyclerAdapter(getContext(), getListAddress(), db);
        list_address.setAdapter(recyclerAdapter);
    }

    /**
     * Get list address
     */
    private List<Address> getListAddress() {
        List<Address> addressList = null;
        try {
            addressList = db.getListAddress();
        } catch (Exception e) {
            LoggerHelper.showErrorLog("Local Address List Error : " + e.getMessage());
        }
        return addressList;
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
