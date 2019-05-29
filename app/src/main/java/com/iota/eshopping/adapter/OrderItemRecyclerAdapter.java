package com.iota.eshopping.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iota.eshopping.R;
import com.iota.eshopping.model.ImageResponse;
import com.iota.eshopping.model.ProductItem;
import com.iota.eshopping.service.datahelper.datasource.online.FetchProductImage;
import com.iota.eshopping.util.ImageViewUtil;
import com.iota.eshopping.util.LoggerHelper;
import com.iota.eshopping.util.NumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by channarith.bong on 1/24/18.
 *
 * @author channarith.bong
 */
public class OrderItemRecyclerAdapter extends RecyclerView.Adapter<OrderItemRecyclerAdapter.ViewHolder> {

    private List<ProductItem> items;
    private Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_list_order_product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProductItem productItem = items.get(position);
        Long productId = productItem.getProductId();
        holder.txt_item_name.setText(productItem.getName());
        holder.txt_item_price.setText(NumberUtils.strMoney(productItem.getPrice().floatValue()));
        holder.txt_item_quantity.setText(String.format(Locale.ENGLISH, "%d", productItem.getQtyOrdered()));
        holder.txt_item_total.setText(NumberUtils.strMoney(productItem.getRowTotal().floatValue()));
        if (productId != null) {
            loadImage(productId, holder.img_product_item);
        }

    }

    @Override
    public int getItemCount() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items.size();
    }

    /**
     *
     * @param id Long
     * @param imageProduct ImageView
     */
    private void loadImage(Long id, final ImageView imageProduct) {
        new FetchProductImage(id, new FetchProductImage.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(List<ImageResponse> imageResponseList) {
                if (imageResponseList != null) {
                    String imageUrl = imageResponseList.get(0).getImageUrl();
                    if (imageUrl != null) {
                        if (!imageUrl.isEmpty()) {
                            ImageViewUtil.loadImageByUrl(context, imageUrl, imageProduct);
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                LoggerHelper.showErrorLog("Product Image " + e);
            }
        });
    }

    /**
     *
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_item_name;
        private TextView txt_item_quantity;
        private TextView txt_item_price;
        private TextView txt_item_total;
        private ImageView img_product_item;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_item_name = itemView.findViewById(R.id.txt_item_name);
            txt_item_quantity = itemView.findViewById(R.id.txt_item_quantity);
            txt_item_price = itemView.findViewById(R.id.txt_item_price);
            txt_item_total = itemView.findViewById(R.id.txt_item_total);
            img_product_item = itemView.findViewById(R.id.img_product_item);
        }
    }
}
