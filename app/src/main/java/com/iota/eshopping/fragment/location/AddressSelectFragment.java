package com.iota.eshopping.fragment.location;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.iota.eshopping.R;
import com.iota.eshopping.adapter.AddressListRecyclerAdapter;
import com.iota.eshopping.model.Address;
import com.iota.eshopping.server.DatabaseHelper;
import com.iota.eshopping.service.datahelper.datasource.offine.address.FetchAddressDAO;
import com.iota.eshopping.util.LoggerHelper;
import com.iota.eshopping.util.preference.LocationPreference;

import java.util.List;

/**
 * @author channarith.bong
 */

public class AddressSelectFragment extends DialogFragment implements View.OnClickListener, AddressListRecyclerAdapter.OnChangeAddress {

    private RecyclerView list_address;

    private FetchAddressDAO db;
    private OnChangeAddress onChangeAddress;

    private Button btn_update_current_address;

    private Boolean isGoToMap = false;

    /**
     *
     */
    public AddressSelectFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.NkrDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.view_address_selection, container, false);
        list_address = view.findViewById(R.id.list_address);
        btn_update_current_address = view.findViewById(R.id.btn_update_current_address);
        list_address.setLayoutManager(new LinearLayoutManager(getActivity()));
        list_address.setHasFixedSize(true);
        view.findViewById(R.id.fragment_outside_dialog).setOnClickListener(this);
        btn_update_current_address.setOnClickListener(this);

        bindData();
        return view;
    }

    @Override
    public void onClick(View view) {
        if (btn_update_current_address.equals(view)) {
            isGoToMap = true;
            onChangeAddress.onIsRequestMap(true);
            dismiss();
        }
        else if (view.getId() == R.id.fragment_outside_dialog) {
            dismiss();
        }
    }

    @Override
    public void onAddressSelect(Address address) {
        if (onChangeAddress != null) {
            LocationPreference.saveLocation(getActivity(), address);
            onChangeAddress.onAddressSelect(address);
            onChangeAddress.onIsRequestMap(false);
            dismiss();
        }
    }

    /**
     * Set data in to view
     */
    private void bindData() {
        if (checkDB()) {
            AddressListRecyclerAdapter recyclerAdapter = new AddressListRecyclerAdapter(getContext(), getListAddress(), this);
            list_address.setAdapter(recyclerAdapter);
        }
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
     * Check SQLite database
     */
    private boolean checkDB() {
        try {
            db = new FetchAddressDAO(DatabaseHelper.getInstance(getActivity()).getDatabase());
            LoggerHelper.showDebugLog("Address Table Check OK!");
            return true;
        } catch (Exception e) {
            LoggerHelper.showErrorLog("Address Table Error " + e.getMessage());
            return false;
        }
    }

    /**
     * @param onChangeAddress OnChangeAddress
     */
    public void setOnChangeAddress(OnChangeAddress onChangeAddress) {
        this.onChangeAddress = onChangeAddress;
    }

    /**
     *
     */
    public interface OnChangeAddress {
        void onAddressSelect(Address address);
        void onIsRequestMap(boolean isRequest);
    }

    @Override
    public void onDetach() {
        if (LocationPreference.getLocation(getContext()) == null && !isGoToMap) {
            Toast.makeText(getContext(), "Unable to load nearby store. Please select location to load nearby store.", Toast.LENGTH_SHORT).show();
        }
        isGoToMap = false;
        super.onDetach();
    }
}
