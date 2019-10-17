package com.gofishfarm.htkj.presenter.main;

import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.view.main.MainView;
import com.gofishfarm.htkj.view.main.RankingView;

import javax.inject.Inject;

/**
 * MrLiu253@163.com
 *
 * @time 2018/12/31
 */
public class RankingPresenter extends RxPresenter<RankingView> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public RankingPresenter(RetrofitHelper paramRetrofitHelper) {
        this.mRetrofitHelper = paramRetrofitHelper;
    }

}
