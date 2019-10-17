package com.gofishfarm.htkj.view.feedback;

import com.gofishfarm.htkj.base.BaseView;
import com.gofishfarm.htkj.bean.BaseBean;
import com.gofishfarm.htkj.bean.ErrorListBean;
import com.gofishfarm.htkj.bean.TemporarykeyBean;
import com.gofishfarm.htkj.bean.UserInformationBean;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/9
 */
public interface FeedBackView extends BaseView {

    void onFeedBackCallback(BaseBean param);

    void onErrorListBeanCallback(ErrorListBean param);

}
