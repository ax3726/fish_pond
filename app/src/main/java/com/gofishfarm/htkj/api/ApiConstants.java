package com.gofishfarm.htkj.api;


import com.gofishfarm.htkj.bean.BaseBean;
import com.gofishfarm.htkj.bean.DoSignBean;
import com.gofishfarm.htkj.bean.ErrorListBean;
import com.gofishfarm.htkj.bean.ExchangeRecordBean;
import com.gofishfarm.htkj.bean.FansListBean;
import com.gofishfarm.htkj.bean.FishDevciceBean;
import com.gofishfarm.htkj.bean.FishGuideFinishBean;
import com.gofishfarm.htkj.bean.FishPondBean;
import com.gofishfarm.htkj.bean.FishingCoinBean;
import com.gofishfarm.htkj.bean.FishingShareBean;
import com.gofishfarm.htkj.bean.FocousOrNotBean;
import com.gofishfarm.htkj.bean.FollowListBean;
import com.gofishfarm.htkj.bean.HelpBean;
import com.gofishfarm.htkj.bean.HomepageBean;
import com.gofishfarm.htkj.bean.MallBean;
import com.gofishfarm.htkj.bean.MissionBean;
import com.gofishfarm.htkj.bean.MissionInfoBean;
import com.gofishfarm.htkj.bean.MyTimeBean;
import com.gofishfarm.htkj.bean.OnLookBean;
import com.gofishfarm.htkj.bean.OnLookFocousBean;
import com.gofishfarm.htkj.bean.OrderBean;
import com.gofishfarm.htkj.bean.OrderRecordBean;
import com.gofishfarm.htkj.bean.OrderRecordNewBean;
import com.gofishfarm.htkj.bean.RankLfetBean;
import com.gofishfarm.htkj.bean.RankRightBean;
import com.gofishfarm.htkj.bean.ShareBean;
import com.gofishfarm.htkj.bean.SingnInBean;
import com.gofishfarm.htkj.bean.SmsBean;
import com.gofishfarm.htkj.bean.TemporarykeyBean;
import com.gofishfarm.htkj.bean.UserDataBean;
import com.gofishfarm.htkj.bean.UserInfoBean;
import com.gofishfarm.htkj.bean.UserInformationBean;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiConstants {

    //发送短信
    @FormUrlEncoded
    @POST(ConfigApi.SEND_SMS_P)
    Flowable<BaseBean<SmsBean>> getSMS(@Field("phone") String phone);

    @FormUrlEncoded
    @POST(ConfigApi.LOGIN)
    Flowable<BaseBean<UserInfoBean>> getLogin(@Field("phone") String phone, @Field("code") String code, @Field("imei") String imei);

    @FormUrlEncoded
    @POST(ConfigApi.LOGIN)
    Flowable<BaseBean<UserInfoBean>> getWechatLogin(@Field("wechat_id") String wechat_id, @Field("nick_name") String nick_name, @Field("avatar") String avatar, @Field("gender") String gender, @Field("imei") String imei);

    @FormUrlEncoded
    @POST(ConfigApi.LOGIN)
    Flowable<BaseBean<UserInfoBean>> getWeiboLogin(@Field("weibo_id") String wechat_id, @Field("nick_name") String nick_name, @Field("avatar") String avatar, @Field("gender") String gender, @Field("imei") String imei);

    @FormUrlEncoded
    @POST(ConfigApi.BINDING)
    Flowable<BaseBean> getBinding(@Field("phone") String phone, @Field("code") String code, @Header("Authorization") String mAuthorization);

    //首页信息列表
    // @Headers("api-version:v1")
    @GET("index")
    Flowable<BaseBean<FishPondBean>> getFishPondData();

    //获取钓台数据(自由渔场)
    @FormUrlEncoded
    @POST(ConfigApi.FISHING_P)
    Flowable<BaseBean<FishDevciceBean>> getFishplatData(@Header("Authorization") String Authorization, @Field("status") int status, @Field("fishery_id") String fishery_id);

    //获取钓台数据(快速开钓)
    @FormUrlEncoded
    @POST(ConfigApi.FISHING_P)
    Flowable<BaseBean<FishDevciceBean>> getFastFishplatData(@Header("Authorization") String Authorization, @Field("status") int status);


    //个人中心
    @GET(ConfigApi.USERS_G)
    Flowable<BaseBean<UserDataBean>> getUser(@Header("Authorization") String authorization);

    //获取围观数据
    @FormUrlEncoded
    @POST(ConfigApi.ONLOOKS_P)
    Flowable<BaseBean<OnLookBean>> getOnLookBeanData(@Header("Authorization") String Authorization, @Field("fishery_id") String fishery_id);

    //获取围观数据-搜索
    @FormUrlEncoded
    @POST(ConfigApi.ONLOOKS_P)
    Flowable<BaseBean<OnLookBean>> getOnLookBeanSearchData(@Header("Authorization") String Authorization, @Field("field") String field);

    //获取我的时长数据
    @GET(ConfigApi.TIMES_G)
    Flowable<BaseBean<MyTimeBean>> getMyTimeBeanData(@Header("Authorization") String Authorization);

    //退出钓鱼
    @DELETE("fishing/{fp_id}")
    Flowable<BaseBean> deleteFinshingOut(@Header("Authorization") String Authorization, @Path("fp_id") int $fp_id);

    //个人信息
    @GET(ConfigApi.USERS_FISHER_ID_EDIT_G)
    Flowable<BaseBean<UserInformationBean>> getUserInformation(@Header("Authorization") String authorization, @Path("fisher_id") String fisher_id);

    //修改个人信息
    @FormUrlEncoded
    @PUT(ConfigApi.USERS_FISHER_ID_P)
    Flowable<BaseBean<UserInformationBean>> setUserInformation(@Header("Authorization") String authorization, @Path("fisher_id") String fisher_id, @FieldMap Map<String, String> map);

    //创建订单
    @FormUrlEncoded
    @POST(ConfigApi.ORDERS_P)
    Flowable<BaseBean<OrderBean>> getOrder(@Header("Authorization") String Authorization, @Field("package_id") String package_id, @Field("pay_way") int pay_way, @Field("number") int number);

    @FormUrlEncoded
    @POST(ConfigApi.BINFING_WECHAT)
    Flowable<BaseBean> getBindingSina(@Header("Authorization") String authorization, @Field("wechat_id") String id, @Field("nick_name") String nick_name, @Field("avatar") String avatar, @Field("type") int i);

    @FormUrlEncoded
    @POST(ConfigApi.BINFING_WECHAT)
    Flowable<BaseBean> getBindingWechat(@Header("Authorization") String authorization, @Field("wechat_id") String id, @Field("nick_name") String nick_name, @Field("avatar") String avatar, @Field("type") int i);

    @GET(ConfigApi.INVITATIONS_FISHERID)
    Flowable<BaseBean<ShareBean>> getShare(@Header("Authorization") String mAuthorization, @Path("fisher_id") String fisher_id);

    //获取累计钓鱼数榜单
    @GET(ConfigApi.RANKS)
    Flowable<BaseBean<RankLfetBean>> getRankLftBeanData(@Header("Authorization") String Authorization);

    //获取等时长钓鱼数榜单
    @GET(ConfigApi.RANK_AVE)
    Flowable<BaseBean<RankRightBean>> getRankRightBeanData(@Header("Authorization") String Authorization);

    //获取订单记录
    @GET(ConfigApi.ORDERS_G)
    Flowable<BaseBean<OrderRecordBean>> getOrderRecordData(@Header("Authorization") String Authorization);

    //围观-关注钓手数据
    @GET(ConfigApi.FOCUSS_ID_G)
    Flowable<BaseBean<OnLookFocousBean>> getOnLookFocousData(@Header("Authorization") String Authorization, @Path("id") String id);

    //关注钓手
    @FormUrlEncoded
    @POST(ConfigApi.FOCUSS_P)
    Flowable<BaseBean<FocousOrNotBean>> getOnFocousData(@Header("Authorization") String Authorization, @Field("focus_id") String focus_id);

    //取消关注钓手
    @DELETE(ConfigApi.FOCUSS_FOCUS_ID_D)
    Flowable<BaseBean<FocousOrNotBean>> getDeFocousData(@Header("Authorization") String Authorization, @Path("focus_id") String focus_id);

    //围观列表
    @GET(ConfigApi.FOCUSS_G)
    Flowable<BaseBean<FollowListBean>> getFollowListBeanData(@Header("Authorization") String Authorization, @Query("page") int page);

    //关注搜索
    @GET(ConfigApi.FOCUSS_G)
    Flowable<BaseBean<FollowListBean>> getSerchFollowListBeanData(@Header("Authorization") String Authorization, @Query("fields") String fields);

    //粉丝列表
    @GET(ConfigApi.FANS_G)
    Flowable<BaseBean<FansListBean>> getFansListBeanData(@Header("Authorization") String Authorization, @Query("page") int page);


    //我的渔币
    @GET(ConfigApi.INTEGRATIONS_FISHER_ID_G)
    Flowable<BaseBean<FishingCoinBean>> getFishingCoin(@Header("Authorization") String mAuthorization, @Path("fisher_id") String fisher_id);

    //个人主页
    @GET(ConfigApi.USERS_FISHER_ID_G)
    Flowable<BaseBean<HomepageBean>> getHomepage(@Header("Authorization") String mAuthorization, @Path("fisher_id") String fisher_id);

    //获取签到数据
    @GET(ConfigApi.CLOCKS)
    Flowable<BaseBean<SingnInBean>> getSignIn(@Header("Authorization") String authorization);

    //用户登出
    @POST(ConfigApi.USER_LOGOUT_P)
    Flowable<BaseBean> Logout(@Header("Authorization") String mAuthorization);

    //鱼币兑换
    @FormUrlEncoded
    @POST(ConfigApi.INTEGRATIONS_P)
    Flowable<BaseBean> getOnExchangeData(@Header("Authorization") String mAuthorization, @Field("is_id") String is_id, @Field("number") String number);

    //获取兑换记录
    @GET(ConfigApi.INTEGRATIONS_G)
    Flowable<BaseBean<ExchangeRecordBean>> getExchangeRecord(@Header("Authorization") String Authorization);

    //获取任务列表
    @GET(ConfigApi.MISSIONS_G)
    Flowable<BaseBean<MissionBean>> getMissionBeabData(@Header("Authorization") String Authorization);

    //执行任务
    @FormUrlEncoded
    @PUT(ConfigApi.MISSIONS_M_ID_P)
    Flowable<BaseBean<MissionInfoBean>> getOnMissionData(@Header("Authorization") String Authorization, @Path("m_id") String m_id, @Field("number") String node);

    //获取临时秘钥
    @GET(ConfigApi.COS)
    Flowable<BaseBean<TemporarykeyBean>> getTemporarykey(@Header("Authorization") String authorization);

    //获取临时秘钥
    @POST(ConfigApi.CLOCKS)
    Flowable<BaseBean<DoSignBean>> getDoSign(@Header("Authorization") String authorization);

    //围观搜索
    @FormUrlEncoded
    @POST(ConfigApi.ONLOOKS_P)
    Flowable<BaseBean<OnLookBean>> getOnLookSerchBeanData(@Header("Authorization") String Authorization, @Field("field") String field);

    //获取我的时长数据-新
    @GET(ConfigApi.TIMES_FISHER_ID_G)
    Flowable<BaseBean<OrderRecordNewBean>> getNewMyTimeBeanData(@Header("Authorization") String Authorization, @Path("fisher_id") String fisher_id);

    //获取我的时长数据-新
    @GET(ConfigApi.BIANXIANMAO_G)
    Flowable<BaseBean<MallBean>> goMall(@Header("Authorization") String Authorization);

    //钓鱼分享
    @GET(ConfigApi.SHARE_FISHING_G)
    Flowable<BaseBean<FishingShareBean>> getFishShareData(@Header("Authorization") String Authorization);

    //故障报错
    @FormUrlEncoded
    @POST(ConfigApi.NOTICES_P)
    Flowable<BaseBean> getEquipErrorData(@Header("Authorization") String Authorization, @Field("fp_id") String fp_id);

    //完成新手指南
    @POST(ConfigApi.NOVICES_P)
    Flowable<BaseBean<FishGuideFinishBean>> getFishGuideFinishData(@Header("Authorization") String Authorization);

    //反馈设备故障
    @FormUrlEncoded
    @POST(ConfigApi.NOTICES_P)
    Flowable<BaseBean> doFeedBack(@Header("Authorization") String Authorization, @Field("fp_id") int fp_id, @Field("type") int type, @Field("solution") String solution);

    //获取错误列表
    @GET(ConfigApi.NOTICES_G)
    Flowable<BaseBean<ErrorListBean>> getErrorList(@Header("Authorization") String Authorization);

    //钓鱼帮助
    @FormUrlEncoded
    @POST(ConfigApi.FISHING_HELP_P)
    Flowable<BaseBean<HelpBean>> getHelpBean(@Header("Authorization") String Authorization, @Field("type") int type, @Field("fp_id") int fp_id);
}
