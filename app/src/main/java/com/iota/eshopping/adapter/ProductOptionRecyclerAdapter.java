package com.iota.eshopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.iota.eshopping.R;
import com.iota.eshopping.model.ConfigurationProductOption;
import com.iota.eshopping.model.Entity;
import com.iota.eshopping.model.Option;
import com.iota.eshopping.model.OptionProduct;
import com.iota.eshopping.model.OptionValue;
import com.iota.eshopping.model.ProductOption;
import com.iota.eshopping.model.enumeration.OptionSelectType;
import com.iota.eshopping.model.modelForView.ProductAttributeOption;
import com.iota.eshopping.util.LoggerHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static android.support.annotation.Dimension.SP;

/**
 * @author yeakleang.ay
 */
public class ProductOptionRecyclerAdapter extends SectioningAdapter {

    private Context context;

    private List<Section> sections;

    private OnAttributeOptionClickListener onAttributeOptionClickListener;

    /**
     *
     * @param context Context
     * @param configurationProductOption list of ProductOption
     */
    public ProductOptionRecyclerAdapter(Context context, ConfigurationProductOption configurationProductOption) {
        this.context = context;
        this.sections = setSection(configurationProductOption);
    }

    /**
     *
     */
    private class Section<T extends Entity> {

        private Long productId;

        private String attributeId;

        private String label;

        private boolean isRequired;

        private List<T> items;

        private String selectType;

        private OptionProduct optionProduct;

        /**
         *
         * @param attributeId String
         * @param label String
         * @param items List of Option
         * @param selectType String
         */
        Section(Long productId, String attributeId, String label, boolean isRequired, List<T> items, String selectType, OptionProduct optionProduct) {
            this.productId = productId;
            this.attributeId = attributeId;
            this.label = label;
            this.isRequired = isRequired;
            this.items = items;
            this.selectType = selectType;
            this.optionProduct = optionProduct;
        }
    }

    /**
     *
     */
    private class AttributeHeaderViewHolder extends HeaderViewHolder {

        private TextView optionHeader;

        /**
         *
         * @param itemView View
         */
        AttributeHeaderViewHolder(View itemView) {
            super(itemView);

            optionHeader = itemView.findViewById(R.id.txt_header_section);
            optionHeader.setTextSize(SP, 15);
        }
    }

    /**
     *
     */
    private class AttributeItemViewHolder extends ItemViewHolder {

        private TextView txt_option_title;
        private CheckBox checkBox;
        private RadioButton radioButton;

        /**
         *
         * @param itemView View
         */
        AttributeItemViewHolder(View itemView) {
            super(itemView);

            txt_option_title = itemView.findViewById(R.id.txt_option_name);
            checkBox = itemView.findViewById(R.id.option_check_box);
            checkBox.setClickable(false);
            radioButton = itemView.findViewById(R.id.option_radio_button);
            radioButton.setClickable(false);

            itemView.setOnClickListener(view -> {

                if (sections.get(getSection()).items != null && !sections.get(getSection()).items.isEmpty()) {
                    if (sections.get(getSection()).items.get(0) instanceof Option) {
                        Option option = (Option) sections.get(getSection()).items.get(getPositionInSection());
                        for (Option option1 : (List<Option>) sections.get(getSection()).items) {
                            if (option1.equals(option)) {
                                option1.setCheck(true);
                            } else {
                                option1.setCheck(false);
                            }
                        }
                    }
                    else if (sections.get(getSection()).items.get(0) instanceof OptionValue) {
                        Section section = sections.get(getSection());
                        OptionValue optionValue = (OptionValue) section.items.get(getPositionInSection());
                        if (section.selectType.equalsIgnoreCase(OptionSelectType.RADIO_BUTTON.getName())) {
                            for (OptionValue optionValue1 : (List<OptionValue>) section.items) {
                                if (optionValue1.equals(optionValue)) {
                                    optionValue1.setCheck(true);
                                } else {
                                    optionValue1.setCheck(false);
                                }
                            }
                        }
                        else if (section.selectType.equalsIgnoreCase(OptionSelectType.CHECK_BOX.getName())) {
                            if (optionValue.isCheck()) {
                                optionValue.setCheck(false);
                            }
                            else {
                                optionValue.setCheck(true);
                            }
                        }
                    }
                }

                onAttributeOptionClickListener.onAttributeOptionClick(currentSelectedProductAttributeOption());
                notifyAllSectionsDataSetChanged();
            });
        }

    }

