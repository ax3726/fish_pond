package com.gofishfarm.htkj.base;


import com.gofishfarm.htkj.event.RxBus;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by zzq on 2016/12/20.
 */
public class RxPresenter<V extends BaseView> extends BasePresenter<V> {
    protected V mView;
    private CompositeDisposable mCompositeDisposable;

//    protected <K> void addRxBusSubscribe(Class<K> paramClass, Consumer<K> paramConsumer) {
//        if (this.mCompositeDisposable == null) {
//            this.mCompositeDisposable = new CompositeDisposable();
//        }
//        this.mCompositeDisposable.add(RxBus.INSTANCE.toDefaultFlowable(paramClass, paramConsumer));
//    }

    protected void addSubscribe(Disposable paramDisposable) {
        if (this.mCompositeDisposable == null) {
            this.mCompositeDisposable = new CompositeDisposable();
        }
        this.mCompositeDisposable.add(paramDisposable);
    }

    public void attachView(V paramV) {
        this.mView = paramV;
    }

    public void detachView() {
        this.mView = null;
        unSubscribe();
    }

    private void unSubscribe() {
        if (this.mCompositeDisposable != null) {
            this.mCompositeDisposable.dispose();
        }
    }

    public V getView() {
        return this.mView;
    }


    /**
     * 删除
     *
     * @param paramDisposable disposable
     */
    protected boolean remove(Disposable paramDisposable) {
        return (this.mCompositeDisposable != null) && (this.mCompositeDisposable.remove(paramDisposable));
    }


}