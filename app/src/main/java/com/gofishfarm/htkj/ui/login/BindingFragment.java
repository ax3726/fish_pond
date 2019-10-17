package com.gofishfarm.htkj.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.base.BaseFragment;
import com.gofishfarm.htkj.bean.SmsBean;
import com.gofishfarm.htkj.bean.UserInfoBean;
import com.gofishfarm.htkj.presenter.login.BindingPresenter;
import com.gofishfarm.htkj.ui.webPage.WebActivity;
import com.gofishfarm.htkj.view.login.BindingView;

import javax.inject.Inject;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/5
 */
public class BindingFragment extends BaseFragment<BindingView, BindingPresenter> implements BindingView {

    private static final String USER_INFO = "user_info";
    private TextView mTitleTextView;
    private LinearLayout mLinearLayout;
    private EditText mPhoneET;
    private ImageView mDeleteIV;
    private TextView mErrorTV;
    private TextView mSendTV;
    private TextView mProtocolTV;
    private UserInfoBean mUserInfoBean;


    @Inject
    BindingPresenter mBindingPresenter;

    public static BindingFragment newInstance(UserInfoBean userInfoBean) {
        BindingFragment fragment = new BindingFragment();
        Bundle args = new Bundle();
        args.putSerializable(USER_INFO, userInfoBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mUserInfoBean = (UserInfoBean) bundle.getSerializable(USER_INFO);

        }
    }

    @Override
    public BindingPresenter createPresenter() {
        return this.mBindingPresenter;
    }

    @Override
    public BindingView createView() {
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
        mTitleTextView = view.findViewById(R.id.tv_title);
        mTitleTextView.setText(R.string.bind_phone_number);
        mLinearLayout = view.findViewById(R.id.login_bottom_ll);
        mLinearLayout.setVisibility(View.INVISIBLE);
        mPhoneET = view.findViewById(R.id.et_phone);
        mPhoneET.addTextChangedListener(mBindingPresenter.mTextWatcher);
        mDeleteIV = view.findViewById(R.id.iv_del);
        mDeleteIV.setOnClickListener(this);
        mErrorTV = view.findViewById(R.id.tv_error_msg);
        mSendTV = view.findViewById(R.id.tv_send_veryfy);
        mSendTV.setOnClickListener(this);
        mProtocolTV = view.findViewById(R.id.tv_user_protocol);
        mProtocolTV.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_del:
                mPhoneET.setText("");
                mErrorTV.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv_send_veryfy:
                hideSoftInput();
                if (mPhoneET.getText().toString().trim().length() != 11) {
                    mErrorTV.setVisibility(View.VISIBLE);
                    mErrorTV.setText(R.string.please_enter_the_correct_mobile_number);
                } else {
//                    showDialog(getString(R.string.LOADING));
                    mErrorTV.setVisibility(View.INVISIBLE);
                    mErrorTV.setVisibility(View.INVISIBLE);
                    mPhoneET.setEnabled(false);
                    mBindingPresenter.getSMS(mPhoneET.getText().toString().trim());

                }
                break;
            case R.id.tv_user_protocol:
                if (mActivity != null) {
                    Intent intent = new Intent(mActivity, WebActivity.class);
                    intent.putExtra(ConfigApi.WEB_TITLE, getResources().getString(R.string.service_agreement));
                    intent.putExtra(ConfigApi.WEB_URL, ConfigApi.LOGIN_URL);
                    startActivity(intent);
                }
                break;
            case R.id.toolbar_left_ib:
                pop();
                hideSoftInput();
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
        mErrorTV.setVisibility(View.VISIBLE);
        mErrorTV.setText(paramString);
        mPhoneET.setEnabled(true);
    }

    @Override
    public void onInputSize(int length) {
        if (length > 0) {
            mDeleteIV.setVisibility(View.VISIBLE);
        } else {
            mDeleteIV.setVisibility(View.INVISIBLE);
            mErrorTV.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onCallbackResult(SmsBean param) {
        dismissDialog();
        mPhoneET.setEnabled(true);
        start(VerificationCodeFragment.newInstance(mPhoneET.getText().toString().trim(), 2, mUserInfoBean));
    }
}
