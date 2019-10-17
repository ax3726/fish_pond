package com.gofishfarm.htkj.event;

/**
 * @author：MrHu
 * @Date ：2019-01-18
 * @Describtion ：
 */
public class WxFistSendBean {

    private String onlook_id;
    private String fishing_id;
    private String access_token;
    private String ws_fp_id;

    public String getWs_fp_id() {
        return ws_fp_id == null ? "" : ws_fp_id;
    }

    public void setWs_fp_id(String ws_fp_id) {
        this.ws_fp_id = ws_fp_id;
    }

    public String getOnlook_id() {
        return onlook_id == null ? "" : onlook_id;
    }

    public void setOnlook_id(String onlook_id) {
        this.onlook_id = onlook_id;
    }

    public String getFishing_id() {
        return fishing_id == null ? "" : fishing_id;
    }

    public void setFishing_id(String fishing_id) {
        this.fishing_id = fishing_id;
    }

    public String getAccess_token() {
        return access_token == null ? "" : access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
