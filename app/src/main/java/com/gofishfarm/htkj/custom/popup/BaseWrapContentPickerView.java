package com.gofishfarm.htkj.custom.popup;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.gofishfarm.htkj.R;

public class BaseWrapContentPickerView {

    public ViewGroup decorView;
    private Context context;
    protected ViewGroup contentContainer;
    private ViewGroup rootView;

    private boolean dismissing;

    private Animation outAnim;
    private Animation inAnim;

    protected int animGravity = Gravity.BOTTOM;

    protected View clickView;
    private boolean isAnim = true;

    public BaseWrapContentPickerView(Context context) {
        this.context = context;
    }

    protected void initViews() {

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if (decorView == null) {
            decorView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        }
        rootView = (ViewGroup) layoutInflater.inflate(R.layout.layout_basepickerview, decorView, false);
        rootView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        contentContainer = rootView.findViewById(R.id.content_container);
        contentContainer.setLayoutParams(params);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseWrapContentPickerView.this.dismiss();
            }
        });
        setKeyBackCancelable(true);
    }

    protected void initAnim() {
        inAnim = getInAnimation();
        outAnim = getOutAnimation();
    }

    protected void initEvents() {
    }


    /**
     * @param v
     * @param isAnim
     */
    public void show(View v, boolean isAnim) {
        this.clickView = v;
        this.isAnim = isAnim;
        show();
    }

    public void show(boolean isAnim) {
        this.isAnim = isAnim;
        show();
    }

    public void show(View v) {
        this.clickView = v;
        show();
    }


    /**
     *
     */
    public void show() {

        if (isShowing()) {
            return;
        }
        onAttached(rootView);
        rootView.requestFocus();
    }


    /**
     *
     *
     * @param view 这个View
     */
    private void onAttached(View view) {
        decorView.addView(view);
        if (isAnim) {
            contentContainer.startAnimation(inAnim);
        }
    }


    /**
     *
     *
     * @return
     */
    public boolean isShowing() {

        return rootView.getParent() != null;

    }

    public void dismiss() {

        if (dismissing) {
            return;
        }

        if (isAnim) {
            //消失动画
            outAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    dismissImmediately();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            contentContainer.startAnimation(outAnim);
        } else {
            dismissImmediately();
        }
        dismissing = true;


    }

    public void dismissImmediately() {
        decorView.post(new Runnable() {
            @Override
            public void run() {
                //从根视图移除
                decorView.removeView(rootView);
                dismissing = false;
            }
        });


    }

    private Animation getInAnimation() {
        int res = PickerViewAnimateUtil.getAnimationResource(this.animGravity, true);
        return AnimationUtils.loadAnimation(context, res);
    }

    private Animation getOutAnimation() {
        int res = PickerViewAnimateUtil.getAnimationResource(this.animGravity, false);
        return AnimationUtils.loadAnimation(context, res);
    }

    public void setKeyBackCancelable(boolean isCancelable) {

        ViewGroup View;

        View = rootView;

        View.setFocusable(isCancelable);
        View.setFocusableInTouchMode(isCancelable);
        if (isCancelable) {
            View.setOnKeyListener(onKeyBackListener);
        } else {
            View.setOnKeyListener(null);
        }
    }

    private View.OnKeyListener onKeyBackListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {

                BaseWrapContentPickerView.this.dismiss();
                return true;
            }
            return false;
        }
    };

    protected BaseWrapContentPickerView setOutSideCancelable(boolean isCancelable) {

        if (rootView != null) {
            View view = rootView.findViewById(R.id.outmost_container);

            if (isCancelable) {
            } else {
                view.setOnTouchListener(null);
            }
        }

        return this;
    }


    private final View.OnTouchListener onCancelableTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                BaseWrapContentPickerView.this.dismiss();
            }
            return false;
        }
    };

    public View findViewById(int id) {
        return contentContainer.findViewById(id);
    }


    public ViewGroup getDialogContainerLayout() {
        return contentContainer;
    }


}
