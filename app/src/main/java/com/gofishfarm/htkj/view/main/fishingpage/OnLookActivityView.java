package com.gofishfarm.htkj.view.main.fishingpage;

import com.gofishfarm.htkj.base.BaseView;
import com.gofishfarm.htkj.bean.FocousOrNotBean;
import com.gofishfarm.htkj.bean.OnLookBean;
import com.gofishfarm.htkj.bean.OnLookFocousBean;

/**
 * MrLiu253@163.com
 *
 * @time 2018/12/31
 */
public interface OnLookActivityView extends BaseView {

    void OnLookFocousBeanData(OnLookFocousBean mOnLookFocousBean);

    void OnFocousBeanData(FocousOrNotBean mBaseBean);

    void OnDeFocousBeanData(FocousOrNotBean mBaseBean);

    void onOnLookDataCallback(OnLookBean onLookBean);

    void onLookSerchBeanDataCallback(OnLookBean onLookBean);
}
