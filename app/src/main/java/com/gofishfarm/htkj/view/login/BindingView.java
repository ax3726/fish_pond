package com.gofishfarm.htkj.view.login;

import com.gofishfarm.htkj.base.BaseView;
import com.gofishfarm.htkj.bean.SmsBean;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/5
 */
public interface BindingView extends BaseView {
    void onInputSize(int length);

    void onCallbackResult(SmsBean param);
}
