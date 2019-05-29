package com.iota.eshopping.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author yeakleang.ay on 1/28/19.
 */

public class Tag extends Entity {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;

    /**
     * Get id
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get description
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
