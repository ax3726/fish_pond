package com.gofishfarm.htkj.ui.main.fishingpage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gofishfarm.htkj.App;
import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.base.BaseActivity;
import com.gofishfarm.htkj.bean.ChatMesgBean;
import com.gofishfarm.htkj.bean.FocousOrNotBean;
import com.gofishfarm.htkj.bean.OnLookBean;
import com.gofishfarm.htkj.bean.OnLookFocousBean;
import com.gofishfarm.htkj.bean.UserInfoBean;
import com.gofishfarm.htkj.event.PlayMessageEvent;
import com.gofishfarm.htkj.event.WsCloseEvent;
import com.gofishfarm.htkj.image.GlideApp;
import com.gofishfarm.htkj.presenter.main.fishingpage.OnLookPresenter;
import com.gofishfarm.htkj.service.PlayService;
import com.gofishfarm.htkj.sweetAlert.SweetAlertDialog;
import com.gofishfarm.htkj.ui.login.LoginActivity;
import com.gofishfarm.htkj.ui.main.MainActivity;
import com.gofishfarm.htkj.ui.myinfo.InviteFriendsActivity;
import com.gofishfarm.htkj.ui.webPage.WebActivity;
import com.gofishfarm.htkj.utils.AppUtils;
import com.gofishfarm.htkj.utils.NavationStatusBarUtils;
import com.gofishfarm.htkj.utils.NetworkUtils;
import com.gofishfarm.htkj.utils.SharedUtils;
import com.gofishfarm.htkj.utils.notificationutils.BroadCastManager;
import com.gofishfarm.htkj.view.main.fishingpage.OnLookActivityView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gyf.barlibrary.ImmersionBar;
import com.hjq.toast.ToastUtils;
import com.tencent.qcloud.logutils.LogDialog;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;
import q.rorbin.badgeview.DisplayUtil;
import q.rorbin.badgeview.QBadgeView;

public class OnLookActivity extends BaseActivity<OnLookActivityView, OnLookPresenter> implements OnLookActivityView, ITXLivePlayListener, View.OnClickListener {

    private TextView tv_onlook;
    private ImageView iv_follow;
    private TextView tv_follow;
    private ImageView iv_onlookers;
    private TextView tv_onlookers;
    private ImageView iv_share;
    private LinearLayout ll_right_menu;
    private TXCloudVideoView mView;
    private TXLivePlayer mTxlpPlayer;

    private ImageView iv_search;
    private EditText et_fisher_id;
    private ImageView iv_del;
    private RelativeLayout rl_search_content;
    private TextView tv_search;
    private LinearLayout rl_search;
    private RelativeLayout rl_fish_num;
    private RelativeLayout rl_fish_num_layout;
    private Toolbar toolbar;
    //聊天布局
    private RelativeLayout rl_root;
    private EditText et_input_message;
    private TextView tv_confrim_btn;
    private ImageView iv_chat;
    private LinearLayout li_chat;
    private RecyclerView recl_chat;

    private FrameLayout fl_root_layout;
    private ImageButton btn_close;

    private ConstraintLayout cl_user_minfo;
    private CircleImageView civ_fisher_imag;
    private TextView tv_fisher_name;
    private TextView tv_rank_num;

    private TextView tv_yutang;
    private ImageView iv_yutang;


    private LinearLayout ll_msg_tip;
    private TextView tv_msg_tips;
    private TextView tv_recharge;
    private ImageView iv_tip_close;

    private OnLookBean mOnLookBean;
    private String liveUrl = "";
    private String fp_id = "";
    private String fishery_id = "";//渔场编号
    private String field = "";//钓手号或者手机号
    private Boolean isFocous = false;

    private String ws_fisher_id_temp = "";//缓存，不一致就请求数据

    private GsonBuilder builder;
    private Gson gson;
    private String ws_likes = "0";
    private String ws_onlook = "0";
    private String ws_fish_num = "0";
    private String content = "";
    private String btn_link = "";
    private String btn_name = "";

    //用户头像信息
    private String avatar;
    private String nick_name;
    private String rank;
    private String fishery_name;

