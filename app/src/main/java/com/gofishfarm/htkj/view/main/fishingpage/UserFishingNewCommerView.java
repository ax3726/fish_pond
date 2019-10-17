package com.gofishfarm.htkj.view.main.fishingpage;

import com.gofishfarm.htkj.base.BaseView;
import com.gofishfarm.htkj.bean.BaseBean;
import com.gofishfarm.htkj.bean.FishingShareBean;
import com.gofishfarm.htkj.bean.HelpBean;

/**
 * MrLiu253@163.com
 *
 * @time 2018/12/31
 */
public interface UserFishingNewCommerView extends BaseView {

    void showLog(BaseBean param);

    void onFishingShareDataCallback(FishingShareBean param);

    void onHelpBeanDataCallback(HelpBean param);

    void onEquipErrorDataCallback(BaseBean param);

}
