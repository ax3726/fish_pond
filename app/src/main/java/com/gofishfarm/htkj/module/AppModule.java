package com.gofishfarm.htkj.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;


/**
 * 描述:App模型
 */
@Module
public class AppModule
{
  private Context mContext;

  public AppModule(Context paramContext)
  {
    this.mContext = paramContext;
  }

  @Provides
  public Context provideContext()
  {
    return this.mContext;
  }
}
