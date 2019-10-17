package com.gofishfarm.htkj.bean;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/9
 */
public class UserInformationBean {


    /**
     * avatar : https://runmanz-1251536883.cos.ap-shanghai.myqcloud.com/default/itachi.jpg
     * nick_name : sf_PHKQHGEV
     * sex : 1
     * sex_zh : 男
     * phone : 15026985615
     * wechat_on : true
     * weibo_on : false
     * birthday : 0
     * birthday_ymd : 1970-01-01
     * city : 0
     * city_zh : city name
     * fishing_age : 0
     */

    private String avatar;
    private String nick_name;
    private int sex;
    private String sex_zh;
    private String phone;
    private boolean wechat_on;
    private boolean weibo_on;
    private String birthday;
    private String birthday_ymd;
    private String city;
    private String city_zh;
    private String fishing_age;

    public String getAvatar() {
        return avatar == null ? "" : avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick_name() {
        return nick_name == null ? "" : nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
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

    public String getPhone() {
        return phone == null ? "" : phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isWechat_on() {
        return wechat_on;
    }

    public void setWechat_on(boolean wechat_on) {
        this.wechat_on = wechat_on;
    }

    public boolean isWeibo_on() {
        return weibo_on;
    }

    public void setWeibo_on(boolean weibo_on) {
        this.weibo_on = weibo_on;
    }

    public String getBirthday() {
        return birthday == null ? "" : birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthday_ymd() {
        return birthday_ymd == null ? "" : birthday_ymd;
    }

    public void setBirthday_ymd(String birthday_ymd) {
        this.birthday_ymd = birthday_ymd;
    }

    public String getCity() {
        return city == null ? "" : city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_zh() {
        return city_zh == null ? "" : city_zh;
    }

    public void setCity_zh(String city_zh) {
        this.city_zh = city_zh;
    }

    public String getFishing_age() {
        return fishing_age == null ? "" : fishing_age;
    }

    public void setFishing_age(String fishing_age) {
        this.fishing_age = fishing_age;
    }
}
