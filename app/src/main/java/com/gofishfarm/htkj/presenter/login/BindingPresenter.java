package com.gofishfarm.htkj.presenter.login;

import android.text.Editable;
import android.text.TextWatcher;

import com.gofishfarm.htkj.base.BaseObjectSubscriber;
import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.base.retrofit.RxHelper;
import com.gofishfarm.htkj.bean.SmsBean;
import com.gofishfarm.htkj.view.login.BindingView;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/5
 */
public class BindingPresenter extends RxPresenter<BindingView> {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public BindingPresenter(RetrofitHelper paramRetrofitHelper) {
        this.mRetrofitHelper = paramRetrofitHelper;
    }

    public void getSMS(String phone) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getSMS(phone)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<SmsBean>(getView()) {
                            @Override
                            public void onSuccess(SmsBean param) {
                                if (getView() != null) {
                                    getView().onCallbackResult(param);
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
