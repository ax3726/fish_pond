package com.gofishfarm.htkj.ui.login;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.base.BaseFragment;
import com.gofishfarm.htkj.bean.SmsBean;
import com.gofishfarm.htkj.bean.UserInfoBean;
import com.gofishfarm.htkj.custom.VerificationCodeEditText;
import com.gofishfarm.htkj.presenter.login.VerificationCodePresenter;
import com.gofishfarm.htkj.utils.Base64;
import com.gofishfarm.htkj.utils.PhoneUtils;
import com.gofishfarm.htkj.utils.RuntimeUtils;
import com.gofishfarm.htkj.utils.SharedUtils;
import com.gofishfarm.htkj.view.login.VerificationCodeView;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.inject.Inject;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/3
 */
public class VerificationCodeFragment extends BaseFragment<VerificationCodeView, VerificationCodePresenter> implements VerificationCodeView {

    private static final String AUTHORIZATION = "Authorization";
    private static final String PHONE = "phone";
    private static final String PAGE_TYPE = "page_type";//判断进入当前界面类型（短信登陆和绑定微信或微博）
    private int mPageType;
    private String mAuthorization;
    private UserInfoBean mUserInfoBean;
    private String mPhone;
//    private String mIMEI;

    private TextView mPhoneTv;
    private TextView mErrorTv;
    private TextView mResendTv;
    private VerificationCodeEditText mVCET;
    private TextView mResebdTv;
    private int mTime = 0;


    @Inject
    VerificationCodePresenter mVerificationCodePresenter;

    /**
     * @param phone     手机号
     * @param page_type 1、登陆  2、绑定手机号
     * @return
     */
    public static VerificationCodeFragment newInstance(String phone, int page_type, UserInfoBean mAuthorization) {
        VerificationCodeFragment fragment = new VerificationCodeFragment();
        Bundle args = new Bundle();
        args.putString(PHONE, phone);
        args.putInt(PAGE_TYPE, page_type);
        args.putSerializable(AUTHORIZATION, mAuthorization);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mPageType = bundle.getInt(PAGE_TYPE);
            mPhone = bundle.getString(PHONE);
            mUserInfoBean = (UserInfoBean) bundle.getSerializable(AUTHORIZATION);
        }
    }

    @Override
    public VerificationCodePresenter createPresenter() {
        return this.mVerificationCodePresenter;
    }

    @Override
    public VerificationCodeView createView() {
        return this;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragmeng_verification_code;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mPhoneTv = view.findViewById(R.id.vc_phone_tv);
        mPhoneTv.setText(String.format(getString(R.string.verification_code_sent_to), mPhone));
        mErrorTv = view.findViewById(R.id.vc_error_tv);
        mResendTv = view.findViewById(R.id.vc_resend_tv);
        mResendTv.setOnClickListener(this);
        mVCET = view.findViewById(R.id.vc_verification_vcet);
        mResebdTv = view.findViewById(R.id.vc_resend_tv);
        mResebdTv.setOnClickListener(this);
        mVCET.showKeyboard();

        mVerificationCodePresenter.setCountDown();

      /*  AndPermission.with(mActivity).runtime()
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
        mVCET.setOnVerificationCodeChangedListener(mVerificationCodePresenter.mOnVerificationCodeChangedListener);

        if (mUserInfoBean != null) {
            String token = String.format("%s:%s:%s", mUserInfoBean.getUser().getApikey(), mUserInfoBean.getUser().getFisher_id(), mUserInfoBean.getAccess_token());
            try {
                byte[] b = token.getBytes("UTF-8");
                String s = Base64.encode(b);
                mAuthorization = String.format("Bearer %s", s);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void complete(String msg) {

    }

    @Override
    public void showError(String paramString, int status_code) {
        dismissDialog();
        mErrorTv.setVisibility(View.VISIBLE);
        mErrorTv.setText(paramString);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vc_resend_tv:
                if (mTime == 0) {
//                    showDialog(getString(R.string.LOADING));
                    mErrorTv.setVisibility(View.INVISIBLE);
                    mVerificationCodePresenter.getSMS(mPhone);
                }

                break;
            case R.id.toolbar_left_ib:
                pop();
                break;
            default:
                break;
        }
    }

    @Override
    public void onSMSResult(int i) {
        mTime = i;
        if (i == 0) {
            mResebdTv.setText(getString(R.string.resend));
        } else {
            mResebdTv.setText(String.format(getString(R.string.resend_d), i));
        }

    }

    @Override
    public void onInputCompleted(CharSequence s) {
        if (s.length() == 4) {
            getLogin();

        }
    }

    private void getLogin() {
        mErrorTv.setVisibility(View.INVISIBLE);
        if (mPhone.length() != 11) {
            mErrorTv.setVisibility(View.VISIBLE);
            mErrorTv.setText(R.string.please_enter_the_correct_mobile_number);
            return;
        } else if (mVCET.getText().toString().trim().length() != 4) {
            mErrorTv.setVisibility(View.VISIBLE);
            mErrorTv.setText(R.string.please_enter_verification_code);
            return;
        }
//        else if (TextUtils.isEmpty(mIMEI)) {
//            mErrorTv.setVisibility(View.VISIBLE);
//            mErrorTv.setText(R.string.mobile_phone_IMEI_acquisition_failed);
//            return;
//        }
        else if (mPageType == 1) {
//            showDialog(getString(R.string.LOADING));
            mVerificationCodePresenter.getLogin(mPhone, mVCET.getText().toString().trim(), "");
        } else if (mPageType == 2) {
            if (TextUtils.isEmpty(mAuthorization)) {
                mErrorTv.setVisibility(View.VISIBLE);
                mErrorTv.setText("获取Authorization失败");
            } else {
//                showDialog(getString(R.string.LOADING));
                mVerificationCodePresenter.getBinding(mPhone, mVCET.getText().toString().trim(), mAuthorization);
            }
        }
    }

    @Override
    public void onCallbackResult(UserInfoBean param) {
        dismissDialog();
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
    }

    @Override
    public void onSMSCallbackResult(SmsBean paramT) {
        dismissDialog();
        mVerificationCodePresenter.setCountDown();
    }

    @Override
    public void onBindingCallbackResult(UserInfoBean param) {
        dismissDialog();
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
    }
}
