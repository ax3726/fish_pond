package com.gofishfarm.htkj.bean;

/**
 * @author：MrHu
 * @Date ：2019-01-11
 * @Describtion ：
 */
public class OnLookFocousBean {
    /**
     * fisher_id : 13164844
     * avatar : http://sfyb-1258095529.cos.ap-chengdu.myqcloud.com/avatar/1547614250661head.png
     * nick_name : 钱塘一哥
     * focused : 1
     * rank : 第3
     * fishery_name : 1号钓台
     */

    private String fisher_id;
    private String avatar;
    private String nick_name;
    private int focused;
    private String rank;
    private String fishery_name;

    public String getFisher_id() {
        return fisher_id == null ? "" : fisher_id;
    }

    public void setFisher_id(String fisher_id) {
        this.fisher_id = fisher_id == null ? "" : fisher_id;
    }

    public String getAvatar() {
        return avatar == null ? "" : avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? "" : avatar;
    }

    public String getNick_name() {
        return nick_name == null ? "" : nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name == null ? "" : nick_name;
    }

    public int getFocused() {
        return focused;
    }

    public void setFocused(int focused) {
        this.focused = focused;
    }

    public String getRank() {
        return rank == null ? "" : rank;
    }

    public void setRank(String rank) {
        this.rank = rank == null ? "" : rank;
    }

    public String getFishery_name() {
        return fishery_name == null ? "" : fishery_name;
    }

    public void setFishery_name(String fishery_name) {
        this.fishery_name = fishery_name == null ? "" : fishery_name;
    }
}
