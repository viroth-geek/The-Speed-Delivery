package com.planb.thespeed.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.planb.thespeed.R;
import com.planb.thespeed.model.magento.search.searchResult.SearchProductResult;
import com.planb.thespeed.model.magento.search.searchResult.SearchResult;
import com.planb.thespeed.model.magento.search.searchResult.SearchStoreResult;
import com.planb.thespeed.model.modelForView.ItemListAdaptor;
import com.planb.thespeed.model.modelForView.Store;
import com.planb.thespeed.model.modelForView.Tag;
import com.planb.thespeed.util.ImageViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by channarith.bong on 12/6/17.
 *
 * @author channarith.bong
 */
public class SearchResultStickyAdapter extends RecyclerView.Adapter {

    private static final String HEADER_PRODUCT = "Product";
    private static final String HEADER_STORE = "Store";

    private String searchText;

    private Context context;
    private List<ItemListAdaptor> itemLists;

    private OnItemSelect onItemSelect;

    /**
     *
     * @param context Context
     * @param searchResults SearchResult
     * @param onItemSelect OnItemSelect
     */
    public SearchResultStickyAdapter(Context context, SearchResult searchResults, String searchText, OnItemSelect onItemSelect) {
        this.context = context;
        this.onItemSelect = onItemSelect;
        this.itemLists = getDataItemList(searchResults);
        this.searchText = searchText;
    }

