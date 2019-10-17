package com.gofishfarm.htkj.bean;

import java.util.List;

/**
 * @author：MrHu
 * @Date ：2019-01-10
 * @Describtion ：
 */
public class RankRightBean {

    private List<InfoBean> info;

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {

        private String fisher_id;
        private String nick_name;
        private String avatar;
        private String ave_fishing;
        private String total_fishing;
        private String status;

        public String getFisher_id() {
            return fisher_id == null ? "" : fisher_id;
        }

        public void setFisher_id(String fisher_id) {
            this.fisher_id = fisher_id;
        }

        public String getNick_name() {
            return nick_name == null ? "" : nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getAvatar() {
            return avatar == null ? "" : avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getAve_fishing() {
            return ave_fishing == null ? "" : ave_fishing;
        }

        public void setAve_fishing(String ave_fishing) {
            this.ave_fishing = ave_fishing;
        }

        public String getTotal_fishing() {
            return total_fishing == null ? "" : total_fishing;
        }

        public void setTotal_fishing(String total_fishing) {
            this.total_fishing = total_fishing;
        }

        public String getStatus() {
            return status == null ? "" : status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
