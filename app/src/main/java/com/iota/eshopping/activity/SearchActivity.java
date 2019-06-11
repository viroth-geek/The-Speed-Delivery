package com.iota.eshopping.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.iota.eshopping.R;
import com.iota.eshopping.adapter.ImageSliderAdapter;
import com.iota.eshopping.constant.ConstantValue;
import com.iota.eshopping.model.Address;
import com.iota.eshopping.model.Tag;
import com.iota.eshopping.model.enumeration.SearchGroupType;
import com.iota.eshopping.model.magento.search.SearchStoreRestriction;
import com.iota.eshopping.model.magento.store.storeList.ListStore;
import com.iota.eshopping.model.magento.store.storeList.StoreRestriction;
import com.iota.eshopping.model.modelForView.Store;
import com.iota.eshopping.service.StoreService;
import com.iota.eshopping.service.base.InvokeOnCompleteAsync;
import com.iota.eshopping.service.datahelper.datasource.online.FetchTag;
import com.iota.eshopping.service.datahelper.mapper.DataMatcher;
import com.iota.eshopping.util.AlertUtils;
import com.iota.eshopping.util.ExceptionUtils;
import com.iota.eshopping.util.LoggerHelper;
import com.iota.eshopping.util.preference.LocationPreference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;

/**
 * @author channarith.bong
 */
