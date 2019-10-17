package com.gofishfarm.htkj.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/13
 */
public class HomepageBean {


    /**
     * focus_flag : false
     * fisher_id : 83281195
     * nick_name : 摸索着人了做
     * avatar : https://sfyb-1258095529.cos.ap-chengdu.myqcloud.com/default/itachi.jpg
     * phone : 15026985615
     * sex : 2
     * sex_zh : 女
     * likes : 0
     * focus : 0
     * fans : 0
     * user_fishplat : []
     */

    private int focus_status;
    private String fisher_id;
    private String nick_name;
    private String avatar;
    private String phone;
    private int sex;
    private String sex_zh;
    private String likes;
    private String focus;
    private String fans;
    private String total_fishing;
    private List<UserFishplatBean> user_fishplat;

    public String getTotal_fishing() {
        return total_fishing == null ? "" : total_fishing;
    }

    public void setTotal_fishing(String total_fishing) {
        this.total_fishing = total_fishing == null ? "" : total_fishing;
    }

    public int getFocus_status() {
        return focus_status;
    }

    public void setFocus_status(int focus_status) {
        this.focus_status = focus_status;
    }

    public String getFisher_id() {
        return fisher_id == null ? "" : fisher_id;
    }

    public void setFisher_id(String fisher_id) {
        this.fisher_id = fisher_id;
    }

    public String getNick_name() {
        return nick_name == null ? "" : nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getAvatar() {
        return avatar == null ? "" : avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone == null ? "" : phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSex_zh() {
        return sex_zh == null ? "" : sex_zh;
    }

    public void setSex_zh(String sex_zh) {
        this.sex_zh = sex_zh;
    }

    public String getLikes() {
        return likes == null ? "" : likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getFocus() {
        return focus == null ? "" : focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public String getFans() {
        return fans == null ? "" : fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public List<UserFishplatBean> getUser_fishplat() {
        if (user_fishplat == null) {
            return new ArrayList<>();
        }
        return user_fishplat;
    }

    public void setUser_fishplat(List<UserFishplatBean> user_fishplat) {
        this.user_fishplat = user_fishplat;
    }

    public static class UserFishplatBean{

        String time;
        String timestamp;
        boolean on;
        String fp_id;
        String fishery_name;
        String rewards;

        public String getTime() {
            return time == null ? "" : time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTimestamp() {
            return timestamp == null ? "" : timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public boolean isOn() {
            return on;
        }

        public void setOn(boolean on) {
            this.on = on;
        }

        public String getFp_id() {
            return fp_id == null ? "" : fp_id;
        }

        public void setFp_id(String fp_id) {
            this.fp_id = fp_id;
        }

        public String getFishery_name() {
            return fishery_name == null ? "" : fishery_name;
        }

        public void setFishery_name(String fishery_name) {
            this.fishery_name = fishery_name;
        }

        public String getRewards() {
            return rewards == null ? "" : rewards;
        }

        public void setRewards(String rewards) {
            this.rewards = rewards;
        }
    }
}
