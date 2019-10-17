package com.gofishfarm.htkj.ui.myinfo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.base.BaseFragment;
import com.gofishfarm.htkj.bean.ExchangeRecordBean;
import com.gofishfarm.htkj.presenter.myinfo.FishingCoinRecordPresenter;
import com.gofishfarm.htkj.ui.myinfo.adapter.ExchangeAdapter;
import com.gofishfarm.htkj.utils.SharedUtils;
import com.gofishfarm.htkj.view.myinfo.FishingCoinRecordView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author：MrHu
 * @Date ：2019-01-14
 * @Describtion ：
 */
public class FishCoinExchangeFragment extends BaseFragment<FishingCoinRecordView, FishingCoinRecordPresenter> implements FishingCoinRecordView {

    private RecyclerView rcl;
    private SmartRefreshLayout srl;
    private ExchangeAdapter adapter;
    private List<ExchangeRecordBean.ListBean> mListBean = new ArrayList<>();


    @Inject
    FishingCoinRecordPresenter mFishingCoinRecordPresenter;

    @Override
    public FishingCoinRecordPresenter createPresenter() {
        return mFishingCoinRecordPresenter;
    }

    public static FishCoinExchangeFragment newInstance() {
        return new FishCoinExchangeFragment();
    }

    @Override
    public FishingCoinRecordView createView() {
        return this;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_exchange_record;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        innitLayout(view);
        iniAdapter();
        initData();
        initListener();

    }

    private void initListener() {
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                initData();
                refreshLayout.finishRefresh(1000);
            }
        });
    }

    private void initData() {
        mFishingCoinRecordPresenter.getExchangeRecord(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, ""));
    }

    private void innitLayout(View view) {
        mToolbarView.setVisibility(View.VISIBLE);
        mTextView.setText(R.string.fish_coin_record);
        mTextView.setVisibility(View.VISIBLE);
        rcl = (RecyclerView) view.findViewById(R.id.rcl);
        srl = (SmartRefreshLayout) view.findViewById(R.id.srl);

    }

    private void iniAdapter() {
        adapter = new ExchangeAdapter(R.layout.item_records_common, mListBean);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rcl.setLayoutManager(manager);

        View view = getLayoutInflater().inflate(R.layout.empty_layout, null);
        view.setVisibility(View.VISIBLE);
        TextView tv_no_record = (TextView) view.findViewById(R.id.tv_no_record);
        tv_no_record.setText(R.string.you_have_not_yet_exchanged_records);
        adapter.setEmptyView(view);
        rcl.setAdapter(adapter);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_ib:
                pop();
                break;
            default:
                break;
        }

    }

    @Override
    public void onCallbackResult(ExchangeRecordBean param) {
        if (null == param) {
            return;
        }
        if (null == param.getList() && param.getList().size() <= 0) {
            return;
        }
        if (null != mListBean) {
            mListBean.clear();
        }
        mListBean.addAll(param.getList());
        adapter.setNewData(mListBean);
    }

    @Override
    public void complete(String msg) {

    }

    @Override
    public void showError(String paramString, int status_code) {

    }
}
