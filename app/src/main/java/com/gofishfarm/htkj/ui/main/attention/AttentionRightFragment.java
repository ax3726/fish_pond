package com.gofishfarm.htkj.ui.main.attention;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.base.BaseFragment;
import com.gofishfarm.htkj.bean.FansListBean;
import com.gofishfarm.htkj.presenter.main.attention.AttentionRightPresenter;
import com.gofishfarm.htkj.ui.login.LoginActivity;
import com.gofishfarm.htkj.ui.myinfo.HomepageActivity;
import com.gofishfarm.htkj.utils.SharedUtils;
import com.gofishfarm.htkj.view.main.attention.AttentionRightView;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author：MrHu
 * @Date ：2019-01-12
 * @Describtion ：
 */
public class AttentionRightFragment extends BaseFragment<AttentionRightView, AttentionRightPresenter> implements AttentionRightView {

    private RecyclerView rcl_top;
    private SmartRefreshLayout srl;

    private RelativeLayout rl_empty_layout;
    private ImageView iv_state;
    private TextView tv_no_record;

    private RightTopAdapter mRightTopAdapter;
    private List<FansListBean.FansBean> mFansBean = new ArrayList<>();
    private int page = 1;
    private int total_page = 1;
    private Boolean isRefresh = true;
    private boolean mOne = false;//判断是否第一次进入页面

    @Inject
    AttentionRightPresenter mAttentionRightPresenter;

    public static AttentionRightFragment newInstance() {
        return new AttentionRightFragment();
    }

    @Override
    public AttentionRightPresenter createPresenter() {
        return this.mAttentionRightPresenter;
    }

    @Override
    public AttentionRightView createView() {
        return this;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fans;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initLayout(view);
        initAdapter();
        initListerner();

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (mOne) {
//            showDialog("正在加载");
        }
        page = 1;
        isRefresh = true;
        initData();
    }

    private void initData() {
        mAttentionRightPresenter.getFansListBeanData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, ""), page);
    }

    private void initListerner() {
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                isRefresh = true;
                mAttentionRightPresenter.getFansListBeanData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, ""), page);
                refreshLayout.finishRefresh(1000);
            }
        });
        srl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                isRefresh = false;
                if (page < total_page) {
                    page++;
                    mAttentionRightPresenter.getFansListBeanData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, ""), page);
                }
                refreshLayout.finishLoadMore(1000);

            }
        });
    }

    private void initAdapter() {
        mRightTopAdapter = new RightTopAdapter(R.layout.item_attention, mFansBean);
        LinearLayoutManager managerTop = new LinearLayoutManager(getContext());
        rcl_top.setLayoutManager(managerTop);
        View view = getLayoutInflater().inflate(R.layout.empty_layout, null);
        view.setVisibility(View.VISIBLE);
        TextView tv_no_record = (TextView) view.findViewById(R.id.tv_no_record);
        tv_no_record.setText("您还没有粉丝");
        mRightTopAdapter.setEmptyView(view);
        rcl_top.setAdapter(mRightTopAdapter);
        mRightTopAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String fisher_id = mFansBean.get(position).getFisher_id();
                goHomePage(fisher_id);
            }
        });
//        mRightTopAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
////                ToastUtils.show("" + position);
//                focous_id = mFansBean.get(position).getFisher_id();
//                Boolean isFocous = mFansBean.get(position).isMutual_focus();
//                if (isFocous) {
//                    mAttentionRightPresenter.getDeFocousData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION), focous_id);
//                } else {
//                    mAttentionRightPresenter.getOnFocousData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION), focous_id);
//                }
//
//            }
//        });
    }

    private void goHomePage(String mFisher) {
        Intent intent_id = new Intent(mActivity, HomepageActivity.class);
        intent_id.putExtra(ConfigApi.FOCUSID, mFisher);
        startActivity(intent_id);
    }

    private void initLayout(View view) {
        mToolbar.setVisibility(View.GONE);
        rcl_top = (RecyclerView) view.findViewById(R.id.rcl_top);
        srl = (SmartRefreshLayout) view.findViewById(R.id.srl);
        rl_empty_layout = (RelativeLayout) view.findViewById(R.id.rl_empty_layout);
        iv_state = (ImageView) view.findViewById(R.id.iv_state);
        tv_no_record = (TextView) view.findViewById(R.id.tv_no_record);
        mOne = true;
    }

    @Override
    public void onClick(View view) {

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
    public void onFansListBeanDataCallback(FansListBean mFansListBean) {
        dismissDialog();
        mOne = false;
        if (null == mFansListBean) {
            return;
        }
        if (isRefresh) {
            if (null != mFansBean) {
                mFansBean.clear();
            }
        }
        total_page = mFansListBean.getTotal_page();
        if (null == mFansListBean.getFans()) {
            return;
        }
        mFansBean.addAll(mFansListBean.getFans());
        if (mFansBean.size() <= 0) {
            rcl_top.setVisibility(View.GONE);
            rl_empty_layout.setVisibility(View.VISIBLE);
            iv_state.setImageResource(R.drawable.norecord);
            tv_no_record.setText("还没有粉丝关注您");
        } else {
            rcl_top.setVisibility(View.VISIBLE);
            rl_empty_layout.setVisibility(View.GONE);
            Log.d("vvvvv", "erger");
        }
        mRightTopAdapter.notifyDataSetChanged();
    }
}
