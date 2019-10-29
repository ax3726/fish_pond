package com.gofishfarm.htkj.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

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

    public GestureMoveView(Context context) {
        super(context);
    }

    public GestureMoveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GestureMoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setMoveEvent(MotionEvent e1, MotionEvent e2)
    {
        startX=e1.getX();
        startY=e1.getY();
        endX=e2.getX();
        endY=e2.getY();
        invalidate();
    }

    public void releaseMove()
    {
        startX=0;
        startY=0;
        endX=0;
        endY=0;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#660080FF"));
        paint.setStrokeWidth(6);

        canvas.drawLine(startX, startY, endX, endY, paint);
    }


}
