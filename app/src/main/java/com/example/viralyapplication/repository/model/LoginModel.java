package com.example.viralyapplication.repository.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginModel implements Serializable {
        @SerializedName("isAuthenticated")
        @Expose
        private Boolean isAuthenticated;
        @SerializedName("account")
        @Expose
        private UserModel account;

        public Boolean getIsAuthenticated() {
            return isAuthenticated;
        }

        public void setIsAuthenticated(Boolean isAuthenticated) {
            this.isAuthenticated = isAuthenticated;
        }

        public UserModel getAccount() {
            return account;
        }

        public void setAccount(UserModel account) {
            this.account = account;
        }
}

