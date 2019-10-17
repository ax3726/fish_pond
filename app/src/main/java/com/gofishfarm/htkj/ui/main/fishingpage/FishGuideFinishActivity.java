package com.gofishfarm.htkj.ui.main.fishingpage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.base.BaseActivity;
import com.gofishfarm.htkj.bean.FishDevciceBean;
import com.gofishfarm.htkj.bean.FishGuideFinishBean;
import com.gofishfarm.htkj.bean.OnLookBean;
import com.gofishfarm.htkj.presenter.main.fishingpage.FishGuideFinishActivityPresenter;
import com.gofishfarm.htkj.utils.SharedUtils;
import com.gofishfarm.htkj.view.main.fishingpage.FishGuideFinishActivityView;
import com.hjq.toast.ToastUtils;

import javax.inject.Inject;

public class FishGuideFinishActivity extends BaseActivity<FishGuideFinishActivityView, FishGuideFinishActivityPresenter> implements FishGuideFinishActivityView {

    private ImageView iv_top;
    private ImageView iv_center;
    private TextView tv_bottom;
    private Button btn_one;
    private Button btn_two;
    private String fishery_id = "";
    private String reward = "";

    @Inject
    FishGuideFinishActivityPresenter mFishGuideFinishActivityPresenter;

    @Override
    public FishGuideFinishActivityPresenter createPresenter() {
        return this.mFishGuideFinishActivityPresenter;
    }

    @Override
    public FishGuideFinishActivity createView() {
        return this;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_fish_guide_finish;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        iv_top = (ImageView) findViewById(R.id.iv_top);
        iv_center = (ImageView) findViewById(R.id.iv_center);
        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        btn_one = (Button) findViewById(R.id.btn_one);
        btn_two = (Button) findViewById(R.id.btn_two);

        btn_one.setOnClickListener(this);
        btn_two.setOnClickListener(this);

        Intent intent = getIntent();
        reward = intent.getStringExtra(ConfigApi.FISHGUIDE_REWARD);
        if (!TextUtils.isEmpty(reward)) {
            String guuideCoin = "已经获得 " + reward + " 免费体验时长";
            setfishCoinText(guuideCoin);
        } else {
            tv_bottom.setText("");
        }
//        initData();
//        String guuideCoin = "已经获得 60分钟 免费体验时长";
    }

    private void initData() {
        mFishGuideFinishActivityPresenter.getFishGuideFinishData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION));
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_one:
                startActivity(new Intent(this, FishGuideActivity.class));
                finish();
                break;
            case R.id.btn_two:
                String Authorization = SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, "");
                int status = 1;
                this.mFishGuideFinishActivityPresenter.getFastFishplatData(Authorization, status);
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

    }

    @Override
    public void onFishGuideFinishData(FishGuideFinishBean fishGuideFinishBean) {
        if (null == fishGuideFinishBean) {
            return;
        }

//        String guuideCoin = "已经获得 60分钟 免费体验时长" + fishGuideFinishBean.toString() + "已经获得 60分钟 免费体验时长";
        String guuideCoin = "已经获得 " + fishGuideFinishBean.getReward() + " 免费体验时长";
        setfishCoinText(guuideCoin);

    }

    @Override
    public void onFishDeviceDataCallback(FishDevciceBean fishDevciceBean) {
        if (null == fishDevciceBean) {
            return;
        }
        //保存命令信息，重新新打开时取出
        SharedUtils.getInstance().putObject(ConfigApi.FISHDEVCICEBEAN, fishDevciceBean);
        if (fishDevciceBean.getInfo().equals("0")) {

//            Intent intent = new Intent(this, UserFishNewCommerActivity.class);
            Intent intent = new Intent(this, UserFishingActivity.class);
            intent.putExtra(ConfigApi.FISHDEVCICEBEAN, fishDevciceBean);
            startActivity(intent);
            finish();

        } else if (fishDevciceBean.getInfo().equals("3")) {
            //跳转围观
            //不需要带上id
            goOnLook(fishery_id);

        }
    }

    @Override
    public void onOnLookDataCallback(OnLookBean onLookBean) {
        if (null == onLookBean) {
            return;
        }
        Intent intent = new Intent(this, OnLookActivity.class);
        intent.putExtra("ONLOOKBEAN", onLookBean);
        startActivity(intent);
        finish();
    }

    private void goOnLook(String fishery_id) {
        String Authorization = SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, "");
        this.mFishGuideFinishActivityPresenter.getOnLookBeanData(Authorization, fishery_id, "");
    }

    private void setfishCoinText(String guuideCoin) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(guuideCoin);
        //设置文字的前景色
        //"已经获得 60分钟 免费体验时长"
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#ffffff")), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#FF2F2F")), 5, guuideCoin.length() - 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#ffffff")), guuideCoin.length() - 7, guuideCoin.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_bottom.setText(spannable);
    }
}
