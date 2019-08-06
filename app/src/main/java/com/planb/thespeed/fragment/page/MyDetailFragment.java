package com.planb.thespeed.fragment.page;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.planb.thespeed.R;
import com.planb.thespeed.activity.BaseActivity;
import com.planb.thespeed.model.Customer;
import com.planb.thespeed.model.UserSecure;
import com.planb.thespeed.security.KeyManagement;
import com.planb.thespeed.security.UserAccount;
import com.planb.thespeed.service.datahelper.datasource.online.FetchCustomer;
import com.planb.thespeed.util.LoggerHelper;

/**
 * @author channarith.bong
 */
public class MyDetailFragment extends Fragment implements View.OnClickListener {

    private EditText edt_first_name, edt_last_name;
    private TextView txt_user_detail;
    private View container_button;
    private Button btn_save, btn_cancel;
    private Button btnEditUpdate;
    private UserAccount userAccount;
    private View parentPanel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_detail, container, false);
        parentPanel = view.findViewById(R.id.parentPanel);
        edt_first_name = view.findViewById(R.id.edt_first_name);
        edt_last_name = view.findViewById(R.id.edt_last_name);
        txt_user_detail = view.findViewById(R.id.txt_user_detail);
        container_button = view.findViewById(R.id.container_button);
        btn_save = view.findViewById(R.id.btn_save);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_save.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btnEditUpdate = view.findViewById(R.id.btn_edit_update);
        btnEditUpdate.setOnClickListener(this);
        setHasOptionsMenu(true);

        Toast.makeText(getContext(), "Please click button Edit to update.", Toast.LENGTH_LONG).show();
        setUserInfo();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_my_detail, menu);
//        menu.findItem(R.id.action_edit).setVisible(true);
//        menu.findItem(R.id.action_done).setVisible(false);
//        menu.findItem(R.id.action_search).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.action_edit == item.getItemId()) {
            container_button.setVisibility(View.VISIBLE);
            edt_first_name.setEnabled(true);
            edt_last_name.setEnabled(true);
        }
//        else if (R.id.action_done == item.getItemId()) {
//
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (btn_save.equals(view)) {
            updateUserDetail();
        } else if (btn_cancel.equals(view)) {
            edt_first_name.setEnabled(false);
            edt_last_name.setEnabled(false);
        } else if (btnEditUpdate.equals(view)) {
            if (btnEditUpdate.getText().equals("Edit")) {
                btnEditUpdate.setText("Update");
                edt_first_name.setEnabled(true);
                edt_last_name.setEnabled(true);
            } else if (btnEditUpdate.getText().equals("Update")) {
                if (!edt_first_name.getText().toString().equals("") && !edt_last_name.getText().toString().equals("")) {
                    updateUserDetail();
                    btnEditUpdate.setText("Edit");
                } else {
                    edt_first_name.setError("Enter First name");
                    edt_last_name.setError("Enter Last name");
                    edt_first_name.setText("");
                    edt_last_name.setText("");
                    edt_first_name.requestFocus();
                }

            }
        }
        container_button.setVisibility(View.GONE);
    }

    /**
     *
     */
    private void updateUserDetail() {
        userAccount = new UserAccount(getActivity());
        UserSecure userSecure = new UserSecure();
        Customer customer = userAccount.getCustomer();
        String token = KeyManagement.getInstance().getTokenAdmin();
//        String token = userAccount.getCustomerToken();
        customer.setFirstname(edt_first_name.getText().toString());
        customer.setLastname(edt_last_name.getText().toString());
        customer.setRpToken(null);
        userSecure.setCustomer(customer);
        requestUpdate(userSecure, token, userAccount.getCustomerToken());
    }

    /**
     * Get user information
     */
    private void setUserInfo() {
        UserAccount account = new UserAccount(getActivity());
        Customer customer = account.getCustomer();
        edt_first_name.setText(customer.getFirstname());
        edt_last_name.setText(customer.getLastname());
        String info = "Your email account is " + customer.getEmail() + ".";
        txt_user_detail.setText(info);
    }

    /**
     *
     */
    private void requestUpdate(final UserSecure user, final String token, final String newToken) {
        edt_first_name.setEnabled(false);
        edt_last_name.setEnabled(false);

        new FetchCustomer(user, token, new FetchCustomer.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(Customer customer) {
                if (customer != null) {
                    Log.d("ooooo", newToken);
                    customer.setRpToken(newToken);
                    if (userAccount.insertCustomer(customer)) {
                        edt_first_name.setText(customer.getFirstname());
                        edt_last_name.setText(customer.getLastname());
                        Snackbar.make(parentPanel, "Update success!", Snackbar.LENGTH_LONG).show();
                        Log.d("ooooo", customer.getRpToken());
                        Intent intent = new Intent(getActivity(), BaseActivity.class);
                        startActivity(intent);
                    } else {
                        Snackbar.make(parentPanel, "Sorry, please try again.", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(parentPanel, "Sorry, please try again.", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(Throwable e) {
                Snackbar.make(parentPanel, "You update fail!", Snackbar.LENGTH_LONG).show();
                LoggerHelper.showErrorLog(" Update Error " + e);
            }
        });
    }

}
