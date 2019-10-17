package com.gofishfarm.htkj.ui.main.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.bean.RankLfetBean;
import com.gofishfarm.htkj.image.GlideApp;
import com.gofishfarm.htkj.utils.AppUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * @author：MrHu
 * @Date ：2019-01-10
 * @Describtion ：
 */
public class RankleftAdapter extends BaseQuickAdapter<RankLfetBean.InfoBean, BaseViewHolder> {

    public RankleftAdapter(int layoutResId, @Nullable List<RankLfetBean.InfoBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, RankLfetBean.InfoBean item) {
        if (helper.getLayoutPosition() <= 2) {
            helper.setBackgroundRes(R.id.rl_img_left, R.drawable.bd_ranking_icon);
            helper.setTextColor(R.id.tv_rank_num, AppUtils.getResource().getColor(R.color.theme_yellow));
        } else {
            helper.setBackgroundRes(R.id.rl_img_left, R.drawable.shape_square_transparent);
            helper.setTextColor(R.id.tv_rank_num, AppUtils.getResource().getColor(R.color.black));
        }
        helper.setText(R.id.tv_rank_num, (helper.getLayoutPosition() + 1) + "");
        helper.setText(R.id.tv_nick_name, item.getNick_name())
                .setText(R.id.tv_fast_most, item.getTotal_fishing() + "条");
        GlideApp.with(AppUtils.getAppContext()).load(item.getAvatar()).placeholder(R.drawable.my_touxiang).error(R.drawable.my_touxiang).into((CircleImageView) helper.getView(R.id.civ_imag));
    }
}
