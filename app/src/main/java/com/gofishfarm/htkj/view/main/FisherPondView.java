package com.gofishfarm.htkj.view.main;

import com.gofishfarm.htkj.base.BaseView;
import com.gofishfarm.htkj.bean.FishDevciceBean;
import com.gofishfarm.htkj.bean.FishPondBean;
import com.gofishfarm.htkj.bean.MyTimeBean;
import com.gofishfarm.htkj.bean.OnLookBean;

/**
 * MrLiu253@163.com
 *
 * @time 2018/12/31
 */
public interface FisherPondView extends BaseView {

    void onFastFishing();

    void onLookOn();

    void onFishPondDataCallback(FishPondBean fishPondBean);

    /**
     * @param fishDevciceBean
     * @param type            1==快速开钓 2=渔场
     */
    void onFishDeviceDataCallback(FishDevciceBean fishDevciceBean, int type);

    void onOnLookDataCallback(OnLookBean onLookBean);

    void onMyTimeBeanDataCallback(MyTimeBean myTimeBean);


}
