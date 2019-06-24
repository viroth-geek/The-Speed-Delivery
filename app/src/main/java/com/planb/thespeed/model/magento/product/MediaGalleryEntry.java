
package com.planb.thespeed.model.magento.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.planb.thespeed.model.Entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * @author channarith.bong
 */
public class MediaGalleryEntry extends Entity {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("media_type")
    @Expose
    private String mediaType;
    @SerializedName("label")
    @Expose
    private Object label;
    @SerializedName("position")
    @Expose
    private Long position;
    @SerializedName("disabled")
    @Expose
    private Boolean disabled;
    @SerializedName("types")
    @Expose
    private List<String> types = null;
    @SerializedName("file")
    @Expose
    private String file;
    private final static long serialVersionUID = 5490574611861448989L;

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
    public String getMediaType() {
        return mediaType;
    }

    /**
     * @param mediaType
     */
    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    /**
     * @return
     */
    public Object getLabel() {
        return label;
    }

    /**
     * @param label
     */
    public void setLabel(Object label) {
        this.label = label;
    }

    /**
     * @return
     */
    public Long getPosition() {
        return position;
    }

    /**
     * @param position
     */
    public void setPosition(Long position) {
        this.position = position;
    }

    /**
     * @return
     */
    public Boolean getDisabled() {
        return disabled;
    }

    /**
     * @param disabled
     */
    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    /**
     * @return
     */
    public List<String> getTypes() {
        return types;
    }

    /**
     * @param types
     */
    public void setTypes(List<String> types) {
        this.types = types;
    }

    /**
     * @return
     */
    public String getFile() {
        return file;
    }

    /**
     * @param file
     */
    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("mediaType", mediaType).append("label", label).append("position", position).append("disabled", disabled).append("types", types).append("file", file).toString();
    }

}
