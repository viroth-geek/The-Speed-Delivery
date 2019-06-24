package com.planb.thespeed.service.datahelper.datasource.offine.localnotification;

import android.database.Cursor;

import com.planb.thespeed.constant.meta.NotificationM;
import com.planb.thespeed.model.Notification;
import com.planb.thespeed.service.datahelper.datasource.offine.RowMapper;

/**
 * Created by Chanrith on 4/17/2018.
 *
 * @author Chanrith
 */
public class NotificationRowMapper implements RowMapper<Notification> {
    @Override
    public Notification mappedRow(Cursor cursor) {
        Notification bean = new Notification();
        if (!cursor.isNull(cursor.getColumnIndex(NotificationM.ID))) {
            bean.setId(cursor.getLong(cursor.getColumnIndex(NotificationM.ID)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(NotificationM.TITLE))) {
            bean.setTitle(cursor.getString(cursor.getColumnIndex(NotificationM.TITLE)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(NotificationM.BODY))) {
            bean.setBody(cursor.getString(cursor.getColumnIndex(NotificationM.BODY)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(NotificationM.RECEIVED_DATE))) {
            bean.setDate(cursor.getString(cursor.getColumnIndex(NotificationM.RECEIVED_DATE)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(NotificationM.IMAGE_URL))) {
            bean.setImageUrl(cursor.getString(cursor.getColumnIndex(NotificationM.IMAGE_URL)));
        }
        if (!cursor.isNull(cursor.getColumnIndex(NotificationM.DATA))) {
            bean.setData(cursor.getString(cursor.getColumnIndex(NotificationM.DATA)));
        }
        return bean;
    }
}
