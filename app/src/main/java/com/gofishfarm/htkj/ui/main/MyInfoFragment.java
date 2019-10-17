package com.gofishfarm.htkj.ui.main;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.base.BaseFragment;
import com.gofishfarm.htkj.bean.DoSignBean;
import com.gofishfarm.htkj.bean.UserDataBean;
import com.gofishfarm.htkj.image.GlideApp;
import com.gofishfarm.htkj.presenter.main.MyInfoPresenter;
import com.gofishfarm.htkj.ui.login.LoginActivity;
import com.gofishfarm.htkj.ui.main.fishingpage.RechargeActivity;
import com.gofishfarm.htkj.ui.main.fishingpage.UserFishNewCommerActivity;
import com.gofishfarm.htkj.ui.myinfo.FishingCoinActivity;
import com.gofishfarm.htkj.ui.myinfo.HomepageActivity;
import com.gofishfarm.htkj.ui.myinfo.InviteFriendsActivity;
import com.gofishfarm.htkj.ui.myinfo.SetUpActivity;
import com.gofishfarm.htkj.ui.myinfo.SignInActivity;
import com.gofishfarm.htkj.ui.myinfo.UserInformationActivity;
import com.gofishfarm.htkj.ui.webPage.WebActivity;
import com.gofishfarm.htkj.utils.SharedUtils;
import com.gofishfarm.htkj.view.main.MyInfoView;
import com.hjq.toast.ToastUtils;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.umeng.socialize.utils.ContextUtil.getPackageName;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/5
 */
public class MyInfoFragment extends BaseFragment<MyInfoView, MyInfoPresenter> implements MyInfoView {

    private ConstraintLayout mTopCl;
    private CircleImageView mCircleImageView;
    private TextView mNameTv;
    private TextView mIdTv;
    private ConstraintLayout mLikeCl;
    private TextView mLikeNumberTv;
    private ConstraintLayout mAttentionCl;
    private TextView mAttentionNumberTv;
    private ConstraintLayout mFanCl;
    private TextView mFanNumberTv;
    private ConstraintLayout mRemainingTimeCl;
    private TextView mRemainingTimeContentTv;
    private ConstraintLayout mFishingCoinCl;
    private ConstraintLayout mInviteFriendsCl;
    private ConstraintLayout mCommentCl;
    private ConstraintLayout mHelpCenterCl;

    private TextView my_fish_number_tv;
    private TextView my_fish_num_num_tv;
    private boolean mOne = false;//判断是否第一次登录
    private String mFisher;

    @Inject
    MyInfoPresenter mMyInfoPresenter;

    public static MyInfoFragment newInstance() {
        return new MyInfoFragment();
    }

    @Override
    public MyInfoPresenter createPresenter() {
        return this.mMyInfoPresenter;
    }

