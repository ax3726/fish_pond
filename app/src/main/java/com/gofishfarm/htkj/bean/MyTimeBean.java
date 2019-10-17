package com.gofishfarm.htkj.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author：MrHu
 * @Date ：2019-01-08
 * @Describtion ：
 */
public class MyTimeBean implements Serializable {

    /**
     * time : 0
     * packages : [{"package_id":"20190101","name":"3小时套餐","details":"3h/￥15","price":15,"time":"3h","discount":100},{"package_id":"20190102","name":"5小时套餐","details":"5h/￥20","price":20,"time":"5h","discount":100}]
     */

    private String time;
    private String discount;

    public String getDiscount() {
        return discount == null ? "" : discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    private List<PackagesBean> packages;

    public String getTime() {
        return time == null ? "" : time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<PackagesBean> getPackages() {
        if (packages == null) {
            return new ArrayList<>();
        }
        return packages;
    }

    public void setPackages(List<PackagesBean> packages) {
        this.packages = packages;
    }

    public static class PackagesBean implements Serializable {
        /**
         * package_id : 20190101
         * name : 3小时套餐
         * details : 3h/￥15
         * price : 15
         * time : 3h
         * discount : 100
         */

        private String package_id;
        private String name;
        private String details;
        private String price;
        private String time;
        private String discount;
        private String thrift_price;

        public String getThrift_price() {
            return thrift_price == null ? "" : thrift_price;
        }

        public void setThrift_price(String thrift_price) {
            this.thrift_price = thrift_price;
        }

        public String getPackage_id() {
            return package_id == null ? "" : package_id;
        }

        public void setPackage_id(String package_id) {
            this.package_id = package_id;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDetails() {
            return details == null ? "" : details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getPrice() {
            return price == null ? "" : price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTime() {
            return time == null ? "" : time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDiscount() {
            return discount == null ? "" : discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }
    }
    
}
