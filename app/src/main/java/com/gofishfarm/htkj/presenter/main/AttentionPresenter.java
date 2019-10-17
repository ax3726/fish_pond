package com.gofishfarm.htkj.presenter.main;

import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.view.main.AttentionView;
import com.gofishfarm.htkj.view.main.MainView;

import javax.inject.Inject;

/**
 * MrLiu253@163.com
 *
 * @time 2018/12/31
 */
public class AttentionPresenter extends RxPresenter<AttentionView> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public AttentionPresenter(RetrofitHelper paramRetrofitHelper) {
        this.mRetrofitHelper = paramRetrofitHelper;
    }

}
