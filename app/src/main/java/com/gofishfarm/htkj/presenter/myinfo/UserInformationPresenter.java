package com.gofishfarm.htkj.presenter.myinfo;

import com.gofishfarm.htkj.base.BaseObjectSubscriber;
import com.gofishfarm.htkj.base.BaseSubscriber;
import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.base.retrofit.RxHelper;
import com.gofishfarm.htkj.bean.BaseBean;
import com.gofishfarm.htkj.bean.TemporarykeyBean;
import com.gofishfarm.htkj.bean.UserDataBean;
import com.gofishfarm.htkj.bean.UserInformationBean;
import com.gofishfarm.htkj.custom.popup.PopupPictureView;
import com.gofishfarm.htkj.utils.GetJsonDataUtil;
import com.gofishfarm.htkj.view.myinfo.UserInformationView;
import com.google.gson.Gson;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/9
 */
public class UserInformationPresenter extends RxPresenter<UserInformationView> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public UserInformationPresenter(RetrofitHelper paramRetrofitHelper) {
        this.mRetrofitHelper = paramRetrofitHelper;
    }

    public void getUserInformation(String authorization,String fisher_id) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getUserInformation(authorization,fisher_id)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<UserInformationBean>(getView()) {
                            @Override
                            public void onSuccess(UserInformationBean param) {
                                if (getView() != null&&param!=null) {
                                    getView().onCallbackResult(param);
                                }
                            }
                        })
        );
    }

    public void setUserInformation(String authorization, String fisher_id,Map<String,String> map) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.setUserInformation(authorization,fisher_id,map)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<UserInformationBean>(getView()) {
                            @Override
                            public void onSuccess(UserInformationBean param) {
                                if (getView() != null&&param!=null) {
                                    getView().onSaveCallbackResult(param);
                                }
                            }
                        })
        );
    }
    public void getBindingWechat(String authorization, String id, String nick_name, String avatar, int i) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getBindingWechat(authorization,id,nick_name,avatar,i)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseSubscriber(getView()) {

                            @Override
                            public void onSuccess(BaseBean param) {
                                if (getView() != null&&param!=null) {
                                    getView().onBindingWechatCallbackResult(param);
                                }
                            }
                        })
        );
    }
    public void getBindingSina(String authorization, String id, String nick_name, String avatar, int i) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getBindingSina(authorization,id,nick_name,avatar,i)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseSubscriber(getView()) {

                            @Override
                            public void onSuccess(BaseBean param) {
                                if (getView() != null&&param!=null) {
                                    getView().onBindingSinaCallbackResult(param);
                                }
                            }
                        })
        );
    }
    public void getTemporarykey(String authorization) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.getTemporarykey(authorization)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseObjectSubscriber<TemporarykeyBean>(getView()) {
                            @Override
                            public void onSuccess(TemporarykeyBean paramT) {
                                if (getView() != null&&paramT!=null) {
                                    getView().onTemporarykeyCallbackResult(paramT);
                                }
                            }
                        })
        );
    }
    public PopupPictureView.OnPicturesClickListener mOnPicturesClickListener =new PopupPictureView.OnPicturesClickListener() {
        @Override
        public void OnPicturesClick() {
            if (getView() != null) {
                getView().OnPicturesClick();
            }
        }

        @Override
        public void OnPhotoClick() {
            if (getView() != null) {
                getView().OnPhotoClick();
            }
        }
    };

    public UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            if (getView() != null) {
                getView().onPlatformStart();
            }
        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            if (getView() != null) {
                getView().onPlatformComplete(platform, data);
            }
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            if (getView() != null) {
                getView().onPlatformError();
            }
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            if (getView() != null) {
                getView().onPlatformCancel(platform);
            }
        }
    };



}
