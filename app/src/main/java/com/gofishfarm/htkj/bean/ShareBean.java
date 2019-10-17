package com.gofishfarm.htkj.bean;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/11
 */
public class ShareBean {


    /**
     * invite_total : 1000
     * invite_url : http://www.gofishfarm.com/share/refer=67224273
     * invite_integration : 1000
     */

    private String invite_total;
    private String invite_url;
    private String invite_integration;

    public String getInvite_total() {
        return invite_total == null ? "" : invite_total;
    }

    public void setInvite_total(String invite_total) {
        this.invite_total = invite_total;
    }

    public String getInvite_url() {
        return invite_url == null ? "" : invite_url;
    }

    public void setInvite_url(String invite_url) {
        this.invite_url = invite_url;
    }

    public String getInvite_integration() {
        return invite_integration == null ? "" : invite_integration;
    }

    public void setInvite_integration(String invite_integration) {
        this.invite_integration = invite_integration;
    }
}
