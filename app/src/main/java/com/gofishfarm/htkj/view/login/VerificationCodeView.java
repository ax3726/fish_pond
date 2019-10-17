package com.gofishfarm.htkj.view.login;

import com.gofishfarm.htkj.base.BaseView;
import com.gofishfarm.htkj.bean.BaseBean;
import com.gofishfarm.htkj.bean.SmsBean;
import com.gofishfarm.htkj.bean.UserInfoBean;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/3
 */
public interface VerificationCodeView extends BaseView {

    void onSMSResult(int i);

    void onInputCompleted(CharSequence s);

    void onCallbackResult(UserInfoBean param);

    void onSMSCallbackResult(SmsBean paramT);

    void onBindingCallbackResult(UserInfoBean param);
}
