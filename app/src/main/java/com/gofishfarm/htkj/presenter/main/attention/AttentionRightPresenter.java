package com.gofishfarm.htkj.presenter.main.attention;

import com.gofishfarm.htkj.base.BaseObjectSubscriber;
import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.base.retrofit.RxHelper;
import com.gofishfarm.htkj.bean.FansListBean;
import com.gofishfarm.htkj.view.main.attention.AttentionRightView;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * MrLiu253@163.com
 *
 * @time 2018/12/31
 */
public class AttentionRightPresenter extends RxPresenter<AttentionRightView> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public AttentionRightPresenter(RetrofitHelper paramRetrofitHelper) {
        this.mRetrofitHelper = paramRetrofitHelper;
    }


    public void getFansListBeanData(String authorization, int page) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getFansListBeanData(authorization,page)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<FansListBean>(getView()) {
                            @Override
                            public void onSuccess(FansListBean paramT) {
                                if (getView() != null&&paramT!=null) {
                                    getView().onFansListBeanDataCallback(paramT);
                                }
                            }
                        })
        );
    }

}
