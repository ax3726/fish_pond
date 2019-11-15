package com.gofishfarm.htkj.ui.main.fishingpage;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gofishfarm.htkj.App;
import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.base.BaseActivity;
import com.gofishfarm.htkj.bean.BaseBean;
import com.gofishfarm.htkj.bean.ChatMesgBean;
import com.gofishfarm.htkj.bean.FishDevciceBean;
import com.gofishfarm.htkj.bean.FishingShareBean;
import com.gofishfarm.htkj.bean.UserInfoBean;
import com.gofishfarm.htkj.event.PlayMessageEvent;
import com.gofishfarm.htkj.event.WsCloseEvent;
import com.gofishfarm.htkj.image.GlideApp;
import com.gofishfarm.htkj.presenter.main.fishingpage.UserFishingActivityPresenter;
import com.gofishfarm.htkj.service.PlayService;
import com.gofishfarm.htkj.ui.feedback.FeedBackActivity;
import com.gofishfarm.htkj.ui.login.LoginActivity;
import com.gofishfarm.htkj.ui.myinfo.InviteFriendsActivity;
import com.gofishfarm.htkj.ui.webPage.WebActivity;
import com.gofishfarm.htkj.utils.AppUtils;
import com.gofishfarm.htkj.utils.BASE64Encoder;
import com.gofishfarm.htkj.utils.NavationStatusBarUtils;
import com.gofishfarm.htkj.utils.NetworkUtils;
import com.gofishfarm.htkj.utils.SharedPreferencesUtils;
import com.gofishfarm.htkj.utils.SharedUtils;
import com.gofishfarm.htkj.utils.Suspended.WindowUtils;
import com.gofishfarm.htkj.utils.notificationutils.BroadCastManager;
import com.gofishfarm.htkj.view.main.fishingpage.UserFishingActivityView;
import com.gofishfarm.htkj.widget.GestureMoveView;
import com.gofishfarm.htkj.widget.RockerView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gyf.barlibrary.ImmersionBar;
import com.hjq.toast.ToastUtils;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;
import q.rorbin.badgeview.DisplayUtil;
import q.rorbin.badgeview.QBadgeView;

import static com.umeng.socialize.utils.ContextUtil.getContext;

public class UserFishingActivity extends BaseActivity<UserFishingActivityView, UserFishingActivityPresenter> implements UserFishingActivityView, View.OnTouchListener, ITXLivePlayListener {
    private Toolbar          toolbar;
    private ImageView        btn_close;
    private ImageView        btn_to_smaller;
    private ImageView        btn_to_help;
    private LinearLayout     ll_top_menu;
    private TextView         tv_msg_tips;
    private TextView         tv_recharge;
    private ImageView        iv_tip_close;
    private LinearLayout     ll_msg_tip;
    private ImageView        iv_error;
    private ImageView        iv_follow;
    private TextView         tv_follow;
    private ImageView        iv_onlookers;
    private TextView         tv_onlookers;
    private ImageView        iv_share;
    private LinearLayout     ll_right_menu;
    private TextView           btn_one;
    private TextView           btn_thr;
    private TextView           btn_fou;
    private TextView           btn_fiv;
    private TextView           btn_two;
    private LinearLayout     ll_bottom_menu;
    private RelativeLayout   rl_fish_num;
    private RelativeLayout   rl_fish_num_layout;
    private TXCloudVideoView mView;
    private TXLivePlayer     mTxlpPlayer;

    private RelativeLayout rl_get_Fish;
    private RelativeLayout rl_share_center;
    private TextView       tv_get_fish_coin;
    private TextView         btn_shere;
    private ImageView      iv_share_cancel;

    private FrameLayout    fl_root_layout;
    private RelativeLayout rl_root;
    private LinearLayout   ll_input_view;
    private EditText       et_input_message;
    private TextView       tv_confrim_btn;
    private ImageView      iv_chat;
    private LinearLayout   li_chat;
    private RecyclerView   recl_chat;

    private ConstraintLayout cl_user_minfo;
    private TextView         tv_rank_num;
    private TextView         tv_fisher_name;
    private TextView         tv_yutang;
    private CircleImageView  civ_fisher_imag;

    private boolean isMenueCanshow = true;
    private int     on_time;
    private int     off_time;

    private String ws_likes    = "0";
    private String ws_onlook   = "0";
    private String ws_fish_num = "0";
    //用户头像信息
    private String avatar;
    private String nick_name;
    private String rank;
    private String fishery_name;

    private String                       fp_id;
    private String                       dtu_id;
    private String                       dtu_apikay;
    private String                       order1_o = "";
    private String                       order1_c = "";
    private String                       order2_o = "";
    private String                       order2_c = "";
    private String                       order3_o = "";
    private String                       order3_c = "";
    private String                       order4_o = "";
    private String                       order4_c = "";
    private String                       order5_o = "";
    private String                       order5_c = "";
    private String                       on6      = "";
    private String                       off6     = "";
    private String                       alloff   = "";
    private String                       fish_integration;//获得金币
    private FishDevciceBean.CommandsBean commands;
    private List<String>                 lives;
    private String                       Playlives;
    private FishDevciceBean              mFishDevciceBean;
    private GsonBuilder                  builder;
    private Gson                         gson;

