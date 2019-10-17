package com.gofishfarm.htkj.presenter.login;

import com.gofishfarm.htkj.base.BaseObjectSubscriber;
import com.gofishfarm.htkj.base.BaseSubscriber;
import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.base.retrofit.RxHelper;
import com.gofishfarm.htkj.bean.BaseBean;
import com.gofishfarm.htkj.bean.SmsBean;
import com.gofishfarm.htkj.bean.UserInfoBean;
import com.gofishfarm.htkj.custom.VerificationAction;
import com.gofishfarm.htkj.event.Event;
import com.gofishfarm.htkj.event.RxBus;
import com.gofishfarm.htkj.view.login.VerificationCodeView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/3
 */
public class VerificationCodePresenter extends RxPresenter<VerificationCodeView> {

    private RetrofitHelper mRetrofitHelper;
    private Event.CountdownEvent countdownEvent = null;
    private Disposable subscribe;

    @Inject
    public VerificationCodePresenter(RetrofitHelper paramRetrofitHelper) {
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
                                    getView().onSMSCallbackResult(paramT);
                                }
                            }
                        })
        );

    }

    public void getLogin(String phone,String code,String imei) {

        addSubscribe((Disposable) this.mRetrofitHelper.getLogin(phone,code,imei)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<UserInfoBean>(getView()) {
                            @Override
                            public void onSuccess(UserInfoBean param) {
                                if (getView() != null) {
                                    getView().onCallbackResult(param);
                                }
                            }
                        }));

    }

    public void getBinding(String phone,String code,String mAuthorization) {

        addSubscribe((Disposable) this.mRetrofitHelper.getBinding(phone,code,mAuthorization)
                .compose(RxHelper.io_main())
                .subscribeWith(new BaseObjectSubscriber<UserInfoBean>(getView()) {

                    @Override
                    public void onSuccess(UserInfoBean param) {
                        if (getView() != null) {
                            getView().onBindingCallbackResult(param);
                        }
                    }
                }));

    }

    public VerificationAction.OnVerificationCodeChangedListener mOnVerificationCodeChangedListener = new VerificationAction.OnVerificationCodeChangedListener() {
        @Override
        public void onVerCodeChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void onInputCompleted(CharSequence s) {
            if (getView() != null) {
                getView().onInputCompleted(s);
            }
        }
    };

    /**
     * 倒计时
     */
    public void setCountDown() {
        final Long count = 60L;
        subscribe = Flowable.interval(0, 1, TimeUnit.SECONDS)//参数一：延时0秒；参数二：每隔1秒开始执行
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return count - aLong;
                    }
                })
                .take(count + 1)//设置循环次数
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(mDisposable);
        addSubscribe(subscribe);

    }

    Consumer mDisposable = new Consumer<Long>() {
        @Override
        public void accept(Long aLong) throws Exception {
            if (countdownEvent == null) {
                countdownEvent = new Event.CountdownEvent();
                countdownEvent.countdown = aLong.intValue();
            } else {
                countdownEvent.countdown = aLong.intValue();
            }
            RxBus.INSTANCE.post(countdownEvent);
            if (getView() != null) {
                getView().onSMSResult(aLong.intValue());
            }
        }
    };

}
