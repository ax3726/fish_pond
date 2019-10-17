package com.gofishfarm.htkj.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gofishfarm.htkj.App;
import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.base.support.ActivityMvpDelegate;
import com.gofishfarm.htkj.base.support.ActivityMvpDelegateImpl;
import com.gofishfarm.htkj.base.support.MvpCallback;
import com.gofishfarm.htkj.module.ActivityComponent;
import com.gofishfarm.htkj.module.ActivityModule;
import com.gofishfarm.htkj.module.DaggerActivityComponent;
import com.gofishfarm.htkj.sweetAlert.SweetAlertDialog;
import com.gyf.barlibrary.ImmersionBar;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportActivity;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportActivityDelegate;
import me.yokeyword.fragmentation.SupportHelper;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * MrLiu253@163.com
 *
 * @time 2018/7/30
 */

public abstract class BaseActivity<V extends BaseView, P extends RxPresenter<V>> extends RxAppCompatActivity implements MvpCallback<V, P>, ISupportActivity, View.OnClickListener {

    protected final SupportActivityDelegate mDelegate = new SupportActivityDelegate(this);
    protected ImmersionBar mImmersionBar;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //绑定dagger
        initInject();
        //绑定生命周期
        getMvpDelegate().onCreate(savedInstanceState);

        mDelegate.onCreate(savedInstanceState);

        setContentView(setLayoutId());


