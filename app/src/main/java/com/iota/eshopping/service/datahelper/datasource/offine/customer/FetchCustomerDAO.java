package com.iota.eshopping.service.datahelper.datasource.offine.customer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.iota.eshopping.constant.entity.SQLStatement;
import com.iota.eshopping.constant.meta.CustomerM;
import com.iota.eshopping.model.Customer;
import com.iota.eshopping.util.DBUtils;
import com.iota.eshopping.util.LoggerHelper;

import java.util.List;

/**
 * Created by channarith.bong on 1/16/18.
 *
 * @author channarith.bong
 */

public class FetchCustomerDAO {
    private SQLiteDatabase database;

    public FetchCustomerDAO(SQLiteDatabase database) {
        this.database = database;
    }

    /**
     * Get customer by id
     *
     * @param id
     * @return Customer
     * @see Customer
     */
    public Customer getCustomerById(Long id) {
        try {
            String sql = SQLStatement.SELECT_ALL +
                    CustomerM.TABLE_CUSTOMER +
                    SQLStatement.WHERE +
                    CustomerM.ENTITY_ID +
                    SQLStatement.QUESTION_SIGN;
            List<Customer> all = DBUtils.rawQuery(database, new CustomerRowMapper(), sql, new String[]{"" + id});
            return all.get(0);
        } catch (SQLException e) {
            LoggerHelper.showErrorLog(" SQL :" + e.getMessage());
        }
        return null;
    }

    /**
     * @return
     */
    public Customer getLastCustomer() {
        try {
            String sql = SQLStatement.SELECT_ALL + CustomerM.TABLE_CUSTOMER;
            List<Customer> all = DBUtils.rawQuery(database, new CustomerRowMapper(), sql, null);
            if (all.size() != 0) {
                return all.get(all.size() - 1);
            }
        } catch (SQLException e) {
            LoggerHelper.showErrorLog(" SQL :" + e.getMessage());
        }
        return null;
    }

    /**
     * @return
     */
    public Integer getNumberOfAccount() {
        try {
            String sql = SQLStatement.SELECT_ALL + CustomerM.TABLE_CUSTOMER;
            Cursor cursor = database.rawQuery(sql, null);
            return cursor.getCount();
        } catch (SQLException e) {
            LoggerHelper.showErrorLog(" SQL :" + e.getMessage());
        }
        return null;
    }

    /**
     * @return
     */
    public String getToken() {
        try {
            String sql = SQLStatement.SELECT_ALL + CustomerM.TABLE_CUSTOMER;
            List<Customer> all = DBUtils.rawQuery(database, new CustomerRowMapper(), sql, null);
            if (all.size() != 0) {
                return all.get(all.size() - 1).getRpToken();
            }
        } catch (SQLException e) {
            LoggerHelper.showErrorLog(" SQL :" + e.getMessage());
        }
        return null;
    }

    /**
     * @param customer
     * @return
     */
    public boolean insert(Customer customer) {
        try {
            ContentValues values = getValueFromCustomer(customer);
            database.insert(CustomerM.TABLE_CUSTOMER, null, values);
            return true;
        } catch (Exception e) {
            LoggerHelper.showErrorLog(" SQL :" + e.getMessage());
        }
        return false;
    }

    /**
     * @param customer
     * @return
     */
    public boolean replace(Customer customer) {
        try {
            ContentValues values = getValueFromCustomer(customer);
            database.delete(CustomerM.TABLE_CUSTOMER, null, null);
            database.insert(CustomerM.TABLE_CUSTOMER, null, values);
            return true;
        } catch (Exception e) {
            LoggerHelper.showErrorLog(">> SecUserDAO.insert: USER : " + e.getMessage());
        }
        return false;
    }

    /**
     * @param token
     * @return
     */
    public boolean updateToken(String token) {
        try {
            ContentValues values = new ContentValues();
            values.put(CustomerM.RP_TOKEN, token);
            database.update(CustomerM.TABLE_CUSTOMER, values, null, null);
            LoggerHelper.showDebugLog(">> SecUserDAO.update: USER_TOKEN : " + token);
            return true;
        } catch (Exception e) {
            LoggerHelper.showErrorLog(">> SecUserDAO.update: USER_TOKEN : " + e.getMessage());
        }
        return false;
    }

    /**
     * @return
     */
    public boolean removeAllUser() {
        try {
            database.delete(CustomerM.TABLE_CUSTOMER, null, null);
            LoggerHelper.showDebugLog(">> SecUserDAO.delete: USER: ");
            return true;
        } catch (Exception e) {
            LoggerHelper.showErrorLog(">> SecUserDAO.delete: USER : " + e.getMessage());
        }
        return false;
    }

    /**
     * @param customer
     * @return
     */
    public boolean update(Customer customer) {
        try {
            ContentValues values = getValueFromCustomer(customer);
            database.replace(CustomerM.TABLE_CUSTOMER, null, values);
            return true;
        } catch (Exception e) {
            LoggerHelper.showErrorLog(">> SecUserDAO.update: FAIL " + e.getMessage());
        }
        return false;
    }

    /**
     * @param customer
     * @return
     * @see ContentValues
     */
    private ContentValues getValueFromCustomer(Customer customer) {
        ContentValues values = new ContentValues();
        if (customer == null) {
            return values;
        }

        if (customer.getId() != null) {
            values.put(CustomerM.ID, customer.getId());
        }
        if (customer.getEntityId() != null) {
            values.put(CustomerM.ENTITY_ID, customer.getEntityId());
        }
        if (customer.getWebsiteId() != null) {
            values.put(CustomerM.WEBSITE_ID, customer.getWebsiteId());
        }
        if (customer.getStoreId() != null) {
            values.put(CustomerM.STORE_ID, customer.getStoreId());
        }
        if (customer.getEmail() != null) {
            values.put(CustomerM.EMAIL, customer.getEmail());
        }
        if (customer.getGroupId() != null) {
            values.put(CustomerM.GROUP_ID, customer.getGroupId());
        }
        if (customer.getCreatedAt() != null) {
            values.put(CustomerM.CREATED_AT, customer.getCreatedAt());
        }
        if (customer.getFirstname() != null) {
            values.put(CustomerM.FIRSTNAME, customer.getFirstname());
        }
        if (customer.getLastname() != null) {
            values.put(CustomerM.LASTNAME, customer.getLastname());
        }
        if (customer.getCreatedIn() != null) {
            values.put(CustomerM.CREATED_IN, customer.getCreatedIn());
        }
        if (customer.getUpdateAt() != null) {
            values.put(CustomerM.UPDATED_AT, customer.getUpdateAt());
        }
        if (customer.getDob() != null) {
            values.put(CustomerM.DOB, customer.getDob());
        }
        if (customer.getRpToken() != null) {
            values.put(CustomerM.RP_TOKEN, customer.getRpToken());
        }
        if (customer.getRpTokenCreatedAt() != null) {
            values.put(CustomerM.RP_TOKEN_CREATED_AT, customer.getRpTokenCreatedAt());
        }
            /*if (customer.getAddresses() != null) {
                values.put(CustomerM.ADDRESSES, customer.getAddresses());
            }*/

        return values;
    }
}