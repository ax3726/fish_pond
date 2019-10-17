package com.gofishfarm.htkj.utils.notificationutils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.bean.FishDevciceBean;
import com.gofishfarm.htkj.ui.main.fishingpage.UserFishingActivity;
import com.gofishfarm.htkj.utils.SharedUtils;

/**
 * @author：MrHu
 * @Date ：2019-01-17
 * @Describtion ：
 */
public class NotificationClickReceiver extends BroadcastReceiver {
    FishDevciceBean fishDevciceBean;

    @Override
    public void onReceive(Context context, Intent intent) {
        //todo 跳转之前要处理的逻辑
        Log.i("TAG", "userClick:我被点击啦！！！ ");
//            Intent newIntent = new Intent(context, Main2Activity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(newIntent);
//        ServiceUtils.dealWithCustomAction(context, UserFishingActivity.class, "我发的消息");

        fishDevciceBean = SharedUtils.getInstance().getObject(ConfigApi.FISHDEVCICEBEAN, FishDevciceBean.class);
        if (null == fishDevciceBean) {
            return;
        }
        Intent intent1 = new Intent(context, UserFishingActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        if (!ServiceUtils.isForeground(context, UserFishingActivity.class.getSimpleName())) {
            intent1.putExtra(ConfigApi.FISHDEVCICEBEAN, fishDevciceBean);
        }
        context.startActivity(intent1);
    }
}
