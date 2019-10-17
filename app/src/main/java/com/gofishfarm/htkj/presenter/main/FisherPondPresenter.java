package com.gofishfarm.htkj.presenter.main;

import com.gofishfarm.htkj.base.BaseObjectSubscriber;
import com.gofishfarm.htkj.base.BaseSubscriber;
import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.base.retrofit.RxHelper;
import com.gofishfarm.htkj.bean.BaseBean;
import com.gofishfarm.htkj.bean.FishDevciceBean;
import com.gofishfarm.htkj.bean.FishPondBean;
import com.gofishfarm.htkj.bean.MyTimeBean;
import com.gofishfarm.htkj.bean.OnLookBean;
import com.gofishfarm.htkj.view.main.FisherPondView;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * MrLiu253@163.com
 *
 * @time 2018/12/31
 */
public class FisherPondPresenter extends RxPresenter<FisherPondView> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public FisherPondPresenter(RetrofitHelper paramRetrofitHelper) {
        this.mRetrofitHelper = paramRetrofitHelper;
    }

    public void getFishPondData() {

        addSubscribe(
                (Disposable) this.mRetrofitHelper.getFishPondData()
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<FishPondBean>(getView()) {
                            @Override
                            public void onSuccess(FishPondBean paramT) {
                                if (getView() != null) {
                                    getView().onFishPondDataCallback(paramT);
                                }
                            }
                        })
        );

    }

    public void getFishplatData(String Authorization, int status, String fishery_id) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getFishplatData(Authorization, status, fishery_id)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<FishDevciceBean>(getView()) {
                            @Override
                            public void onSuccess(FishDevciceBean paramT) {
                                if (getView() != null) {
                                    getView().onFishDeviceDataCallback(paramT, 2);
                                }
                            }
                        })
        );
    }

    public void getFastFishplatData(String Authorization, int status) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getFastFishplatData(Authorization, status)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<FishDevciceBean>(getView()) {
                            @Override
                            public void onSuccess(FishDevciceBean paramT) {
                                if (getView() != null) {
                                    getView().onFishDeviceDataCallback(paramT, 1);
                                }
                            }
                        })
        );
    }

    public void getOnLookBeanData(String Authorization, String fishery_id, String field) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getOnLookBeanData(Authorization, fishery_id)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<OnLookBean>(getView()) {
                            @Override
                            public void onSuccess(OnLookBean paramT) {
                                if (getView() != null) {
                                    getView().onOnLookDataCallback(paramT);
                                }
                            }
                        })
        );
    }

    public void getMyTimeBeanData(String Authorization) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getMyTimeBeanData(Authorization)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<MyTimeBean>(getView()) {
                            @Override
                            public void onSuccess(MyTimeBean paramT) {
                                if (getView() != null) {
                                    getView().onMyTimeBeanDataCallback(paramT);
                                }
                            }
                        })
        );
    }

    public void deleteFinshingOut(String Authorization, int $fp_id) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.deleteFinshingOut(Authorization, $fp_id)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseSubscriber(getView()) {
                            @Override
                            public void onSuccess(BaseBean param) {
                                if (getView() != null) {
                                }
                            }
                        })
        );
    }


}
