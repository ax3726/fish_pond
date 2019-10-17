package com.gofishfarm.htkj.ui.login;

import android.os.Bundle;
import android.view.View;

import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.base.BaseActivity;
import com.gofishfarm.htkj.bean.SmsBean;
import com.gofishfarm.htkj.bean.UserInfoBean;
import com.gofishfarm.htkj.presenter.login.LoginPresenter;
import com.gofishfarm.htkj.view.login.LoginView;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import javax.inject.Inject;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/3
 */
public class LoginActivity extends BaseActivity<LoginView,LoginPresenter>implements LoginView{


    @Inject
    LoginPresenter mLoginPresenter;

    @Override
    public LoginPresenter createPresenter() {
        return this.mLoginPresenter;
    }

    @Override
    public LoginView createView() {
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
        ISupportFragment fragment = findFragment(LoginFragment.class);
        if (fragment == null) {
            loadRootFragment(R.id.activity_framelayout, LoginFragment.newInstance());
        }
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
    public void onInputSize(int length) {

    }

    @Override
    public void onPlatformStart() {

    }

    @Override
    public void onPlatformComplete(SHARE_MEDIA platform, Map<String, String> data) {

    }

    @Override
    public void onPlatformError() {

    }

    @Override
    public void onPlatformCancel(SHARE_MEDIA platform) {

    }

    @Override
    public void onCallbackResult(SmsBean paramT) {

    }

    @Override
    public void onLoginCallbackResult(UserInfoBean param) {

    }

    @Override
    public void onClick(View v) {

    }
}
