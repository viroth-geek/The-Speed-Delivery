package com.planb.thespeed.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.planb.thespeed.activity.StoreListActivity;
import com.planb.thespeed.constant.ConstantValue;
import com.planb.thespeed.model.enumeration.SearchGroupType;
import com.planb.thespeed.model.modelForView.Store;
import com.planb.thespeed.util.LoggerHelper;
import com.planb.thespeed.widget.StoreSliderView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author channarith.bong
 */
public class ImageSliderAdapter implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    /**
     *
     * @param context Context
     * @param SearchGroupType SearchGroupType
     * @param stores list of Store
     * @param sliderLayout SliderLayout
     */
    private ImageSliderAdapter(Context context, SearchGroupType SearchGroupType, List<Store> stores, SliderLayout sliderLayout) {
        initSlider(context, SearchGroupType, stores, sliderLayout);
    }

    /**
     *
     * @param context Context
     * @param SearchGroupType SearchGroupType
     * @param stores list of Store
     * @param sliderLayout SliderLayout
     * @param slideLabelType slideLabelType
     */
    private ImageSliderAdapter(Context context, SearchGroupType SearchGroupType, List<Store> stores, SliderLayout sliderLayout, TextView slideLabelType) {
        initSlider(context, SearchGroupType, stores, sliderLayout, slideLabelType);
    }

    /**
     *
     * @param context Context
     * @param SearchGroupType SearchGroupType
     * @param stores list of Store
     * @param sliderLayout SliderLayout
     * @return ImageSliderAdapter
     */
    public static ImageSliderAdapter getInstance(Context context, SearchGroupType SearchGroupType, List<Store> stores, SliderLayout sliderLayout) {
        return new ImageSliderAdapter(context, SearchGroupType, stores, sliderLayout);
    }

    /**
     *
     * @param context Context
     * @param SearchGroupType SearchGroupType
     * @param stores stores
     * @param sliderLayout sliderLayout
     * @param slideLabelType slideLabelType
     * @return ImageSliderAdapter
     */
    public static ImageSliderAdapter getInstance(Context context, SearchGroupType SearchGroupType, List<Store> stores, SliderLayout sliderLayout, TextView slideLabelType) {
        return new ImageSliderAdapter(context, SearchGroupType, stores, sliderLayout, slideLabelType);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        LoggerHelper.showDebugLog("Page desc " + slider.getUrl());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     *
     * @param context Context
     * @param searchGroupType SearchGroupType
     * @param stores list of Store
     * @param sliderLayout SliderLayout
     */
    private void initSlider(final Context context, final SearchGroupType searchGroupType, final List<Store> stores, SliderLayout sliderLayout, TextView slideLabelType) {
        if (stores != null) {
            for (final Store store : stores) {
                StoreSliderView storeSliderView = new StoreSliderView(context);
                // initialize a SliderLayout
                if (store.getName() == null || store.getName().isEmpty()) {
                    return;
                }

                String imageUrl = store.getLogoUrl();
                String storeName = store.getNameKh() != null && !store.getNameKh().isEmpty() ? store.getName() + "/" + store.getNameKh() : store.getName();

                storeSliderView
                        .description(storeName)
                        .image(imageUrl)
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);

                if (slideLabelType != null) {
                    slideLabelType.setVisibility(View.VISIBLE);
                    slideLabelType.setText(searchGroupType.getDesc());
                }

                //add your extra information
                storeSliderView.bundle(new Bundle());
                storeSliderView.getBundle()
                        .putString("extra", store.getName());

                storeSliderView.setOnSliderClickListener(slider -> {
                    Intent intent = new Intent(context, StoreListActivity.class);
                    intent.putExtra(ConstantValue.SEARCH_GROUP_TYPE, searchGroupType);
                    List<Store> stores1 = new ArrayList<>(stores);
                    if (!stores1.get(0).getId().equals(store.getId())) {
                        stores1.add(stores1.get(0));
                        stores1.remove(store);
                        stores1.set(0, store);
                    }
                    intent.putExtra(ConstantValue.STORES, (ArrayList<Store>) stores1);
                    context.startActivity(intent);
                });
                sliderLayout.addSlider(storeSliderView);
            }

            sliderLayout.getPagerIndicator().setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
            sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
            sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
            sliderLayout.setDuration(4000);
            sliderLayout.addOnPageChangeListener(this);
        }
    }

    /**
     *
     * @param context context
     * @param searchGroupType searchGroupType
     * @param stores stores
     * @param sliderLayout sliderLayout
     */
    private void initSlider(final Context context, final SearchGroupType searchGroupType, final List<Store> stores, SliderLayout sliderLayout) {
        initSlider(context, searchGroupType, stores, sliderLayout, null);
    }
}
