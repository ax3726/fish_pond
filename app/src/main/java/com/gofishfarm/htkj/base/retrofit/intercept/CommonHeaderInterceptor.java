package com.gofishfarm.htkj.base.retrofit.intercept;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 设置缓存策略
 * author: 康栋普
 * date: 2018/5/24
 */

public class CommonHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request authorised = originalRequest.newBuilder()
                .header("api-version", "v1")
//                .header("Authorization", SharedUtils.getInstance().getString("Authorization", ""))
                .build();
        return chain.proceed(authorised);

    }
}
