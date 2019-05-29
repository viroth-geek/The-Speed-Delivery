package com.iota.eshopping.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

/**
 * @author yeakleang.ay on 12/20/17.
 */

public class NkrNumberPicker extends android.widget.NumberPicker {

    public NkrNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    /**
     * @param view
     */
    private void updateView(View view) {
        if (view instanceof EditText) {
            ((EditText) view).setTextSize(24);
            ((EditText) view).setTextColor(Color.parseColor("#333333"));
        }
    }
}
