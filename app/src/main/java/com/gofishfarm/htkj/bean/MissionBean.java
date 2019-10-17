package com.gofishfarm.htkj.bean;

import java.util.List;

/**
 * @author：MrHu
 * @Date ：2019-01-15
 * @Describtion ：
 */
public class MissionBean {


    private List<InfoBean> info;

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * m_id : 10001
         * name : 首次充值
         * detail : 首次充值
         * integration : 50
         * thumbnail : https://sfyb-1258095529.cos.ap-chengdu.myqcloud.com/default/cz.png
         * accomplished : false
         * node : 4
         * spacing : 1
         */

        private String m_id;
        private String name;
        private String detail;
        private String integration;
        private String thumbnail;
        private boolean accomplished;
        private String node;
        private String spacing;

        public String getM_id() {
            return m_id == null ? "" : m_id;
        }

        public void setM_id(String m_id) {
            this.m_id = m_id;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDetail() {
            return detail == null ? "" : detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getIntegration() {
            return integration == null ? "" : integration;
        }

        public void setIntegration(String integration) {
            this.integration = integration;
        }

        public String getThumbnail() {
            return thumbnail == null ? "" : thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public boolean isAccomplished() {
            return accomplished;
        }

        public void setAccomplished(boolean accomplished) {
            this.accomplished = accomplished;
        }

        public String getNode() {
            return node == null ? "" : node;
        }

        public void setNode(String node) {
            this.node = node;
        }

        public String getSpacing() {
            return spacing == null ? "" : spacing;
        }

        public void setSpacing(String spacing) {
            this.spacing = spacing;
        }
    }
}
