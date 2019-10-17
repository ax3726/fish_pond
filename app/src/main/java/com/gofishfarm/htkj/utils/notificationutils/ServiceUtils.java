package com.gofishfarm.htkj.utils.notificationutils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.util.List;

/**
 * @author：MrHu
 * @Date ：2019-01-17
 * @Describtion ：
 */
public class ServiceUtils {

//    若APP被杀死，要将通知到携带的Message传递到APP启动后的首个Activity中，
//    然后一步步传递到MainActivity中，再根据Message中的数据不同，处理业务逻辑并跳转到对应的Activity中即可。
    public static void dealWithCustomAction(Context context,Class<?> Activity ,String uMessage) {

        if (ServiceUtils.isProessRunning(context, context.getPackageName())) {
            context.startActivity(new Intent(context, Activity));
        }else{
            //将通知携带的数据一步步传递到MainActivity
            Intent intentNotification = new Intent(context, Activity);
            intentNotification.putExtra("uMessage", uMessage);
            context.startActivity(intentNotification);
        }
    }

    /**
     * 判断服务是否运行
     *
     * @param context
     * @param serviceName
     * @return
     */
    public static boolean isServiceRunning(Context context, String serviceName) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> lists = am.getRunningServices(30);

        for (ActivityManager.RunningServiceInfo info : lists) {//判断服务
            if (info.service.getClassName().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断进程是否存活
     *
     * @param context
     * @param proessName
     * @return
     */
    public static boolean isProessRunning(Context context, String proessName) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> lists = am.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo info : lists) {
            if (info.processName.equals(proessName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断某个Activity 界面是否在前台
     * @param context
     * @param className 某个界面名称
     * @return
     */
    public static boolean  isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }

        return false;

    }
}
