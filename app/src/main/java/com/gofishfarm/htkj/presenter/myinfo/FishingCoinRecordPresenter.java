package com.gofishfarm.htkj.presenter.myinfo;

import com.gofishfarm.htkj.base.BaseObjectSubscriber;
import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.base.retrofit.RxHelper;
import com.gofishfarm.htkj.bean.ExchangeRecordBean;
import com.gofishfarm.htkj.view.myinfo.FishingCoinRecordView;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author：MrHu
 * @Date ：2019-01-14
 * @Describtion ：
 */
public class FishingCoinRecordPresenter extends RxPresenter<FishingCoinRecordView> {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public FishingCoinRecordPresenter(RetrofitHelper paramRetrofitHelper) {
        this.mRetrofitHelper = paramRetrofitHelper;
    }

    public void getExchangeRecord(String mAuthorization) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getExchangeRecord(mAuthorization)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<ExchangeRecordBean>(getView()) {

                            @Override
                            public void onSuccess(ExchangeRecordBean param) {
                                if (getView() != null && param != null) {
                                    getView().onCallbackResult(param);
                                }
                            }
                        })
        );
    }

}
