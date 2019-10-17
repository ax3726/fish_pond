package com.gofishfarm.htkj.view.main.fishingpage;

import com.gofishfarm.htkj.base.BaseView;
import com.gofishfarm.htkj.bean.BaseBean;
import com.gofishfarm.htkj.bean.FishingShareBean;

/**
 * MrLiu253@163.com
 *
 * @time 2018/12/31
 */
public interface UserFishingActivityView extends BaseView {

    void showLog(BaseBean param);

    void onFishingShareDataCallback(FishingShareBean param);

    void onEquipErrorDataCallback(BaseBean param);

}
