package com.gofishfarm.htkj.presenter.myinfo;

import com.gofishfarm.htkj.base.BaseObjectSubscriber;
import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.base.retrofit.RxHelper;
import com.gofishfarm.htkj.bean.FocousOrNotBean;
import com.gofishfarm.htkj.bean.HomepageBean;
import com.gofishfarm.htkj.view.myinfo.HomepageView;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/12
 */
public class HomepagePresenter extends RxPresenter<HomepageView> {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public HomepagePresenter(RetrofitHelper paramRetrofitHelper) {
        this.mRetrofitHelper = paramRetrofitHelper;
    }

    public void getHomepage(String mAuthorization, String fisher_id) {

        addSubscribe(
                (Disposable) this.mRetrofitHelper.getHomepage(mAuthorization, fisher_id)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<HomepageBean>(getView()) {
                            @Override
                            public void onSuccess(HomepageBean param) {
                                if (getView() != null && param != null) {
                                    getView().onCallbackResult(param);
                                }
                            }
                        })
        );
    }

    public void getAttention(String authorization, String fisher_id) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getOnFocousData(authorization, fisher_id)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<FocousOrNotBean>(getView()) {
                            @Override
                            public void onSuccess(FocousOrNotBean param) {
                                if (getView() != null && param != null) {
                                    getView().onAttentionCallbackResult(param);
                                }
                            }
                        })
        );
    }

    public void getCancelAttention(String authorization, String focusId) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getCancelAttention(authorization, focusId)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<FocousOrNotBean>(getView()) {
                            @Override
                            public void onSuccess(FocousOrNotBean param) {
                                if (getView() != null && param != null) {
                                    getView().onCancelCallbackResult(param);
                                }
                            }
                        })
        );
    }
}
