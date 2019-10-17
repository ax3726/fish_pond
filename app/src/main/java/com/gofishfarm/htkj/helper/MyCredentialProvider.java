package com.gofishfarm.htkj.helper;

import com.tencent.qcloud.core.auth.BasicLifecycleCredentialProvider;
import com.tencent.qcloud.core.auth.QCloudLifecycleCredentials;
import com.tencent.qcloud.core.auth.SessionQCloudCredentials;
import com.tencent.qcloud.core.common.QCloudClientException;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/15
 */
public class MyCredentialProvider extends BasicLifecycleCredentialProvider {

    // 然后解析响应，获取密钥信息
    String tmpSecretId;
    String tmpSecretKey;
    String sessionToken;
    long beginTime ;
    long expiredTime;

    public MyCredentialProvider(String tmpSecretId, String tmpSecretKey, String sessionToken, long beginTime, long expiredTime) {
        this.tmpSecretId = tmpSecretId;
        this.tmpSecretKey = tmpSecretKey;
        this.sessionToken = sessionToken;
        this.beginTime = beginTime;
        this.expiredTime = expiredTime;
    }

    @Override
    protected QCloudLifecycleCredentials fetchNewCredentials() throws QCloudClientException {
        return new SessionQCloudCredentials(tmpSecretId, tmpSecretKey, sessionToken, beginTime, expiredTime);
    }
}
