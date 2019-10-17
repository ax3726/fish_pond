package com.gofishfarm.htkj.ui.myinfo;

import android.os.Bundle;
import android.view.View;

import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.base.BaseActivity;
import com.gofishfarm.htkj.bean.BaseBean;
import com.gofishfarm.htkj.bean.FishingCoinBean;
import com.gofishfarm.htkj.bean.MallBean;
import com.gofishfarm.htkj.bean.MissionBean;
import com.gofishfarm.htkj.bean.MissionInfoBean;
import com.gofishfarm.htkj.presenter.myinfo.FishingCoinPresenter;
import com.gofishfarm.htkj.view.myinfo.FishingCoinView;

import javax.inject.Inject;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/9
 */
public class FishingCoinActivity extends BaseActivity<FishingCoinView, FishingCoinPresenter> implements FishingCoinView {

    @Inject
    FishingCoinPresenter mFishingCoinPresenter;

    @Override
    public FishingCoinPresenter createPresenter() {
        return this.mFishingCoinPresenter;
    }

    @Override
    public FishingCoinView createView() {
        return this;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.base_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ISupportFragment fragment = findFragment(FishingCoinFragment.class);
        if (fragment == null) {
            loadRootFragment(R.id.activity_framelayout, FishingCoinFragment.newInstance());
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void complete(String msg) {

    }

    @Override
    public void showError(String paramString, int status_code) {

    }

    @Override
    public void onCallbackResult(FishingCoinBean param) {

    }

    @Override
    public void onExchangeCallback(BaseBean param) {

    }

    @Override
    public void onMissionCallback(MissionBean param) {

    }

    @Override
    public void onDoMissionCallback(MissionInfoBean param) {

    }

    @Override
    public void onMallBeanDataCallback(MallBean mallBean) {

    }
}
