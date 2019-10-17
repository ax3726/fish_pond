package com.gofishfarm.htkj.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gofishfarm.htkj.App;
import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.base.support.ActivityMvpDelegate;
import com.gofishfarm.htkj.base.support.ActivityMvpDelegateImpl;
import com.gofishfarm.htkj.base.support.MvpCallback;
import com.gofishfarm.htkj.module.DaggerFragmentComponent;
import com.gofishfarm.htkj.module.FragmentComponent;
import com.gofishfarm.htkj.module.FragmentModule;
import com.gofishfarm.htkj.sweetAlert.SweetAlertDialog;
import com.gyf.barlibrary.ImmersionBar;
import com.trello.rxlifecycle2.components.support.RxFragment;

import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragmentDelegate;
import me.yokeyword.fragmentation.SupportHelper;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * MrLiu253@163.com
 *
 * @time 2018/7/31
 */

@SuppressWarnings("EmptyCatchBlock")
public abstract class BaseFragment<V extends BaseView, P extends RxPresenter<V>> extends RxFragment implements MvpCallback<V, P>, ISupportFragment, View.OnClickListener {

    final SupportFragmentDelegate mDelegate = new SupportFragmentDelegate(this);

    protected FragmentActivity mActivity;

    protected View mToolbarView;
    protected View mRootView;
    protected View mBaseView;
    protected ImmersionBar mImmersionBar;
    protected Toolbar mToolbar;
    protected ImageButton mImageButton;
    protected TextView mTextView;
    protected TextView mToolbarRightTv;
    protected ImageView mImageView;
    protected ImageView mImageView_marg;
    private SweetAlertDialog mSweetAlertDialog;


    private ActivityMvpDelegate<V, P> mvpDelegate;

    private ActivityMvpDelegate<V, P> getMvpDelegate() {
        if (this.mvpDelegate == null) {
            this.mvpDelegate = new ActivityMvpDelegateImpl(this);
        }
        return this.mvpDelegate;
    }


    private P presenter;
    private V view;

    @Override
    public P createPresenter() {
        return this.presenter;
    }

    @Override
    public P getMvpPresenter() {
        return this.presenter;
    }

    @Override
    public void setMvpPresenter(P paramP) {
        this.presenter = paramP;
    }


    @Override
    public V createView() {
        return this.view;
    }

    @Override
    public V getMvpView() {
        return this.view;
    }

    @Override
    public void setMvpView(V paramV) {
        this.view = paramV;
    }


    protected FragmentComponent getFragmentComponent() {

        return DaggerFragmentComponent.builder().appComponent(App.getInstance().getAppComponent()).fragmentModule(getFragmentModule()).build();
    }

    protected FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        } else {

            mRootView = inflater.inflate(R.layout.base_fragment, container, false);
            mBaseView = inflater.inflate(getLayoutId(), container, false);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mBaseView.setLayoutParams(params);
            FrameLayout frameLayout = mRootView.findViewById(R.id.base_fragment);
            frameLayout.addView(mBaseView);
        }
        return mRootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mActivity == null) {
            return;
        }

        mToolbarView = mRootView.findViewById(R.id.toolbar_v);
        mToolbar = mRootView.findViewById(R.id.base_toolbar);
        mImageButton = mRootView.findViewById(R.id.toolbar_left_ib);
        mImageButton.setOnClickListener(this);
        mTextView = mRootView.findViewById(R.id.toolbar_title);
        mToolbarRightTv = mRootView.findViewById(R.id.toolbar_right_bt);
        mToolbarRightTv.setOnClickListener(this);
        mImageView = mRootView.findViewById(R.id.toolbar_right_iv);
        mImageView.setOnClickListener(this);
        mImageView_marg = mRootView.findViewById(R.id.toolbar_right_my_iv);
        mImageView.setOnClickListener(this);
        //设置toolbar
        ImmersionBar.setTitleBar(mActivity, mToolbar);

        //设置dagger
        initInject();
        //设置代理
        getMvpDelegate().onCreate(savedInstanceState);
        //初始化沉浸式
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }

        try {
            view.setOnTouchListener(mTouchListener);
        } catch (Exception e) {

        }

        initView(view, savedInstanceState);

        //设置软键盘监听
