package com.gofishfarm.htkj.ui.main.fishingpage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.base.BaseActivity;
import com.gofishfarm.htkj.bean.MyTimeBean;
import com.gofishfarm.htkj.bean.OrderBean;
import com.gofishfarm.htkj.common.OnRecyclerViewItemClickListener;
import com.gofishfarm.htkj.presenter.main.fishingpage.RechargePresenter;
import com.gofishfarm.htkj.ui.login.LoginActivity;
import com.gofishfarm.htkj.ui.webPage.WebActivity;
import com.gofishfarm.htkj.utils.PayResult;
import com.gofishfarm.htkj.utils.SharedUtils;
import com.gofishfarm.htkj.view.main.fishingpage.RechargeView;
import com.gyf.barlibrary.ImmersionBar;
import com.hjq.toast.ToastUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;

public class RechargeActivity extends BaseActivity<RechargeView, RechargePresenter> implements RechargeView {
    private ImageButton toolbar_left_ib;
    private TextView toolbar_title;
    private TextView toolbar_right_bt;
    private TextView tv_rest_time;
    private TextView tv_rest_value;
    private ConstraintLayout cl_top;
    private TextView tv_first_charge;
    private TextView tv_first_charge_disacount;
    private RecyclerView rv_pay_set;
    private ImageView iv_tb;
    private ImageView iv_wx_check;
    private RelativeLayout rl_wx;
    private ImageView iv_wx;
    private ImageView iv_tb_check;
    private RelativeLayout rl_tb;
    private TextView tv_real_pay;
    private TextView tv_real_pay_money;
    private TextView tv_go_pay;
    private ImageView login_agree;
    private TextView tv;
    private TextView tv_protcol;
    private ArrayList<MyTimeBean.PackagesBean> mPackagesBean = new ArrayList<>();
    private ArrayList<Boolean> status = new ArrayList<>();
    private RechargeAdapter adapter;
    private String package_id;//套餐编号
    private Boolean isWXorTB = true;
    private Boolean isProtocol = true;
    private int payWay = 2;
    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    Log.d("okhttp", "resultStatus==" + resultStatus);
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        showAlert(PayDemoActivity.this, getString(R.string.pay_success) + payResult);
                        ToastUtils.show("支付成功");
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        showAlert(PayDemoActivity.this, getString(R.string.pay_failed) + payResult);
                        ToastUtils.show("支付失败");
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    @Inject
    RechargePresenter mRechargePresenter;

    @Override
    public RechargePresenter createPresenter() {
        return this.mRechargePresenter;
    }

