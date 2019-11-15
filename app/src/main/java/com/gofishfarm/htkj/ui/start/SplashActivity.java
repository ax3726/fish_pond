package com.gofishfarm.htkj.ui.start;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.base.BaseActivity;
import com.gofishfarm.htkj.presenter.start.SplashPresenter;
import com.gofishfarm.htkj.ui.main.MainActivity;
import com.gofishfarm.htkj.utils.BASE64Encoder;
import com.gofishfarm.htkj.utils.SharedUtils;
import com.gofishfarm.htkj.view.start.SplashView;

import java.util.Arrays;

import javax.inject.Inject;

/**
 * MrLiu253@163.com
 *
 * @time 2018/7/27
 */

public class SplashActivity extends BaseActivity<SplashView, SplashPresenter> implements SplashView {

    @Inject
    SplashPresenter mSplashPresenter;

    @Override
    public SplashPresenter createPresenter() {
        return this.mSplashPresenter;
    }

    @Override
    public SplashView createView() {
        return this;
    }


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //恢复网络状态
        SharedUtils.getInstance().putBoolean(ConfigApi.CANUSEMOBILENET, false);
        SharedUtils.getInstance().putBoolean(ConfigApi.ISFISHING, false);
        mSplashPresenter.setCountDown();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    /**
     * 跳转首页
     */
    private void setEnd() {
        mSplashPresenter.removeCountDown();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
//        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
    }

    //删除父类调用，禁止返回
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK || super.onKeyDown(keyCode, event);
    }

    @Override
    public void complete(String msg) {

    }

    @Override
    public void showError(String paramString, int code) {

    }

    /**
     * 倒计时结束
     */
    @Override
    public void showCountDown(int intValue) {
        if (intValue == 0) {
            setEnd();
        }
    }

    @Override
    public void onClick(View v) {

    }
}
