package com.gofishfarm.htkj.service;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.dhh.websocket.Config;
import com.dhh.websocket.RxWebSocket;
import com.dhh.websocket.WebSocketSubscriber;
import com.dhh.websocket.WebSocketSubscriber2;
import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.event.OrderEvent;
import com.gofishfarm.htkj.event.PlayMessageEvent;
import com.gofishfarm.htkj.event.WsCloseEvent;
import com.gofishfarm.htkj.ui.main.fishingpage.OnLookActivity;
import com.gofishfarm.htkj.ui.main.fishingpage.UserFishNewCommerActivity;
import com.gofishfarm.htkj.ui.main.fishingpage.UserFishingActivity;
import com.gofishfarm.htkj.utils.ActivityUtils;
import com.gofishfarm.htkj.utils.NetworkUtils;
import com.gofishfarm.htkj.utils.SharedUtils;
import com.gofishfarm.htkj.utils.Suspended.WindowUtils;
import com.gofishfarm.htkj.utils.notificationutils.BroadCastManager;
import com.gofishfarm.htkj.utils.notificationutils.NotificationUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hjq.toast.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.WebSocket;

public class PlayService extends Service {

    private final static int SERVICE_ID = 1000;
    private static final String TAG = PlayService.class.getSimpleName();
    private WebSocketSubscriber wsObservable;
    private static Disposable mDisposable;
    private OrderEvent mOrderEvent;
    private String Urls;
    private GsonBuilder builder;
    private Gson gson;
    private int reconnect_num = 0;
    private Boolean isFurstIn = false;


