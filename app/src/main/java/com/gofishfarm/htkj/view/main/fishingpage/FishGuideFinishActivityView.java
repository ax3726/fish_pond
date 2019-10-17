package com.gofishfarm.htkj.view.main.fishingpage;

import com.gofishfarm.htkj.base.BaseView;
import com.gofishfarm.htkj.bean.FishDevciceBean;
import com.gofishfarm.htkj.bean.FishGuideFinishBean;
import com.gofishfarm.htkj.bean.OnLookBean;

/**
 * Created by Android Studio.
 * User: MrHu
 * Date: 2019-03-20
 * Time: 下午 01:49
 *
 * @Description:
 */
public interface FishGuideFinishActivityView extends BaseView {

    void onFishGuideFinishData(FishGuideFinishBean fishGuideFinishBean);

    void onFishDeviceDataCallback(FishDevciceBean fishDevciceBean);

    void onOnLookDataCallback(OnLookBean onLookBean);

}