    /**
     * Get current selected ProductAttribute
     * @return list of ProductOption
     */
    public ProductAttributeOption currentSelectedProductAttributeOption() {
        ProductAttributeOption selectedAttributeOption = new ProductAttributeOption();
        for (int i = 0; i < sections.size(); i++) {
            if (sections.get(i).items.get(0) instanceof Option) {
                List<Option> options = new ArrayList<>();
                for (int j = 0; j < sections.get(i).items.size(); j++) {
                    if (((Option) sections.get(i).items.get(j)).getCheck()) {
                        options.add((Option) sections.get(i).items.get(j));
                    }
                }
                selectedAttributeOption.getProductOptions().add(new ProductOption(sections.get(i).productId, sections.get(i).attributeId, sections.get(i).label, options));
            }
            else if (sections.get(i).items.get(0) instanceof OptionValue) {
                List<OptionValue> optionValues = new ArrayList<>();
                for (int j = 0; j < sections.get(i).items.size(); j++) {
                    if (((OptionValue) sections.get(i).items.get(j)).isCheck()) {
                        optionValues.add((OptionValue) sections.get(i).items.get(j));
                    }
                }
                selectedAttributeOption.getOptionProducts().add(new OptionProduct(sections.get(i).productId, Long.valueOf(sections.get(i).attributeId), sections.get(i).label, optionValues));
            }
        }
        return selectedAttributeOption;
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
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemUserType) {
        View itemView = LayoutInflater.from(context)
                            .inflate(R.layout.product_option_row_list, parent, false);
        return new AttributeItemViewHolder(itemView);
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerUserType) {
        View itemView = LayoutInflater.from(context)
                            .inflate(R.layout.view_header_section, parent, false);
        return new AttributeHeaderViewHolder(itemView);
    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder viewHolder, int sectionIndex, int itemIndex, int itemUserType) {
        Section section = sections.get(sectionIndex);
        AttributeItemViewHolder attributeItemViewHolder = (AttributeItemViewHolder) viewHolder;
        if (section.items != null && !section.items.isEmpty()) {
            if (section.items.get(0) instanceof Option){

                attributeItemViewHolder.checkBox.setVisibility(View.GONE);
                attributeItemViewHolder.radioButton.setVisibility(View.VISIBLE);

                Option option = (Option) section.items.get(itemIndex);
                attributeItemViewHolder.txt_option_title.setText(option.getLabel());
                if (option.getCheck()) {
                    attributeItemViewHolder.radioButton.setChecked(true);
                }
                else {
                    attributeItemViewHolder.radioButton.setChecked(false);
                }
            }
            else if (section.items.get(0) instanceof OptionValue) {
                OptionValue optionValue = (OptionValue) section.items.get(itemIndex);

                if (section.optionProduct != null) {
                    if (shouldShowPrice(section.optionProduct.getTitle())) {
                        attributeItemViewHolder.txt_option_title.setText(String.format("%s + $%s", optionValue.getTitle(), optionValue.getPrice()));
                    } else {
                        attributeItemViewHolder.txt_option_title.setText(String.format("%s", optionValue.getTitle()));
                    }
                }

                if (section.selectType.equalsIgnoreCase(OptionSelectType.RADIO_BUTTON.getName())) {
                    attributeItemViewHolder.checkBox.setVisibility(View.GONE);
                    attributeItemViewHolder.radioButton.setVisibility(View.VISIBLE);
                }
                else {
                    attributeItemViewHolder.checkBox.setVisibility(View.VISIBLE);
                    attributeItemViewHolder.radioButton.setVisibility(View.GONE);
                }

                if (optionValue.isCheck()) {
                    if (attributeItemViewHolder.radioButton.getVisibility() == View.GONE) {
                        attributeItemViewHolder.checkBox.setChecked(true);
                    }
                    else if (attributeItemViewHolder.checkBox.getVisibility() == View.GONE) {
                        attributeItemViewHolder.radioButton.setChecked(true);
                    }
                }
                else {
                    if (attributeItemViewHolder.radioButton.getVisibility() == View.GONE) {
                        attributeItemViewHolder.checkBox.setChecked(false);
                    }
                    else if (attributeItemViewHolder.checkBox.getVisibility() == View.GONE) {
                        attributeItemViewHolder.radioButton.setChecked(false);
                    }
                }
            }
        }
    }

