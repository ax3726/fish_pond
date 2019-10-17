package com.gofishfarm.htkj.ui.myinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.base.BaseActivity;
import com.gofishfarm.htkj.bean.MissionBean;
import com.gofishfarm.htkj.bean.MissionInfoBean;
import com.gofishfarm.htkj.bean.SingnInBean;
import com.gofishfarm.htkj.presenter.myinfo.SignInPresenter;
import com.gofishfarm.htkj.ui.main.fishingpage.RechargeActivity;
import com.gofishfarm.htkj.ui.myinfo.adapter.ExchangeMissionAdapter;
import com.gofishfarm.htkj.ui.webPage.WebActivity;
import com.gofishfarm.htkj.utils.SharedUtils;
import com.gofishfarm.htkj.view.myinfo.SignInView;
import com.gyf.barlibrary.ImmersionBar;
import com.hjq.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/12
 */
public class SignInActivity extends BaseActivity<SignInView, SignInPresenter> implements SignInView {

    private TextView toolbar_right_bt;
    private TextView tv_days;
    private RecyclerView rc_more_task;
    private ImageButton toolbar_left_ib;
    private TextView toolbar_title;
    private ImageView toolbar_right_iv;
    private ImageView toolbar_right_my_iv;
    private View toolbar_v;
    private TextView tv_day1;
    private RelativeLayout rl_day1;
    private TextView tv_day2;
    private RelativeLayout rl_day2;
    private TextView tv_day3;
    private RelativeLayout rl_day3;
    private TextView tv_day4;
    private RelativeLayout rl_day4;
    private TextView tv_day5;
    private RelativeLayout rl_day5;
    private TextView tv_day6;
    private RelativeLayout rl_day6;
    private TextView tv_day7;
    private RelativeLayout rl_day7;

    private List<MissionBean.InfoBean> mInfoBean = new ArrayList<>();
    private ExchangeMissionAdapter adapter;

    @Inject
    SignInPresenter mSignInPresenter;

    @Override
    public SignInPresenter createPresenter() {
        return this.mSignInPresenter;
    }

