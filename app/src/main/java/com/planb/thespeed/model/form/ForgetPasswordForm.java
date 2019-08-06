package com.planb.thespeed.model.form;

/**
 * @author yeakleang.ay on 8/13/18.
 */

public class ForgetPasswordForm {

    private String email;

    private String template;

    private Long webSiteId;

    /**
     * Constructor
     * @param email String
     * @param template String
     * @param webSiteId Long
     */
    public ForgetPasswordForm(String email, String template, Long webSiteId) {
        this.email = email;
        this.template = template;
        this.webSiteId = webSiteId;
    }

    /**
     * Get email
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get template
     *
     * @return template
     */
    public String getTemplate() {
        return template;
    }

    /**
     * Setter template
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * Get webSiteId
     *
     * @return webSiteId
     */
    public Long getWebSiteId() {
        return webSiteId;
    }

    /**
     * Setter webSiteId
     */
    public void setWebSiteId(Long webSiteId) {
        this.webSiteId = webSiteId;
    }
}
