package com.gofishfarm.htkj.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.base.BaseFragment;
import com.gofishfarm.htkj.bean.BannerBean;
import com.gofishfarm.htkj.bean.FishDevciceBean;
import com.gofishfarm.htkj.bean.FishPondBean;
import com.gofishfarm.htkj.bean.MyTimeBean;
import com.gofishfarm.htkj.bean.OnLookBean;
import com.gofishfarm.htkj.common.OnRecyclerViewItemClickListener;
import com.gofishfarm.htkj.image.GlideApp;
import com.gofishfarm.htkj.image.transformation.GlideRoundImageTransform;
import com.gofishfarm.htkj.presenter.main.FisherPondPresenter;
import com.gofishfarm.htkj.ui.main.adapter.FishPondAdapter;
import com.gofishfarm.htkj.ui.main.fishingpage.FishGuideActivity;
import com.gofishfarm.htkj.ui.main.fishingpage.OnLookActivity;
import com.gofishfarm.htkj.ui.main.fishingpage.RechargeActivity;
import com.gofishfarm.htkj.ui.main.fishingpage.UserFishNewCommerActivity;
import com.gofishfarm.htkj.ui.main.fishingpage.UserFishingActivity;
import com.gofishfarm.htkj.ui.webPage.WebActivity;
import com.gofishfarm.htkj.utils.SharedUtils;
import com.gofishfarm.htkj.view.main.FisherPondView;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/5
 */
public class FisherPondFragment extends BaseFragment<FisherPondView, FisherPondPresenter> implements FisherPondView {

    private SmartRefreshLayout srl;
    private BGABanner mBanner;
    private LinearLayout mLlFastFishing;
    private LinearLayout mLlGoFollow;
    private RecyclerView mRvFishPond;
    private ArrayList<String> bannerImg = new ArrayList<>();
    private ArrayList<BannerBean> bannerBeans = new ArrayList<>();
    private ArrayList<FishPondBean.FisheriesBean> mFisheriesBean = new ArrayList<>();
    private FishPondAdapter adapter;

    String fishery_id = "";
    private boolean mOne = false;//判断是否第一次进入页面

    private com.gofishfarm.htkj.widget.iosalert.AlertDialog myDialog;//新手引导弹窗

    @Inject
    FisherPondPresenter mFisherPondPresenter;

    public static FisherPondFragment newInstance() {
        return new FisherPondFragment();
    }

    @Override
    public FisherPondPresenter createPresenter() {
        return this.mFisherPondPresenter;
    }

