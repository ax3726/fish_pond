package com.gofishfarm.htkj.utils.notificationutils;

import android.content.BroadcastReceiver;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * @Description：广播管理类：注册广播、注销广播、发送广播
 */
public class BroadCastManager {

    private static BroadCastManager broadCastManager = new BroadCastManager();

    public static BroadCastManager getInstance() {
        if(null==broadCastManager){
            broadCastManager = new BroadCastManager();
        }
        return broadCastManager;
    }

    //注册广播接收者
    public void registerReceiver(ContextWrapper activity,
                                 BroadcastReceiver receiver, IntentFilter filter) {
        activity.registerReceiver(receiver, filter);
    }

    //注销广播接收者
    public void unregisterReceiver(ContextWrapper activity,
                                   BroadcastReceiver receiver) {
        activity.unregisterReceiver(receiver);
    }

    //发送广播
    public void sendBroadCast(ContextWrapper activity, Intent intent) {
        activity.sendBroadcast(intent);
    }
}
