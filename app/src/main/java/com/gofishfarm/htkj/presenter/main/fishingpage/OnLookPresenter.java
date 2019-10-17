package com.gofishfarm.htkj.presenter.main.fishingpage;

import com.gofishfarm.htkj.base.BaseObjectSubscriber;
import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.base.retrofit.RxHelper;
import com.gofishfarm.htkj.bean.FocousOrNotBean;
import com.gofishfarm.htkj.bean.OnLookBean;
import com.gofishfarm.htkj.bean.OnLookFocousBean;
import com.gofishfarm.htkj.view.main.fishingpage.OnLookActivityView;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author：MrHu
 * @Date ：2019-01-08
 * @Describtion ：
 */
public class OnLookPresenter extends RxPresenter<OnLookActivityView> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public OnLookPresenter(RetrofitHelper paramRetrofitHelper) {
        this.mRetrofitHelper = paramRetrofitHelper;
    }

    public void getOnLookFocousData(String Authorization, String id) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getOnLookFocousData(Authorization, id)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<OnLookFocousBean>(getView()) {
                            @Override
                            public void onSuccess(OnLookFocousBean paramT) {
                                if (getView() != null) {
                                    getView().OnLookFocousBeanData(paramT);
                                }
                            }
                        })
        );
    }
    public void getOnLookSerchBeanData(String Authorization, String field) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getOnLookSerchBeanData(Authorization, field)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<OnLookBean>(getView()) {
                            @Override
                            public void onSuccess(OnLookBean paramT) {
                                if (getView() != null) {
                                    getView().onLookSerchBeanDataCallback(paramT);
                                }
                            }
                        })
        );
    }

    public void getOnFocousData(String Authorization, String focus_id) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getOnFocousData(Authorization, focus_id)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<FocousOrNotBean>(getView()) {
                            @Override
                            public void onSuccess(FocousOrNotBean param) {
                                if (getView() != null) {
                                    getView().OnFocousBeanData(param);
                                }
                            }

                        })
        );
    }

    public void getDeFocousData(String Authorization, String focus_id) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getDeFocousData(Authorization, focus_id)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<FocousOrNotBean>(getView()) {
                            @Override
                            public void onSuccess(FocousOrNotBean param) {
                                if (getView() != null) {
                                    getView().OnDeFocousBeanData(param);
                                }
                            }

                        })
        );
    }

    public void getOnLookBeanData(String Authorization, String fishery_id) {
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

}
