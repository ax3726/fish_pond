package com.gofishfarm.htkj.ui.myinfo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.base.BaseActivity;
import com.gofishfarm.htkj.bean.BaseBean;
import com.gofishfarm.htkj.bean.CityBean;
import com.gofishfarm.htkj.bean.TemporarykeyBean;
import com.gofishfarm.htkj.bean.UserInfoBean;
import com.gofishfarm.htkj.bean.UserInformationBean;
import com.gofishfarm.htkj.custom.popup.PopupPictureView;
import com.gofishfarm.htkj.helper.MyCredentialProvider;
import com.gofishfarm.htkj.image.GlideApp;
import com.gofishfarm.htkj.presenter.myinfo.UserInformationPresenter;
import com.gofishfarm.htkj.utils.GetJsonDataUtil;
import com.gofishfarm.htkj.utils.SharedUtils;
import com.gofishfarm.htkj.utils.TimeUtils;
import com.gofishfarm.htkj.utils.editutils.charLimite;
import com.gofishfarm.htkj.view.myinfo.UserInformationView;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.hjq.toast.ToastUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.CosXmlSimpleService;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlProgressListener;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.transfer.COSXMLUploadTask;
import com.tencent.cos.xml.transfer.TransferConfig;
import com.tencent.cos.xml.transfer.TransferManager;
import com.tencent.qcloud.core.auth.QCloudCredentialProvider;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/9
 */
public class UserInformationActivity extends BaseActivity<UserInformationView, UserInformationPresenter> implements UserInformationView {

    private UMShareAPI mUMShareAPI;
    private UserInfoBean mUserInfoBean;
    private String mAuthorization;
    private ImageButton mImageButton;
    private TextView mTitleTv;
    private CircleImageView mCircleImageView;
    private RelativeLayout mHeadImgRl;
    private EditText mNickNameEt;
    private TextView mGenderTv;
    private EditText mPhoneEt;
    private TextView mRightTv;
    private TextView mBirthdayTv;
    private TextView mCityTv;
    private TextView mFishAgeTv;
    private RelativeLayout mGenderRl;
    private RelativeLayout mWxBindRl;
    private RelativeLayout mWbBindRl;
    private RelativeLayout mBirthdayRl;
    private RelativeLayout mCityRl;
    private RelativeLayout mFishAgeRl;
    private TimePickerView pvTime;
    private OptionsPickerView pvNoLinkOptions, pvAgeOptions;
    private String mImage = "";//头像
    private String uri = "";
    private String mName = "";//姓名
    private String mGender = "";//性别
    private String mPhone = "";//手机号
    private boolean mWeiXin;
    private boolean mWeiBo;
    private String mTime = "";//年月
    private String mAge = "";//钓龄
    private String mCityID = "";//城市id
    private String mCity = "";//城市
    private ArrayList<CityBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<CityBean.SubCityBean>> options2Items = new ArrayList<>();
    private List<LocalMedia> mLocalMedia = new ArrayList<>();
    @Inject
    UserInformationPresenter mUserInformationPresenter;

    @Override
    public UserInformationPresenter createPresenter() {
        return this.mUserInformationPresenter;
    }

