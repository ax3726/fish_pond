package com.gofishfarm.htkj.base.support;

import android.os.Bundle;

import com.gofishfarm.htkj.base.BaseView;
import com.gofishfarm.htkj.base.RxPresenter;


/**
 * MrLiu253@163.com
 *
 * @time 2018/7/30
 */
public class ActivityMvpDelegateImpl<V extends BaseView, P extends RxPresenter<V>> implements ActivityMvpDelegate<V, P> {
    private ProxyMvpCallback<V, P> proxyMvpCallback;

    public ActivityMvpDelegateImpl(MvpCallback<V, P> mvpCallback) {
        this.proxyMvpCallback = new ProxyMvpCallback<>(mvpCallback);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.proxyMvpCallback.createPresenter();
        this.proxyMvpCallback.createView();
        this.proxyMvpCallback.attachView();


    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        this.proxyMvpCallback.detachView();
    }
}

