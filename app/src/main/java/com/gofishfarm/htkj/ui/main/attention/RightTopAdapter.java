package com.gofishfarm.htkj.ui.main.attention;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.bean.FansListBean;
import com.gofishfarm.htkj.image.GlideApp;
import com.gofishfarm.htkj.utils.AppUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * @author：MrHu
 * @Date ：2019-01-10
 * @Describtion ：
 */
public class RightTopAdapter extends BaseQuickAdapter<FansListBean.FansBean, BaseViewHolder> {

    public RightTopAdapter(int layoutResId, @Nullable List<FansListBean.FansBean> data) {
        super(R.layout.item_attention, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FansListBean.FansBean item) {
        if (helper.getLayoutPosition() == getData().size() - 1) {
            helper.setVisible(R.id.view_bottom, false);
        } else {
            helper.setVisible(R.id.view_bottom, true);
        }

        if (item.getMutual_focus() == 3) {
            helper.setText(R.id.tv_focous_state, "互相关注");
            helper.setTextColor(R.id.tv_focous_state, AppUtils.getResource().getColor(R.color.font_gray));
        } else if (item.getMutual_focus() == 1) {
            helper.setText(R.id.tv_focous_state, "立即关注");
            helper.setTextColor(R.id.tv_focous_state, AppUtils.getResource().getColor(R.color.theme_yellow));
        }else if (item.getMutual_focus() == 2) {
            helper.setText(R.id.tv_focous_state, "");
            helper.setTextColor(R.id.tv_focous_state, AppUtils.getResource().getColor(R.color.theme_yellow));
        }

        if (item.getStatus().equals("1")) {
            helper.setText(R.id.tv_state, "[离线]");
            helper.setTextColor(R.id.tv_state, AppUtils.getResource().getColor(R.color.font_gray));
        } else if (item.getStatus().equals("2")) {
            helper.setText(R.id.tv_state, "[在线]");
            helper.setTextColor(R.id.tv_state, AppUtils.getResource().getColor(R.color.font_green));
        } else if (item.getStatus().equals("3")) {
            helper.setText(R.id.tv_state, "[ 垂钓中]");
            helper.setTextColor(R.id.tv_state, AppUtils.getResource().getColor(R.color.font_green));
        } else if (item.getStatus().equals("4")) {
            helper.setText(R.id.tv_state, "[围观中]");
            helper.setTextColor(R.id.tv_state, AppUtils.getResource().getColor(R.color.font_green));
        }
        helper.setText(R.id.tv_nick_name, item.getNick_name())
                .setText(R.id.tv_fisher_id, "钓手号：" + item.getFisher_id());
//                .addOnClickListener(R.id.tv_focous_state);
        GlideApp.with(AppUtils.getAppContext()).load(item.getAvatar()).placeholder(R.drawable.my_touxiang).error(R.drawable.my_touxiang).into((CircleImageView) helper.getView(R.id.civ_imag));
    }
}
