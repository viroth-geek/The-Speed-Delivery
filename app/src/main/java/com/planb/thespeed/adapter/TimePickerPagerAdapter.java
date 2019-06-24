package com.planb.thespeed.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.planb.thespeed.fragment.time.TimeTodayFragment;
import com.planb.thespeed.fragment.time.TimeTomorrowFragment;

/**
 * Created by channarith.bong on 12/22/17.
 *
 * @author channarith.bong
 */
public class TimePickerPagerAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs;
    private TimeTodayFragment.OnTimeChange onTimeChange;

    /**
     * @param fragmentManager FragmentManager
     * @param numberOfTabs int
     */
    public TimePickerPagerAdapter(FragmentManager fragmentManager, int numberOfTabs) {
        super(fragmentManager);
        this.numOfTabs = numberOfTabs;
    }

    /**
     * @param fragmentManager FragmentManager
     * @param numberOfTabs int
     * @param onTimeChange TimeTodayFragment.OnTimeChange
     */
    public TimePickerPagerAdapter(FragmentManager fragmentManager, int numberOfTabs, TimeTodayFragment.OnTimeChange onTimeChange) {
        super(fragmentManager);
        this.numOfTabs = numberOfTabs;
        this.onTimeChange = onTimeChange;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TimeTodayFragment tab1 = new TimeTodayFragment();
                if (onTimeChange != null) {
                    tab1.setTimeChangeListener(onTimeChange);
                }

                return tab1;
            case 1:
                TimeTomorrowFragment tab2 = new TimeTomorrowFragment();
                if (onTimeChange != null) {
                    tab2.setTimeChangeListener(onTimeChange);
                }
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.numOfTabs;
    }
}
