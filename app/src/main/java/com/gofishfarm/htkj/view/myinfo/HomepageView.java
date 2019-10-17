package com.gofishfarm.htkj.view.myinfo;

import com.gofishfarm.htkj.base.BaseView;
import com.gofishfarm.htkj.bean.FocousOrNotBean;
import com.gofishfarm.htkj.bean.HomepageBean;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/13
 */
public interface HomepageView extends BaseView {
    void onCallbackResult(HomepageBean param);

    void onAttentionCallbackResult(FocousOrNotBean param);

    void onCancelCallbackResult(FocousOrNotBean param);

}
