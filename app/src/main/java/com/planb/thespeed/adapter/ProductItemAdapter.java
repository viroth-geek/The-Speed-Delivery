package com.planb.thespeed.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.planb.thespeed.model.modelForView.Product;
import com.planb.thespeed.model.modelForView.ProductCategory;
import com.planb.thespeed.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by channarith.bong on 11/30/17.
 * @author channarith.bong
 */
public class ProductItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int HEADER = 0;
    private static final int CHILD = 1;

    private Map<Integer, Item> holderIndex;
    private Context context;

    /**
     *
     * @param context Context
     * @param data list of ProductCategory
     */
    public ProductItemAdapter(Context context, List<ProductCategory> data){
        this.context = context;
        this.holderIndex = Indexer(data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        switch (viewType) {
            case HEADER:
                view = inflater.inflate(R.layout.view_product_category, parent, false);
                ViewHolderHead header = new ViewHolderHead(view);
                return header;
            case CHILD:
                view = inflater.inflate(R.layout.view_product_item, parent, false);
                ViewHolderChild child = new ViewHolderChild(view);
                return child;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolderHead) {
            ViewHolderHead vholder = (ViewHolderHead) holder;
            Item item = holderIndex.get(position);
            String name = (String) item.getValue();
            vholder.txt_product_item_group.setText(name);

        } else if (holder instanceof ViewHolderChild) {
            ViewHolderChild vholder = (ViewHolderChild) holder;
            Item item = holderIndex.get(position);
            Product product = (Product) item.getValue();
            vholder.txt_product_item_name.setText(product.getName());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                vholder.txt_product_item_desc.setText(Html.fromHtml(product.getDetail(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                vholder.txt_product_item_desc.setText(Html.fromHtml(product.getDetail()));
            }
            vholder.txt_product_item_price.setText("$ " + product.getPrice());
            vholder.setProductItemId(product.getId());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return holderIndex.get(position).getType();
    }


    @Override
    public int getItemCount() {
        return holderIndex.size();
    }

    /**
     *
     * @param data
     * @return
     */
    private Map<Integer, Item> Indexer(List<ProductCategory> data){
        int i = -1;
        Map<Integer, Item> index = new HashMap<>();
        for (ProductCategory cat : data){
            i++;
            index.put(i, new Item(HEADER, cat.getName()));
            for (Product product : cat.getProducts()){
                i++;
                index.put(i, new Item(CHILD, product));
            }
        }
        return index;
    }

    /**
     *
     */
    public class Item {
        int type;
        Object value;

        /**
         *
         * @param type
         * @param value
         */
        public Item(int type, Object value) {
            this.type = type;
            this.value = value;
        }

        /**
         *
         * @return
         */
        public int getType() {
            return type;
        }

        /**
         *
         * @return
         */
        public Object getValue() {
            return value;
        }
    }

    /**
     * Header row
     */
    public class ViewHolderHead extends SectioningAdapter.HeaderViewHolder {

        public TextView txt_product_item_group;

        /**
         *
         * @param itemView
         */
        public ViewHolderHead(View itemView) {
            super(itemView);
            txt_product_item_group = itemView.findViewById(R.id.txt_product_category);
        }
    }

    /**
     * Child row
     */
    public class ViewHolderChild extends SectioningAdapter.ItemViewHolder {
        private Long productItemId;
        public TextView txt_product_item_name;
        public TextView txt_product_item_desc;
        public TextView txt_product_item_price;

        /**
         *
         * @param itemView
         */
        public ViewHolderChild(View itemView) {
            super(itemView);
            txt_product_item_name = itemView.findViewById(R.id.txt_product_item_name);
            txt_product_item_desc = itemView.findViewById(R.id.txt_product_item_desc);
            txt_product_item_price = itemView.findViewById(R.id.txt_product_item_price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, " Item click " + getProductItemId(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        /**
         *
         * @return
         */
        public Long getProductItemId() {
            return productItemId;
        }

        /**
         *
         * @param productItemId
         */
        public void setProductItemId(Long productItemId) {
            this.productItemId = productItemId;
        }
    }
}
