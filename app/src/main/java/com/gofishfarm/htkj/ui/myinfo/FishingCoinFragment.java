package com.gofishfarm.htkj.ui.myinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.base.BaseFragment;
import com.gofishfarm.htkj.bean.BaseBean;
import com.gofishfarm.htkj.bean.FishingCoinBean;
import com.gofishfarm.htkj.bean.MallBean;
import com.gofishfarm.htkj.bean.MissionBean;
import com.gofishfarm.htkj.bean.MissionInfoBean;
import com.gofishfarm.htkj.bean.UserInfoBean;
import com.gofishfarm.htkj.custom.popup.PopupExchangeView;
import com.gofishfarm.htkj.presenter.myinfo.FishingCoinPresenter;
import com.gofishfarm.htkj.ui.main.fishingpage.RechargeActivity;
import com.gofishfarm.htkj.ui.myinfo.adapter.ExchangeMissionAdapter;
import com.gofishfarm.htkj.ui.webPage.WebActivity;
import com.gofishfarm.htkj.utils.SharedUtils;
import com.gofishfarm.htkj.view.myinfo.FishingCoinView;
import com.hjq.toast.ToastUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/9
 */
public class FishingCoinFragment extends BaseFragment<FishingCoinView, FishingCoinPresenter> implements FishingCoinView {


    private TextView mTvFishers;
    private TextView mTvGetCoin;
    private TextView mMfcMoneyTv;
    private TextView mTvExchangeTypeOne;
    private TextView mTvExchangeOne;
    private TextView mTvExchangeTypeTwo;
    private TextView mTvExchangeTwo;
    private TextView mTvExchangeTypeThree;
    private TextView mTvExchangeThree;
    private RecyclerView mRcCoinTask;

    String zeroJudge = "";
    private String time = "";
    private String is_id_select = "";
    private String integration = "";
    private BigDecimal integrationSelect;

    BigDecimal coinNum1;
    BigDecimal temp;
    private String myCoins = "0";
    private int num = 1;
    private PopupExchangeView popupExchangeView;
    List<FishingCoinBean.SettingsBean> mSettingsBean = new ArrayList<>();
    private List<MissionBean.InfoBean> mInfoBean = new ArrayList<>();
    private ExchangeMissionAdapter adapter;

    @Inject
    FishingCoinPresenter mFishingCoinPresenter;

    public static FishingCoinFragment newInstance() {
        return new FishingCoinFragment();
    }

    @Override
    public FishingCoinPresenter createPresenter() {
        return this.mFishingCoinPresenter;
    }

    @Override
    public FishingCoinView createView() {
        return this;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_finsh_coin;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mTextView.setVisibility(View.VISIBLE);
        mTextView.setText(R.string.my_fishing_coin);
        if (getResources() != null) {
            setRightText(getString(R.string.fish_coin_record), getResources().getColor(R.color.color_fe));
        }

        mMfcMoneyTv = view.findViewById(R.id.mfc_money_tv);
        mTvFishers = (TextView) view.findViewById(R.id.tv_fishers);
        mTvGetCoin = (TextView) view.findViewById(R.id.tv_get_coin);
        mTvExchangeTypeOne = (TextView) view.findViewById(R.id.tv_exchange_type_one);
        mTvExchangeOne = (TextView) view.findViewById(R.id.tv_exchange_one);
        mTvExchangeTypeTwo = (TextView) view.findViewById(R.id.tv_exchange_type_two);
        mTvExchangeTwo = (TextView) view.findViewById(R.id.tv_exchange_two);
        mTvExchangeTypeThree = (TextView) view.findViewById(R.id.tv_exchange_type_three);
        mTvExchangeThree = (TextView) view.findViewById(R.id.tv_exchange_three);
        mRcCoinTask = (RecyclerView) view.findViewById(R.id.rc_coin_task);
        mTvExchangeOne.setOnClickListener(this);
        mTvExchangeTwo.setOnClickListener(this);
        mTvExchangeThree.setOnClickListener(this);
        initAdapter();
        initPopView();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        initData();
    }

