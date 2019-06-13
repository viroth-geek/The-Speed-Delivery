package com.iota.eshopping.fragment.page;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iota.eshopping.R;
import com.iota.eshopping.adapter.NotificationRecyclerAdapter;
import com.iota.eshopping.constant.ApplicationConfiguration;
import com.iota.eshopping.constant.ConstantValue;
import com.iota.eshopping.model.Notification;
import com.iota.eshopping.server.DatabaseHelper;
import com.iota.eshopping.service.datahelper.datasource.offine.localnotification.FetchNotificationDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author channarith.bong
 */
public class NotificationFragment extends Fragment {

    private RelativeLayout progressBar;
    private RecyclerView list_notification;

    private FrameLayout flLoadingContainer;
    private ProgressBar pbLoading;
    private TextView tvLoadingLabel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notificaton, container, false);


        flLoadingContainer = view.findViewById(R.id.notification_container_float_loading);
        pbLoading = view.findViewById(R.id.notification_delivery_address_loading);
        tvLoadingLabel = view.findViewById(R.id.tv_notification_label);

        progressBar = view.findViewById(R.id.loading_progress_bar);
        list_notification = view.findViewById(R.id.list_notification);
        list_notification.setHasFixedSize(true);
        list_notification.setLayoutManager(new LinearLayoutManager(getActivity()));
        bindData();


        return view;
    }

    /**
     * Get data from database and server
     */
    private void bindData() {
        NotificationRecyclerAdapter recyclerAdapter = new NotificationRecyclerAdapter(getContext(), getListNotification());
        recyclerAdapter.setProgressBar(progressBar);
        list_notification.setAdapter(recyclerAdapter);
    }

    /**
     * Get list address
     */
    private List<Notification> getListNotification() {
        List<Notification> notificationList = new ArrayList<>();
        FetchNotificationDAO db = new FetchNotificationDAO(DatabaseHelper.getInstance(getActivity()).getDatabase());

        Log.d(ConstantValue.TAG_LOG, "data is " + db.getListNotification().size());

        if (db.getListNotification().size() > 0) {
            flLoadingContainer.setVisibility(View.GONE);
            notificationList = db.getListNotification();
            return notificationList;

        } else {
            pbLoading.setVisibility(View.GONE);
            tvLoadingLabel.setText(R.string.empty_notification);
        }

        return notificationList;
    }
}
