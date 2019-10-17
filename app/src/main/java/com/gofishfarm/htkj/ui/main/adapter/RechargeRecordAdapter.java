package com.gofishfarm.htkj.ui.main.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.bean.OrderRecordNewBean;

import java.util.List;


/**
 * @author：MrHu
 * @Date ：2019-01-10
 * @Describtion ：
 */
public class RechargeRecordAdapter extends BaseQuickAdapter<OrderRecordNewBean.ListBean, BaseViewHolder> {

    public RechargeRecordAdapter(int layoutResId, @Nullable List<OrderRecordNewBean.ListBean> data) {
//        super(layoutResId, data);
        super(R.layout.item_records_common, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderRecordNewBean.ListBean item) {
        helper.setText(R.id.tv_top, item.getTitle());
        helper.setText(R.id.tv_bottom, item.getCreated_at())
                .setText(R.id.tv_right, item.getTimes());
    }

}
