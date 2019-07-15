package com.planb.thespeed.service.datahelper.datasource.offine.localnotification;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.planb.thespeed.constant.entity.SQLStatement;
import com.planb.thespeed.constant.meta.NotificationM;
import com.planb.thespeed.model.Notification;
import com.planb.thespeed.util.DBUtils;
import com.planb.thespeed.util.LoggerHelper;

import java.util.Collections;
import java.util.List;

/**
 * @author channarith.bong
 */

public class FetchNotificationDAO {

    private SQLiteDatabase database;

    /**
     * @param database
     */
    public FetchNotificationDAO(SQLiteDatabase database) {
        this.database = database;
    }

    /**
     * @param notification
     * @return
     */
    public boolean insert(Notification notification) {
        try {
            ContentValues values = getValueFromNotification(notification);
            database.insert(NotificationM.TABLE_NOTIFICATION, null, values);
            return true;
        } catch (SQLException e) {
            LoggerHelper.showErrorLog(" SQL :" + e.getMessage());
        }
        return false;
    }

    /**
     * @param id
     * @return
     */
    public boolean delete(Long id) {
        try {
            database.delete(NotificationM.TABLE_NOTIFICATION, NotificationM.ID + "=" + id, null);
            return true;
        } catch (SQLException e) {
            LoggerHelper.showErrorLog(" SQL :" + e.getMessage());
        }
        return false;
    }

    /**
     * Get list of notification
     *
     * @return
     */
    public List<Notification> getListNotification() {
        try {
            String sql = SQLStatement.SELECT_ALL + NotificationM.TABLE_NOTIFICATION ;
            List<Notification> all = DBUtils.rawQuery(database, new NotificationRowMapper(), sql, null);
            Collections.reverse(all);
            return all;
        } catch (SQLException e) {
            LoggerHelper.showErrorLog(" SQL :" + e.getMessage());
        }
        return null;
    }

    /**
     * @param notification
     * @return
     */
    private ContentValues getValueFromNotification(Notification notification) {
        ContentValues values = new ContentValues();
        if (notification == null) {
            return values;
        }

        if (notification.getId() != null) {
            values.put(NotificationM.ID, notification.getId());
        }
        if (notification.getTitle() != null) {
            values.put(NotificationM.TITLE, notification.getTitle());
        }
        if (notification.getBody() != null) {
            values.put(NotificationM.BODY, notification.getBody());
        }
        if (notification.getDate() != null) {
            values.put(NotificationM.RECEIVED_DATE, notification.getDate());
        }
        if (notification.getImageUrl() != null) {
            values.put(NotificationM.IMAGE_URL, notification.getImageUrl());
        }
        if (notification.getData() != null) {
            values.put(NotificationM.DATA, notification.getData());
        }
        return values;
    }
}

