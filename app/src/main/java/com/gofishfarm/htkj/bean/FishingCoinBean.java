package com.gofishfarm.htkj.bean;

import java.util.List;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/12
 */
public class FishingCoinBean {


    /**
     * available_integration : 0
     * total_integration : 0
     * total_fishing : 0
     * settings : [{"is_id":1,"integration":1000,"time":1,"description":null}]
     */

    private String available_integration;
    private String total_integration;
    private String total_fishing;
    private List<SettingsBean> settings;

    public String getAvailable_integration() {
        return available_integration == null ? "" : available_integration;
    }

    public void setAvailable_integration(String available_integration) {
        this.available_integration = available_integration;
    }

    public String getTotal_integration() {
        return total_integration == null ? "" : total_integration;
    }

    public void setTotal_integration(String total_integration) {
        this.total_integration = total_integration;
    }

    public String getTotal_fishing() {
        return total_fishing == null ? "" : total_fishing;
    }

    public void setTotal_fishing(String total_fishing) {
        this.total_fishing = total_fishing;
    }

    public List<SettingsBean> getSettings() {
        return settings;
    }

    public void setSettings(List<SettingsBean> settings) {
        this.settings = settings;
    }

    public static class SettingsBean {
        /**
         * is_id : 1
         * integration : 1000
         * time : 1
         * description : null
         */

        private String is_id;
        private String integration;
        private String time;
        private String description;

        public String getIs_id() {
            return is_id == null ? "" : is_id;
        }

        public void setIs_id(String is_id) {
            this.is_id = is_id;
        }

        public String getIntegration() {
            return integration == null ? "" : integration;
        }

        public void setIntegration(String integration) {
            this.integration = integration;
        }

        public String getTime() {
            return time == null ? "" : time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDescription() {
            return description == null ? "" : description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