    @Override
    public FisherPondView createView() {
        return this;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fish_pond;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        Log.d("token", SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, ""));
        mImageButton.setVisibility(View.INVISIBLE);
        mTextView.setVisibility(View.VISIBLE);
        mTextView.setText(R.string.app_name);
        mImageView_marg.setVisibility(View.VISIBLE);
        mImageView_marg.setImageResource(R.drawable.sy_shichang_icon);
        mImageView_marg.setOnClickListener(this);
        srl = (SmartRefreshLayout) view.findViewById(R.id.srl);
        mBanner = (BGABanner) view.findViewById(R.id.banner);
        mLlFastFishing = (LinearLayout) view.findViewById(R.id.ll_fast_fishing);
        mLlGoFollow = (LinearLayout) view.findViewById(R.id.ll_go_follow);
        mRvFishPond = (RecyclerView) view.findViewById(R.id.rv_fishPond);
        mLlFastFishing.setOnClickListener(this);
        mLlGoFollow.setOnClickListener(this);
        mOne = true;
        iniListener();
        initAdapter();
        myDialog = new com.gofishfarm.htkj.widget.iosalert.AlertDialog(getActivity()).builder();
    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (mOne) {
//            showDialog("正在加载");
        }
        this.mFisherPondPresenter.getFishPondData();
    }

    private void initAdapter() {
        adapter = new FishPondAdapter(getContext(), mFisheriesBean);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        mRvFishPond.setLayoutManager(manager);
        mRvFishPond.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                fishery_id = mFisheriesBean.get(position).getFishery_id();
                if (!TextUtils.isEmpty(fishery_id)) {
                    goPondFishing(fishery_id);
                } else {
//                    goOnLook(fishery_id);
                }
            }
        });

    }


    private void iniListener() {
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mFisherPondPresenter.getFishPondData();
                refreshLayout.finishRefresh(1000);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_fast_fishing:
                fastFishing();
                break;
            case R.id.ll_go_follow:
                if (SharedUtils.getInstance().getBoolean(ConfigApi.ISFISHING, true) == true) {
                    ToastUtils.show("您正在钓鱼中，不能围观别人哦！");
                } else {
                    goOnLook("");
                }
                break;
            case R.id.toolbar_right_my_iv:
                goCharge();
//                goGuide();
                break;
            default:
                break;
        }
    }


    private void goCharge() {
        mFisherPondPresenter.getMyTimeBeanData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, ""));
    }

    private void goOnLook(String fishery_id) {
        String Authorization = SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, "");
        this.mFisherPondPresenter.getOnLookBeanData(Authorization, fishery_id, "");
    }

    private void fastFishing() {
        String Authorization = SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, "");
        int status = 1;
        this.mFisherPondPresenter.getFastFishplatData(Authorization, status);
    }

    /**
     * 自由钓场
     */
    private void goPondFishing(String fishery_id) {
        String Authorization = SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION, "");
        int status = 1;
        this.mFisherPondPresenter.getFishplatData(Authorization, status, fishery_id);
    }

    @Override
    public void complete(String msg) {

    }

    @Override
    public void showError(String paramString, int status_code) {
        dismissDialog();
//        if (status_code != 401 && status_code != 302) {
        if (status_code != 401) {
            ToastUtils.show(paramString);
        }
//        if (status_code == 401) {
//            startActivity(new Intent(getContext(), LoginActivity.class));
//        }
//        if (status_code == 302) {
//            //提醒钓鱼提已满，可以去围观
//            if (!SharedUtils.getInstance().getBoolean(ConfigApi.ISFISHING, true) == true) {
//                showDialog("提示", "钓台已满！你可以去围观");
//            }
//        }
    }

    @Override
    public void onFastFishing() {

    }

    @Override
    public void onLookOn() {

    }

    @Override
    public void onFishPondDataCallback(FishPondBean fishPondBean) {
        dismissDialog();
        mOne = false;
        setPageData(fishPondBean);
    }

    @Override
    public void onFishDeviceDataCallback(FishDevciceBean fishDevciceBean, int type) {
        if (null == fishDevciceBean) {
            return;
        }
        int fisherType = fishDevciceBean.getType();
        //保存命令信息，重新新打开时取出
        SharedUtils.getInstance().putObject(ConfigApi.FISHDEVCICEBEAN, fishDevciceBean);
        if (fishDevciceBean.getInfo().equals("0")) {
//            Intent intent = new Intent(getContext(), UserFishingActivity.class);
//            intent.putExtra(ConfigApi.FISHDEVCICEBEAN, fishDevciceBean);
//            startActivity(intent);
            if (type == 1) {
                if (fisherType == 1) {
                    Intent intent = new Intent(getContext(), UserFishingActivity.class);
                    intent.putExtra(ConfigApi.FISHDEVCICEBEAN, fishDevciceBean);
                    startActivity(intent);
                } else if (fisherType == 2) {
                    Intent intent = new Intent(mActivity, UserFishNewCommerActivity.class);
                    intent.putExtra(ConfigApi.FISHDEVCICEBEAN, fishDevciceBean);
                    startActivity(intent);
                }
            } else if (type == 2) {
                if (fisherType == 1) {
                    Intent intent = new Intent(getContext(), UserFishingActivity.class);
                    intent.putExtra(ConfigApi.FISHDEVCICEBEAN, fishDevciceBean);
                    startActivity(intent);
                } else if (fisherType == 2) {
                    Intent intent = new Intent(mActivity, UserFishNewCommerActivity.class);
                    intent.putExtra(ConfigApi.FISHDEVCICEBEAN, fishDevciceBean);
                    startActivity(intent);
                }
            }
        } else if (fishDevciceBean.getInfo().equals("1")) {
            //充值
            ToastUtils.show("没有可用时长，请先购买！");
            startActivity(new Intent(getContext(), RechargeActivity.class));

        } else if (fishDevciceBean.getInfo().equals("2")) {
            //新手引导
            //如果是新手训练场直接进入
            if (null == fishDevciceBean.getCommands()) {
                startActivity(new Intent(getActivity(), FishGuideActivity.class));
            } else {
                Intent intent = new Intent(getContext(), UserFishingActivity.class);
                intent.putExtra(ConfigApi.FISHDEVCICEBEAN, fishDevciceBean);
                intent.putExtra("shou_guide", true);
                startActivity(intent);
                //goGuide();
            }
        } else if (fishDevciceBean.getInfo().equals("3")) {
            //跳转围观
            goOnLook(fishery_id);
        }
    }

    /**
     * 新手引导
     */
    private void goGuide() {
//        myDialog.setGone().setTitle("提示").setMsg("请先完成手动模式的教学").setNegativeButton("稍后再去", null).setPositiveButton("立即进入", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), FishGuideActivity.class));
//            }
//        }).show();
        myDialog.setGone().setMsg("首次进入该模式，请先完成教学！").setNegativeButton("稍后再去", null).setPositiveButton("立即进入", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FishGuideActivity.class));
            }
        }).show();
    }


    @Override
    public void onOnLookDataCallback(OnLookBean onLookBean) {
        if (null == onLookBean) {
            return;
        }
        Intent intent = new Intent(getContext(), OnLookActivity.class);
        intent.putExtra("ONLOOKBEAN", onLookBean);
        startActivity(intent);
    }

    @Override
    public void onMyTimeBeanDataCallback(MyTimeBean myTimeBean) {
        if (null == myTimeBean) {
            return;
        }
        Intent intent = new Intent(getContext(), RechargeActivity.class);
        intent.putExtra("MYTIMEBEAN", myTimeBean);
        startActivity(intent);
    }

    private void setPageData(FishPondBean fishPondBean) {
        if (null != bannerBeans) {
            bannerBeans.clear();
            for (int i = 0; i < fishPondBean.getBanners().size(); i++) {
                BannerBean bannerBean = new BannerBean();
                bannerBean.setName(fishPondBean.getBanners().get(i).getName());
                bannerBean.setLink(fishPondBean.getBanners().get(i).getLink());
                bannerBean.setThumbnail(fishPondBean.getBanners().get(i).getThumbnail());
                bannerBeans.add(bannerBean);
            }
            if (null != bannerImg) {
                bannerImg.clear();
                for (int i = 0; i < bannerBeans.size(); i++) {
                    bannerImg.add(bannerBeans.get(i).getThumbnail());
                }
//                bannerImg.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546812592356&di=e872722ae61118f136824cb0c8081daf&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20180822%2F5d7b51cabc9f4f8f8f812e4f83974b6f.gif");
//                bannerImg.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546812592356&di=e872722ae61118f136824cb0c8081daf&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20180822%2F5d7b51cabc9f4f8f8f812e4f83974b6f.gif");
            }
            setBanner();
        }
        if (null != mFisheriesBean) {
            mFisheriesBean.clear();
            for (int i = 0; i < fishPondBean.getFisheries().size(); i++) {
                FishPondBean.FisheriesBean FisheriesBean = new FishPondBean.FisheriesBean();
                FisheriesBean.setFishery_id(fishPondBean.getFisheries().get(i).getFishery_id());
                FisheriesBean.setName(fishPondBean.getFisheries().get(i).getName());
                FisheriesBean.setThumbnail(fishPondBean.getFisheries().get(i).getThumbnail());
                FisheriesBean.setCurrent_capacity(fishPondBean.getFisheries().get(i).getCurrent_capacity());
                FisheriesBean.setCapacity(fishPondBean.getFisheries().get(i).getCapacity());
                FisheriesBean.setSurplus_capacity(fishPondBean.getFisheries().get(i).getSurplus_capacity());
                FisheriesBean.setDetails(fishPondBean.getFisheries().get(i).getDetails());
                mFisheriesBean.add(FisheriesBean);
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void setBanner() {
        mBanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable String model, int position) {
                GlideApp.with(getActivity())
                        .load(model)
                        .transform(new GlideRoundImageTransform(getActivity(), 10))
                        .centerCrop()
                        .placeholder(R.drawable.two)
                        .error(R.drawable.two)
                        .into(itemView);

            }
        });
        mBanner.setData(bannerImg, Arrays.asList("", "", ""));
        mBanner.setDelegate(new BGABanner.Delegate<ImageView, String>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
                String link = bannerBeans.get(position).getLink();
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra(ConfigApi.WEB_TITLE, "");
                intent.putExtra(ConfigApi.WEB_URL, link);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onDestroy() {
        closeClearDialog();
        super.onDestroy();
    }

    //    ---------------------显示对话框------------------------------------
    private AlertDialog tipDialog;

    public void showDialog(String title, String content) {
        if (null == tipDialog) {
            tipDialog = new AlertDialog.Builder(getContext())
                    .setTitle(title)
                    .setMessage(content)
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            goOnLook(fishery_id);
                        }
                    })
                    .create();
        }
        tipDialog.show();
    }

    public void closeClearDialog() {
        if (null != tipDialog && tipDialog.isShowing()) {
            tipDialog.dismiss();
        }
        if (null != tipDialog) {
            tipDialog = null;
        }
    }

    //    ---------------------显示页面的加载进度条------------------------------------
}
