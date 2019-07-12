package com.planb.thespeed.server;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.planb.thespeed.constant.ApplicationConfiguration;
import com.planb.thespeed.constant.entity.SQLStatement;
import com.planb.thespeed.constant.meta.AddressM;
import com.planb.thespeed.constant.meta.CartItemM;
import com.planb.thespeed.constant.meta.CustomerM;
import com.planb.thespeed.constant.meta.LocationM;
import com.planb.thespeed.constant.meta.NotificationM;
import com.planb.thespeed.constant.meta.OptionM;
import com.planb.thespeed.constant.meta.OptionProductM;
import com.planb.thespeed.constant.meta.OptionValueM;
import com.planb.thespeed.constant.meta.ProductOptionM;
import com.planb.thespeed.constant.meta.SequencesM;
import com.planb.thespeed.constant.meta.TokenM;
import com.planb.thespeed.util.LoggerHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author channarith.bong
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private int referenceCount;
    private static DatabaseHelper mInstance;
    private SQLiteDatabase database;

    private static final int DATABASE_VERSION = ApplicationConfiguration.DATABASE_VERSION;

    // Database Name
    private static final String DATABASE_NAME = "nkr_delivery";

    /**
     * @param context Context
     */
    private DatabaseHelper(Context context) {
        super(context, context.getFilesDir() + File.separator + "app" + File.separator + DATABASE_NAME, null, DATABASE_VERSION);
        LoggerHelper.showDebugLog("Database path : " + context.getFilesDir() + File.separator + "app" + File.separator + DATABASE_NAME);
    }

    /**
     * @param context Context
     * @return DatabaseHelper
     */
    public static DatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    /**
     * Attach to database
     *
     * @return SQLiteDatabase
     */
    public synchronized SQLiteDatabase getDatabase() {
        referenceCount++;
        if (database == null || !database.isOpen() || referenceCount == 1) {
            database = getWritableDatabase();
        }
        return database;
    }

