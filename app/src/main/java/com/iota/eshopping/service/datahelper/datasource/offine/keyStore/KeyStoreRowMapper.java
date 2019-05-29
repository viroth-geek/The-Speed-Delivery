package com.iota.eshopping.service.datahelper.datasource.offine.keyStore;

import android.database.Cursor;

import com.iota.eshopping.constant.meta.TokenM;
import com.iota.eshopping.model.Token;
import com.iota.eshopping.service.datahelper.datasource.offine.RowMapper;

/**
 * @author channarith.bong
 */
public class KeyStoreRowMapper implements RowMapper<Token> {
    @Override
    public Token mappedRow(Cursor cursor) {
        Token bean = new Token();

        if (!cursor.isNull(cursor.getColumnIndex(TokenM.ID))) {
            bean.setId(cursor.getInt(cursor.getColumnIndex(TokenM.ID)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(TokenM.KEY_TYPE))) {
            bean.setKeyType(cursor.getString(cursor.getColumnIndex(TokenM.KEY_TYPE)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(TokenM.KEY))) {
            bean.setKey(cursor.getString(cursor.getColumnIndex(TokenM.KEY)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(TokenM.TAG))) {
            bean.setKey(cursor.getString(cursor.getColumnIndex(TokenM.TAG)));
        }
        return bean;
    }
}
