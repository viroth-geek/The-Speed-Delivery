package com.planb.thespeed.util;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Utils {

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String FormatDateTimeLocal(String d) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date;
        try {
            date = dateFormat.parse(d);

            return new SimpleDateFormat("MMM dd, yyyy HH:mm:ss").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String FormatDate(String d){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date;
        try {
            date = dateFormat.parse(d);

            return new SimpleDateFormat("MMM dd, yyyy HH:mm:ss").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

}
