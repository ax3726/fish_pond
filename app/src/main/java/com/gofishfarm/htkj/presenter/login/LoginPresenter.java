package com.gofishfarm.htkj.presenter.login;

import android.text.Editable;
import android.text.TextWatcher;

import com.gofishfarm.htkj.base.BaseObjectSubscriber;
import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.base.retrofit.RxHelper;
import com.gofishfarm.htkj.bean.SmsBean;
import com.gofishfarm.htkj.bean.UserInfoBean;
import com.gofishfarm.htkj.view.login.LoginView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/3
 */
public class LoginPresenter extends RxPresenter<LoginView> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public LoginPresenter(RetrofitHelper paramRetrofitHelper) {
        this.mRetrofitHelper = paramRetrofitHelper;
    }

    public void getSMS(String phone) {

        addSubscribe(
                (Disposable) this.mRetrofitHelper.getSMS(phone)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<SmsBean>(getView()) {
                            @Override
                            public void onSuccess(SmsBean paramT) {
                                if (getView() != null) {
                                    getView().onCallbackResult(paramT);
                                }
                            }
                        })
        );

    }
    public void getLogin(String wechat_id,String nick_name,String avatar,String gender,String imei,int type) {

        addSubscribe((Disposable) this.mRetrofitHelper.getLogin(wechat_id,nick_name,avatar,gender,imei,type)
                .compose(RxHelper.io_main())
                .subscribeWith(new BaseObjectSubscriber<UserInfoBean>(getView()) {
                    @Override
                    public void onSuccess(UserInfoBean param) {
                        if (getView() != null) {
                            getView().onLoginCallbackResult(param);
                        }
                    }
                }));

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

    public UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            if (getView() != null) {
                getView().onPlatformStart();
            }
        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            if (getView() != null) {
                getView().onPlatformComplete(platform, data);
            }
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            if (getView() != null) {
                getView().onPlatformError();
            }
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            if (getView() != null) {
                getView().onPlatformCancel(platform);
            }
        }
    };


}
