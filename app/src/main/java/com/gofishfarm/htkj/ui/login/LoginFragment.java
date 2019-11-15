package com.gofishfarm.htkj.ui.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.base.BaseFragment;
import com.gofishfarm.htkj.bean.SmsBean;
import com.gofishfarm.htkj.bean.UserInfoBean;
import com.gofishfarm.htkj.presenter.login.LoginPresenter;
import com.gofishfarm.htkj.ui.webPage.WebActivity;
import com.gofishfarm.htkj.utils.Base64;
import com.gofishfarm.htkj.utils.PhoneUtils;
import com.gofishfarm.htkj.utils.RuntimeUtils;
import com.gofishfarm.htkj.utils.SharedUtils;
import com.gofishfarm.htkj.view.login.LoginView;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * MrLiu253@163.com
 * Binding
 *
 * @time 2019/1/3
 */
public class LoginFragment extends BaseFragment<LoginView, LoginPresenter> implements LoginView {

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    private EditText mPhoneET;
    private ImageView mDeleteIV;
    private TextView mErrorTextView;
    private TextView mSendTextView;
    private TextView mProtocolTextView;
    private ImageView mWXImageView;
    private ImageView mXBImageView;
    private UMShareAPI mUMShareAPI;
    private String mIMEI="";

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
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_log_in;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {


        mPhoneET = view.findViewById(R.id.et_phone);
        mPhoneET.addTextChangedListener(mLoginPresenter.mTextWatcher);
        mDeleteIV = view.findViewById(R.id.iv_del);
        mDeleteIV.setOnClickListener(this);
        mErrorTextView = view.findViewById(R.id.tv_error_msg);
        mSendTextView = view.findViewById(R.id.tv_send_veryfy);
        mSendTextView.setOnClickListener(this);
        mProtocolTextView = view.findViewById(R.id.tv_user_protocol);
        mProtocolTextView.setOnClickListener(this);
        mWXImageView = view.findViewById(R.id.iv_wx_login);
        mWXImageView.setOnClickListener(this);
        mXBImageView = view.findViewById(R.id.iv_wb_login);
        mXBImageView.setOnClickListener(this);
        setAuthorization();

     /*   AndPermission.with(mActivity).runtime()
                .permission(Manifest.permission.READ_PHONE_STATE)
                .rationale(new RuntimeUtils())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {//权限允许
                        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        mIMEI = PhoneUtils.getIMEI();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) { // 权限拒绝
                        if (AndPermission.hasAlwaysDeniedPermission(mActivity, data)) {

                        }
                    }
                }).start();*/
    }

    private void setAuthorization() {
        if (mActivity != null) {
            //每次登录拉取确认界面
            UMShareConfig config = new UMShareConfig();
            config.isNeedAuthOnGetUserInfo(true);
            this.mUMShareAPI = UMShareAPI.get(mActivity);
            mUMShareAPI.setShareConfig(config);
            if (!mUMShareAPI.isInstall(mActivity, SHARE_MEDIA.WEIXIN)) {
                mWXImageView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void complete(String msg) {

    }

    @Override
    public void showError(String paramString, int status_code) {
        dismissDialog();
        mPhoneET.setEnabled(true);
        mErrorTextView.setVisibility(View.VISIBLE);
        mErrorTextView.setText(paramString);

    }


    @Override
    public void onInputSize(int length) {
        if (length > 0) {
            mDeleteIV.setVisibility(View.VISIBLE);
        } else {
            mDeleteIV.setVisibility(View.INVISIBLE);
            mErrorTextView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left_ib:
                if (mActivity != null) {
                    mActivity.finish();
                }
                break;
            case R.id.tv_send_veryfy:
                hideSoftInput();
                mErrorTextView.setVisibility(View.INVISIBLE);
                if (mPhoneET.getText().toString().length() != 11) {
                    mErrorTextView.setVisibility(View.VISIBLE);
                    mErrorTextView.setText(R.string.please_enter_the_correct_mobile_number);
                } else {
                    showDialog(getString(R.string.LOADING));
                    mErrorTextView.setVisibility(View.INVISIBLE);
                    mPhoneET.setEnabled(false);
                    mLoginPresenter.getSMS(mPhoneET.getText().toString().trim());
                }
                break;
            case R.id.iv_del:
                mPhoneET.setText("");
                mErrorTextView.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv_user_protocol://服务协议
                if (mActivity != null) {
                    Intent intent = new Intent(mActivity, WebActivity.class);
                    intent.putExtra(ConfigApi.WEB_TITLE, getResources().getString(R.string.service_agreement));
                    intent.putExtra(ConfigApi.WEB_URL, ConfigApi.LOGIN_URL);
                    startActivity(intent);
                }
                break;
            case R.id.iv_wx_login:
                mErrorTextView.setVisibility(View.INVISIBLE);
                platformInfo(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.iv_wb_login:
                mErrorTextView.setVisibility(View.INVISIBLE);
                platformInfo(SHARE_MEDIA.SINA);
                break;
            default:
                break;
        }
    }

    private void platformInfo(SHARE_MEDIA media) {
        //请求权限
        if (mActivity != null) {
//            if (TextUtils.isEmpty(mIMEI)) {
//                mErrorTextView.setVisibility(View.VISIBLE);
//                mErrorTextView.setText("获取手机IMEI失败");
//            } else {
//                mUMShareAPI.getPlatformInfo(mActivity, media, mLoginPresenter.authListener);
//            }
            mUMShareAPI.getPlatformInfo(mActivity, media, mLoginPresenter.authListener);
        }
    }

    @Override
    public void onPlatformStart() {

    }

    @Override
    public void onPlatformComplete(SHARE_MEDIA platform, Map<String, String> data) {
        String id = data.get("uid");
        String nick_name = data.get("name");
        String gender = TextUtils.equals(data.get("gender"), "男") ? "1" : "2";
        String avatar = data.get("iconurl");
        if (platform == SHARE_MEDIA.WEIXIN) {
            //用户唯一标识
//            showDialog(getString(R.string.LOADING));
            mLoginPresenter.getLogin(id, nick_name, avatar, gender, mIMEI, 1);
        } else {
            mLoginPresenter.getLogin(id, nick_name, avatar, gender, mIMEI, 2);
        }

    }

    @Override
    public void onPlatformError() {
        mErrorTextView.setVisibility(View.VISIBLE);
        mErrorTextView.setText(R.string.login_failed);
    }

    @Override
    public void onPlatformCancel(SHARE_MEDIA platform) {

    }

    @Override
    public void onCallbackResult(SmsBean paramT) {
        dismissDialog();
        mPhoneET.setEnabled(true);
        start(VerificationCodeFragment.newInstance(mPhoneET.getText().toString().trim(), 1, null));
    }

    @Override
    public void onLoginCallbackResult(UserInfoBean param) {
        dismissDialog();
        if (param.isLocked()) {
            SharedUtils.getInstance().putObject(ConfigApi.USER_INFO, param);
            String token = String.format("%s:%s:%s", param.getUser().getApikey(), param.getUser().getFisher_id(), param.getAccess_token());
            try {
                byte[] b = token.getBytes("UTF-8");
                String s = Base64.encode(b);
                SharedUtils.getInstance().putString(ConfigApi.AUTHORIZATION, String.format("Bearer %s", s));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (mActivity != null)
                mActivity.finish();
        } else {
            start(BindingFragment.newInstance(param));
        }

    }
}
