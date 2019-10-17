package com.gofishfarm.htkj.ui.myinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.adapter.HomepageAdapter;
import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.base.BaseActivity;
import com.gofishfarm.htkj.bean.FocousOrNotBean;
import com.gofishfarm.htkj.bean.HomepageBean;
import com.gofishfarm.htkj.bean.UserInfoBean;
import com.gofishfarm.htkj.image.GlideApp;
import com.gofishfarm.htkj.presenter.myinfo.HomepagePresenter;
import com.gofishfarm.htkj.ui.main.fishingpage.OnLookActivity;
import com.gofishfarm.htkj.utils.SharedUtils;
import com.gofishfarm.htkj.view.myinfo.HomepageView;
import com.gyf.barlibrary.ImmersionBar;
import com.hjq.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/12
 */
public class HomepageActivity extends BaseActivity<HomepageView, HomepagePresenter> implements HomepageView {

    private ImageView mImageView;
    private CircleImageView mCircleImageView;
    private TextView mNameTv;
    private TextView mIdTv;
    private TextView mRightTv;
    private TextView mLikeNumberTv;
    private TextView mAttentionTv;
    private TextView mFanNumberTv;
    private TextView my_fish_number_tv;
    private RecyclerView mRecyclerView;
    private List<HomepageBean.UserFishplatBean> mHomepageItemBeans = new ArrayList<>();
    private HomepageAdapter mHomepageAdapter;
    private String name;
    private UserInfoBean mUserInfoBean;
    private String mAuthorization;
    private String mFocusId;

    private int focus_status;

    @Inject
    HomepagePresenter mHomepagePresenter;


    @Override
    public HomepagePresenter createPresenter() {
        return this.mHomepagePresenter;
    }

    @Override
    public HomepageView createView() {
        return this;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_home_page;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ImmersionBar.setTitleBar(this, findViewById(R.id.base_toolbar));
        mImageView = findViewById(R.id.toolbar_right_iv);

        mFocusId = getIntent().getStringExtra(ConfigApi.FOCUSID);

        mCircleImageView = findViewById(R.id.my_head_portrait_iv);
        mNameTv = findViewById(R.id.my_name_tv);
        mIdTv = findViewById(R.id.my_id_tv);
        mRightTv = findViewById(R.id.my_right_tv);
        mLikeNumberTv = findViewById(R.id.my_like_number_tv);
        mAttentionTv = findViewById(R.id.my_attention_number_tv);
        mFanNumberTv = findViewById(R.id.my_fan_number_tv);
        mRecyclerView = findViewById(R.id.my_rv);
        my_fish_number_tv = findViewById(R.id.my_fish_number_tv);

        initAdapter();

        mUserInfoBean = SharedUtils.getInstance().getObject(ConfigApi.USER_INFO, UserInfoBean.class);
        mAuthorization = SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION);
        if (TextUtils.equals(mFocusId, mUserInfoBean.getUser().getFisher_id())) {
            mRightTv.setVisibility(View.INVISIBLE);
            mImageView.setVisibility(View.VISIBLE);
        } else {
            mRightTv.setVisibility(View.VISIBLE);
            mImageView.setVisibility(View.INVISIBLE);
        }
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mHomepageAdapter = new HomepageAdapter(R.layout.item_homepage, mHomepageItemBeans);
        mHomepageAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.empty_homepage_layout, null));
        mRecyclerView.setAdapter(mHomepageAdapter);
        mHomepageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mHomepageItemBeans.get(position).isOn()) {
                    if (SharedUtils.getInstance().getBoolean(ConfigApi.ISFISHING, true) == true) {
                        ToastUtils.show("您正在钓鱼中，不能围观别人哦！");
                        return;
                    }
                    String fp_id = mHomepageItemBeans.get(position).getFp_id();
                    if (null != fp_id && !TextUtils.isEmpty(fp_id)) {
                        Intent intent = new Intent(HomepageActivity.this, OnLookActivity.class);
                        intent.putExtra("fp_id", fp_id);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        if (mUserInfoBean != null && mFocusId != null && !TextUtils.isEmpty(mAuthorization)) {
            showDialog("正在加载");
            mHomepagePresenter.getHomepage(mAuthorization, mFocusId);
        } else {
            ToastUtils.show("获取信息失败");
        }
    }

    @Override
    protected void initListener() {
        findViewById(R.id.toolbar_left_ib).setOnClickListener(this);
        mImageView.setOnClickListener(this);
        mRightTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left_ib:
                finish();
                break;
            case R.id.toolbar_right_iv:
                startActivity(new Intent(this, UserInformationActivity.class));
                break;
            case R.id.my_right_tv:
                if (TextUtils.isEmpty(mFocusId)) {
                    ToastUtils.show("获取钓手号失败");
                    return;
                }
                if (focus_status == 2 || focus_status == 3) {
                    showDialog("正在加载");
                    mHomepagePresenter.getCancelAttention(mAuthorization, mFocusId);
                } else if (focus_status == 1) {
                    showDialog("正在加载");
                    mHomepagePresenter.getAttention(mAuthorization, mFocusId);
                }
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
        ToastUtils.show(paramString);
    }

    @Override
    public void onCallbackResult(HomepageBean param) {
        dismissDialog();
        mHomepageItemBeans.clear();
        mHomepageItemBeans.addAll(param.getUser_fishplat());
//        mHomepageAdapter.setNewData(mHomepageItemBeans);
        mHomepageAdapter.notifyDataSetChanged();
        GlideApp
                .with(this)
                .load(param.getAvatar())
                .placeholder(R.drawable.sy_zw)
                .error(R.drawable.sy_zw)
                .into(mCircleImageView);
        mNameTv.setText(param.getNick_name());
        mIdTv.setText(String.format("钓手号：%s", param.getFisher_id()));
        mLikeNumberTv.setText(param.getLikes());
        mAttentionTv.setText(param.getFocus());
        mFanNumberTv.setText(param.getFans());
        my_fish_number_tv.setText(param.getTotal_fishing());
        focus_status = param.getFocus_status();
        setState(focus_status);
    }


    @Override
    public void onAttentionCallbackResult(FocousOrNotBean param) {
        dismissDialog();
        if (null == param) {
            return;
        }
        focus_status = param.getFocus_status();
        setState(focus_status);
        ToastUtils.show("关注成功");
    }

    private void setState(int focus_status) {
        if (focus_status == 1) {
            mRightTv.setText("立即关注");
        } else if (focus_status == 2) {
            mRightTv.setText("已关注");
        }
        if (focus_status == 3) {
            mRightTv.setText("互相关注");
        }
    }

    @Override
    public void onCancelCallbackResult(FocousOrNotBean param) {
        dismissDialog();
        if (null == param) {
            return;
        }
        focus_status = param.getFocus_status();
        setState(focus_status);
        ToastUtils.show("已取消关注");
    }
}
