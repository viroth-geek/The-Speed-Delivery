package com.iota.eshopping.fragment.time;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.ProgressBar;

import com.iota.eshopping.R;
import com.iota.eshopping.model.ServerDateTime;
import com.iota.eshopping.model.enumeration.DayType;
import com.iota.eshopping.service.base.InvokeOnCompleteAsync;
import com.iota.eshopping.service.datahelper.datasource.online.FetchServerDateTime;
import com.iota.eshopping.util.DateUtil;
import com.iota.eshopping.util.preference.TimeDeliveryPreference;
import com.iota.eshopping.widget.NkrNumberPicker;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by channarith.bong on 12/22/17.
 *
 * @author channarith.bong
 */

public class TimeTodayFragment extends Fragment {

    private NkrNumberPicker nkrNumberPicker;
    private ProgressBar timeProgressBar;
    private OnTimeChange onTimeChange;
    private String[] time;

    /**
     *
     */
    public TimeTodayFragment() {
//        time = getActivity().getResources().getStringArray(R.array.static_deliver_time_today);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        time = getActivity().getResources().getStringArray(R.array.static_deliver_time_today);
        fetchServerDateTime();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_time_today, container, false);
        nkrNumberPicker = view.findViewById(R.id.picker_time_picker_today);
        nkrNumberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        nkrNumberPicker.setVisibility(View.GONE);

        timeProgressBar = view.findViewById(R.id.time_progress_bar);
        timeProgressBar.setVisibility(View.VISIBLE);
//        onTimeChange.onSelectChange(new Pair<>(DayType.TODAY, time[nkrNumberPicker.getValue()]));

        return view;
    }

    /**
     * bind view
     */
    private void bindView() {
        String t = TimeDeliveryPreference.getTimeDeliveryText(getContext());
        if (t != null && !t.isEmpty()) {
            String[] times = t.split(Pattern.quote(" "));
            if (times.length > 0) {
                t = times[1];
            }
        }

        nkrNumberPicker.setMaxValue(time.length - 1);
        nkrNumberPicker.setFocusable(true);
        nkrNumberPicker.setWrapSelectorWheel(false);
        nkrNumberPicker.setFocusableInTouchMode(true);
        nkrNumberPicker.setDisplayedValues(time);
        for (int i = 0; i < time.length; i++) {
            if (time[i].equals(t)) {
                nkrNumberPicker.setValue(i);
                onTimeChange.onSelectChange(new Pair<>(DayType.TODAY, t));
            }
        }
        nkrNumberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            if (onTimeChange != null) {
                onTimeChange.onSelectChange(new Pair<>(DayType.TODAY, time[newVal]));
            }
        });
    }

    /**
     * @param onTimeChange OnTimeChange
     */
    public void setTimeChangeListener(OnTimeChange onTimeChange) {
        this.onTimeChange = onTimeChange;
    }

    /**
     *
     */
    public interface OnTimeChange {
        void onSelectChange(Pair<DayType, String> time);
    }

    /**
     * fetch server time
     */
    private void fetchServerDateTime() {
        new FetchServerDateTime(new InvokeOnCompleteAsync<List<ServerDateTime>>() {
            @Override
            public void onComplete(List<ServerDateTime> serverDateTimes) {

                nkrNumberPicker.setVisibility(View.VISIBLE);
                timeProgressBar.setVisibility(View.GONE);

                if (serverDateTimes != null && !serverDateTimes.isEmpty()) {
                    ServerDateTime serverDateTime = serverDateTimes.get(0);
                    String time = serverDateTime.getTime();
                    String times[] = time.split(":");
                    int currentHour = Integer.valueOf(times[0]);
                    int currentMinute = Integer.valueOf(times[1]);
                    List<String> listTime = DateUtil.generateTimeInterval(currentHour, currentMinute, 20, 30, 15, true);
                    TimeTodayFragment.this.time = listTime.toArray(new String[listTime.size()]);
                    bindView();
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();

                nkrNumberPicker.setVisibility(View.VISIBLE);
                timeProgressBar.setVisibility(View.GONE);

                int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
                List<String> listTime = DateUtil.generateTimeInterval(currentHour, currentMinute, 20, 30, 15, true);
                TimeTodayFragment.this.time = listTime.toArray(new String[listTime.size()]);
                bindView();
            }
        });
    }
}
