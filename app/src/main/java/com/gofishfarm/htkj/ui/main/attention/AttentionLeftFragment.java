package com.gofishfarm.htkj.ui.main.attention;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.base.BaseFragment;
import com.gofishfarm.htkj.bean.FollowListBean;
import com.gofishfarm.htkj.presenter.main.attention.AttentionLeftPresenter;
import com.gofishfarm.htkj.ui.login.LoginActivity;
import com.gofishfarm.htkj.ui.myinfo.HomepageActivity;
import com.gofishfarm.htkj.utils.SharedUtils;
import com.gofishfarm.htkj.view.main.attention.AttentionLeftView;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author：MrHu
 * @Date ：2019-01-12
 * @Describtion ：
 */
public class AttentionLeftFragment extends BaseFragment<AttentionLeftView, AttentionLeftPresenter> implements AttentionLeftView {

    private ImageView iv_search;
    private EditText et_fisher_id;
    private ImageView iv_del;
    private RelativeLayout rl_search_content;
    private TextView tv_search;
    private LinearLayout rl_search;
    private RecyclerView rcl_top;
    private RecyclerView rcl_bottom;
    private LinearLayout li_top;
    private LinearLayout li_bottom;
    private SmartRefreshLayout srl;
    private RecyclerView rcl_search;
    private LinearLayout li_search;
    private LinearLayout li_top_content;

    private LeftBottomAdapter mLeftBottomAdapter;
    private LeftTopAdapter mLeftTopAdapter;
    private LeftSearchAdapter mLeftSearchAdapter;

    private List<FollowListBean.FocusBean> mFocusBean = new ArrayList<>();
    private List<FollowListBean.FocusBean> mSearchFocusBean = new ArrayList<>();
    private List<FollowListBean.RecommendFocusBean> mRecommendFocusBean = new ArrayList<>();
    private int page = 1;
    private int total_page = 1;
    private Boolean isRefresh = true;
    private String top_fisher_id = "";
    private String bottom_fisher_id = "";
    private String search_fisher_id = "";
    private boolean mOne = false;//判断是否第一次进入页面

    @Inject
    AttentionLeftPresenter mAttentionLeftPresenter;

    public static AttentionLeftFragment newInstance() {
        return new AttentionLeftFragment();
    }

    @Override
    public AttentionLeftPresenter createPresenter() {
        return this.mAttentionLeftPresenter;
    }

    @Override
    public AttentionLeftView createView() {
        return this;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_follow;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initLayout(view);
        initAdapter();
        initListerner();
    }

    private void initListerner() {

        et_fisher_id.addTextChangedListener(this.mAttentionLeftPresenter.mTextWatcher);

        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                isRefresh = true;
                initData();
                refreshLayout.finishRefresh(1000);

            }
        });

        mLeftTopAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                srl.setEnabled(false);
                //这个是适配器自带的上拉加载功能 很方便一个实现方法搞定
                isRefresh = false;
                if (page < total_page) {
                    //模拟一下网络请求
                    page++;
                    initData();
                }
            }
        }, rcl_top);
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

//    public void hintKeyBoard() {
//        //拿到InputMethodManager
//        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//
//        //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
//
////                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//
//    }

    private void initData() {
        mAttentionLeftPresenter.getFollowListBeanData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, ""), page);
    }

    private void initAdapter() {

        mLeftTopAdapter = new LeftTopAdapter(R.layout.item_attention, mFocusBean);
        mLeftBottomAdapter = new LeftBottomAdapter(R.layout.item_attention, mRecommendFocusBean);
        mLeftSearchAdapter = new LeftSearchAdapter(R.layout.item_attention, mSearchFocusBean);
        LinearLayoutManager managerTop = new LinearLayoutManager(getContext());
        LinearLayoutManager managerBottom = new LinearLayoutManager(getContext());
        LinearLayoutManager managerSearch = new LinearLayoutManager(getContext());

        rcl_top.setLayoutManager(managerTop);
        rcl_bottom.setLayoutManager(managerBottom);
        rcl_search.setLayoutManager(managerSearch);
        rcl_top.setAdapter(mLeftTopAdapter);
        rcl_bottom.setAdapter(mLeftBottomAdapter);
        rcl_search.setAdapter(mLeftSearchAdapter);
        mLeftTopAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String fisher_id = mFocusBean.get(position).getFisher_id();
                goHomePage(fisher_id);

            }
        });

//        mLeftTopAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                top_fisher_id = mFocusBean.get(position).getFisher_id();
//                Boolean isFocous = mFocusBean.get(position).isMutual_focus();
//                if (isFocous) {
//                    mAttentionLeftPresenter.getDeFocousData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION), top_fisher_id);
//                } else {
//                    mAttentionLeftPresenter.getOnFocousData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION), top_fisher_id);
//                }
//            }
//        });
        mLeftBottomAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String fisher_id = mRecommendFocusBean.get(position).getFisher_id();
                goHomePage(fisher_id);
            }
        });
