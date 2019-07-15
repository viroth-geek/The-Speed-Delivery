package com.planb.thespeed.model;

/**
 * Created by channarith.bong on 2/9/18.
 *
 * @author channarith.bong
 */

public class Notification extends Entity {

    private Long id;
    private String date;
    private String title;
    private String body;
    private String imageUrl;
    private String data;

    /**
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * @return
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * @param imageUrl
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Get data
     *
     * @return data
     */
    public String getData() {
        return data;
    }

    /**
     * Setter data
     */
    public void setData(String data) {
        this.data = data;
    }
}
