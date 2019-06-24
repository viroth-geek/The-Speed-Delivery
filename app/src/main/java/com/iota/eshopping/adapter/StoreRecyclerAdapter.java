package com.iota.eshopping.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.daimajia.slider.library.SliderLayout;
import com.iota.eshopping.R;
import com.iota.eshopping.activity.PromotionActivity;
import com.iota.eshopping.activity.StoreActivity;
import com.iota.eshopping.constant.ConstantValue;
import com.iota.eshopping.model.Address;
import com.iota.eshopping.model.enumeration.SearchGroupType;
import com.iota.eshopping.model.magento.search.SearchStoreRestriction;
import com.iota.eshopping.model.magento.store.storeList.ListStore;
import com.iota.eshopping.model.magento.store.storeList.StoreRestriction;
import com.iota.eshopping.model.modelForView.Store;
import com.iota.eshopping.service.StoreService;
import com.iota.eshopping.service.datahelper.mapper.DataMatcher;
import com.iota.eshopping.util.FontManager;
import com.iota.eshopping.util.ImageViewUtil;
import com.iota.eshopping.util.LoggerHelper;
import com.iota.eshopping.util.preference.LocationPreference;

import java.util.List;

/**
 * @author channarith.bong
 */
public class StoreRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int ADS = 0;
    private final int BODY = 1;
    private boolean isShowAds = true;

    private List<Store> storeList;
    private Context context;

    private RelativeLayout loadingProgressBar;

    /**
     * @param context Context
     * @param storeList list of Store
     * @param isShowAds boolean
     */
    public StoreRecyclerAdapter(Context context, List<Store> storeList, boolean isShowAds) {
        this.context = context;
        this.storeList = storeList;
        this.isShowAds = isShowAds;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case ADS:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.view_ads_1x2, parent, false);
                return new AdsStoreRowViewHolder(view);

            case BODY:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.view_estore_list, parent, false);
                return new NormalStoreRowViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof AdsStoreRowViewHolder) {
            AdsStoreRowViewHolder adsStoreRowViewHolder = (AdsStoreRowViewHolder) holder;
            fetchStoreByType(SearchGroupType.SPONSOR, adsStoreRowViewHolder.txt_ads1x2left, adsStoreRowViewHolder.slideLabelSponsor);
            fetchStoreByType(SearchGroupType.NEW_ARRIVAL, adsStoreRowViewHolder.txt_ads1x2right, adsStoreRowViewHolder.slideLabelRecommend);
        }

        if (holder instanceof NormalStoreRowViewHolder) {
            NormalStoreRowViewHolder vh = (NormalStoreRowViewHolder) holder;
            if (isShowAds) {
                position -= 1;
            }
            Store store = storeList.get(position);
            vh.txt_estore_type.setText(store.getName());
            if (store.getNameKh() != null && !store.getNameKh().isEmpty()) {
                vh.txt_estore.setText(store.getNameKh());
            }
            else {
                vh.txt_estore.setText(store.getName());
            }

//            StringBuilder estoreType = new StringBuilder();
//            if (store.getStoreTypes() != null) {
//                for (StoreType type : store.getStoreTypes()) {
//                    if (store.getStoreTypes().lastIndexOf(type) == store.getStoreTypes().size() - 1) {
//                        estoreType.append(type.getTypeName()).append(".");
//                    } else {
//                        estoreType.append(type.getTypeName()).append(", ");
//                    }
//                }
//            }

            Typeface typeface = FontManager.getTypeface(this.context, FontManager.FONTAWESOME);
            String tag = context.getString(R.string.fa_tag) + " " + store.getTag();
            if (store.getTag().isEmpty()) {
                tag = "";
            }

            String openHour = store.getOpenHour();
            if (openHour == null || openHour.isEmpty()) {
                openHour = "None";
            }

//            vh.txt_estore_type.setText(estoreType);
            if (!store.isOpenToday()) {
                vh.txt_is_open.setTextColor(context.getResources().getColor(R.color.red));
            } else {
                vh.txt_is_open.setTextColor(context.getResources().getColor(R.color.green));
            }
            vh.txt_is_open.setText(store.isOpenToday() ? "Open" : "Closed");
            vh.txt_percentage.setTypeface(typeface);
            vh.txt_percentage.setText(String.format("%s %s %%", context.getString(R.string.fa_smile_o), store.getPercentage()));
            vh.txt_rating.setText(String.format("%s", store.getRate()));
            vh.txt_tag.setTypeface(typeface);
            vh.txt_tag.setText(tag);
            vh.txt_time.setText(openHour);
            vh.context = context;
            vh.estoreId = storeList.get(position).getId();
            vh.store = storeList.get(position);

            String imageUrl = store.getLogoUrl();
            ImageViewUtil.loadImageByUrl(context, imageUrl, vh.img_estore, new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    vh.storeImageProgressBar.setVisibility(View.GONE);
                    vh.img_estore.requestLayout();
                    vh.img_estore.invalidate();
                    return false;
                }
            });

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            if (isShowAds) {
                return ADS;
            }
            return BODY;
        } else {
            return BODY;
        }
    }

    @Override
    public int getItemCount() {
        if (isShowAds) {
            return (storeList.size() + 1);
        }
        return storeList.size();
    }

    /**
     *
     * @param searchGroupType SearchGroupType
     * @param sliderLayout SliderLayout
     */
    private void fetchStoreByType(SearchGroupType searchGroupType, SliderLayout sliderLayout, TextView slideLabelType) {
        Address address = LocationPreference.getLocation(context);
        StoreRestriction storeRestriction = new StoreRestriction();
        SearchStoreRestriction restriction = new SearchStoreRestriction();
        restriction.setPage(1);
        restriction.setLimit(100);

        if (searchGroupType == SearchGroupType.SPONSOR) {
            restriction.setIsSponsor(true);
        } else if (searchGroupType == SearchGroupType.NEW_ARRIVAL) {
            restriction.setIsNew(true);
        }

        if (address != null) {
            restriction.setLatitude(address.getLatitude());
            restriction.setLongitude(address.getLongitude());
        }
        storeRestriction.setStoreRestriction(restriction);
        loadListStore(storeRestriction, searchGroupType, sliderLayout, slideLabelType);
    }

    /**
     *
     * @param storeRestriction StoreRestriction
     * @param type SearchGroupType
     * @param sliderLayoutImage SliderLayout
     */
    private void loadListStore(StoreRestriction storeRestriction, final SearchGroupType type, final SliderLayout sliderLayoutImage, final TextView slideLabelType) {
        StoreService.getInstance().loadStoreList(storeRestriction, new StoreService.InvokeOnCompleteAsync() {

            @Override
            public void onComplete(ListStore listStore) {
                sliderLayoutImage.removeAllSliders();
                if (listStore.getList() != null) {
                    ImageSliderAdapter.getInstance(context, type, DataMatcher.getInstance().getStoreList(listStore.getList()), sliderLayoutImage, slideLabelType);
                }
            }

            @Override
            public void onError(Throwable e) {
                LoggerHelper.showErrorLog(" Store " + e.getMessage());
            }
        });
    }

    /**
     * Setter loadingProgressBar
     */
    public void setLoadingProgressBar(RelativeLayout loadingProgressBar) {
        this.loadingProgressBar = loadingProgressBar;
    }

    /**
     * Normal Store Row
     */
    public class NormalStoreRowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_percentage;
        TextView txt_rating;
        TextView txt_tag;
        TextView txt_estore_type;
        TextView txt_estore;
        TextView txt_time;
        TextView txt_distant;
        TextView txt_is_open;
        ImageView img_estore;
        ProgressBar storeImageProgressBar;

        Context context;
        Long estoreId;
        Store store;

        /**
         * @param view View
         */
        NormalStoreRowViewHolder(View view) {
            super(view);
            txt_estore = view.findViewById(R.id.txt_estore_name);
            txt_estore_type = view.findViewById(R.id.txt_estore_type_list);
            txt_tag = view.findViewById(R.id.txt_tag);
            txt_rating = view.findViewById(R.id.txt_rating);
            txt_percentage = view.findViewById(R.id.txt_percentage);
            txt_time = view.findViewById(R.id.txt_time);
            txt_distant = view.findViewById(R.id.txt_distant);
            txt_is_open = view.findViewById(R.id.txt_is_open);
            img_estore = view.findViewById(R.id.img_estore);
            storeImageProgressBar = view.findViewById(R.id.store_image_progress_bar);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (loadingProgressBar.getVisibility() == View.GONE) {
                if (context != null && estoreId != null) {
                    int position = getAdapterPosition();
                    if (isShowAds) {
                        position -= 1;
                    }
                    Store store = storeList.get(position);
                    Intent intent = new Intent(context, StoreActivity.class);
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, img_estore, "image_store");
                    intent.putExtra(ConstantValue.STORE, store);
//                    intent.putExtra(ConstantValue.STORE_IMAGE, ImageViewUtil.byteArray(img_estore));
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent, options.toBundle());
                }
            }
        }
    }

    /**
     * Sponsor/Recommended Store Row
     */
    public class AdsStoreRowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Context context;
        SliderLayout txt_ads1x2left;
        SliderLayout txt_ads1x2right;
        TextView slideLabelSponsor;
        TextView slideLabelRecommend;
//        TextView txt_ads1x2right;
//        ImageView img_ads1x2left;
//        ImageView img_ads1x2right;

        /**
         * @param view View
         */
        AdsStoreRowViewHolder(View view) {
            super(view);
            context = view.getContext();
            txt_ads1x2left = view.findViewById(R.id.txt_ads1x2left);
            txt_ads1x2right = view.findViewById(R.id.txt_ads1x2right);
            slideLabelSponsor = view.findViewById(R.id.slide_label_sponsor);
            slideLabelRecommend = view.findViewById(R.id.slide_label_recommend);

            slideLabelSponsor.setVisibility(View.GONE);
            slideLabelRecommend.setVisibility(View.GONE);
//            img_ads1x2left = view.findViewById(R.id.img_ads1x2left);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (context != null) {
                Intent intent = new Intent(context, PromotionActivity.class);
                context.startActivity(intent);
            }
        }
    }
}
