package com.gofishfarm.htkj.bean;

import java.util.List;

/**
 * @author：MrHu
 * @Date ：2019-01-12
 * @Describtion ：
 */
public class FansListBean {


    /**
     * fans : [{"fisher_id":98045418,"status":2,"nick_name":"runmanz","avatar":"https://runmanz-1251536883.cos.ap-shanghai.myqcloud.com/default/itachi.jpg","mutual_focus":false}]
     * page : 1
     * total_page : 1
     */

    private String page;
    private int total_page;
    private List<FansBean> fans;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public List<FansBean> getFans() {
        return fans;
    }

    public void setFans(List<FansBean> fans) {
        this.fans = fans;
    }

    public static class FansBean {
        /**
         * fisher_id : 98045418
         * status : 2
         * nick_name : runmanz
         * avatar : https://runmanz-1251536883.cos.ap-shanghai.myqcloud.com/default/itachi.jpg
         * mutual_focus : false
         */

        private String fisher_id;
        private String status;
        private String nick_name;
        private String avatar;
        private int mutual_focus;

        public String getFisher_id() {
            return fisher_id == null ? "" : fisher_id;
        }

        public void setFisher_id(String fisher_id) {
            this.fisher_id = fisher_id;
        }

        public String getStatus() {
            return status == null ? "" : status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public int getMutual_focus() {
            return mutual_focus;
        }

        public void setMutual_focus(int mutual_focus) {
            this.mutual_focus = mutual_focus;
        }
    }
}
