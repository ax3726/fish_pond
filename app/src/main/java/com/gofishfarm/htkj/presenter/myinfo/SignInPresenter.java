package com.gofishfarm.htkj.presenter.myinfo;

import com.gofishfarm.htkj.base.BaseObjectSubscriber;
import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.base.retrofit.RxHelper;
import com.gofishfarm.htkj.bean.MissionBean;
import com.gofishfarm.htkj.bean.MissionInfoBean;
import com.gofishfarm.htkj.bean.SingnInBean;
import com.gofishfarm.htkj.view.myinfo.SignInView;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/12
 */
public class SignInPresenter extends RxPresenter<SignInView> {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public SignInPresenter(RetrofitHelper paramRetrofitHelper) {
        this.mRetrofitHelper = paramRetrofitHelper;
    }


    public void getSignIn(String authorization) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getSignIn(authorization)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<SingnInBean>(getView()) {
                            @Override
                            public void onSuccess(SingnInBean param) {
                                if (getView() != null && param != null) {
                                    getView().onCallbackResult(param);
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
}
