package com.iota.eshopping.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iota.eshopping.R;
import com.iota.eshopping.activity.MyOrderDetailActivity;
import com.iota.eshopping.constant.ConstantValue;
import com.iota.eshopping.model.ImageResponse;
import com.iota.eshopping.model.OrderDetail;
import com.iota.eshopping.model.enumeration.OrderStatusType;
import com.iota.eshopping.security.CurrencyConfiguration;
import com.iota.eshopping.service.datahelper.datasource.online.FetchDeliveryDate;
import com.iota.eshopping.service.datahelper.datasource.online.FetchStoreImage;
import com.iota.eshopping.util.ImageViewUtil;
import com.iota.eshopping.util.LoggerHelper;
import com.iota.eshopping.util.NumberUtils;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static com.iota.eshopping.util.Utils.FormatDateTimeLocal;

/**
 * @author channarith.bong
 */
public class OrderDetailStickyAdapter extends SectioningAdapter {

    private List<Section> sections;
    private Context context;

    /**
     * @param data list of OrderDetail
     */
    public OrderDetailStickyAdapter(List<OrderDetail> data, Context context) {
        this.sections = setSection(data);
        this.context = context;
    }

    @Override
    public int getNumberOfSections() {
        return sections.size();
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        return sections.get(sectionIndex).details.size();
    }

    @Override
    public boolean doesSectionHaveHeader(int sectionIndex) {
        return false;
    }

