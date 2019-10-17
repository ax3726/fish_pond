package com.gofishfarm.htkj.event;

/**
 * @author：MrHu
 * @Date ：2019-01-19
 * @Describtion ：
 */
public class SwitchLiveEvent {
    private String feverJson;

    public String getFeverJson() {
        return feverJson == null ? "" : feverJson;
    }

    public void setFeverJson(String feverJson) {
        this.feverJson = feverJson;
    }
}
