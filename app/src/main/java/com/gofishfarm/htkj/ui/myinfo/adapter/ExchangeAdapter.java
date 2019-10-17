package com.gofishfarm.htkj.ui.myinfo.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.bean.ExchangeRecordBean;

import java.util.List;

/**
 * @author：MrHu
 * @Date ：2019-01-14
 * @Describtion ：
 */
public class ExchangeAdapter extends BaseQuickAdapter<ExchangeRecordBean.ListBean, BaseViewHolder> {

    public ExchangeAdapter(int layoutResId, @Nullable List<ExchangeRecordBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExchangeRecordBean.ListBean item) {
        helper.setText(R.id.tv_top, item.getTitle());
        helper.setText(R.id.tv_bottom, item.getCreated_at())
                .setText(R.id.tv_right, item.getIntegration());
    }
}
