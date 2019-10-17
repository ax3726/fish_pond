package com.gofishfarm.htkj.view.login;

import com.gofishfarm.htkj.base.BaseView;
import com.gofishfarm.htkj.bean.SmsBean;
import com.gofishfarm.htkj.bean.UserInfoBean;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/3
 */
public interface LoginView extends BaseView {

    void onInputSize(int length);

    void onPlatformStart();

    void onPlatformComplete(SHARE_MEDIA platform, Map<String,String> data);

    void onPlatformError();

    void onPlatformCancel(SHARE_MEDIA platform);

    void onCallbackResult(SmsBean paramT);

    void onLoginCallbackResult(UserInfoBean param);
}
