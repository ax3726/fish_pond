package com.gofishfarm.htkj.presenter.main.fishingpage;

import com.gofishfarm.htkj.base.BaseObjectSubscriber;
import com.gofishfarm.htkj.base.BaseSubscriber;
import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.base.retrofit.RxHelper;
import com.gofishfarm.htkj.bean.BaseBean;
import com.gofishfarm.htkj.bean.FishingShareBean;
import com.gofishfarm.htkj.bean.HelpBean;
import com.gofishfarm.htkj.view.main.fishingpage.UserFishingActivityView;
import com.gofishfarm.htkj.view.main.fishingpage.UserFishingNewCommerView;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import retrofit2.http.Field;

/**
 * MrLiu253@163.com
 *
 * @time 2018/12/31
 */
public class UserFishingNewCommerPresenter extends RxPresenter<UserFishingNewCommerView> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public UserFishingNewCommerPresenter(RetrofitHelper paramRetrofitHelper) {
        this.mRetrofitHelper = paramRetrofitHelper;
    }

    public void getFishShareData(String Authorization) {

        addSubscribe(
                (Disposable) this.mRetrofitHelper.getFishShareData(Authorization)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<FishingShareBean>(getView()) {
                            @Override
                            public void onSuccess(FishingShareBean paramT) {
                                if (getView() != null) {
                                    getView().onFishingShareDataCallback(paramT);
                                }
                            }
                        })
        );
    }

    public void getHelpBean(String Authorization, int type, int fp_id) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getHelpBean(Authorization,  type,  fp_id)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<HelpBean>(getView()) {
                            @Override
                            public void onSuccess(HelpBean paramT) {
                                if (getView() != null) {
                                    getView().onHelpBeanDataCallback(paramT);
                                }
                            }
                        })
        );
    }

}
