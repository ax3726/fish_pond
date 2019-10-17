package com.gofishfarm.htkj.bean;

import java.io.Serializable;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/6
 */
public class UserInfoBean implements Serializable {

    private String access_token;
    private String expires_time;
    private String refresh_token;
    private boolean locked;
    private UserBean user;

    public String getAccess_token() {
        return access_token == null ? "" : access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_time() {
        return expires_time == null ? "" : expires_time;
    }

    public void setExpires_time(String expires_time) {
        this.expires_time = expires_time;
    }

    public String getRefresh_token() {
        return refresh_token == null ? "" : refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean implements Serializable {
        /**
         * fisher_id : 67224273
         * apikey : mKm3pTXSZtwAi2HVj0GhiAFHYcG6XOms
         * phone : 18267857539
         * invite_token : b4paLVLjGzM8rta6p0bJDbXsLcOzxMp3
         */

        private String fisher_id;
        private String apikey;
        private String phone;
        private String invite_token;

        public String getFisher_id() {
            return fisher_id == null ? "" : fisher_id;
        }

        public void setFisher_id(String fisher_id) {
            this.fisher_id = fisher_id;
        }

        public String getApikey() {
            return apikey == null ? "" : apikey;
        }

        public void setApikey(String apikey) {
            this.apikey = apikey;
        }

        public String getPhone() {
            return phone == null ? "" : phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getInvite_token() {
            return invite_token == null ? "" : invite_token;
        }

        public void setInvite_token(String invite_token) {
            this.invite_token = invite_token;
        }
    }
}