    /**
     * get spannable String
     * @param fullString String
     * @param stringForHighLight String
     * @return SpannableString
     */
    private SpannableString getSpannableString(String fullString, String stringForHighLight) {
        //Get the text from text view and create a spannable string
        SpannableString spannableString = new SpannableString(fullString);
        //Get the previous spans and remove them
        BackgroundColorSpan[] backgroundSpans = spannableString.getSpans(0, spannableString.length(), BackgroundColorSpan.class);

        for (BackgroundColorSpan span: backgroundSpans) {
            spannableString.removeSpan(span);
        }

        //Search for all occurrences of the keyword in the string
        int indexOfKeyword = spannableString.toString().indexOf(stringForHighLight);

        while (indexOfKeyword > 0) {
            //Create a background color span on the keyword
            spannableString.setSpan(new BackgroundColorSpan(Color.YELLOW), indexOfKeyword, indexOfKeyword + stringForHighLight.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            //Get the next index of the keyword
            indexOfKeyword = spannableString.toString().indexOf(stringForHighLight, indexOfKeyword + stringForHighLight.length());
        }

        //Set the final text on TextView
        return spannableString;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case ItemListAdaptor.HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_header_section, null);
                return new ViewHolderHeader(view);
            case ItemListAdaptor.SECTION_TYPE_STO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_search_store_item, null);
                return new ViewHolderStore(view);
            case ItemListAdaptor.SECTION_TYPE_PRO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_search_product_item, null);
                return new ViewHolderProduct(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemListAdaptor listAdaptor = itemLists.get(position);
        switch (listAdaptor.getType()) {
            case ItemListAdaptor.HEADER:
                loadHeaderViewByItemPosition((ViewHolderHeader) holder, listAdaptor);
                break;
            case ItemListAdaptor.SECTION_TYPE_PRO:
                loadProductViewByItemPosition((ViewHolderProduct) holder, listAdaptor);
                break;
            case ItemListAdaptor.SECTION_TYPE_STO:
                loadStoreViewByItemPosition((ViewHolderStore) holder, listAdaptor);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    @Override
    public int getItemViewType(int position) {
        return itemLists.get(position).getType();
    }

    /**
     * @param holderHeader ViewHolderHeader
     * @param listAdaptor ItemListAdaptor
     */
    private void loadHeaderViewByItemPosition(ViewHolderHeader holderHeader, ItemListAdaptor listAdaptor) {
        if (listAdaptor.getData() == null) {
            holderHeader.txt_header.setText(listAdaptor.getTag());
        }
    }

    /**
     * @param storeResult SearchStoreResult
     */
    private void openSelectedStore(SearchStoreResult storeResult) {
        Store store = new Store();
        store.setId(storeResult.getId());
        store.setDescription(storeResult.getStoreDescription());
        store.setImageUrl(storeResult.getStoreImageUrl());
        store.setLogoUrl(storeResult.getStoreLogoUrl());
        store.setOpenHour(storeResult.getStoreOpenHour());
        store.setName(storeResult.getStoreName());
        store.setPercentage(storeResult.getStorePercentage());
        store.setRate(storeResult.getStoreRate());
        List<Tag> tagList = storeResult.getStoreTags();
        StringBuilder tags = new StringBuilder();
        for (Tag tag : tagList) {
            tags.append(tag.getName()).append(tagList.indexOf(tag) == (tagList.size() - 1) ? "" : ",");
        }

        store.setTag(tags.toString());
        onItemSelect.onSelect(store);
    }

    /**
     * @param holderProduct ViewHolderProduct
     * @param listAdaptor ItemListAdaptor
     */
    private void loadProductViewByItemPosition(ViewHolderProduct holderProduct, ItemListAdaptor listAdaptor) {
        if (listAdaptor.getData() != null) {
            SearchProductResult productResult = (SearchProductResult) listAdaptor.getData();
            holderProduct.txt_product_name.setText(getSpannableString(productResult.getName(), this.searchText));
            holderProduct.txt_store_name.setText(getSpannableString(productResult.getStores().get(0).getStoreName(), this.searchText));
            ImageViewUtil.loadImageByUrl(context, productResult.getImagUrl(), holderProduct.img_product);
        }
    }

    /**
     * @param holderStore ViewHolderStore
     * @param listAdaptor ItemListAdaptor
     */
    private void loadStoreViewByItemPosition(ViewHolderStore holderStore, ItemListAdaptor listAdaptor) {
        if (listAdaptor.getData() != null) {
            SearchStoreResult storeResult = (SearchStoreResult) listAdaptor.getData();
            holderStore.txt_store_name.setText(getSpannableString(storeResult.getStoreName(), this.searchText));
            holderStore.txt_store_desc.setText(storeResult.getStoreDescription());

            Log.d("ooooo", storeResult.getOpen() + "");
            holderStore.txt_is_open.setText(storeResult.getOpen() ? "Open" : "Close");
            ImageViewUtil.loadImageByUrl(context, storeResult.getStoreLogoUrl(), holderStore.img_store_logo);
        }
    }

    /**
     * @param result SearchResult
     * @return list of ItemListAdaptor
     */
    private List<ItemListAdaptor> getDataItemList(SearchResult result) {
        List<ItemListAdaptor> list = new ArrayList<>();
        if (result.getStores() != null) {
            if (!result.getStores().isEmpty()) {
                list.add(new ItemListAdaptor(ItemListAdaptor.HEADER, HEADER_STORE, null));
                for (SearchStoreResult storeResult : result.getStores()) {
                    ItemListAdaptor listAdaptor = new ItemListAdaptor(ItemListAdaptor.SECTION_TYPE_STO,
                            HEADER_STORE, storeResult);
                    list.add(listAdaptor);
                }
            }
        }
        if (result.getProducts() != null) {
            if (!result.getProducts().isEmpty()) {
                list.add(new ItemListAdaptor(ItemListAdaptor.HEADER, HEADER_PRODUCT, null));
                for (SearchProductResult productResult : result.getProducts()) {
                    ItemListAdaptor listAdaptor = new ItemListAdaptor(ItemListAdaptor.SECTION_TYPE_PRO,
                            HEADER_PRODUCT, productResult);

                    //filter product has no store
                    if (productResult.getStores() != null) {
                        if (productResult.getStores().size() > 0) {
                            list.add(listAdaptor);
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     *
     */
    private class ViewHolderHeader extends RecyclerView.ViewHolder {

        TextView txt_header;

        /**
         * @param itemView View
         */
        ViewHolderHeader(final View itemView) {
            super(itemView);
            txt_header = itemView.findViewById(R.id.txt_header_section);
        }
    }

    /**
     *
     */
    private class ViewHolderProduct extends RecyclerView.ViewHolder {

        TextView txt_product_name;
        TextView txt_store_name;
        ImageView img_product;

        /**
         * @param itemView View
         */
        ViewHolderProduct(final View itemView) {
            super(itemView);
            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            txt_store_name = itemView.findViewById(R.id.txt_store_name);
            img_product = itemView.findViewById(R.id.img_product);

            itemView.setOnClickListener(view -> {
                SearchProductResult result = (SearchProductResult) itemLists.get(getAdapterPosition()).getData();
                openSelectedStore(result.getStores().get(0));
//                Toast.makeText(context, "Product ID : " + result.getId() + " Store ID " + result.getStores().get(0).getId(), Toast.LENGTH_SHORT).show();
            });
        }
    }

    /**
     *
     */
    private class ViewHolderStore extends RecyclerView.ViewHolder {

        TextView txt_store_name;
        TextView txt_is_open;
        TextView txt_store_desc;
        ImageView img_store_logo;

        /**
         * @param itemView View
         */
        ViewHolderStore(final View itemView) {
            super(itemView);
            txt_is_open = itemView.findViewById(R.id.txt_is_open);
            txt_store_name = itemView.findViewById(R.id.txt_store_name);
            txt_store_desc = itemView.findViewById(R.id.txt_store_desc);
            img_store_logo = itemView.findViewById(R.id.img_store_logo);
            itemView.setOnClickListener(view -> {
                SearchStoreResult result = (SearchStoreResult) itemLists.get(getAdapterPosition()).getData();
                openSelectedStore(result);
            });
        }
    }

    /**
     *
     */
    public void clearList() {
        if (itemLists != null) {
            itemLists.clear();
            notifyDataSetChanged();
        }
    }

    /**
     *
     */
    public interface OnItemSelect {
        void onSelect(Store store);
    }

}
