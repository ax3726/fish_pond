package com.gofishfarm.htkj.event;

/**
 * @author：MrHu
 * @Date ：2019-01-19
 * @Describtion ：
 */
public class PlayMessageEvent {
    private String ws_fisher_id;//关注的id用来显示个人信息及关注信息
    private String ws_likes; //点赞数
    private String ws_onlook;//围观数量
    private String ws_focus; //
    private String ws_fish_num;//钓鱼条数
    private String integration;//钓到鱼可获渔币
    private Boolean onheartbeat;//true（长连接成功）
    private int now_mode;//true（长连接成功）// 钓鱼大状态 1 状态1， 2 状态2， ... n 状态n 只有要跳转下个状态时才有该状态值
    private String now_cmd; // 当前指令位置 03复位，04上饵，05抖饵，06垂钓，07刺鱼，08提鱼，09抄鱼，0a摘鱼，02收鱼成功
    private String now_status;// 指令状态 00 下发 01 指令进行中 02 已完成
    private String tiro_prompt;// "按“上饵”，立即开钓"

    private MessageBean messages;
    private BroadcastBean broadcast;

    public BroadcastBean getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(BroadcastBean broadcast) {
        this.broadcast = broadcast;
    }

    public String getIntegration() {
        return integration == null ? "" : integration;
    }

    public void setIntegration(String integration) {
        this.integration = integration == null ? "" : integration;
    }

    public int getNow_mode() {
        return now_mode;
    }

    public void setNow_mode(int now_mode) {
        this.now_mode = now_mode;
    }

    public String getNow_cmd() {
        return now_cmd == null ? "" : now_cmd;
    }

    public void setNow_cmd(String now_cmd) {
        this.now_cmd = now_cmd == null ? "" : now_cmd;
    }

    public String getNow_status() {
        return now_status == null ? "" : now_status;
    }

    public void setNow_status(String now_status) {
        this.now_status = now_status == null ? "" : now_status;
    }

    public String getTiro_prompt() {
        return tiro_prompt == null ? "" : tiro_prompt;
    }

    public void setTiro_prompt(String tiro_prompt) {
        this.tiro_prompt = tiro_prompt == null ? "" : tiro_prompt;
    }

    public MessageBean getMessages() {
        return messages;
    }

    public void setMessages(MessageBean messages) {
        this.messages = messages;
    }

    public String getWs_fisher_id() {
        return ws_fisher_id == null ? "" : ws_fisher_id;
    }

    public void setWs_fisher_id(String ws_fisher_id) {
        this.ws_fisher_id = ws_fisher_id;
    }

    public String getWs_likes() {
        return ws_likes == null ? "" : ws_likes;
    }

    public void setWs_likes(String ws_likes) {
        this.ws_likes = ws_likes;
    }

    public String getWs_onlook() {
        return ws_onlook == null ? "" : ws_onlook;
    }

    public void setWs_onlook(String ws_onlook) {
        this.ws_onlook = ws_onlook;
    }

    public String getWs_focus() {
        return ws_focus == null ? "" : ws_focus;
    }

    public void setWs_focus(String ws_focus) {
        this.ws_focus = ws_focus;
    }

    public String getWs_fish_num() {
        return ws_fish_num == null ? "" : ws_fish_num;
    }

    public void setWs_fish_num(String ws_fish_num) {
        this.ws_fish_num = ws_fish_num;
    }


    public Boolean getOnheartbeat() {
        return onheartbeat;
    }

    public void setOnheartbeat(Boolean onheartbeat) {
        this.onheartbeat = onheartbeat;
    }

    @Override
    public String toString() {
        return "PlayMessageEvent{" +
                "ws_fisher_id='" + ws_fisher_id + '\'' +
                ", ws_likes='" + ws_likes + '\'' +
                ", ws_onlook='" + ws_onlook + '\'' +
                ", ws_focus='" + ws_focus + '\'' +
                ", ws_fish_num='" + ws_fish_num + '\'' +
                ", integration='" + integration + '\'' +
                ", onheartbeat=" + onheartbeat +
                ", now_mode=" + now_mode +
                ", now_cmd='" + now_cmd + '\'' +
                ", now_status='" + now_status + '\'' +
                ", tiro_prompt='" + tiro_prompt + '\'' +
                ", messages=" + messages +
                ", broadcast=" + broadcast +
                '}';
    }

    public static class MessageBean {
        private String name;  // 发言者名称，
        private String msg;  // 消息内容
        private String msg_type;  // 消息类型
        private String avatar;  // 头像

        public String getAvatar() {
            return avatar == null ? "" : avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar == null ? "" : avatar;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name == null ? "" : name;
        }

        public String getMsg() {
            return msg == null ? "" : msg;
        }

        public void setMsg(String msg) {
            this.msg = msg == null ? "" : msg;
        }

        public String getMsg_type() {
            return msg_type == null ? "" : msg_type;
        }

        public void setMsg_type(String msg_type) {
            this.msg_type = msg_type == null ? "" : msg_type;
        }

        @Override
        public String toString() {
            return "MessageBean{" +
                    "name='" + name + '\'' +
                    ", msg='" + msg + '\'' +
                    ", msg_type='" + msg_type + '\'' +
                    '}';
        }
    }


    public static class BroadcastBean {
        private String content;
        private String btn_name;
        private String btn_link;

        public String getContent() {
            return content == null ? "" : content;
        }

        public void setContent(String content) {
            this.content = content == null ? "" : content;
        }

        public String getBtn_name() {
            return btn_name == null ? "" : btn_name;
        }

        public void setBtn_name(String btn_name) {
            this.btn_name = btn_name == null ? "" : btn_name;
        }

        public String getBtn_link() {
            return btn_link == null ? "" : btn_link;
        }

        public void setBtn_link(String btn_link) {
            this.btn_link = btn_link == null ? "" : btn_link;
        }

        @Override
        public String toString() {
            return "BroadcastBean{" +
                    "content='" + content + '\'' +
                    ", btn_name='" + btn_name + '\'' +
                    ", btn_link='" + btn_link + '\'' +
                    '}';
        }
    }
}