    @Override
    public UserInformationView createView() {
        return this;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_user_info;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {

        ImmersionBar.setTitleBar(this, findViewById(R.id.ui_toolbar));
        mRightTv = findViewById(R.id.toolbar_right_bt);
        mRightTv.setOnClickListener(this);
        mRightTv.setVisibility(View.VISIBLE);
        if (getResources() != null)
            mRightTv.setTextColor(getResources().getColor(R.color.font_red));
        mRightTv.setText("保存");
        mImageButton = findViewById(R.id.toolbar_left_ib);
        mTitleTv = findViewById(R.id.toolbar_title);
        mTitleTv.setVisibility(View.VISIBLE);
        mTitleTv.setText(R.string.personal_information);
        mCircleImageView = findViewById(R.id.civ_user_imag);
        mHeadImgRl = findViewById(R.id.rl_head_img);
        mNickNameEt = findViewById(R.id.et_nick_name);
        mGenderTv = findViewById(R.id.tv_gender);
        mPhoneEt = findViewById(R.id.et_phone);
        mBirthdayTv = findViewById(R.id.tv_birthday);
        mCityTv = findViewById(R.id.tv_city);
        mFishAgeTv = findViewById(R.id.tv_fish_age);
        mGenderRl = findViewById(R.id.rl_gender);
        mGenderRl.setOnClickListener(this);
        mWxBindRl = findViewById(R.id.rl_wx_bind);
        mWxBindRl.setOnClickListener(this);
        mWbBindRl = findViewById(R.id.rl_wb_bind);
        mWbBindRl.setOnClickListener(this);
        mBirthdayRl = findViewById(R.id.rl_birthday);
        mBirthdayRl.setOnClickListener(this);
        mCityRl = findViewById(R.id.rl_city);
        mCityRl.setOnClickListener(this);
        mFishAgeRl = findViewById(R.id.rl_fish_age);
        mFishAgeRl.setOnClickListener(this);


        mUserInfoBean = SharedUtils.getInstance().getObject(ConfigApi.USER_INFO, UserInfoBean.class);
        mAuthorization = SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION);
        if (mUserInfoBean != null && mUserInfoBean.getUser() != null && !TextUtils.isEmpty(mAuthorization)) {
            showDialog("正在加载");
            mUserInformationPresenter.getUserInformation(mAuthorization, mUserInfoBean.getUser().getFisher_id());
        } else {
            ToastUtils.show("获取信息失败");
        }
        initTimePicker();
        initNoLinkOptionsPicker();
        initAgePicker();


    }