    private AlertDialog                                     tipDialog;
    private AlertDialog                                     tipDialog1;
    private com.gofishfarm.htkj.widget.iosalert.AlertDialog myDialog;//故障确认弹窗

    private QBadgeView badgeVie;

    private ChatAdapter                     adapter;
    private FixSizeLinkedList<ChatMesgBean> datas = new FixSizeLinkedList<>(100);

    private String content  = "";
    private String btn_link = "";
    private String btn_name = "";


    private FrameLayout      fly_rocker;
    private RockerView       rv_rocker;
    private GestureMoveView  gmv_body;
    private ConstraintLayout ll_guide;
    private TextView         tv_guide_close;

    private int     mType      = 0; //0 摇杆  1按键
    //是否展示新手指南
    private boolean mShouGuide = false;

    @Inject
    UserFishingActivityPresenter mUserFishingActivityPresenter;

    @Override
    public UserFishingActivityPresenter createPresenter() {
        return this.mUserFishingActivityPresenter;
    }

    @Override
    public UserFishingActivityView createView() {
        return this;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_user_fishing;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setcaCloaseKeyBord(false);
        EventBus.getDefault().register(this);
        mType = (int) SharedPreferencesUtils.getParam(this, "type", 0);


        initGson();
        initViews();
        setControlTypeView();
        initplayer();
        initData();
        initRockerControl();
        initGestureDetector();
        myDialog = new com.gofishfarm.htkj.widget.iosalert.AlertDialog(UserFishingActivity.this).builder();
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

    //获取长连接的消息
    @Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    public void onWSPlayMessageEvent(PlayMessageEvent event) {
        /* Do something */
//        tv_msg.setText(gson.toJson(event));
        if (!event.getIntegration().equals(fish_integration) && !TextUtils.isEmpty(event.getIntegration())) {
            fish_integration = event.getIntegration();
        }
        if (!event.getWs_likes().equals(ws_likes) && !TextUtils.isEmpty(event.getWs_likes())) {
            ws_likes = event.getWs_likes();
        }
        if (!event.getWs_onlook().equals(ws_onlook) && !TextUtils.isEmpty(event.getWs_onlook())) {
            ws_onlook = event.getWs_onlook();
        }
        if (!event.getWs_fish_num().equals(ws_fish_num) && !TextUtils.isEmpty(event.getWs_fish_num())) {
            ws_fish_num = event.getWs_fish_num();
            if (ws_fish_num.compareTo("0") > 0) {
                closeShare();
                //钓到一条鱼
                showShare();
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
            Log.d("kkk", "onWSPlayMessageEvent: " + chatMesgBean.toString());
//            if(datas.size()==5){
//                adapter.notifyItemRemoved(0);
//                adapter.notifyItemInserted(datas.size());
//            }else {
//                adapter.notifyItemInserted(datas.size());
//            }
            adapter.notifyDataSetChanged();
            recl_chat.smoothScrollToPosition(datas.size());
        }

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
            if (!event.getBroadcast().getBtn_link().equals(btn_link) && !TextUtils.isEmpty(event.getBroadcast().getContent())) {
                btn_link = event.getBroadcast().getBtn_link();
            }
        }

        tv_follow.setText(ws_likes);
        tv_onlookers.setText(ws_onlook);
        badgeVie.setBadgeText(ws_fish_num);
    }

    private void showShare() {
        rl_get_Fish.setVisibility(View.VISIBLE);
        tv_get_fish_coin.setText("获得了 " + fish_integration + " 渔币");
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

    private void initData() {
        Intent intent = getIntent();
        mFishDevciceBean = (FishDevciceBean) intent.getSerializableExtra(ConfigApi.FISHDEVCICEBEAN);
        SharedUtils.getInstance().putObject(ConfigApi.FISHDEVCICEBEAN, mFishDevciceBean);
        fish_integration = mFishDevciceBean.getFish_integration();
        fp_id = mFishDevciceBean.getFp_id();
        dtu_id = mFishDevciceBean.getDtu_id();
        dtu_apikay = mFishDevciceBean.getDtu_apikay();

        avatar = mFishDevciceBean.getUser_info().getAvatar();
        nick_name = mFishDevciceBean.getUser_info().getNick_name();
        rank = mFishDevciceBean.getUser_info().getRank();
        fishery_name = mFishDevciceBean.getUser_info().getFishery_name();
        GlideApp.with(AppUtils.getAppContext())
                .load(avatar)
                .placeholder(R.drawable.my_touxiang)
                .error(R.drawable.my_touxiang)
                .into(civ_fisher_imag);
        tv_fisher_name.setText(nick_name);
        tv_rank_num.setText(rank);
        tv_yutang.setText(fishery_name);

        order1_o = mFishDevciceBean.getCommands().getOn1();
        order1_c = mFishDevciceBean.getCommands().getOff1();
        order2_o = mFishDevciceBean.getCommands().getOn2();
        order2_c = mFishDevciceBean.getCommands().getOff2();
        order3_o = mFishDevciceBean.getCommands().getOn3();
        order3_c = mFishDevciceBean.getCommands().getOff3();
        order4_o = mFishDevciceBean.getCommands().getOn4();
        order4_c = mFishDevciceBean.getCommands().getOff4();
        order5_o = mFishDevciceBean.getCommands().getOn5();
        order5_c = mFishDevciceBean.getCommands().getOff5();
        on6 = mFishDevciceBean.getCommands().getOn6();
        off6 = mFishDevciceBean.getCommands().getOff6();
        alloff = mFishDevciceBean.getCommands().getAlloff();
        on_time = mFishDevciceBean.getOn_time();
        off_time = mFishDevciceBean.getOff_time();
        lives = mFishDevciceBean.getLives();
  /*      if (!NetworkUtils.isWifiConnected(this)
                && NetworkUtils.isMobileConnected(this)
                && !SharedUtils.getInstance().getBoolean(ConfigApi.CANUSEMOBILENET, false)) {
            showDialog("提示", "您正在使用移动网络,继续将会耗费通用流量！");
        } else {
            showProgress(getString(R.string.video_load));
            play();
        }*/
        if (!NetworkUtils.isWifiConnected(this)
                && NetworkUtils.isMobileConnected(this)) {
            ToastUtils.show("您正在使用手机流量，请尽量切换至WiFi环境！");
        }
        showProgress(getString(R.string.video_load));
        play();
    }

    private void initViews() {
        //让虚拟键盘一直不显示
        Window                     window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
//        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE;
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        window.setAttributes(params);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImmersionBar.setTitleBar(this, toolbar);
        mView = (TXCloudVideoView) findViewById(R.id.video_view);
        btn_close = findViewById(R.id.btn_close);
        btn_to_smaller = findViewById(R.id.btn_to_smaller);
        btn_to_help = findViewById(R.id.btn_to_help);
        ll_top_menu = (LinearLayout) findViewById(R.id.ll_top_menu);
        tv_msg_tips = (TextView) findViewById(R.id.tv_msg_tips);
        tv_recharge = (TextView) findViewById(R.id.tv_recharge);
        iv_tip_close = (ImageView) findViewById(R.id.iv_tip_close);
        ll_msg_tip = (LinearLayout) findViewById(R.id.ll_msg_tip);
        iv_error = (ImageView) findViewById(R.id.iv_error);
        iv_follow = (ImageView) findViewById(R.id.iv_follow);
        tv_follow = (TextView) findViewById(R.id.tv_follow);
        iv_onlookers = (ImageView) findViewById(R.id.iv_onlookers);
        tv_onlookers = (TextView) findViewById(R.id.tv_onlookers);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        ll_right_menu = (LinearLayout) findViewById(R.id.ll_right_menu);
        btn_one =  findViewById(R.id.btn_one);
        btn_thr =  findViewById(R.id.btn_thr);
        btn_fou =  findViewById(R.id.btn_fou);
        btn_fiv =  findViewById(R.id.btn_fiv);
        btn_two = findViewById(R.id.btn_two);
        ll_bottom_menu = (LinearLayout) findViewById(R.id.ll_bottom_menu);
        rl_fish_num = (RelativeLayout) findViewById(R.id.rl_fish_num);
        rl_fish_num_layout = (RelativeLayout) findViewById(R.id.rl_fish_num_layout);

        rl_root = findViewById(R.id.rl_root);
        rl_get_Fish = findViewById(R.id.rl_get_Fish);
        rl_share_center = findViewById(R.id.rl_share_center);
        tv_get_fish_coin = findViewById(R.id.tv_get_fish_coin);
        btn_shere = findViewById(R.id.btn_shere);
        iv_share_cancel = findViewById(R.id.iv_share_cancel);

        fl_root_layout = findViewById(R.id.fl_root_layout);
        ll_input_view = findViewById(R.id.ll_input_view);
        et_input_message = findViewById(R.id.et_input_message);
        tv_confrim_btn = findViewById(R.id.tv_confrim_btn);
        iv_chat = findViewById(R.id.iv_chat);
        li_chat = findViewById(R.id.li_chat);
        recl_chat = (RecyclerView) findViewById(R.id.recl_chat);

        cl_user_minfo = findViewById(R.id.cl_user_minfo);
        tv_rank_num = findViewById(R.id.tv_rank_num);
        tv_fisher_name = findViewById(R.id.tv_fisher_name);
        tv_yutang = findViewById(R.id.tv_yutang);
        civ_fisher_imag = findViewById(R.id.civ_fisher_imag);
        fly_rocker = findViewById(R.id.fly_rocker);
        rv_rocker = findViewById(R.id.rv_rocker);
        gmv_body = findViewById(R.id.gmv_body);
        ll_guide = findViewById(R.id.ll_guide);
        tv_guide_close = findViewById(R.id.tv_guide_close);

        mShouGuide = getIntent().getBooleanExtra("shou_guide", false);
        if (mShouGuide) {
            ll_guide.setVisibility(View.VISIBLE);
            ll_guide.setSelected(true);
        }

        badgeVie = new QBadgeView(this);
        badgeVie.bindTarget(rl_fish_num_layout)
                .setBadgeBackgroundColor(Color.parseColor("#FF2F2F"))
                .setBadgeTextColor(Color.parseColor("#ffe958"))
                .setBadgeGravity(Gravity.TOP | Gravity.END)
                .setBadgeTextSize(12, true)
                .setBadgeText(ws_fish_num);

        adapter = new ChatAdapter(R.layout.item_chat, datas);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recl_chat.setLayoutManager(manager);
        recl_chat.setAdapter(adapter);

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

    //-------------------悬浮窗-------------------
    private void initFloatWindow() {
        if (null == tipDialog1) {
            tipDialog1 = new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("当前应用暂无悬浮窗权限，请跳转设置")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
                            dialog.dismiss();
                        }
                    })
                    .create();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                tipDialog1.show();
            } else {
                WindowUtils.showPopupWindow(App.getInstance().getApplicationContext());
                finish();
            }
        } else {
            WindowUtils.showPopupWindow(App.getInstance().getApplicationContext());
            finish();
        }
    }

    //-------------------悬浮窗-------------------
    //    ---------------------显示对话框------------------------------------


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
                            play();
                        }
                    })
                    .create();
        }
        tipDialog.show();
    }

    public void showWorning() {
        myDialog.setGone().setMsg("设备出故障了吗，请立即上报！").setNegativeButton("取消", null).setPositiveButton("上报", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserFishingActivity.this, FeedBackActivity.class);
                intent.putExtra("fp_id", Integer.parseInt(fp_id));
                startActivity(intent);
            }
        }).show();
    }

    public void closeClearDialog() {
        if (null != tipDialog && tipDialog.isShowing()) {
            tipDialog.dismiss();
        }
        if (null != tipDialog) {
            tipDialog = null;
        }
        if (null != tipDialog1 && tipDialog1.isShowing()) {
            tipDialog1.dismiss();
        }
        if (null != tipDialog1) {
            tipDialog1 = null;
        }
    }

    //    ---------------------显示页面的加载进度条------------------------------------
    private void initplayer() {
        mView = (TXCloudVideoView) findViewById(R.id.video_view);
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
        mTxlpPlayer.setPlayListener(UserFishingActivity.this);
    }

    private void play() {
        if (null != lives.get(0) && !TextUtils.isEmpty(lives.get(0))) {
//             Log.d("lives", "" + lives.get(0));
            mTxlpPlayer.startPlay(lives.get(0), TXLivePlayer.PLAY_TYPE_LIVE_RTMP);
//            mTxlpPlayer.startPlay(lives.get(0), TXLivePlayer.PLAY_TYPE_LIVE_RTMP_ACC);
            //开启服务--socket连接
            startServices();
        }
    }

    private void startServices() {
        Intent       intent        = new Intent(this, PlayService.class);
        UserInfoBean mUserInfoBean = SharedUtils.getInstance().getObject(ConfigApi.USER_INFO, UserInfoBean.class);
        String       My_fishery_id = mUserInfoBean.getUser().getFisher_id();
        String       access_token  = mUserInfoBean.getAccess_token();
        //wsUrl = "ws://192.168.111.129:9501?fishing_id=67224273&ws_fp_id=1&access_token=L5pnaJx5M2hb6wdguU6NHjRskEK4XcG7";
//        String urlStr = "ws://148.70.13.176:9501?fishing_id=";
        String urlStr = ConfigApi.SOCKETURL + "fishing_id=";
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
        /**
         * 保存控制模式
         */
        SharedPreferencesUtils.setParam(this, "type", mType);
        EventBus.getDefault().unregister(this);
        closeClearDialog();
        mTxlpPlayer.stopPlay(true); // true 代表清除最后一帧画面
        mView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void initListener() {
        btn_close.setOnClickListener(this);
        iv_error.setOnClickListener(this);
        iv_tip_close.setOnClickListener(this);
        iv_follow.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        btn_to_smaller.setOnClickListener(this);
        btn_to_help.setOnClickListener(this);
        tv_recharge.setOnClickListener(this);
        mView.setOnClickListener(this);
        btn_shere.setOnClickListener(this);
        iv_share_cancel.setOnClickListener(this);

        tv_confrim_btn.setOnClickListener(this);
        iv_chat.setOnClickListener(this);

        btn_one.setOnTouchListener(this);
        btn_thr.setOnTouchListener(this);
        btn_fou.setOnTouchListener(this);
        btn_fiv.setOnTouchListener(this);
        btn_two.setOnTouchListener(this);

        tv_guide_close.setOnClickListener(this);

        //输入法到底部的间距(按需求设置)
        final int paddingBottom = DisplayUtil.dp2px(this, 5);
        final int paddings      = DisplayUtil.dp2px(this, 50);

        fl_root_layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                int screenHeight = getWindow().getDecorView().getRootView().getHeight();
//                rl_root.getWindowVisibleDisplayFrame(r);
//                fl_root_layout.getWindowVisibleDisplayFrame(r);
//                //r.top 是状态栏高度
//                int screenHeight = getScreenHeight(AppUtils.getAppContext());
                int softHeight = screenHeight - r.bottom;
//                Log.e("test", "screenHeight:" + screenHeight);
//                Log.e("test", "top:" + r.top);
//                Log.e("test", "bottom:" + r.bottom);
//                Log.e("test", "Size: " + softHeight);
                // 当前视图最外层的高度减去现在所看到的视图的最底部的y坐标
//                int rootInvisibleHeight = fl_root_layout.getRootView() .getHeight() - r.bottom;
//                if (rootInvisibleHeight > 400) {//当输入法高度大于100判定为输入法打开了
                if (softHeight > 400) {//当输入法高度大于100判定为输入法打开了
                    if (et_input_message.hasFocus()) {
                        //软键盘弹出来的时候
//                        int[] location = new int[2];
//                        // 获取scrollToView在窗体的坐标
//                        rl_root.getLocationInWindow(location);
//                        // 计算root滚动高度，使scrollToView在可见区域的底部
//                        int srollHeight = (location[1] + rl_root.getHeight()) - r.bottom;
                        rl_root.setVisibility(View.VISIBLE);
                        rl_root.scrollTo(0, softHeight);
                    }
                } else {//否则判断为输入法隐藏了
                    rl_root.scrollTo(0, 0);
                    rl_root.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 获取导航栏高度(如华为底部导航栏高度)
     *
     * @param context
     * @return
     */
    private int getBottomBarHeight(Context context) {
        int resourceId = 0;
        int rid        = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0) {
            resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    /**
     * 获得屏幕高度(有导航栏)
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager  wm         = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    @Override
    public void showLog(BaseBean param) {
        if (param.getCode() == 200) {
            mTxlpPlayer.stopPlay(true); // true 代表清除最后一帧画面
            mView.onDestroy();
            stopServices();
            this.finish();
        } else {
            ToastUtils.show("请重试");
            mTxlpPlayer.stopPlay(true); // true 代表清除最后一帧画面
            mView.onDestroy();
            stopServices();
            this.finish();
        }
    }

    @Override
    public void onFishingShareDataCallback(FishingShareBean param) {
        if (null == param) {
            return;
        }
        String shareImgUrl = param.getShare_img();
        if (TextUtils.isEmpty(shareImgUrl)) {
            ToastUtils.show("分享链接格式错误");
            return;
        }
        shareToFlatForm(shareImgUrl);
    }

    @Override
    public void onEquipErrorDataCallback(BaseBean param) {
        if (param.getCode() == 200) {
            ToastUtils.show("您的故障已提交，我们正在加急处理，您可以前往其它钓台钓鱼！");
        } else {
            ToastUtils.show(param.getMessage());
        }
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
            startActivity(new Intent(getContext(), LoginActivity.class));
        }
    }


    /**
     * 初始化摇杆控制
     */
    private void initRockerControl() {
        rv_rocker.setCallBackMode(RockerView.CallBackMode.CALL_BACK_MODE_STATE_CHANGE);
        rv_rocker.setOnShakeListener(RockerView.DirectionMode.DIRECTION_8, new RockerView.OnShakeListener() {
            @Override
            public void onStart() {
                checkControl();
                fly_rocker.setBackgroundResource(R.drawable.rocker_center_icon);
            }

            @Override
            public void direction(RockerView.Direction direction) {
                getDirectionByBg(direction);
                getDirection(direction);
            }

            @Override
            public void onFinish() {
                checkControl();
                fly_rocker.setBackgroundColor(Color.TRANSPARENT);
            }
        });
    }

    private void getDirectionByBg(RockerView.Direction direction) {
        switch (direction) {
            case DIRECTION_LEFT://左 上饵
                fly_rocker.setBackgroundResource(R.drawable.rocker_left_icon);
                break;
            case DIRECTION_RIGHT://右  收鱼
                fly_rocker.setBackgroundResource(R.drawable.rocker_right_icon);
                break;
            case DIRECTION_UP://上  抛竿
                fly_rocker.setBackgroundResource(R.drawable.rocker_top_icon);
                break;
            case DIRECTION_DOWN://下   提竿
                fly_rocker.setBackgroundResource(R.drawable.rocker_buttom_icon);
                break;
            case DIRECTION_UP_LEFT://左上
            case DIRECTION_UP_RIGHT://右上
            case DIRECTION_DOWN_LEFT://左下
            case DIRECTION_DOWN_RIGHT://右下
            case DIRECTION_CENTER://中间
                fly_rocker.setBackgroundResource(R.drawable.rocker_center_icon);
            default:
                break;
        }
    }

    private void getDirection(RockerView.Direction direction) {
        checkControl();

        switch (direction) {
            case DIRECTION_LEFT://左 上饵
                mCurindex = 1;
                push(order5_o, order5_o, dtu_id, dtu_apikay);
                break;
            case DIRECTION_RIGHT://右  收鱼
                mCurindex = 2;
                push(order4_o, order4_o, dtu_id, dtu_apikay);
                break;
            case DIRECTION_UP://上   抛竿
                mCurindex = 3;
                push(order1_o, order1_o, dtu_id, dtu_apikay);
                break;
            case DIRECTION_DOWN://下  提竿
                mCurindex = 4;
                push(order2_o, order2_o, dtu_id, dtu_apikay);

                break;
            case DIRECTION_UP_LEFT://左上
            case DIRECTION_UP_RIGHT://右上
            case DIRECTION_DOWN_LEFT://左下
            case DIRECTION_DOWN_RIGHT://右下
            case DIRECTION_CENTER://中间

            default:
                break;
        }
        Log.e("lm", "开始操作" + mCurindex);
    }


    //当前操控的位置  (1 上饵  2收鱼  3提竿  4抛竿  5甩杆)
    private int mCurindex = -1;

    //检查上一次是否有未关闭的操作
    private void checkControl() {
        Log.e("lm", "关闭操作" + mCurindex);
        switch (mCurindex) {
            case 1:
                push(order5_c, order5_c, dtu_id, dtu_apikay);
                break;
            case 2:
                push(order4_c, order4_c, dtu_id, dtu_apikay);
                break;
            case 3:
                push(order1_c, order1_c, dtu_id, dtu_apikay);
                break;
            case 4:
                push(order2_c, order2_c, dtu_id, dtu_apikay);
                break;
            case 5:
                mHandler.removeCallbacks(mRunnable);
                push(order3_c, order3_c, dtu_id, dtu_apikay);
                break;

        }
        mCurindex = -1;
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.btn_one:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    //push(alloff, alloff, dtu_id, dtu_apikay);
                    push(order1_o, order1_o, dtu_id, dtu_apikay);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    push(order1_c, order1_c, dtu_id, dtu_apikay);
                }
                break;
            case R.id.btn_two:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    push(order2_o, order2_o, dtu_id, dtu_apikay);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    push(order2_c, order2_c, dtu_id, dtu_apikay);
                }
                break;
            case R.id.btn_thr:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    push(order3_o, order3_o, dtu_id, dtu_apikay);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    push(order3_c, order3_c, dtu_id, dtu_apikay);
                }
                break;
            case R.id.btn_fou:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    push(order4_o, order4_o, dtu_id, dtu_apikay);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    push(order4_c, order4_c, dtu_id, dtu_apikay);
                }
                break;
            case R.id.btn_fiv:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    push(order5_o, order5_o, dtu_id, dtu_apikay);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    push(order5_c, order5_c, dtu_id, dtu_apikay);
                }
                break;
            default:
                break;
        }
        return false;
    }


    private                GestureDetector mDetector;
    protected static final float           FLIP_DISTANCE = 50;
    private                int             mButtomHeight = 0;
    private                Handler         mHandler      = new Handler();
    private                Runnable        mRunnable     = new Runnable() {
        @Override
        public void run() {
            push(order3_c, order3_c, dtu_id, dtu_apikay);
            mCurindex = -1;
        }
    };

    public int dp2px(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    /**
     * 初始化手势
     */
    private void initGestureDetector() {
        int height = getWindow().getWindowManager().getDefaultDisplay().getHeight();
        mButtomHeight = height - dp2px(150);


        mDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                onMenueCanshow();
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                // TODO Auto-generated method stub

            }


            /**
             *
             * e1 The first down motion event that started the fling. e2 The
             * move motion event that triggered the current onFling.
             */

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.e("lm", "xx" + e2.getX() + "  yy" + e2.getY());

                if (e1.getX() - e2.getX() > FLIP_DISTANCE) {
                    if (e2.getY() > mButtomHeight) {
                        mType = mType == 0 ? 1 : 0;
                        setControlTypeView();
                    } else {
                        toUp(e1, e2);
                    }
                    Log.e("lm", "向左滑...");
                    return true;
                }
                if (e2.getX() - e1.getX() > FLIP_DISTANCE) {
                    if (e2.getY() > mButtomHeight) {
                        mType = mType == 0 ? 1 : 0;
                        setControlTypeView();
                    } else {
                        toUp(e1, e2);
                    }

                    Log.e("lm", "向右滑...");  //忽然觉得这个log好智障...
                    return true;
                }

                if (e1.getY() - e2.getY() > FLIP_DISTANCE) {
                    Log.e("lm", "向上滑...");
                    toUp(e1, e2);
                    return true;
                }
                if (e2.getY() - e1.getY() > FLIP_DISTANCE) {
                    Log.e("lm", "向下滑...");
                    return true;
                }

                Log.d("lm", "xx" + e2.getX() + "  yy" + e2.getY());

                return false;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                // TODO Auto-generated method stub
                return true;
            }
        });
        mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mDetector.onTouchEvent(event);
            }
        });
    }


    private void toUp(MotionEvent e1, MotionEvent e2) {
        if (mType == 0) {
            showMove(e1, e2);
            checkControl();
            mCurindex = 5;
            push(order3_o, order3_o, dtu_id, dtu_apikay);
            mHandler.postDelayed(mRunnable, 3000);
        }
    }

    private MediaPlayer mMediaPlayer;

    //播放甩杆语音
    public void playVoice() {
        mMediaPlayer = MediaPlayer.create(this, R.raw.mast_voice);
        mMediaPlayer.start();
    }


    private Runnable mMoveRunnable = new Runnable() {
        @Override
        public void run() {
            gmv_body.releaseMove();
        }
    };

    private void showMove(MotionEvent e1, MotionEvent e2) {
        gmv_body.setMoveEvent(e1, e2);
        mHandler.removeCallbacks(mMoveRunnable);
        mHandler.postDelayed(mMoveRunnable, 3000);
        playVoice();
    }


    private void setControlTypeView() {
        if (mType == 0) {
            fly_rocker.setVisibility(View.VISIBLE);
            ll_bottom_menu.setVisibility(View.GONE);
        } else {
            fly_rocker.setVisibility(View.GONE);
            ll_bottom_menu.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_shere:
                HideKeyboard(fl_root_layout);
                closeShare();
                mUserFishingActivityPresenter.getFishShareData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, ""));
                break;
            case R.id.iv_share_cancel:
                HideKeyboard(fl_root_layout);
                closeShare();
                break;
            case R.id.btn_to_help:
                HideKeyboard(fl_root_layout);
               /* Intent intent = new Intent(this, WebActivity.class);
                intent.putExtra(ConfigApi.WEB_TITLE, getResources().getString(R.string.user_help));
                intent.putExtra(ConfigApi.WEB_URL, ConfigApi.XSZN_URL);
                startActivity(intent);*/
                if (btn_to_help.isSelected()) {
                    ll_guide.setVisibility(View.GONE);

                } else {
                    ll_guide.setVisibility(View.VISIBLE);
                }
                btn_to_help.setSelected(!btn_to_help.isSelected());
                break;
            case R.id.tv_guide_close:
                ll_guide.setVisibility(View.GONE);
                btn_to_help.setSelected(false);
                break;
            case R.id.iv_error:
                HideKeyboard(fl_root_layout);
                showWorning();
                break;
            case R.id.btn_close:
                HideKeyboard(fl_root_layout);
                finishFishing();
