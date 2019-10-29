package com.gofishfarm.htkj;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.gofishfarm.htkj.module.AppComponent;
import com.gofishfarm.htkj.module.AppModule;
import com.gofishfarm.htkj.module.DaggerAppComponent;
import com.gofishfarm.htkj.module.httpModule;
import com.gofishfarm.htkj.utils.AppUtils;
import com.gofishfarm.htkj.utils.SharedUtils;
import com.gofishfarm.htkj.utils.Utils;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.Bugly;
import com.tencent.rtmp.TXLiveBase;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

/**
 * MrLiu253@163.com
 *
 * @time 2018/12/20
 */
public class App extends Application {
    private AppComponent mAppComponent;
    private static App mContext;
    private int count = 0;

    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(android.R.color.white, R.color.color_fe);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        if (System.currentTimeMillis() >= 1572938589000L) {//大于当前时间退出APP
            android.os.Process.killProcess(android.os.Process.myPid());    //获取PID
            System.exit(0);   //常规java、c#的标准退出法，返回值为0代表正常退出
        }
        initPrefs();
        initComponent();
        Utils.init(this);
        // 在Application中初始化
        ToastUtils.init(this);
        AppUtils.init(this);
        Bugly.init(this, "486893c124", false);

//        UMConfigure.setLogEnabled(true);
//        友盟分享
        UMConfigure.init(this, "5a12384aa40fa3551f0001d1", "UMENG_CHANNEL", UMConfigure.DEVICE_TYPE_PHONE, "");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0
        PlatformConfig.setWeixin("wx147a98b0ec2caa53", "9027f5189e1f15d8ccc1746be05b96f0");
        //豆瓣RENREN平台目前只能在服务器端配置
        PlatformConfig.setSinaWeibo("483013937", "8638f1f4968fa760f82ae83215f2fbc1", "http://www.gofishfarm.com");
        PlatformConfig.setQQZone("1108039519", "15VxdBt2p4zAqxJ9");
        /**
         *
         *上面已经设置过
         *
         *注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，也需要在App代码中调用初始化接口（如需要使用AndroidManifest.xml中配置好的appkey和channel值，UMConfigure.init调用中appkey和channel参数请置为null）。
         */
        // 友盟统计（分享公用）
        // UMConfigure.init(this, String appkey, String channel, int deviceType, String pushSecret);
        MobclickAgent.setScenarioType(mContext, MobclickAgent.EScenarioType.E_UM_NORMAL);

//        TXLiveBase.getInstance().setLicence(this,
//                "http://license.vod2.myqcloud.com/license/v1/91eb23037f9d49e7f5d06ce7bda2bfc8/TXLiveSDK.licence"
//                , "58e3f66b348b97bc4815f86515d89823");
        ReistLifeCallBack();

    }

    private void ReistLifeCallBack() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityStopped(Activity activity) {
                Log.v("LifecycleCallbacks", activity + "onActivityStopped");
                count--;
                if (count == 0) {
                    Log.v("LifecycleCallbacks", ">>>>>>>>>>>>>>>>>>>切到后台  lifecycle");
//                    WindowUtils.hidePopupWindow();
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.v("LifecycleCallbacks", activity + "onActivityStarted");
                if (count == 0) {
                    Log.v("LifecycleCallbacks", ">>>>>>>>>>>>>>>>>>>切到前台  lifecycle");
//                    WindowUtils.showPopupWindow(App.getInstance().getApplicationContext());
                }
                count++;
            }

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.v("LifecycleCallbacks", activity + "onActivityCreated");
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.v("LifecycleCallbacks", activity + "onActivityResumed");
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.v("LifecycleCallbacks", activity + "onActivityPaused");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Log.v("LifecycleCallbacks", activity + "onActivitySaveInstanceState");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.v("LifecycleCallbacks", activity + "onActivityDestroyed");
            }
        });
    }

    public AppComponent getAppComponent() {
        return this.mAppComponent;
    }

    public static App getInstance() {
        return mContext;
    }

    private void initComponent() {

        this.mAppComponent = DaggerAppComponent
                .builder()
                .httpModule(new httpModule())
                .appModule(new AppModule(this))
                .build();
    }

    /**
     * 初始化sp
     */
    private void initPrefs() {
        SharedUtils.init(this, getPackageName() + "_preference", Context.MODE_MULTI_PROCESS);
    }

    //字体的适配
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //非默认值
        if (newConfig.fontScale != 1) {
            getResources();
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {//还原字体大小
        Resources res = super.getResources();
        //非默认值
        if (res.getConfiguration().fontScale != 1) {
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }
}
