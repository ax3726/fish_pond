package com.gofishfarm.htkj.view.myinfo;

import com.gofishfarm.htkj.base.BaseView;
import com.gofishfarm.htkj.bean.BaseBean;
import com.gofishfarm.htkj.bean.TemporarykeyBean;
import com.gofishfarm.htkj.bean.UserInformationBean;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/9
 */
public interface UserInformationView extends BaseView {
    void onCallbackResult(UserInformationBean param);

    void onSaveCallbackResult(UserInformationBean param);

    void onPlatformStart();

    void onPlatformComplete(SHARE_MEDIA platform, Map<String,String> data);

    void onPlatformError();

    void onPlatformCancel(SHARE_MEDIA platform);

    void onBindingWechatCallbackResult(BaseBean param);

    void onBindingSinaCallbackResult(BaseBean param);

    void OnPicturesClick();

    void OnPhotoClick();

    void onTemporarykeyCallbackResult(TemporarykeyBean paramT);
}
