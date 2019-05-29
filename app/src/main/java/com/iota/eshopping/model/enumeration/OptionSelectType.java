package com.iota.eshopping.model.enumeration;

/**
 * @author yeakleang.ay on 7/17/18.
 */

public enum  OptionSelectType {

    CHECK_BOX("checkbox"),

    RADIO_BUTTON("radio")

    ;

    private String name;

    OptionSelectType(String name) {
        this.name = name;
    }

    /**
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }
}
