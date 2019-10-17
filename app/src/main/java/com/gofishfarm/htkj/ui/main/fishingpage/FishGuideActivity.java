package com.gofishfarm.htkj.ui.main.fishingpage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.dhh.websocket.RxWebSocket;
import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.api.ConfigApi;
import com.gofishfarm.htkj.base.BaseActivity;
import com.gofishfarm.htkj.bean.FishGuideFinishBean;
import com.gofishfarm.htkj.presenter.main.fishingpage.FishGuideActivityPresenter;
import com.gofishfarm.htkj.utils.SharedUtils;
import com.gofishfarm.htkj.view.main.fishingpage.FishGuideActivityView;
import com.gyf.barlibrary.ImmersionBar;
import com.hjq.toast.ToastUtils;


import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FishGuideActivity extends BaseActivity<FishGuideActivityView, FishGuideActivityPresenter> implements FishGuideActivityView, View.OnTouchListener {

    private Toolbar toolbar;
    private Button btn_close;
    private ImageView mImage;
    private VideoView mVideo;
    private Button btn_one;
    private Button btn_two;
    private Button btn_thr;
    private Button btn_fou;
    private Button btn_fiv;
    private LinearLayout ll_bottom_menu;
    private ImageView iv_01;
    private ImageView iv_02;
    private ImageView iv_03;
    private ImageView iv_04;
    private ImageView iv_05;


    private Uri mPlayerPath;

    //是否第一次初始化video组件你

    private boolean isFirstIn;

    private volatile boolean mark_one = true;
    private volatile boolean mark_two;
    private volatile boolean mark_three;
    private volatile boolean mark_four;
    private volatile boolean mark_five;
    private volatile boolean mark_six;
    private volatile boolean mark_seven;
    private volatile boolean mark_eight;
    private volatile boolean mark_nine;
    private volatile boolean mark_ten;
    private volatile boolean mark_eleven;
    private volatile boolean mark_twelev;


    private volatile int duration;
    private volatile int percent;//0-100

    private int mCurrentPosition;//记录当前播放的位置，从后台进入时恢复

    private static Disposable mDisposable;

    @Inject
    FishGuideActivityPresenter mFishGuideActivityPresenter;


    @Override
    public FishGuideActivityPresenter createPresenter() {
        return this.mFishGuideActivityPresenter;
    }

    @Override
    public FishGuideActivity createView() {
        return this;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_fish_guide;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btn_close = (Button) findViewById(R.id.btn_close);
        ImmersionBar.setTitleBar(this, toolbar);
        mImage = (ImageView) findViewById(R.id.image);
        mVideo = (VideoView) findViewById(R.id.video);
        btn_one = (Button) findViewById(R.id.btn_one);
        btn_two = (Button) findViewById(R.id.btn_two);
        btn_thr = (Button) findViewById(R.id.btn_thr);
        btn_fou = (Button) findViewById(R.id.btn_fou);
        btn_fiv = (Button) findViewById(R.id.btn_fiv);
        ll_bottom_menu = (LinearLayout) findViewById(R.id.ll_bottom_menu);

        iv_01 = (ImageView) findViewById(R.id.iv_01);
        iv_02 = (ImageView) findViewById(R.id.iv_02);
        iv_03 = (ImageView) findViewById(R.id.iv_03);
        iv_04 = (ImageView) findViewById(R.id.iv_04);
        iv_05 = (ImageView) findViewById(R.id.iv_05);

        btn_close.setOnClickListener(this);

        btn_one.setOnTouchListener(this);
        btn_two.setOnTouchListener(this);
        btn_thr.setOnTouchListener(this);
        btn_fou.setOnTouchListener(this);
        btn_fiv.setOnTouchListener(this);

        iv_02.setVisibility(View.VISIBLE);

        initImage();
        initVideo();
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close:
                finish();
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

    }

    private void initImage() {
        mPlayerPath = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.fish_guide);
//        mVideo.setVisibility(View.GONE);
        mImage.setVisibility(View.VISIBLE);

        /**
         * MediaMetadataRetriever class provides a unified interface for retrieving
         * frame and meta data from an input media file.
         */
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(this, mPlayerPath);
        Bitmap bitmap = mmr.getFrameAtTime(0);//获取第一帧图片
        mImage.setImageBitmap(bitmap);
        mmr.release();//释放资源
    }

    private void initVideo() {

        mVideo.setVideoURI(mPlayerPath);
        mVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // 如果正在播放，没0.5.毫秒更新一次进度条
                duration = mVideo.getDuration();
//                Log.d("perc", duration + "duration");
                //解决初次播放不会停止的问题
//                play();
//                pause();
//                mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
//                    @Override
//                    public void onBufferingUpdate(MediaPlayer mp, int percent) {
//                        //更新SeekBar的进度
//                        Log.d("percent", percent + "");
//                    }
//                });


                Observable.interval(100, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        //使用observerOn()指定订阅者运行的线程
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Long>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable disposable) {
                                mDisposable = disposable;
                            }

                            @Override
                            public void onNext(@NonNull Long number) {
                                int current = mVideo.getCurrentPosition();
                                percent = (int) ((current * 1000) / duration);
                                Log.d("perc", percent + "percent");
                                setTouch(percent);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                //取消订阅//取消订阅
                                cancel();
                            }

                            @Override
                            public void onComplete() {
                                //取消订阅
                                cancel();

                            }
                        });
            }
        });

        mVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mark_eleven = false;
                mFishGuideActivityPresenter.getFishGuideFinishData(SharedUtils.getInstance().getString(ConfigApi.AUTHORIZATION));
            }
        });

    }

    /**
     * 取消订阅
     */
    public void cancel() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            // Log.d(TAG, "====定时器取消======");
        }
    }

    private int mvalue;

    //控制视频的点击
    private void setTouch(int value) {

        if (value >= 61 && value <= 64) {
            mvalue = 63;
            setwhich(mvalue);
            return;
        }
        if (value >= 124 && value <= 126) {
            mvalue = 125;
            setwhich(mvalue);
            return;
        }
        if (value >= 197 && value <= 199) {
            mvalue = 198;
            setwhich(mvalue);
            return;
        }
        if (value >= 256 && value <= 258) {
            mvalue = 257;
            setwhich(mvalue);
            return;
        }
        if (value >= 364 && value <= 366) {
            mvalue = 365;
            setwhich(mvalue);
            return;
        }
        if (value >= 542 && value <= 544) {
            mvalue = 543;
            setwhich(mvalue);
            return;
        }
        if (value >= 607 && value <= 609) {
            mvalue = 608;
            setwhich(mvalue);
            return;
        }
        if (value >= 671 && value <= 673) {
            mvalue = 672;
            setwhich(mvalue);
            return;
        }
        if (value >= 772 && value <= 774) {
            mvalue = 773;
            setwhich(mvalue);
            return;
        }
        if (value >= 837 && value <= 839) {
            mvalue = 838;
            setwhich(mvalue);
            return;
        }
    }

    private void setwhich(int value) {
        switch (value) {
            case 63:
                if (mark_one) {
                    iv_05.setVisibility(View.VISIBLE);
                    iv_02.setVisibility(View.INVISIBLE);
//                    mark_one = false;
                    initAll();
                    mark_two = true;
                    mVideo.pause();
                }
                break;
            case 125:
                if (mark_two) {
                    iv_04.setVisibility(View.VISIBLE);
                    iv_05.setVisibility(View.INVISIBLE);
//                    mark_two = false;
                    initAll();
                    mark_three = true;
                    mVideo.pause();
                }
                break;
            case 198:
                if (mark_three) {
                    iv_05.setVisibility(View.VISIBLE);
                    iv_04.setVisibility(View.INVISIBLE);
//                    mark_three = false;
                    initAll();
                    mark_four = true;
                    mVideo.pause();
                }
                break;
            case 257:
                if (mark_four) {
                    iv_01.setVisibility(View.VISIBLE);
                    iv_05.setVisibility(View.INVISIBLE);
//                    mark_four = false;
                    initAll();
                    mark_five = true;
                    mVideo.pause();
                }
                break;
            case 365:
                if (mark_five) {
                    mVideo.pause();
                    iv_03.setVisibility(View.VISIBLE);
                    iv_01.setVisibility(View.INVISIBLE);
//                    mark_five = false;
                    initAll();
                    mark_six = true;
                }
                break;
            case 543:
                if (mark_six) {
                    iv_04.setVisibility(View.VISIBLE);
                    iv_03.setVisibility(View.INVISIBLE);
//                    mark_six = false;
                    initAll();
                    mark_seven = true;
                    mVideo.pause();
                }
                break;
            case 608:
                if (mark_seven) {
                    iv_02.setVisibility(View.VISIBLE);
                    iv_04.setVisibility(View.INVISIBLE);
//                    mark_seven = false;
                    initAll();
                    mark_eight = true;
                    mVideo.pause();
                }
                break;
            case 672:
                if (mark_eight) {
                    iv_04.setVisibility(View.VISIBLE);
                    iv_02.setVisibility(View.INVISIBLE);
//                    mark_eight = false;
                    initAll();
                    mark_nine = true;
                    mVideo.pause();
                }
                break;
            case 773:
                if (mark_nine) {
                    iv_01.setVisibility(View.VISIBLE);
                    iv_04.setVisibility(View.INVISIBLE);
//                    mark_nine = false;
                    initAll();
                    mark_ten = true;
                    mVideo.pause();
                }
                break;
            case 838:
                if (mark_ten) {
                    iv_05.setVisibility(View.VISIBLE);
                    iv_01.setVisibility(View.INVISIBLE);
//                    mark_ten = false;
                    initAll();
                    mark_eleven = true;
                    mVideo.pause();
                }
                break;
            default:
                break;
        }
    }


    private void initAll() {
//        btn_one.setBackgroundResource(R.drawable.shape_circle);
//        btn_two.setBackgroundResource(R.drawable.shape_circle);
//        btn_thr.setBackgroundResource(R.drawable.shape_circle);
//        btn_fou.setBackgroundResource(R.drawable.shape_circle);
//        btn_fiv.setBackgroundResource(R.drawable.shape_circle);

        mark_one = false;
        mark_two = false;
        mark_three = false;
        mark_four = false;
        mark_five = false;
        mark_six = false;
        mark_seven = false;
        mark_eight = false;
        mark_nine = false;
        mark_ten = false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.btn_one:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mark_five || mark_ten) {
//                        if (mark_five) {
//                            mark_five = false;
//                        }
                        if (!mVideo.isPlaying()) {
                            play();
                        }
                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    //第一次抛竿，不让暂停
                    if (!mark_eleven) {
                        if (!mark_five) {
                            pause();
                        }
                    }
                }
                break;
            case R.id.btn_two:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mark_one || mark_eight) {
                        if (!isFirstIn) {
                            isFirstIn = true;
                            //隐藏掉第一帧的imageview
                            mImage.setVisibility(View.GONE);
                            play();
                        }
                        play();
                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    if (!mark_eleven) {
                        pause();
                    }
                }
                break;
            case R.id.btn_thr:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mark_six) {
                        play();
                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    if (!mark_eleven) {
                        pause();
                    }
                }
                break;
            case R.id.btn_fou:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mark_three || mark_seven || mark_nine) {
                        play();
                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    if (!mark_eleven) {
                        pause();
                    }
                }
                break;
            case R.id.btn_fiv:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mark_two || mark_four || mark_eleven) {
                        play();
                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                    if (!mark_eleven) {
                        pause();
                    }
                }
                break;
            default:
                break;
        }
        return false;
    }

    public void play() {
        if (null != mVideo) {
            mVideo.start();
        }
    }

    public void pause() {
        if (null != mVideo && mVideo.isPlaying()) {
            mVideo.pause();
        }
    }

    public void resume() {
        if (null != mVideo) {
            mVideo.resume();
        }
    }

    @Override
    protected void onPause() {
        pause();
        mCurrentPosition = mVideo.getCurrentPosition();
        super.onPause();
    }

    @Override
    protected void onResume() {
//        mVideo.setVisibility(View.VISIBLE);
        if (mVideo != null) {
            mVideo.seekTo(mCurrentPosition);
        }
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //取消订阅
        cancel();
    }

    @Override
    protected void onDestroy() {
        //取消订阅
        cancel();
        super.onDestroy();
    }

    @Override
    public void onFishGuideFinishData(FishGuideFinishBean fishGuideFinishBean) {
        if (null == fishGuideFinishBean) {
            ToastUtils.show("完成学习！");
            this.finish();
            return;
        }
        String fishGuideAward = fishGuideFinishBean.getReward();
        Intent intent = new Intent(this, FishGuideFinishActivity.class);
        intent.putExtra(ConfigApi.FISHGUIDE_REWARD, fishGuideAward);
        startActivity(intent);
        finish();

    }
}
