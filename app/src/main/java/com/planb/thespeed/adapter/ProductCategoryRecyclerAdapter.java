package com.planb.thespeed.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
 *
 * @author channarith.bong
 */
public class ProductCategoryRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int HEADER = 0;
    public static final int CHILD = 1;

    private List<ProductCategory> data;
    private Map<Integer, Item> holderIndex;
    private Context context;

    /**
     * @param context
     * @param data
     */
    public ProductCategoryRecyclerAdapter(Context context, List<ProductCategory> data) {
        this.context = context;
        this.data = data;
        this.holderIndex = Indexer(data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        float dp = context.getResources().getDisplayMetrics().density;
        int subItemPaddingLeft = (int) (18 * dp);
        int subItemPaddingTopAndBottom = (int) (5 * dp);

        switch (viewType) {
            case HEADER:
                view = inflater.inflate(R.layout.view_product_category, parent, false);
                ViewHolderHead header = new ViewHolderHead(view);
                return header;
            case CHILD:
                view = inflater.inflate(R.layout.view_product, parent, false);
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
            vholder.txt_product_category.setText(name);

        } else if (holder instanceof ViewHolderChild) {
            ViewHolderChild vholder = (ViewHolderChild) holder;
            Item item = holderIndex.get(position);
            Product product = (Product) item.getValue();
            vholder.txt_product_name.setText(product.getName());
            vholder.txt_product_detail.setText(product.getDetail());
            vholder.txt_product_price.setText(String.format("$ %s", product.getPrice()));
            vholder.setProductId(product.getId());
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
     * @param data
     * @return
     */
    private Map<Integer, Item> Indexer(List<ProductCategory> data) {
        int i = -1;
        Map<Integer, Item> index = new HashMap<>();
        for (ProductCategory cat : data) {
            i++;
            index.put(i, new Item(HEADER, cat.getName()));
            for (Product product : cat.getProducts()) {
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

        public Item(int type, Object value) {
            this.type = type;
            this.value = value;
        }

        public int getType() {
            return type;
        }

        public Object getValue() {
            return value;
        }
    }

    /**
     * Header row
     */
    public class ViewHolderHead extends RecyclerView.ViewHolder {

        public TextView txt_product_category;

        public ViewHolderHead(View itemView) {
            super(itemView);
            txt_product_category = itemView.findViewById(R.id.txt_product_category);
        }
    }

    /**
     * Child row
     */
    public class ViewHolderChild extends RecyclerView.ViewHolder {
        private Long productId;
        public TextView txt_product_name;
        public TextView txt_product_detail;
        public TextView txt_product_price;

        public ViewHolderChild(View itemView) {
            super(itemView);
            txt_product_name = itemView.findViewById(R.id.txt_product_name);
            txt_product_detail = itemView.findViewById(R.id.txt_product_detail);
            txt_product_price = itemView.findViewById(R.id.txt_product_price);
            itemView.setOnClickListener(v -> Toast.makeText(context, " Item click " + getProductId(), Toast.LENGTH_SHORT).show());
        }

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }
    }
}