//                String Authorization = SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, "");
//                mUserFishingActivityPresenter.deleteFinshingOut(Authorization, fp_id);
                break;
            case R.id.iv_tip_close:
                HideKeyboard(fl_root_layout);
                ll_msg_tip.setVisibility(View.GONE);
                break;
            case R.id.iv_follow:
                HideKeyboard(fl_root_layout);
                //自己不能给自己点赞
//                ToastUtils.show("点赞");
//                doFever();
                break;
            case R.id.iv_share:
                HideKeyboard(fl_root_layout);
                startActivity(new Intent(this, InviteFriendsActivity.class));
                break;
            case R.id.btn_to_smaller:
                HideKeyboard(fl_root_layout);
                //用户正在钓鱼，判断标记
                SharedUtils.getInstance().putBoolean(ConfigApi.ISFISHING, true);
                initFloatWindow();
                break;
            case R.id.tv_recharge:
                HideKeyboard(fl_root_layout);
                if (null != btn_link && !TextUtils.isEmpty(btn_link) && btn_link.length() > 5) {
                    Intent intentWeb = new Intent(this, WebActivity.class);
                    //intentWeb.putExtra(ConfigApi.WEB_TITLE, getResources().getString(R.string.user_help));
                    intentWeb.putExtra(ConfigApi.WEB_URL, btn_link);
                    startActivity(intentWeb);
                } else if (btn_link.equals("1")) {
                    startActivity(new Intent(this, RechargeActivity.class));
                }
                break;
            case R.id.video_view:
                onMenueCanshow();
                break;
            case R.id.tv_confrim_btn:
                sendMsg("message", et_input_message.getText().toString().trim());
                et_input_message.setText("");
                HideKeyboard(fl_root_layout);
                break;
            case R.id.iv_chat:
                li_chat.setVisibility(View.VISIBLE);
                et_input_message.requestFocus();
                openKeyboard();
                break;
            default:
                break;
        }
    }
    private void onMenueCanshow()
    {
        HideKeyboard(fl_root_layout);
        isMenueCanshow = !isMenueCanshow;
        if (isMenueCanshow) {
//                    ll_top_menu.setVisibility(View.VISIBLE);
            ll_right_menu.setVisibility(View.VISIBLE);
            rl_fish_num.setVisibility(View.VISIBLE);
            badgeVie.setVisibility(View.VISIBLE);
            li_chat.setVisibility(View.VISIBLE);
            iv_chat.setVisibility(View.VISIBLE);
//                    ll_bottom_menu.setVisibility(View.VISIBLE);
        } else {
//                    ll_top_menu.setVisibility(View.GONE);
            ll_right_menu.setVisibility(View.GONE);
            rl_fish_num.setVisibility(View.GONE);
            badgeVie.setVisibility(View.INVISIBLE);
            li_chat.setVisibility(View.INVISIBLE);
            iv_chat.setVisibility(View.INVISIBLE);
//                    ll_bottom_menu.setVisibility(View.GONE);
        }
    }

