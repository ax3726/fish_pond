package com.gofishfarm.htkj.presenter.main.fishingpage;

import com.gofishfarm.htkj.base.BaseObjectSubscriber;
import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.base.retrofit.RxHelper;
import com.gofishfarm.htkj.bean.MyTimeBean;
import com.gofishfarm.htkj.bean.OrderBean;
import com.gofishfarm.htkj.view.main.fishingpage.RechargeView;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author：MrHu
 * @Date ：2019-01-08
 * @Describtion ：
 */
public class RechargePresenter extends RxPresenter<RechargeView> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public RechargePresenter(RetrofitHelper paramRetrofitHelper) {
        this.mRetrofitHelper = paramRetrofitHelper;
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

    public void getOrder(String Authorization, String package_id, int pay_way, int number) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getOrder(Authorization, package_id, pay_way, number)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<OrderBean>(getView()) {
                            @Override
                            public void onSuccess(OrderBean paramT) {
                                if (getView() != null) {
                                    getView().onOrderBeanDataCallback(paramT);
                                }
                            }
                        })
        );
    }
}
