package com.iota.eshopping.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iota.eshopping.R;

/**
 * Created by channarith.bong on 11/30/17.
 *
 * @author channarith.bong
 */
public class StoreDetailRecyclerAdapter extends RecyclerView.Adapter<StoreRecyclerAdapter.NormalStoreRowViewHolder> {

    @Override
    public StoreRecyclerAdapter.NormalStoreRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(StoreRecyclerAdapter.NormalStoreRowViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     *
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_percentage;
        public TextView txt_rating;
        public TextView txt_tag;
        public TextView txt_categories;
        public TextView txt_estore;
        public TextView txt_time;

        /**
         * @param v
         */
        public ViewHolder(View v) {
            super(v);
            txt_estore = (TextView) v.findViewById(R.id.txt_estore_name);
            txt_categories = (TextView) v.findViewById(R.id.txt_estore_type_list);
            txt_tag = (TextView) v.findViewById(R.id.txt_tag);
            txt_rating = (TextView) v.findViewById(R.id.txt_rating);
            txt_percentage = (TextView) v.findViewById(R.id.txt_percentage);
            txt_time = (TextView) v.findViewById(R.id.txt_time);
        }
    }
}
