package com.gofishfarm.htkj.event;

/**
 * @author：MrHu
 * @Date ：2019-01-19
 * @Describtion ：
 */
public class OrderEvent {
    private String op_type;
    private String ws_fp_id;
    private String msg;// 消息内容
    private String msg_type;// 消息类型
    private int cmd_btn;// 发送指令时操作

    public int getCmd_btn() {
        return cmd_btn;
    }

    public void setCmd_btn(int cmd_btn) {
        this.cmd_btn = cmd_btn;
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

    public String getOp_type() {
        return op_type == null ? "" : op_type;
    }

    public void setOp_type(String op_type) {
        this.op_type = op_type;
    }

    public String getWs_fp_id() {
        return ws_fp_id == null ? "" : ws_fp_id;
    }

    public void setWs_fp_id(String ws_fp_id) {
        this.ws_fp_id = ws_fp_id;
    }
}
