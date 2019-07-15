package com.planb.thespeed.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.planb.thespeed.fragment.order.OrderStatusFragment;
import com.planb.thespeed.model.enumeration.OrderStatusType;

/**
 * @author channarith.bong
 */
public class OrderPagerAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs;

    /**
     * @param fragmentManager FragmentManager
     * @param numOfTabs int
     */
    public OrderPagerAdapter(FragmentManager fragmentManager, int numOfTabs) {
        super(fragmentManager);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return OrderStatusFragment.newInstance(OrderStatusType.PENDING);
        } else if (position == 1) {
            return OrderStatusFragment.newInstance(OrderStatusType.COMPLETE);
        }
        return null;
    }

    @Override
    public int getCount() {
        return this.numOfTabs;
    }
}
