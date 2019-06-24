package com.planb.thespeed.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.planb.thespeed.util.ImageViewUtil;

/**
 * @author yeakleang.ay on 5/22/18.
 */

public class StoreSliderView extends TextSliderView {

    /**
     * @param context Context
     */
    public StoreSliderView(Context context) {
        super(context);
    }


    @Override
    protected void bindEventAndShow(View v, ImageView targetImageView) {

        final BaseSliderView me = this;

        v.setOnClickListener(v1 -> {
            if (mOnSliderClickListener != null) {
                mOnSliderClickListener.onSliderClick(me);
            }
        });

        if (targetImageView == null)
            return;

        targetImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        if (getUrl() != null && !getUrl().isEmpty()) {
            ImageViewUtil.loadImageByUrl(mContext, getUrl(), targetImageView, new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    hideLoadingView(v);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    hideLoadingView(v);
                    return false;
                }
            });
        } else {
            hideLoadingView(v);
        }
    }

    /**
     * @param v
     */
    private void hideLoadingView(View v) {
        if (v.findViewById(com.daimajia.slider.library.R.id.loading_bar) != null) {
            v.findViewById(com.daimajia.slider.library.R.id.loading_bar).setVisibility(View.INVISIBLE);
        }
    }

}
