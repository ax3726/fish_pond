package com.gofishfarm.htkj.base.support;

import android.os.Bundle;

import com.gofishfarm.htkj.base.BaseView;
import com.gofishfarm.htkj.base.RxPresenter;

/**
 * MrLiu253@163.com
 *
 * @time 2018/7/30
 */
public  interface ActivityMvpDelegate<V extends BaseView, P extends RxPresenter<V>>
{
  void onCreate(Bundle savedInstanceState);

  void onStart();

  void onResume();

  void onPause();

  void onStop();

  void onDestroy();

  //扩展其它方法

}