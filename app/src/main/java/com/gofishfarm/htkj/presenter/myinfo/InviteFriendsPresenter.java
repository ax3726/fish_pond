package com.gofishfarm.htkj.presenter.myinfo;

import com.gofishfarm.htkj.base.BaseObjectSubscriber;
import com.gofishfarm.htkj.base.BaseSubscriber;
import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.base.retrofit.RxHelper;
import com.gofishfarm.htkj.bean.BaseBean;
import com.gofishfarm.htkj.bean.ShareBean;
import com.gofishfarm.htkj.view.myinfo.InviteFriendsView;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/9
 */
public class InviteFriendsPresenter extends RxPresenter<InviteFriendsView> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public InviteFriendsPresenter(RetrofitHelper paramRetrofitHelper) {
        this.mRetrofitHelper = paramRetrofitHelper;
    }

    public void getShare(String mAuthorization, String fisher_id) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getShare(mAuthorization,fisher_id)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<ShareBean>(getView()) {

                            @Override
                            public void onSuccess(ShareBean param) {
                                if (getView() != null&&param!=null) {
                                    getView().onCallbackResult(param);
                                }
                            }
                        })
        );
    }
}
