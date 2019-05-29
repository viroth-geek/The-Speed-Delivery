package com.iota.eshopping.fragment.time;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.iota.eshopping.R;
import com.iota.eshopping.adapter.TimePickerPagerAdapter;
import com.iota.eshopping.model.ServerDateTime;
import com.iota.eshopping.model.enumeration.DayType;
import com.iota.eshopping.service.base.InvokeOnCompleteAsync;
import com.iota.eshopping.service.datahelper.datasource.online.FetchServerDateTime;
import com.iota.eshopping.util.DateUtil;
import com.iota.eshopping.util.LoggerHelper;
import com.iota.eshopping.util.preference.TimeDeliveryPreference;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author channarith.bong
 */

public class NkrTimePicker extends DialogFragment {

    private ViewPager viewPager_time_picker;
    private TimeTodayFragment.OnTimeChange onTimeChange;
    private Pair<DayType, String> timeSelected;
    private TabLayout tabLayout_time_picker;
    TimePickerPagerAdapter pagerAdapter;
    String time = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.NkrDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.view_time_picker, container, false);

        fetchServerDateTime();

        Button btn_time_picker = view.findViewById(R.id.btn_time_picker_set_time);
        viewPager_time_picker = view.findViewById(R.id.pager_times_picker);
        tabLayout_time_picker = view.findViewById(R.id.tabs_times_picker);
//        LinearLayout container_time_picker = view.findViewById(R.id.container_time_picker);
        View outside_dialog = view.findViewById(R.id.fragment_outside_dialog);
        outside_dialog.setOnClickListener(view1 -> {
            view1.setVisibility(View.GONE);
            dismiss();
        });

        btn_time_picker.setOnClickListener(v -> {
            // DeliveryAnimationUtils.loadAnimationSlideDown(getActivity() , container_time_picker);
            v.postDelayed(() -> {
                view.setVisibility(View.GONE);
                dismiss();
            }, 500);
            if (timeSelected != null) {
                onTimeChange.onSelectChange(timeSelected);
            }
        });

        tabLayout_time_picker.addTab(tabLayout_time_picker.newTab().setText("Today"));
        tabLayout_time_picker.addTab(tabLayout_time_picker.newTab().setText("Tomorrow"));
        tabLayout_time_picker.setTabGravity(TabLayout.GRAVITY_FILL);

        if (onTimeChange != null) {
            pagerAdapter = new TimePickerPagerAdapter(getChildFragmentManager(), tabLayout_time_picker.getTabCount(), time -> timeSelected = time);
        } else {
            pagerAdapter = new TimePickerPagerAdapter(getChildFragmentManager(), tabLayout_time_picker.getTabCount());
        }

        viewPager_time_picker.setAdapter(pagerAdapter);
        viewPager_time_picker.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout_time_picker));

//        fetchServerDateTime();

        return view;
    }

    /**
     * bind date
     * @param currentHour int
     * @param currentMinute int
     */
    private void bindData(int currentHour, int currentMinute) {
        String date = TimeDeliveryPreference.getTimeDeliveryText(getContext());
        if (date != null && !date.isEmpty()) {
            String[] dates = date.split(Pattern.quote(" "));
            if (dates.length > 0) {
                date = dates[0];
                time = dates[1];
            }
        }

        tabLayout_time_picker.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LoggerHelper.showDebugLog("Tab Position: " + tab.getPosition());
                viewPager_time_picker.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        List<String> listTime = DateUtil.generateTimeInterval(currentHour, currentMinute, 20, 0, 15, true);
                        timeSelected = new Pair<>(DayType.TODAY, listTime.get(0));
                        break;
                    case 1:
                        timeSelected = new Pair<>(DayType.TOMORROW, DateUtil.generateTimeInterval(8, 0, 20, 0, 45).get(0));
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        if (date != null) {
            if (date.equalsIgnoreCase(DayType.TODAY.toString()) || date.equalsIgnoreCase("Deliver")) {
                tabLayout_time_picker.getTabAt(0).select();
            }
            else if (date.equalsIgnoreCase(DayType.TOMORROW.toString())) {
                tabLayout_time_picker.getTabAt(1).select();
            }
        }
    }

    /**
     * fetch server date time
     */
    private void fetchServerDateTime() {
        new FetchServerDateTime(new InvokeOnCompleteAsync<List<ServerDateTime>>() {
            @Override
            public void onComplete(List<ServerDateTime> serverDateTimes) {
                if (serverDateTimes != null && !serverDateTimes.isEmpty()) {
                    ServerDateTime serverDateTime = serverDateTimes.get(0);
                    String time = serverDateTime.getTime();
                    String times[] = time.split(":");
                    int currentHour = Integer.valueOf(times[0]);
                    int currentMinute = Integer.valueOf(times[1]);
                    bindData(currentHour, currentMinute);
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
                bindData(currentHour, currentMinute);
            }
        });
    }

    /**
     * Handle for selection time
     *
     * @param onTimeChange TimeTodayFragment.OnTimeChange
     */
    public void setTimeChangeListener(TimeTodayFragment.OnTimeChange onTimeChange) {
        this.onTimeChange = onTimeChange;
    }
}