//    /**
//     * Note： return mDelegate.dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev);
//     */
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        //添加快速点击处理---------
//
//        //点击edittext之外关闭键盘————————
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            View v = getCurrentFocus();
//            if (isShouldHideInput(v, ev)) {
//
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (imm != null) {
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                }
//            }
//            return super.dispatchTouchEvent(ev);
//        }
//        //点击edittext之外关闭键盘————————
//        return super.dispatchTouchEvent(ev);
//
//    }

    private void shareToFlatForm(String shareImgUrl) {
        UMImage image = new UMImage(UserFishingActivity.this, shareImgUrl);//网络图片
        UMImage thumb = new UMImage(this, R.mipmap.app_logo);
        image.setThumb(thumb);
        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        new ShareAction(UserFishingActivity.this)
                .withText("钓鱼赚金币")
                .withMedia(image)
                .setCallback(umShareListener)//回调监听器
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
                .open();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            showDialog("正在加载");
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            dismissDialog();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            dismissDialog();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            dismissDialog();
        }
    };


    private void closeShare() {
        rl_get_Fish.setVisibility(View.GONE);
    }

    private void finishFishing() {
        SharedUtils.getInstance().putBoolean(ConfigApi.ISFISHING, false);
        stopServices();
        finish();
    }

    private void push(final String fileName, final String order, final String device_id, final String apikey) {
        final String orderUrl = "https://api.heclouds.com/cmds?device_id=" + device_id + "&qos=1";
        new AsyncTask<String, Object, Object>() {
            @Override
            protected String doInBackground(String... objects) {
                try {
//                    OkHttpClient client = new OkHttpClient();
//                    MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
//                    InputStream inputStream = new ByteArrayInputStream(new BASE64Encoder().decode("/gUAAP8AmDU="));
//                    RequestBody requestBody = requestBodyCreate(MEDIA_TYPE_MARKDOWN, inputStream);
//                    Request request = new Request.Builder()
//                            .url("https://api.heclouds.com/cmds?device_id=505437446&qos=1")
//                            .post(requestBody)
//                            .addHeader("api-key", "vAcBwxQ5D4HiISRtxDwoQXxE=xI=")
//                            .addHeader("Content-Type", "application/x-www-form-urlencoded")
//                            .build();
                    OkHttpClient client              = new OkHttpClient();
                    MediaType    MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
//                    Log.d("orders", order);
//                    Log.d("orders", String.valueOf(System.currentTimeMillis()));
//                    Log.d("orders", Arrays.toString(longToByte(System.currentTimeMillis())));
//                    Log.d("orders", Long.toHexString(System.currentTimeMillis()));
//                    Log.d("orders", Arrays.toString(hexStringToByte(Long.toHexString(System.currentTimeMillis()))));
//                    Log.d("orders", Arrays.toString(new BASE64Encoder().decode(order)));
//                    Log.d("orders", Arrays.toString(addBytes(new BASE64Encoder().decode(order), longToByte(System.currentTimeMillis()))));

//                    InputStream inputStream = new ByteArrayInputStream(new BASE64Encoder().decode(order));
//                    InputStream inputStream = new ByteArrayInputStream(addBytes(new BASE64Encoder().decode(order), longToByte(System.currentTimeMillis())));
//                    InputStream inputStream = new ByteArrayInputStream(addBytes(new BASE64Encoder().decode(order), hexStringToByte(Long.toHexString(System.currentTimeMillis()))));
                    InputStream inputStream = new ByteArrayInputStream(addBytes(new BASE64Encoder().decode(order), hexStringToByte("00000" + Long.toHexString(System.currentTimeMillis()))));
                    RequestBody requestBody = requestBodyCreate(MEDIA_TYPE_MARKDOWN, inputStream);
                    Request request = new Request.Builder()
                            .url(orderUrl)
                            .post(requestBody)
                            .addHeader("api-key", apikey)
                            .addHeader("Content-Type", "application/x-www-form-urlencoded")
                            .build();

                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(fileName);
    }


    /**
     * @param data1
     * @param data2
     * @return data1 与 data2拼接的结果
     */
    public static byte[] addBytes(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);
        return data3;

    }

    //long类型转成byte数组
    public static byte[] longToByte(long number) {
        long   temp = number;
        byte[] b    = new byte[8];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Long(temp & 0xff).byteValue();
            //将最低位保存在最低位
            temp = temp >> 8; // 向右移8位
        }
        return b;
    }

    /**
     * 16进制字符串转换成字节数组
     *
     * @param hex
     * @return
     */
    public static byte[] hexStringToByte(String hex) {
        byte[] b = new byte[hex.length() / 2];
        int    j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hex.charAt(j++);
            char c1 = hex.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }

    private static int parse(char c) {
        if (c >= 'a') {
            return (c - 'a' + 10) & 0x0f;
        }
        if (c >= 'A') {
            return (c - 'A' + 10) & 0x0f;
        }
        return (c - '0') & 0x0f;

    }


    public RequestBody requestBodyCreate(final MediaType mediaType, final InputStream inputStream) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return mediaType;
            }

            @Override
            public long contentLength() {
                try {
                    return inputStream.available();
                } catch (IOException e) {
                    return 0;
                }
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source = null;
                try {
                    source = Okio.source(inputStream);
                    sink.writeAll(source);
                } finally {
                    Util.closeQuietly(source);
                }
            }
        };
    }

    public static RequestBody create(final MediaType mediaType, final InputStream inputStream) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return mediaType;
            }

            @Override
            public long contentLength() {
                try {
                    return inputStream.available();
                } catch (IOException e) {
                    return 0;
                }
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source = null;
                try {
                    source = Okio.source(inputStream);
                    sink.writeAll(source);
                } finally {
                    Util.closeQuietly(source);
                }
            }
        };
    }

    @Override
    public void onPlayEvent(int event, Bundle bundle) {
        Log.e("bundle", bundle.toString());
        if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {
            ToastUtils.show("网络断开，拉流失败");
            dismissDialog();
            hideProgress();
        } else if (event == TXLiveConstants.PLAY_EVT_GET_MESSAGE) {
            String msg = null;
            try {
                msg = new String(bundle.getByteArray(TXLiveConstants.EVT_GET_MSG), "UTF-8");
                ToastUtils.show(msg);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_END) {
//            ToastUtils.show("直播已经结束，请观看其他频道");
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
            dismissDialog();
            hideProgress();
        }
    }

    @Override
    public void onNetStatus(Bundle bundle) {

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //点击完返回键，执行的动作
//            String Authorization = SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, "");
//            mUserFishingActivityPresenter.deleteFinshingOut(Authorization, fp_id);
            if (rl_get_Fish.getVisibility() == View.VISIBLE) {
                return false;
            }
            finishFishing();
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