    private String focus_status = "";
    private QBadgeView badgeVie;
    private boolean isMenueCanshow = true;
    //聊天
    private ChatAdapter adapter;
    private FixSizeLinkedList<ChatMesgBean> datas = new FixSizeLinkedList<>(100);

    @Inject
    OnLookPresenter mOnLookPresenter;

    @Override
    public OnLookPresenter createPresenter() {
        return this.mOnLookPresenter;
    }

    @Override
    public OnLookActivity createView() {
        return this;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_onlook_play;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setcaCloaseKeyBord(false);
        EventBus.getDefault().register(this);
        initGson();
        initLayout();
        initPlayer();
        initInoutLayout();

      /*  if (!NetworkUtils.isWifiConnected(this)
                && NetworkUtils.isMobileConnected(this)
                && !SharedUtils.getInstance().getBoolean(ConfigApi.CANUSEMOBILENET, false)) {
//            initNetTipWindow();
            showDialog("提示", "您正在使用移动网络,继续将会耗费通用流量！");
        } else {
            showProgress(getString(R.string.video_load));
            initData();
        }*/
        if (!NetworkUtils.isWifiConnected(this)
                && NetworkUtils.isMobileConnected(this)) {
            ToastUtils.show("您正在使用手机流量，请尽量切换至WiFi环境！");
        }
        showProgress(getString(R.string.video_load));
        initData();
    }

