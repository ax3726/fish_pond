package com.gofishfarm.htkj.ui.myinfo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.base.BaseFragment;
import com.gofishfarm.htkj.bean.BaseBean;
import com.gofishfarm.htkj.event.Event;
import com.gofishfarm.htkj.event.RxBus;
import com.gofishfarm.htkj.presenter.myinfo.SetUpPresenter;
import com.gofishfarm.htkj.ui.webPage.WebActivity;
import com.gofishfarm.htkj.utils.SharedUtils;
import com.gofishfarm.htkj.view.myinfo.SetUpView;
import com.hjq.toast.ToastUtils;
import com.tencent.bugly.beta.Beta;

import javax.inject.Inject;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/9
 */
public class SetUpFragment extends BaseFragment<SetUpView, SetUpPresenter> implements SetUpView {

    private RelativeLayout mUpdateRl;
    private TextView mVersionTv;
    private RelativeLayout mClearCacheRl;
    private RelativeLayout mAboutUsRl;
    private RelativeLayout mServiceProtocolRl;
    private RelativeLayout mLogOutRl;

    @Inject
    SetUpPresenter mSetUpPresenter;

    public static SetUpFragment newInstance() {
        return new SetUpFragment();
    }

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
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mUpdateRl = view.findViewById(R.id.rl_update);
        mUpdateRl.setOnClickListener(this);
        mVersionTv = view.findViewById(R.id.tv_version);
        mClearCacheRl = view.findViewById(R.id.rl_clear_cache);
        mClearCacheRl.setOnClickListener(this);
        mAboutUsRl = view.findViewById(R.id.rl_about_us);
        mAboutUsRl.setOnClickListener(this);
        mServiceProtocolRl = view.findViewById(R.id.rl_service_protocol);
        mServiceProtocolRl.setOnClickListener(this);
        mLogOutRl = view.findViewById(R.id.rl_log_out);
        mLogOutRl.setOnClickListener(this);

        if (mActivity != null && !TextUtils.isEmpty(mSetUpPresenter.packageName(mActivity)))
            mVersionTv.setText(mSetUpPresenter.packageName(mActivity));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left_ib:
                if (mActivity != null)
                    mActivity.finish();
                break;
            case R.id.rl_update:
//                Beta.checkUpgrade(true,true);
                Beta.checkUpgrade();
                break;
            case R.id.rl_clear_cache:
                if (mActivity != null) {
                    showDialog(getString(R.string.clearing));
                    mSetUpPresenter.clearAllCache(mActivity);
                    dismissDialog();
                    ToastUtils.show(R.string.cleared_successfully);
                }
                break;
            case R.id.rl_about_us:
                if (mActivity != null) {
                    Intent intent = new Intent(mActivity, WebActivity.class);
                    intent.putExtra(ConfigApi.WEB_TITLE, getResources().getString(R.string.about_us));
                    intent.putExtra(ConfigApi.WEB_URL, ConfigApi.ABOUT_URL);
                    startActivity(intent);
                }
                break;
            case R.id.rl_service_protocol:
                if (mActivity != null) {
                    Intent intent = new Intent(mActivity, WebActivity.class);
                    intent.putExtra(ConfigApi.WEB_TITLE, getResources().getString(R.string.service_agreement));
                    intent.putExtra(ConfigApi.WEB_URL, ConfigApi.LOGIN_URL);
                    startActivity(intent);
                }
                break;
            case R.id.rl_log_out:
                logOut();
                break;
            default:
                break;
        }
    }

    private void logOut() {
        String authorization = SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, "");
        if (TextUtils.isEmpty(authorization)) {
            SharedUtils.getInstance().remove(ConfigApi.USER_INFO);
            SharedUtils.getInstance().remove(ConfigApi.AUTHORIZATION);
            Event.ShowFragment showFragment = new Event.ShowFragment();
            showFragment.pos = 0;
            RxBus.INSTANCE.post(showFragment);
            ToastUtils.show("退出成功");
            mActivity.finish();
        } else {
            mSetUpPresenter.Logout(authorization);
        }
    }

    @Override
    public void complete(String msg) {

    }

    @Override
    public void showError(String paramString, int status_code) {

    }

    @Override
    public void onLogOut(BaseBean baseBean) {
        if (baseBean.getCode() == 200) {
            if (mActivity != null) {
                SharedUtils.getInstance().remove(ConfigApi.USER_INFO);
                SharedUtils.getInstance().remove(ConfigApi.AUTHORIZATION);
                Event.ShowFragment showFragment = new Event.ShowFragment();
                showFragment.pos = 0;
                RxBus.INSTANCE.post(showFragment);
                ToastUtils.show("退出成功");
                mActivity.finish();
            }
        } else {
            ToastUtils.show("退出登录失败，请重试");
        }
    }
}
