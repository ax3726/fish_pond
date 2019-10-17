package com.gofishfarm.htkj.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.bean.HomepageBean;

import java.util.List;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/14
 */
public class HomepageAdapter extends BaseQuickAdapter<HomepageBean.UserFishplatBean, BaseViewHolder> {


    public HomepageAdapter(int layoutResId, @Nullable List<HomepageBean.UserFishplatBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomepageBean.UserFishplatBean item) {
        item.getFishery_name();
        helper.setText(R.id.item_homepage_time_tv, item.getTime());
        helper.setText(R.id.item_homepage_conent_tv, item.getFishery_name());
        helper.setText(R.id.item_conent_tv, item.getRewards());
        if (item.isOn()) {
            helper.setGone(R.id.item_homepage_onlookers_tv, true);
        } else {
            helper.setGone(R.id.item_homepage_onlookers_tv, false);
        }
        if (helper.getLayoutPosition() == getData().size() - 1) {
            helper.setVisible(R.id.item_homepage_middle_v, false);
        } else {
            helper.setVisible(R.id.item_homepage_middle_v, true);
        }

    }

}
