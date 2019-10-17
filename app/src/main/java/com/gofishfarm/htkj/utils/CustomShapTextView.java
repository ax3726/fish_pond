package com.gofishfarm.htkj.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;

import com.gofishfarm.htkj.R;


/**
 * Created by Android Studio.
 * User: MrHu
 * Date: 2019-03-05
 * Time: 下午 06:08
 *
 * @Description:
 */
public class CustomShapTextView extends android.support.v7.widget.AppCompatTextView {
    private Context mContext;
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 画笔颜色 默认灰色
     */
    private int mPaintNormalColor = 0xFFDCDCDC;
    /**
     * 画笔颜色 选中时的颜色,默认灰色
     */
    private int mPaintSelectColor = 0xFFDCDCDC;
    /**
     * 是否填充颜色
     */
    private boolean isFillColor = false;

    private int backgroundColor = Color.parseColor("#FF2F2F");
    private boolean initBgFlag;

    public CustomShapTextView(Context context) {
        super(context);
    }

    public CustomShapTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initbg();
//        initPaint(context,attrs);

    }

    public CustomShapTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        initPaint(context,attrs);
    }

    /**
     * 初始化画笔和自定义属性
     * @param context
     * @param attrs
     */
    private void initPaint(Context context,AttributeSet attrs){
        mContext = context;
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomShapTextView);
        mPaintNormalColor = typeArray.getColor(R.styleable.CustomShapTextView_paintNormalColor,mPaintNormalColor);
        mPaintSelectColor = typeArray.getColor(R.styleable.CustomShapTextView_paintSelectColor,mPaintSelectColor);
        isFillColor = typeArray.getBoolean(R.styleable.CustomShapTextView_isFillColor,isFillColor);
        mPaint = new Paint();
    }


    private void initbg() {
        setGravity(Gravity.CENTER);
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @SuppressWarnings("deprecation")
            @Override
            public boolean onPreDraw() {
                if (!initBgFlag) {
                    CustomShapTextView.this.setBackgroundDrawable(createStateListDrawable(
                            (getHeight() > getWidth() ? getHeight() : getWidth()) / 2, backgroundColor));
                    initBgFlag = true;
                    return false;
                }
                return true;
            }
        });
    }

    /**
     * @param radius 圆角角度
     * @param color  填充颜色
     * @return StateListDrawable 对象
     * @author zy
     */
    public static StateListDrawable createStateListDrawable(int radius, int color) {
        StateListDrawable bg = new StateListDrawable();
        GradientDrawable gradientStateNormal = new GradientDrawable();
        gradientStateNormal.setColor(color);
        gradientStateNormal.setShape(GradientDrawable.RECTANGLE);
        gradientStateNormal.setCornerRadius(50);
        gradientStateNormal.setStroke(0, 0);
        bg.addState(View.EMPTY_STATE_SET, gradientStateNormal);
        return bg;
    }

    /**
     * 调用onDraw绘制边框
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //抗锯齿
        mPaint.setAntiAlias(true);
        if (isFillColor) {
            //画笔颜色
            mPaint.setColor(mPaintSelectColor);
            mPaint.setStyle(Paint.Style.FILL);
        }else{
            //画笔颜色
            mPaint.setColor(mPaintNormalColor);
            //画笔样式:空心
            mPaint.setStyle(Paint.Style.STROKE);
        }

        //创建一个区域,限制圆弧范围
        RectF rectF = new RectF();
        //设置半径,比较长宽,取最大值
        int radius = getMeasuredWidth() > getMeasuredHeight() ? getMeasuredWidth() : getMeasuredHeight();
        //设置Padding 不一致,绘制出的是椭圆;一致的是圆形
        rectF.set(getPaddingLeft(),getPaddingTop(),radius-getPaddingRight(),radius-getPaddingBottom());
        //绘制圆弧
        canvas.drawArc(rectF,0,360,false,mPaint);

        //最后调用super方法,解决文本被所绘制的圆圈背景锁覆盖的问题
        super.onDraw(canvas);
    }

    /**
     * 设置是否填充颜色
     * @param isFill
     */
    public void setFillColor(boolean isFill){
        this.isFillColor = isFill;
        invalidate();
    }
}
