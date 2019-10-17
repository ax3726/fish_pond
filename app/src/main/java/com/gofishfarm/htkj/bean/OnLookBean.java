package com.gofishfarm.htkj.bean;

import java.io.Serializable;

/**
 * @author：MrHu
 * @Date ：2019-01-07
 * @Describtion ：
 */
public class OnLookBean implements Serializable {


    /**
     * fp_id : 1
     * lives : rtmp://play.gofishfarm.com/live/5SXT00005a
     * fishing_fisher : {"nick_name":"788889600","avatar":"http://sfyb-1258095529.cos.ap-chengdu.myqcloud.com/avatar/1547614250661head.png","fisher_id":13164844,"focus_status":1}
     */

    private String fp_id;
    private String lives;
    private FishingFisherBean fishing_fisher;

    public String getFp_id() {
        return fp_id == null ? "" : fp_id;
    }

    public void setFp_id(String fp_id) {
        this.fp_id = fp_id;
    }

    public String getLives() {
        return lives;
    }

    public void setLives(String lives) {
        this.lives = lives;
    }

    public FishingFisherBean getFishing_fisher() {
        return fishing_fisher;
    }

    public void setFishing_fisher(FishingFisherBean fishing_fisher) {
        this.fishing_fisher = fishing_fisher;
    }

    public static class FishingFisherBean implements Serializable{
        /**
         * nick_name : 788889600
         * avatar : http://sfyb-1258095529.cos.ap-chengdu.myqcloud.com/avatar/1547614250661head.png
         * fisher_id : 13164844
         * focus_status : 1
         */

        private String nick_name;
        private String avatar;
        private String fisher_id;
        private String focus_status;

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

        public String getFisher_id() {
            return fisher_id == null ? "" : fisher_id;
        }

        public void setFisher_id(String fisher_id) {
            this.fisher_id = fisher_id;
        }

        public String getFocus_status() {
            return focus_status == null ? "" : focus_status;
        }

        public void setFocus_status(String focus_status) {
            this.focus_status = focus_status;
        }
    }
}
