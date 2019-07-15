package com.planb.thespeed.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.planb.thespeed.R;
import com.planb.thespeed.adapter.SearchResultStickyAdapter;
import com.planb.thespeed.constant.ConstantValue;
import com.planb.thespeed.model.Address;
import com.planb.thespeed.model.magento.search.SearchStoreRestriction;
import com.planb.thespeed.model.magento.search.searchResult.SearchProductResult;
import com.planb.thespeed.model.magento.search.searchResult.SearchResult;
import com.planb.thespeed.model.magento.search.searchResult.SearchStoreResult;
import com.planb.thespeed.model.magento.store.storeList.ListStore;
import com.planb.thespeed.model.magento.store.storeList.StoreRestriction;
import com.planb.thespeed.model.modelForView.Store;
import com.planb.thespeed.service.StoreService;
import com.planb.thespeed.service.datahelper.datasource.online.FetchSearchResult;
import com.planb.thespeed.service.datahelper.mapper.DataMatcher;
import com.planb.thespeed.util.ExceptionUtils;
import com.planb.thespeed.util.InputHelper;
import com.planb.thespeed.util.preference.LocationPreference;
import com.planb.thespeed.util.LoggerHelper;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author channarith.bong
 */
public class SearchResultActivity extends AppCompatActivity implements View.OnClickListener, SearchResultStickyAdapter.OnItemSelect {

    private RecyclerView list_search_result;
    private EditText edt_search_input;
    private ImageButton img_btn_search;
    private ImageButton img_btn_back;
    private View container_float_loading;
    private ProgressBar loading_cycle_i;
    private ProgressBar loadStoreProgressBar;
    private TextView txt_search_indicator;
    private SearchResultStickyAdapter stickyAdapter;

    private Address address;
    private String searchKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        container_float_loading = findViewById(R.id.container_float_loading);
        list_search_result = findViewById(R.id.list_search_result);
        edt_search_input = findViewById(R.id.edt_search_input);
        img_btn_search = findViewById(R.id.img_btn_search);
        img_btn_back = findViewById(R.id.img_btn_back);
        loading_cycle_i = findViewById(R.id.loading_cycle_ii);
        txt_search_indicator = findViewById(R.id.txt_search_indicator);
        loadStoreProgressBar = findViewById(R.id.progress_bar_load_store);
        img_btn_search.setOnClickListener(this);
        img_btn_back.setOnClickListener(this);

