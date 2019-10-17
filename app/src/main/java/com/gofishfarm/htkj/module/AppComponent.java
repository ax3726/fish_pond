package com.gofishfarm.htkj.module;

import android.content.Context;


import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Component;


/**
 * 描述:AppComponent
 */
@Component(modules={AppModule.class, httpModule.class})
@Singleton
public  interface AppComponent
{
  Context getContext();
  
  RetrofitHelper getRetrofitHelper();
}