    @Override
    public boolean doesSectionHaveFooter(int sectionIndex) {
        return false;
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, final int itemType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.view_list_order_item, parent, false);
        return new ViewChildHolder(v);
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.view_header_order_item, parent, false);
        return new ViewHeadHolder(v);
    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder viewHolder, int sectionIndex, int itemIndex, int itemType) {
        Section section = sections.get(sectionIndex);
        OrderDetail orderDetail = section.details.get(itemIndex);
        ViewChildHolder childHolder = (ViewChildHolder) viewHolder;
        String storeName = getStoreName(orderDetail.getStoreName());
        Long storeId = orderDetail.getStoreId();

        childHolder.txt_order_id.setText(String.format("%s", orderDetail.getIncrementId()));
        childHolder.txt_order_time.setText(String.format(" %s", FormatDateTimeLocal(orderDetail.getUpdatedAt())));

        childHolder.txt_order_status.setText(orderDetail.getStatus());
        if (OrderStatusType.PENDING.toString().equalsIgnoreCase(orderDetail.getStatus()) ||
                OrderStatusType.HOLDED.toString().equalsIgnoreCase(orderDetail.getStatus())) {
            childHolder.txt_order_status.setTextColor(context.getResources().getColor(R.color.colorDeliverPrimary));
        } else if (OrderStatusType.PROCESSING.toString().equalsIgnoreCase(orderDetail.getStatus())) {
            childHolder.txt_order_status.setTextColor(context.getResources().getColor(R.color.gray));
        } else if (OrderStatusType.COMPLETE.toString().equalsIgnoreCase(orderDetail.getStatus())) {
            childHolder.txt_order_status.setTextColor(context.getResources().getColor(R.color.green));
        } else if (OrderStatusType.CANCELED.toString().equalsIgnoreCase(orderDetail.getStatus())) {
            childHolder.txt_order_status.setTextColor(context.getResources().getColor(R.color.red));
        }

        childHolder.txt_item_quantity.setText(String.format(Locale.ENGLISH, "%d", orderDetail.getTotalQtyOrdered()));
        childHolder.txt_item_total_amount.setText(String.format("%s%s", CurrencyConfiguration.getDollarSign(), NumberUtils.strMoney((Float.valueOf(NumberUtils.strMoney(orderDetail.getTotalDue().floatValue()))))));
        childHolder.txt_store_name.setText(storeName);
        getDeliveryDate(orderDetail.getId(), childHolder.txtDeliveryTime);
        childHolder.img_order_item.setImageBitmap(null);
        childHolder.setOrderDetail(orderDetail);

        // request image
        if (storeId != null) {
            loadImage(storeId, childHolder.img_order_item);
        }
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder viewHolder, int sectionIndex, int headerType) {
        Section s = sections.get(sectionIndex);
        ViewHeadHolder headHolder = (ViewHeadHolder) viewHolder;
        headHolder.txt_time_update.setText(s.header);
    }

    /**
     * @param data list of OrderDetail
     * @return list of Section
     */
    private List<Section> setSection(List<OrderDetail> data) {
        List<Section> sections = new ArrayList<>();
        sections.add(new Section("last week", data));
        return sections;
    }

    /**
     * @return String
     */
    private String getStoreName(String storeFullName) {
        String storeName;
        String[] names = storeFullName.split("\\n");
        try {
            storeName = names[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            LoggerHelper.showErrorLog("Store Name Error" + e.getMessage());
            storeName = names[names.length - 1];
        }
        return storeName;
    }

    /**
     *
     * @param id Long
     * @param imageStore ImageView
     */
    private void loadImage(Long id, final ImageView imageStore) {
        new FetchStoreImage(id, new FetchStoreImage.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(List<ImageResponse> imageResponseList) {
                if (imageResponseList != null) {
                    ImageViewUtil.loadImageByUrl(context, imageResponseList.get(0).getImageUrl(), imageStore);
                }
            }

            @Override
            public void onError(Throwable e) {
                LoggerHelper.showErrorLog("Store Image " + e);
            }
        });
    }

    /**
     *
     * @param orderId Long
     */
    private void getDeliveryDate(Long orderId, TextView txtDeliveryDate) {
        new FetchDeliveryDate(orderId, new FetchDeliveryDate.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(String orderDate) {

                if (orderDate != null && !orderDate.isEmpty()) {
//                    txtDeliveryDate.setText(orderDate);
                    txtDeliveryDate.setText(String.format(" %s", FormatDateTimeLocal(orderDate)));
                }
                else {
                    txtDeliveryDate.setText("N/A");
                }

//                Log.d(ConstantValue.TAG_LOG, orderDate);
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Phnom_Penh"));
//
//                try {
//                    Date date = simpleDateFormat.parse(orderDate);
//                    txtDeliveryDate.setText(String.format(" %s", new SimpleDateFormat("MMM dd, yyyy HH:mm:ss").format(date)));
//
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
////                if (orderDate != null && !orderDate.isEmpty()) {
////
////                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.ENGLISH);
////
////                    try {
////                        Date date = simpleDateFormat.parse(orderDate);
////                        txtDeliveryDate.setText(String.format(" %s", new SimpleDateFormat("MMM dd, yyyy hh:mm:ss", Locale.ENGLISH).format(date)));
////                    } catch (ParseException e) {
////                        e.printStackTrace();
////                    }
////                } else {
////                    txtDeliveryDate.setText("N/A");
////                }
            }

            @Override
            public void onError(Throwable e) {
                txtDeliveryDate.setText("N/A");
                LoggerHelper.showErrorLog("Error: ", e);
            }
        });
    }

    /**
     *
     */
    private class Section {
        String header;
        List<OrderDetail> details;

        Section(String header, List<OrderDetail> orderDetails) {
            this.header = header;
            this.details = orderDetails;
        }
    }

    /**
     * Header row
     */
    private class ViewHeadHolder extends HeaderViewHolder {

        TextView txt_time_update;

        ViewHeadHolder(View itemView) {
            super(itemView);
            txt_time_update = itemView.findViewById(R.id.txt_time_update);
        }
    }

    /**
     * Child row
     */
    private class ViewChildHolder extends ItemViewHolder {
        private Context context;
        private OrderDetail orderDetail;
        private TextView txt_order_id;
        private TextView txt_order_time;
        private TextView txt_order_status;
        private TextView txt_item_quantity;
        private TextView txt_item_total_amount;
        private TextView txt_store_name;
        private TextView txtDeliveryTime;
        private ImageView img_order_item;

        ViewChildHolder(final View itemView) {
            super(itemView);
            context = itemView.getContext();
            txt_order_id = itemView.findViewById(R.id.txt_order_id);
            txt_order_time = itemView.findViewById(R.id.txt_order_time);
            txt_order_status = itemView.findViewById(R.id.txt_order_status);
            txt_item_quantity = itemView.findViewById(R.id.txt_item_quantity);
            txt_item_total_amount = itemView.findViewById(R.id.txt_item_total_amount);
            txt_store_name = itemView.findViewById(R.id.txt_store_name);
            txtDeliveryTime = itemView.findViewById(R.id.txt_delivery_time);
            img_order_item = itemView.findViewById(R.id.img_order_item);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, MyOrderDetailActivity.class);
                intent.putExtra(ConstantValue.ITEMS, getOrderDetail());
                context.startActivity(intent);
            });
        }

        OrderDetail getOrderDetail() {
            return orderDetail;
        }

        void setOrderDetail(OrderDetail orderDetail) {
            this.orderDetail = orderDetail;
        }
    }


    private String formatLocale(String dateTime) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZZ")
                .withLocale(Locale.ENGLISH);
        return fmt.toString();
    }
}
