package com.gofishfarm.htkj.ui.main.fishingpage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.gofishfarm.htkj.App;
import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.base.BaseActivity;
import com.gofishfarm.htkj.bean.BaseBean;
import com.gofishfarm.htkj.bean.ChatMesgBean;
import com.gofishfarm.htkj.bean.FishDevciceBean;
import com.gofishfarm.htkj.bean.FishingShareBean;
import com.gofishfarm.htkj.bean.HelpBean;
import com.gofishfarm.htkj.bean.UserInfoBean;
import com.gofishfarm.htkj.event.PlayMessageEvent;
import com.gofishfarm.htkj.event.WsCloseEvent;
import com.gofishfarm.htkj.image.GlideApp;
import com.gofishfarm.htkj.presenter.main.fishingpage.UserFishingNewCommerPresenter;
import com.gofishfarm.htkj.service.PlayService;
import com.gofishfarm.htkj.ui.feedback.FeedBackActivity;
import com.gofishfarm.htkj.ui.webPage.WebActivity;
import com.gofishfarm.htkj.utils.AppUtils;
import com.gofishfarm.htkj.utils.BASE64Encoder;
import com.gofishfarm.htkj.utils.NetworkUtils;
import com.gofishfarm.htkj.utils.SharedUtils;
import com.gofishfarm.htkj.utils.notificationutils.BroadCastManager;
import com.gofishfarm.htkj.view.main.fishingpage.UserFishingNewCommerView;
import com.gofishfarm.htkj.widget.CompletedView;
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
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.operators.observable.ObservableCount;
import io.reactivex.schedulers.Schedulers;
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

public class UserFishNewCommerActivity extends BaseActivity<UserFishingNewCommerView, UserFishingNewCommerPresenter> implements UserFishingNewCommerView, View.OnClickListener, ITXLivePlayListener {
    private FrameLayout fl_root_layout;
    private Button btn_close;
    private Button btn_to_smaller;
    private LinearLayout ll_top_menu;
    private Button btn_to_help;
    private Toolbar toolbar;
    private TextView tv_msg_tips;
    private TextView tv_recharge;
    private ImageView iv_tip_close;
    private LinearLayout ll_msg_tip;
    private ImageView iv_error;
    private ImageView iv_follow;
    private TextView tv_follow;
    private ImageView iv_onlookers;
    private TextView tv_onlookers;
    private ImageView iv_share;
    private LinearLayout ll_right_menu;

    private RelativeLayout rl_fish_num;
    private RelativeLayout rl_fish_num_layout;

    private ImageView iv_guide;
    private RecyclerView recl_chat;
    private LinearLayout li_chat;
    private LinearLayout ll_left_menu;
    private TextView tv_guide_content;
    private TextView tv_guide_close;
    private ConstraintLayout ll_guide;
    private CompletedView completedview_step1_1;
    private ConstraintLayout cl_step1;
    private CompletedView completedview_step2_1;
    private CompletedView completedview_step2_2;
    private CompletedView completedview_step2_3;
    private LinearLayout ll_step2;
    private CompletedView completedview_step3_1;
    private CompletedView completedview_step3_2;
    private ConstraintLayout cl_step3;
    private CompletedView completedview_step4_1;
    private CompletedView completedview_step4_2;
    private ConstraintLayout cl_step4;
    private CompletedView completedview_step5_1;
    private CompletedView completedview_step5_2;
    private ConstraintLayout cl_step5;
    private CompletedView completedview_step6_1;
    private CompletedView completedview_step6_2;
    private CompletedView completedview_step6_3;
    private ConstraintLayout cl_step6;
    private ImageView iv_chat;
    private TextView tv_get_fish_coin;
    private RelativeLayout rl_share_center;
    private Button btn_shere;
    private ImageView iv_share_cancel;
    private RelativeLayout rl_get_Fish;
    private EditText et_input_message;
    private TextView tv_confrim_btn;
    private LinearLayout ll_input_view;
    private RelativeLayout rl_root;

    private ConstraintLayout cl_user_minfo;
    private TextView tv_rank_num;
    private TextView tv_fisher_name;
    private TextView tv_yutang;
    private CircleImageView civ_fisher_imag;


    private TXCloudVideoView mView;
    private TXLivePlayer mTxlpPlayer;

