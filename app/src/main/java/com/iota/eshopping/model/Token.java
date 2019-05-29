package com.iota.eshopping.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Chanrith on 4/26/2018.
 */
public class Token extends Entity {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("key_type")
    @Expose
    private String keyType;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("tag")
    @Expose
    private String tag;

    /**
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return
     */
    public String getKeyType() {
        return keyType;
    }

    /**
     * @param keyType
     */
    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    /**
     * @return
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }
}
