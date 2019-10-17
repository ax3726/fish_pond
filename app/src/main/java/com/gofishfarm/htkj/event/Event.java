package com.gofishfarm.htkj.event;


/**
 * @date 创建时间：2017/6/4 15:24
 * 描述:事件
 */
public class Event {

    public static class CountdownEvent {
        public int countdown;//倒计时数
    }

    public static class ShowFragment {
        public int pos;
        public int posVisible;
    }

    public static class refreshMyCoin {
        public Boolean isRefresh;
    }

    public static class showWsMessage {

        public String ws_fisher_id;
        public String ws_likes;
        public String ws_onlook;
        public String ws_focus;
        public String ws_fish_num;
        public String timestr;

        @Override
        public String toString() {
            return "showWsMessage{" +
                    "ws_fisher_id='" + ws_fisher_id + '\'' +
                    ", ws_likes='" + ws_likes + '\'' +
                    ", ws_onlook='" + ws_onlook + '\'' +
                    ", ws_focus='" + ws_focus + '\'' +
                    ", ws_fish_num='" + ws_fish_num + '\'' +
                    ", timestr='" + timestr + '\'' +
                    '}';
        }
    }
}
