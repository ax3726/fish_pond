package com.gofishfarm.htkj.presenter.main.fishingpage;

import com.gofishfarm.htkj.base.BaseObjectSubscriber;
import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.base.retrofit.RxHelper;
import com.gofishfarm.htkj.bean.OrderRecordBean;
import com.gofishfarm.htkj.bean.OrderRecordNewBean;
import com.gofishfarm.htkj.view.main.fishingpage.RechargeRecordView;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author：MrHu
 * @Date ：2019-01-08
 * @Describtion ：
 */
public class RechargeRecordPresenter extends RxPresenter<RechargeRecordView> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public RechargeRecordPresenter(RetrofitHelper paramRetrofitHelper) {
        this.mRetrofitHelper = paramRetrofitHelper;
    }

    public void getMyTimeBeanData(String Authorization) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getOrderRecordData(Authorization)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<OrderRecordBean>(getView()) {
                            @Override
                            public void onSuccess(OrderRecordBean paramT) {
                                if (getView() != null) {
                                    getView().onRechargeRecordDataCallback(paramT);
                                }
                            }
                        })
        );
    }

    public void getNewMyTimeBeanData(String Authorization, String fisher_id) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getNewMyTimeBeanData(Authorization, fisher_id)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<OrderRecordNewBean>(getView()) {
                            @Override
                            public void onSuccess(OrderRecordNewBean paramT) {
                                if (getView() != null) {
                                    getView().onRechargeRecordNewDataCallback(paramT);
                                }
                            }
                        })
        );
    }


}