        //初始化沉浸式
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
        //自定义
        initView(savedInstanceState);
        //监听
        initListener();
    }

    protected abstract void initInject();

    protected abstract int setLayoutId();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initListener();

    //    ---------------------显示页面的加载进度条------------------------------------

    protected Dialog mDialog;//加载对话框

    /**
     * 显示圆形加载进度条
     */
    public void showProgress(String msg) {
        if (null == mDialog) {
            LayoutInflater inflater = LayoutInflater.from(this);
            View layout = inflater.inflate(R.layout.loading_dialog, null);
            mDialog = new Dialog(this, R.style.dialog_parent);

            // 不可以用"返回键"取消
            mDialog.setCancelable(false);
            //设置点击外部是否可以消失
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setContentView(layout, new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT));
            ((TextView) mDialog.findViewById(R.id.tv_load_dialog)).setText(msg);
            mDialog.show();
        } else {
            ((TextView) mDialog.findViewById(R.id.tv_load_dialog)).setText(msg);
            mDialog.show();
        }
    }


    /**
     * 取消加载进度条
     */
    public void hideProgress() {
        if (null != mDialog && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    /**
     * 清除
     */
    public void hideClearProgress() {
        if (null != mDialog && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (null != mDialog) {
            mDialog = null;
        }
    }

//    ---------------------显示页面的加载进度条------------------------------------

    protected void showDialog(String string) {
        if (mSweetAlertDialog == null) {
            mSweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
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

    //用于子类获取
    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder().appComponent(App.getInstance().getAppComponent()).activityModule(getActivityModule()).build();
    }

    private ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    private void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);

        mImmersionBar
                .statusBarDarkFont(true, 0.1f)
                .flymeOSStatusBarFontColor(R.color.border_333)
                .navigationBarWithKitkatEnable(false)
                .init();

    }

    /**
     * 设置字体加粗
     *
     * @param paint
     */
    protected void setPaint(TextPaint paint) {
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(0.4f);
    }

    protected void onStar() {
        super.onStart();
        getMvpDelegate().onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMvpDelegate().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getMvpDelegate().onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getMvpDelegate().onStop();
    }

    @Override
    protected void onDestroy() {
        mDelegate.onDestroy();
        super.onDestroy();
        getMvpDelegate().onDestroy();

        if (mImmersionBar != null) {
            mImmersionBar.destroy();  //在BaseActivity里销毁
        }
        if (mSweetAlertDialog != null && mSweetAlertDialog.isShowing()) {
            mSweetAlertDialog.dismiss();
        }
        if (mSweetAlertDialog != null) {
            mSweetAlertDialog = null;
        }
        hideClearProgress();

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


    @Override
    public SupportActivityDelegate getSupportDelegate() {
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
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDelegate.onPostCreate(savedInstanceState);
    }

    //-----------------------------快速点击，软键盘关闭------------------------------------------
    private static final int MIN_DELAY_TIME = 200;  // 两次点击间隔不能少于200ms
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    //-----------------------------快速点击，软键盘关闭------------------------------------------
    public boolean caCloaseKeyBord = true;

    public void setcaCloaseKeyBord(Boolean mcaCloaseKeyBord) {
        caCloaseKeyBord = mcaCloaseKeyBord;
    }

    /**
     * Note： return mDelegate.dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev);
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //添加快速点击处理---------
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isFastClick()) {
                return true;
            }
        }
        //添加快速点击处理---------
        if (caCloaseKeyBord) {
            //点击edittext之外关闭键盘————————
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (isShouldHideInput(v, ev)) {

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
                return super.dispatchTouchEvent(ev);
            }
        }
        //点击edittext之外关闭键盘————————
        return mDelegate.dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev);


    }

    /**
     * 不建议复写该方法,请使用 {@link #onBackPressedSupport} 代替
     */
    @Override
    final public void onBackPressed() {
        mDelegate.onBackPressed();
    }

    /**
     * 该方法回调时机为,Activity回退栈内Fragment的数量 小于等于1 时,默认finish Activity
     * 请尽量复写该方法,避免复写onBackPress(),以保证SupportFragment内的onBackPressedSupport()回退事件正常执行
     */
    @Override
    public void onBackPressedSupport() {
        mDelegate.onBackPressedSupport();
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
     * Set all fragments animation.
     * 设置Fragment内的全局动画
     */
    @Override
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        mDelegate.setFragmentAnimator(fragmentAnimator);
    }

    /**
     * Set all fragments animation.
     * 构建Fragment转场动画
     * <p/>
     * 如果是在Activity内实现,则构建的是Activity内所有Fragment的转场动画,
     * 如果是在Fragment内实现,则构建的是该Fragment的转场动画,此时优先级 > Activity的onCreateFragmentAnimator()
     *
     * @return FragmentAnimator对象
     */
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return mDelegate.onCreateFragmentAnimator();
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


    /****************************************以下为可选方法(Optional methods)******************************************************/

    // 选择性拓展其他方法
    protected void loadRootFragment(int containerId, @NonNull ISupportFragment toFragment) {
        mDelegate.loadRootFragment(containerId, toFragment);
    }

    public void start(ISupportFragment toFragment) {
        mDelegate.start(toFragment);
    }

    /**
     * @param launchMode Same as Activity's LaunchMode.
     */
    public void start(ISupportFragment toFragment, @ISupportFragment.LaunchMode int launchMode) {
        mDelegate.start(toFragment, launchMode);
    }

    /**
     * It is recommended to use {@link SupportFragment#startWithPopTo(ISupportFragment, Class, boolean)}.
     *
     * @see #popTo(Class, boolean)
     * +
     * @see #start(ISupportFragment)
     */
    public void startWithPopTo(ISupportFragment toFragment, Class<?> targetFragmentClass,
                               boolean includeTargetFragment) {
        mDelegate.startWithPopTo(toFragment, targetFragmentClass, includeTargetFragment);
    }

    /**
     * Pop the fragment.
     */
    public void pop() {
        mDelegate.pop();
    }

    /**
     * Pop the last fragment transition from the manager's fragment
     * back stack.
     */
    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment) {
        mDelegate.popTo(targetFragmentClass, includeTargetFragment);
    }

    /**
     * If you want to begin another FragmentTransaction immediately after popTo(), use this method.
     * 如果你想在出栈后, 立刻进行FragmentTransaction操作，请使用该方法
     */
    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable
            afterPopTransactionRunnable) {
        mDelegate.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable);
    }

    public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment, Runnable
            afterPopTransactionRunnable, int popAnim) {
        mDelegate.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable, popAnim);
    }

    /**
     * 得到位于栈顶Fragment
     */
    public ISupportFragment getTopFragment() {
        return SupportHelper.getTopFragment(getSupportFragmentManager());
    }

    /**
     * 获取栈内的fragment对象
     */
    protected <T extends ISupportFragment> T findFragment(Class<T> fragmentClass) {
        return SupportHelper.findFragment(getSupportFragmentManager(), fragmentClass);
    }
}
