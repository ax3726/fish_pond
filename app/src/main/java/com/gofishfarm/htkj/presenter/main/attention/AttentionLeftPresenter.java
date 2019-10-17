package com.gofishfarm.htkj.presenter.main.attention;

import android.text.Editable;
import android.text.TextWatcher;

import com.gofishfarm.htkj.base.BaseObjectSubscriber;
import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.base.retrofit.RxHelper;
import com.gofishfarm.htkj.bean.FollowListBean;
import com.gofishfarm.htkj.view.main.attention.AttentionLeftView;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * MrLiu253@163.com
 *
 * @time 2018/12/31
 */
public class AttentionLeftPresenter extends RxPresenter<AttentionLeftView> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public AttentionLeftPresenter(RetrofitHelper paramRetrofitHelper) {
        this.mRetrofitHelper = paramRetrofitHelper;
    }


    public void getFollowListBeanData(String authorization, int page) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getFollowListBeanData(authorization, page)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<FollowListBean>(getView()) {
                            @Override
                            public void onSuccess(FollowListBean paramT) {
                                if (getView() != null && paramT != null) {
                                    getView().onFollowListBeanDataCallback(paramT);
                                }
                            }
                        })
        );
    }

    public void getSerchFollowListBeanData(String authorization, String fields) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getSerchFollowListBeanData(authorization, fields)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<FollowListBean>(getView()) {
                            @Override
                            public void onSuccess(FollowListBean paramT) {
                                if (getView() != null && paramT != null) {
                                    getView().onSearchBeanDataCallback(paramT);
                                }
                            }
                        })
        );
    }



    public TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (getView() != null) {
                getView().onInputSize(s.length());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
