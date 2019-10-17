package com.gofishfarm.htkj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.event.Event;
import com.gofishfarm.htkj.event.OrderEvent;
import com.gofishfarm.htkj.event.PlayMessageEvent;
import com.gofishfarm.htkj.service.PlayService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private Button btn;
    private TextView tv_msg;
    private Button btn1;
    private Button btn2;

    private GsonBuilder builder;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        EventBus.getDefault().register(this);
        initGson();
        initView();
    }

    private void initGson() {
        if (null == builder) {
            builder = new GsonBuilder();
        }
        if (null == gson) {
            gson = builder.create();
        }
    }

    @Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    public void onWSPlayMessageEvent(PlayMessageEvent event) {
        /* Do something */
        tv_msg.setText(gson.toJson(event));
    }


    private void initText(Event.showWsMessage showWsMessage) {
        tv_msg.setText(showWsMessage.toString());
    }

    private void initView() {
        btn = (Button) findViewById(R.id.btn);
        tv_msg = (TextView) findViewById(R.id.tv_msg);

        btn.setOnClickListener(this);
        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                doFever();
                break;
            case R.id.btn1:
                startServices();
                break;
            case R.id.btn2:
                stopServices();
                break;
        }
    }

    private void doFever() {
        OrderEvent event = new OrderEvent();
        event.setOp_type("like");
        EventBus.getDefault().post(event);
    }

    //切换流
    private void doSwitch() {
        OrderEvent event = new OrderEvent();
        event.setOp_type("cutover");
        event.setWs_fp_id("setWs_fp_id");
        EventBus.getDefault().post(event);
    }

    private void startServices() {
        Intent intent = new Intent(this, PlayService.class);
        String str = "ws://148.70.13.176:9501?fishing_id=123123&ws_fp_id=1";
        intent.putExtra(ConfigApi.SOCKETURL, str);
        startService(intent);
    }

    private void stopServices() {
        Intent stopIntent = new Intent(this, PlayService.class);
        stopService(stopIntent);//停止服务
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        stopServices();
        super.onDestroy();

    }
}
