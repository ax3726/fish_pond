package com.gofishfarm.htkj.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

import com.gofishfarm.htkj.App;
import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.base.BaseActivity;
import com.gofishfarm.htkj.bean.UserInfoBean;
import com.gofishfarm.htkj.event.Event;
import com.gofishfarm.htkj.event.RxBus;
import com.gofishfarm.htkj.presenter.main.MainPresenter;
import com.gofishfarm.htkj.ui.login.LoginActivity;
import com.gofishfarm.htkj.utils.SharedUtils;
import com.gofishfarm.htkj.utils.Suspended.WindowUtils;
import com.gofishfarm.htkj.view.main.MainView;
import com.hjq.toast.ToastUtils;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import me.yokeyword.fragmentation.ISupportFragment;

public class MainActivity extends BaseActivity<MainView, MainPresenter> implements MainView, View.OnClickListener {

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;
    private TextView mFisheryTextView;
    private TextView mRankingTextView;
    private TextView mAttentionTextView;
    private TextView mMyTextView;
    private int mPos = 0;
    TextView[] textViews;
    private ConstraintLayout mMainCl;
    private ISupportFragment[] mFragments = new ISupportFragment[4];
    @Inject
    MainPresenter mMainPresenter;

    @Override
    public MainPresenter createPresenter() {
        return this.mMainPresenter;
    }

    @Override
    public MainView createView() {
        return this;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
//        return R.layout.activity_recharge;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {


        mFisheryTextView = findViewById(R.id.main_bottom_fishery);
        mFisheryTextView.setOnClickListener(this);
        mRankingTextView = findViewById(R.id.main_bottom_ranking);
        mRankingTextView.setOnClickListener(this);
        mAttentionTextView = findViewById(R.id.main_bottom_attention);
        mAttentionTextView.setOnClickListener(this);
        mMyTextView = findViewById(R.id.main_bottom_my);
        mMyTextView.setOnClickListener(this);
        mMainCl = findViewById(R.id.activity_main_cl);

        initData();
        initEvent();
    }

    private void initEvent() {
        RxBus.INSTANCE.toFlowable(Event.ShowFragment.class)
                .compose(bindToLifecycle())
                .subscribe((Consumer) new Consumer<Event.ShowFragment>() {
                    @Override
                    public void accept(Event.ShowFragment showFragment) throws Exception {
                        if (showFragment.pos == 0&&showFragment.posVisible==401) {
                            showView(showFragment.pos, mPos);
                        }else {
                            showView(showFragment.pos, 3);
                        }
                    }
                });
    }

    private void initData() {
        textViews = new TextView[]{mFisheryTextView, mRankingTextView, mAttentionTextView, mMyTextView};


        ISupportFragment firstFragment = findFragment(FisherPondFragment.class);
        if (firstFragment == null) {
            mFragments[FIRST] = FisherPondFragment.newInstance();
            mFragments[SECOND] = RankingFragment.newInstance();
            mFragments[THIRD] = AttentionFragment.newInstance();
            mFragments[FOURTH] = MyInfoFragment.newInstance();

            mDelegate.loadMultipleRootFragment(R.id.activity_main_fl, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findFragment(RankingFragment.class);
            mFragments[THIRD] = findFragment(AttentionFragment.class);
            mFragments[FOURTH] = findFragment(MyInfoFragment.class);
        }
        showView(0, mPos);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void complete(String msg) {

    }

    @Override
    public void showError(String paramString, int status_code) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_bottom_fishery:
//                if (getResources() != null)
//                    mMainCl.setBackground(getResources().getDrawable(R.drawable.ic_main_bg));
                showView(0, mPos);
                break;
            case R.id.main_bottom_ranking:
//                if (getResources() != null)
//                    mMainCl.setBackground(null);
                if (SharedUtils.getInstance().getObject(ConfigApi.USER_INFO, UserInfoBean.class) == null) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                } else {
                    showView(1, mPos);
                }
                break;
            case R.id.main_bottom_attention:
//                    mMainCl.setBackground(null);
                if (SharedUtils.getInstance().getObject(ConfigApi.USER_INFO, UserInfoBean.class) == null) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                } else {
                    showView(2, mPos);
                }
                break;
            case R.id.main_bottom_my:
//                mMainCl.setBackground(null);
                if (SharedUtils.getInstance().getObject(ConfigApi.USER_INFO, UserInfoBean.class) == null) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                } else {
                    showView(3, mPos);
                }
                break;
            default:
                break;
        }
    }

    private void showView(int position, int prePosition) {
        mPos = position;
        if (textViews != null) {
            mMainPresenter.setBrightView(position, textViews);
        }
        mDelegate.showHideFragment(mFragments[position], mFragments[prePosition]);
    }

    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @Override
    public void onBackPressedSupport() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                ToastUtils.cancel();
                finish();
            } else {
                TOUCH_TIME = System.currentTimeMillis();
                ToastUtils.show(R.string.press_again_exit);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        WindowUtils.hidePopupWindow();
        super.onDestroy();
        textViews = null;

    }

}
