package com.iota.eshopping.fragment.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iota.eshopping.R;
import com.iota.eshopping.adapter.OrderDetailStickyAdapter;
import com.iota.eshopping.adapter.StickyHeaderLayoutManager;
import com.iota.eshopping.constant.ConstantValue;
import com.iota.eshopping.model.OrderDetail;
import com.iota.eshopping.model.enumeration.OrderStatusType;
import com.iota.eshopping.model.modelForView.Product;
import com.iota.eshopping.model.modelForView.ProductItem;
import com.iota.eshopping.model.modelForView.Store;
import com.iota.eshopping.security.UserAccount;
import com.iota.eshopping.service.datahelper.datasource.online.FetchDeliveryDate;
import com.iota.eshopping.service.datahelper.datasource.online.FetchOrderDetails;
import com.iota.eshopping.util.ExceptionUtils;
import com.iota.eshopping.util.LoggerHelper;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author channarith.bong
 */
public class OrderStatusFragment extends Fragment {

    private RecyclerView recyclerView;
    private View container_float_loading;
    private StickyHeaderLayoutManager stickyLayoutManager;
    private OrderStatusType orderStatusType;

    private static int MAX_ATTEMPT = 3;
    private static int ATTEMPT_COUNT = 0;

    /**
     * @param orderStatusType OrderStatusType
     * @return OrderStatusFragment
     */
    public static OrderStatusFragment newInstance(OrderStatusType orderStatusType) {
        OrderStatusFragment instance = new OrderStatusFragment();
        instance.orderStatusType = orderStatusType;
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_status, container, false);
        recyclerView = view.findViewById(R.id.item_orders_list);
        container_float_loading = view.findViewById(R.id.container_float_loading);
        stickyLayoutManager = new StickyHeaderLayoutManager();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindData();
    }

    /**
     *
     */
    private void bindData() {
        UserAccount userAccount = new UserAccount(getActivity());
        if (userAccount.getCustomer().getId() != null) {
            showMyOrder(userAccount.getCustomer().getId());
        } else {
            settingProcessBar(true, "Please logging again!");
        }
    }

    /**
     * @param orderDetails list of OrderDetail
     */
    private void bindView(List<OrderDetail> orderDetails) {
        if (orderDetails.size() > 0) {
            List<OrderDetail> orderDetailList = new ArrayList<>();
            for (int i = 0; i < orderDetails.size(); i++) {
                if (Store.isSysStore(orderDetails.get(i).getStoreId())) {
                    orderDetails.remove(i);
                }
                List<ProductItem> productItems = new ArrayList<>();
                ProductItem proItem = null;
                for (ProductItem productItem : orderDetails.get(i).getProductItems()) {
                    Product product = (Product) productItem.getItem();
                    Product pro = null;
                    if (proItem != null) {
                        pro = (Product) proItem.getItem();
                    }
                    else {
                        productItems.add(productItem);
                    }
                    if (pro != null && pro.getSku() != null && pro.getSku().equals(product.getSku())) {
                        if (product.getProductType().equals(ConstantValue.CONFIGURABLE_PRODUCT)) {
                            productItems.add(productItem);
                        }
                    }
                    else {
                        productItems.add(productItem);
                    }
                    proItem = productItem;
                }
                orderDetails.get(i).setProductItems(productItems);
                orderDetailList.add(orderDetails.get(i));
            }

            Collections.sort(orderDetailList);
            OrderDetailStickyAdapter stickyAdapter = new OrderDetailStickyAdapter(orderDetails, getActivity());
            stickyAdapter.notifyAllSectionsDataSetChanged();
            recyclerView.setLayoutManager(stickyLayoutManager);
            recyclerView.setAdapter(stickyAdapter);
            settingProcessBar(false, null);
        } else {
            settingProcessBar(true, "Empty order list");
        }
    }

    /**
     * @param isShow Boolean
     * @param message String
     */
    private void settingProcessBar(Boolean isShow, String message) {
        TextView txt = container_float_loading.findViewById(R.id.txt_container_loading_label);
        ProgressBar loading_cycle_i = container_float_loading.findViewById(R.id.loading_cycle_ii);
        if (isShow) {
            container_float_loading.setVisibility(View.VISIBLE);
        } else {
            container_float_loading.setVisibility(View.GONE);
        }

        if (message != null) {
            loading_cycle_i.setVisibility(View.GONE);
            txt.setText(message);
        } else {
            loading_cycle_i.setVisibility(View.VISIBLE);
            txt.setText(R.string.loading);
        }
    }

    /**
     * Request information about user orders
     *
     * @param id Long
     */
    private synchronized void showMyOrder(final Long id) {
        settingProcessBar(true, null);
        new FetchOrderDetails(id, orderStatusType, new FetchOrderDetails.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(List<OrderDetail> orderDetails) {
                if (orderDetails != null) {
                    bindView(orderDetails);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof SocketTimeoutException) {
                    if (ATTEMPT_COUNT < MAX_ATTEMPT) {
                        showMyOrder(id);
                        ATTEMPT_COUNT++;
                        LoggerHelper.showErrorLog(" >>> BIND DATA ATTEMPT  " + ATTEMPT_COUNT);
                    }
                    LoggerHelper.showErrorLog(" >>> BIND DATA ERROR " + orderStatusType.toString() + " : " + e.getMessage());
//                    settingProcessBar(true, "Cannot get records!");
                    settingProcessBar(true, "Error: " + ExceptionUtils.translateExceptionMessage(e));
                } else {
                    LoggerHelper.showErrorLog(" >>> BIND DATA ERROR " + orderStatusType.toString() + " : " + e.getMessage());
//                    settingProcessBar(true, "Cannot get records!");
                    settingProcessBar(true, "Error: " + ExceptionUtils.translateExceptionMessage(e));
                }

            }
        });
    }
}
