package com.gofishfarm.htkj.ui.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.bean.FishPondBean;
import com.gofishfarm.htkj.common.OnRecyclerViewItemClickListener;
import com.gofishfarm.htkj.image.GlideApp;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：MrHu
 * @Date ：2019-01-07
 * @Describtion ：
 */
public class FishPondAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<FishPondBean.FisheriesBean> datas;

    public FishPondAdapter(Context context, ArrayList<FishPondBean.FisheriesBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    public OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_fish_pond, viewGroup, false);
        ;
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof MyViewHolder) {
            GlideApp
                    .with(context)
                    .load(datas.get(i).getThumbnail())
                    .placeholder(R.drawable.sy_zw)
                    .error(R.drawable.sy_zw)
                    .into(((MyViewHolder) viewHolder).iv_finsh_pond_item);
            ((MyViewHolder) viewHolder).tv_pond_name.setText(datas.get(i).getName());
            ((MyViewHolder) viewHolder).tv_pond_rest.setText("剩" + datas.get(i).getSurplus_capacity() + "个钓台");
            if (datas.get(i).getSurplus_capacity() <= 0) {
                ((MyViewHolder) viewHolder).tv_can_in.setText(context.getString(R.string.fish_go_follow));
                ((MyViewHolder) viewHolder).tv_can_in.setTextColor(context.getResources().getColor(R.color.font_gray));
            } else {
                ((MyViewHolder) viewHolder).tv_can_in.setText(context.getString(R.string.fish_go_in));
//                ((MyViewHolder) viewHolder).tv_can_in.setTextColor(context.getResources().getColor(R.color.font_gray));
                ((MyViewHolder) viewHolder).tv_can_in.setTextColor(context.getResources().getColor(R.color.theme_yellow));
            }
            ((MyViewHolder) viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击时间
                    mOnItemClickListener.onItemClick(v, i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView card_pond;
        ImageView iv_finsh_pond_item;
        TextView tv_pond_name;
        TextView tv_pond_rest;
        TextView tv_can_in;

        public MyViewHolder(View itemView) {
            super(itemView);
            card_pond = (CardView) itemView.findViewById(R.id.card_pond);
            iv_finsh_pond_item = (ImageView) itemView.findViewById(R.id.iv_finsh_pond_item);
            tv_pond_name = (TextView) itemView.findViewById(R.id.tv_pond_name);
            tv_pond_rest = (TextView) itemView.findViewById(R.id.tv_pond_rest);
            tv_can_in = (TextView) itemView.findViewById(R.id.tv_can_in);
        }
    }
}
