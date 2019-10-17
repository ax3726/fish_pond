package com.gofishfarm.htkj.bean;

/**
 * Created by Android Studio.
 * User: MrHu
 * Date: 2019-03-22
 * Time: 下午 11:58
 *
 * @Description:
 */
public class FishGuideFinishBean {
    private String reward;

    public String getReward() {
        return reward == null ? "" : reward;
    }

    public void setReward(String reward) {
        this.reward = reward == null ? "" : reward;
    }
}
