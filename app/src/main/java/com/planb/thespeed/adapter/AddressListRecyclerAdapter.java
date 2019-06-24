package com.planb.thespeed.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.planb.thespeed.R;
import com.planb.thespeed.activity.AddAddressActivity;
import com.planb.thespeed.constant.ConstantValue;
import com.planb.thespeed.model.Address;
import com.planb.thespeed.service.datahelper.datasource.offine.address.FetchAddressDAO;
import com.planb.thespeed.service.datahelper.datasource.online.DeleteAddress;
import com.planb.thespeed.util.LoggerHelper;

import java.util.List;

/**
 * @author channarith.bong
 */
public class AddressListRecyclerAdapter extends RecyclerView.Adapter<AddressListRecyclerAdapter.ViewHolder> {

    private List<Address> addressList;
    private FetchAddressDAO db;
    private OnChangeAddress onChangeAddress;
    private Context mContext;

    /**
     * @param context         Context
     * @param addressList     list of Address
     * @param onChangeAddress OnChangeAddress
     */
    public AddressListRecyclerAdapter(Context context, List<Address> addressList, OnChangeAddress onChangeAddress) {
        this.mContext = context;
        this.addressList = addressList;
        this.onChangeAddress = onChangeAddress;
    }

    /**
     * @param addressList list of addresses
     */
    public AddressListRecyclerAdapter(Context context, List<Address> addressList, FetchAddressDAO db) {
        this.mContext = context;
        this.addressList = addressList;
        this.db = db;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        if (inflater != null) {
            view = inflater.inflate(R.layout.view_address_list, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LoggerHelper.showDebugLog("Position " + position);
        Address address = addressList.get(position);

        String addressLine = "";
        if (address.getStreet() != null && !address.getStreet().isEmpty()) {
            if (address.getStreet() != null && !address.getStreet().isEmpty()) {

                addressLine = address.getStreet().get(0);
            }
        } else if (address.getAddressLine() != null) {
            addressLine = address.getAddressLine();
        }

        holder.txt_address_nick_name.setText(address.getCity());
        holder.txt_address_line.setText(addressLine);
        holder.txt_address_postcode.setText(address.getPostcode());

    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public void clear() {
        addressList.clear();
    }

    /**
     * @param position int
     */
    private void removeAt(int position) {
        Long id = addressList.get(position).getId();
        String addressLine = addressList.get(position).getAddressLine();
        addressList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, addressList.size());
        if (db.delete(id)) {
            LoggerHelper.showDebugLog("DB-Delete Address ID : " + id + " -- " + addressLine);
        }
    }

    /**
     * @param addressId Long
     */
    private void deleteAddressFromServer(Long addressId) {
        new DeleteAddress(addressId, new DeleteAddress.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(Integer result) {
                Toast.makeText(mContext, "Deleted successfully!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(mContext, "Failed to delete!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Alert confirm box
     *
     * @param context  Context
     * @param position int
     */
    private void showConfirm(Context context, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogDanger);
        builder.setTitle(R.string.app_name);
        builder.setMessage("Do you want to delete this address?");
        builder.setPositiveButton("Yes", (dialog, id) -> {
//            Toast.makeText(context, addressList.get(position).getId().toString(), Toast.LENGTH_SHORT).show();
            deleteAddressFromServer(addressList.get(position).getId());
            removeAt(position);
            dialog.dismiss();
        });
        builder.setNegativeButton("No", (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     *
     */
    public interface OnChangeAddress {
        void onAddressSelect(Address address);
    }

    /**
     * Class for response view in list
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_address_nick_name;
        TextView txt_address_line;
        TextView txt_address_postcode;
        ImageView img_delete;

        /**
         * @param itemView View
         */
        public ViewHolder(View itemView) {
            super(itemView);

            txt_address_nick_name = itemView.findViewById(R.id.txt_address_nick_name);
            txt_address_line = itemView.findViewById(R.id.txt_address_line);
            txt_address_postcode = itemView.findViewById(R.id.txt_address_postcode);
            img_delete = itemView.findViewById(R.id.img_delete);
            if (db != null) {
                img_delete.setVisibility(View.VISIBLE);
                img_delete.setOnClickListener(v -> {
                    try {
                        showConfirm(itemView.getContext(), getAdapterPosition());
                    } catch (Exception e) {
                        LoggerHelper.showErrorLog("Address Cannot Delete : " + e.getMessage());
                    }
                });
            }

            itemView.setOnClickListener(view -> {
                Log.d(ConstantValue.TAG_LOG, "latitude" + addressList.get(getAdapterPosition()).getLatitude());
                Log.d(ConstantValue.TAG_LOG, "langitude" + addressList.get(getAdapterPosition()).getLatitude());
                Intent intent = new Intent(itemView.getContext(), AddAddressActivity.class);
                intent.putExtra(ConstantValue.ADDRESS, addressList.get(getAdapterPosition()));
                intent.putExtra(ConstantValue.EDIT_ADDRESS, true);
                itemView.getContext().startActivity(intent);
            });

            if (onChangeAddress != null) {
                itemView.setOnClickListener(view -> onChangeAddress.onAddressSelect(addressList.get(getAdapterPosition())));
            }
        }
    }
}
