package com.gofishfarm.htkj.presenter.start;

import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.base.retrofit.RxHelper;
import com.gofishfarm.htkj.view.start.SplashView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/8
 */
public class SplashPresenter extends RxPresenter<SplashView> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public SplashPresenter(RetrofitHelper paramRetrofitHelper) {
        this.mRetrofitHelper = paramRetrofitHelper;
    }

    Disposable subscribe;

    public void setCountDown() {
        final Long count = 2L;
        subscribe = Flowable.interval(0, 1, TimeUnit.SECONDS)//参数一：延时0秒；参数二：每隔1秒开始执行
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return count - aLong;
                    }
                })
                .take(count + 1)//设置循环次数
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe((Consumer)new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (SplashPresenter.this.getView() != null)
                            SplashPresenter.this.getView().showCountDown(aLong.intValue());
                    }
                });
        addSubscribe(subscribe);
    }

    public void removeCountDown() {
        if (subscribe != null)
            remove(subscribe);
    }
}
