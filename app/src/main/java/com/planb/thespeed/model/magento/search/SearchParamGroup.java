package com.planb.thespeed.model.magento.search;

import java.util.List;

/**
 * Created by channarith.bong on 1/30/18.
 *
 * @author channarith.bong
 */
public class SearchParamGroup {

    private List<SearchParam> searchParam;

    /**
     * @param searchParam
     */
    public SearchParamGroup(List<SearchParam> searchParam) {
        this.searchParam = searchParam;
    }

    /**
     * @return
     */
    public List<SearchParam> getSearchParam() {
        return searchParam;
    }

    /**
     * @param searchParam
     */
    public void setSearchParam(List<SearchParam> searchParam) {
        this.searchParam = searchParam;
    }

    @Override
    public String toString() {
        return "SearchParamGroup{" +
                "searchParam=" + searchParam +
                '}';
    }
}
