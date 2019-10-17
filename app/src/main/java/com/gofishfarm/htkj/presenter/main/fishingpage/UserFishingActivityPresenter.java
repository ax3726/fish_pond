package com.gofishfarm.htkj.presenter.main.fishingpage;

import com.gofishfarm.htkj.base.BaseObjectSubscriber;
import com.gofishfarm.htkj.base.BaseSubscriber;
import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.base.retrofit.RxHelper;
import com.gofishfarm.htkj.bean.BaseBean;
import com.gofishfarm.htkj.bean.FishingShareBean;
import com.gofishfarm.htkj.view.main.fishingpage.UserFishingActivityView;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * MrLiu253@163.com
 *
 * @time 2018/12/31
 */
public class UserFishingActivityPresenter extends RxPresenter<UserFishingActivityView> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public UserFishingActivityPresenter(RetrofitHelper paramRetrofitHelper) {
        this.mRetrofitHelper = paramRetrofitHelper;
    }

//    public void getFishPondData() {

//        addSubscribe(
//                (Disposable) this.mRetrofitHelper.getFishPondData()
//                        .compose(RxHelper.io_main())
//                        .subscribeWith(new BaseObjectSubscriber<FishPondBean>(getView()) {
//                            @Override
//                            public void onSuccess(FishPondBean paramT) {
//                                if (getView() != null) {
//                                    getView().onFishPondDataCallback(paramT);
//                                }
//                            }
//                        })
//        );
//
//    }

    public void deleteFinshingOut(String Authorization, int $fp_id) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.deleteFinshingOut(Authorization, $fp_id)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseSubscriber(getView()) {
                            @Override
                            public void onSuccess(BaseBean param) {
                                if (getView() != null) {
                                    getView().showLog(param);
                                }
                            }
                        })
        );
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

    public void getEquipErrorData(String Authorization,String fp_id) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getEquipErrorData(Authorization, fp_id)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseSubscriber(getView()) {
                            @Override
                            public void onSuccess(BaseBean paramT) {
                                if (getView() != null) {
                                    getView().onEquipErrorDataCallback(paramT);
                                }
                            }
                        })
        );
    }
}
