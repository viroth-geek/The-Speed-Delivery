package com.planb.thespeed.model.enumeration;

/**
 * @author yeakleang.ay on 8/10/18.
 */

public enum SocialType {

    FACEBOOK("facebook"),

    GOOGLE("google")

    ;

    private String value;

    SocialType(String value) {
        this.value = value;
    }

    /**
     * Get value
     *
     * @return value
     */
    public String getValue() {
        return value;
    }
}
