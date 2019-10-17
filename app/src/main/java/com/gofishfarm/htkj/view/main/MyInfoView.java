package com.gofishfarm.htkj.view.main;

import com.gofishfarm.htkj.base.BaseView;
import com.gofishfarm.htkj.bean.DoSignBean;
import com.gofishfarm.htkj.bean.UserDataBean;

/**
 * MrLiu253@163.com
 *
 * @time 2018/12/31
 */
public interface MyInfoView extends BaseView {
    void onCallbackResult(UserDataBean paramT);

    void onDoSignCallbackResult(DoSignBean paramT);
}
