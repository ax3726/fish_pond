package com.gofishfarm.htkj.view.main.fishingpage;

import com.gofishfarm.htkj.base.BaseView;
import com.gofishfarm.htkj.bean.MyTimeBean;
import com.gofishfarm.htkj.bean.OrderBean;

/**
 * MrLiu253@163.com
 *
 * @time 2018/12/31
 */
public interface RechargeView extends BaseView {
    void onMyTimeBeanDataCallback(MyTimeBean myTimeBean);

    void onOrderBeanDataCallback(OrderBean mOrderBean);


}
