package com.gofishfarm.htkj.bean;

import java.util.List;

/**
 * @author：MrHu
 * @Date ：2019-01-11
 * @Describtion ：
 */
public class OrderRecordBean {

    private List<InfoBean> info;

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * order_id : 2019010218021701878365067224273
         * buy_time : 5h
         * buy_at : 您还未付款
         * status : 待支付
         * price : 20.00元
         */

        private String order_id;
        private String buy_time;
        private String buy_at;
        private String status;
        private String price;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getBuy_time() {
            return buy_time;
        }

        public void setBuy_time(String buy_time) {
            this.buy_time = buy_time;
        }

        public String getBuy_at() {
            return buy_at;
        }

        public void setBuy_at(String buy_at) {
            this.buy_at = buy_at;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
