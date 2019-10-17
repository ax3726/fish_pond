package com.gofishfarm.htkj.base.retrofit;


import com.gofishfarm.htkj.api.ApiConstants;
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


/**
 * 描述:RetrofitHelper 帮助类
 */
public class RetrofitHelper {
    private final ApiConstants mApiConstants;

    public RetrofitHelper(ApiConstants paramApiConstants) {
        this.mApiConstants = paramApiConstants;
    }

    public Flowable<BaseBean<SmsBean>> getSMS(String phone) {
        return this.mApiConstants.getSMS(phone);
    }

    public Flowable<BaseBean<UserInfoBean>> getLogin(String phone, String code, String imei) {
        return this.mApiConstants.getLogin(phone, code, imei);
    }

    public Flowable<BaseBean<UserInfoBean>> getLogin(String wechat_id, String nick_name, String avatar, String gender, String imei, int type) {
        if (type == 1) {
            return this.mApiConstants.getWechatLogin(wechat_id, nick_name, avatar, gender, imei);
        } else {
            return this.mApiConstants.getWeiboLogin(wechat_id, nick_name, avatar, gender, imei);
        }

    }

    public Flowable<BaseBean> getBinding(String phone, String code, String mAuthorization) {
        return this.mApiConstants.getBinding(phone, code, mAuthorization);
    }

    public Flowable<BaseBean<FishPondBean>> getFishPondData() {
        return this.mApiConstants.getFishPondData();
    }

    public Flowable<BaseBean<FishDevciceBean>> getFishplatData(String Authorization, int status, String fishery_id) {
        return this.mApiConstants.getFishplatData(Authorization, status, fishery_id);
    }

    public Flowable<BaseBean<FishDevciceBean>> getFastFishplatData(String Authorization, int status) {
        return this.mApiConstants.getFastFishplatData(Authorization, status);
    }

    public Flowable<BaseBean<OnLookBean>> getOnLookBeanData(String Authorization, String fishery_id) {
        return this.mApiConstants.getOnLookBeanData(Authorization, fishery_id);
    }

    //围观搜索
    public Flowable<BaseBean<OnLookBean>> getOnLookBeanSearchData(String Authorization, String field) {
        return this.mApiConstants.getOnLookBeanSearchData(Authorization, field);
    }

    public Flowable<BaseBean<UserDataBean>> getUser(String authorization) {
        return this.mApiConstants.getUser(authorization);
    }

    public Flowable<BaseBean> deleteFinshingOut(String Authorization, int fp_id) {
        return this.mApiConstants.deleteFinshingOut(Authorization, fp_id);
    }

    public Flowable<BaseBean<UserInformationBean>> getUserInformation(String authorization, String fisher_id) {
        return this.mApiConstants.getUserInformation(authorization, fisher_id);
    }

    public Flowable<BaseBean<UserInformationBean>> setUserInformation(String authorization, String fisher_id, Map<String, String> map) {
        return this.mApiConstants.setUserInformation(authorization, fisher_id, map);
    }

    public Flowable<BaseBean<MyTimeBean>> getMyTimeBeanData(String Authorization) {
        return this.mApiConstants.getMyTimeBeanData(Authorization);
    }

    public Flowable<BaseBean<OrderBean>> getOrder(String Authorization, String package_id, int pay_way, int number) {
        return this.mApiConstants.getOrder(Authorization, package_id, pay_way, number);
    }

    public Flowable<BaseBean> getBindingSina(String authorization, String id, String nick_name, String avatar, int i) {
        return this.mApiConstants.getBindingSina(authorization, id, nick_name, avatar, i);
    }

    public Flowable<BaseBean> getBindingWechat(String authorization, String id, String nick_name, String avatar, int i) {
        return this.mApiConstants.getBindingWechat(authorization, id, nick_name, avatar, i);
    }

    public Flowable<BaseBean<ShareBean>> getShare(String mAuthorization, String fisher_id) {
        return this.mApiConstants.getShare(mAuthorization, fisher_id);
    }

    public Flowable<BaseBean<RankLfetBean>> getRankLftBeanData(String Authorization) {
        return this.mApiConstants.getRankLftBeanData(Authorization);
    }

    public Flowable<BaseBean<RankRightBean>> getRankRightBeanData(String Authorization) {
        return this.mApiConstants.getRankRightBeanData(Authorization);
    }

    public Flowable<BaseBean<OrderRecordBean>> getOrderRecordData(String Authorization) {
        return this.mApiConstants.getOrderRecordData(Authorization);
    }