    private void initPopView() {
        popupExchangeView = new PopupExchangeView(mActivity);
        popupExchangeView.setOnAddSubtractClickListener(new PopupExchangeView.OnAddSubtractClickListener() {
            @Override
            public void OnMinusClick(TextView conent) {
                if (num >= 2) {
                    num--;
                    ((TextView) popupExchangeView.findViewById(R.id.pe_content_tv)).setText("" + num);
                } else {
                    ToastUtils.show("数量不能小于0");
                    return;
                }
                coinNum1 = integrationSelect.multiply(BigDecimal.valueOf(num));
                if ((new BigDecimal(myCoins)).compareTo(coinNum1) >= 0) {
                    popupExchangeView.findViewById(R.id.pe_error_tv).setVisibility(View.INVISIBLE);
                } else {
                    popupExchangeView.findViewById(R.id.pe_error_tv).setVisibility(View.VISIBLE);
                }
                ((TextView) popupExchangeView.findViewById(R.id.pe_content_tv)).setText("" + num);
                ((TextView) popupExchangeView.findViewById(R.id.pe_money_tv)).setText(coinNum1.toString());
                //  精确一位小数 结果为 3.1
                zeroJudge = (((BigDecimal.valueOf(num)).multiply(new BigDecimal(time)).setScale(1, RoundingMode.FLOOR))).toString();
                ((TextView) popupExchangeView.findViewById(R.id.pe_hours_tv)).setText("时长" + parseData(zeroJudge) + "小时");

            }

            @Override
            public void OnAddClick(TextView conent) {
                int i = num + 1;
                temp = integrationSelect.multiply(BigDecimal.valueOf(i));

                if ((new BigDecimal(myCoins)).compareTo(temp) >= 0) {
                    popupExchangeView.findViewById(R.id.pe_error_tv).setVisibility(View.INVISIBLE);
                } else {
                    popupExchangeView.findViewById(R.id.pe_error_tv).setVisibility(View.VISIBLE);
                }
                if ((new BigDecimal(myCoins)).compareTo(temp) >= 0) {
                    ++num;
                    temp = integrationSelect.multiply(BigDecimal.valueOf(num));
//                    temp = new BigDecimal(num * coinNum);
                } else {
                    temp = integrationSelect.multiply(BigDecimal.valueOf(num));
                }
//                Log.d("ttt", "" + ((new BigDecimal(myCoins)).compareTo(temp) >= 0) + "=" + temp.toString() + "" + "---" + myCoins + "--" + i + "====" + num);

                ((TextView) popupExchangeView.findViewById(R.id.pe_content_tv)).setText("" + num);
                ((TextView) popupExchangeView.findViewById(R.id.pe_money_tv)).setText(temp.toString());
                zeroJudge = (((BigDecimal.valueOf(num)).multiply(new BigDecimal(time)).setScale(1, RoundingMode.FLOOR))).toString();
                ((TextView) popupExchangeView.findViewById(R.id.pe_hours_tv)).setText("时长" + parseData(zeroJudge) + "小时");
            }

            @Override
            public void OnConfirmClick(TextView conent) {
                popupExchangeView.dismiss();
                mFishingCoinPresenter.getOnExchangeData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, ""), is_id_select, num + "");
            }
        });
    }

    private void initAdapter() {
        mRcCoinTask.setNestedScrollingEnabled(false);
        adapter = new ExchangeMissionAdapter(R.layout.item_coin_task, mInfoBean);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRcCoinTask.setLayoutManager(manager);
        mRcCoinTask.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!mInfoBean.get(position).isAccomplished()) {
                    String m_id = mInfoBean.get(position).getM_id();
                    String node = mInfoBean.get(position).getNode();
                    mFishingCoinPresenter.getOnMissionData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION), m_id, node);
                }
            }
        });
    }

    private String parseData(String str) {
        if (str.endsWith(".0")) {
            str = str.substring(0, str.indexOf("."));
        }
        return str;
    }


    private void initData() {
        UserInfoBean mUserInfoBean = SharedUtils.getInstance().getObject(ConfigApi.USER_INFO, UserInfoBean.class);
        String mAuthorization = SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION);
        if (mUserInfoBean != null && mUserInfoBean.getUser() != null && !TextUtils.isEmpty(mAuthorization)) {
//            showDialog("正在加载");
            mFishingCoinPresenter.getFishingCoin(mAuthorization, mUserInfoBean.getUser().getFisher_id());
        } else {
            ToastUtils.show("获取信息失败");
        }
        mFishingCoinPresenter.getMissionBeabData(mAuthorization);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_left_ib:
                if (mActivity != null) {
                    mActivity.finish();
                }
                break;
            case R.id.toolbar_right_bt:
