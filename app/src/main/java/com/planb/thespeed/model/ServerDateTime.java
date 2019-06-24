package com.planb.thespeed.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author yeakleang.ay on 7/17/18.
 */

public class ServerDateTime {

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("time")
    @Expose
    private String time;

    /**
     *
     * @return String
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @param date String
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * @return String
     */
    public String getTime() {
        return time;
    }

    /**
     *
     * @param time String
     */
    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ServerDateTime{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
