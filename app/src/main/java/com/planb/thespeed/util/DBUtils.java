package com.planb.thespeed.util;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.planb.thespeed.constant.ConstantValue;
import com.planb.thespeed.service.datahelper.datasource.offine.RowMapper;

import java.util.ArrayList;
import java.util.List;

public class DBUtils {

    /**
     * @param db
     * @param mapper
     * @param sql
     * @param cols
     * @param <T>
     * @return
     */
    public static <T> List<T> rawQuery(SQLiteDatabase db, RowMapper<T> mapper, String sql, String[] cols) {
        List<T> all = new ArrayList<T>();
        if (db == null || mapper == null || sql == null) {
            return all;
        }
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(sql, cols);
            while (cursor.moveToNext()) {
                try {
                    all.add(mapper.mappedRow(cursor));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return all;
    }

    /**
     * @param db
     * @param table
     * @param columns
     * @param where
     * @param whereArgs
     * @param mapper
     * @param <T>
     * @return
     */
    public static <T> List<T> query(SQLiteDatabase db, String table, String[] columns, String where, String[] whereArgs, RowMapper<T> mapper) {
        List<T> all = new ArrayList<T>();
        if (db == null || mapper == null)
            return all;

        Cursor cursor = db.query(false, table, columns, where, whereArgs, null, null, null, null);

        try {
            while (cursor.moveToNext()) {
                try {
                    all.add(mapper.mappedRow(cursor));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return all;
    }

    /**
     * return rowId of the row inserted, otherwise -1
     *
     * @param db
     * @param tableName
     * @param columns
     * @param where
     * @param whereArgs
     * @param values
     * @param mapper
     * @param <T>
     * @return
     */
    public static <T> long replace(SQLiteDatabase db, String tableName, String[] columns, String where, String[] whereArgs, ContentValues values, RowMapper<T> mapper) {
        long rowId = -1;
        boolean bindParameterNull = false;

        for (int i = 0; i < whereArgs.length; i++) {
            if (whereArgs[i] == null) {
                bindParameterNull = true;
                break;
            }
        }

        if (bindParameterNull) {
            rowId = db.insert(tableName, null, values);
        } else {
            List<T> all = query(db, tableName, columns, where, whereArgs, mapper);
            if (all.size() > 0) {
                db.update(tableName, values, where, whereArgs);
            } else {
                rowId = db.insert(tableName, null, values);
            }
        }

        return rowId;
    }

    /**
     * @param db
     * @param tableName
     * @param where
     * @param trxNo
     * @param offlineId
     * @param values
     * @param <T>
     * @return
     */
    public static <T> long replace(SQLiteDatabase db, String tableName, String where, String trxNo, Long offlineId, ContentValues values) {
        long rowId = -1;
        Log.d(ConstantValue.TAG, ">> DBUtils.replace: table name=" + tableName + ", trxNo=" + trxNo + ", offlineId=" + offlineId + ", where=" + where);

        try {

            long count = getCount(db, tableName, where, trxNo, offlineId);
            Log.d(ConstantValue.TAG, ">> DBUtils.replace: count=" + count);

            if (count == 0)
                rowId = db.insert(tableName, null, values);
            else {
                int affectedRows = db.update(tableName, values, where, new String[]{trxNo, offlineId.longValue() + ""});
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return rowId;
    }

    /**
     * @param db
     * @param tableName
     * @param where
     * @param id
     * @param values
     * @param <T>
     * @return
     */
    public static <T> long replace(SQLiteDatabase db, String tableName, String where, Long id, ContentValues values) {
        long rowId = -1;

        if (id == null) {
            rowId = db.insert(tableName, null, values);
        } else {
            db.update(tableName, values, where, new String[]{id.longValue() + ""});
        }

        return rowId;
    }

    /**
     * @param db
     * @param tableName
     * @param where
     * @param trxNo
     * @param offlineId
     * @return
     */
    public static long getCount(SQLiteDatabase db, String tableName, String where, String trxNo, Long offlineId) {
        int count = 0;
        Cursor cursor = null;

        try {
            cursor = db.query(tableName, null, where, new String[]{trxNo, offlineId + ""}, null, null, null);

            if (cursor != null)
                count = cursor.getCount();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return count;
    }

    /**
     * @param db
     * @param tableName
     * @return
     */
    public static Long getNextId(SQLiteDatabase db, String tableName) {
        Long id = null;
        Cursor cursor = null;

        try {
            cursor = db.query("ts_sequences", null, "table_name=?", new String[]{tableName}, null, null, null);

            if (cursor != null && cursor.moveToNext()) {
                id = cursor.getLong(cursor.getColumnIndex("seq_value"));

                ContentValues values = new ContentValues();
                values.put("table_name", tableName);
                values.put("seq_value", id.longValue() + 1);
                db.update("ts_sequences", values, "table_name=?", new String[]{tableName});
                Log.d(ConstantValue.TAG, "<< DBUtils.getNextId: table_name=" + tableName + ", seq_value=" + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return id + 1;
    }

}