    public PlayService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initGson();
        initBroadRecever();
        improvePriority();

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (!isFurstIn == true) {
            Urls = intent.getStringExtra(ConfigApi.SOCKETURL);
            if (null != Urls && !TextUtils.isEmpty(Urls)) {
                initWebSocket();
            }
        }
        isFurstIn = true;
        return super.onStartCommand(intent, flags, startId);
    }

    private void initGson() {
        if (null == builder) {
            builder = new GsonBuilder();
        }
        if (null == gson) {
            gson = builder.create();
        }
    }

    private LocalReceiver mReceiver;

    private void initBroadRecever() {
        //接收广播
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction("sendMsg");
            mReceiver = new LocalReceiver();
            BroadCastManager.getInstance().registerReceiver(PlayService.this, mReceiver, filter);//注册广播接收者
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class LocalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //收到广播后的,发送不同的指令
            String op_type = intent.getStringExtra("op_type");
            String Ws_fp_id = intent.getStringExtra("Ws_fp_id");
            String msg = intent.getStringExtra("msg");
            String msg_type = intent.getStringExtra("msg_type");
            int cmd_btn = intent.getIntExtra("cmd_btn", 0);
//            Log.d(TAG, op_type + "" + Ws_fp_id);
            if (op_type.equals("like")) {
                if (null == mOrderEvent) {
                    mOrderEvent = new OrderEvent();
                }
                mOrderEvent.setOp_type("like");
                sendFeverMsg(mOrderEvent);
            }
            if (op_type.equals("cutover")) {
                if (null == mOrderEvent) {
                    mOrderEvent = new OrderEvent();
                }
                mOrderEvent.setOp_type("cutover");
                mOrderEvent.setWs_fp_id(Ws_fp_id);
                sendSwitchMsg(mOrderEvent);
            }
            if (op_type.equals("talk")) {
                if (null == mOrderEvent) {
                    mOrderEvent = new OrderEvent();
                }
                mOrderEvent.setOp_type("talk");
                mOrderEvent.setMsg(msg);
                mOrderEvent.setMsg_type(msg_type);
                sendSwitchMsg(mOrderEvent);
            }
            //发送指令时操作
            if (op_type.equals("send_cmd_btn")) {
                if (null == mOrderEvent) {
                    mOrderEvent = new OrderEvent();
                }
                mOrderEvent.setOp_type("send_cmd_btn");
                mOrderEvent.setCmd_btn(cmd_btn);
                sendSwitchMsg(mOrderEvent);
            }

        }
    }

    private void sendSwitchMsg(OrderEvent event) {
        //1、使用JSONObject
        //url 对应的WebSocket 必须打开,否则报错
        try {
            //Log.d(TAG, gson.toJson(event).toString());
            RxWebSocket.send(Urls, gson.toJson(event));
        } catch (Exception e) {
        }
    }

    private void sendFeverMsg(OrderEvent event) {
        //1、使用JSONObject
        //url 对应的WebSocket 必须打开,否则报错
        try {
            RxWebSocket.send(Urls, gson.toJson(event));
        } catch (Exception e) {
        }
    }

    private void initWebSocket() {
        Config config = new Config.Builder()
                .setShowLog(false)           //show  log
//                .setClient(yourClient)   //if you want to set your okhttpClient
                .setShowLog(true, "SOCKETURL")
                .setReconnectInterval(3, TimeUnit.SECONDS)  //set reconnect interval
//                .setSSLSocketFactory(yourSSlSocketFactory, yourX509TrustManager) // wss support
                .build();
        RxWebSocket.setConfig(config);

        /**
         *
         *如果你想将String类型的text解析成具体的实体类，比如{@link List<String>},
         * 请使用 {@link  WebSocketSubscriber2}，仅需要将泛型传入即可
         */
        if (wsObservable == null) {
            wsObservable =
                    RxWebSocket.get(Urls)
                            //自动注销：RxLifecycle : https://github.com/dhhAndroid/RxLifecycle
//                            .compose(RxLifecycle.with(this).<WebSocketInfo>bindToLifecycle())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new WebSocketSubscriber() {
                                @Override
                                protected void onOpen(WebSocket webSocket) {
//                                    Log.d(TAG, "onOpen");
                                    super.onOpen(webSocket);
                                    sendHeartBeat(5);
                                    //initFirstSend();
                                }

                                @Override
                                protected void onMessage(@NonNull String text) {
//                                    Log.d(TAG, "onMessage");
                                    if (null != text && !TextUtils.isEmpty(text)) {
                                        PlayMessageEvent messageEvent = new PlayMessageEvent();
                                        messageEvent = gson.fromJson(text, PlayMessageEvent.class);
                                        Log.d(TAG, messageEvent.toString());
                                        EventBus.getDefault().post(messageEvent);
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
//                                    Log.d(TAG, "onError" + e.getMessage());
                                    super.onError(e);
                                }

                                @Override
                                protected void onReconnect() {
//                                    Log.d(TAG, "onReconnect");
                                    reconnect_num++;
                                    if (reconnect_num >= 3) {
                                        //重连次数超过3次，关闭页面
                                        if (!NetworkUtils.isAvailable(getApplicationContext())) {
                                            ToastUtils.show("您的网络已经断开，请检查网络设置");
                                        } else {
                                            ToastUtils.show("当前钓鱼已结束！");
                                        }
                                        closeFishingPage();
                                    }
                                    super.onReconnect();
                                }

                                @Override
                                protected void onClose() {
//                                    Log.d(TAG, "onClose");
                                    closeFishingPage();
                                    super.onClose();
                                }
                            });
        }
    }

    private void closeFishingPage() {
        SharedUtils.getInstance().putBoolean(ConfigApi.ISFISHING, false);
        //关闭页面
        closeVideo();
        WindowUtils.hidePopupWindow();
        List<Activity> mList = ActivityUtils.getActivityList();
        for (Activity mActivity : mList) {
            if ((mActivity instanceof OnLookActivity) || (mActivity instanceof UserFishingActivity) || (mActivity instanceof UserFishNewCommerActivity)) {
                mActivity.finish();
            }
        }
    }

    private void closeVideo() {
        WsCloseEvent wsCloseEvent = new WsCloseEvent();
        wsCloseEvent.setClose(true);
        EventBus.getDefault().post(wsCloseEvent);
    }

    private void sendHeartBeat(long time) {
        Observable.interval(time, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                //使用observerOn()指定订阅者运行的线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        mDisposable = disposable;
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        //url 对应的WebSocket 必须打开,否则报错
                        try {
                            //1、使用JSONObject
                            RxWebSocket.send(Urls, ConfigApi.HEARTBEAT_SRT);
                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //取消订阅
                        cancel();
                    }

                    @Override
                    public void onComplete() {
                        //取消订阅
                        cancel();
                    }
                });

    }

    /**
     * 取消订阅
     */
    public void cancel() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            // Log.d(TAG, "====定时器取消======");
        }
    }

    private void improvePriority() {
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, UserFishingActivity.class), 0);
        PendingIntent contentIntent = PendingIntent.getService(this, 0, new Intent(this, PlayService.class), 0);

//        Intent intent = new Intent(this, UserFishingActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);

//        PendingIntent contentIntent = PendingIntent.getBroadcast(this, 0, new Intent(this, NotificationClickReceiver.class), 0);
        NotificationUtils notificationUtils = new NotificationUtils(this);
        Notification notification = notificationUtils.getNotification("沙发渔霸", "钓手钓鱼中");
        notification.contentIntent = contentIntent;
        startForeground(SERVICE_ID, notification);

    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        BroadCastManager.getInstance().unregisterReceiver(this, mReceiver);//注销广播接收者
        if (null != wsObservable) {
            wsObservable.dispose();
        }
        if (null != wsObservable) {
            wsObservable = null;
        }
        //取消订阅
        cancel();
        super.onDestroy();
    }

}
