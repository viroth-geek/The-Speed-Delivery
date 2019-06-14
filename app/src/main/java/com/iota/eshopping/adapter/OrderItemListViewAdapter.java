package com.iota.eshopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iota.eshopping.R;
import com.iota.eshopping.activity.MyOrderDetailActivity;
import com.iota.eshopping.constant.ConstantValue;
import com.iota.eshopping.model.ImageResponse;
import com.iota.eshopping.model.modelForView.Product;
import com.iota.eshopping.security.CurrencyConfiguration;
import com.iota.eshopping.service.datahelper.datasource.online.FetchProductImage;
import com.iota.eshopping.util.ImageViewUtil;
import com.iota.eshopping.util.LoggerHelper;
import com.iota.eshopping.util.NumberUtils;

import java.util.List;

/**
 * @author channarith.bong
 */
public class OrderItemListViewAdapter {
    private List<com.iota.eshopping.model.modelForView.ProductItem> productItems;
    private Context context;
    private LinearLayout layoutParent;

    /**
     * @param context Context
     * @param layoutParent LinearLayout
     * @param productItems list of ProductItem
     */
    public OrderItemListViewAdapter(Context context, LinearLayout layoutParent, List<com.iota.eshopping.model.modelForView.ProductItem> productItems) {
        this.context = context;
        this.layoutParent = layoutParent;
        this.productItems = productItems;
    }

    /**
     * @return LinearLayout
     */
    public LinearLayout setChildViewIntoLinearLayout() {
        for (int i = 0; i < productItems.size(); i++) {
            Product product = (Product) productItems.get(i).getItem();
            if (product != null && !product.getProductType().equals(ConstantValue.VIRTUAL_PRODUCT)) {
                View child = LayoutInflater.from(context).inflate(R.layout.view_list_order_product_item, null);
                TextView txt_item_name = child.findViewById(R.id.txt_item_name);
                TextView txt_item_total = child.findViewById(R.id.txt_item_total);
                TextView txt_item_quantity = child.findViewById(R.id.txt_item_quantity);
                TextView txt_item_price = child.findViewById(R.id.txt_item_price);
                ImageView img_product_item = child.findViewById(R.id.img_product_item);

                txt_item_name.setText(product.getSku());
                String qty = product.getCount() + "";
                String price = CurrencyConfiguration.getDollarSign() + NumberUtils.strMoney(product.getPrice());
                String total = CurrencyConfiguration.getDollarSign() + NumberUtils.strMoney(product.getPrice() * product.getCount());
                txt_item_quantity.setText(qty);
                txt_item_price.setText(price);
                txt_item_total.setText(total);
                loadImage(product.getParentId(), img_product_item);
                layoutParent.addView(child);
            }
        }
        return layoutParent;
    }

    /**
     *
     * @param id Long
     * @param imageStore ImageView
     */
    private void loadImage(Long id, final ImageView imageStore) {
        new FetchProductImage(id, new FetchProductImage.InvokeOnCompleteAsync() {
            @Override
            public void onComplete(List<ImageResponse> imageResponseList) {
                if (imageResponseList != null) {
                    if (context instanceof MyOrderDetailActivity && !((MyOrderDetailActivity) context).isFinishing()) {
                        ImageViewUtil.loadImageByUrl(context, imageResponseList.get(0).getImageUrl(), imageStore);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                LoggerHelper.showErrorLog("Product Image " + e);
            }
        });
    }
}