    public Flowable<BaseBean<OnLookFocousBean>> getOnLookFocousData(String Authorization, String id) {
        return this.mApiConstants.getOnLookFocousData(Authorization, id);
    }

    public Flowable<BaseBean<FocousOrNotBean>> getOnFocousData(String Authorization, String focus_id) {
        return this.mApiConstants.getOnFocousData(Authorization, focus_id);
    }

    public Flowable<BaseBean<FocousOrNotBean>> getDeFocousData(String Authorization, String focus_id) {
        return this.mApiConstants.getDeFocousData(Authorization, focus_id);
    }

    public Flowable<BaseBean<FishingCoinBean>> getFishingCoin(String mAuthorization, String fisher_id) {
        return this.mApiConstants.getFishingCoin(mAuthorization, fisher_id);
    }

    public Flowable<BaseBean<HomepageBean>> getHomepage(String mAuthorization, String fisher_id) {
        return this.mApiConstants.getHomepage(mAuthorization, fisher_id);
    }

    public Flowable<BaseBean<FollowListBean>> getFollowListBeanData(String Authorization, int page) {
        return this.mApiConstants.getFollowListBeanData(Authorization, page);
    }

    public Flowable<BaseBean<FollowListBean>> getSerchFollowListBeanData(String Authorization, String fields) {
        return this.mApiConstants.getSerchFollowListBeanData(Authorization, fields);
    }

    public Flowable<BaseBean<FansListBean>> getFansListBeanData(String Authorization, int page) {
        return this.mApiConstants.getFansListBeanData(Authorization, page);
    }


    public Flowable<BaseBean<SingnInBean>> getSignIn(String authorization) {
        return this.mApiConstants.getSignIn(authorization);
    }

    public Flowable<BaseBean<FocousOrNotBean>> getCancelAttention(String authorization, String focusId) {
        return this.mApiConstants.getDeFocousData(authorization, focusId);
    }

    public Flowable<BaseBean> Logout(String authorization) {
        return this.mApiConstants.Logout(authorization);
    }

    public Flowable<BaseBean> getOnExchangeData(String authorization, String is_id, String number) {
        return this.mApiConstants.getOnExchangeData(authorization, is_id, number);
    }

    public Flowable<BaseBean<ExchangeRecordBean>> getExchangeRecord(String authorization) {
        return this.mApiConstants.getExchangeRecord(authorization);
    }

    public Flowable<BaseBean<MissionBean>> getMissionBeabData(String authorization) {
        return this.mApiConstants.getMissionBeabData(authorization);
    }

    public Flowable<BaseBean<MissionInfoBean>> getOnMissionData(String authorization, String m_id, String node) {
        return this.mApiConstants.getOnMissionData(authorization, m_id, node);
    }

    public Flowable<BaseBean<TemporarykeyBean>> getTemporarykey(String authorization) {
        return this.mApiConstants.getTemporarykey(authorization);
    }

    public Flowable<BaseBean<DoSignBean>> getDoSign(String authorization) {
        return this.mApiConstants.getDoSign(authorization);
    }

    public Flowable<BaseBean<OnLookBean>> getOnLookSerchBeanData(String authorization, String field) {
        return this.mApiConstants.getOnLookSerchBeanData(authorization, field);
    }


    public Flowable<BaseBean<OrderRecordNewBean>> getNewMyTimeBeanData(String Authorization, String fisher_id) {
        return this.mApiConstants.getNewMyTimeBeanData(Authorization, fisher_id);
    }

    public Flowable<BaseBean<MallBean>> goMall(String Authorization) {
        return this.mApiConstants.goMall(Authorization);
    }

    public Flowable<BaseBean<FishingShareBean>> getFishShareData(String Authorization) {
        return this.mApiConstants.getFishShareData(Authorization);
    }

    public Flowable<BaseBean> getEquipErrorData(String Authorization, String fp_id) {
        return this.mApiConstants.getEquipErrorData(Authorization, fp_id);
    }

    public Flowable<BaseBean<FishGuideFinishBean>> getFishGuideFinishData(String Authorization) {
        return this.mApiConstants.getFishGuideFinishData(Authorization);
    }

    public Flowable<BaseBean<ErrorListBean>> getErrorList(String Authorization) {
        return this.mApiConstants.getErrorList(Authorization);
    }

    public Flowable<BaseBean> doFeedBack(String Authorization, int fp_id, int type, String solution) {
        return this.mApiConstants.doFeedBack(Authorization, fp_id, type, solution);
    }

    public Flowable<BaseBean<HelpBean>> getHelpBean(String Authorization, int type, int fp_id) {
        return this.mApiConstants.getHelpBean(Authorization, type, fp_id);
    }
}
