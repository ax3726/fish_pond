package com.gofishfarm.htkj.presenter.main;

import com.gofishfarm.htkj.base.BaseObjectSubscriber;
import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.base.retrofit.RxHelper;
import com.gofishfarm.htkj.bean.RankRightBean;
import com.gofishfarm.htkj.view.main.RankingRightView;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * MrLiu253@163.com
 *
 * @time 2018/12/31
 */
public class RankingRightPresenter extends RxPresenter<RankingRightView> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public RankingRightPresenter(RetrofitHelper paramRetrofitHelper) {
        this.mRetrofitHelper = paramRetrofitHelper;
    }


    public void getRankLftBeanData(String authorization) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getRankRightBeanData(authorization)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<RankRightBean>(getView()) {
                            @Override
                            public void onSuccess(RankRightBean paramT) {
                                if (getView() != null && paramT != null) {
                                    getView().onRankRightBeanDataCallback(paramT);
                                }
                            }
                        })
        );
    }
}
