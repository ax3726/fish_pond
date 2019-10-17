package com.gofishfarm.htkj.presenter.main.fishingpage;

import com.gofishfarm.htkj.base.BaseObjectSubscriber;
import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.base.retrofit.RxHelper;
import com.gofishfarm.htkj.bean.FishGuideFinishBean;
import com.gofishfarm.htkj.view.main.fishingpage.FishGuideActivityView;

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
public class FishGuideActivityPresenter extends RxPresenter<FishGuideActivityView> {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public FishGuideActivityPresenter(RetrofitHelper paramRetrofitHelper) {
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
}
