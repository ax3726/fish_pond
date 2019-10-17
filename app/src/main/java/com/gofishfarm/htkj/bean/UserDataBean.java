package com.gofishfarm.htkj.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/8
 */
public class UserDataBean {

    /**
     * focus_flag : false
     * fisher_id : 36196679
     * nick_name : sf_PHKQHGEV
     * avatar : https://runmanz-1251536883.cos.ap-shanghai.myqcloud.com/default/itachi.jpg
     * phone : 15026985615
     * sex : 1
     * sex_zh : 男
     * likes : 0
     * focus : 0
     * fans : 0
     * user_fishplat : []
     */

    private String fisher_id;
    private String nick_name;
    private String avatar;
    private String phone;
    private int sex;
    private String sex_zh;
    private String likes;
    private String focus;
    private String fans;
    private String remaining_time;
    private String total_fishing;

    public String getTotal_fishing() {
        return total_fishing == null ? "" : total_fishing;
    }

    public void setTotal_fishing(String total_fishing) {
        this.total_fishing = total_fishing == null ? "" : total_fishing;
    }

    public String getRemaining_time() {
        return remaining_time == null ? "" : remaining_time;
    }

    public void setRemaining_time(String remaining_time) {
        this.remaining_time = remaining_time;
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
}
