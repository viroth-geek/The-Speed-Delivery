package com.iota.eshopping.fragment.page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iota.eshopping.R;
import com.iota.eshopping.adapter.OrderPagerAdapter;

/**
 * @author channarith.bong
 */
public class MyOrderFragment extends Fragment {

    private TabLayout tabs_status;
    private ViewPager pager_status;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_order, container, false);
        tabs_status = view.findViewById(R.id.tabs_status);
        pager_status = view.findViewById(R.id.pager_status);
        setTab();
        return view;
    }

    /**
     * Create Two tab for Order Status
     * "Pending" include "Pending" and "Process" status
     * "Complete" show only "Complete" status
     */
    private void setTab() {
        tabs_status.addTab(tabs_status.newTab().setText("PENDING / PROCESSING"));
        tabs_status.addTab(tabs_status.newTab().setText("COMPLETE / CANCELED"));
        tabs_status.setTabGravity(TabLayout.GRAVITY_FILL);

        OrderPagerAdapter pagerAdapter = new OrderPagerAdapter(getChildFragmentManager(), tabs_status.getTabCount());
        pager_status.setAdapter(pagerAdapter);
        pager_status.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs_status));
        tabs_status.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager_status.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
