package com.gofishfarm.htkj.ui.main.fishingpage;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gofishfarm.htkj.App;
import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.bean.ChatMesgBean;
import com.gofishfarm.htkj.event.PlayMessageEvent;
import com.gofishfarm.htkj.image.GlideApp;
import com.gofishfarm.htkj.utils.AppUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: MrHu
 * Date: 2019-03-27
 * Time: 下午 11:13
 *
 * @Description:
 */
public class ChatAdapter extends BaseQuickAdapter<ChatMesgBean, BaseViewHolder> {

    public ChatAdapter(int layoutResId, @Nullable LinkedList<ChatMesgBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChatMesgBean item) {
        String s = item.getName() + " : ";
        String ss = item.getMsg();
        if (ss.length() > 20) {
            ss = ss.substring(0, 20);
//            ss=String.format("%s:%s",ss.substring(0,20),"...");
        }
        helper.setText(R.id.tv_chat_item, Html.fromHtml(App.getInstance().getResources().getString(R.string.chat_content, s, ss)));
        GlideApp.with(AppUtils.getAppContext())
                .load(item.getAvatar())
                .placeholder(R.drawable.my_touxiang)
                .error(R.drawable.my_touxiang)
                .into((ImageView) helper.getView(R.id.iv_chat_fisher));
    }
}
