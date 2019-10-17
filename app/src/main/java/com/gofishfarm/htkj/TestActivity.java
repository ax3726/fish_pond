package com.gofishfarm.htkj;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gofishfarm.htkj.widget.CompletedView;
import com.tencent.rtmp.ui.TXCloudVideoView;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {


    private TXCloudVideoView video_view;
    private Button btn_close;
    private LinearLayout ll_top_menu;
    private Toolbar toolbar;
    private TextView tv_msg_tips;
    private TextView tv_recharge;
    private ImageView iv_tip_close;
    private LinearLayout ll_msg_tip;
    private RecyclerView recl_chat;
    private LinearLayout li_chat;
    private ImageView iv_follow;
    private TextView tv_follow;
    private ImageView iv_onlookers;
    private TextView tv_onlookers;
    private ImageView iv_share;
    private LinearLayout ll_right_menu;
    private ImageView iv_guide;
    private ImageView iv_error;
    private Button btn_to_help;
    private LinearLayout ll_left_menu;
    private TextView tv_guide_content;
    private TextView tv_guide_close;
    private ConstraintLayout ll_guide;
    private CompletedView completedview_step1_1;
    private ConstraintLayout cl_step1;
    private CompletedView completedview_step2_1;
    private CompletedView completedview_step2_2;
    private CompletedView completedview_step2_3;
    private LinearLayout ll_step2;
    private CompletedView completedview_step3_1;
    private CompletedView completedview_step3_2;
    private ConstraintLayout cl_step3;
    private CompletedView completedview_step4_1;
    private CompletedView completedview_step4_2;
    private ConstraintLayout cl_step4;
    private CompletedView completedview_step5_1;
    private CompletedView completedview_step5_2;
    private ConstraintLayout cl_step5;
    private CompletedView completedview_step6_1;
    private CompletedView completedview_step6_2;
    private ConstraintLayout cl_step6;
    private RelativeLayout rl_fish_num;
    private RelativeLayout rl_fish_num_layout;
    private ImageView iv_chat;
    private TextView tv_get_fish_coin;
    private RelativeLayout rl_share_center;
    private Button btn_shere;
    private ImageView iv_share_cancel;
    private RelativeLayout rl_get_Fish;
    private EditText et_input_message;
    private TextView tv_confrim_btn;
    private LinearLayout ll_input_view;
    private RelativeLayout rl_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_fish_new_commer);
        initView();


    }


    private void initView() {
        video_view = (TXCloudVideoView) findViewById(R.id.video_view);
        btn_close = (Button) findViewById(R.id.btn_close);
        ll_top_menu = (LinearLayout) findViewById(R.id.ll_top_menu);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_msg_tips = (TextView) findViewById(R.id.tv_msg_tips);
        tv_recharge = (TextView) findViewById(R.id.tv_recharge);
        iv_tip_close = (ImageView) findViewById(R.id.iv_tip_close);
        ll_msg_tip = (LinearLayout) findViewById(R.id.ll_msg_tip);
        recl_chat = (RecyclerView) findViewById(R.id.recl_chat);
        li_chat = (LinearLayout) findViewById(R.id.li_chat);
        iv_follow = (ImageView) findViewById(R.id.iv_follow);
        tv_follow = (TextView) findViewById(R.id.tv_follow);
        iv_onlookers = (ImageView) findViewById(R.id.iv_onlookers);
        tv_onlookers = (TextView) findViewById(R.id.tv_onlookers);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        ll_right_menu = (LinearLayout) findViewById(R.id.ll_right_menu);
        iv_guide = (ImageView) findViewById(R.id.iv_guide);
        iv_error = (ImageView) findViewById(R.id.iv_error);
        btn_to_help = (Button) findViewById(R.id.btn_to_help);
        ll_left_menu = (LinearLayout) findViewById(R.id.ll_left_menu);
        tv_guide_content = (TextView) findViewById(R.id.tv_guide_content);
        tv_guide_close = (TextView) findViewById(R.id.tv_guide_close);
        ll_guide = (ConstraintLayout) findViewById(R.id.ll_guide);
        completedview_step1_1 = (CompletedView) findViewById(R.id.completedview_step1_1);
        cl_step1 = (ConstraintLayout) findViewById(R.id.cl_step1);
        completedview_step2_1 = (CompletedView) findViewById(R.id.completedview_step2_1);
        completedview_step2_2 = (CompletedView) findViewById(R.id.completedview_step2_2);
        completedview_step2_3 = (CompletedView) findViewById(R.id.completedview_step2_3);
        ll_step2 = (LinearLayout) findViewById(R.id.ll_step2);
        completedview_step3_1 = (CompletedView) findViewById(R.id.completedview_step3_1);
        completedview_step3_2 = (CompletedView) findViewById(R.id.completedview_step3_2);
        cl_step3 = (ConstraintLayout) findViewById(R.id.cl_step3);
        completedview_step4_1 = (CompletedView) findViewById(R.id.completedview_step4_1);
        completedview_step4_2 = (CompletedView) findViewById(R.id.completedview_step4_2);
        cl_step4 = (ConstraintLayout) findViewById(R.id.cl_step4);
        completedview_step5_1 = (CompletedView) findViewById(R.id.completedview_step5_1);
        completedview_step5_2 = (CompletedView) findViewById(R.id.completedview_step5_2);
        cl_step5 = (ConstraintLayout) findViewById(R.id.cl_step5);
        completedview_step6_1 = (CompletedView) findViewById(R.id.completedview_step6_1);
        completedview_step6_2 = (CompletedView) findViewById(R.id.completedview_step6_2);
        cl_step6 = (ConstraintLayout) findViewById(R.id.cl_step6);
        rl_fish_num = (RelativeLayout) findViewById(R.id.rl_fish_num);
        rl_fish_num_layout = (RelativeLayout) findViewById(R.id.rl_fish_num_layout);
        iv_chat = (ImageView) findViewById(R.id.iv_chat);
        tv_get_fish_coin = (TextView) findViewById(R.id.tv_get_fish_coin);
        rl_share_center = (RelativeLayout) findViewById(R.id.rl_share_center);
        btn_shere = (Button) findViewById(R.id.btn_shere);
        iv_share_cancel = (ImageView) findViewById(R.id.iv_share_cancel);
        rl_get_Fish = (RelativeLayout) findViewById(R.id.rl_get_Fish);
        et_input_message = (EditText) findViewById(R.id.et_input_message);
        tv_confrim_btn = (TextView) findViewById(R.id.tv_confrim_btn);
        ll_input_view = (LinearLayout) findViewById(R.id.ll_input_view);
        rl_root = (RelativeLayout) findViewById(R.id.rl_root);

        btn_close.setOnClickListener(this);
        tv_recharge.setOnClickListener(this);
        li_chat.setOnClickListener(this);
        iv_follow.setOnClickListener(this);
        iv_guide.setOnClickListener(this);
        iv_error.setOnClickListener(this);
        btn_to_help.setOnClickListener(this);
        completedview_step1_1.setOnClickListener(this);
        completedview_step2_1.setOnClickListener(this);
        completedview_step2_2.setOnClickListener(this);
        completedview_step2_3.setOnClickListener(this);
        completedview_step3_1.setOnClickListener(this);
        completedview_step3_2.setOnClickListener(this);
        completedview_step4_1.setOnClickListener(this);
        completedview_step4_2.setOnClickListener(this);
        completedview_step5_1.setOnClickListener(this);
        completedview_step5_2.setOnClickListener(this);
        completedview_step6_1.setOnClickListener(this);
        completedview_step6_2.setOnClickListener(this);
        iv_chat.setOnClickListener(this);
        btn_shere.setOnClickListener(this);
        rl_get_Fish.setOnClickListener(this);
        tv_confrim_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close:

                break;
            case R.id.tv_recharge:

                break;
            case R.id.li_chat:

                break;
            case R.id.iv_follow:

                break;
            case R.id.iv_guide:

                break;
            case R.id.iv_error:

                break;
            case R.id.btn_to_help:

                break;
            case R.id.completedview_step1_1:

                break;
            case R.id.completedview_step2_1:

                break;
            case R.id.completedview_step2_2:

                break;
            case R.id.completedview_step2_3:

                break;
            case R.id.completedview_step3_1:

                break;
            case R.id.completedview_step3_2:

                break;
            case R.id.completedview_step4_1:

                break;
            case R.id.completedview_step4_2:

                break;
            case R.id.completedview_step5_1:

                break;
            case R.id.completedview_step5_2:

                break;
            case R.id.completedview_step6_1:

                break;
            case R.id.completedview_step6_2:

                break;
            case R.id.iv_chat:

                break;
            case R.id.btn_shere:

                break;
            case R.id.rl_get_Fish:

                break;
            case R.id.tv_confrim_btn:

                break;
        }
    }

    private void submit() {
        // validate
        String message = et_input_message.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            Toast.makeText(this, "吐个槽呗~", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}
