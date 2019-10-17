package com.gofishfarm.htkj.presenter.myinfo;

import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.view.myinfo.RedemptionRecordView;

import javax.inject.Inject;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/9
 */
public class RedemptionRecordPresenter extends RxPresenter<RedemptionRecordView> {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public RedemptionRecordPresenter(RetrofitHelper paramRetrofitHelper) {
        this.mRetrofitHelper = paramRetrofitHelper;
    }
}
