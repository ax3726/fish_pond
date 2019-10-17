package com.gofishfarm.htkj.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.api.ConfigApi;
import com.hjq.toast.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
//		"wx147a98b0ec2caa53", "9027f5189e1f15d8ccc1746be05b96f0"
        api = WXAPIFactory.createWXAPI(this, ConfigApi.WX_APPID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp baseResp) {

//        Log.d(TAG, "onPayFinish, errCode = " + baseResp);
//		if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle(R.string.app_tip);
//			builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(baseResp.errCode)));
//			builder.show();
//		}
        if (baseResp.errCode == 0) {
            ToastUtils.show("支付成功!");
            //支付成功
//			EventBus.getDefault().post(new PaySuccessEvent());
//			//刷新新订单列表
//			EventBus.getDefault().post(new OrderRefreshEvent());
//			Intent localIntent = new Intent(this, CheckResultActivity1.class);
//			localIntent.putExtra("orderId", orderId);
//			startActivity(localIntent);
        } else if (baseResp.errCode == -1) {
            ToastUtils.show("支付失败!");
        } else if (baseResp.errCode == -2) {
            ToastUtils.show("取消支付!");
        }
        finish();
    }
}