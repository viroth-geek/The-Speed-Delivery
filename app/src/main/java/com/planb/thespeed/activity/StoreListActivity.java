package com.planb.thespeed.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.planb.thespeed.R;
import com.planb.thespeed.adapter.StoreRecyclerAdapter;
import com.planb.thespeed.constant.ConstantValue;
import com.planb.thespeed.fragment.time.NkrTimePicker;
import com.planb.thespeed.model.enumeration.SearchGroupType;
import com.planb.thespeed.model.modelForView.Store;
import com.jpardogo.android.googleprogressbar.library.NexusRotationCrossDrawable;

import java.util.List;

/**
 * @author channarith.bong
 */
public class StoreListActivity extends AppCompatActivity
        implements View.OnClickListener {

    private RecyclerView estoreList;
    private RelativeLayout loadingProgressBar;
    private Button btn_deliver_time;
    private Button btn_selected_location;
    private View container_location_time;
    private Toolbar toolbar;

    private boolean isShowAds = true;
    private String[] timeSelectionToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estore_list);
        toolbar = findViewById(R.id.toolbar);

        timeSelectionToday = getResources().getStringArray(R.array.static_deliver_time_today);
        container_location_time = findViewById(R.id.container_location_time);
        btn_deliver_time = findViewById(R.id.btn_deliver_time);
        btn_deliver_time.setOnClickListener(this);
        btn_selected_location = findViewById(R.id.btn_selected_location);
        btn_selected_location.setOnClickListener(this);
        loadingProgressBar = findViewById(R.id.loading_progress_bar);

        RecyclerView.LayoutManager layoutManager;
        if (getResources().getBoolean(R.bool.isLarge)) {
            layoutManager = new GridLayoutManager(this, 2);
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position != 0) {
                        return 1;
                    }
                    else {
                        return 2;
                    }
                }
            });
        } else {
            layoutManager = new LinearLayoutManager(this);
        }

        estoreList = findViewById(R.id.estore_list);
        estoreList.setHasFixedSize(true);
        estoreList.setLayoutManager(layoutManager);
        loadingProgressBar.setVisibility(View.VISIBLE);

        initToolbar();
        loadStoreList();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Rect bounds = loadingProgressBar.getIndeterminateDrawable().getBounds();
//        loadingProgressBar.setIndeterminateDrawable(getProgressDrawable());
//        loadingProgressBar.getIndeterminateDrawable().setBounds(bounds);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isShowAds) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == android.R.id.home) {
            if (!isShowAds) {
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (btn_deliver_time.equals(view)) {
            showTimePicker();
        } else if (btn_selected_location.equals(view)) {
            showLocationPicker();
        }
    }

    /**
     * Get all available store for agent
     */
    private void loadStoreList() {
        Intent intent = getIntent();
        if (intent.hasExtra(ConstantValue.STORES)) {
            List<Store> stores = (List<Store>) intent.getExtras().get(ConstantValue.STORES);
            if (stores != null) {
                StoreRecyclerAdapter adapter = new StoreRecyclerAdapter(StoreListActivity.this, stores, isShowAds);
                adapter.setLoadingProgressBar(loadingProgressBar);
                adapter.notifyDataSetChanged();
                estoreList.setAdapter(adapter);
                loadingProgressBar.setVisibility(View.GONE);
            }
        }
    }

    /**
     * Prepare view for setup Toolbar
     */
    private void initToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        SearchGroupType type = (SearchGroupType) getIntent().getSerializableExtra(ConstantValue.SEARCH_GROUP_TYPE);
        String tagName = getIntent().getStringExtra(ConstantValue.SEARCH_BY_TAG);
        if (type != null) {
            isShowAds = false;
            container_location_time.setVisibility(View.GONE);
            switch (type) {
                case SPONSOR:
                    getSupportActionBar().setTitle(R.string.sponsor_shop);
                    break;
                case RECOMMEND:
                    getSupportActionBar().setTitle(R.string.recommend_shop);
                    break;
                case MOST_LOVE:
                    getSupportActionBar().setTitle(R.string.most_love_shop);
                    break;
                case NEW_ARRIVAL:
                    getSupportActionBar().setTitle(R.string.new_arrival_shop);
                    break;
                case MOST_ORDER:
                    getSupportActionBar().setTitle(R.string.most_order_shop);
                    break;
                case CATEGORY:
                    getSupportActionBar().setTitle(tagName);
            }
        }
    }

    /**
     * Progress Animation Setting
     */
    private Drawable getProgressDrawable() {
        return new NexusRotationCrossDrawable.Builder(this)
                .colors(getProgressDrawableColors())
                .build();
    }

    /**
     * Set drawable to indicator in progress view
     *
     * @return int[]
     */
    private int[] getProgressDrawableColors() {
        int[] colors = new int[4];
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        colors[0] = prefs.getInt(getString(R.string.firstcolor_pref_key), getResources().getColor(R.color.red));
        colors[1] = prefs.getInt(getString(R.string.secondcolor_pref_key), getResources().getColor(R.color.blue));
        colors[2] = prefs.getInt(getString(R.string.thirdcolor_pref_key), getResources().getColor(R.color.yellow));
        colors[3] = prefs.getInt(getString(R.string.fourthcolor_pref_key), getResources().getColor(R.color.green));
        return colors;
    }

    /**
     * Show dialog for select time
     */
    private void showTimePicker() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        NkrTimePicker timePicker = new NkrTimePicker();
        timePicker.setTimeChangeListener(time -> {
            Button button = container_location_time.findViewById(R.id.btn_deliver_time);
            if (time != null) {
                switch (time.first) {
                    case TODAY:
                        if (timeSelectionToday[0].equals(time.second)) {
                            button.setText(String.format("Deliver %s", time.second));
                        } else {
                            button.setText(String.format("Today %s", time.second));
                        }
                        break;
                    case TOMORROW:
                        button.setText(String.format("Tomorrow %s", time.second));
                        break;
                }
            }
        });
        timePicker.show(fragmentManager, "TIME");
    }

    /**
     * Open Map screen to get the location
     */
    private void showLocationPicker() {
        Intent intent = new Intent(this, RegisterLocationActivity.class);
        startActivityForResult(intent, ConstantValue.LOCATION_ACTIVITY_TAG_CODE);
    }
}