//                start(RedemptionRecordFragment.newInstance());
                start(FishCoinExchangeFragment.newInstance());
                break;
            case R.id.tv_exchange_one:
                if (mSettingsBean.size() <= 0)
                    return;
                if (null == mSettingsBean.get(0) || null == mSettingsBean.get(0).getIs_id() || TextUtils.isEmpty(mSettingsBean.get(0).getIs_id()))
                    return;
                if (null == mSettingsBean.get(0) || null == mSettingsBean.get(0).getTime() || TextUtils.isEmpty(mSettingsBean.get(0).getTime()))
                    return;
                if (null == mSettingsBean.get(0) || null == mSettingsBean.get(0).getIntegration() || TextUtils.isEmpty(mSettingsBean.get(0).getIntegration()))
                    return;
                is_id_select = mSettingsBean.get(0).getIs_id();
                integration = mSettingsBean.get(0).getIntegration();
                time = mSettingsBean.get(0).getTime();
                showPop(myCoins, time, integration);

                break;
            case R.id.tv_exchange_two:
                if (mSettingsBean.size() <= 1)
                    return;
                if (null == mSettingsBean.get(1) || null == mSettingsBean.get(1).getIs_id() || TextUtils.isEmpty(mSettingsBean.get(1).getIs_id()))
                    return;
                if (null == mSettingsBean.get(1) || null == mSettingsBean.get(1).getTime() || TextUtils.isEmpty(mSettingsBean.get(1).getTime()))
                    return;
                if (null == mSettingsBean.get(1) || null == mSettingsBean.get(1).getIntegration() || TextUtils.isEmpty(mSettingsBean.get(1).getIntegration()))
                    return;
                is_id_select = mSettingsBean.get(1).getIs_id();
                integration = mSettingsBean.get(1).getIntegration();
                time = mSettingsBean.get(1).getTime();
                showPop(myCoins, time, integration);
                break;
            case R.id.tv_exchange_three:
//                ToastUtils.show("更所任务");
                mFishingCoinPresenter.goMall(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, ""));
                break;
            default:
                break;
        }
    }

    private void showPop(String myCoins, String time, String integration) {
        if (null != popupExchangeView) {
            popupExchangeView.show();
        }
        integrationSelect = new BigDecimal(integration);
        num = 1;
        ((TextView) popupExchangeView.findViewById(R.id.pe_content_tv)).setText("" + num);
        ((TextView) popupExchangeView.findViewById(R.id.pe_hours_tv)).setText("时长" + time + "小时");
        ((TextView) popupExchangeView.findViewById(R.id.pe_money_tv)).setText(integration);
        ((TextView) popupExchangeView.findViewById(R.id.pe_description_tv)).setText(time + "h/" + integration);

        if ((new BigDecimal(myCoins)).compareTo(integrationSelect) >= 0) {
            popupExchangeView.findViewById(R.id.pe_error_tv).setVisibility(View.INVISIBLE);
        } else {
            popupExchangeView.findViewById(R.id.pe_error_tv).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void complete(String msg) {

    }

    @Override
    public void showError(String paramString, int status_code) {
        dismissDialog();
        ToastUtils.show(paramString);
    }

    @Override
    public void onCallbackResult(FishingCoinBean param) {
        dismissDialog();
        if (null == param) {
            return;
        }
        if (null != param.getSettings()) {
            if (null != mSettingsBean) {
                mSettingsBean.clear();
            }
            mSettingsBean.addAll(param.getSettings());
        }
        myCoins = param.getAvailable_integration();
        mMfcMoneyTv.setText(myCoins);
        mTvFishers.setText(param.getTotal_fishing());
        mTvGetCoin.setText(param.getTotal_integration());
        for (int i = 0; i < param.getSettings().size(); i++) {
            if (i == 0) {
                mTvExchangeTypeOne.setText(param.getSettings().get(0).getTime() + "h/" + param.getSettings().get(0).getIntegration());
            } else if (i == 1) {
                mTvExchangeTypeTwo.setText(param.getSettings().get(1).getTime() + "h/" + param.getSettings().get(1).getIntegration());
            }
        }
    }

    @Override
    public void onExchangeCallback(BaseBean param) {
        if (null == param) {
            return;
        }
        if (param.getCode() == 200) {
            ToastUtils.show("兑换成功");
            initData();
        } else {
            ToastUtils.show(param.getMessage());
        }
    }

    @Override
    public void onMissionCallback(MissionBean param) {
        if (null == param) {
            return;
        }
        if (null == param.getInfo() && param.getInfo().size() <= 0) {
            return;
        }
        if (null != mInfoBean) {
            mInfoBean.clear();
        }
        mInfoBean.addAll(param.getInfo());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDoMissionCallback(MissionInfoBean param) {

        if (null == param) {
            return;
        }
        initData();

        if (param.getInfo().equals("0")) {
            ToastUtils.show("领取成功");
        } else if (param.getInfo().equals("1")) {
            startActivity(new Intent(getContext(), RechargeActivity.class));
        } else if (param.getInfo().equals("2")) {
            startActivity(new Intent(getContext(), InviteFriendsActivity.class));
        }
    }

    @Override
    public void onMallBeanDataCallback(MallBean mallBean) {
        if (null == mallBean) {
            return;
        }
        String mallUrl = mallBean.getUrl();
        if (!TextUtils.isEmpty(mallUrl)) {
            Intent intent = new Intent(getContext(), WebActivity.class);
            intent.putExtra(ConfigApi.WEB_TITLE, getResources().getString(R.string.mall));
            intent.putExtra(ConfigApi.WEB_URL, mallUrl);
            startActivity(intent);
        }
    }
}
