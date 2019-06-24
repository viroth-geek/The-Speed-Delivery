
package com.iota.eshopping.model.magento.store.storeList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.iota.eshopping.model.Entity;
import com.iota.eshopping.model.Product;

import java.io.Serializable;
import java.util.List;

public class Category extends Entity implements Serializable {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("imageUrl")
    @Expose
    private Boolean imageUrl;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;
    @SerializedName("count")
    @Expose
    private int count;

    private final static long serialVersionUID = -401305130125273420L;

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
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return
     */
    public Boolean getImageUrl() {
        return imageUrl;
    }

    /**
     * @param imageUrl
     */
    public void setImageUrl(Boolean imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * @return
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * @param products
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /**
     * Get count
     *
     * @return count
     */
    public int getCount() {
        return count;
    }

    /**
     * Setter count
     */
    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", imageUrl=" + imageUrl +
                ", products=" + products +
                ", count=" + count +
                '}';
    }
}
