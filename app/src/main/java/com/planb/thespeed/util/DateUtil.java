package com.planb.thespeed.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by channarith.bong on 1/17/18.
 *
 * @author channarith.bong
 */

public class DateUtil {

    /**
     * Get current date
     *
     * @return String of date "01 Jan 2018"
     */
    public static String getCurrent() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        return df.format(c.getTime());
    }

    /**
     * formatDateTimeFromServer
     * @param dateTime String
     * @return String
     */
    public static String formatDateTimeFromServerAndAdd45Mn(String dateTime) {
        SimpleDateFormat serverFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat orderDateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.getDefault());
        try {
            Date serverDate = serverFormat.parse(dateTime);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(serverDate);
            calendar.add(Calendar.MINUTE, 45);

            return orderDateFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return String
     */
    public static String getToday() {
        return String.valueOf(new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date()));
    }

    /**
     * get now
     * @return String
     */
    public static String getNow() {
        return String.valueOf(new SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.getDefault()).format(new Date()));
    }

    /**
     * getNowAdd45Mn
     * @return String
     */
    public static String getNowAdd45Mn() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 45);
        return String.valueOf(new SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.getDefault()).format(calendar.getTime()));
    }

    /**
     * @return String
     */
    public static String getTomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        return String.valueOf(new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(calendar.getTime()));
    }

    /**
     * Get current time
     *
     * @return String of time format "00:00"
     */
    public static String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("KK:mm", Locale.getDefault());
        return df.format(c.getTime());
    }

    /**
     * generate time
     * @param startHour int
     * @param startMin int
     * @param endHour int
     * @param endMin int
     * @param minInterval int
     * @return
     */
    public static ArrayList<String> generateTimeInterval(int startHour, int startMin, int endHour, int endMin, int minInterval) {
        return generateTimeInterval(startHour, startMin, endHour, endMin, minInterval, false);
    }

    /**
     * generate time
     * @param startHour int
     * @param startMin int
     * @param endHour int
     * @param endMin int
     * @param minInterval int
     * @param isToday default false
     * @return
     */
    public static ArrayList<String> generateTimeInterval(int startHour, int startMin, int endHour, int endMin, int minInterval, boolean isToday) {
        ArrayList<String> intervals = new ArrayList<>();
        Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY, startHour);
//        start.set(Calendar.MINUTE, startMin);

        int min = startMin;

        int modular = min % 15;

        if (modular > 0 && modular < 7) {
            min -= modular;
        } else if (modular >= 7) {
            min += (15 - modular);
        }

        start.set(Calendar.MINUTE, min);

        Calendar end = Calendar.getInstance();
        end.set(Calendar.HOUR_OF_DAY, endHour);
        end.set(Calendar.MINUTE, endMin);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        if (start.after(end)) {
            intervals.add("Now");
            return intervals;
        }

        boolean isFirst = true;
        while (start.before(end)) {
            if (isToday && isFirst) {
                isFirst = false;
                start.add(Calendar.MINUTE, 60);
                intervals.add(simpleDateFormat.format(start.getTime()));
                continue;
            }
            start.add(Calendar.MINUTE, minInterval);
            intervals.add(simpleDateFormat.format(start.getTime()));
        }

        if (isToday) {
            intervals.add(0, "Now");
        }

        return intervals;
    }
}
