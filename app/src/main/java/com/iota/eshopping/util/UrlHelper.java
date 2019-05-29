package com.iota.eshopping.util;

import com.iota.eshopping.model.magento.search.SearchParam;
import com.iota.eshopping.model.magento.search.SearchParamGroup;

import java.util.HashMap;
import java.util.List;

/**
 * Created by channarith.bong on 1/30/18.
 *
 * @author channarith.bong
 */

public class UrlHelper {

    /**
     * @param groupList
     * @return
     */
    public static HashMap<String, String> searchCriteria(List<SearchParamGroup> groupList) {
        HashMap<String, String> query = new HashMap<>();
        for (SearchParamGroup group : groupList) {
            for (SearchParam param : group.getSearchParam()) {
                int gIndex = groupList.indexOf(group);
                int fIndex = group.getSearchParam().indexOf(param);
                String field = "searchCriteria[filterGroups][" + gIndex + "][filters][" + fIndex + "][field]";
                String value = "searchCriteria[filterGroups][" + gIndex + "][filters][" + fIndex + "][value]";
                String condition_type = "searchCriteria[filterGroups][" + gIndex + "][filters][" + fIndex + "][condition_type]";
                query.put(field, param.getFieldName());
                query.put(value, param.getValue());
                query.put(condition_type, param.getConditionType());
            }
        }
        return query;
    }
}
