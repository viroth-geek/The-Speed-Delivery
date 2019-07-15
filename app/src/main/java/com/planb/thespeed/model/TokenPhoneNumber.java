package com.planb.thespeed.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenPhoneNumber {

    @SerializedName("data")
    @Expose
    private Token token;

    public TokenPhoneNumber() {
    }

    public TokenPhoneNumber(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public static class Token {
        @SerializedName("token")
        @Expose
        private String token;

        public Token() {
        }

        public Token(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
