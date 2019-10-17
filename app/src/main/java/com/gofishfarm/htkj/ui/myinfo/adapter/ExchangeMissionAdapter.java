package com.gofishfarm.htkj.ui.myinfo.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.bean.MissionBean;
import com.gofishfarm.htkj.image.GlideApp;
import com.gofishfarm.htkj.utils.AppUtils;

import java.util.List;

/**
 * @author：MrHu
 * @Date ：2019-01-14
 * @Describtion ：
 */
public class ExchangeMissionAdapter extends BaseQuickAdapter<MissionBean.InfoBean, BaseViewHolder> {

    public ExchangeMissionAdapter(int layoutResId, @Nullable List<MissionBean.InfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MissionBean.InfoBean item) {
        GlideApp.with(AppUtils.getAppContext())
                .load(item.getThumbnail())
                .error(R.drawable.my_touxiang)
                .placeholder(R.drawable.my_touxiang)
                .into((ImageView) helper.getView(R.id.iv_coin_img));

        helper.setText(R.id.tv_coin_value, "+" + item.getIntegration())
                .setText(R.id.tv_coin_title, item.getName());

        for (int i = 0; i < getData().size(); i++) {
            if (item.isAccomplished()) {
                helper.setText(R.id.tv_coin_state, "已领取");
            } else {
                helper.setText(R.id.tv_coin_state, "领取");
            }
        }
    }
}