    @Override
    public MyInfoView createView() {
        return this;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        setLeftImage(R.drawable.my_setup_icon);
        setRight();
        mTopCl = view.findViewById(R.id.my_top_cl);
        mTopCl.setOnClickListener(this);
        mCircleImageView = view.findViewById(R.id.my_head_portrait_iv);
        mCircleImageView.setOnClickListener(this);
        mNameTv = view.findViewById(R.id.my_name_tv);
        mIdTv = view.findViewById(R.id.my_id_tv);
        mLikeCl = view.findViewById(R.id.my_like_cl);
        mLikeCl.setOnClickListener(this);
        mLikeNumberTv = view.findViewById(R.id.my_like_number_tv);
        mAttentionCl = view.findViewById(R.id.my_attention_cl);
        mAttentionCl.setOnClickListener(this);
        mAttentionNumberTv = view.findViewById(R.id.my_attention_number_tv);

        my_fish_number_tv = view.findViewById(R.id.my_fish_number_tv);
        my_fish_num_num_tv = view.findViewById(R.id.my_fish_num_num_tv);

        mFanCl = view.findViewById(R.id.my_fan_cl);
        mFanCl.setOnClickListener(this);
        mFanNumberTv = view.findViewById(R.id.my_fan_number_tv);
        mRemainingTimeCl = view.findViewById(R.id.my_remaining_time_cl);
        mRemainingTimeCl.setOnClickListener(this);
        mRemainingTimeContentTv = view.findViewById(R.id.my_remaining_time_content_tv);
        mFishingCoinCl = view.findViewById(R.id.my_fishing_coin_cl);
        mFishingCoinCl.setOnClickListener(this);
        mInviteFriendsCl = view.findViewById(R.id.my_invite_friends_cl);
        mInviteFriendsCl.setOnClickListener(this);
        mCommentCl = view.findViewById(R.id.my_comment_cl);
        mCommentCl.setOnClickListener(this);
        mHelpCenterCl = view.findViewById(R.id.my_help_center_cl);
        mHelpCenterCl.setOnClickListener(this);
        mOne = true;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        String authorization = SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION);
        if (!TextUtils.isEmpty(authorization)) {
            if (mOne) {
//                showDialog("正在加载");
            }
            mMyInfoPresenter.getUser(authorization);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left_ib:
                startActivity(new Intent(mActivity, SetUpActivity.class));
                break;
            case R.id.toolbar_right_bt:
            case R.id.toolbar_right_iv:
                mMyInfoPresenter.getDoSign(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, ""));
                break;
            case R.id.my_head_portrait_iv:
                startActivity(new Intent(mActivity, UserInformationActivity.class));
                break;
            case R.id.my_top_cl://个人信息
                Intent intent_id = new Intent(mActivity, HomepageActivity.class);
                intent_id.putExtra(ConfigApi.FOCUSID, mFisher);
                startActivity(intent_id);
                break;
            case R.id.my_like_cl://点赞
                break;
            case R.id.my_attention_cl://关注
                break;
            case R.id.my_fan_cl://粉丝
                break;
            case R.id.my_remaining_time_cl://剩余时长
                startActivity(new Intent(mActivity, RechargeActivity.class));
                break;
            case R.id.my_fishing_coin_cl://我的渔币
                startActivity(new Intent(mActivity, FishingCoinActivity.class));
                break;
            case R.id.my_invite_friends_cl://邀请好友
                startActivity(new Intent(mActivity, InviteFriendsActivity.class));
                break;
            case R.id.my_comment_cl://给个好评

                try {
                    String mAddress = String.format("market://details?id=%s", getPackageName());
                    Intent marketIntent = new Intent("android.intent.action.VIEW");
                    marketIntent.setData(Uri.parse(mAddress));
                    startActivity(marketIntent);
                } catch (ActivityNotFoundException e) {
                    ToastUtils.show(R.string.unable_to_find_app_store);
                }

                break;
            case R.id.my_help_center_cl://帮助中心
                if (mActivity != null) {
                    Intent intent = new Intent(mActivity, WebActivity.class);
                    intent.putExtra(ConfigApi.WEB_TITLE, getResources().getString(R.string.help_center));
                    intent.putExtra(ConfigApi.WEB_URL, ConfigApi.HELP_URL);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void complete(String msg) {
        dismissDialog();
    }

    @Override
    public void showError(String paramString, int status_code) {
        dismissDialog();
//        if (mOne) {
//            ToastUtils.show(paramString);
//        }
        if (status_code != 401) {
            ToastUtils.show(paramString);
        }
        if (status_code == 401) {
            startActivity(new Intent(getContext(), LoginActivity.class));
        }
    }

    @Override
    public void onCallbackResult(UserDataBean paramT) {
        dismissDialog();
        mOne = false;
        mNameTv.setText(paramT.getNick_name());
        mFisher = paramT.getFisher_id();
        mIdTv.setText(String.format(getString(R.string.fishing_number), paramT.getFisher_id()));
        mLikeNumberTv.setText(paramT.getLikes());
        mAttentionNumberTv.setText(paramT.getFocus());
        mFanNumberTv.setText(paramT.getFans());
        my_fish_number_tv.setText(paramT.getTotal_fishing());

        mRemainingTimeContentTv.setText(String.format(getString(R.string.hour), paramT.getRemaining_time()));
        if (mActivity != null)
            GlideApp
                    .with(mActivity)
                    .load(paramT.getAvatar())
                    .placeholder(R.drawable.sy_zw)
                    .error(R.drawable.sy_zw)
                    .into(mCircleImageView);
    }

    @Override
    public void onDoSignCallbackResult(DoSignBean paramT) {
        if (null == paramT) {
            return;
        }
        ToastUtils.show(paramT.getClocked());
        startActivity(new Intent(mActivity, SignInActivity.class));
    }
}
