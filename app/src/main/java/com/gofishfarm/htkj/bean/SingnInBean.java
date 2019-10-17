package com.gofishfarm.htkj.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/13
 */
public class SingnInBean {


    /**
     * clock_setting : [1,1,1,2,2,2,3]
     * hasClocked : false
     * continuous_times : 0
     */

    private boolean hasClocked;
    private String continuous_times;
    private List<String> clock_setting;

    public boolean isHasClocked() {
        return hasClocked;
    }

    public void setHasClocked(boolean hasClocked) {
        this.hasClocked = hasClocked;
    }

    public String getContinuous_times() {
        return continuous_times == null ? "" : continuous_times;
    }

    public void setContinuous_times(String continuous_times) {
        this.continuous_times = continuous_times;
    }

    public List<String> getClock_setting() {
        if (clock_setting == null) {
            return new ArrayList<>();
        }
        return clock_setting;
    }

    public void setClock_setting(List<String> clock_setting) {
        this.clock_setting = clock_setting;
    }
}
