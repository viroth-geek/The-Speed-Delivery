package com.iota.eshopping.model.form;

import com.google.gson.annotations.SerializedName;

/**
 * @author yeakleang.ay on 8/10/18.
 */

public class SocialLoginForm {

    @SerializedName("login")
    private FormSocialUser formSocialUser;

    public SocialLoginForm(FormSocialUser formSocialUser) {
        this.formSocialUser = formSocialUser;
    }

    /**
     * Get formSocialUser
     *
     * @return formSocialUser
     */
    public FormSocialUser getFormSocialUser() {
        return formSocialUser;
    }

    /**
     * Setter formSocialUser
     */
    public void setFormSocialUser(FormSocialUser formSocialUser) {
        this.formSocialUser = formSocialUser;
    }
}
