package com.gofishfarm.htkj.module;

import android.app.Activity;


import com.gofishfarm.htkj.module.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;


/**
 * 描述:Activity模型
 */
@Module
public class ActivityModule
{
  private Activity mActivity;
  
  public ActivityModule(Activity paramActivity)
  {
    this.mActivity = paramActivity;
  }
  
  @Provides
  @ActivityScope
  public Activity provideActivity()
  {
    return this.mActivity;
  }
}
