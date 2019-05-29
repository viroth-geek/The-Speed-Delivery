
package com.iota.eshopping.model.magento.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.iota.eshopping.model.Entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class FilterGroup extends Entity {

    @SerializedName("filters")
    @Expose
    private List<Filter> filters = null;
    private final static long serialVersionUID = 3334556701133257710L;

    /**
     * @return
     */
    public List<Filter> getFilters() {
        return filters;
    }

    /**
     * @param filters
     */
    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("filters", filters).toString();
    }

}
