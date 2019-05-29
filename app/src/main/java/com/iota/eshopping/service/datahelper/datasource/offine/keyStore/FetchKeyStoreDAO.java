package com.iota.eshopping.service.datahelper.datasource.offine.keyStore;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.iota.eshopping.constant.entity.SQLStatement;
import com.iota.eshopping.constant.meta.TokenM;
import com.iota.eshopping.model.Token;
import com.iota.eshopping.util.DBUtils;
import com.iota.eshopping.util.LoggerHelper;

import java.util.List;

/**
 * @author channarith.bong
 */
public class FetchKeyStoreDAO {

    private SQLiteDatabase database;

    /**
     * @param database
     */
    public FetchKeyStoreDAO(SQLiteDatabase database) {
        this.database = database;
    }

    /**
     * @param token
     * @return
     */
    public boolean insert(Token token) {
        try {
            ContentValues values = getValueFromToken(token);
            if (getToken(token.getKeyType()) == null) {
                database.insert(TokenM.TABLE_TOKEN, null, values);
            } else {
                database.update(TokenM.TABLE_TOKEN, values, TokenM.KEY_TYPE + "=?", new String[]{token.getKeyType()});
            }
            return true;
        } catch (Exception e) {
            LoggerHelper.showErrorLog(" SQL :" + e.getMessage());
        }
        return false;
    }

    /**
     * @return
     */
    public List<Token> getTokenList() {
        try {
            String sql = SQLStatement.SELECT_ALL + TokenM.TABLE_TOKEN;
            List<Token> all = DBUtils.rawQuery(database, new KeyStoreRowMapper(), sql, null);
            return all;
        } catch (SQLException e) {
            LoggerHelper.showErrorLog(" SQL :" + e.getMessage());
        }
        return null;
    }

    /**
     * @return
     */
    public String getToken(String type) {
        try {
            List<String> key = DBUtils.query(database, TokenM.TABLE_TOKEN, new String[]{TokenM.KEY}, TokenM.KEY_TYPE + "=?", new String[]{type}, null);
            return key.get(0);
        } catch (SQLException e) {
            LoggerHelper.showErrorLog(" SQL :" + e.getMessage());
        }
        return null;
    }

    /**
     * @param token
     * @return
     */
    private ContentValues getValueFromToken(Token token) {
        ContentValues values = new ContentValues();
        if (token == null) {
            return values;
        }
        if (token.getId() != null) {
            values.put(TokenM.ID, token.getId());
        }
        if (token.getKey() != null) {
            values.put(TokenM.KEY, token.getKey());
        }
        if (token.getKeyType() != null) {
            values.put(TokenM.KEY_TYPE, token.getKeyType());
        }
        if (token.getTag() != null) {
            values.put(TokenM.TAG, token.getTag());
        }

        return values;
    }
}
