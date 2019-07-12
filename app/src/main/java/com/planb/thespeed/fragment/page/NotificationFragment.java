package com.planb.thespeed.fragment.page;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.planb.thespeed.R;
import com.planb.thespeed.adapter.NotificationRecyclerAdapter;
import com.planb.thespeed.model.Notification;
import com.planb.thespeed.server.DatabaseHelper;
import com.planb.thespeed.service.datahelper.datasource.offine.localnotification.FetchNotificationDAO;
import com.planb.thespeed.util.LoggerHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author channarith.bong
 */
public class NotificationFragment extends Fragment {

    private RelativeLayout progressBar;
    private RecyclerView list_notification;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notificaton, container, false);
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
        try {
            FetchNotificationDAO db = new FetchNotificationDAO(DatabaseHelper.getInstance(getActivity()).getDatabase());
            notificationList = db.getListNotification();
        } catch (Exception e) {
            LoggerHelper.showErrorLog("Local Address List Error : " + e.getMessage());
        }
        return notificationList;
    }
}