//        setKeyboardInfo();

    }

    protected void showDialog(String string) {
        if (mActivity == null) return;
        if (mSweetAlertDialog == null) {
            mSweetAlertDialog = new SweetAlertDialog(mActivity, SweetAlertDialog.PROGRESS_TYPE);
            mSweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            mSweetAlertDialog.setTitleText(string);
            mSweetAlertDialog.setCancelable(false);
            mSweetAlertDialog.show();
        } else {
            mSweetAlertDialog.setTitleText(string);
            mSweetAlertDialog.setCancelable(false);
            mSweetAlertDialog.show();
        }

    }

    protected void dismissDialog() {
        if (mSweetAlertDialog != null && mSweetAlertDialog.isShowing()) {
            mSweetAlertDialog.dismiss();
        }
    }


    protected abstract void initInject();

    protected abstract int getLayoutId();

    protected abstract void initView(View view, Bundle savedInstanceState);


    /**
     * 设置字体加粗
     *
     * @param paint
     */
    protected void setPaint(TextPaint paint) {
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(0.4f);
    }


    protected void setLeftImage(int id) {
        if (getResources() != null)
            mImageButton.setImageDrawable(getResources().getDrawable(id));
    }

    protected void setRight() {
        mToolbarRightTv.setVisibility(View.VISIBLE);
        mImageView.setVisibility(View.VISIBLE);
    }
    protected void setRightText(String txt,int color) {
        mToolbarRightTv.setVisibility(View.VISIBLE);
        mToolbarRightTv.setTextColor(color);
        mToolbarRightTv.setText(txt);
    }

    /**
     * 设置toolbar背景颜色
     *
     * @param color
     */
    protected void setBackground(int color) {
        try {
            mToolbar.setBackgroundColor(mActivity.getResources().getColor(color));
        } catch (Exception e) {

        }
    }


    protected void initImmersionBar() {

        if (mImmersionBar == null) {
            //在BaseActivity里初始化
            mImmersionBar = ImmersionBar.with(this);
            mImmersionBar //解决软键盘与底部输入框冲突问题
                    .statusBarDarkFont(true, 0.1f)
                    .flymeOSStatusBarFontColor(R.color.border_333)
                    .init();
        } else {
            mImmersionBar  //解决软键盘与底部输入框冲突问题
                    .statusBarDarkFont(true, 0.1f)
                    .flymeOSStatusBarFontColor(R.color.border_333)
                    .init();
        }


    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }


    protected void onStar() {
        super.onStart();
        getMvpDelegate().onStart();
    }

    private long lastClickTime = 0;

    /**
     * 防止多次点击
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    protected boolean preventOnClick() {

        long time = SystemClock.uptimeMillis(); // 此方法仅用于Android
        if (time - lastClickTime > 1000) {
            lastClickTime = time;
            return true;
        }
        return false;
    }


    @Override
    public SupportFragmentDelegate getSupportDelegate() {
        return mDelegate;
    }

    /**
     * Perform some extra transactions.
     * 额外的事务：自定义Tag，添加SharedElement动画，操作非回退栈Fragment
     */
    @Override
    public ExtraTransaction extraTransaction() {
        return mDelegate.extraTransaction();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mDelegate.onAttach(activity);
        mActivity = mDelegate.getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDelegate.onCreate(savedInstanceState);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return mDelegate.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDelegate.onActivityCreated(savedInstanceState);
        mDelegate.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mDelegate.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getMvpDelegate().onResume();
        mDelegate.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        getMvpDelegate().onPause();
        mDelegate.onPause();
    }

    @Override
    public void onDestroyView() {
        mDelegate.onDestroyView();
        super.onDestroyView();
        getMvpDelegate().onDestroy();
        if (mSweetAlertDialog != null && mSweetAlertDialog.isShowing()) {
            mSweetAlertDialog.dismiss();
        }
        if (mSweetAlertDialog != null) {
            mSweetAlertDialog = null;
        }
    }

    @Override
    public void onDestroy() {
        mDelegate.onDestroy();
        super.onDestroy();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mDelegate.onHiddenChanged(hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mDelegate.setUserVisibleHint(isVisibleToUser);
    }

    /**
     * Causes the Runnable r to be added to the action queue.
     * <p>
     * The runnable will be run after all the previous action has been run.
     * <p>
     * 前面的事务全部执行后 执行该Action
     *
     * @deprecated Use {@link #post(Runnable)} instead.
     */
    @Deprecated
    @Override
    public void enqueueAction(Runnable runnable) {
        mDelegate.enqueueAction(runnable);
    }

    /**
     * Causes the Runnable r to be added to the action queue.
     * <p>
     * The runnable will be run after all the previous action has been run.
     * <p>
     * 前面的事务全部执行后 执行该Action
     */
    @Override
    public void post(Runnable runnable) {
        mDelegate.post(runnable);
    }

    /**
     * Called when the enter-animation end.
     * 入栈动画 结束时,回调
     */
    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        mDelegate.onEnterAnimationEnd(savedInstanceState);
    }


    /**
     * Lazy initial，Called when fragment is first called.
     * <p>
     * 同级下的 懒加载 ＋ ViewPager下的懒加载  的结合回调方法
     */
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        mDelegate.onLazyInitView(savedInstanceState);
    }

    /**
     * Called when the fragment is visible.
     * 当Fragment对用户可见时回调
     * <p>
     * Is the combination of  [onHiddenChanged() + onResume()/onPause() + setUserVisibleHint()]
     */
    @Override
    public void onSupportVisible() {
        mDelegate.onSupportVisible();
    }

    /**
     * Called when the fragment is invivible.
     * <p>
     * Is the combination of  [onHiddenChanged() + onResume()/onPause() + setUserVisibleHint()]
     */
    @Override
    public void onSupportInvisible() {
        mDelegate.onSupportInvisible();
    }

    /**
     * Return true if the fragment has been supportVisible.
     */
    @Override
    final public boolean isSupportVisible() {
        return mDelegate.isSupportVisible();
    }

    /**
     * Set fragment animation with a higher priority than the ISupportActivity
     * 设定当前Fragmemt动画,优先级比在SupportActivity里高
     */
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return mDelegate.onCreateFragmentAnimator();
    }

    /**
     * 获取设置的全局动画 copy
     *
     * @return FragmentAnimator
     */
    @Override
    public FragmentAnimator getFragmentAnimator() {
        return mDelegate.getFragmentAnimator();
    }

    /**
     * 设置Fragment内的全局动画
     */
    @Override
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        mDelegate.setFragmentAnimator(fragmentAnimator);
    }

    /**
     * 按返回键触发,前提是SupportActivity的onBackPressed()方法能被调用
     *
     * @return false则继续向上传递, true则消费掉该事件
     */
    @Override
    public boolean onBackPressedSupport() {
        return mDelegate.onBackPressedSupport();
    }

    /**
     * 类似 {@link Activity#setResult(int, Intent)}
     * <p>
     * Similar to {@link Activity#setResult(int, Intent)}
     *
     * @see #startForResult(ISupportFragment, int)
     */
    @Override
    public void setFragmentResult(int resultCode, Bundle bundle) {
        mDelegate.setFragmentResult(resultCode, bundle);
    }

    /**
     * 类似  {@link Activity#onActivityResult(int, int, Intent)}
     * <p>
     * Similar to {@link Activity#onActivityResult(int, int, Intent)}
     *
     * @see #startForResult(ISupportFragment, int)
     */
    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        mDelegate.onFragmentResult(requestCode, resultCode, data);
    }

    /**
     * 在start(TargetFragment,LaunchMode)时,启动模式为SingleTask/SingleTop, 回调TargetFragment的该方法
     * 类似 {@link Activity#onNewIntent(Intent)}
     * <p>
     * Similar to {@link Activity#onNewIntent(Intent)}
     *
     * @param args putNewBundle(Bundle newBundle)
     * @see #start(ISupportFragment, int)
     */
    @Override
    public void onNewBundle(Bundle args) {
        mDelegate.onNewBundle(args);
    }

    /**
     * 添加NewBundle,用于启动模式为SingleTask/SingleTop时
     *
     * @see #start(ISupportFragment, int)
     */
    @Override
    public void putNewBundle(Bundle newBundle) {
        mDelegate.putNewBundle(newBundle);
    }

    /****************************************以下为可选方法(Optional methods)******************************************************/
    // 自定制Support时，可移除不必要的方法

    /**
     * 隐藏软键盘
     */
    protected void hideSoftInput() {
        mDelegate.hideSoftInput();
    }

    /**
     * 显示软键盘,调用该方法后,会在onPause时自动隐藏软键盘
     */
    protected void showSoftInput(final View view) {
        mDelegate.showSoftInput(view);
    }

    /**
     * 加载根Fragment, 即Activity内的第一个Fragment 或 Fragment内的第一个子Fragment
     *
     * @param containerId 容器id
     * @param toFragment  目标Fragment
     */
    public void loadRootFragment(int containerId, ISupportFragment toFragment) {
        mDelegate.loadRootFragment(containerId, toFragment);
    }

    public void loadRootFragment(int containerId, ISupportFragment toFragment, boolean addToBackStack, boolean allowAnim) {
        mDelegate.loadRootFragment(containerId, toFragment, addToBackStack, allowAnim);
    }

    public void start(ISupportFragment toFragment) {
        mDelegate.start(toFragment);
    }

    /**
     * @param launchMode Similar to Activity's LaunchMode.
     */
    public void start(final ISupportFragment toFragment, @LaunchMode int launchMode) {
        mDelegate.start(toFragment, launchMode);
    }

    /**
     * Launch an fragment for which you would like a result when it poped.
     */
    public void startForResult(ISupportFragment toFragment, int requestCode) {
        mDelegate.startForResult(toFragment, requestCode);
    }

    /**
     * Start the target Fragment and pop itself
     */
    public void startWithPop(ISupportFragment toFragment) {
        mDelegate.startWithPop(toFragment);
    }

    /**
     * @see #popTo(Class, boolean)
     * +
     * @see #start(ISupportFragment)
     */
    public void startWithPopTo(ISupportFragment toFragment, Class<?> targetFragmentClass, boolean includeTargetFragment) {
        mDelegate.startWithPopTo(toFragment, targetFragmentClass, includeTargetFragment);
    }

    public void replaceFragment(ISupportFragment toFragment, boolean addToBackStack) {
        mDelegate.replaceFragment(toFragment, addToBackStack);
    }

    public void pop() {
        mDelegate.pop();
    }

    /**
     * Pop the last fragment transition from the manager's fragment
     * back stack.
     * <p>
     * 出栈到目标fragment
     *
     * @param targetFragmentClass   目标fragment
     * @param includeTargetFragment 是否包含该fragment
     */
    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment) {
        mDelegate.popTo(targetFragmentClass, includeTargetFragment);
    }

    /**
     * 获取栈内的fragment对象
     */
    public <T extends ISupportFragment> T findChildFragment(Class<T> fragmentClass) {
        return SupportHelper.findFragment(getChildFragmentManager(), fragmentClass);
    }

    /**
     * 键盘是否已经弹起 true表示键盘已经弹起
     *
     * @param visible
     */
    protected void onKeyboardVisible(boolean visible) {
    }

    //点击输入框其他地方的监听
    public View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if (event.getAction() == MotionEvent.ACTION_DOWN && mActivity != null) {

                View view = mActivity.getCurrentFocus();

                onHeiKeyboard();
                if (isShouldHideKeyboard(view, event)) {
                    onHeiDIYKeyboard();
                }

            }
            return false;
        }
    };


    //点击了屏幕
    protected void onHeiKeyboard() {
    }

    //隐藏键盘以外地方
    protected void onHeiDIYKeyboard() {
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }


}
