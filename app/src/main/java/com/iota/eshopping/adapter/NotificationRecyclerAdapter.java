package com.iota.eshopping.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iota.eshopping.R;
import com.iota.eshopping.activity.MyOrderDetailActivity;
import com.iota.eshopping.constant.ConstantValue;
import com.iota.eshopping.constant.entity.NotificationDataKey;
import com.iota.eshopping.model.Notification;
import com.iota.eshopping.model.OrderDetail;
import com.iota.eshopping.security.UserAccount;
import com.iota.eshopping.service.datahelper.datasource.online.FetchOrderDetails;
import com.iota.eshopping.util.ImageViewUtil;
import com.iota.eshopping.util.LoggerHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by channarith.bong on 2/9/18.
 *
 * @author channarith.bong
 */

public class NotificationRecyclerAdapter extends RecyclerView.Adapter<NotificationRecyclerAdapter.ViewHolder> {

    private Context context;
    private RelativeLayout progressBar;
    private List<Notification> notificationList;

    /**
     * Constructor
     * @param context Context
     * @param notificationList List<Notification>
     */
    public NotificationRecyclerAdapter(Context context, List<Notification> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater != null ? inflater.inflate(R.layout.view_notification_list, parent, false) : null;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Notification notification = notificationList.get(position);
        holder.txt_date.setText(notification.getDate());
        holder.txt_title.setText(notification.getTitle());
        holder.txt_body.setText(notification.getBody());

        if (!notification.getData().isEmpty()) {
            try {
                JSONObject jsonObject = new JSONObject(notification.getData());
                String imageUrl = jsonObject.getString(NotificationDataKey.IMAGE_URL);
                ImageViewUtil.loadImageByUrl(context, imageUrl, holder.img_notification);
            } catch (JSONException e) {
                holder.img_notification.setImageResource(R.drawable.the_speed_logo);
                LoggerHelper.showErrorLog("Error: ", e);
            }
        }

    }

    /**
     * Load order detail
     * @param customerId customerId
     * @param incrementId incrementId
     */
    private void loadOrderDetail(Long customerId, String incrementId) {
        progressBar.setVisibility(View.VISIBLE);
        new FetchOrderDetails(customerId, incrementId, null, new FetchOrderDetails.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(List<OrderDetail> orderDetails) {
                progressBar.setVisibility(View.GONE);
                bindView(orderDetails);
            }

            @Override
            public void onError(Throwable e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(context, "Cannot open order detail", Toast.LENGTH_SHORT).show();
                LoggerHelper.showDebugLog("Error: " + e);
            }
        });
    }

    /**
     * bind data
     * @param orderDetails List of OrderDetail
     */
    private void bindView(List<OrderDetail> orderDetails) {
        if (orderDetails.size() > 0) {
            Intent intent = new Intent(context, MyOrderDetailActivity.class);
            intent.putExtra(ConstantValue.ITEMS, orderDetails.get(0));
            context.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    /**
     * Setter progressBar
     */
    public void setProgressBar(RelativeLayout progressBar) {
        this.progressBar = progressBar;
    }

    /**
     * Class for response view in list
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_date;
        TextView txt_title;
        TextView txt_body;
        ImageView img_notification;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_body = itemView.findViewById(R.id.txt_body);
            img_notification = itemView.findViewById(R.id.img_notification);

            itemView.setOnClickListener(view -> {
                Notification notification = notificationList.get(getAdapterPosition());
                if (!notification.getData().isEmpty()) {
                    try {
                        JSONObject jsonObject = new JSONObject(notification.getData());
                        String orderIncrementId = jsonObject.getString(NotificationDataKey.ORDER_INCREMENT_ID);
                        loadOrderDetail(new UserAccount(context).getCustomer().getId(), orderIncrementId);
                    } catch (JSONException e) {
                        LoggerHelper.showErrorLog("Error: ", e);
                    }
                }
            });
        }
    }
}