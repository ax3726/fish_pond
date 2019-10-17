package com.gofishfarm.htkj.bean;

import java.util.List;

/**
 * @author：MrHu
 * @Date ：2019-01-10
 * @Describtion ：
 */
public class RankLfetBean {

    private List<InfoBean> info;

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * fisher_id : 67224273
         * nick_name : 水水
         * avatar : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJz0Y0iac4RpPicSgnJR8xLQ3J3EhohibjuUMy7mhrRicQ71Ysk4icRaAwvciaiavDsnqciaicrbI3MPgOZKKw/132
         * total_fishing : 0
         * status : 2
         */

        private String fisher_id;
        private String nick_name;
        private String avatar;
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
