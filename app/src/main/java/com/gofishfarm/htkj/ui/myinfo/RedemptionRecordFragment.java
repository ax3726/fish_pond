package com.gofishfarm.htkj.ui.myinfo;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.base.BaseFragment;
import com.gofishfarm.htkj.presenter.myinfo.RedemptionRecordPresenter;
import com.gofishfarm.htkj.view.myinfo.RedemptionRecordView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import javax.inject.Inject;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/9
 */
public class RedemptionRecordFragment extends BaseFragment<RedemptionRecordView, RedemptionRecordPresenter> implements RedemptionRecordView {

    private SmartRefreshLayout mSmartRefreshLayout;
    private RecyclerView mRecyclerView;
    @Inject
    RedemptionRecordPresenter mRedemptionRecordPresenter;

    public static RedemptionRecordFragment newInstance() {
        return new RedemptionRecordFragment();
    }

    @Override
    public RedemptionRecordPresenter createPresenter() {
        return this.mRedemptionRecordPresenter;
    }

    @Override
    public RedemptionRecordView createView() {
        return this;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_redemption_record;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mTextView.setText(R.string.redemption_record);
        mSmartRefreshLayout = view.findViewById(R.id.rr_srl);
        mRecyclerView = view.findViewById(R.id.rr_rv);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left_ib:
                pop();
                break;
        }
    }

    @Override
    public void complete(String msg) {

    }

    @Override
    public void showError(String paramString, int status_code) {

    }
}
