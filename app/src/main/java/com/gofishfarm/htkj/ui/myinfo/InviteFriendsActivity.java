package com.gofishfarm.htkj.ui.myinfo;

import android.Manifest;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.base.BaseActivity;
import com.gofishfarm.htkj.bean.ShareBean;
import com.gofishfarm.htkj.bean.UserInfoBean;
import com.gofishfarm.htkj.helper.Share;
import com.gofishfarm.htkj.helper.ShareHelper;
import com.gofishfarm.htkj.presenter.myinfo.InviteFriendsPresenter;
import com.gofishfarm.htkj.utils.RuntimeUtils;
import com.gofishfarm.htkj.utils.SharedUtils;
import com.gofishfarm.htkj.view.myinfo.InviteFriendsView;
import com.gyf.barlibrary.ImmersionBar;
import com.hjq.toast.ToastUtils;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.List;

import javax.inject.Inject;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/9
 */
public class InviteFriendsActivity extends BaseActivity<InviteFriendsView, InviteFriendsPresenter> implements InviteFriendsView {


    private TextView mMoneytv;
    private TextView mGetMoneyTv;
    private ImageView mShareWechatIV;
    private ImageView mShareCircleIV;
    private ImageView mShareSinaIV;
    private ImageView mShareQqIV;
    private ImageView mShareKjIV;
    private UMShareAPI mUMShareAPI;
    private UMImage mUMImage;
    private Share mShare;
    @Inject
    InviteFriendsPresenter mInviteFriendsPresenter;

    @Override
    public InviteFriendsPresenter createPresenter() {
        return this.mInviteFriendsPresenter;
    }

    @Override
    public InviteFriendsView createView() {
        return this;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_invite_friends;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        if (mShare == null) {
            mShare = new ShareHelper(this);
        }
        TextView pTitle = findViewById(R.id.toolbar_title);
        pTitle.setText(R.string.invite_friends);
        pTitle.setVisibility(View.VISIBLE);
        ImmersionBar.setTitleBar(this, findViewById(R.id.toolbar));
        mMoneytv = findViewById(R.id.if_money_tv);
        mGetMoneyTv = findViewById(R.id.if_obtain_tv);
        mShareWechatIV = findViewById(R.id.if_share_wechat_iv);
        mShareCircleIV = findViewById(R.id.if_share_circle_iv);
        mShareSinaIV = findViewById(R.id.if_share_sina_iv);
        mShareQqIV = findViewById(R.id.if_share_qq_iv);
        mShareKjIV = findViewById(R.id.if_share_kj_iv);
        setThe();

        UserInfoBean mUserInfoBean = SharedUtils.getInstance().getObject(ConfigApi.USER_INFO, UserInfoBean.class);
        String mAuthorization = SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION);
        if (mUserInfoBean != null && mUserInfoBean.getUser() != null && !TextUtils.isEmpty(mAuthorization)) {
            showDialog("正在加载");
            mInviteFriendsPresenter.getShare(mAuthorization, mUserInfoBean.getUser().getFisher_id());
        }
    }

    private void setThe() {
        //每次登录拉取确认界面
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        this.mUMShareAPI = UMShareAPI.get(this);
        mUMShareAPI.setShareConfig(config);
    }

    @Override
    protected void initListener() {
        findViewById(R.id.toolbar_left_ib).setOnClickListener(this);
        mShareWechatIV.setOnClickListener(this);
        mShareCircleIV.setOnClickListener(this);
        mShareSinaIV.setOnClickListener(this);
        mShareQqIV.setOnClickListener(this);
        mShareKjIV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left_ib:
                finish();
                break;
            case R.id.if_share_wechat_iv:

                if (!mUMShareAPI.isInstall(this, SHARE_MEDIA.WEIXIN)) {
                    ToastUtils.show(R.string.the_client_is_not_installed_on_the_phone);
                } else {
                    share(SHARE_MEDIA.WEIXIN, mUMImage);
                }
                break;
            case R.id.if_share_circle_iv:
                if (!mUMShareAPI.isInstall(this, SHARE_MEDIA.WEIXIN_CIRCLE)) {
                    ToastUtils.show(R.string.the_client_is_not_installed_on_the_phone);
                } else {
                    share(SHARE_MEDIA.WEIXIN_CIRCLE, mUMImage);
                }
                break;
            case R.id.if_share_sina_iv:
                share(SHARE_MEDIA.SINA, mUMImage);
                break;
            case R.id.if_share_qq_iv:
                if (!mUMShareAPI.isInstall(this, SHARE_MEDIA.QQ)) {
                    ToastUtils.show(R.string.the_client_is_not_installed_on_the_phone);
                } else {
                    share(SHARE_MEDIA.QQ, mUMImage);
                }
                break;
            case R.id.if_share_kj_iv:
                if (!mUMShareAPI.isInstall(this, SHARE_MEDIA.QZONE)) {
                    ToastUtils.show(R.string.the_client_is_not_installed_on_the_phone);
                } else {
                    share(SHARE_MEDIA.QZONE, mUMImage);
                }
                break;
            default:
                break;

        }
    }

    private void share(final SHARE_MEDIA qzone, final UMImage mUMImage) {
        if (mUMImage == null) {
            ToastUtils.show("获取分享数据失败");
            return;
        }
        AndPermission.with(this).runtime()
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .rationale(new RuntimeUtils())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {//权限允许
                        mShare.share(qzone, mUMImage);
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) { // 权限拒绝
                        if (AndPermission.hasAlwaysDeniedPermission(InviteFriendsActivity.this, data)) {
                            ToastUtils.show(R.string.failed_to_get_permission);
                        }
                    }
                }).start();
    }


    @Override
    public void complete(String msg) {

    }

    @Override
    public void showError(String paramString, int status_code) {
        dismissDialog();
        ToastUtils.show(paramString);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    @Override
    public void onCallbackResult(ShareBean param) {
        dismissDialog();
        mMoneytv.setText(param.getInvite_integration());
        mGetMoneyTv.setText(param.getInvite_total());
        mUMImage = new UMImage(InviteFriendsActivity.this, param.getInvite_url());
        UMImage thumb = new UMImage(this, R.mipmap.app_logo);
        mUMImage.setThumb(thumb);
    }
}
