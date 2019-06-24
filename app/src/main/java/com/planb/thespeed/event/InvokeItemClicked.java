package com.planb.thespeed.event;

import android.view.View;

/**
 * Created by channarith.bong on 12/18/17.
 */

public interface InvokeItemClicked {

    /**
     *
     * @param v
     * @param position
     */
    void onItemClick(View v, int position);
}
