package com.planb.thespeed.fragment.time;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.planb.thespeed.R;
import com.planb.thespeed.model.enumeration.DayType;
import com.planb.thespeed.util.DateUtil;
import com.planb.thespeed.util.preference.TimeDeliveryPreference;
import com.planb.thespeed.widget.NkrNumberPicker;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by channarith.bong on 12/22/17.
 *
 * @author channarith.bong
 */

public class TimeTomorrowFragment extends Fragment {

    private TimeTodayFragment.OnTimeChange onTimeChange;
    private String[] time;

    /**
     *
     */
    public TimeTomorrowFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        time = getActivity().getResources().getStringArray(R.array.static_deliver_time_tomorrow);
        List<String> listTime = DateUtil.generateTimeInterval(8, 30, 20, 0, 15);
        time = listTime.toArray(new String[listTime.size()]);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String t = TimeDeliveryPreference.getTimeDeliveryText(getContext());
        if (t != null && !t.isEmpty()) {
            String[] times = t.split(Pattern.quote(" "));
            if (times.length > 0) {
                t = times[1];
            }
        }

        View view = inflater.inflate(R.layout.fragment_time_tomorrow, container, false);
        NkrNumberPicker nkrNumberPicker = view.findViewById(R.id.picker_time_picker_tomorrow);
        nkrNumberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        nkrNumberPicker.setMaxValue(time.length - 1);
        nkrNumberPicker.setMinValue(0);
        nkrNumberPicker.setFocusable(true);
        nkrNumberPicker.setWrapSelectorWheel(false);
        nkrNumberPicker.setFocusableInTouchMode(true);
        nkrNumberPicker.setDisplayedValues(time);
        for (int i = 0; i < time.length; i++) {
            if (time[i].equals(t)) {
                nkrNumberPicker.setValue(i);
                onTimeChange.onSelectChange(new Pair<>(DayType.TOMORROW, t));
            }
        }
        nkrNumberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            if (onTimeChange != null) {
                onTimeChange.onSelectChange(new Pair<>(DayType.TOMORROW, time[newVal]));
            }
        });
        return view;
    }

    /**
     * @param onTimeChange TimeTodayFragment.OnTimeChange
     */
    public void setTimeChangeListener(TimeTodayFragment.OnTimeChange onTimeChange) {
        this.onTimeChange = onTimeChange;
    }
}
