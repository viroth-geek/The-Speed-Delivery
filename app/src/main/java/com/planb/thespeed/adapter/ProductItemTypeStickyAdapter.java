package com.planb.thespeed.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.planb.thespeed.model.modelForView.Product;
import com.planb.thespeed.model.modelForView.ProductCategory;
import com.planb.thespeed.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by channarith.bong on 12/6/17.
 *
 * @author channarith.bong
 */
public class ProductItemTypeStickyAdapter extends SectioningAdapter {

    private Context context;
    private List<Section> sections;


    @Override
    public int getNumberOfSections() {
        return sections.size();
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        return sections.get(sectionIndex).productList.size();
    }

    @Override
    public boolean doesSectionHaveHeader(int sectionIndex) {
        return true;
    }

    @Override
    public boolean doesSectionHaveFooter(int sectionIndex) {
        return false;
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.view_product_item, parent, false);
        return new ViewHolderChild(v);
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.view_product_category, parent, false);
        return new ViewHolderHead(v);
    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder viewHolder, int sectionIndex, int itemIndex, int itemType) {
        Section s = sections.get(sectionIndex);
        ViewHolderChild viewHolderChild = (ViewHolderChild) viewHolder;
        Product product = s.productList.get(itemIndex);
        viewHolderChild.setProductItemId(product.getId());
        viewHolderChild.txt_product_item_name.setText(product.getName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            viewHolderChild.txt_product_item_desc.setText(Html.fromHtml(product.getDetail(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            viewHolderChild.txt_product_item_desc.setText(Html.fromHtml(product.getDetail()));
        }
        viewHolderChild.txt_product_item_price.setText(" $ " + product.getPrice());
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder viewHolder, int sectionIndex, int headerType) {
        Section s = sections.get(sectionIndex);
        ViewHolderHead viewHolderHead = (ViewHolderHead) viewHolder;
        viewHolderHead.txt_product_item_group.setText(s.alpha);
    }

    /**
     * @param data
     * @return
     */
    private List<Section> setSection(List<ProductCategory> data) {
        List<Section> sections = new ArrayList<>();
        for (ProductCategory cat : data) {
            sections.add(new Section(cat.getName(), cat.getProducts()));
        }
        return sections;
    }

    /**
     *
     */
    private class Section {
        String alpha;
        List<Product> productList = new ArrayList<>();

        public Section(String alpha, List<Product> productList) {
            this.alpha = alpha;
            this.productList = productList;
        }
    }

    /**
     * Header row
     */
    public class ViewHolderHead extends HeaderViewHolder {

        public TextView txt_product_item_group;

        public ViewHolderHead(View itemView) {
            super(itemView);
            txt_product_item_group = itemView.findViewById(R.id.txt_product_category);
        }
    }

    /**
     * Child row
     */
    public class ViewHolderChild extends ItemViewHolder {
        private Long productItemId;
        public TextView txt_product_item_name;
        public TextView txt_product_item_desc;
        public TextView txt_product_item_price;

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
         * @return
         */
        public Long getProductItemId() {
            return productItemId;
        }

        /**
         * @param productItemId
         */
        public void setProductItemId(Long productItemId) {
            this.productItemId = productItemId;
        }
    }
}
