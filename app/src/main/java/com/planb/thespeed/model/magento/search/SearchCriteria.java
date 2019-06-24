
package com.planb.thespeed.model.magento.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.planb.thespeed.model.Entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class SearchCriteria extends Entity {

    @SerializedName("filter_groups")
    @Expose
    private List<FilterGroup> filterGroups = null;
    private final static long serialVersionUID = -1241972647678676853L;

    /**
     * @return
     */
    public List<FilterGroup> getFilterGroups() {
        return filterGroups;
    }

    /**
     * @param filterGroups
     */
    public void setFilterGroups(List<FilterGroup> filterGroups) {
        this.filterGroups = filterGroups;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("filterGroups", filterGroups).toString();
    }

}
