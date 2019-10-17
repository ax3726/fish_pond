package com.gofishfarm.htkj.presenter.main;

import com.gofishfarm.htkj.base.BaseObjectSubscriber;
import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.base.retrofit.RxHelper;
import com.gofishfarm.htkj.bean.DoSignBean;
import com.gofishfarm.htkj.bean.UserDataBean;
import com.gofishfarm.htkj.view.main.MyInfoView;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * MrLiu253@163.com
 *
 * @time 2018/12/31
 */
public class MyInfoPresenter extends RxPresenter<MyInfoView> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public MyInfoPresenter(RetrofitHelper paramRetrofitHelper) {
        this.mRetrofitHelper = paramRetrofitHelper;
    }

    public void getUser(String authorization) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getUser(authorization)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<UserDataBean>(getView()) {
                            @Override
                            public void onSuccess(UserDataBean paramT) {
                                if (getView() != null&&paramT!=null) {
                                    getView().onCallbackResult(paramT);
                                }
                            }
                        })
        );
    }


    public void getDoSign(String authorization) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getDoSign(authorization)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<DoSignBean>(getView()) {
                            @Override
                            public void onSuccess(DoSignBean paramT) {
                                if (getView() != null&&paramT!=null) {
                                    getView().onDoSignCallbackResult(paramT);
                                }
                            }
                        })
        );
    }
}
