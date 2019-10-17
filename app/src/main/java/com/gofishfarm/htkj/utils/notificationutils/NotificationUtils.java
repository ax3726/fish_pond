package com.gofishfarm.htkj.utils.notificationutils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.gofishfarm.htkj.R;

/**
 * @author：MrHu
 * @Date ：2019-01-17
 * @Describtion ：
 */
public class NotificationUtils extends ContextWrapper {
    private NotificationManager manager;
    public static final String id = "channel_1";//渠道号channel_1
    public static final String name = "播放通知服务";//渠道名字channel_name_1
    public Notification notification;

    public NotificationUtils(Context context) {
        super(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }

    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getChannelNotification(String title, String content) {
        return new Notification.Builder(getApplicationContext(), id)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setAutoCancel(true);
    }


    public Notification.Builder getNotification_25(String title, String content) {
//        return new NotificationCompat.Builder(getApplicationContext())
//                .setContentTitle(title)
//                .setContentText(content).
//        setSmallIcon( R.mipmap.ic_launcher).setLargeIcon( BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
//                .setAutoCancel(true);

        return new Notification.Builder(this).setTicker("沙发渔霸").
                setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentText("钓手钓鱼中")
                .setContentTitle("沙发渔霸");

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void sendNotification(String title, String content) {
        if (Build.VERSION.SDK_INT >= 26) {
            createNotificationChannel();
            this.notification = getChannelNotification(title, content).build();
            getManager().notify(1, notification);
        } else {
            this.notification = getNotification_25(title, content).build();
            getManager().notify(1, notification);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Notification getNotification(String title, String content) {
        if (Build.VERSION.SDK_INT >= 26) {
            createNotificationChannel();
            this.notification = getChannelNotification(title, content).build();
            return notification;
        } else {
            this.notification = getNotification_25(title, content).build();
            return notification;
        }
    }

    public Notification getNotification() {

        return this.notification;
    }
}