//        mLeftBottomAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                bottom_fisher_id = mRecommendFocusBean.get(position).getFisher_id();
//                Boolean isFocous = mRecommendFocusBean.get(position).isMutual_focus();
//                if (isFocous) {
//                    mAttentionLeftPresenter.getBottomDeFocousData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION), bottom_fisher_id);
//                } else {
//                    mAttentionLeftPresenter.getBottomOnFocousData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION), bottom_fisher_id);
//                }
//            }
//        });
        mLeftSearchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                ToastUtils.show("" + position);
                String fisher_id = mSearchFocusBean.get(position).getFisher_id();
                goHomePage(fisher_id);
            }
        });
//        mLeftSearchAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                search_fisher_id = mSearchFocusBean.get(position).getFisher_id();
//                Boolean isFocous = mSearchFocusBean.get(position).isMutual_focus();
//                if (isFocous) {
//                    mAttentionLeftPresenter.getSearchDeFocousData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION), search_fisher_id);
//                } else {
//                    mAttentionLeftPresenter.getSearchOnFocousData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION), search_fisher_id);
//                }
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
        iv_search = (ImageView) view.findViewById(R.id.iv_search);
        et_fisher_id = (EditText) view.findViewById(R.id.et_fisher_id);
        iv_del = (ImageView) view.findViewById(R.id.iv_del);
        rl_search_content = (RelativeLayout) view.findViewById(R.id.rl_search_content);
        tv_search = (TextView) view.findViewById(R.id.tv_search);
        rl_search = (LinearLayout) view.findViewById(R.id.rl_search);
        rcl_top = (RecyclerView) view.findViewById(R.id.rcl_top);
        rcl_bottom = (RecyclerView) view.findViewById(R.id.rcl_bottom);
        li_top = (LinearLayout) view.findViewById(R.id.li_top);
        li_top_content = (LinearLayout) view.findViewById(R.id.li_top_content);
        li_bottom = (LinearLayout) view.findViewById(R.id.li_bottom);
        srl = (SmartRefreshLayout) view.findViewById(R.id.srl);
        rcl_search = (RecyclerView) view.findViewById(R.id.rcl_search);
        li_search = (LinearLayout) view.findViewById(R.id.li_search);
        tv_search.setOnClickListener(this);
        iv_del.setOnClickListener(this);
        mOne = true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                goSearch();
                break;
            case R.id.iv_del:
                et_fisher_id.setText("");
                iv_del.setVisibility(View.INVISIBLE);
                li_search.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }

    }

    private void goSearch() {
        iv_del.setVisibility(View.VISIBLE);
        li_search.setVisibility(View.VISIBLE);
        String id = et_fisher_id.getText().toString();
//        mLeftSearchAdapter.notifyDataSetChanged();
        mAttentionLeftPresenter.getSerchFollowListBeanData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, ""), id);
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
    public void onSearchBeanDataCallback(FollowListBean mFollowListBean) {
        if (null == mFollowListBean) {
            return;
        }
        if (null != mSearchFocusBean) {
            mSearchFocusBean.clear();
        }
        mSearchFocusBean.addAll(mFollowListBean.getFocus());
        mLeftSearchAdapter.notifyDataSetChanged();

    }

    @Override
    public void onFollowListBeanDataCallback(FollowListBean mFollowListBean) {
        dismissDialog();
        mOne = false;
        if (null == mFollowListBean) {
//            mLeftTopAdapter.loadMoreComplete();
            srl.setEnabled(true);
            return;
        }
        clearListData();
        if (null != mFollowListBean.getFocus()) {
            mFocusBean.addAll(mFollowListBean.getFocus());
        }
        if (null != mFollowListBean.getRecommend_focus()) {
            mRecommendFocusBean.addAll(mFollowListBean.getRecommend_focus());
        }
        total_page = mFollowListBean.getTotal_page();
        if (mFocusBean.size() >= 3) {
            li_bottom.setVisibility(View.GONE);
        } else {
            li_bottom.setVisibility(View.VISIBLE);
        }
        mLeftTopAdapter.notifyDataSetChanged();
        mLeftBottomAdapter.notifyDataSetChanged();
        mLeftTopAdapter.loadMoreEnd();
        mLeftTopAdapter.loadMoreEnd(true);
        srl.setEnabled(true);

    }


    @Override
    public void onInputSize(int length) {
        if (length > 0) {
            iv_del.setVisibility(View.VISIBLE);
        } else {
            iv_del.setVisibility(View.INVISIBLE);
        }
    }

    private void clearListData() {
        if (isRefresh) {
            if (null != mFocusBean) {
                mFocusBean.clear();
            }
            if (null != mRecommendFocusBean) {
                mRecommendFocusBean.clear();
            }
        }
    }

}