    @Override
    public RechargeActivity createView() {
        return this;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initlayout();
        iniAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void iniAdapter() {
        adapter = new RechargeAdapter(this, mPackagesBean, status);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        rv_pay_set.setLayoutManager(manager);
        rv_pay_set.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (null != status) {
                    status.clear();
                }
                for (int i = 0; i < mPackagesBean.size(); i++) {
                    if (i == position) {
                        status.add(true);
                    } else {
                        status.add(false);
                    }
                }
                tv_real_pay_money.setText("¥" + mPackagesBean.get(position).getPrice());
                package_id = mPackagesBean.get(position).getPackage_id();
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        MyTimeBean myTimeBean = (MyTimeBean) intent.getSerializableExtra("MYTIMEBEAN");
        if (null != myTimeBean) {
            setData(myTimeBean);
        } else {
            mRechargePresenter.getMyTimeBeanData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION));
        }
    }

    private void initlayout() {
        ImmersionBar.setTitleBar(this, findViewById(R.id.ui_toolbar));
        toolbar_left_ib = findViewById(R.id.toolbar_left_ib);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_right_bt = findViewById(R.id.toolbar_right_bt);
        toolbar_right_bt.setTextColor(getResources().getColor(R.color.theme_yellow));
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_right_bt.setVisibility(View.VISIBLE);
        toolbar_title.setText(getString(R.string.my_charge));
        toolbar_right_bt.setText(getString(R.string.time_record));
        tv_rest_time = (TextView) findViewById(R.id.tv_rest_time);
        tv_rest_value = (TextView) findViewById(R.id.tv_rest_value);
        cl_top = (ConstraintLayout) findViewById(R.id.cl_top);
        tv_first_charge = (TextView) findViewById(R.id.tv_first_charge);
        tv_first_charge_disacount = (TextView) findViewById(R.id.tv_first_charge_disacount);
        rv_pay_set = (RecyclerView) findViewById(R.id.rv_pay_set);
        iv_tb = (ImageView) findViewById(R.id.iv_tb);
        iv_wx_check = (ImageView) findViewById(R.id.iv_wx_check);
        rl_wx = (RelativeLayout) findViewById(R.id.rl_wx);
        iv_wx = (ImageView) findViewById(R.id.iv_wx);
        iv_tb_check = (ImageView) findViewById(R.id.iv_tb_check);
        rl_tb = (RelativeLayout) findViewById(R.id.rl_tb);
        tv_real_pay = (TextView) findViewById(R.id.tv_real_pay);
        tv_real_pay_money = (TextView) findViewById(R.id.tv_real_pay_money);
        tv_go_pay = (TextView) findViewById(R.id.tv_go_pay);
        login_agree = (ImageView) findViewById(R.id.login_agree);
        tv = (TextView) findViewById(R.id.tv);
        tv_protcol = (TextView) findViewById(R.id.tv_protcol);
        setPayWay();
        setProtocol();
    }

    private void setProtocol() {
        if (isProtocol) {
            login_agree.setImageResource(R.drawable.cz_a_icon);
        } else {
            login_agree.setImageResource(R.drawable.cz_against_icon);
        }
    }

    private void setPayWay() {
        if (isWXorTB) {
            payWay = 2;
            iv_tb_check.setImageResource(R.drawable.cz_uncheck_icon);
            iv_wx_check.setImageResource(R.drawable.cz_selected_icon);
        } else {
            payWay = 1;
            iv_tb_check.setImageResource(R.drawable.cz_selected_icon);
            iv_wx_check.setImageResource(R.drawable.cz_uncheck_icon);
        }
    }

    @Override
    protected void initListener() {
        rl_wx.setOnClickListener(this);
        rl_tb.setOnClickListener(this);
        iv_tb_check.setOnClickListener(this);
        tv_go_pay.setOnClickListener(this);
        login_agree.setOnClickListener(this);
        tv.setOnClickListener(this);
        tv_protcol.setOnClickListener(this);
        toolbar_left_ib.setOnClickListener(this);
        toolbar_right_bt.setOnClickListener(this);
    }

    @Override
    public void complete(String msg) {

    }

    @Override
    public void showError(String paramString, int status_code) {
        if (status_code != 401) {
            ToastUtils.show(paramString);
        }
        if (status_code == 401) {
            startActivity(new Intent(this, LoginActivity.class));
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_wx:
                isWXorTB = true;
                setPayWay();
                break;
            case R.id.rl_tb:
                isWXorTB = false;
                setPayWay();
                break;
            case R.id.tv_go_pay:
                goBuy();
                break;
            case R.id.login_agree:
                isProtocol = !isProtocol;
                setProtocol();
                break;
            case R.id.tv:

                break;
            case R.id.tv_protcol:
                Intent intent = new Intent(this, WebActivity.class);
                intent.putExtra(ConfigApi.WEB_TITLE, getResources().getString(R.string.service_vip));
                intent.putExtra(ConfigApi.WEB_URL, ConfigApi.PAY_URL);
//                intent.putExtra(ConfigApi.WEB_URL, "https://www.baidu.com/");
                startActivity(intent);
                break;
            case R.id.toolbar_left_ib:
                finish();
                break;
            case R.id.toolbar_right_bt:
                goToRecord();
                break;
            default:
                break;
        }
    }

    private void goToRecord() {
        startActivity(new Intent(this, RechargeRecordActivity.class));
    }

    private void goBuy() {
        if (!isProtocol) {
            ToastUtils.show("请确认会员服务协议");
            return;
        }
        if (TextUtils.isEmpty(package_id)) {
            ToastUtils.show("请选择套餐");
            return;
        }
//        Log.d("okhttp", "package_id===" + package_id + "payWay===" + payWay);
        if (isWXorTB) {
            WXPay();
        } else {
            TBPay();
        }
    }

    private void TBPay() {
        mRechargePresenter.getOrder(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, ""), package_id, payWay, 1);
    }

    private void WXPay() {
        mRechargePresenter.getOrder(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, ""), package_id, payWay, 1);
    }

    @Override
    public void onMyTimeBeanDataCallback(MyTimeBean myTimeBean) {
        if (null != myTimeBean) {
            setData(myTimeBean);
        }
    }

    @Override
    public void onOrderBeanDataCallback(OrderBean mOrderBean) {
        if (null == mOrderBean) {
            return;
        }
        if (payWay == 1) {
            //支付宝
            pullTB(mOrderBean);
        } else if (payWay == 2) {
            //微信
            pullWX(mOrderBean);
        }

    }

    private void pullTB(OrderBean mOrderBean) {
        final String orderInfo = mOrderBean.getOrderInfo();
        if (null == orderInfo || TextUtils.isEmpty(orderInfo)) {
            return;
        }
        final Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(RechargeActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private void pullWX(OrderBean mOrderBean) {
        // 将该app注册到微信
        IWXAPI api;
//        api = WXAPIFactory.createWXAPI(RechargeActivity.this, ConfigApi.WX_APPID, false);
        api = WXAPIFactory.createWXAPI(RechargeActivity.this, ConfigApi.WX_APPID);
//        api = WXAPIFactory.createWXAPI(RechargeActivity.this, null);
        api.registerApp(ConfigApi.WX_APPID);
        PayReq request = new PayReq();
        request.appId = ConfigApi.WX_APPID;
        request.partnerId = ConfigApi.WX_PARTNERID;
        request.prepayId = mOrderBean.getPrepayid();
        request.packageValue = mOrderBean.getPackage_str();
        request.nonceStr = mOrderBean.getNoncestr();
        request.timeStamp = mOrderBean.getTimestamp();
        request.sign = mOrderBean.getSign();
        api.sendReq(request);
    }


    private void setData(MyTimeBean myTimeBean) {

        if (null != myTimeBean.getDiscount() && !TextUtils.isEmpty(myTimeBean.getDiscount())) {
            tv_first_charge_disacount.setText(myTimeBean.getDiscount());
            tv_first_charge_disacount.setVisibility(View.VISIBLE);
        } else {
            tv_first_charge_disacount.setVisibility(View.INVISIBLE);
        }
        package_id = myTimeBean.getPackages().get(0).getPackage_id();
        tv_rest_value.setText(myTimeBean.getTime() + "h");
        tv_real_pay_money.setText("¥" + myTimeBean.getPackages().get(0).getPrice());
        setAdapterData(myTimeBean);
    }

    private void setAdapterData(MyTimeBean myTimeBean) {
        if (null != mPackagesBean) {
            mPackagesBean.clear();
        }
        if (null != status) {
            status.clear();
        }
        mPackagesBean.addAll(myTimeBean.getPackages());
        for (int i = 0; i < mPackagesBean.size(); i++) {
            if (i == 0) {
                status.add(true);
            } else {
                status.add(false);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
