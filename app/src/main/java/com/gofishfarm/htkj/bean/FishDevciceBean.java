package com.gofishfarm.htkj.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author：MrHu
 * @Date ：2019-01-04
 * @Describtion ：
 */
public class FishDevciceBean implements Serializable {


    /**
     * fp_id : 1
     * dtu_id : 505437446
     * dtu_apikay : vAcBwxQ5D4HiISRtxDwoQXxE=xI=
     * lives : ["rtmp://dytl.game.caizs.com/live/11111?bizid=37147&txSecret=4bd09fe542ddecc1d0ae42694b6ccb5f&txTime=5C320520"]
     * commands : {"on1":"/gUAAP8AmDU=","off1":"/gUAAAAA2cU=","on2":"/gUAAf8AyfU=","off2":"/gUAAQAAiAU=","on3":"/gUAAv8AOfU=","off3":"/gUAAgAAeAU=","on4":"/gUAA/8AaDU=","off4":"/gUAAwAAKcU=","on5":"/gUABP8A2fQ=","off5":"/gUABAAAmAQ=","alloff":"/g8AAAAFAQAgUg=="}
     */

    private String info;
    private String fp_id;
    private int type;
    private int on_time;
    private int off_time;
    private String dtu_id;
    private String dtu_apikay;
    private String fish_integration;
    private UserInfoBean user_info;
    private CommandsBean commands;
    private List<String> lives;

    public UserInfoBean getUser_info() {
        return user_info;
    }

    public void setUser_info(UserInfoBean user_info) {
        this.user_info = user_info;
    }

    public String getFish_integration() {
        return fish_integration == null ? "" : fish_integration;
    }