    private GsonBuilder builder;
    private Gson gson;
    private boolean isMenueCanshow = true;
    private com.gofishfarm.htkj.widget.iosalert.AlertDialog myDialog;//故障确认弹窗
    private AlertDialog tipDialog;
    private QBadgeView badgeVie;
    private ChatAdapter adapter;
    private FixSizeLinkedList<ChatMesgBean> datas = new FixSizeLinkedList<>(100);

    private String content = "";
    private String btn_link = "";
    private String btn_name = "";

    private String send0 = "";
    private String send1 = "";
    private String send3 = "";
    private String send4 = "";
    private String send5 = "";
    private String send6 = "";
    private String send7 = "";
    private String send8 = "";
    private String send9 = "";
    private String send10 = "";

    private FishDevciceBean mFishDevciceBean;
    private List<String> lives = new ArrayList<>();
    private String Playlives;
    private String fp_id;
    private String dtu_id;
    private String dtu_apikay;
    //用户头像信息
    private String avatar;
    private String nick_name;
    private String rank;
    private String fishery_name;

    private String fish_integration;//获得金币

    private String ws_likes = "0";
    private String ws_onlook = "0";
    private String ws_fish_num = "0";

    private int currentState = 1;//默认状态是 1
    private int currentbtn = 0;//默认状态是 0
    //指南
    private String tiro_prompt = "";
    private String now_cmd = "";// 当前指令位置 03复位，04上饵，05抖饵，06垂钓，07刺鱼，08提鱼，09抄鱼，0a摘鱼，02收鱼成功
    private String now_status = "";// 指令状态 00 下发 01 指令进行中 02 已完成
    private int now_mode = 6; // 6 默认状态
    private boolean isReset; // 是否是复位的状态
    private boolean isInited = false; // 页面是否初始化完毕


    private boolean isPushed;//按钮是否按下，按下这不能够继续操作，除非限位已到或者复位了操作

    @Inject
    UserFishingNewCommerPresenter mUserFishingNewCommerPresenter;

    @Override
    public UserFishingNewCommerPresenter createPresenter() {
        return this.mUserFishingNewCommerPresenter;
    }

    @Override
    public UserFishingNewCommerView createView() {
        return this;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_user_fish_new_commer;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setcaCloaseKeyBord(false);
        EventBus.getDefault().register(this);
        initGson();
        initViews();
        initplayer();
        setBottomMenu(now_mode);
        initData();
        myDialog = new com.gofishfarm.htkj.widget.iosalert.AlertDialog(this).builder();
    }

    private void setBottomMenu(int now_mode) {
        completedview_step1_1.setProgress(0);
        completedview_step2_1.setProgress(0);
        completedview_step2_2.setProgress(0);
        completedview_step2_3.setProgress(0);
        completedview_step3_1.setProgress(0);
        completedview_step3_2.setProgress(0);
        completedview_step4_1.setProgress(0);
        completedview_step4_2.setProgress(0);
        completedview_step5_1.setProgress(0);
        completedview_step5_2.setProgress(0);
        completedview_step6_1.setProgress(0);
        completedview_step6_2.setProgress(0);
        completedview_step6_3.setProgress(0);
        if (!isReset) {
            completedview_step1_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
            completedview_step2_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
            completedview_step2_2.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
            completedview_step2_3.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
            completedview_step3_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
            completedview_step3_2.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
            completedview_step4_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
            completedview_step4_2.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
            completedview_step5_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
            completedview_step5_2.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
            completedview_step6_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
            completedview_step6_2.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
            completedview_step6_3.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
        } else {
            completedview_step1_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
            completedview_step2_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
            completedview_step2_2.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
            completedview_step2_3.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
            completedview_step3_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
            completedview_step3_2.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
            completedview_step4_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
            completedview_step4_2.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
            completedview_step5_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
            completedview_step5_2.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
            completedview_step6_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
            completedview_step6_2.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
            completedview_step6_3.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
        }
        //设置对应步骤的按钮可见不可见
        setBtnInVisiable(now_mode);
    }


