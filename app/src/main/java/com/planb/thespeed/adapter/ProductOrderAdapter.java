package com.planb.thespeed.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.planb.thespeed.R;
import com.planb.thespeed.model.modelForView.OrderItem;
import com.planb.thespeed.security.CurrencyConfiguration;
import com.planb.thespeed.util.FontManager;

import java.util.List;

/**
 * @author channarith.bong
 */
public class ProductOrderAdapter extends RecyclerView.Adapter<ProductOrderAdapter.ItemViewHolder> {

    private Context context;
    private List<OrderItem> orderItems;
    private Typeface font;

    /**
     * @param context    Context
     * @param orderItems list of OrderItem
     */
    public ProductOrderAdapter(Context context, List<OrderItem> orderItems) {
        this.context = context;
        this.orderItems = orderItems;
        this.font = FontManager.getTypeface(context, FontManager.FONTAWESOME);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_order, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        OrderItem item = orderItems.get(position);
        holder.txt_name_item_order.setText(item.getName());
        holder.txt_item_price.setText(String.format("%s%s", CurrencyConfiguration.getDollarSign(), item.getPrice()));
        holder.txt_date_order.setText(item.getDateOrder());
        holder.txt_reorder.setTypeface(font);
        holder.txt_reorder.setText(String.format("%s Reorder", context.getString(R.string.fa_refresh)));
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    /**
     *
     */
    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView txt_name_item_order;
        TextView txt_date_order;
        TextView txt_reorder;
        TextView txt_item_price;

        /**
         * @param itemView View
         */
        ItemViewHolder(View itemView) {
            super(itemView);
            txt_name_item_order = itemView.findViewById(R.id.txt_order_product_name);
            txt_date_order = itemView.findViewById(R.id.txt_order_product_price);
            txt_reorder = itemView.findViewById(R.id.txt_order_btn_reorder);
            txt_item_price = itemView.findViewById(R.id.txt_order_price_of_product);
        }
    }
}
