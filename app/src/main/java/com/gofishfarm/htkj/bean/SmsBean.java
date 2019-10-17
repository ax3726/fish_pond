package com.gofishfarm.htkj.bean;

import java.io.Serializable;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/3
 */
public class SmsBean implements Serializable {

    private String info;
    /**
     * code : 200
     * message : success
     * data : {"info":"3809"}
     */
    public String getInfo() {
        return info == null ? "" : info;
    }

    public void setInfo(String info) {
        this.info = info;
    }




}
