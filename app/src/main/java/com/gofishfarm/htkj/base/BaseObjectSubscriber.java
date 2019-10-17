package com.gofishfarm.htkj.base;


import android.app.Activity;
import android.content.Intent;

import com.gofishfarm.htkj.bean.BaseBean;
import com.gofishfarm.htkj.event.Event;
import com.gofishfarm.htkj.event.RxBus;
import com.gofishfarm.htkj.ui.login.LoginActivity;
import com.gofishfarm.htkj.ui.main.MainActivity;
import com.gofishfarm.htkj.utils.ActivityUtils;
import com.gofishfarm.htkj.utils.AppUtils;
import com.gofishfarm.htkj.utils.NetworkUtils;

import java.util.List;

import io.reactivex.subscribers.ResourceSubscriber;


/**
 * MrLiu253@163.com
 *
 * @time 2018/7/30
 */
public abstract class BaseObjectSubscriber<T> extends ResourceSubscriber<BaseBean<T>> {
    private String mMsg;
    private BaseView mView;

    public BaseObjectSubscriber(BaseView paramBaseView) {
        this.mView = paramBaseView;
    }

    public void onFailure(int code, String message) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!NetworkUtils.isConnected(AppUtils.getAppContext())) {
            this.mMsg = "网络连接失败";
        }
    }

    @Override
    public void onNext(BaseBean<T> tBaseBean) {
        if (this.mView == null) {
            return;
        }
        if (tBaseBean.getCode() == 200) {
            this.mView.complete(tBaseBean.getMessage());
            onSuccess(tBaseBean.getData());
        } else if (tBaseBean.getCode() == 401) {
            this.mView.complete("");
            if (ActivityUtils.getTopActivity() != null)
                ActivityUtils.getTopActivity().startActivity(new Intent(ActivityUtils.getTopActivity(), LoginActivity.class));
            List<Activity> mList = ActivityUtils.getActivityList();
            for (Activity mActivity:mList){
                if (!(mActivity instanceof MainActivity)){
                    mActivity.finish();
                }
            }
            Event.ShowFragment showFragment = new Event.ShowFragment();
            showFragment.pos = 0;
            showFragment.posVisible = 401;
            RxBus.INSTANCE.post(showFragment);
        } else {
            this.mView.showError(tBaseBean.getMessage(), tBaseBean.getCode());
        }
    }

    public abstract void onSuccess(T paramT);

    @Override
    public void onError(Throwable paramThrowable) {
        if (this.mView == null) {
            return;
        }
        this.mView.showError("网络连接错误", 500);
//        if ((this.mMsg != null) && (!TextUtils.isEmpty(this.mMsg))) {
//            this.mView.showError(this.mMsg, 1001);
//        } else if ((paramThrowable instanceof ApiException)) {
//            this.mView.showError(paramThrowable.toString(), 1002);
//        } else if ((paramThrowable instanceof SocketTimeoutException)) {
//            this.mView.showError("服务器响应超时", 1003);
//        } else if ((paramThrowable instanceof HttpException)) {
//            HttpException httpException = (HttpException) paramThrowable;
////            Log.d("aaa", "状态码" + ((HttpException) paramThrowable).code());
//            this.mView.showError("数据加载失败", 1004);
//        } else {
//            this.mView.showError("未知错误", 1005);
//        }
    }

    public void onComplete() {
    }


}
