package com.gofishfarm.htkj.ui.feedback;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.bean.FeedBackStateBean;

import java.util.List;

public class FeedBackAdapter extends BaseQuickAdapter<FeedBackStateBean, BaseViewHolder> {

    public FeedBackAdapter(int layoutResId, @Nullable List<FeedBackStateBean> data) {
        super(R.layout.item_feedback, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FeedBackStateBean item) {
        if (item.isChecked()) {
            helper.setBackgroundRes(R.id.rb_left,R.drawable.white_check);
        } else {
            helper.setBackgroundRes(R.id.rb_left,R.drawable.grey_check);
        }
        helper.setText(R.id.tv_error_content, item.getName());

    }
}
