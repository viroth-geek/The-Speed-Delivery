
package com.iota.eshopping.model.magento.store.storeList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.iota.eshopping.model.Entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class TimeDelivery extends Entity implements Serializable {

    @SerializedName("0")
    @Expose
    private Long _0;
    @SerializedName("seconds")
    @Expose
    private Long seconds;
    @SerializedName("minutes")
    @Expose
    private Long minutes;
    @SerializedName("hours")
    @Expose
    private Long hours;
    @SerializedName("mday")
    @Expose
    private Long mday;
    @SerializedName("wday")
    @Expose
    private Long wday;
    @SerializedName("mon")
    @Expose
    private Long mon;
    @SerializedName("year")
    @Expose
    private Long year;
    @SerializedName("yday")
    @Expose
    private Long yday;
    @SerializedName("weekday")
    @Expose
    private String weekday;
    @SerializedName("month")
    @Expose
    private String month;
    private final static long serialVersionUID = 9115927769152431433L;

    /**
     * @return
     */
    public Long get0() {
        return _0;
    }

    /**
     * @param _0
     */
    public void set0(Long _0) {
        this._0 = _0;
    }

    /**
     * @return
     */
    public Long getSeconds() {
        return seconds;
    }

    /**
     * @param seconds
     */
    public void setSeconds(Long seconds) {
        this.seconds = seconds;
    }

    /**
     * @return
     */
    public Long getMinutes() {
        return minutes;
    }

    /**
     * @param minutes
     */
    public void setMinutes(Long minutes) {
        this.minutes = minutes;
    }

    /**
     * @return
     */
    public Long getHours() {
        return hours;
    }

    /**
     * @param hours
     */
    public void setHours(Long hours) {
        this.hours = hours;
    }

    /**
     * @return
     */
    public Long getMday() {
        return mday;
    }

    /**
     * @param mday
     */
    public void setMday(Long mday) {
        this.mday = mday;
    }

    /**
     * @return
     */
    public Long getWday() {
        return wday;
    }

    /**
     * @param wday
     */
    public void setWday(Long wday) {
        this.wday = wday;
    }

    /**
     * @return
     */
    public Long getMon() {
        return mon;
    }

    /**
     * @param mon
     */
    public void setMon(Long mon) {
        this.mon = mon;
    }

    /**
     * @return
     */
    public Long getYear() {
        return year;
    }

    /**
     * @param year
     */
    public void setYear(Long year) {
        this.year = year;
    }

    /**
     * @return
     */
    public Long getYday() {
        return yday;
    }

    /**
     * @param yday
     */
    public void setYday(Long yday) {
        this.yday = yday;
    }

    /**
     * @return
     */
    public String getWeekday() {
        return weekday;
    }

    /**
     * @param weekday
     */
    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    /**
     * @return
     */
    public String getMonth() {
        return month;
    }

    /**
     * @param month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("_0", _0).append("seconds", seconds).append("minutes", minutes).append("hours", hours).append("mday", mday).append("wday", wday).append("mon", mon).append("year", year).append("yday", yday).append("weekday", weekday).append("month", month).toString();
    }

}