    private void initViews() {
        //让虚拟键盘一直不显示
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
//        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE;
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        window.setAttributes(params);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImmersionBar.setTitleBar(this, toolbar);
        fl_root_layout = findViewById(R.id.fl_root_layout);
        mView = (TXCloudVideoView) findViewById(R.id.video_view);
        btn_close = (Button) findViewById(R.id.btn_close);
        btn_to_smaller = (Button) findViewById(R.id.btn_to_smaller);
        ll_top_menu = (LinearLayout) findViewById(R.id.ll_top_menu);
        btn_to_help = (Button) findViewById(R.id.btn_to_help);
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

        rl_fish_num = (RelativeLayout) findViewById(R.id.rl_fish_num);
        rl_fish_num_layout = (RelativeLayout) findViewById(R.id.rl_fish_num_layout);

        btn_close = (Button) findViewById(R.id.btn_close);
        ll_top_menu = (LinearLayout) findViewById(R.id.ll_top_menu);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_msg_tips = (TextView) findViewById(R.id.tv_msg_tips);
        tv_recharge = (TextView) findViewById(R.id.tv_recharge);
        ll_msg_tip = (LinearLayout) findViewById(R.id.ll_msg_tip);
        recl_chat = (RecyclerView) findViewById(R.id.recl_chat);
        li_chat = (LinearLayout) findViewById(R.id.li_chat);
        iv_follow = (ImageView) findViewById(R.id.iv_follow);
        tv_follow = (TextView) findViewById(R.id.tv_follow);
        iv_onlookers = (ImageView) findViewById(R.id.iv_onlookers);
        tv_onlookers = (TextView) findViewById(R.id.tv_onlookers);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        ll_right_menu = (LinearLayout) findViewById(R.id.ll_right_menu);
        iv_guide = (ImageView) findViewById(R.id.iv_guide);
        iv_error = (ImageView) findViewById(R.id.iv_error);
        ll_left_menu = (LinearLayout) findViewById(R.id.ll_left_menu);
        tv_guide_content = (TextView) findViewById(R.id.tv_guide_content);
        tv_guide_close = (TextView) findViewById(R.id.tv_guide_close);
        ll_guide = (ConstraintLayout) findViewById(R.id.ll_guide);
        completedview_step1_1 = (CompletedView) findViewById(R.id.completedview_step1_1);
        cl_step1 = (ConstraintLayout) findViewById(R.id.cl_step1);
        completedview_step2_1 = (CompletedView) findViewById(R.id.completedview_step2_1);
        completedview_step2_2 = (CompletedView) findViewById(R.id.completedview_step2_2);
        completedview_step2_3 = (CompletedView) findViewById(R.id.completedview_step2_3);
        ll_step2 = (LinearLayout) findViewById(R.id.ll_step2);
        completedview_step3_1 = (CompletedView) findViewById(R.id.completedview_step3_1);
        completedview_step3_2 = (CompletedView) findViewById(R.id.completedview_step3_2);
        cl_step3 = (ConstraintLayout) findViewById(R.id.cl_step3);
        completedview_step4_1 = (CompletedView) findViewById(R.id.completedview_step4_1);
        completedview_step4_2 = (CompletedView) findViewById(R.id.completedview_step4_2);
        cl_step4 = (ConstraintLayout) findViewById(R.id.cl_step4);
        completedview_step5_1 = (CompletedView) findViewById(R.id.completedview_step5_1);
        completedview_step5_2 = (CompletedView) findViewById(R.id.completedview_step5_2);
        cl_step5 = (ConstraintLayout) findViewById(R.id.cl_step5);
        completedview_step6_1 = (CompletedView) findViewById(R.id.completedview_step6_1);
        completedview_step6_2 = (CompletedView) findViewById(R.id.completedview_step6_2);
        completedview_step6_3 = (CompletedView) findViewById(R.id.completedview_step6_3);
        cl_step6 = (ConstraintLayout) findViewById(R.id.cl_step6);
        rl_fish_num = (RelativeLayout) findViewById(R.id.rl_fish_num);
        rl_fish_num_layout = (RelativeLayout) findViewById(R.id.rl_fish_num_layout);
        iv_chat = (ImageView) findViewById(R.id.iv_chat);
        tv_get_fish_coin = (TextView) findViewById(R.id.tv_get_fish_coin);
        rl_share_center = (RelativeLayout) findViewById(R.id.rl_share_center);
        btn_shere = (Button) findViewById(R.id.btn_shere);
        iv_share_cancel = (ImageView) findViewById(R.id.iv_share_cancel);
        rl_get_Fish = (RelativeLayout) findViewById(R.id.rl_get_Fish);
        et_input_message = (EditText) findViewById(R.id.et_input_message);
        tv_confrim_btn = (TextView) findViewById(R.id.tv_confrim_btn);
        ll_input_view = (LinearLayout) findViewById(R.id.ll_input_view);
        rl_root = (RelativeLayout) findViewById(R.id.rl_root);

        cl_user_minfo = findViewById(R.id.cl_user_minfo);
        tv_rank_num = findViewById(R.id.tv_rank_num);
        tv_fisher_name = findViewById(R.id.tv_fisher_name);
        tv_yutang = findViewById(R.id.tv_yutang);
        civ_fisher_imag = findViewById(R.id.civ_fisher_imag);

        iv_tip_close.setOnClickListener(this);
        tv_guide_close.setOnClickListener(this);
        mView.setOnClickListener(this);
        iv_share_cancel.setOnClickListener(this);
        btn_close.setOnClickListener(this);
        tv_recharge.setOnClickListener(this);
        li_chat.setOnClickListener(this);
        iv_follow.setOnClickListener(this);
        iv_guide.setOnClickListener(this);
        iv_error.setOnClickListener(this);
        btn_to_help.setOnClickListener(this);
        completedview_step1_1.setOnClickListener(this);
        completedview_step2_1.setOnClickListener(this);
        completedview_step2_2.setOnClickListener(this);
        completedview_step2_3.setOnClickListener(this);
        completedview_step3_1.setOnClickListener(this);
        completedview_step3_2.setOnClickListener(this);
        completedview_step4_1.setOnClickListener(this);
        completedview_step4_2.setOnClickListener(this);
        completedview_step5_1.setOnClickListener(this);
        completedview_step5_2.setOnClickListener(this);
        completedview_step6_1.setOnClickListener(this);
        completedview_step6_2.setOnClickListener(this);
        completedview_step6_3.setOnClickListener(this);
        iv_chat.setOnClickListener(this);
        btn_shere.setOnClickListener(this);
        rl_get_Fish.setOnClickListener(this);
        tv_confrim_btn.setOnClickListener(this);

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

        //输入法到底部的间距(按需求设置)
        final int paddingBottom = DisplayUtil.dp2px(this, 5);
        final int paddings = DisplayUtil.dp2px(this, 50);

        fl_root_layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                int screenHeight = getWindow().getDecorView().getRootView().getHeight();
                int softHeight = screenHeight - r.bottom;
                if (softHeight > 400) {//当输入法高度大于100判定为输入法打开了
                    if (et_input_message.hasFocus()) {
                        //软键盘弹出来的时候
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

        send0 = mFishDevciceBean.getCommands().getSend0();
        send1 = mFishDevciceBean.getCommands().getSend1();
        send3 = mFishDevciceBean.getCommands().getSend3();
        send4 = mFishDevciceBean.getCommands().getSend4();
        send5 = mFishDevciceBean.getCommands().getSend5();
        send6 = mFishDevciceBean.getCommands().getSend6();
        send6 = mFishDevciceBean.getCommands().getSend6();
        send7 = mFishDevciceBean.getCommands().getSend7();
        send8 = mFishDevciceBean.getCommands().getSend8();
        send9 = mFishDevciceBean.getCommands().getSend9();
        send10 = mFishDevciceBean.getCommands().getSend10();

        lives = mFishDevciceBean.getLives();

        if (!NetworkUtils.isWifiConnected(this)
                && NetworkUtils.isMobileConnected(this)) {
            ToastUtils.show("您正在使用手机流量，请尽量切换至WiFi环境！");
        }
        showProgress(getString(R.string.video_load));
        play();
    }

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
        mTxlpPlayer.setPlayListener(this);
    }

    private void play() {
        if (null != lives.get(0) && !TextUtils.isEmpty(lives.get(0))) {
             Log.d("lives", "" + lives.get(0));
            mTxlpPlayer.startPlay(lives.get(0), TXLivePlayer.PLAY_TYPE_LIVE_RTMP);
            //开启服务--socket连接
            startServices();
        }
    }

    //点赞
    private void doFever() {
        Intent intent = new Intent();
        intent.setAction("sendMsg");
        intent.putExtra("op_type", "like");
        BroadCastManager.getInstance().sendBroadCast(this, intent);
    }

    //发送指令时操作（3 复位，11 重提）
    private void sentBtnOrder(int cmd_btn) {
        Intent intent = new Intent();
        intent.setAction("sendMsg");
        intent.putExtra("op_type", "send_cmd_btn");
        intent.putExtra("cmd_btn", cmd_btn);
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
        //钓鱼渔币奖励
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
                //钓到一条鱼
                showShare();
                initalState();
            }
        }

