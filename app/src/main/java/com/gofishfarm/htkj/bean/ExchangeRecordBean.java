package com.gofishfarm.htkj.bean;

import java.util.List;

/**
 * @author：MrHu
 * @Date ：2019-01-14
 * @Describtion ：
 */
public class ExchangeRecordBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * title : 兑换了1h*1
         * integration : -400渔币
         * created_at : 2019-01-17 18:06:11
         */

        private String title;
        private String integration;
        private String created_at;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIntegration() {
            return integration;
        }

        public void setIntegration(String integration) {
            this.integration = integration;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }
}