//    /**
//     * Connection to database
//     *
//     * @return SQLiteDatabase
//     */
//    public synchronized SQLiteDatabase getReadOnlyDatabase() {
//        referenceCount++;
//
//        if (database == null || !database.isOpen() || referenceCount == 1) {
//            database = getReadableDatabase();
//        }
//        return database;
//    }

    /**
     * Close Connection
     */
    public synchronized void close() {
        referenceCount--;
        if (referenceCount == 0 && database != null && database.isOpen()) {
            database.close();
        }
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        initTableSequences(db);
        createTableCustomer(db);
        createTableCartItem(db);
        createTableLocation(db);
        createTableAddress(db);
        createTableNotification(db);
        createTableToken(db);
        createTableProductOption(db);
        createTableOption(db);
        createTableOptionProduct(db);
        createTableOptionValue(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL(SQLStatement.DROP_TABLE_EXIST + SequencesM.TABLE_SEQUENCES);
        db.execSQL(SQLStatement.DROP_TABLE_EXIST + CustomerM.TABLE_CUSTOMER);
        db.execSQL(SQLStatement.DROP_TABLE_EXIST + CartItemM.TABLE_CART_ITEM);
        db.execSQL(SQLStatement.DROP_TABLE_EXIST + LocationM.TABLE_LOCATION);
        db.execSQL(SQLStatement.DROP_TABLE_EXIST + NotificationM.TABLE_NOTIFICATION);
        db.execSQL(SQLStatement.DROP_TABLE_EXIST + TokenM.TABLE_TOKEN);

        // create database again
        onCreate(db);
    }

    /**
     * Table Sequences
     *
     * @param db SQLiteDatabase
     */
    private void initTableSequences(SQLiteDatabase db) {
        String sql = SQLStatement.CREATE_TABLE_NEW + SequencesM.TABLE_SEQUENCES + " (" +
                SequencesM.TABLE_NAME + " text primary key," +
                SequencesM.SEQ_VALUE + " integer not null)";

        try {
            List<String> tables = new ArrayList<>();
            tables.add(CustomerM.TABLE_CUSTOMER);

            db.execSQL(sql);

            for (String tableName : tables) {
                ContentValues values = new ContentValues();
                values.put(SequencesM.TABLE_NAME, tableName);
                values.put(SequencesM.SEQ_VALUE, 0);
                db.insert(SequencesM.TABLE_SEQUENCES, null, values);
            }

        } catch (SQLException e) {
            LoggerHelper.showErrorLog("<< initTableSequences: " + e.getMessage());
        }
    }

    /**
     * Table Customer
     *
     * @param db SQLiteDatabase
     */
    private void createTableCustomer(SQLiteDatabase db) {
        String sql = SQLStatement.CREATE_TABLE_NEW +
                CustomerM.TABLE_CUSTOMER + " (" +
                CustomerM.ID + " integer primary key," +
                CustomerM.ENTITY_ID + " integer," +
                CustomerM.GROUP_ID + " integer," +
                CustomerM.CREATED_AT + " timestamp," +
                CustomerM.UPDATED_AT + " timestamp," +
                CustomerM.CREATED_IN + " text," +
                CustomerM.EMAIL + " text," +
                CustomerM.FIRSTNAME + " text," +
                CustomerM.LASTNAME + " text," +
                CustomerM.RP_TOKEN + " text," +
                CustomerM.RP_TOKEN_CREATED_AT + " text, " +
                CustomerM.STORE_ID + " integer," +
                CustomerM.WEBSITE_ID + " integer," +
                CustomerM.DOB + " text)";
        try {
            db.execSQL(sql);
        } catch (SQLException e) {
            LoggerHelper.showErrorLog("--DB-- createTableCustomer: " + e.getMessage());
        }
    }

    /**
     * Table Cart Item
     *
     * @param db SQLiteDatabase
     */
    private void createTableCartItem(SQLiteDatabase db) {
        String sql = SQLStatement.CREATE_TABLE_NEW +
                CartItemM.TABLE_CART_ITEM + " (" +
                CartItemM.PRODUCT_UID + " text primary key," +
                CartItemM.PRODUCT_ID + " integer, " +
                CartItemM.PRODUCT_NAME + " text," +
                CartItemM.PRODUCT_TYPE + " text," +
                CartItemM.SKU + " text," +
                CartItemM.PRICE + " real," +
                CartItemM.QTY + " double," +
                CartItemM.STORE_ID + " integer," +
                CartItemM.PARENT_ID + " integer)";
        try {
            db.execSQL(sql);
        } catch (SQLException e) {
            LoggerHelper.showErrorLog("--DB-- createTableCartItem: " + e.getMessage());
        }
    }

    /**
     * Table Location
     *
     * @param db SQLiteDatabase
     */
    private void createTableLocation(SQLiteDatabase db) {
        String sql = SQLStatement.CREATE_TABLE_NEW +
                LocationM.TABLE_LOCATION + " (" +
                LocationM.ID + " integer primary key," +
                LocationM.LATITUDE + " integer," +
                LocationM.LONGITUDE + " integer," +
                LocationM.CITY + " text," +
                LocationM.POSTCODE + " integer," +
                LocationM.COUNTRY_ID + " text)";
        try {
            db.execSQL(sql);
            LoggerHelper.showDebugLog("--DB-- createTableLocation: SUCCESS ");
        } catch (SQLException e) {
            LoggerHelper.showErrorLog("--DB-- createTableLocation " + e.getMessage());
        }
    }

    /**
     * Table Address
     *
     * @param db SQLiteDatabase
     */
    private void createTableAddress(SQLiteDatabase db) {
        String sql = SQLStatement.CREATE_TABLE_NEW +
                AddressM.TABLE_ADDRESS + " (" +
                AddressM.ID + " integer primary key," +
                AddressM.CUSTOMER_ID + " integer," +
                AddressM.COUNTRY_ID + " text," +
                AddressM.COUNTRY_CODE + " text," +
                AddressM.COUNTRY_NAME + " text," +
                AddressM.TELEPHONE + " text," +
                AddressM.POSTCODE + " text," +
                AddressM.CITY + " text," +
                AddressM.FIRST_NAME + " text," +
                AddressM.LAST_NAME + " text," +
                AddressM.DEFAULT_BILLING + " integer," +
                AddressM.DEFAULT_SHIPPING + " integer," +
                AddressM.LATITUDE + " text," +
                AddressM.LONGITUDE + " text," +
                AddressM.ADDRESS_LINE + " text)";

        try {
            db.execSQL(sql);
            LoggerHelper.showDebugLog("--DB-- createTableAddress: SUCCESS ");
        } catch (SQLException e) {
            LoggerHelper.showErrorLog("--DB-- createTableAddress " + e.getMessage());
        }
    }

    /**
     * Table Notification
     *
     * @param db SQLiteDatabase
     */
    private void createTableNotification(SQLiteDatabase db) {
        String sql = SQLStatement.CREATE_TABLE_NEW +
                NotificationM.TABLE_NOTIFICATION + " (" +
                NotificationM.ID + " integer primary key," +
                NotificationM.TITLE + " text," +
                NotificationM.BODY + " text," +
                NotificationM.RECEIVED_DATE + " text," +
                NotificationM.IMAGE_URL + " text," +
                NotificationM.DATA + " text)";
        try {
            db.execSQL(sql);
            LoggerHelper.showDebugLog("--DB-- createTableNotification: SUCCESS ");
        } catch (SQLException e) {
            LoggerHelper.showErrorLog("--DB-- createTableNotification " + e.getMessage());
        }
    }

    /**
     * Table Token
     *
     * @param db SQLiteDatabase
     */
    private void createTableToken(SQLiteDatabase db) {
        String sql = SQLStatement.CREATE_TABLE_NEW +
                TokenM.TABLE_TOKEN + " (" +
                TokenM.ID + " integer primary key," +
                TokenM.KEY + " text," +
                TokenM.KEY_TYPE + " text," +
                TokenM.TAG + " text)";
        try {
            db.execSQL(sql);
            LoggerHelper.showDebugLog("--DB-- createTableToken: SUCCESS ");
        } catch (SQLException e) {
            LoggerHelper.showErrorLog("--DB-- createTableToken " + e.getMessage());
        }
    }

    /**
     * create table product option
     * @param sqLiteDatabase SQLiteDatabase
     */
    private void createTableProductOption(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(SQLStatement.CREATE_TABLE_NEW).append(ProductOptionM.TABLE_PRODUCT_OPTION)
                .append("(")
                    .append(ProductOptionM.ID).append(" integer ").append(" primary key ").append(" AUTOINCREMENT ").append(" , ")
                    .append(ProductOptionM.ATTRIBUTE_ID).append(" integer ").append(" , ")
                    .append(ProductOptionM.LABEL).append(" text ").append(" , ")
                    .append(ProductOptionM.PRODUCT_ID).append(" integer ").append(" , ")
                    .append(ProductOptionM.PRODUCT_UID).append(" text ").append(" , ")
                    .append(ProductOptionM.ORIGINAL_PRODUCT_ID).append(" integer ")
                .append(");");
        try {
            sqLiteDatabase.execSQL(sqlBuilder.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * create table option
     * @param sqLiteDatabase SQLiteDatabase
     */
    private void createTableOption(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(SQLStatement.CREATE_TABLE_NEW).append(OptionM.TABLE_OPTION)
                .append("(")
                .append(OptionM.ID).append(" integer ").append(" primary key ").append(" AUTOINCREMENT ").append(" , ")
                .append(OptionM.LABEL).append(" text ").append(" , ")
                .append(OptionM.VALUE).append(" integer ").append(" , ")
                .append(OptionM.PRODUCT_UID).append(" text ").append(" , ")
                .append(OptionM.FK_PRODUCT_OPTION).append(" integer ").append(" , ")
                .append("FOREIGN KEY")
                    .append(" ( ")
                        .append(OptionM.FK_PRODUCT_OPTION)
                    .append(" ) ")
                .append("REFERENCES")
                    .append(ProductOptionM.TABLE_PRODUCT_OPTION)
                    .append("(")
                        .append(ProductOptionM.ID)
                    .append(")")
                .append(");");
        try {
            sqLiteDatabase.execSQL(sqlBuilder.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * create table option product
     * @param sqLiteDatabase SQLiteDatabase
     */
    private void createTableOptionProduct(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(SQLStatement.CREATE_TABLE_NEW).append(OptionProductM.TABLE_OPTION_PRODUCT)
                .append("(")
                .append(OptionProductM.ID).append(" integer ").append(" primary key ").append(" AUTOINCREMENT ").append(" , ")
                .append(OptionProductM.OPTION_ID).append(" text ").append(" , ")
                .append(OptionProductM.TITLE).append(" text ").append(" , ")
                .append(OptionProductM.PRODUCT_ID).append(" integer ").append(" , ")
                .append(OptionProductM.PRODUCT_UID).append(" text ").append(" , ")
                .append(OptionProductM.ORIGINAL_PRODUCT_ID).append(" integer ")
                .append(");");
        try {
            sqLiteDatabase.execSQL(sqlBuilder.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * create table option value
     * @param sqLiteDatabase SQLiteDatabase
     */
    private void createTableOptionValue(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(SQLStatement.CREATE_TABLE_NEW).append(OptionValueM.TABLE_OPTION_VALUE)
                .append("(")
                .append(OptionValueM.ID).append(" integer ").append(" primary key ").append(" AUTOINCREMENT ").append(" , ")
                .append(OptionValueM.OPTION_TYPE_ID).append(" integer ").append(" , ")
                .append(OptionValueM.OPTION_VALUE_TITLE).append(" text ").append(" , ")
                .append(OptionValueM.PRODUCT_UID).append(" text ").append(" , ")
                .append(OptionValueM.PRICE).append(" double ").append(" , ")
                .append(OptionValueM.FK_OPTION_PRODUCT).append(" integer ").append(" , ")
                .append("FOREIGN KEY")
                    .append(" ( ")
                        .append(OptionValueM.FK_OPTION_PRODUCT)
                    .append(" ) ")
                .append("REFERENCES")
                    .append(OptionProductM.TABLE_OPTION_PRODUCT)
                        .append("(")
                            .append(OptionProductM.ID)
                        .append(")")
                .append(");");
        try {
            sqLiteDatabase.execSQL(sqlBuilder.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
















