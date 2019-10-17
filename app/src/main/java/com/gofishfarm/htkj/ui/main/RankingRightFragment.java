package com.gofishfarm.htkj.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.base.BaseFragment;
import com.gofishfarm.htkj.bean.RankRightBean;
import com.gofishfarm.htkj.image.GlideApp;
import com.gofishfarm.htkj.image.transformation.GlideRoundImageTransform;
import com.gofishfarm.htkj.presenter.main.RankingRightPresenter;
import com.gofishfarm.htkj.ui.login.LoginActivity;
import com.gofishfarm.htkj.ui.main.adapter.RankRightAdapter;
import com.gofishfarm.htkj.ui.myinfo.HomepageActivity;
import com.gofishfarm.htkj.utils.SharedUtils;
import com.gofishfarm.htkj.view.main.RankingRightView;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/5
 */
public class RankingRightFragment extends BaseFragment<RankingRightView, RankingRightPresenter> implements RankingRightView {

    private CircleImageView civ_imag_two;
    private TextView tv_two;
    private CircleImageView civ_imag_one;
    private TextView tv_one;
    private CircleImageView civ_imag_three;
    private TextView tv_three;
    private TextView tv_fast_most;
    private TextView tv_title_rank;
    private RecyclerView recy;
    private SmartRefreshLayout refresh_layout;
    RankRightAdapter adapter;
    List<RankRightBean.InfoBean> mInfoBean = new ArrayList<>();
    private boolean mOne = false;//判断是否第一次进入页面

    @Inject
    RankingRightPresenter mRankingRightPresenter;

    public static RankingRightFragment newInstance() {
        return new RankingRightFragment();
    }

    @Override
    public RankingRightPresenter createPresenter() {
        return this.mRankingRightPresenter;
    }

    @Override
    public RankingRightView createView() {
        return this;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ranking_left;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initLayout(view);
        initAdapter();
        initListener();
    }

    private void initListener() {
        refresh_layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initData();
                refreshLayout.finishRefresh(1000);
            }
        });
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (mOne) {
//            showDialog("正在加载");
        }
        initData();
    }

    private void initData() {
        mRankingRightPresenter.getRankLftBeanData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, ""));
    }

    private void initAdapter() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        adapter = new RankRightAdapter(R.layout.item_rank, mInfoBean);
        recy.setAdapter(adapter);
        recy.setLayoutManager(manager);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String fisher_id = mInfoBean.get(position).getFisher_id();
                goHomePage(fisher_id);
            }
        });
    }

    private void goHomePage(String mFisher) {
        Intent intent_id = new Intent(mActivity, HomepageActivity.class);
        intent_id.putExtra(ConfigApi.FOCUSID, mFisher);
        startActivity(intent_id);
    }

    private void initLayout(View view) {
        mToolbar.setVisibility(View.GONE);
        civ_imag_two = (CircleImageView) view.findViewById(R.id.civ_imag_two);
        tv_two = (TextView) view.findViewById(R.id.tv_two);
        civ_imag_one = (CircleImageView) view.findViewById(R.id.civ_imag_one);
        tv_one = (TextView) view.findViewById(R.id.tv_one);
        civ_imag_three = (CircleImageView) view.findViewById(R.id.civ_imag_three);
        tv_three = (TextView) view.findViewById(R.id.tv_three);
        tv_title_rank = (TextView) view.findViewById(R.id.tv_title_rank);
        tv_fast_most = (TextView) view.findViewById(R.id.tv_fast_most);
        recy = (RecyclerView) view.findViewById(R.id.recy);
        refresh_layout = (SmartRefreshLayout) view.findViewById(R.id.refresh_layout);
        tv_fast_most.setText(getString(R.string.fish_speed));
        tv_title_rank.setText(getString(R.string.stars));
        mOne = true;
    }

    private void initHeadInfo() {
        if (null != mInfoBean.get(0) && null != mInfoBean.get(0).getNick_name()) {
            tv_one.setText(mInfoBean.get(0).getNick_name());
        }
        if (null != mInfoBean.get(1) && null != mInfoBean.get(1).getNick_name()) {
            tv_two.setText(mInfoBean.get(1).getNick_name());
        }
        if (null != mInfoBean.get(2) && null != mInfoBean.get(2).getNick_name()) {
            tv_three.setText(mInfoBean.get(2).getNick_name());
        }
        if (null != mInfoBean.get(0) && null != mInfoBean.get(0).getAvatar()) {
            GlideApp.with(getContext())
                    .load(mInfoBean.get(0).getAvatar())
                    .transform(new GlideRoundImageTransform(getActivity(), 22))
                    .centerCrop()
                    .placeholder(R.drawable.two)
                    .error(R.drawable.two)
                    .into(civ_imag_one);
        }
        if (null != mInfoBean.get(1) && null != mInfoBean.get(1).getAvatar()) {
            GlideApp.with(getContext())
                    .load(mInfoBean.get(1).getAvatar())
                    .transform(new GlideRoundImageTransform(getActivity(), 22))
                    .centerCrop()
                    .placeholder(R.drawable.two)
                    .error(R.drawable.two)
                    .into(civ_imag_two);
        }
        if (null != mInfoBean.get(2) && null != mInfoBean.get(2).getAvatar()) {
            GlideApp.with(getContext())
                    .load(mInfoBean.get(2).getAvatar())
                    .transform(new GlideRoundImageTransform(getActivity(), 22))
                    .centerCrop()
                    .placeholder(R.drawable.two)
                    .error(R.drawable.two)
                    .into(civ_imag_three);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void complete(String msg) {
        dismissDialog();
    }

    @Override
    public void showError(String paramString, int status_code) {
        dismissDialog();
        if (status_code != 401) {
            ToastUtils.show(paramString);
        }
        if (status_code == 401) {
            startActivity(new Intent(getContext(), LoginActivity.class));
        }
    }

    @Override
    public void onRankRightBeanDataCallback(RankRightBean mRankRightBean) {
        dismissDialog();
        mOne = false;
        if (null == mRankRightBean) {
            return;
        }
        setData(mRankRightBean);
    }

    private void setData(RankRightBean mRankRightBean) {
        if (null != mInfoBean) {
            mInfoBean.clear();
        }
        mInfoBean.addAll(mRankRightBean.getInfo());
        initHeadInfo();
        adapter.notifyDataSetChanged();
    }
}
