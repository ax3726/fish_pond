package com.gofishfarm.htkj.event;

/**
 * @author：MrHu
 * @Date ：2019-01-19
 * @Describtion ：
 */
public class WsCloseEvent {
   private boolean isClose;

    public boolean isClose() {
        return isClose;
    }

    public void setClose(boolean close) {
        isClose = close;
    }
}
