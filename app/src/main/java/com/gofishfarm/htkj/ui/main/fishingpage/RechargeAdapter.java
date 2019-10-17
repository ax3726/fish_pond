package com.gofishfarm.htkj.ui.main.fishingpage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.bean.MyTimeBean;
import com.gofishfarm.htkj.common.OnRecyclerViewItemClickListener;

import java.util.ArrayList;

/**
 * @author：MrHu
 * @Date ：2019-01-07
 * @Describtion ：
 */
public class RechargeAdapter extends RecyclerView.Adapter {
    private Context context;
    ArrayList<MyTimeBean.PackagesBean> datas;
    ArrayList<Boolean> status;

    public RechargeAdapter(Context context, ArrayList<MyTimeBean.PackagesBean> datas, ArrayList<Boolean> status) {
        this.context = context;
        this.datas = datas;
        this.status = status;
    }

    public OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.item_pay_set, viewGroup, false);
        return new MyViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof MyViewHolder) {
            ((MyViewHolder) viewHolder).tv_set_money.setText("¥" + datas.get(i).getPrice());
            ((MyViewHolder) viewHolder).tv_set_time.setText(datas.get(i).getTime() + "");
            ((MyViewHolder) viewHolder).tv_save_value.setText(datas.get(i).getThrift_price() + "元");
            if (status.get(i) == true) {
                ((MyViewHolder) viewHolder).iv_select.setImageResource(R.drawable.cz_label_y);
            } else {
                ((MyViewHolder) viewHolder).iv_select.setImageResource(R.drawable.cz_label_g);
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
        ImageView iv_select;
        TextView tv_set_time_unit;
        TextView tv_set_time;
        TextView tv_set_money;
        TextView tv_save_string;
        TextView tv_save_value;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_select = (ImageView) itemView.findViewById(R.id.iv_select);
            tv_set_time_unit = (TextView) itemView.findViewById(R.id.tv_set_time_unit);
            tv_set_time = (TextView) itemView.findViewById(R.id.tv_set_time);
            tv_set_money = (TextView) itemView.findViewById(R.id.tv_set_money);
            tv_save_string = (TextView) itemView.findViewById(R.id.tv_save_string);
            tv_save_value = (TextView) itemView.findViewById(R.id.tv_save_value);
        }
    }
}
