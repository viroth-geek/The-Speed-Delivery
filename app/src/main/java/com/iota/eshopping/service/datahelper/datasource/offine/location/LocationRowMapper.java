package com.iota.eshopping.service.datahelper.datasource.offine.location;

import android.database.Cursor;

import com.iota.eshopping.constant.meta.LocationM;
import com.iota.eshopping.model.Address;
import com.iota.eshopping.model.Location;
import com.iota.eshopping.service.datahelper.datasource.offine.RowMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by channarith.bong on 2/6/18.
 *
 * @author channarith.bong
 */

public class LocationRowMapper implements RowMapper<Location> {
    @Override
    public Location mappedRow(Cursor cursor) {
        Location bean = new Location();
        Address address = new Address();

        if (!cursor.isNull(cursor.getColumnIndex(LocationM.ID))) {
            bean.setId(cursor.getLong(cursor.getColumnIndex(LocationM.ID)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(LocationM.LATITUDE))) {
            bean.setLatitude(cursor.getLong(cursor.getColumnIndex(LocationM.LATITUDE)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(LocationM.LONGITUDE))) {
            bean.setLongitude(cursor.getLong(cursor.getColumnIndex(LocationM.LONGITUDE)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(LocationM.CITY))) {
            address.setCity(cursor.getString(cursor.getColumnIndex(LocationM.CITY)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(LocationM.COUNTRY_ID))) {
            address.setCountryId(cursor.getString(cursor.getColumnIndex(LocationM.COUNTRY_ID)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(LocationM.POSTCODE))) {
            address.setPostcode(cursor.getString(cursor.getColumnIndex(LocationM.POSTCODE)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(LocationM.STREET))) {
            List<String> listAddress = new ArrayList<>();
            listAddress.add(cursor.getString(cursor.getColumnIndex(LocationM.STREET)));
            address.setStreet(listAddress);
        }
        bean.setAddress(address);
        return bean;
    }
}