    /**
     * 获得屏幕高度(有导航栏)
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 初始化输入法布局
     */
    private void initInoutLayout() {
        //输入法到底部的间距(按需求设置)
        final int paddingBottom = DisplayUtil.dp2px(this, 5);
        final int paddings = DisplayUtil.dp2px(this, 50);
        //输入法滚动监听
        fl_root_layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                int screenHeight = getWindow().getDecorView().getRootView().getHeight();
                //r.top 是状态栏高度
                int softHeight = screenHeight - r.bottom;
                if (softHeight > 400) {//当输入法高度大于100判定为输入法打开了
                    if (et_input_message.hasFocus()) {
                        rl_root.scrollTo(0, softHeight);
                        rl_root.setVisibility(View.VISIBLE);
                    } else {
                        rl_root.scrollTo(0, 0);
                        rl_root.setVisibility(View.GONE);
                    }
                } else {//否则判断为输入法隐藏了
                    rl_root.scrollTo(0, 0);
                    rl_root.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void initListener() {
        btn_close.setOnClickListener(this);
        tv_onlook.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        iv_del.setOnClickListener(this);
        iv_follow.setOnClickListener(this);
    }

    private void initGson() {
        if (null == builder) {
            builder = new GsonBuilder();
        }
        if (null == gson) {
            gson = builder.create();
        }
    }

    //点赞
    private void doFever() {
        Intent intent = new Intent();
        intent.setAction("sendMsg");
        intent.putExtra("op_type", "like");
        BroadCastManager.getInstance().sendBroadCast(this, intent);
    }

    //发送消息
    private void sendMsg(String msg_type, String msg) {
        Intent intent = new Intent();
        intent.setAction("sendMsg");
        intent.putExtra("op_type", "talk");
        intent.putExtra("msg", msg);
        intent.putExtra("msg_type", msg_type);
        BroadCastManager.getInstance().sendBroadCast(this, intent);
    }

    //切换流
    private void doSwitch() {
        Intent intent = new Intent();
        intent.setAction("sendMsg");
        intent.putExtra("op_type", "cutover");
        intent.putExtra("Ws_fp_id", fp_id);
        BroadCastManager.getInstance().sendBroadCast(this, intent);
    }

    //关闭钓鱼和服务
    @Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    public void onWsCloseEvent(WsCloseEvent event) {
        /* Do something */
        if (null != event && event.isClose() == true) {
            stopServices();
            finish();
        }
    }

    //获取长连接的消息
    @Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    public void onWSPlayMessageEvent(PlayMessageEvent event) {
        /* Do something */
//        tv_msg.setText(gson.toJson(event));
        if (!event.getWs_likes().equals(ws_likes) && !TextUtils.isEmpty(event.getWs_likes())) {
            ws_likes = event.getWs_likes();
        }
        if (!event.getWs_onlook().equals(ws_onlook) && !TextUtils.isEmpty(event.getWs_onlook())) {
            ws_onlook = event.getWs_onlook();
        }
        if (!event.getWs_fish_num().equals(ws_fish_num) && !TextUtils.isEmpty(event.getWs_fish_num())) {
            ws_fish_num = event.getWs_fish_num();
        }
        tv_follow.setText(ws_likes);
        tv_onlookers.setText(ws_onlook);
        badgeVie.setBadgeText(ws_fish_num);

        if (!event.getWs_fisher_id().equals(fishery_id) && !TextUtils.isEmpty(event.getWs_fisher_id())) {
            fishery_id = event.getWs_fisher_id();
        }
//        if (!ws_fisher_id_temp.equals(fishery_id)) {
//            ws_fisher_id_temp = fishery_id;
//            mOnLookPresenter.getOnLookFocousData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION), ws_fisher_id_temp);
//        }
        //广播
        if (null != event.getBroadcast()) {
            if (!event.getBroadcast().getContent().equals(content) && !TextUtils.isEmpty(event.getBroadcast().getContent())) {
                content = event.getBroadcast().getContent();
                ll_msg_tip.setVisibility(View.VISIBLE);
                tv_msg_tips.setText(content);
            }

            if (!event.getBroadcast().getBtn_name().equals(btn_name)) {
                if (!TextUtils.isEmpty(event.getBroadcast().getBtn_name())) {
                    btn_name = event.getBroadcast().getBtn_name();
                    tv_recharge.setText(btn_name);
                } else {
                    btn_name = "";
                    tv_recharge.setText(btn_name);
                }
            }
            if (!TextUtils.isEmpty(event.getBroadcast().getBtn_link()) && !event.getBroadcast().getBtn_link().equals(btn_link)) {
                btn_link = event.getBroadcast().getBtn_link();
            }
        }


        //聊天内容
        if (null != event.getMessages() && null != event.getMessages().getMsg()) {
            ChatMesgBean chatMesgBean = new ChatMesgBean();
            chatMesgBean.setName(event.getMessages().getName());
            chatMesgBean.setMsg_type(event.getMessages().getMsg_type());
            chatMesgBean.setMsg(event.getMessages().getMsg());
            chatMesgBean.setAvatar(event.getMessages().getAvatar());
            datas.add(chatMesgBean);
            adapter.notifyDataSetChanged();
            recl_chat.smoothScrollToPosition(datas.size());
        }

    }

    private void initLayout() {
        //让虚拟键盘一直不显示
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
//        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE;
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        window.setAttributes(params);

        iv_tip_close = (ImageView) findViewById(R.id.iv_tip_close);
        ll_msg_tip = (LinearLayout) findViewById(R.id.ll_msg_tip);
        tv_msg_tips = (TextView) findViewById(R.id.tv_msg_tips);
        tv_recharge = (TextView) findViewById(R.id.tv_recharge);

        btn_close = (ImageButton) findViewById(R.id.btn_close);
        civ_fisher_imag = (CircleImageView) findViewById(R.id.civ_fisher_imag);
        tv_fisher_name = (TextView) findViewById(R.id.tv_fisher_name);
        cl_user_minfo = findViewById(R.id.cl_user_minfo);
        tv_rank_num = findViewById(R.id.tv_rank_num);
        tv_yutang = findViewById(R.id.tv_yutang);
        iv_yutang = findViewById(R.id.iv_yutang);

        tv_onlook = (TextView) findViewById(R.id.tv_onlook);
        tv_follow = (TextView) findViewById(R.id.tv_follow);
        tv_onlookers = (TextView) findViewById(R.id.tv_onlookers);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        ll_right_menu = (LinearLayout) findViewById(R.id.ll_right_menu);

        iv_follow = (ImageView) findViewById(R.id.iv_follow);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        et_fisher_id = (EditText) findViewById(R.id.et_fisher_id);
        iv_del = (ImageView) findViewById(R.id.iv_del);
        rl_search_content = (RelativeLayout) findViewById(R.id.rl_search_content);
        rl_fish_num = (RelativeLayout) findViewById(R.id.rl_fish_num);
        rl_fish_num_layout = (RelativeLayout) findViewById(R.id.rl_fish_num_layout);
        tv_search = (TextView) findViewById(R.id.tv_search);
        rl_search = (LinearLayout) findViewById(R.id.rl_search);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImmersionBar.setTitleBar(this, toolbar);

        fl_root_layout = findViewById(R.id.fl_root_layout);
        rl_root = findViewById(R.id.rl_root);
        li_chat = findViewById(R.id.li_chat);
        recl_chat = (RecyclerView) findViewById(R.id.recl_chat);
        et_input_message = findViewById(R.id.et_input_message);
        tv_confrim_btn = findViewById(R.id.tv_confrim_btn);
        iv_chat = findViewById(R.id.iv_chat);

        tv_confrim_btn.setOnClickListener(this);
        iv_chat.setOnClickListener(this);

        iv_tip_close.setOnClickListener(this);
        ll_msg_tip.setOnClickListener(this);
        tv_msg_tips.setOnClickListener(this);
        tv_recharge.setOnClickListener(this);

        adapter = new ChatAdapter(R.layout.item_chat, datas);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recl_chat.setLayoutManager(manager);
        recl_chat.setAdapter(adapter);


        badgeVie = new QBadgeView(this);
        badgeVie.bindTarget(rl_fish_num_layout)
                .setBadgeBackgroundColor(Color.parseColor("#FF2F2F"))
                .setBadgeTextColor(Color.parseColor("#ffe958"))
                .setBadgeGravity(Gravity.TOP | Gravity.END)
                .setBadgeTextSize(12, true)
                .setBadgeText(ws_fish_num);

        tv_follow.setText(ws_likes);
        tv_onlookers.setText(ws_onlook);
        badgeVie.setBadgeText(ws_fish_num);

        et_input_message.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    HideKeyboard(v);
                    sendMsg("message", et_input_message.getText().toString().trim());
                    et_input_message.setText("");
                    return true;
                }
                return false;
            }
        });
    }

    //    ---------------------显示对话框------------------------------------
    private AlertDialog tipDialog;

    public void showDialog(String title, String content) {
        if (null == tipDialog) {
            tipDialog = new AlertDialog.Builder(this)
                    .setTitle(title)
                    .setMessage(content)
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            stopServices();
                            finish();
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            showProgress(getString(R.string.video_load));
                            SharedUtils.getInstance().putBoolean(ConfigApi.CANUSEMOBILENET, true);
                            initData();
                        }
                    })
                    .create();
        }
        tipDialog.show();
    }

    public void closeClearDialog() {
        if (null != tipDialog && tipDialog.isShowing()) {
            tipDialog.dismiss();
        }
        if (null != tipDialog) {
            tipDialog = null;
        }
    }
    //    ---------------------显示页面的加载进度条------------------------------------

    private void initNetTipWindow() {
        final SweetAlertDialog mSweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE);
        mSweetAlertDialog.setCancelText("取消");
        mSweetAlertDialog.setConfirmText("继续");
        mSweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                mSweetAlertDialog.dismiss();
                finish();
            }
        });
        mSweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                mSweetAlertDialog.dismiss();
                showProgress(getString(R.string.video_load));
                SharedUtils.getInstance().putBoolean(ConfigApi.CANUSEMOBILENET, true);
                initData();
            }
        });
        mSweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        mSweetAlertDialog.setTitleText("提示");
        mSweetAlertDialog.setContentText("您正在使用移动网络\r\n,继续将会耗费通用流量");
        mSweetAlertDialog.show();
    }

    private void initPlayer() {
        mView = findViewById(R.id.tx_video_view_follow);
        mView.setOnClickListener(this);
        //创建 player 对象
        mTxlpPlayer = new TXLivePlayer(this);
        //关键 player 对象与界面 view
        mTxlpPlayer.setPlayerView(mView);

        mTxlpPlayer.setConfig(new TXLivePlayConfig());
        mTxlpPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
        // 设置填充模式
        mTxlpPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
        // 设置画面渲染方向
        mTxlpPlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_LANDSCAPE);
        mTxlpPlayer.setPlayListener(OnLookActivity.this);
    }

    private void play() {
        if (null != liveUrl && !TextUtils.isEmpty(liveUrl)) {
            mTxlpPlayer.startPlay(liveUrl, TXLivePlayer.PLAY_TYPE_LIVE_RTMP);
            //开启服务--socket连接
            startServices();
        }
    }

    private void startServices() {
        Intent intent = new Intent(this, PlayService.class);
        UserInfoBean mUserInfoBean = SharedUtils.getInstance().getObject(ConfigApi.USER_INFO, UserInfoBean.class);
        String My_fishery_id = mUserInfoBean.getUser().getFisher_id();
        String access_token = mUserInfoBean.getAccess_token();

//        String urlStr = "ws://148.70.13.176:9501?onlook_id=";
        String urlStr = ConfigApi.SOCKETURL + "onlook_id=";
        //ws://148.70.13.176:9501?onlook_id={onlook_id}&ws_fp_id={fp_id}&access_token={access_token}
        if (fp_id != null && !TextUtils.isEmpty(fp_id)) {
            urlStr = urlStr + My_fishery_id + "&ws_fp_id=" + fp_id + "&access_token=" + access_token;
            intent.putExtra(ConfigApi.SOCKETURL, urlStr);
            startService(intent);
        }
    }

    private void stopServices() {
        Intent stopIntent = new Intent(this, PlayService.class);
        stopService(stopIntent);//停止服务
    }

    private void initData() {
        Intent intent = getIntent();
        mOnLookBean = (OnLookBean) intent.getSerializableExtra("ONLOOKBEAN");
        if (null != mOnLookBean) {
            liveUrl = mOnLookBean.getLives();
            fp_id = mOnLookBean.getFp_id();
            fishery_id = mOnLookBean.getFishing_fisher().getFisher_id();
            initHead(mOnLookBean);
            play();
            mOnLookPresenter.getOnLookFocousData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION), fishery_id);
        } else {
            fishery_id = intent.getStringExtra(ConfigApi.FISHERY_ID);
            fp_id = intent.getStringExtra(ConfigApi.FP_ID);

            if (null != fishery_id && !TextUtils.isEmpty(fishery_id)) {
                mOnLookPresenter.getOnLookBeanData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION), fishery_id);
            }
            if (null != fp_id && !TextUtils.isEmpty(fp_id)) {
                mOnLookPresenter.getOnLookBeanData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION), fp_id);
            }
        }
    }

    @Override
    protected void onResume() {
        mTxlpPlayer.resume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mTxlpPlayer.pause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        closeClearDialog();
        mTxlpPlayer.stopPlay(true); // true 代表清除最后一帧画面
        mView.onDestroy();
        stopServices();
        super.onDestroy();
    }


    @Override
    public void complete(String msg) {

    }

    @Override
    public void showError(String paramString, int status_code) {
        if (status_code != 401) {
            ToastUtils.show(paramString);
        }
        if (status_code == 401) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    public void onPlayEvent(int event, Bundle bundle) {
        if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {
//            roomListenerCallback.onDebugLog("[AnswerRoom] 拉流失败：网络断开");
//            roomListenerCallback.onError(-1, "网络断开，拉流失败");
            ToastUtils.show("网络断开，拉流失败");
            hideProgress();
            dismissDialog();
        } else if (event == TXLiveConstants.PLAY_EVT_GET_MESSAGE) {
            String msg = null;
            try {
                msg = new String(bundle.getByteArray(TXLiveConstants.EVT_GET_MSG), "UTF-8");
//                roomListenerCallback.onRecvAnswerMsg(msg);
                ToastUtils.show(msg);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_END) {
//            ToastUtils.show("直播已经结束，请观看其他频道");
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
            hideProgress();
            dismissDialog();
        }
    }

    @Override
    public void onNetStatus(Bundle bundle) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_recharge:
                if (null != btn_link && !TextUtils.isEmpty(btn_link) && btn_link.length() > 5) {
                    Intent intentWeb = new Intent(this, WebActivity.class);
                    intentWeb.putExtra(ConfigApi.WEB_URL, btn_link);
                    startActivity(intentWeb);
                } else if (btn_link.equals("1")) {
                    startActivity(new Intent(this, RechargeActivity.class));
                }
                break;
            case R.id.iv_tip_close:
                ll_msg_tip.setVisibility(View.GONE);
                break;
            case R.id.iv_follow:
                HideKeyboard(fl_root_layout);
//                ToastUtils.show("点赞");
                doFever();
                showFeverAnimi(iv_follow);
                break;
            case R.id.iv_del:
                HideKeyboard(fl_root_layout);
                iv_del.setVisibility(View.INVISIBLE);
                et_fisher_id.setText("");
                break;
            case R.id.tv_search:
                HideKeyboard(fl_root_layout);
                goSearch();
                break;
            case R.id.btn_close:
                HideKeyboard(fl_root_layout);
                stopServices();
                finish();
                break;
            case R.id.iv_share:
                HideKeyboard(fl_root_layout);
                startActivity(new Intent(this, InviteFriendsActivity.class));
                break;
            case R.id.tv_onlook:
                HideKeyboard(fl_root_layout);
                if (focus_status.equals("3") || focus_status.equals("2")) {
                    mOnLookPresenter.getDeFocousData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION), fishery_id);
                } else if (focus_status.equals("1")) {
                    mOnLookPresenter.getOnFocousData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION), fishery_id);
                }
                break;
            case R.id.tv_confrim_btn:
                sendMsg("message", et_input_message.getText().toString().trim());
                et_input_message.setText("");
                HideKeyboard(fl_root_layout);
                break;
            case R.id.iv_chat:
                li_chat.setVisibility(View.VISIBLE);
                et_fisher_id.clearFocus();
                et_input_message.requestFocus();
                openKeyboard();
                break;
            case R.id.tx_video_view_follow:
                HideKeyboard(fl_root_layout);
                isMenueCanshow = !isMenueCanshow;
                if (isMenueCanshow) {
                    li_chat.setVisibility(View.VISIBLE);
                    iv_chat.setVisibility(View.VISIBLE);
                } else {
                    li_chat.setVisibility(View.INVISIBLE);
                    iv_chat.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                break;
        }
    }

    private ScaleAnimation animation;


    /**
     * 点赞的动画
     *
     * @param view
     */
    private void showFeverAnimi(View view) {
        if (null == animation) {
            //animation = new ScaleAnimation(1, 1.2f, 1, 1.2f, Animation.RELATIVE_TO_SELF, 0.2f, Animation.RELATIVE_TO_SELF, 0.2f);
            animation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, 1, 0.5f);
            /**
             *
             * @param fromX 起始x轴位置，0为最小，1为原始，float形
             * @param toX 同上
             * @param fromY 同上T
             * @param toY 同上
             * @param pivotXType 用来约束pivotXValue的取值。取值有三种：Animation.ABSOLUTE，Animation.RELATIVE_TO_SELF，Animation.RELATIVE_TO_PARENT
             * Type：Animation.ABSOLUTE：绝对，如果设置这种类型，后面pivotXValue取值就必须是像素点；比如：控件X方向上的中心点，pivotXValue的取值mIvScale.getWidth() / 2f
             *      Animation.RELATIVE_TO_SELF：相对于控件自己，设置这种类型，后面pivotXValue取值就会去拿这个取值是乘上控件本身的宽度；比如：控件X方向上的中心点，pivotXValue的取值0.5f
             *      Animation.RELATIVE_TO_PARENT：相对于它父容器（这个父容器是指包括这个这个做动画控件的外一层控件）， 原理同上，
             * @param pivotXValue  配合pivotXType使用，原理在上面
             * @param pivotYType 同from/to
             * @param pivotYValue 原理同上
             */

            animation.setDuration(500);
            //设置持续时间
            animation.setFillAfter(false);
            //设置动画结束之后的状态是否是动画的最终状态，true，表示是保持动画结束时的最终状态
            animation.setRepeatCount(0);
        }
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //设置循环次数，0为1次
        view.startAnimation(animation);

    }

    private void goSearch() {
        iv_del.setVisibility(View.VISIBLE);
        String id = et_fisher_id.getText().toString();
        mOnLookPresenter.getOnLookSerchBeanData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, ""), id);
    }

    @Override
    public void OnLookFocousBeanData(OnLookFocousBean mOnLookFocousBean) {
        if (null == mOnLookFocousBean) {
            return;
        }
        avatar = mOnLookFocousBean.getAvatar();
        tv_fisher_name.setText(mOnLookFocousBean.getNick_name());
        focus_status = mOnLookFocousBean.getFocused() + "";
        if (focus_status.equals("1")) {
            tv_onlook.setText("立即关注");
        } else if (focus_status.equals("2")) {
            tv_onlook.setText("已关注");
        } else if (focus_status.equals("3")) {
            tv_onlook.setText("互相关注");
        }
        GlideApp.with(this).load(avatar).placeholder(R.drawable.my_touxiang).error(R.drawable.my_touxiang).into(civ_fisher_imag);
        tv_rank_num.setText(mOnLookFocousBean.getRank());
        tv_yutang.setText(mOnLookFocousBean.getFishery_name());
    }

    @Override
    public void OnFocousBeanData(FocousOrNotBean mFocousOrNotBean) {
        if (null == mFocousOrNotBean) {
            return;
        }
        ToastUtils.show("关注成功");
        focus_status = mFocousOrNotBean.getFocus_status() + "";
        if (focus_status.equals("1")) {
            tv_onlook.setText("立即关注");
        } else if (focus_status.equals("2")) {
            tv_onlook.setText("已关注");
        } else if (focus_status.equals("3")) {
            tv_onlook.setText("互相关注");
        }
    }

    @Override
    public void OnDeFocousBeanData(FocousOrNotBean mFocousOrNotBean) {
        if (null == mFocousOrNotBean) {
            return;
        }
        ToastUtils.show("已取消关注");
        focus_status = mFocousOrNotBean.getFocus_status() + "";
        if (focus_status.equals("1")) {
            tv_onlook.setText("立即关注");
        } else if (focus_status.equals("2")) {
            tv_onlook.setText("已关注");
        } else if (focus_status.equals("3")) {
            tv_onlook.setText("互相关注");
        }
    }

    @Override
    public void onOnLookDataCallback(OnLookBean onLookBean) {
        if (null == onLookBean) {
            return;
        }
        liveUrl = onLookBean.getLives();
        fp_id = onLookBean.getFp_id();
        fishery_id = onLookBean.getFishing_fisher().getFisher_id();
        initHead(onLookBean);
        play();
        mOnLookPresenter.getOnLookFocousData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION), fishery_id);
    }

    private void initHead(OnLookBean onLookBean) {
        if (onLookBean.getFishing_fisher() != null) {
            GlideApp
                    .with(this)
                    .load(onLookBean.getFishing_fisher().getAvatar())
                    .placeholder(R.drawable.my_touxiang)
                    .error(R.drawable.my_touxiang)
                    .into(civ_fisher_imag);
            fishery_id = onLookBean.getFishing_fisher().getFisher_id();
            ws_fisher_id_temp = fishery_id;
//            tv_fisher_name.setText(onLookBean.getFishing_fisher().getNick_name());1234
//            tv_rank_num.setText(onLookBean.getFishing_fisher().getNick_name());
//            tv_yutang.setText(onLookBean.getFishing_fisher().getNick_name());

            focus_status = onLookBean.getFishing_fisher().getFocus_status();
            if (focus_status.equals("1")) {
                tv_onlook.setText("立即关注");
            } else if (focus_status.equals("2")) {
                tv_onlook.setText("已关注");
            } else if (focus_status.equals("3")) {
                tv_onlook.setText("互相关注");
            }
        }
    }

    @Override
    public void onLookSerchBeanDataCallback(OnLookBean onLookBean) {
        //搜错结果
        if (null == onLookBean) {
            return;
        }
        fp_id = onLookBean.getFp_id();
        liveUrl = onLookBean.getLives();
        if (onLookBean.getFishing_fisher() != null) {
            initHead(onLookBean);
        }
        if (null != liveUrl && !TextUtils.isEmpty(liveUrl)) {
            //切换流
            mTxlpPlayer.startPlay(liveUrl, TXLivePlayer.PLAY_TYPE_LIVE_RTMP);
        }
        if (null != fp_id && !TextUtils.isEmpty(fp_id)) {
            //长连接切换
            doSwitch();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //点击完返回键，执行的动作
            stopServices();
            finish();
        }
        return true;
    }

    /**
     * 打开软键盘
     */
    private static void openKeyboard() {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) App.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

            }
        }, 200);
    }

    //隐藏虚拟键盘
    public static void HideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }
}
