package com.gofishfarm.htkj.ui.myinfo;

import android.os.Bundle;
import android.view.View;

import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.base.BaseActivity;
import com.gofishfarm.htkj.bean.BaseBean;
import com.gofishfarm.htkj.presenter.myinfo.SetUpPresenter;
import com.gofishfarm.htkj.view.myinfo.SetUpView;

import javax.inject.Inject;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/9
 */
public class SetUpActivity extends BaseActivity<SetUpView,SetUpPresenter>implements SetUpView {

    @Inject
    SetUpPresenter mSetUpPresenter;

    @Override
    public SetUpPresenter createPresenter() {
        return this.mSetUpPresenter;
    }

    @Override
    public SetUpView createView() {
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
        ISupportFragment fragment = findFragment(SetUpFragment.class);
        if (fragment == null) {
            loadRootFragment(R.id.activity_framelayout, SetUpFragment.newInstance());
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
    public void onLogOut(BaseBean baseBean) {

    }
}
