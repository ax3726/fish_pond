package com.gofishfarm.htkj.base.support;


import com.gofishfarm.htkj.base.BaseView;
import com.gofishfarm.htkj.base.RxPresenter;

/**
 * MrLiu253@163.com
 *
 * @time 2018/7/30
 */
public  interface MvpCallback<V extends BaseView, P extends RxPresenter<V>>
{
  P createPresenter();

  V createView();

  P getMvpPresenter();

  void setMvpPresenter(P presenter);

  V getMvpView();

  void setMvpView(V view);

}