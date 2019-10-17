package com.gofishfarm.htkj.module;

import android.app.Service;

import com.gofishfarm.htkj.module.scope.ServiceScope;

import dagger.Module;
import dagger.Provides;


/**
 * 描述:Activity模型
 */
@Module
public class ServiceModule
{
  private Service mService;

  public ServiceModule(Service paramService)
  {
    this.mService = paramService;
  }
  
  @Provides
  @ServiceScope
  public Service provideActivity()
  {
    return this.mService;
  }
}
