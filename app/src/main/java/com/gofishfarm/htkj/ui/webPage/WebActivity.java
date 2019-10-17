package com.gofishfarm.htkj.ui.webPage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.base.BaseActivity;
import com.gofishfarm.htkj.presenter.webPage.WebActivityPresenter;
import com.gofishfarm.htkj.view.webPage.WebActivityView;
import com.gyf.barlibrary.ImmersionBar;
import com.just.agentweb.AgentWeb;

import javax.inject.Inject;

public class WebActivity extends BaseActivity<WebActivityView, WebActivityPresenter> implements WebActivityView {

    private ImageButton toolbar_left_ib;
    private TextView toolbar_title;
    private ImageView toolbar_right_iv;
    private ImageView toolbar_right_my_iv;
    private TextView toolbar_right_bt;
    private View toolbar_v;
    private WebView webView;
    private AgentWeb mAgentWeb;
    private FrameLayout fl_web;
    private String webTitle = "";
    private String webUrl = "";

    @Inject
    WebActivityPresenter mWebActivityPresenter;

    @Override
    public WebActivityPresenter createPresenter() {
        return this.mWebActivityPresenter;
    }

    @Override
    public WebActivity createView() {
        return this;
    }


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_web;
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
        webView = (WebView) findViewById(R.id.webView);
        fl_web = (FrameLayout) findViewById(R.id.fl_web);
        toolbar_v.setVisibility(View.VISIBLE);
        toolbar_title.setVisibility(View.VISIBLE);

        toolbar_left_ib.setOnClickListener(this);
        Intent intent = getIntent();
        webTitle = intent.getStringExtra(ConfigApi.WEB_TITLE);
        webUrl = intent.getStringExtra(ConfigApi.WEB_URL);
        if (null == webUrl) {
            webUrl = "";
        }
//        try {
//            webUrl = URLEncoder.encode(webUrl, "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        if (null != webTitle && !TextUtils.isEmpty(webTitle)) {
            toolbar_title.setText(webTitle);
        }
        mAgentWeb = AgentWeb.with(this)//传入Activity
                .setAgentWebParent(fl_web, new FrameLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
                .useDefaultIndicator()// 使用默认进度条
//                .setReceivedTitleCallback(mCallback) //设置 Web 页面的 title 回调
                .createAgentWeb()//
                .ready()
                .go(webUrl);

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.toolbar_left_ib:
                if (!mAgentWeb.back()) {
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void complete(String msg) {

    }

    @Override
    public void showError(String paramString, int status_code) {

    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
