package com.iota.eshopping.model.phone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Phone {

    @SerializedName("rp_token")
    @Expose
    private String rpToken;

    public Phone(String rpToken) {
        this.rpToken = rpToken;
    }

    public String getRpToken() {
        return rpToken;
    }

    public void setRpToken(String rpToken) {
        this.rpToken = rpToken;
    }
}
