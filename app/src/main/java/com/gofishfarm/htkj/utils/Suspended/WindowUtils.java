package com.gofishfarm.htkj.utils.Suspended;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.bean.FishDevciceBean;
import com.gofishfarm.htkj.ui.main.fishingpage.UserFishingActivity;
import com.gofishfarm.htkj.utils.SharedUtils;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

/**
 * 悬浮窗
 * Created by bob on
 */

public class WindowUtils {
    private static final String LOG_TAG = "WindowUtils";
    private static View mView = null;
    private static WindowManager mWindowManager = null;
    private static Context mContext = null;
    public static Boolean isShown = false;
    public static Boolean isMove = false;
    private static TXLivePlayer mTxlpPlayer;
    private static TXCloudVideoView tx_video_flow;
    private static FishDevciceBean fishDevciceBean;
    private static String liveUrl;
    private static int mFloatWinWidth, mFloatWinHeight,mFloatWinMarginTop,mFloatWinMarginRight;//悬浮窗的宽高
    private static OnClickListener mListener;//view的点击回调listener
    private static WindowManager.LayoutParams params;

    public static void setOnClickListener(OnClickListener listener) {
        mListener = listener;
    }

    public interface OnClickListener {
        void onClick(View view);
    }

    /**
     * 显示弹出框
     *
     * @param context
     * @param
     */
    public static void showPopupWindow(final Context context) {
        if (isShown) {
            mWindowManager.removeView(mView);
        }

//        mContext = context.getApplicationContext();
        mContext = context;
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
//        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();


//        // 悬浮窗默认显示以右上角为起始坐标
//        params.gravity = Gravity.RIGHT| Gravity.TOP;
        //计算得出悬浮窗口的宽高
        DisplayMetrics metric = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(metric);
        int screenWidth = metric.widthPixels;
        mFloatWinWidth = screenWidth * 1 / 3;
        mFloatWinHeight = mFloatWinWidth * 4 / 3;
        mFloatWinMarginTop= (int)context.getResources().getDimension(R.dimen.dp_15);
        mFloatWinMarginRight= (int)context.getResources().getDimension(R.dimen.dp_15);

        // 窗体的布局样式
        // 获取LayoutParams对象
        params = new WindowManager.LayoutParams();
        // 确定爱悬浮窗类型，表示在所有应用程序之上，但在状态栏之下
        //TODO? 在android2.3以上可以使用TYPE_TOAST规避权限问题
//        params.type = WindowManager.LayoutParams.TYPE_TOAST;//TYPE_PHONE
        params.format = PixelFormat.RGBA_8888;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        // 悬浮窗的对齐方式
        params.gravity = Gravity.LEFT | Gravity.TOP;
        // 悬浮窗的位置
        params.x = mFloatWinMarginRight;
        params.y = mFloatWinMarginTop;
        params.width = mFloatWinWidth;
        params.height = mFloatWinHeight;


        mView = setUpView(context);
        mView.setFocusableInTouchMode(true);
        mView.setOnTouchListener(new View.OnTouchListener() {
            int lastX = 0;
            int lastY = 0;
            int paramX = 0;
            int paramY = 0;

            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isMove = false;
                        lastX = (int) motionEvent.getRawX();
                        lastY = (int) motionEvent.getRawY();
                        paramX = params.x;
                        paramY = params.y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) motionEvent.getRawX() - lastX;
                        int dy = (int) motionEvent.getRawY() - lastY;
                        params.x = paramX + dx;
                        params.y = paramY + dy;
                        mWindowManager.updateViewLayout(mView, params);
                        break;
                    case MotionEvent.ACTION_UP:
                        int newOffsetX = params.x;
                        int newOffsetY = params.y;
                        if (Math.abs(newOffsetX - paramX) >= 2 && Math.abs(newOffsetY - paramY) >= 2) {
                            isMove = true;
                        }
                        break;
                }
                return isMove;
            }

        });

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtils.show("跳转隐藏");
                Intent intent = new Intent(context, UserFishingActivity.class);
                intent.putExtra(ConfigApi.FISHDEVCICEBEAN, fishDevciceBean);
                context.startActivity(intent);
                hidePopupWindow();
            }
        });
        // 类型
        if (Build.VERSION.SDK_INT >= 24) { /*android7.0不能用TYPE_TOAST*/
            FloatWindowManager.getInstance().applyOrShowFloatWindow(mContext);
            if (Build.VERSION.SDK_INT >= 25) {
                params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                params.type = WindowManager.LayoutParams.TYPE_PHONE;//TYPE_SYSTEM_ALERT
            }
        } else { /*以下代码块使得android6.0之后的用户不必再去手动开启悬浮窗权限*/
            String packname = context.getPackageName();
            PackageManager pm = context.getPackageManager();
            boolean permission = (PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.SYSTEM_ALERT_WINDOW", packname));
            if (permission) {
                params.type = WindowManager.LayoutParams.TYPE_PHONE;
            } else {
                params.type = WindowManager.LayoutParams.TYPE_TOAST;
            }
        }


        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.format = PixelFormat.TRANSPARENT;

        mWindowManager.addView(mView, params);
        isShown = true;
    }

    /**
     * 隐藏弹出框
     */
    public static void hidePopupWindow() {
        if (isShown && null != mView) {
            mWindowManager.removeView(mView);
            isShown = false;
        }
    }

    private static View setUpView(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.popupwindow, null);
        tx_video_flow = (TXCloudVideoView) view.findViewById(R.id.tx_video_flow);
        LinearLayout screenshot_layout = (LinearLayout) view.findViewById(R.id.screenshot_layout);
        fishDevciceBean = SharedUtils.getInstance().getObject(ConfigApi.FISHDEVCICEBEAN, FishDevciceBean.class);
        liveUrl = fishDevciceBean.getLives().get(0);
        initPlayer(context);
        return view;
    }

    private static void initPlayer(Context context) {
        //创建 player 对象
        mTxlpPlayer = new TXLivePlayer(context);
        //关键 player 对象与界面 view
        mTxlpPlayer.setPlayerView(tx_video_flow);

        mTxlpPlayer.setConfig(new TXLivePlayConfig());
        mTxlpPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
        // 设置填充模式
        mTxlpPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
        // 设置画面渲染方向
        mTxlpPlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_LANDSCAPE);
        mTxlpPlayer.setPlayListener(new ITXLivePlayListener() {
            @Override
            public void onPlayEvent(int i, Bundle bundle) {

            }

            @Override
            public void onNetStatus(Bundle bundle) {

            }
        });
        if (null != liveUrl && !TextUtils.isEmpty(liveUrl)) {
            mTxlpPlayer.startPlay(liveUrl, TXLivePlayer.PLAY_TYPE_LIVE_RTMP_ACC);
        }
    }
}
