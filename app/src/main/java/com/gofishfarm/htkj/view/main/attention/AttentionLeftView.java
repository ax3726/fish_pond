package com.gofishfarm.htkj.view.main.attention;

import com.gofishfarm.htkj.base.BaseView;
import com.gofishfarm.htkj.bean.FollowListBean;

/**
 * MrLiu253@163.com
 *
 * @time 2018/12/31
 */
public interface AttentionLeftView extends BaseView {


    void onSearchBeanDataCallback(FollowListBean mFollowListBean);

    void onFollowListBeanDataCallback(FollowListBean mFollowListBean);


    void onInputSize(int length);

}
