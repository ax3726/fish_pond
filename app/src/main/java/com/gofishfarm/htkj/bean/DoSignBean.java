package com.gofishfarm.htkj.bean;

/**
 * @author：MrHu
 * @Date ：2019-01-16
 * @Describtion ：
 */
public class DoSignBean {

    /**
     * continuous_times : 1
     */

    private String continuous_times;

    private String clocked;

    public String getClocked() {
        return clocked == null ? "" : clocked;
    }

    public void setClocked(String clocked) {
        this.clocked = clocked;
    }

    public String getContinuous_times() {
        return continuous_times == null ? "" : continuous_times;
    }

    public void setContinuous_times(String continuous_times) {
        this.continuous_times = continuous_times;
    }
}
