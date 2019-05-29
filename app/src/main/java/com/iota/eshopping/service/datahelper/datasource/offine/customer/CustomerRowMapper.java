package com.iota.eshopping.service.datahelper.datasource.offine.customer;

import android.database.Cursor;

import com.iota.eshopping.constant.meta.CustomerM;
import com.iota.eshopping.model.Customer;
import com.iota.eshopping.service.datahelper.datasource.offine.RowMapper;

public class CustomerRowMapper implements RowMapper<Customer> {

    @Override
    public Customer mappedRow(Cursor cursor) {
        Customer bean = new Customer();

        if (!cursor.isNull(cursor.getColumnIndex(CustomerM.ID))) {
            bean.setId(Long.valueOf(cursor.getInt(cursor.getColumnIndex(CustomerM.ID))));
        }
        if (!cursor.isNull(cursor.getColumnIndex(CustomerM.ENTITY_ID))) {
            bean.setEntityId(Long.valueOf(cursor.getInt(cursor.getColumnIndex(CustomerM.ENTITY_ID))));
        }
        if (!cursor.isNull(cursor.getColumnIndex(CustomerM.GROUP_ID))) {
            bean.setGroupId(cursor.getLong(cursor.getColumnIndex(CustomerM.GROUP_ID)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(CustomerM.CREATED_AT))) {
            bean.setCreatedAt(cursor.getString(cursor.getColumnIndex(CustomerM.CREATED_AT)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(CustomerM.UPDATED_AT))) {
            bean.setUpdateAt(cursor.getString(cursor.getColumnIndex(CustomerM.UPDATED_AT)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(CustomerM.EMAIL))) {
            bean.setEmail(cursor.getString(cursor.getColumnIndex(CustomerM.EMAIL)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(CustomerM.FIRSTNAME))) {
            bean.setFirstname(cursor.getString(cursor.getColumnIndex(CustomerM.FIRSTNAME)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(CustomerM.LASTNAME))) {
            bean.setLastname(cursor.getString(cursor.getColumnIndex(CustomerM.LASTNAME)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(CustomerM.STORE_ID))) {
            bean.setStoreId((cursor.getLong(cursor.getColumnIndex(CustomerM.STORE_ID))));
        }
        if (!cursor.isNull(cursor.getColumnIndex(CustomerM.WEBSITE_ID))) {
            bean.setWebsiteId(cursor.getLong(cursor.getColumnIndex(CustomerM.WEBSITE_ID)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(CustomerM.RP_TOKEN))) {
            bean.setRpToken(cursor.getString(cursor.getColumnIndex(CustomerM.RP_TOKEN)));
        }
/*		if(!cursor.isNull(cursor.getColumnIndex(CustomerM.ADDRESS_ID))){
            bean.setAddressId(cursor.getLong(cursor.getColumnIndex(CustomerM.ADDRESS_ID)));
		}*/
        if (!cursor.isNull(cursor.getColumnIndex(CustomerM.DOB))) {
            bean.setDob(cursor.getString(cursor.getColumnIndex(CustomerM.DOB)));
        }

        return bean;
    }
}
