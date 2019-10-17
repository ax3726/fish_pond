package com.gofishfarm.htkj.bean;

import java.util.List;

/**
 * @author：MrHu
 * @Date ：2019-01-04
 * @Describtion ：
 */
public class FishPondBean {

    private List<BannersBean> banners;
    private List<FisheriesBean> fisheries;

    public List<BannersBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannersBean> banners) {
        this.banners = banners;
    }

    public List<FisheriesBean> getFisheries() {
        return fisheries;
    }

    public void setFisheries(List<FisheriesBean> fisheries) {
        this.fisheries = fisheries;
    }

    public static class BannersBean {
        /**
         * name : 首页轮播图
         * link : http://www.baidu.com
         * thumbnail : https://sfyb-1258095529.cos.ap-chengdu.myqcloud.com/banner/banner.png
         */

        private String name;
        private String link;
        private String thumbnail;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }
    }

    public static class FisheriesBean {
        /**
         * fishery_id : 2
         * name : 钱塘渔场
         * thumbnail : https://sfyb-1258095529.cos.ap-chengdu.myqcloud.com/fishery/qtyc.png
         * current_capacity : 0
         * capacity : 2
         * onlook : 0
         * details : 钱塘渔场
         * surplus_capacity : 2
         */

        private String fishery_id;
        private String name;
        private String thumbnail;
        private int current_capacity;
        private int capacity;
        private int onlook;
        private String details;
        private int surplus_capacity;

        public String getFishery_id() {
            return fishery_id == null ? "" : fishery_id;
        }

        public void setFishery_id(String fishery_id) {
            this.fishery_id = fishery_id;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getThumbnail() {
            return thumbnail == null ? "" : thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public int getCurrent_capacity() {
            return current_capacity;
        }

        public void setCurrent_capacity(int current_capacity) {
            this.current_capacity = current_capacity;
        }

        public int getCapacity() {
            return capacity;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }

        public int getOnlook() {
            return onlook;
        }

        public void setOnlook(int onlook) {
            this.onlook = onlook;
        }

        public String getDetails() {
            return details == null ? "" : details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public int getSurplus_capacity() {
            return surplus_capacity;
        }

        public void setSurplus_capacity(int surplus_capacity) {
            this.surplus_capacity = surplus_capacity;
        }
    }
}
