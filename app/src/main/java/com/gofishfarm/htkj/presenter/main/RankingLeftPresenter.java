package com.gofishfarm.htkj.presenter.main;

import com.gofishfarm.htkj.base.BaseObjectSubscriber;
import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.base.retrofit.RxHelper;
import com.gofishfarm.htkj.bean.RankLfetBean;
import com.gofishfarm.htkj.view.main.RankingLeftView;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * MrLiu253@163.com
 *
 * @time 2018/12/31
 */
public class RankingLeftPresenter extends RxPresenter<RankingLeftView> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public RankingLeftPresenter(RetrofitHelper paramRetrofitHelper) {
        this.mRetrofitHelper = paramRetrofitHelper;
    }


    public void getRankLftBeanData(String authorization) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getRankLftBeanData(authorization)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<RankLfetBean>(getView()) {
                            @Override
                            public void onSuccess(RankLfetBean paramT) {
                                if (getView() != null&&paramT!=null) {
                                    getView().onRankLeftBeanDataCallback(paramT);
                                }
                            }
                        })
        );
    }
}
