package com.gofishfarm.htkj.bean;

/**
 * Created by Android Studio.
 * User: MrHu
 * Date: 2019-03-27
 * Time: 下午 11:15
 *
 * @Description:
 */
public class ChatMesgBean {

    private String name;  // talk
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
        return "ChatMesgBean{" +
                "name='" + name + '\'' +
                ", msg='" + msg + '\'' +
                ", msg_type='" + msg_type + '\'' +
                '}';
    }
}
