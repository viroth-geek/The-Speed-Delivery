package com.planb.thespeed.service.datahelper.datasource.online;

import com.planb.thespeed.constant.entity.ConditionType;
import com.planb.thespeed.constant.meta.OrderDetailM;
import com.planb.thespeed.model.OrderDetail;
import com.planb.thespeed.model.enumeration.OrderStatusType;
import com.planb.thespeed.model.magento.order.ItemList;
import com.planb.thespeed.model.magento.search.SearchParam;
import com.planb.thespeed.model.magento.search.SearchParamGroup;
import com.planb.thespeed.security.KeyManagement;
import com.planb.thespeed.server.ServiceGenerator;
import com.planb.thespeed.service.api.order.OrderService;
import com.planb.thespeed.util.LoggerHelper;
import com.planb.thespeed.util.UrlHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by channarith.bong on 1/22/18.
 *
 * @author channarith.bong
 */
public class FetchOrderDetails implements Observer<ItemList> {

    private InvokeOnCompleteAsync completeAsync;
    private ItemList itemList;
    private Disposable disposable;

    /**
     * @param customerId
     * @param statusType
     * @param completeAsync
     */
    public FetchOrderDetails(Long customerId, OrderStatusType statusType, InvokeOnCompleteAsync completeAsync) {
        this.completeAsync = completeAsync;
        List<SearchParamGroup> paramGroups = new ArrayList<>();

        if (statusType != null) {
            List<SearchParam> searchStatusParams = new ArrayList<>();
            if (statusType.equals(OrderStatusType.PENDING)) {
                searchStatusParams.add(new SearchParam(OrderDetailM.STATUS,
                        OrderStatusType.PENDING.toString().toLowerCase(),
                        ConditionType.EQUALS));
                searchStatusParams.add(new SearchParam(OrderDetailM.STATUS,
                        OrderStatusType.PROCESSING.toString().toLowerCase(),
                        ConditionType.EQUALS));
                searchStatusParams.add(new SearchParam(OrderDetailM.STATUS,
                        OrderStatusType.HOLDED.toString().toLowerCase(),
                        ConditionType.EQUALS));
            } else if (statusType.equals(OrderStatusType.COMPLETE)) {
                searchStatusParams.add(new SearchParam(OrderDetailM.STATUS,
                        OrderStatusType.COMPLETE.toString().toLowerCase(),
                        ConditionType.EQUALS));
                searchStatusParams.add(new SearchParam(OrderDetailM.STATUS,
                        OrderStatusType.CANCELED.toString().toLowerCase(),
                        ConditionType.EQUALS));
            }

            SearchParamGroup paramStatusGroup = new SearchParamGroup(searchStatusParams);

            LoggerHelper.showDebugLog(paramStatusGroup.toString());
            paramGroups.add(paramStatusGroup);
        }

        List<SearchParam> searchCustomerParams = new ArrayList<>();
        searchCustomerParams.add(new SearchParam(OrderDetailM.CUSTOMER_ID, customerId + "", ConditionType.EQUALS));
        SearchParamGroup paramCustomerGroup = new SearchParamGroup(searchCustomerParams);

        paramGroups.add(paramCustomerGroup);

        HashMap<String, String> searchHashMap = UrlHelper.searchCriteria(paramGroups);
        LoggerHelper.showDebugLog(searchHashMap.toString());

        // Sort DESC by increment_id (order id)
        searchHashMap.put("searchCriteria[sortOrders][0][field]", "created_at");
        searchHashMap.put("searchCriteria[sortOrders][0][direction]", "DESC");
        request(searchHashMap);
    }

    /**
     * @param customerId
     * @param statusType
     * @param completeAsync
     */
    public FetchOrderDetails(Long customerId, String orderIncrement, OrderStatusType statusType, InvokeOnCompleteAsync completeAsync) {
        this.completeAsync = completeAsync;
        List<SearchParamGroup> paramGroups = new ArrayList<>();

        if (statusType != null) {
            // Status filter
            List<SearchParam> searchStatusParams = new ArrayList<>();
            if (statusType.equals(OrderStatusType.PENDING)) {
                searchStatusParams.add(new SearchParam(OrderDetailM.STATUS,
                        OrderStatusType.PENDING.toString().toLowerCase(),
                        ConditionType.EQUALS));
                searchStatusParams.add(new SearchParam(OrderDetailM.STATUS,
                        OrderStatusType.PROCESSING.toString().toLowerCase(),
                        ConditionType.EQUALS));
            } else if (statusType.equals(OrderStatusType.COMPLETE)) {
                searchStatusParams.add(new SearchParam(OrderDetailM.STATUS,
                        OrderStatusType.COMPLETE.toString().toLowerCase(),
                        ConditionType.EQUALS));
                searchStatusParams.add(new SearchParam(OrderDetailM.STATUS,
                        OrderStatusType.CANCELED.toString().toLowerCase(),
                        ConditionType.EQUALS));

            }
            SearchParamGroup paramStatusGroup = new SearchParamGroup(searchStatusParams);

            LoggerHelper.showDebugLog(paramStatusGroup.toString());
            paramGroups.add(paramStatusGroup);
        }

        // Customer filter
        List<SearchParam> searchCustomerParams = new ArrayList<>();
        searchCustomerParams.add(new SearchParam(OrderDetailM.CUSTOMER_ID, customerId + "", ConditionType.EQUALS));
        SearchParamGroup paramCustomerGroup = new SearchParamGroup(searchCustomerParams);

        // Increment filter
        List<SearchParam> searchIncrementParams = new ArrayList<>();
        searchIncrementParams.add(new SearchParam(OrderDetailM.INCREMENT_ID, orderIncrement + "", ConditionType.EQUALS));
        SearchParamGroup paramIncrementParams = new SearchParamGroup(searchIncrementParams);

        // Add filter
        paramGroups.add(paramCustomerGroup);
        paramGroups.add(paramIncrementParams);

        HashMap<String, String> searchHashMap = UrlHelper.searchCriteria(paramGroups);
        // Sort DESC by increment_id (order id)
        searchHashMap.put("searchCriteria[sortOrders][0][field]", "created_at");
        searchHashMap.put("searchCriteria[sortOrders][0][direction]", "DESC");
        request(searchHashMap);
    }

    /**
     * @param hashMap
     */
    private void request(HashMap<String, String> hashMap) {
        ServiceGenerator
                .createService(OrderService.class, KeyManagement.getInstance().getTokenAdmin().trim())
                .getOrderDetailList(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(ItemList itemList) {
        this.itemList = itemList;
    }

    @Override
    public void onError(Throwable e) {
        disposable.dispose();
        completeAsync.onError(e);
    }

    @Override
    public void onComplete() {
        disposable.dispose();
        completeAsync.onComplete(itemList.getOrderDetail());
    }

    /**
     *
     */
    public interface InvokeOnCompleteAsync {
        void onComplete(List<OrderDetail> orderDetails);

        void onError(Throwable e);
    }
}