    @Override
    protected void initListener() {
        mImageButton.setOnClickListener(this);
        mHeadImgRl.setOnClickListener(this);
        //字符串输入监听
        charLimite.setEditTextInputFilter(mNickNameEt);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_right_bt:
//                if (getJudge()) {
                showDialog("正在保存");
                Map<String, String> map = new LinkedHashMap();
                map.put("avatar", mImage);
                map.put("phone", mPhoneEt.getText().toString().trim());
                map.put("fishing_age", mAge);
                map.put("birthday", mTime);
                map.put("sex", mGender);
                map.put("nick_name", mNickNameEt.getText().toString().trim());
                map.put("city", mCityID);

                Log.d("nick_name", mNickNameEt.getText().toString().trim());
                mUserInformationPresenter.setUserInformation(mAuthorization, mUserInfoBean.getUser().getFisher_id(), map);
//                }
                break;
            case R.id.toolbar_left_ib:
                finish();
                break;
            case R.id.rl_head_img:
                PopupPictureView popupPictureView = new PopupPictureView(this);
                popupPictureView.setOnPicturesClickListener(mUserInformationPresenter.mOnPicturesClickListener);
                popupPictureView.show();
                break;
            case R.id.rl_gender://性别
                if (pvNoLinkOptions != null)
                    pvNoLinkOptions.show();
                break;
            case R.id.rl_wx_bind://绑定微信
                setAuthorization();
                break;
            case R.id.rl_wb_bind://绑定微博
                platformInfo(SHARE_MEDIA.SINA);
                break;
            case R.id.rl_birthday://出生年份
                if (pvTime != null)
                    pvTime.show(v);
                break;
            case R.id.rl_city://选择城市
                showDialog("正在解析数据");
                initJsonData();
                break;
            case R.id.rl_fish_age://选择钓龄
                if (pvAgeOptions != null) {
                    pvAgeOptions.show();
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
        dismissDialog();
        ToastUtils.show(paramString);
    }

    @Override
    public void onSaveCallbackResult(UserInformationBean param) {
        dismissDialog();
        ToastUtils.show("保存成功");
//        onCallbackResult(param);
        finish();
    }

    @Override
    public void onCallbackResult(UserInformationBean param) {
        dismissDialog();
        mImage = param.getAvatar();
        mName = param.getNick_name();
        mPhone = param.getPhone();
        mWeiXin = param.isWechat_on();
        mWeiBo = param.isWeibo_on();
        mGender = String.valueOf(param.getSex());
        mTime = param.getBirthday_ymd();
        mCityID = param.getCity();
        mCity = param.getCity_zh();
        mAge = param.getFishing_age();

        if (!TextUtils.isEmpty(mImage)) {

            GlideApp
                    .with(this)
                    .load(mImage)
                    .placeholder(R.drawable.sy_zw)
                    .error(R.drawable.sy_zw)
                    .into(mCircleImageView);
        }

        if (!TextUtils.isEmpty(mName)) {
            mNickNameEt.setText(mName);
//            mNickNameEt.setSelection(mName.length());
            mNickNameEt.setSelection(mNickNameEt.getText().length());
        }
        if (!TextUtils.isEmpty(mPhone)) {
            mPhoneEt.setText(mPhone);
//            mPhoneEt.setSelection(mPhone.length());
            mPhoneEt.setSelection(mPhoneEt.getText().length());
        }
        if (mWeiXin) {
            mWxBindRl.setVisibility(View.GONE);
        }
        if (mWeiBo) {
            mWbBindRl.setVisibility(View.GONE);
        }
        if (TextUtils.equals(mGender, "1")) {
            mGenderTv.setText("男");
        } else {
            mGenderTv.setText("女");
        }
        if (!TextUtils.isEmpty(mTime)) {
            mBirthdayTv.setText(mTime);
        }
        if (!TextUtils.isEmpty(mCityID) && !TextUtils.isEmpty(mCity)) {
            mCityTv.setText(mCity);
        }
        if (!TextUtils.isEmpty(mAge)) {
            mFishAgeTv.setText(mAge);
        }
    }

    @Override
    public void onBindingWechatCallbackResult(BaseBean param) {
        dismissDialog();
        mWeiXin = true;
        mWxBindRl.setVisibility(View.GONE);
        ToastUtils.show(param.getMessage());
    }

    @Override
    public void onBindingSinaCallbackResult(BaseBean param) {
        dismissDialog();
        mWeiBo = true;
        mWbBindRl.setVisibility(View.GONE);
        ToastUtils.show(param.getMessage());
    }

    @Override
    public void onTemporarykeyCallbackResult(TemporarykeyBean paramT) {


        String appid = "1258095529";
        String region = "ap-chengdu";

        //创建 CosXmlServiceConfig 对象，根据需要修改默认的配置参数
        CosXmlServiceConfig serviceConfig = new CosXmlServiceConfig.Builder()
                .setAppidAndRegion(appid, region)
                .setDebuggable(true)
                .builder();

        QCloudCredentialProvider credentialProvider = new MyCredentialProvider(paramT.getCredentials().getTmpSecretId(), paramT.getCredentials().getTmpSecretKey(), paramT.getCredentials().getSessionToken(), Long.parseLong(paramT.getStartTime()), Long.parseLong(paramT.getExpiredTime()));

        CosXmlSimpleService cosXmlService = new CosXmlSimpleService(this, serviceConfig, credentialProvider);
        TransferConfig transferConfig = new TransferConfig.Builder().build();
        TransferManager transferManager = new TransferManager(cosXmlService, transferConfig);
        generateRandomStr();

        String head_path = "avatar/" + generateRandomStr() + "head.png";
        COSXMLUploadTask cosxmlUploadTask = transferManager.upload("sfyb-1258095529", head_path, uri, null);
        showDialog("上传中。。。");
        //设置上传进度回调
        cosxmlUploadTask.setCosXmlProgressListener(new CosXmlProgressListener() {
            @Override
            public void onProgress(long complete, long target) {
//                float progress = 1.0f * complete / target * 100;
//                Log.d("aaa", String.format("progress = %d%%", (int) progress));
            }
        });
        //设置返回结果回调
        cosxmlUploadTask.setCosXmlResultListener(new CosXmlResultListener() {
            @Override
            public void onSuccess(CosXmlRequest request, CosXmlResult result) {
//                Log.d("TEST", "Success: " + result.printResult());
//                Log.d("TEST", "Success: " + result.accessUrl);
                mImage = result.accessUrl;
                dismissDialog();
            }

            @Override
            public void onFail(CosXmlRequest request, CosXmlClientException exception, CosXmlServiceException serviceException) {
                dismissDialog();
//                Log.d("TEST", "Failed: " + (exception == null ? serviceException.getMessage() : exception.toString()));
            }
        });

    }

    private String generateRandomStr() {
        long time = System.currentTimeMillis();
        Random rd = new Random();
        String str = "";
        for (int i = 0; i < 9; i++) {
            // 你想生成几个字符的，就把9改成几，如果改成１,那就生成一个随机字母．
            str = str + (char) (Math.random() * 26 + 'a');
        }
        return str + time;
    }

    @Override
    public void OnPicturesClick() {
        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())
                .minSelectNum(1)
                .selectionMode(PictureConfig.MULTIPLE)
                .previewImage(true)
                .previewVideo(false)
                .enablePreviewAudio(false) // 是否可播放音频
                .isCamera(false)
                .enableCrop(true)
                .compress(true)
                .glideOverride(160, 160)
                .hideBottomControls(true)
                .isGif(false)
                .freeStyleCropEnabled(true)
                .circleDimmedLayer(true)
                .openClickSound(true)
                .rotateEnabled(true)
                .scaleEnabled(true)
                .showCropFrame(false)
                .showCropGrid(false)
                .selectionMedia(mLocalMedia)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    public void OnPhotoClick() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(1)
                .minSelectNum(1)
                .selectionMode(PictureConfig.MULTIPLE)
                .previewImage(true)
                .previewVideo(false)
                .enablePreviewAudio(false) // 是否可播放音频
                .isCamera(false)
                .enableCrop(true)
                .compress(true)
                .glideOverride(160, 160)
                .previewEggs(true)
                .hideBottomControls(true)
                .isGif(false)
                .freeStyleCropEnabled(true)
                .circleDimmedLayer(true)
                .openClickSound(true)
                .rotateEnabled(true)
                .scaleEnabled(true)
                .showCropFrame(false)
                .showCropGrid(false)
                .isDragFrame(true)
                .selectionMedia(mLocalMedia)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    mLocalMedia.clear();
                    mLocalMedia.addAll(PictureSelector.obtainMultipleResult(data));

                    for (LocalMedia mData : mLocalMedia) {
                        if (mData.isCompressed()) {
                            uri = mData.getCompressPath();
                        } else {
                            uri = mData.getPath();
                        }
                    }

                    if (!TextUtils.isEmpty(uri)) {

                        mUserInformationPresenter.getTemporarykey(mAuthorization);
                        GlideApp
                                .with(this)
                                .load(uri)
                                .placeholder(R.drawable.sy_zw)
                                .error(R.drawable.sy_zw)
                                .into(mCircleImageView);

                    }


                    break;
            }
        }
    }

    private void setAuthorization() {
        //每次登录拉取确认界面
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        this.mUMShareAPI = UMShareAPI.get(this);
        mUMShareAPI.setShareConfig(config);
        if (!mUMShareAPI.isInstall(this, SHARE_MEDIA.WEIXIN)) {
            ToastUtils.show(R.string.not_find_wechat_should);
        } else {
            platformInfo(SHARE_MEDIA.WEIXIN);
        }
    }

    private void platformInfo(SHARE_MEDIA media) {
        if (TextUtils.isEmpty(mAuthorization)) {
            ToastUtils.show(R.string.failed_to_get_verification_token);
        } else {
            showDialog(getString(R.string.genuine_binding));
            mUMShareAPI.getPlatformInfo(this, media, mUserInformationPresenter.authListener);
        }
    }

    @Override
    public void onPlatformStart() {

    }

    @Override
    public void onPlatformComplete(SHARE_MEDIA platform, Map<String, String> data) {
        String id = data.get("uid");
        String nick_name = data.get("name");
        String avatar = data.get("iconurl");
        if (platform == SHARE_MEDIA.WEIXIN) {
            //用户唯一标识
            showDialog(getString(R.string.LOADING));

            mUserInformationPresenter.getBindingWechat(mAuthorization, id, nick_name, avatar, 2);
        } else {
            mUserInformationPresenter.getBindingSina(mAuthorization, id, nick_name, avatar, 2);
        }
    }

    @Override
    public void onPlatformError() {
        dismissDialog();
        ToastUtils.show(R.string.login_failed);
    }

    @Override
    public void onPlatformCancel(SHARE_MEDIA platform) {
        dismissDialog();

    }

    public ArrayList<CityBean> parseData(String result) {//Gson 解析
        ArrayList<CityBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                CityBean entity = gson.fromJson(data.optJSONObject(i).toString(), CityBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    private boolean getJudge() {
        if (TextUtils.isEmpty(mImage)) {
            ToastUtils.show("请选择头像");
            return false;
        } else if (TextUtils.isEmpty(mNickNameEt.getText().toString().trim())) {
            ToastUtils.show("请输入昵称");
            return false;
        } else if (mNickNameEt.getText().toString().trim().length() <= 0) {
            ToastUtils.show("请输入昵称");
            return false;
        } else if (TextUtils.isEmpty(mPhoneEt.getText().toString().trim())) {
            ToastUtils.show("请输入手机号");
            return false;
        } else if (TextUtils.isEmpty(mGender)) {
            ToastUtils.show("请选择性别");
            return false;
        } else if (TextUtils.isEmpty(mTime)) {
            ToastUtils.show("请选择出生年份");
            return false;
        } else if (TextUtils.isEmpty(mCityID) || TextUtils.equals(mCityID, "0")) {
            ToastUtils.show("请选择所在城市");
            return false;
        } else if (TextUtils.isEmpty(mAge) || TextUtils.equals(mAge, "0")) {
            ToastUtils.show(R.string.please_choose_fishing_age);
            return false;
        } else if (mUserInfoBean == null || TextUtils.isEmpty(mAuthorization)) {
            ToastUtils.show(R.string.failed_to_get_verification_token);
            return false;
        }
        return true;
    }

    private void initTimePicker() {

        Calendar endDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(1947, 10, 1);
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(1980, 0, 1);
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mTime = TimeUtils.dateString(date);
                mBirthdayTv.setText(mTime);

            }
        }).setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setType(new boolean[]{true, true, true, false, false, false})
                .build();
    }

    private void initNoLinkOptionsPicker() {
        final List<String> mStringList = new ArrayList<>();
        mStringList.add("男");
        mStringList.add("女");
        pvNoLinkOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                mGenderTv.setText(mStringList.get(options1));
                if (TextUtils.equals(mStringList.get(options1), "男")) {
                    mGender = "1";
                } else {
                    mGender = "2";
                }
            }
        }).build();
        pvNoLinkOptions.setPicker(mStringList);


    }

    private void initAgePicker() {
        final List<String> mStringList = new ArrayList<>();
        for (int i = 1; i < 51; i++) {
            mStringList.add(String.format("%d", i));
        }
        pvAgeOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                mAge = mStringList.get(options1);
                mFishAgeTv.setText(mAge);

            }
        }).build();
        pvAgeOptions.setPicker(mStringList);


    }

    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                mCityID = options2Items.get(options1).get(options2).getCity_id();
                mCity = options2Items.get(options1).get(options2).getName();
                mCityTv.setText(mCity);
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(options1Items, options2Items);
        pvOptions.show();
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "city.json");//获取assets目录下的json文件数据

        ArrayList<CityBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<CityBean.SubCityBean> CityList = new ArrayList<>();//该省的城市列表（第二级）

            for (int c = 0; c < jsonBean.get(i).getSub_city().size(); c++) {//遍历该省份的所有城市
                CityBean.SubCityBean CityName = jsonBean.get(i).getSub_city().get(c);
                CityList.add(CityName);//添加城市
            }
            /**
             * 添加城市数据
             */
            options2Items.add(CityList);
        }
        dismissDialog();
        showPickerView();
    }

}
