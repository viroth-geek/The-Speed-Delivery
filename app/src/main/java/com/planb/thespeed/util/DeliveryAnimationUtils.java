package com.planb.thespeed.util;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.planb.thespeed.R;

/**
 * Created by channarith.bong on 12/18/17.
 */

public class DeliveryAnimationUtils {

    /**
     * @param context
     * @param view
     */
    public static void loadAnimationSlideUp(Context context, View view) {
        // Load animation
        Animation slide_up = AnimationUtils.loadAnimation(context, R.anim.anim_slide_up);

        // Start animation
        view.setVisibility(View.VISIBLE);
        view.startAnimation(slide_up);
    }

    /**
     * @param context
     * @param view
     */
    public static void loadAnimationSlideDown(Context context, View view) {
        // Load animation
        Animation slide_down = AnimationUtils.loadAnimation(context, R.anim.anim_slide_down);

        // Start animation
        view.startAnimation(slide_down);
        view.setVisibility(View.GONE);

    }

    // Progress Bar Animation
}