    @Override
    public SignInView createView() {
        return this;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_sign;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initLayout();
        initAdapter();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        String authorization = SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION);
        if (!TextUtils.isEmpty(authorization)) {
//            showDialog("正在加载");
            mSignInPresenter.getSignIn(authorization);
        } else {
            ToastUtils.show("获取令牌失败");
        }
        mSignInPresenter.getMissionBeabData(authorization);
    }

    private void initAdapter() {
        rc_more_task.setLayoutManager(new LinearLayoutManager(this));
        rc_more_task.setNestedScrollingEnabled(false);
        adapter = new ExchangeMissionAdapter(R.layout.item_coin_task, mInfoBean);
        rc_more_task.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!mInfoBean.get(position).isAccomplished()) {
                    String m_id = mInfoBean.get(position).getM_id();
                    String node = mInfoBean.get(position).getNode();
                    mSignInPresenter.getOnMissionData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION), m_id, node);
                }
            }
        });
    }

    private void initLayout() {
        ImmersionBar.setTitleBar(this, findViewById(R.id.base_toolbar));

        toolbar_left_ib = (ImageButton) findViewById(R.id.toolbar_left_ib);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_right_iv = (ImageView) findViewById(R.id.toolbar_right_iv);
        toolbar_right_my_iv = (ImageView) findViewById(R.id.toolbar_right_my_iv);
        toolbar_right_bt = (TextView) findViewById(R.id.toolbar_right_bt);
        toolbar_v = (View) findViewById(R.id.toolbar_v);
        tv_days = (TextView) findViewById(R.id.tv_days);
        tv_day1 = (TextView) findViewById(R.id.tv_day1);
        rl_day1 = (RelativeLayout) findViewById(R.id.rl_day1);
        tv_day2 = (TextView) findViewById(R.id.tv_day2);
        rl_day2 = (RelativeLayout) findViewById(R.id.rl_day2);
        tv_day3 = (TextView) findViewById(R.id.tv_day3);
        rl_day3 = (RelativeLayout) findViewById(R.id.rl_day3);
        tv_day4 = (TextView) findViewById(R.id.tv_day4);
        rl_day4 = (RelativeLayout) findViewById(R.id.rl_day4);
        tv_day5 = (TextView) findViewById(R.id.tv_day5);
        rl_day5 = (RelativeLayout) findViewById(R.id.rl_day5);
        tv_day6 = (TextView) findViewById(R.id.tv_day6);
        rl_day6 = (RelativeLayout) findViewById(R.id.rl_day6);
        tv_day7 = (TextView) findViewById(R.id.tv_day7);
        rl_day7 = (RelativeLayout) findViewById(R.id.rl_day7);
        rc_more_task = (RecyclerView) findViewById(R.id.rc_more_task);

        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText("签到");
        if (getResources() != null)
            toolbar_right_bt.setTextColor(getResources().getColor(R.color.color_fe7));
        toolbar_right_bt.setVisibility(View.VISIBLE);
        toolbar_right_bt.setText(R.string.sign_in_rules);
    }

    @Override
    protected void initListener() {
        toolbar_right_bt.setOnClickListener(this);
        toolbar_left_ib.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left_ib:
                finish();
                break;
            case R.id.toolbar_right_bt:
                Intent intent = new Intent(this, WebActivity.class);
                intent.putExtra(ConfigApi.WEB_TITLE, getResources().getString(R.string.sign_in_rules));
                intent.putExtra(ConfigApi.WEB_URL, ConfigApi.SIGN_URL);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void complete(String msg) {

    }

    @Override
    public void showError(String paramString, int status_code) {
        dismissDialog();
        ToastUtils.show(paramString);
    }

    @Override
    public void onCallbackResult(SingnInBean param) {
        dismissDialog();
        initSingn(param);

    }

    private void initSingn(SingnInBean param) {
        if (null == param) {
            return;
        }
        if (null == param.getClock_setting()) {
            return;
        }
        for (int i = 0; i < param.getClock_setting().size(); i++) {
            if (null != param.getClock_setting().get(0)) {
                tv_day1.setText("+" + param.getClock_setting().get(0));
            }
            if (null != param.getClock_setting().get(1)) {
                tv_day2.setText("+" + param.getClock_setting().get(1));
            }
            if (null != param.getClock_setting().get(2)) {
                tv_day3.setText("+" + param.getClock_setting().get(2));
            }
            if (null != param.getClock_setting().get(3)) {
                tv_day4.setText("+" + param.getClock_setting().get(3));
            }
            if (null != param.getClock_setting().get(4)) {
                tv_day5.setText("+" + param.getClock_setting().get(4));
            }
            if (null != param.getClock_setting().get(5)) {
                tv_day6.setText("+" + param.getClock_setting().get(5));
            }
            if (null != param.getClock_setting().get(6)) {
                tv_day7.setText("+" + param.getClock_setting().get(6));
            }

            if (i == 0) {
                if (i < (Integer.parseInt(param.getContinuous_times()))) {
                    rl_day1.setBackgroundResource(R.drawable.qd_xz);
                } else {
                    rl_day1.setBackgroundResource(R.drawable.qd_wxz);
                }
            }

            if (i == 1) {
                if (i < (Integer.parseInt(param.getContinuous_times()))) {
                    rl_day2.setBackgroundResource(R.drawable.qd_xz);
                } else {
                    rl_day2.setBackgroundResource(R.drawable.qd_wxz);
                }
            }

            if (i == 2) {
                if (i < (Integer.parseInt(param.getContinuous_times()))) {
                    rl_day3.setBackgroundResource(R.drawable.qd_xz);
                } else {
                    rl_day3.setBackgroundResource(R.drawable.qd_wxz);
                }
            }

            if (i == 3) {
                if (i < (Integer.parseInt(param.getContinuous_times()))) {
                    rl_day4.setBackgroundResource(R.drawable.qd_xz);
                } else {
                    rl_day4.setBackgroundResource(R.drawable.qd_wxz);
                }
            }

            if (i == 4) {
                if (i < (Integer.parseInt(param.getContinuous_times()))) {
                    rl_day5.setBackgroundResource(R.drawable.qd_xz);
                } else {
                    rl_day5.setBackgroundResource(R.drawable.qd_wxz);
                }
            }


            if (i == 5) {
                if (i < (Integer.parseInt(param.getContinuous_times()))) {
                    rl_day6.setBackgroundResource(R.drawable.qd_xz);
                } else {
                    rl_day6.setBackgroundResource(R.drawable.qd_wxz);
                }
            }


            if (i == 6) {
                if (i < (Integer.parseInt(param.getContinuous_times()))) {
                    rl_day7.setBackgroundResource(R.drawable.qd_xz);
                } else {
                    rl_day7.setBackgroundResource(R.drawable.qd_wxz);
                }
            }
        }

        tv_days.setText(param.getContinuous_times() + "天");

    }

    @Override
    public void onMissionCallback(MissionBean param) {
        if (null == param) {
            return;
        }
        if (null == param.getInfo() && param.getInfo().size() <= 0) {
            return;
        }
        if (null != mInfoBean) {
            mInfoBean.clear();
        }
        mInfoBean.addAll(param.getInfo());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDoMissionCallback(MissionInfoBean param) {

        if (null == param) {
            return;
        }
        initData();

        if (param.getInfo().equals("0")) {
            ToastUtils.show("领取成功");
        } else if (param.getInfo().equals("1")) {
            startActivity(new Intent(this, RechargeActivity.class));
        } else if (param.getInfo().equals("2")) {
            startActivity(new Intent(this, InviteFriendsActivity.class));
        }
    }
}
