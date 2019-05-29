package com.iota.eshopping.service.datahelper.datasource.offine.location;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.iota.eshopping.constant.entity.SQLStatement;
import com.iota.eshopping.constant.meta.LocationM;
import com.iota.eshopping.model.Location;
import com.iota.eshopping.util.DBUtils;
import com.iota.eshopping.util.LoggerHelper;

import java.util.List;

/**
 * Created by channarith.bong on 2/6/18.
 *
 * @author channarith.bong
 */

public class FetchLocationDAO {

    private SQLiteDatabase database;

    public FetchLocationDAO(SQLiteDatabase database) {
        this.database = database;
    }

    /**
     * Insert Location into table
     *
     * @param location
     * @return
     */
    public boolean insert(Location location) {
        try {
            ContentValues values = getValueFromLocation(location);
            database.insert(LocationM.TABLE_LOCATION, null, values);
            return true;
        } catch (SQLException e) {
            LoggerHelper.showErrorLog(" SQL :" + e.getMessage());
        }
        return false;
    }

    /**
     * Get list of location
     *
     * @return
     */
    public List<Location> getListLocation() {
        try {
            String sql = SQLStatement.SELECT_ALL + LocationM.TABLE_LOCATION;
            List<Location> all = DBUtils.rawQuery(database, new LocationRowMapper(), sql, null);
            return all;
        } catch (SQLException e) {
            LoggerHelper.showErrorLog(" SQL :" + e.getMessage());
        }
        return null;
    }


    /**
     * Convert location into ContentValue
     *
     * @param location
     * @return
     */
    private ContentValues getValueFromLocation(Location location) {
        ContentValues values = new ContentValues();
        if (location == null) {
            return values;
        }

        /*if (location.getId() != null) {
            values.put(LocationM.ID, location.getId());
        }*/

        values.put(LocationM.LATITUDE, location.getLatitude());
        values.put(LocationM.LONGITUDE, location.getLongitude());

        if (location.getAddress().getStreet() != null) {
            values.put(LocationM.STREET, location.getAddress().getStreet().get(0));
        }
        if (location.getAddress().getPostcode() != null) {
            values.put(LocationM.POSTCODE, location.getAddress().getPostcode());
        }
        if (location.getAddress().getCity() != null) {
            values.put(LocationM.CITY, location.getAddress().getCity());
        }
        if (location.getAddress().getCountryId() != null) {
            values.put(LocationM.COUNTRY_ID, location.getAddress().getCountryId());
        }
        return values;
    }
}
