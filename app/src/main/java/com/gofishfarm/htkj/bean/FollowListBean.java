package com.gofishfarm.htkj.bean;

import java.util.List;

/**
 * @author：MrHu
 * @Date ：2019-01-12
 * @Describtion ：
 */
public class FollowListBean {

    /**
     * focus : [{"fisher_id":83373784,"status":2,"avatar":"https://runmanz-1251536883.cos.ap-shanghai.myqcloud.com/default/itachi.jpg","nick_name":"sf_JZAXJKAE","mutual_focus":false},{"fisher_id":59041581,"status":2,"avatar":"https://runmanz-1251536883.cos.ap-shanghai.myqcloud.com/default/itachi.jpg","nick_name":"sf_BUJXJZSP","mutual_focus":false}]
     * page : 1
     * total_page : 1
     * recommend_focus : [{"fisher_id":67224273,"status":2,"avatar":"https://runmanz-1251536883.cos.ap-shanghai.myqcloud.com/default/itachi.jpg","nick_name":"runmanz","mutual_focus":false}]
     */

    private int page;
    private int total_page;
    private List<FocusBean> focus;
    private List<RecommendFocusBean> recommend_focus;


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public List<FocusBean> getFocus() {
        return focus;
    }

    public void setFocus(List<FocusBean> focus) {
        this.focus = focus;
    }

    public List<RecommendFocusBean> getRecommend_focus() {
        return recommend_focus;
    }

    public void setRecommend_focus(List<RecommendFocusBean> recommend_focus) {
        this.recommend_focus = recommend_focus;
    }

    public static class FocusBean {
        /**
         * fisher_id : 83373784
         * status : 2
         * avatar : https://runmanz-1251536883.cos.ap-shanghai.myqcloud.com/default/itachi.jpg
         * nick_name : sf_JZAXJKAE
         * mutual_focus : false
         */

        private String fisher_id;
        private String status;
        private String avatar;
        private String nick_name;
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

        public String getAvatar() {
            return avatar == null ? "" : avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNick_name() {
            return nick_name == null ? "" : nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public int getMutual_focus() {
            return mutual_focus;
        }

        public void setMutual_focus(int mutual_focus) {
            this.mutual_focus = mutual_focus;
        }
    }

    public static class RecommendFocusBean {
        /**
         * fisher_id : 67224273
         * status : 2
         * avatar : https://runmanz-1251536883.cos.ap-shanghai.myqcloud.com/default/itachi.jpg
         * nick_name : runmanz
         * mutual_focus : false
         */

        private String fisher_id;
        private String status;
        private String avatar;
        private String nick_name;
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

        public String getAvatar() {
            return avatar == null ? "" : avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNick_name() {
            return nick_name == null ? "" : nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public int getMutual_focus() {
            return mutual_focus;
        }

        public void setMutual_focus(int mutual_focus) {
            this.mutual_focus = mutual_focus;
        }
    }
}