    public void setFish_integration(String fish_integration) {
        this.fish_integration = fish_integration;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getInfo() {
        return info == null ? "" : info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getOn_time() {
        return on_time;
    }

    public void setOn_time(int on_time) {
        this.on_time = on_time;
    }

    public int getOff_time() {
        return off_time;
    }

    public void setOff_time(int off_time) {
        this.off_time = off_time;
    }

    public String getFp_id() {
        return fp_id == null ? "" : fp_id;
    }

    public void setFp_id(String fp_id) {
        this.fp_id = fp_id;
    }

    public String getDtu_id() {
        return dtu_id;
    }

    public void setDtu_id(String dtu_id) {
        this.dtu_id = dtu_id;
    }

    public String getDtu_apikay() {
        return dtu_apikay;
    }

    public void setDtu_apikay(String dtu_apikay) {
        this.dtu_apikay = dtu_apikay;
    }

    public CommandsBean getCommands() {
        return commands;
    }

    public void setCommands(CommandsBean commands) {
        this.commands = commands;
    }

    public List<String> getLives() {
        return lives;
    }

    public void setLives(List<String> lives) {
        this.lives = lives;
    }



    public static class UserInfoBean implements Serializable {
        private String avatar;
        private String nick_name;
        private String rank;
        private String fishery_name;

        public String getAvatar() {
            return avatar == null ? "" : avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar == null ? "" : avatar;
        }

        public String getNick_name() {
            return nick_name == null ? "" : nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name == null ? "" : nick_name;
        }

        public String getRank() {
            return rank == null ? "" : rank;
        }

        public void setRank(String rank) {
            this.rank = rank == null ? "" : rank;
        }

        public String getFishery_name() {
            return fishery_name == null ? "" : fishery_name;
        }

        public void setFishery_name(String fishery_name) {
            this.fishery_name = fishery_name == null ? "" : fishery_name;
        }

        @Override
        public String toString() {
            return "UserInfoBean{" +
                    "avatar='" + avatar + '\'' +
                    ", nick_name='" + nick_name + '\'' +
                    ", rank='" + rank + '\'' +
                    ", fishery_name='" + fishery_name + '\'' +
                    '}';
        }
    }
    public static class CommandsBean implements Serializable {
        /**
         * on1 : /gUAAP8AmDU=
         * off1 : /gUAAAAA2cU=
         * on2 : /gUAAf8AyfU=
         * off2 : /gUAAQAAiAU=
         * on3 : /gUAAv8AOfU=
         * off3 : /gUAAgAAeAU=
         * on4 : /gUAA/8AaDU=
         * off4 : /gUAAwAAKcU=
         * on5 : /gUABP8A2fQ=
         * off5 : /gUABAAAmAQ=
         * alloff : /g8AAAAFAQAgUg==
         */

        private String on1;
        private String off1;
        private String on2;
        private String off2;
        private String on3;
        private String off3;
        private String on4;
        private String off4;
        private String on5;
        private String off5;
        private String on6;
        private String off6;
        private String alloff;

        private String send0;
        private String send1;
        private String send3;
        private String send4;
        private String send5;
        private String send6;
        private String send7;
        private String send8;
        private String send9;
        private String send10;

        public String getSend0() {
            return send0 == null ? "" : send0;
        }

        public void setSend0(String send0) {
            this.send0 = send0 == null ? "" : send0;
        }

        public String getSend1() {
            return send1 == null ? "" : send1;
        }

        public void setSend1(String send1) {
            this.send1 = send1 == null ? "" : send1;
        }

        public String getSend3() {
            return send3 == null ? "" : send3;
        }

        public void setSend3(String send3) {
            this.send3 = send3 == null ? "" : send3;
        }

        public String getSend4() {
            return send4 == null ? "" : send4;
        }

        public void setSend4(String send4) {
            this.send4 = send4 == null ? "" : send4;
        }

        public String getSend5() {
            return send5 == null ? "" : send5;
        }

        public void setSend5(String send5) {
            this.send5 = send5 == null ? "" : send5;
        }

        public String getSend6() {
            return send6 == null ? "" : send6;
        }

        public void setSend6(String send6) {
            this.send6 = send6 == null ? "" : send6;
        }

        public String getSend7() {
            return send7 == null ? "" : send7;
        }

        public void setSend7(String send7) {
            this.send7 = send7 == null ? "" : send7;
        }

        public String getSend8() {
            return send8 == null ? "" : send8;
        }

        public void setSend8(String send8) {
            this.send8 = send8 == null ? "" : send8;
        }

        public String getSend9() {
            return send9 == null ? "" : send9;
        }

        public void setSend9(String send9) {
            this.send9 = send9 == null ? "" : send9;
        }

        public String getSend10() {
            return send10 == null ? "" : send10;
        }

        public void setSend10(String send10) {
            this.send10 = send10 == null ? "" : send10;
        }

        public String getOn6() {
            return on6 == null ? "" : on6;
        }

        public void setOn6(String on6) {
            this.on6 = on6;
        }

        public String getOff6() {
            return off6 == null ? "" : off6;
        }

        public void setOff6(String off6) {
            this.off6 = off6;
        }

        public String getOn1() {
            return on1;
        }

        public void setOn1(String on1) {
            this.on1 = on1;
        }

        public String getOff1() {
            return off1;
        }

        public void setOff1(String off1) {
            this.off1 = off1;
        }

        public String getOn2() {
            return on2;
        }

        public void setOn2(String on2) {
            this.on2 = on2;
        }

        public String getOff2() {
            return off2;
        }

        public void setOff2(String off2) {
            this.off2 = off2;
        }

        public String getOn3() {
            return on3;
        }

        public void setOn3(String on3) {
            this.on3 = on3;
        }

        public String getOff3() {
            return off3;
        }

        public void setOff3(String off3) {
            this.off3 = off3;
        }

        public String getOn4() {
            return on4;
        }

        public void setOn4(String on4) {
            this.on4 = on4;
        }

        public String getOff4() {
            return off4;
        }

        public void setOff4(String off4) {
            this.off4 = off4;
        }

        public String getOn5() {
            return on5;
        }

        public void setOn5(String on5) {
            this.on5 = on5;
        }

        public String getOff5() {
            return off5;
        }

        public void setOff5(String off5) {
            this.off5 = off5;
        }

        public String getAlloff() {
            return alloff;
        }

        public void setAlloff(String alloff) {
            this.alloff = alloff;
        }
    }
}