        edt_search_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    bindData();
                    return true;
                }
                return false;
            }
        });

        edt_search_input.requestFocus();

        address = LocationPreference.getLocation(this);
    }

    @Override
    public void onClick(View view) {
        if (img_btn_back.equals(view)) {
            finish();
        } else if (img_btn_search.equals(view)) {
            bindData();
        }
    }

    @Override
    public void onSelect(Store store) {
        openStore(store);
    }

    /**
     * @param store
     */
    private void openStore(Store store) {
        if (loadStoreProgressBar.getVisibility() == View.GONE) {
            toggleLoadStoreProgressBar(true);
            loadListStore(prepareForGetStore(store.getId()));
        }
    }

    /**
     *
     * @param storeRestriction
     */
    private void loadListStore(StoreRestriction storeRestriction) {

        if (storeRestriction == null) {
            return;
        }

        StoreService.getInstance().loadStoreList(storeRestriction, new StoreService.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(ListStore listStore) {
                toggleLoadStoreProgressBar(false);
                if (listStore.getList() != null && listStore.getList().size() > 0) {
                    Intent intent = new Intent(SearchResultActivity.this, StoreActivity.class);
                    intent.putExtra(ConstantValue.STORE, DataMatcher.getInstance().getStore(listStore.getList().get(0)));
                    Log.d("StoreService", DataMatcher.getInstance().getStore(listStore.getList().get(0)) + "");
                    startActivity(intent);
                }
                else {
                    Toast.makeText(SearchResultActivity.this, "Cannot get store.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
                LoggerHelper.showErrorLog("Error: " + e.getMessage());
                toggleLoadStoreProgressBar(false);
            }
        });
    }

    /**
     *
     * @param storeId
     * @return
     */
    private StoreRestriction prepareForGetStore(Long storeId) {
        StoreRestriction storeRestriction = new StoreRestriction();
        SearchStoreRestriction restriction = new SearchStoreRestriction();
        restriction.setId(storeId);
        if (address.getLatitude() != null && address.getLongitude() != null) {
            restriction.setLatitude(address.getLatitude());
            restriction.setLongitude(address.getLongitude());
        }
        else {
            Toast.makeText(this, "Cannot reorder: Address not found.", Toast.LENGTH_SHORT).show();
            return null;
        }
        storeRestriction.setStoreRestriction(restriction);
        return storeRestriction;
    }

    /**
     *
     */
    private void bindData() {
        if (stickyAdapter != null) {
            stickyAdapter.clearList();
        }
        if (validateInputText()) {
            if (address != null && address.getLatitude() != null && address.getLongitude() != null) {
                loadProgressBar(true, true, null);
                requestQuery(searchKey, address.getLatitude(), address.getLongitude());
            }
            else {
                Toast.makeText(this, "Cannot detect location. Results not found.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * @param searchResults
     */
    private void bindViewWithList(List<SearchResult> searchResults, String searchText) {
        if (searchResults == null) {
            //loadProgressBar(true, false, "Cannot found any result!");
        } else {
            loadProgressBar(false, false, null);
            SearchResult searchResult = searchResults.get(0);
            List<SearchStoreResult> storeResults = Observable.fromIterable(searchResult.getStores())
//                    .filter(searchStoreResult -> searchStoreResult.getDistant() <= ApplicationConfiguration.MAX_DISTANT_ALLOW )
                    .toList().blockingGet();
            List<SearchProductResult> productResults = Observable.fromIterable(searchResult.getProducts())
//                    .filter(searchProductResult -> {
//                        if (searchProductResult.getStores() != null && !searchProductResult.getStores().isEmpty()) {
//                            if (searchProductResult.getStores().get(0).getDistant() <= ApplicationConfiguration.MAX_DISTANT_ALLOW) {
//                                return true;
//                            }
//                        }
//                        return false;
//                    })
                    .toList().blockingGet();
            searchResult.setStores(storeResults);
            searchResult.setProducts(productResults);
            if (searchResult.getProducts().isEmpty() && searchResult.getStores().isEmpty()) {
                loadProgressBar(true, false, "Cannot found any result!");
                return;
            }
            LoggerHelper.showDebugLog("Search Result " +
                    "Product " + searchResult.getProducts().size() +
                    ", Store " + searchResult.getStores().size()
            );
            stickyAdapter = new SearchResultStickyAdapter(this, searchResult, searchText, this);
            list_search_result.setHasFixedSize(true);
            list_search_result.setLayoutManager(new LinearLayoutManager(this));
            list_search_result.setAdapter(stickyAdapter);
        }
    }

    /**
     * @return boolean
     */
    private boolean validateInputText() {
        InputHelper.hideKeyboard(this);
        searchKey = edt_search_input.getText().toString().trim();
        if (!searchKey.isEmpty()) {
            edt_search_input.getText().clear();
            return true;
        }
        loadProgressBar(true, false, "Try with Store or Product name!");
        edt_search_input.getText().clear();
        return false;
    }

    /**
     * @param searchKey
     */
    private void requestQuery(String searchKey, Double latitude, Double longitude) {
        new FetchSearchResult(searchKey, latitude, longitude, new FetchSearchResult.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(List<SearchResult> searchResults) {
                bindViewWithList(searchResults, searchKey);
            }

            @Override
            public void onError(Throwable e) {
                loadProgressBar(false, false, null);
                LoggerHelper.showErrorLog("Search Error: " + ExceptionUtils.translateExceptionMessage(e));
            }
        });
    }

    /**
     * @param show
     * @param indicator
     * @param textIndicator
     */
    private void loadProgressBar(boolean show, boolean indicator, String textIndicator) {
        container_float_loading.setVisibility(show ? View.VISIBLE : View.GONE);
        loading_cycle_i.setVisibility(indicator ? View.VISIBLE : View.GONE);
        txt_search_indicator.setText(textIndicator == null ? "Searching" : textIndicator);
    }

    /**
     * toggle load store progress bar
     * @param show
     */
    private void  toggleLoadStoreProgressBar(boolean show) {
        loadStoreProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}
