package com.gofishfarm.htkj.ui.main.fishingpage;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.base.BaseActivity;
import com.gofishfarm.htkj.bean.OrderRecordBean;
import com.gofishfarm.htkj.bean.OrderRecordNewBean;
import com.gofishfarm.htkj.bean.UserInfoBean;
import com.gofishfarm.htkj.presenter.main.fishingpage.RechargeRecordPresenter;
import com.gofishfarm.htkj.ui.main.adapter.RechargeRecordAdapter;
import com.gofishfarm.htkj.utils.SharedUtils;
import com.gofishfarm.htkj.view.main.fishingpage.RechargeRecordView;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RechargeRecordActivity extends BaseActivity<RechargeRecordView, RechargeRecordPresenter> implements RechargeRecordView {

    private ImageButton toolbar_left_ib;
    private TextView toolbar_title;
    private ImageView toolbar_right_iv;
    private ImageView toolbar_right_my_iv;
    private TextView toolbar_right_bt;
    private View toolbar_v;
    private RecyclerView rcl;
    private SmartRefreshLayout srl;
    RechargeRecordAdapter adapter;

    private ImageView iv_empty_state;
    private TextView tv_empty_no_record;
    private RelativeLayout rl_empty_layout;
    private ImageView iv_state;
    private TextView tv_no_record;
    private List<OrderRecordNewBean.ListBean> mListBean = new ArrayList<>();
    @Inject
    RechargeRecordPresenter mRechargeRecordPresenter;

    @Override
    public RechargeRecordPresenter createPresenter() {
        return this.mRechargeRecordPresenter;
    }

    @Override
    public RechargeRecordView createView() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_record_common;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initLayout();
        initAdapter();
        initData();
    }

    private void initData() {
//        mRechargeRecordPresenter.getMyTimeBeanData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, ""));
        String fisher_id = SharedUtils.getInstance().getObject(ConfigApi.USER_INFO, UserInfoBean.class).getUser().getFisher_id();
        mRechargeRecordPresenter.getNewMyTimeBeanData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, ""), fisher_id);
    }

    private void initAdapter() {
        adapter = new RechargeRecordAdapter(R.layout.item_records_common, mListBean);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        View view = getLayoutInflater().inflate(R.layout.empty_layout, null);
        view.setVisibility(View.VISIBLE);
        TextView tv_no_record = (TextView) view.findViewById(R.id.tv_no_record);
        tv_no_record.setText("暂无记录");
        adapter.setEmptyView(view);
        rcl.setLayoutManager(manager);
        rcl.setAdapter(adapter);
    }

    private void initLayout() {
        ImmersionBar.setTitleBar(this, findViewById(R.id.ui_toolbar));
        toolbar_left_ib = (ImageButton) findViewById(R.id.toolbar_left_ib);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_right_iv = (ImageView) findViewById(R.id.toolbar_right_iv);
        toolbar_right_my_iv = (ImageView) findViewById(R.id.toolbar_right_my_iv);
        toolbar_right_bt = (TextView) findViewById(R.id.toolbar_right_bt);
        toolbar_v = (View) findViewById(R.id.toolbar_v);
        rcl = (RecyclerView) findViewById(R.id.rcl);
        srl = (SmartRefreshLayout) findViewById(R.id.srl);
        iv_state = (ImageView) findViewById(R.id.iv_state);
        tv_no_record = (TextView) findViewById(R.id.tv_no_record);
        toolbar_v.setVisibility(View.VISIBLE);
        toolbar_title.setVisibility(View.VISIBLE);

        iv_state.setOnClickListener(this);
        tv_no_record.setOnClickListener(this);
        toolbar_left_ib.setOnClickListener(this);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText(getString(R.string.time_record));
    }

    @Override
    protected void initListener() {
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initData();
                refreshLayout.finishRefresh(1000);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_ib:
                finish();
                break;
            case R.id.iv_state:
                break;
            case R.id.tv_no_record:
                break;
            default:
                break;
        }
    }

    @Override
    public void onRechargeRecordDataCallback(OrderRecordBean mOrderRecordBean) {
    }

    @Override
    public void onRechargeRecordNewDataCallback(OrderRecordNewBean mOrderRecordNewBean) {
        if (null == mOrderRecordNewBean) {
            return;
        }
        setData(mOrderRecordNewBean);
    }

    private void setData(OrderRecordNewBean mOrderRecordNewBean) {

        if (null == mOrderRecordNewBean.getList() && mOrderRecordNewBean.getList().size() <= 0) {
            return;
        }
        if (null != mListBean) {
            mListBean.clear();
        }
        mListBean.addAll(mOrderRecordNewBean.getList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void complete(String msg) {

    }

    @Override
    public void showError(String paramString, int status_code) {

    }
}
