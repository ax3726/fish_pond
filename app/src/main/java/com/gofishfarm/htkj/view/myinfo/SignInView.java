package com.gofishfarm.htkj.view.myinfo;

import com.gofishfarm.htkj.base.BaseView;
import com.gofishfarm.htkj.bean.MissionBean;
import com.gofishfarm.htkj.bean.MissionInfoBean;
import com.gofishfarm.htkj.bean.SingnInBean;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/12
 */
public interface SignInView extends BaseView {
    void onCallbackResult(SingnInBean param);

    void onMissionCallback(MissionBean param);

    void onDoMissionCallback(MissionInfoBean param);
}
