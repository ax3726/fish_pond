package com.gofishfarm.htkj.presenter.main.fishingpage;

import com.gofishfarm.htkj.base.BaseObjectSubscriber;
import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.base.retrofit.RxHelper;
import com.gofishfarm.htkj.bean.FishDevciceBean;
import com.gofishfarm.htkj.bean.FishGuideFinishBean;
import com.gofishfarm.htkj.bean.OnLookBean;
import com.gofishfarm.htkj.bean.OnLookFocousBean;
import com.gofishfarm.htkj.view.main.fishingpage.FishGuideActivityView;
import com.gofishfarm.htkj.view.main.fishingpage.FishGuideFinishActivityView;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by Android Studio.
 * User: MrHu
 * Date: 2019-03-20
 * Time: 下午 01:51
 *
 * @Description:
 */
public class FishGuideFinishActivityPresenter extends RxPresenter<FishGuideFinishActivityView> {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public FishGuideFinishActivityPresenter(RetrofitHelper paramRetrofitHelper) {
        this.mRetrofitHelper = paramRetrofitHelper;
    }

    public void getFishGuideFinishData(String Authorization) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getFishGuideFinishData(Authorization)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<FishGuideFinishBean>(getView()) {
                            @Override
                            public void onSuccess(FishGuideFinishBean paramT) {
                                if (getView() != null) {
                                    getView().onFishGuideFinishData(paramT);
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
                                    getView().onFishDeviceDataCallback(paramT);
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
}
