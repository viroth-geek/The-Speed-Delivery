package com.planb.thespeed.service.datahelper.datasource.offine.address;

import android.database.Cursor;

import com.planb.thespeed.constant.meta.AddressM;
import com.planb.thespeed.model.Address;
import com.planb.thespeed.service.datahelper.datasource.offine.RowMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by channarith.bong on 2/7/18.
 *
 * @author channarith.bong
 */

public class AddressRowMapper implements RowMapper<Address> {
    @Override
    public Address mappedRow(Cursor cursor) {
        Address bean = new Address();
        if (!cursor.isNull(cursor.getColumnIndex(AddressM.ID))) {
            bean.setId(cursor.getLong(cursor.getColumnIndex(AddressM.ID)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(AddressM.CUSTOMER_ID))) {
            bean.setCustomerId(cursor.getLong(cursor.getColumnIndex(AddressM.CUSTOMER_ID)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(AddressM.COUNTRY_ID))) {
            bean.setCountryId(cursor.getString(cursor.getColumnIndex(AddressM.COUNTRY_ID)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(AddressM.TELEPHONE))) {
            bean.setTelephone(cursor.getString(cursor.getColumnIndex(AddressM.TELEPHONE)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(AddressM.POSTCODE))) {
            bean.setPostcode(cursor.getString(cursor.getColumnIndex(AddressM.POSTCODE)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(AddressM.CITY))) {
            bean.setCity(cursor.getString(cursor.getColumnIndex(AddressM.CITY)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(AddressM.FIRST_NAME))) {
            bean.setFirstname(cursor.getString(cursor.getColumnIndex(AddressM.FIRST_NAME)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(AddressM.LAST_NAME))) {
            bean.setLastname(cursor.getString(cursor.getColumnIndex(AddressM.LAST_NAME)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(AddressM.LATITUDE))) {
            bean.setLatitude(cursor.getDouble(cursor.getColumnIndex(AddressM.LATITUDE)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(AddressM.LONGITUDE))) {
            bean.setLongitude(cursor.getDouble(cursor.getColumnIndex(AddressM.LONGITUDE)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(AddressM.COUNTRY_CODE))) {
            bean.setCountryCode(cursor.getString(cursor.getColumnIndex(AddressM.COUNTRY_CODE)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(AddressM.COUNTRY_NAME))) {
            bean.setCountryName(cursor.getString(cursor.getColumnIndex(AddressM.COUNTRY_NAME)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(AddressM.ADDRESS_LINE))) {
            bean.setAddressLine(cursor.getString(cursor.getColumnIndex(AddressM.ADDRESS_LINE)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(AddressM.STREET))) {
            List<String> street = new ArrayList<>();
            street.add(cursor.getString(cursor.getColumnIndex(AddressM.STREET)));
            bean.setStreet(street);
        }
        if (!cursor.isNull(cursor.getColumnIndex(AddressM.DEFAULT_BILLING))) {
            bean.setDefaultBilling(cursor.getInt(cursor.getColumnIndex(AddressM.DEFAULT_BILLING)) > 0);
        }
        if (!cursor.isNull(cursor.getColumnIndex(AddressM.DEFAULT_SHIPPING))) {
            bean.setDefaultShipping(cursor.getInt(cursor.getColumnIndex(AddressM.DEFAULT_SHIPPING)) > 0);
        }
        return bean;
    }
}
