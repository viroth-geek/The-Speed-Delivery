
package com.iota.eshopping.model.direction;

import com.google.gson.annotations.SerializedName;

public class Polyline {

    @SerializedName("points")
    private String mPoints;

    public String getPoints() {
        return mPoints;
    }

    public void setPoints(String points) {
        mPoints = points;
    }

}