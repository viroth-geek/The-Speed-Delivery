package com.planb.thespeed.service.datahelper.datasource.offine.address;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.planb.thespeed.constant.entity.SQLStatement;
import com.planb.thespeed.constant.meta.AddressM;
import com.planb.thespeed.model.Address;
import com.planb.thespeed.util.DBUtils;
import com.planb.thespeed.util.LoggerHelper;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by channarith.bong on 2/7/18.
 *
 * @author channarith.bong
 */

public class FetchAddressDAO {

    private SQLiteDatabase database;

    /**
     * @param database
     */
    public FetchAddressDAO(SQLiteDatabase database) {
        this.database = database;
    }

    /**
     * Insert Address
     *
     * @param address
     * @return
     */
    public boolean insert(Address address) {
        try {
//            if (!isAddressExist(address.getLongitude(), address.getLatitude())) {
                ContentValues values = getValueFromAddress(address);
                return database.insert(AddressM.TABLE_ADDRESS, null, values) > 0;
//            }
        } catch (SQLException e) {
            LoggerHelper.showErrorLog(" SQL :" + e.getMessage());
        }
        return false;
    }

    /**
     * @param addressId
     * @return
     */
    public boolean delete(Long addressId) {
        try {
            return database.delete(AddressM.TABLE_ADDRESS, AddressM.ID + " = ?", new String[] { addressId.toString() }) > 0;
        } catch (SQLException e) {
            LoggerHelper.showErrorLog(" SQL :" + e.getMessage());
        }
        return false;
    }

    /**
     * Delete all from table address
     *
     * @return
     */
    public boolean deleteAll() {
        try {
            return database.delete(AddressM.TABLE_ADDRESS, null, null) > 0;
        } catch (SQLException e) {
            LoggerHelper.showErrorLog("SQL : " + e.getMessage());
        }
        return false;
    }

    /**
     * Get list of address
     *
     * @return
     */
    public List<Address> getListAddress() {
        try {
            String sql = SQLStatement.SELECT_ALL + AddressM.TABLE_ADDRESS;
            return DBUtils.rawQuery(database, new AddressRowMapper(), sql, null);
        } catch (SQLException e) {
            LoggerHelper.showErrorLog(" SQL :" + e.getMessage());
        }
        return null;
    }

    /**
     * check if longitude and latitude exist
     * @param lng
     * @param lat
     * @return
     */
    private boolean isAddressExist(double lng, double lat) {
        int count = 0;
        try {
            String sql = SQLStatement.SELECT_ALL + AddressM.TABLE_ADDRESS + SQLStatement.WHERE + AddressM.LONGITUDE + "=" + lng + " AND " + AddressM.LATITUDE + "=" + lat;;
            Cursor cursor = database.rawQuery(sql, null);
            if (cursor.getCount() > 0 && cursor.getColumnCount() > 0) {
                count = cursor.getCount();
                cursor.close();
            }
            return count > 0;
        }
        catch (SQLException e) {
            LoggerHelper.showErrorLog("SQL : " + e.getMessage());
        }
        return false;
    }

    /**
     *
     * @param address
     * @return
     */
    public boolean updateAddress(Address address) {
        try {
            return database.update(AddressM.TABLE_ADDRESS, getValueFromAddress(address), AddressM.ID + "=" + address.getId(), null) > 0;
        }
        catch (SQLException e) {
            LoggerHelper.showErrorLog("SQL: " + e.getMessage());
        }
        return false;
    }

    /**
     * Convert Address into ContentValue
     *
     * @param address
     * @return
     */
    private ContentValues getValueFromAddress(Address address) {

        LoggerHelper.showDebugLog("===> AddressID: " + address.getId());

        ContentValues values = new ContentValues();
        if (address == null) {
            return values;
        }

        if (address.getId() != null) {
            values.put(AddressM.ID, address.getId());
        }
        if (address.getCustomerId() != null) {
            values.put(AddressM.CUSTOMER_ID, address.getCustomerId());
        }
        if (address.getCountryId() != null) {
            values.put(AddressM.COUNTRY_ID, address.getCountryId());
        }
        if (address.getTelephone() != null) {
            values.put(AddressM.TELEPHONE, address.getTelephone());
        }
        if (address.getPostcode() != null) {
            values.put(AddressM.POSTCODE, address.getPostcode());
        }
        if (address.getCity() != null) {
            values.put(AddressM.CITY, address.getCity());
        }
        if (address.getFirstname() != null) {
            values.put(AddressM.FIRST_NAME, address.getFirstname());
        }
        if (address.getLastname() != null) {
            values.put(AddressM.LAST_NAME, address.getLastname());
        }
        if (address.getCountryCode() != null) {
            values.put(AddressM.COUNTRY_CODE, address.getCountryCode());
        }
        if (address.getCountryName() != null) {
            values.put(AddressM.COUNTRY_NAME, address.getCountryName());
        }
//        if (address.getAddressLine() != null) {
//            values.put(AddressM.ADDRESS_LINE, address.getAddressLine());
//        }
        if (address.getStreet() != null && !address.getStreet().isEmpty()) {
//            StringBuilder street = new StringBuilder();
//            int index = 0;
//            for (String s : address.getStreet()) {
//                street.append(s);
//                if (index < address.getStreet().size() - 1) {
//                    street.append(", ");
//                }
//                index++;
//            }
            values.put(AddressM.ADDRESS_LINE, StringUtils.join(address.getStreet()));
        }

        if (address.getLatitude() != null) {
            values.put(AddressM.LATITUDE, address.getLatitude());
        }

        if (address.getLongitude() != null) {
            values.put(AddressM.LONGITUDE, address.getLongitude());
        }

        if (address.getDefaultBilling() != null) {
            values.put(AddressM.DEFAULT_BILLING, address.getDefaultBilling());
        }

        if (address.getDefaultShipping() != null) {
            values.put(AddressM.DEFAULT_SHIPPING, address.getDefaultShipping());
        }

        return values;
    }
}