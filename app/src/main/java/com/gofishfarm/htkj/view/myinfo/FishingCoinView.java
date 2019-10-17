package com.gofishfarm.htkj.view.myinfo;

import com.gofishfarm.htkj.base.BaseView;
import com.gofishfarm.htkj.bean.BaseBean;
import com.gofishfarm.htkj.bean.FishingCoinBean;
import com.gofishfarm.htkj.bean.MallBean;
import com.gofishfarm.htkj.bean.MissionBean;
import com.gofishfarm.htkj.bean.MissionInfoBean;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/9
 */
public interface FishingCoinView extends BaseView {

    void onCallbackResult(FishingCoinBean param);

    void onExchangeCallback(BaseBean param);

    void onMissionCallback(MissionBean param);

    void onDoMissionCallback(MissionInfoBean param);

    void onMallBeanDataCallback(MallBean mallBean);

}
