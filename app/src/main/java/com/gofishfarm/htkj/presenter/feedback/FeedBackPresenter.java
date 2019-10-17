package com.gofishfarm.htkj.presenter.feedback;

import com.gofishfarm.htkj.base.BaseObjectSubscriber;
import com.gofishfarm.htkj.base.BaseSubscriber;
import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.base.retrofit.RxHelper;
import com.gofishfarm.htkj.bean.BaseBean;
import com.gofishfarm.htkj.bean.ErrorListBean;
import com.gofishfarm.htkj.bean.UserInformationBean;
import com.gofishfarm.htkj.view.feedback.FeedBackView;
import com.gofishfarm.htkj.view.myinfo.UserInformationView;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class FeedBackPresenter extends RxPresenter<FeedBackView> {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public FeedBackPresenter(RetrofitHelper paramRetrofitHelper) {
        this.mRetrofitHelper = paramRetrofitHelper;
    }

    public void doFeedBack(String authorization,int fp_id,int type ,String solution) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.doFeedBack(authorization,fp_id,type,solution)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseSubscriber(getView()) {
                            @Override
                            public void onSuccess(BaseBean param) {
                                if (getView() != null&&param!=null) {
                                    getView().onFeedBackCallback(param);
                                }
                            }
                        })
        );
    }
    public void getErrorList(String authorization) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getErrorList(authorization)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<ErrorListBean>(getView()) {
                            @Override
                            public void onSuccess(ErrorListBean param) {
                                if (getView() != null&&param!=null) {
                                    getView().onErrorListBeanCallback(param);
                                }
                            }
                        })
        );
    }
}
