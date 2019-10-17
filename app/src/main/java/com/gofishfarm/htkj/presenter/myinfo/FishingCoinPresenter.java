package com.gofishfarm.htkj.presenter.myinfo;

import com.gofishfarm.htkj.base.BaseObjectSubscriber;
import com.gofishfarm.htkj.base.BaseSubscriber;
import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.base.retrofit.RxHelper;
import com.gofishfarm.htkj.bean.BaseBean;
import com.gofishfarm.htkj.bean.FishingCoinBean;
import com.gofishfarm.htkj.bean.MallBean;
import com.gofishfarm.htkj.bean.MissionBean;
import com.gofishfarm.htkj.bean.MissionInfoBean;
import com.gofishfarm.htkj.view.myinfo.FishingCoinView;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/9
 */
public class FishingCoinPresenter extends RxPresenter<FishingCoinView> {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public FishingCoinPresenter(RetrofitHelper paramRetrofitHelper) {
        this.mRetrofitHelper = paramRetrofitHelper;
    }

    public void getFishingCoin(String mAuthorization, String fisher_id) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getFishingCoin(mAuthorization, fisher_id)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<FishingCoinBean>(getView()) {

                            @Override
                            public void onSuccess(FishingCoinBean param) {
                                if (getView() != null && param != null) {
                                    getView().onCallbackResult(param);
                                }
                            }
                        })
        );
    }

    public void getOnExchangeData(String Authorization, String is_id, String number) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getOnExchangeData(Authorization, is_id, number)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseSubscriber(getView()) {
                            @Override
                            public void onSuccess(BaseBean param) {
                                if (getView() != null) {
                                    getView().onExchangeCallback(param);
                                }
                            }
                        })
        );
    }


    public void getMissionBeabData(String mAuthorization) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getMissionBeabData(mAuthorization)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<MissionBean>(getView()) {

                            @Override
                            public void onSuccess(MissionBean param) {
                                if (getView() != null && param != null) {
                                    getView().onMissionCallback(param);
                                }
                            }
                        })
        );
    }


    public void getOnMissionData(String Authorization, String m_id, String node) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getOnMissionData(Authorization, m_id, node)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<MissionInfoBean>(getView()) {
                            @Override
                            public void onSuccess(MissionInfoBean param) {
                                if (getView() != null) {
                                    getView().onDoMissionCallback(param);
                                }
                            }
                        })
        );
    }


    public void goMall(String Authorization) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.goMall(Authorization)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<MallBean>(getView()) {
                            @Override
                            public void onSuccess(MallBean param ) {
                                if (getView() != null) {
                                    getView().onMallBeanDataCallback(param);
                                }
                            }
                        })
        );
    }
}