    /**
     * check if should price
     * @param title boolean
     * @return boolean
     */
    private boolean shouldShowPrice(String title) {
        return !title.contains("[N]");
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder viewHolder, int sectionIndex, int headerUserType) {
        Section section = sections.get(sectionIndex);
        AttributeHeaderViewHolder attributeHeaderViewHolder = (AttributeHeaderViewHolder) viewHolder;

        if (section.label.contains("[N]")) {
            section.label = section.label.replace("[N]", "");
        }

        String label = section.label;
        label += section.isRequired ? " *" : "";
        attributeHeaderViewHolder.optionHeader.setText(label);
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        return sections.get(sectionIndex).items.size();
    }

    @Override
    public int getNumberOfSections() {
        return sections.size();
    }

    /**
     *
     * @param configurationProductOption list of ConfigurationProductOption
     * @return list of Section
     */
    private List<Section> setSection(ConfigurationProductOption configurationProductOption) {
        List<Section> sectionList = new ArrayList<>();
        List<ProductOption> productOptions = Observable.fromIterable(configurationProductOption.getExtensionAttribute().getProductOptions()).sorted((productOption, t1) -> productOption.getPosition() - t1.getPosition()).toList().blockingGet();
        List<OptionProduct> optionProducts = Observable.fromIterable(configurationProductOption.getOptionProducts()).sorted((optionProduct, t1) -> optionProduct.getSortOrder() - t1.getSortOrder()).toList().blockingGet();

        for (ProductOption productOption : configurationProductOption.getExtensionAttribute().getProductOptions()) {
            LoggerHelper.showDebugLog("===> " + productOption.getPosition());
        }

        if (productOptions != null) {
            for (ProductOption productOption : productOptions) {
                List<Option> options = new ArrayList<>();
                options.addAll(productOption.getOptions());
                sectionList.add(new Section<>(productOption.getProductId() , productOption.getAttributeId(), productOption.getLabel(), true, options, OptionSelectType.RADIO_BUTTON.getName(), null));
            }
        }
        if (optionProducts != null) {
            for (OptionProduct optionProduct : optionProducts) {
                List<OptionValue> optionValues = new ArrayList<>();
                optionValues.addAll(optionProduct.getOptionValues());
                OptionSelectType optionSelectType;
                if (optionProduct.getType().equalsIgnoreCase(OptionSelectType.RADIO_BUTTON.getName())) {
                    optionSelectType = OptionSelectType.RADIO_BUTTON;
                }
                else {
                    optionSelectType = OptionSelectType.CHECK_BOX;
                }
                sectionList.add(new Section<>(optionProduct.getProductId(), optionProduct.getOptionId().toString(), optionProduct.getTitle(), optionProduct.getRequired(), optionValues, optionSelectType.getName(), optionProduct));
            }
        }
        return sectionList;
    }

    /**
     *
     * @param onAttributeOptionClickListener OnAttributeOptionClickListener
     */
    public void setOnAttributeOptionClickListener(OnAttributeOptionClickListener onAttributeOptionClickListener) {
        this.onAttributeOptionClickListener = onAttributeOptionClickListener;
    }

    /**
     *
     */
    public interface OnAttributeOptionClickListener {
        void onAttributeOptionClick(ProductAttributeOption productAttributeOption);
    }
}