public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private SliderLayout img_sponsor_shop;
    private SliderLayout img_recommend_shop;
    private SliderLayout img_new_arrival_shop;
    private SliderLayout img_most_order_shop;
    private SliderLayout img_most_love_shop;

    private View progress_sponsor_stores;
    private View progress_recommended_stores;
    private View progress_new_stores;
    private View progress_most_order_stores;
    private View progress_most_love_stores;

    private TextView txtSponsorShop;
    private TextView txtNewArrivalShop;
    private TextView txtRecommendShop;
    private TextView txtMostOrderShop;
    private TextView txtMostLoveShop;

    private Address address;
    private View parentPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        parentPanel = findViewById(R.id.parent_panel);

        img_sponsor_shop = findViewById(R.id.img_sponsor_shop);
        img_new_arrival_shop = findViewById(R.id.img_new_shop);
        img_recommend_shop = findViewById(R.id.img_recommend_shop);
        img_most_order_shop = findViewById(R.id.img_most_order_shop);
        img_most_love_shop = findViewById(R.id.img_most_love_shop);

        progress_sponsor_stores = findViewById(R.id.progress_sponsor_stores);
        progress_recommended_stores = findViewById(R.id.progress_recommended_stores);
        progress_new_stores = findViewById(R.id.progress_new_stores);
        progress_most_order_stores = findViewById(R.id.progress_most_order_stores);
        progress_most_love_stores = findViewById(R.id.progress_most_love_stores);

        txtSponsorShop = findViewById(R.id.txt_sponsor_shop);
        txtRecommendShop = findViewById(R.id.txt_recommend_shop);
        txtNewArrivalShop = findViewById(R.id.txt_new_arrival_shop);
        txtMostLoveShop = findViewById(R.id.txt_most_love_shop);
        txtMostOrderShop = findViewById(R.id.txt_most_order_shop);

        address = LocationPreference.getLocation(this);

        stopRecycle();

        if (address != null) {
            bindData();
        } else {
            progress_sponsor_stores.setVisibility(View.INVISIBLE);
            progress_recommended_stores.setVisibility(View.INVISIBLE);
            progress_new_stores.setVisibility(View.INVISIBLE);
            progress_most_order_stores.setVisibility(View.INVISIBLE);
            progress_most_order_stores.setVisibility(View.INVISIBLE);
            progress_most_love_stores.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            finish();
        } else if (R.id.action_search == item.getItemId()) {
            Intent intent = new Intent(this, SearchResultActivity.class);
            finish();
            startActivityForResult(intent, ConstantValue.INTENT_ACTIVITY_TAG_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        menu.findItem(R.id.action_search);
        /*SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }*/
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStop() {
        stopRecycle();
        super.onStop();
    }

    @Override
    protected void onRestart() {
        startRecycle();
        super.onRestart();
    }

    /**
     * Request data from server
     */
    private void bindData() {
        fetchRecommendedStores();
        fetchSponsorStores();
        fetchNewStores();
        fetchTags();
    }

    /**
     * @param tags List<Tag>
     */
    private void bindTags(List<Tag> tags) {

        List<String> tagsArr = new ArrayList<>();
        for (int i = 0; i < tags.size(); i++) {
            tagsArr.add(tags.get(i).getName());
        }

        TagGroup mTagGroup = findViewById(R.id.tag_group);
        mTagGroup.setTags(tagsArr);
        mTagGroup.setOnTagClickListener(tagString -> {
            int index = tagsArr.indexOf(tagString);
            Tag tag = tags.get(index);
            fetchStoresByTag(Arrays.asList(tag.getId()), tag);
        });
    }

    /**
     * @return restriction
     */
    private StoreRestriction prepareStore(SearchStoreRestriction restriction) {
        StoreRestriction storeRestriction = new StoreRestriction();
        restriction.setPage(1);
        restriction.setLimit(100);
        storeRestriction.setStoreRestriction(restriction);
        return storeRestriction;
    }

    /**
     * Request Sponsor Store
     */
    private void fetchSponsorStores() {
        SearchStoreRestriction restriction = new SearchStoreRestriction();
        restriction.setIsSponsor(true);
        restriction.setLongitude(address.getLongitude());
        restriction.setLatitude(address.getLatitude());
        loadListStore(prepareStore(restriction), SearchGroupType.SPONSOR, img_sponsor_shop);
    }

    /**
     *
     */
    private void fetchTags() {
        new FetchTag(new InvokeOnCompleteAsync<List<Tag>>() {
            @Override
            public void onComplete(List<Tag> tags) {
                bindTags(tags);
            }

            @Override
            public void onError(Throwable e) {
                LoggerHelper.showErrorLog(e.getMessage(), e);
            }
        });
    }

    /**
     *
     */
    private void fetchRecommendedStores() {
        SearchStoreRestriction restriction = new SearchStoreRestriction();
        restriction.setIsRecommended(true);
        restriction.setLongitude(address.getLongitude());
        restriction.setLatitude(address.getLatitude());
        loadListStore(prepareStore(restriction), SearchGroupType.RECOMMEND, img_recommend_shop);
    }

    /**
     *
     */
    private void fetchNewStores() {
        SearchStoreRestriction restriction = new SearchStoreRestriction();
        restriction.setIsNew(true);
        restriction.setLongitude(address.getLongitude());
        restriction.setLatitude(address.getLatitude());
        loadListStore(prepareStore(restriction), SearchGroupType.NEW_ARRIVAL, img_new_arrival_shop);
    }

    /**
     *
     */
    private void fetchStoresByTag(List<Long> tagsList, Tag tag) {
        SearchStoreRestriction restriction = new SearchStoreRestriction();
        restriction.setStoreTages(tagsList);
        restriction.setLongitude(address.getLongitude());
        restriction.setLatitude(address.getLatitude());
        listStore(prepareStore(restriction), tag);
    }

    /**
     *
     */
    private void stopRecycle() {
        img_sponsor_shop.stopAutoCycle();
        img_recommend_shop.stopAutoCycle();
        img_new_arrival_shop.stopAutoCycle();
        img_most_order_shop.stopAutoCycle();
        img_most_love_shop.stopAutoCycle();
    }

    /**
     *
     */
    private void startRecycle() {
        img_sponsor_shop.startAutoCycle();
        img_recommend_shop.startAutoCycle();
        img_new_arrival_shop.startAutoCycle();
        img_most_order_shop.startAutoCycle();
        img_most_love_shop.startAutoCycle();
    }

    /**
     * @param show boolean
     * @param type SearchGroupType
     */
    private void showProgressBar(boolean show, SearchGroupType type) {
        switch (type) {
            case SPONSOR:
                if (show) {
                    progress_sponsor_stores.setVisibility(View.VISIBLE);
                } else {
                    progress_sponsor_stores.setVisibility(View.GONE);
                }
                break;
            case RECOMMEND:
                if (show) {
                    progress_recommended_stores.setVisibility(View.VISIBLE);
                } else {
                    progress_recommended_stores.setVisibility(View.GONE);
                }
                break;
            case NEW_ARRIVAL:
                if (show) {
                    progress_new_stores.setVisibility(View.VISIBLE);
                } else {
                    progress_new_stores.setVisibility(View.GONE);
                }
                break;
            case MOST_ORDER:
                if (show) {
                    progress_most_order_stores.setVisibility(View.VISIBLE);
                } else {
                    progress_most_order_stores.setVisibility(View.GONE);
                }
                break;
            case MOST_LOVE:
                if (show) {
                    progress_most_love_stores.setVisibility(View.VISIBLE);
                } else {
                    progress_most_love_stores.setVisibility(View.GONE);
                }
                break;
        }
    }

    /**
     * @param storeRestriction  StoreRestriction
     * @param type              SearchGroupType
     * @param sliderLayoutImage SliderLayout
     */
    private void loadListStore(StoreRestriction storeRestriction, final SearchGroupType type, final SliderLayout sliderLayoutImage) {
        StoreService.getInstance().loadStoreList(storeRestriction, new StoreService.InvokeOnCompleteAsync() {

            @Override
            public void onComplete(ListStore listStore) {
                if (listStore != null) {
                    List<Store> stores = DataMatcher.getInstance().getStoreList(listStore.getList());
                    showProgressBar(false, type);
                    switch (type) {
                        case SPONSOR:
                            if (!stores.isEmpty()) {
                                txtSponsorShop.setVisibility(View.GONE);
                            }
                            break;
                        case MOST_LOVE:
                            if (!stores.isEmpty()) {
                                txtMostLoveShop.setVisibility(View.GONE);
                            }
                            break;
                        case RECOMMEND:
                            if (!stores.isEmpty()) {
                                txtRecommendShop.setVisibility(View.GONE);
                            }
                            break;
                        case MOST_ORDER:
                            if (!stores.isEmpty()) {
                                txtMostOrderShop.setVisibility(View.GONE);
                            }
                            break;
                        case NEW_ARRIVAL:
                            if (!stores.isEmpty()) {
                                txtNewArrivalShop.setVisibility(View.GONE);
                            }
                            break;
                    }
                    if (!stores.isEmpty()) {
                        ImageSliderAdapter.getInstance(SearchActivity.this, type, stores, sliderLayoutImage);
                        startRecycle();
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(SearchActivity.this, "Error: " + ExceptionUtils.translateExceptionMessage(e), Toast.LENGTH_SHORT).show();
                LoggerHelper.showErrorLog(" Store ", e);
            }
        });
    }

    /**
     * @param storeRestriction StoreRestriction
     * @param tag              Tag
     */
    private void listStore(StoreRestriction storeRestriction, Tag tag) {

        Snackbar snackbar = Snackbar.make(parentPanel, "Loading...", Snackbar.LENGTH_INDEFINITE);
        if (!snackbar.isShown()) {
            snackbar.show();
        }

        StoreService.getInstance().loadStoreList(storeRestriction, new StoreService.InvokeOnCompleteAsync() {

            @Override
            public void onComplete(ListStore listStore) {
                List<Store> stores = DataMatcher.getInstance().getStoreList(listStore.getList());
                snackbar.dismiss();
                if (stores != null && !stores.isEmpty()) {
                    Intent intent = new Intent(SearchActivity.this, StoreListActivity.class);
                    intent.putExtra(ConstantValue.SEARCH_GROUP_TYPE, SearchGroupType.CATEGORY);
                    intent.putExtra(ConstantValue.SEARCH_BY_TAG, tag.getName());
                    intent.putExtra(ConstantValue.STORES, (ArrayList<Store>) stores);
                    startActivity(intent);
                } else {
                    AlertUtils.showMessage(SearchActivity.this, "Message", "No store available.");
                }
            }

            @Override
            public void onError(Throwable e) {
                snackbar.dismiss();
                Toast.makeText(SearchActivity.this, "Error: " + ExceptionUtils.translateExceptionMessage(e), Toast.LENGTH_SHORT).show();
                LoggerHelper.showErrorLog(" Store ", e);
            }
        });
    }
}
