package com.gofishfarm.htkj.module;



import com.gofishfarm.htkj.api.ApiConstants;
import com.gofishfarm.htkj.api.AppUrl;
import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.base.retrofit.OkHttpHelper;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 描述:Api网络模型
 */
@Module
public class httpModule
{
  public Retrofit createRetrofit(Builder paramBuilder, OkHttpClient paramOkHttpClient, String paramString)
  {
    return paramBuilder
            .baseUrl(paramString)
            .client(paramOkHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
  }

  @Provides
  @Singleton
  @AppUrl
  public Retrofit provideAppRetrofit(Builder paramBuilder, OkHttpClient paramOkHttpClient)
  {
    return createRetrofit(paramBuilder, paramOkHttpClient, ConfigApi.INFO);
  }

  @Provides
  @Singleton
  public ApiConstants provideAppService(@AppUrl Retrofit paramRetrofit)
  {
    return paramRetrofit.create(ApiConstants.class);
  }

  @Provides
  @Singleton
  public OkHttpClient provideOkHttpClient()
  {
    return OkHttpHelper.getInstance().getOkHttpClient();
  }

  @Provides
  @Singleton
  public Builder provideRetrofitBuilder()
  {
    return new Builder();
  }
  
  @Provides
  @Singleton
  public RetrofitHelper provideRetrofitHelper(ApiConstants paramApiConstants)
  {
    return new RetrofitHelper(paramApiConstants);
  }
}
