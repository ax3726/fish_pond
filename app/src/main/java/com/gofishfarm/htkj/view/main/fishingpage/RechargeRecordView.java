package com.gofishfarm.htkj.view.main.fishingpage;

import com.gofishfarm.htkj.base.BaseView;
import com.gofishfarm.htkj.bean.OrderRecordBean;
import com.gofishfarm.htkj.bean.OrderRecordNewBean;

/**
 * MrLiu253@163.com
 *
 * @time 2018/12/31
 */
public interface RechargeRecordView extends BaseView {

    void onRechargeRecordDataCallback(OrderRecordBean mOrderRecordBean);
    void onRechargeRecordNewDataCallback(OrderRecordNewBean mOrderRecordNewBean);

}