        //状态改变
//        if (event.getNow_mode() != 0 && event.getNow_mode() != now_mode) {
        if (event.getNow_mode() != 0) {
            now_mode = event.getNow_mode();
            doRecovery();
        }
        //按钮的动作执行完毕
        if (!TextUtils.isEmpty(event.getNow_status())) {
//        if (!TextUtils.isEmpty(event.getNow_status()) && !event.getNow_status().equals(now_status)) {
            now_status = event.getNow_status();
            if (now_status.equals("02")) {
                //收到状态完成，就关闭30秒的定时器
                cancelQuery();
                if (isReset) {
                    //如果此时是复位按钮在操作
                    if (event.getNow_cmd().equals("03")) {
                        //处理复位的情况
                        isReset = false;
                        setBottomMenu(now_mode);
                    }
                } else {
                    doRecovery();
                }
            }

        }
        //指南提示框
        if (!TextUtils.isEmpty(event.getTiro_prompt())) {
            if (isInited) {
                //视频的加载进度弹窗显示完毕才能显示
                ll_guide.setVisibility(View.VISIBLE);
            }
            tiro_prompt = event.getTiro_prompt();
            tv_guide_content.setText(tiro_prompt);
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


    private void startServices() {
        Intent intent = new Intent(this, PlayService.class);
        UserInfoBean mUserInfoBean = SharedUtils.getInstance().getObject(ConfigApi.USER_INFO, UserInfoBean.class);
        String My_fishery_id = mUserInfoBean.getUser().getFisher_id();
        String access_token = mUserInfoBean.getAccess_token();
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

    public void closeClearDialog() {
        if (null != tipDialog && tipDialog.isShowing()) {
            tipDialog.dismiss();
        }
        if (null != tipDialog) {
            tipDialog = null;
        }
    }

    public void showWorning() {
        myDialog.setGone().setMsg("设备出故障了吗，请立即上报！").setNegativeButton("取消", null).setPositiveButton("上报", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserFishNewCommerActivity.this, FeedBackActivity.class);
                intent.putExtra("fp_id", Integer.parseInt(fp_id));
                startActivity(intent);
            }
        }).show();
    }

    private void shareToFlatForm(String shareImgUrl) {
        UMImage image = new UMImage(UserFishNewCommerActivity.this, shareImgUrl);//网络图片
        UMImage thumb = new UMImage(this, R.mipmap.app_logo);
        image.setThumb(thumb);
        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        new ShareAction(UserFishNewCommerActivity.this)
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
        stopServices();
        finish();
    }

    private void initGson() {
        if (null == builder) {
            builder = new GsonBuilder();
        }
        if (null == gson) {
            gson = builder.create();
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View v) {
        if (!(v.getId() == R.id.iv_chat) && !(v.getId() == R.id.tv_confrim_btn)) {
            HideKeyboard(fl_root_layout);
        }
        switch (v.getId()) {
            case R.id.btn_close:
                finishFishing();
                break;
            case R.id.tv_recharge:
                if (null != btn_link && !TextUtils.isEmpty(btn_link) && btn_link.length() > 5) {
                    Intent intentWeb = new Intent(this, WebActivity.class);
                    //intentWeb.putExtra(ConfigApi.WEB_TITLE, getResources().getString(R.string.user_help));
                    intentWeb.putExtra(ConfigApi.WEB_URL, btn_link);
                    startActivity(intentWeb);
                } else if (btn_link.equals("1")) {
                    startActivity(new Intent(this, RechargeActivity.class));
                }
                break;
            case R.id.li_chat:

                break;
            case R.id.iv_follow:
                break;
            case R.id.tv_guide_close:
                ll_guide.setVisibility(View.GONE);
                break;
            case R.id.iv_guide:
                if (ll_guide.getVisibility() == View.VISIBLE) {
                    ll_guide.setVisibility(View.GONE);
                } else {
                    showGuide();
                }
                break;
            case R.id.btn_to_help://复位
                initalState();
                break;
//      ├─send3	string	是	复位指令binary流经过base64加密
//　 　　├─send4	string	是	上饵指令binary流经过base64加密
//　 　　├─send5	string	是	抖饵指令binary流经过base64加密
//　 　　├─send6	string	是	垂钓指令binary流经过base64加密 抛竿
//　 　　├─send7	string	是	刺鱼指令binary流经过base64加密 甩干
//　 　　├─send8	string	是	提鱼指令binary流经过base64加密
//　 　　├─send9	string	是	抄鱼指令binary流经过base64加密
//　 　　└─send10	string	是	摘鱼指令binary流经过base64加密
            case R.id.completedview_step1_1:
                //保存当前按下的操作按钮，用来处理5-1,6_3的状态
                doAction(R.id.completedview_step1_1, completedview_step1_1, send4, ConfigApi.timstr_shanger);
                break;
            case R.id.completedview_step2_1:
                doAction(R.id.completedview_step2_1, completedview_step2_1, send4, ConfigApi.timstr_shanger);
                break;
            case R.id.completedview_step2_2:
                doAction(R.id.completedview_step2_2, completedview_step2_2, send6, ConfigApi.timstr_paogan);
                break;
            case R.id.completedview_step2_3:
                doAction(R.id.completedview_step2_3, completedview_step2_3, send5, ConfigApi.timstr_douer);
                break;
            case R.id.completedview_step3_1:
                doAction(R.id.completedview_step3_1, completedview_step3_1, send4, ConfigApi.timstr_shanger);
                break;
            case R.id.completedview_step3_2:
                doAction(R.id.completedview_step3_2, completedview_step3_2, send7, ConfigApi.timstr_shuaigan);
                break;
            case R.id.completedview_step4_1:
                doAction(R.id.completedview_step4_1, completedview_step4_1, send4, ConfigApi.timstr_shanger);
                break;
            case R.id.completedview_step4_2:
                doAction(R.id.completedview_step4_2, completedview_step4_2, send8, ConfigApi.timstr_tiyu);
                break;
            case R.id.completedview_step5_1:
                //重提发送的是复位指令
                doAction(R.id.completedview_step5_1, completedview_step5_1, send3, ConfigApi.timstr_chongti);
                break;
            case R.id.completedview_step5_2:
                doAction(R.id.completedview_step5_2, completedview_step5_2, send9, ConfigApi.timstr_chaoyu);
                break;
            case R.id.completedview_step6_1:
                //重提发送的是复位指令
                doAction(R.id.completedview_step6_1, completedview_step6_1, send3, ConfigApi.timstr_chongti);
                break;
            case R.id.completedview_step6_2:
                doAction(R.id.completedview_step6_2, completedview_step6_2, send10, ConfigApi.timstr_zhaiyu);
                break;
            case R.id.completedview_step6_3:
                doAction(R.id.completedview_step6_3, completedview_step6_3, send9, ConfigApi.timstr_chaoyu);
                break;

            case R.id.iv_tip_close:
                ll_msg_tip.setVisibility(View.GONE);
                break;
            case R.id.iv_error:
                showWorning();
                break;
            case R.id.iv_chat:
                li_chat.setVisibility(View.VISIBLE);
                et_input_message.requestFocus();
                openKeyboard();
                break;
            case R.id.btn_shere:
                closeShare();
                mUserFishingNewCommerPresenter.getFishShareData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, ""));
                break;
            case R.id.iv_share_cancel:
                closeShare();
                break;
            case R.id.rl_get_Fish:

                break;
            case R.id.video_view:
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
                break;
            case R.id.tv_confrim_btn:
                sendMsg("message", et_input_message.getText().toString().trim());
                et_input_message.setText("");
                HideKeyboard(fl_root_layout);
                break;
            default:
                break;
        }
    }

    /**
     * 按下复位按钮/收到鱼成功
     */
    private void initalState() {
        cancel();
        currentbtn = 0;
        progress = 0;
        isReset = true;
        setBottomMenu(now_mode);
        sentBtnOrder(3);//复位
        push(send3, send3, dtu_id, dtu_apikay);
    }

    private static Disposable mDisposable;
    private static Disposable mDisposableQuery;
    private int progress = 0;

    private void doAction(final int completedview_step, final CompletedView btn, final String order, final int timstr) {
        //如果当前按钮没有释放掉，则不能继续点击其他按钮，除非被复位
        if (currentbtn != 0 || isReset) {
            return;
        }
        //设置当前被操作的按钮的id
        currentbtn = completedview_step;
        if (currentbtn == R.id.completedview_step5_1 || currentbtn == R.id.completedview_step6_1) {
            sentBtnOrder(11);//复位
        }
        //设置其它按钮不可操作
        setBtnselect(currentbtn);
        //如果30秒定时器为null，开启
        if (null == mDisposableQuery && (null != mDisposableQuery && mDisposableQuery.isDisposed())) {
            startQuery();
        }
        Observable.interval(1, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposable = disposable;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        progress++;
                        btn.setProgress((int) (95 / timstr * progress));
                        Log.e("prog", ((int) (95 / timstr * progress)) + "");
                        if (progress >= timstr) {
                            cancel();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        cancel();
                    }

                    @Override
                    public void onComplete() {
                        cancel();
                    }
                });
        //向设备发送对应指令
        push(order, order, dtu_id, dtu_apikay);
    }

    /**
     * 每隔30秒查询一次
     */
    private void startQuery() {
        Observable.interval(30, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposableQuery = disposable;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        //查询按钮状态
                        push(send0, send0, dtu_id, dtu_apikay);
                    }

                    @Override
                    public void onError(Throwable e) {
                        cancelQuery();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void cancelQuery() {
        if (mDisposableQuery != null && !mDisposableQuery.isDisposed()) {
            mDisposableQuery.dispose();
        }
    }

    private void cancelCloseQuery() {
        if (mDisposableQuery != null && !mDisposableQuery.isDisposed()) {
            mDisposableQuery.dispose();
            mDisposableQuery = null;
        }
    }

    /**
     * 取消订阅，定时器取消
     */
    public void cancel() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    /**
     * 按钮按下，按钮不能点击
     *
     * @param id
     */
    private void setBtnselect(int id) {
        switch (id) {
            case R.id.completedview_step1_1:
                completedview_step1_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
                break;
            case R.id.completedview_step2_1:
                completedview_step2_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
                completedview_step2_2.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
                completedview_step2_3.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
                break;
            case R.id.completedview_step2_2:
                completedview_step2_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
                completedview_step2_2.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
                completedview_step2_3.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
                break;
            case R.id.completedview_step2_3:
                completedview_step2_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
                completedview_step2_2.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
                completedview_step2_3.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
                break;
            case R.id.completedview_step3_1:
                completedview_step3_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
                completedview_step3_2.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
                break;
            case R.id.completedview_step3_2:
                completedview_step3_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
                completedview_step3_2.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
                break;
            case R.id.completedview_step4_1:
                completedview_step4_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
                completedview_step4_2.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
                break;
            case R.id.completedview_step4_2:
                completedview_step4_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
                completedview_step4_2.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
                break;
            case R.id.completedview_step5_1:
                completedview_step5_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
                completedview_step5_2.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
                break;
            case R.id.completedview_step5_2:
                completedview_step5_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
                completedview_step5_2.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
                break;
            case R.id.completedview_step6_1:
                completedview_step6_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
                completedview_step6_2.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
                completedview_step6_3.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
                break;
            case R.id.completedview_step6_2:
                completedview_step6_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
                completedview_step6_2.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
                completedview_step6_3.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
                break;
            case R.id.completedview_step6_3:
                completedview_step6_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
                completedview_step6_2.setCircleBg(getResources().getColor(R.color.progress_btn_bg_grey));
                completedview_step6_3.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
                break;
            default:
                break;
        }
    }

    /**
     * 恢复按钮的状态
     */
    private void resetBtnState() {
        currentbtn = 0;
        progress = 0;
        setBtnInVisiable(now_mode);
        switch (now_mode) {
            case 6:
                completedview_step1_1.setProgress(0);
                completedview_step1_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
                break;
            case 1:
                completedview_step2_1.setProgress(0);
                completedview_step2_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
                completedview_step2_2.setProgress(0);
                completedview_step2_2.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
                completedview_step2_3.setProgress(0);
                completedview_step2_3.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
                break;
            case 2:
                completedview_step3_1.setProgress(0);
                completedview_step3_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
                completedview_step3_2.setProgress(0);
                completedview_step3_2.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
                break;
            case 3:
                completedview_step4_1.setProgress(0);
                completedview_step4_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
                completedview_step4_2.setProgress(0);
                completedview_step4_2.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
                break;
            case 4:
                completedview_step5_1.setProgress(0);
                completedview_step5_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
                completedview_step5_2.setProgress(0);
                completedview_step5_2.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
                break;
            case 5:
                completedview_step6_1.setProgress(0);
                completedview_step6_1.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
                completedview_step6_2.setProgress(0);
                completedview_step6_2.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
                completedview_step6_3.setProgress(0);
                completedview_step6_3.setCircleBg(getResources().getColor(R.color.progress_btn_bg_yellow));
                break;
            default:
                break;
        }
    }

    /**
     * 设置按钮全部不可见
     *
     * @param now_mode
     */
    private void setBtnInVisiable(int now_mode) {
        if (now_mode == 6) {
            cl_step1.setVisibility(View.VISIBLE);
        } else {
            cl_step1.setVisibility(View.GONE);
        }
        if (now_mode == 1) {
            ll_step2.setVisibility(View.VISIBLE);
        } else {
            ll_step2.setVisibility(View.GONE);
        }
        if (now_mode == 2) {
            cl_step3.setVisibility(View.VISIBLE);
        } else {
            cl_step3.setVisibility(View.GONE);
        }
        if (now_mode == 3) {
            cl_step4.setVisibility(View.VISIBLE);
        } else {
            cl_step4.setVisibility(View.GONE);
        }
        if (now_mode == 4) {
            cl_step5.setVisibility(View.VISIBLE);
        } else {
            cl_step5.setVisibility(View.GONE);
        }
        if (now_mode == 5) {
            cl_step6.setVisibility(View.VISIBLE);
        } else {
            cl_step6.setVisibility(View.GONE);
        }
    }

    /**
     * 执行复位操作
     */
    private void doRecovery() {
        cancel();
        resetBtnState();
    }

    //指南
    private void showGuide() {
        //请求网络
        mUserFishingNewCommerPresenter.getHelpBean(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, ""), 2, Integer.parseInt(fp_id));
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
    public void onHelpBeanDataCallback(HelpBean param) {
        if (null == param) {
            return;
        }
        if (!TextUtils.isEmpty(param.getTiro_prompt())) {
            ll_guide.setVisibility(View.VISIBLE);
            tv_guide_content.setText(param.getTiro_prompt());
        }
    }

    @Override
    public void onEquipErrorDataCallback(BaseBean param) {

    }

    @Override
    public void onPlayEvent(int event, Bundle bundle) {
        Log.e("bundle",bundle.toString());
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
            isInited = true;
            if (!TextUtils.isEmpty(tiro_prompt)) {
                ll_guide.setVisibility(View.VISIBLE);
                tv_guide_content.setText(tiro_prompt);
            }
            dismissDialog();
            hideProgress();
        }
    }

    @Override
    public void complete(String msg) {

    }

    @Override
    public void showError(String paramString, int status_code) {
        ToastUtils.show(paramString);
    }


    @Override
    public void onNetStatus(Bundle bundle) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) and run LayoutCreator again
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
    protected void onDestroy() {
        //钓鱼结束，发送复位指令
        push(send3, send3, dtu_id, dtu_apikay);
        cancelCloseQuery();
        cancel();
        EventBus.getDefault().unregister(this);
        closeClearDialog();
        mTxlpPlayer.stopPlay(true); // true 代表清除最后一帧画面
        mView.onDestroy();
        super.onDestroy();
    }

    private void push(final String fileName, final String order, final String device_id, final String apikey) {
        final String orderUrl = "https://api.heclouds.com/cmds?device_id=" + device_id + "&qos=1";
        new AsyncTask<String, Object, Object>() {
            @Override
            protected String doInBackground(String... objects) {
                try {
                    OkHttpClient client = new OkHttpClient();
                    MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
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
        long temp = number;
        byte[] b = new byte[8];
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
        int j = 0;
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //点击完返回键，执行的动作
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
