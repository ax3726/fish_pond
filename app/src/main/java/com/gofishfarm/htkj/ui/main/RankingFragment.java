package com.gofishfarm.htkj.ui.main;

import android.os.Bundle;
//import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.base.BaseFragment;
import com.gofishfarm.htkj.presenter.main.RankingPresenter;
import com.gofishfarm.htkj.ui.main.adapter.RankingPagerFragmentAdapter;
import com.gofishfarm.htkj.view.main.RankingView;

import javax.inject.Inject;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/5
 */
public class RankingFragment extends BaseFragment<RankingView, RankingPresenter> implements RankingView {

    private TabLayout mTab;
    private ViewPager mViewPager;
    @Inject
    RankingPresenter mRankingPresenter;

    public static RankingFragment newInstance() {
        return new RankingFragment();
    }

    @Override
    public RankingPresenter createPresenter() {
        return this.mRankingPresenter;
    }

    @Override
    public RankingView createView() {
        return this;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ranking;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initLayout(view);
        intAdapter();

    }

    private void intAdapter() {
        mViewPager.setAdapter(new RankingPagerFragmentAdapter(getChildFragmentManager(), getString(R.string.fish_most), getString(R.string.fish_fast)));
        mTab.setupWithViewPager(mViewPager);
    }

    private void initLayout(View view) {
        mImageButton.setVisibility(View.INVISIBLE);
        mTextView.setVisibility(View.VISIBLE);
        mTextView.setText(getString(R.string.billBoard));
        mTab = (TabLayout) view.findViewById(R.id.tab);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mTab.addTab(mTab.newTab());
        mTab.addTab(mTab.newTab());
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void complete(String msg) {

    }

    @Override
    public void showError(String paramString, int status_code) {

    }
}
