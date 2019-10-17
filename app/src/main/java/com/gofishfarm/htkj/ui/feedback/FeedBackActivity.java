package com.gofishfarm.htkj.ui.feedback;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.base.BaseActivity;
import com.gofishfarm.htkj.bean.BaseBean;
import com.gofishfarm.htkj.bean.ErrorListBean;
import com.gofishfarm.htkj.bean.FeedBackStateBean;
import com.gofishfarm.htkj.presenter.feedback.FeedBackPresenter;
import com.gofishfarm.htkj.utils.SharedUtils;
import com.gofishfarm.htkj.view.feedback.FeedBackView;
import com.gyf.barlibrary.ImmersionBar;
import com.hjq.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FeedBackActivity extends BaseActivity<FeedBackView, FeedBackPresenter> implements FeedBackView {

    @Inject
    FeedBackPresenter mFeedBackPresenter;

    private ImageButton toolbar_left_ib;
    private TextView toolbar_title;
    private ImageView toolbar_right_iv;
    private ImageView toolbar_right_my_iv;
    private TextView toolbar_right_bt;
    private View toolbar_v;
    private TextView tv_tips;
    private RecyclerView rec_errors;
    private TextView tv_desc;
    private EditText et_solution;
    private FeedBackAdapter mFeedBackAdapter;
    private List<FeedBackStateBean> data = new ArrayList<>();
    private int fp_id = -1;
    private int type = -1;
    private String solution = "";

    @Override
    public FeedBackPresenter createPresenter() {
        return this.mFeedBackPresenter;
    }

    @Override
    public FeedBackView createView() {
        return this;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ImmersionBar.setTitleBar(this, findViewById(R.id.ui_toolbar));
        toolbar_left_ib = (ImageButton) findViewById(R.id.toolbar_left_ib);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_right_iv = (ImageView) findViewById(R.id.toolbar_right_iv);
        toolbar_right_my_iv = (ImageView) findViewById(R.id.toolbar_right_my_iv);
        toolbar_right_bt = (TextView) findViewById(R.id.toolbar_right_bt);
        toolbar_v = (View) findViewById(R.id.toolbar_v);
        tv_tips = (TextView) findViewById(R.id.tv_tips);
        rec_errors = (RecyclerView) findViewById(R.id.rec_errors);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        et_solution = (EditText) findViewById(R.id.et_solution);
        toolbar_right_bt.setOnClickListener(this);

        toolbar_left_ib.setVisibility(View.VISIBLE);
        toolbar_right_bt.setVisibility(View.VISIBLE);
        toolbar_left_ib.setOnClickListener(this);
        toolbar_right_bt.setOnClickListener(this);
        if (getResources() != null) {
            toolbar_right_bt.setTextColor(getResources().getColor(R.color.theme_yellow));
        }
        toolbar_right_bt.setText("提交");
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText("故障类型");
        Intent intent = getIntent();
        fp_id = intent.getIntExtra("fp_id", -1);
        initAdapter();
        initErroeData();
    }

    private void initErroeData() {
        mFeedBackPresenter.getErrorList(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, ""));
    }

    private void initAdapter() {
        mFeedBackAdapter = new FeedBackAdapter(R.layout.item_feedback, data);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rec_errors.setLayoutManager(manager);
        rec_errors.setAdapter(mFeedBackAdapter);
        mFeedBackAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < data.size(); i++) {
                    data.get(i).setChecked(false);
                }
                data.get(position).setChecked(true);
                type = data.get(position).getId();
                mFeedBackAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left_ib:
                finish();
                break;
            case R.id.toolbar_right_bt:
                feedBack();
                break;
            default:
                break;
        }
    }

    private void feedBack() {
        solution = et_solution.getText().toString().trim();
        if (type == -1) {
            ToastUtils.show("请选择故障类型");
            return;
        }
        showDialog("故障提交中...");
        mFeedBackPresenter.doFeedBack(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, ""), fp_id, type, solution);
    }

    @Override
    public void onFeedBackCallback(BaseBean param) {
        if (param.getCode() == 200) {
            dismissDialog();
            ToastUtils.show("您的故障已提交，我们正在加急处理，您可以前往其它钓台钓鱼！");
            finish();
        } else {
            dismissDialog();
            ToastUtils.show("提交失败，请重试");
        }
    }

    /**
     * 获取故障列表
     *
     * @param param
     */
    @Override
    public void onErrorListBeanCallback(ErrorListBean param) {
        if (null == param) {
            return;
        }
        for (int i = 0; i < param.getTypes().size(); i++) {
            FeedBackStateBean feedBackStateBean = new FeedBackStateBean();
            feedBackStateBean.setChecked(false);
            feedBackStateBean.setId(param.getTypes().get(i).getId());
            feedBackStateBean.setName(param.getTypes().get(i).getName());
            data.add(feedBackStateBean);
        }
        mFeedBackAdapter.notifyDataSetChanged();
    }

    @Override
    public void complete(String msg) {

    }

    @Override
    public void showError(String paramString, int status_code) {
        dismissDialog();
        ToastUtils.show("提交失败，请重试！");
    }

}
