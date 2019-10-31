package com.gofishfarm.htkj.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.gofishfarm.htkj.R;

/**
 * @author ：LiMing
 * @date ：2019-10-29
 * @desc ：
 */
public class GestureMoveView extends View {
    float startX = 0;
    float startY = 0;

    float endX = 0;
    float endY = 0;
    private Bitmap mBitmap;
    float mVelocityX;
    float mVvelocityY;

    public GestureMoveView(Context context) {
        super(context);
        init();
    }

    public GestureMoveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GestureMoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBitmap = createBitMap();
    }


    public void setMoveEvent(MotionEvent e1, MotionEvent e2) {
        startX = e1.getX();
        startY = e1.getY();
        endX = e2.getX();
        endY = e2.getY();
        invalidate();
    }

    public void releaseMove() {
        startX = 0;
        startY = 0;
        endX = 0;
        endY = 0;
        invalidate();
    }

    private Bitmap createBitMap() {
        Bitmap bitmap = null;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Drawable vectorDrawable = getContext().getDrawable(R.drawable.trace_icon);
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        } else {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.trace_icon);
        }
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (startX == 0 && startY == 0 && endX == 0 && endY == 0) {
            return;
        }
        // 画出原图像
//        canvas.drawBitmap(mBitmap, (startX + endX) / 2, (startY + endY) / 2, null);
        Matrix matrix = new Matrix();
        matrix.postRotate((float) count(), mBitmap.getWidth() * 0.5f, mBitmap.getHeight() * 0.5f);
        mVelocityX = -((startX + endX) / 2);
        mVvelocityY = -((startY + endY) / 2);
        matrix.postTranslate(-mVelocityX, -mVvelocityY);
        canvas.drawBitmap(mBitmap, matrix, null);
    }

    /**
     * 弧度转角度
     *
     * @param radian 弧度
     * @return 角度[0, 360)
     */
    private double radian2Angle(double radian) {
        double tmp = Math.round(radian / Math.PI * 180);
        tmp = tmp - 90;
        return tmp >= 0 ? tmp : 360 + tmp;
    }


    private double count() {
        // 两点在X轴的距离
        float lenX = (float) (endX - startX);
        // 两点在Y轴距离
        float lenY = (float) (endY - startY);
        // 两点距离
        float lenXY = (float) Math.sqrt((double) (lenX * lenX + lenY * lenY));
        // 计算弧度
        double radian = Math.acos(lenX / lenXY) * (endY < startY ? -1 : 1);
        // 计算角度
        return radian2Angle(radian);
    }


}
