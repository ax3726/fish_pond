package com.gofishfarm.htkj.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;


import com.gofishfarm.htkj.R;

import java.util.List;


/**
 * MrLiu253@163.com
 *
 * @time 2018/7/24
 */

public class CustomKeyboardView extends KeyboardView {

    private Paint paint;
    private Context mContext;
    private Rect bounds ;

    public CustomKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView(context);
    }


    public CustomKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView(context);
    }

    private void initView(Context context) {
        paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.DEFAULT);
        paint.setAntiAlias(true);
        paint.setTextSize(context.getResources().getDimension(R.dimen.sp_23));
        paint.setColor(context.getResources().getColor(R.color.border_333));
        bounds = new Rect();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Keyboard keyboard = getKeyboard();
        if (keyboard == null) return;
        List<Keyboard.Key> keys = keyboard.getKeys();

        if (keys != null && keys.size() > 0) {

            for (Keyboard.Key key : keys) {

                //只画需要的按键背景
                if (key.codes[0] == 49 || key.codes[0] == 50 ||
                        key.codes[0] == 51 || key.codes[0] == 52 ||
                        key.codes[0] == 53 || key.codes[0] == 54 ||
                        key.codes[0] == 55 || key.codes[0] == 56 ||
                        key.codes[0] == 57 || key.codes[0] == 48
                        ) {
                    drawKeyBackground(R.drawable.selector_keyboard_key, canvas, key);
                }


                if (key.codes[0] == -5 || key.codes[0] == 88) {
                    drawKeyBackground(R.drawable.keyboard_special_key_bg, canvas, key);
                }

                if (key.codes[0] == -5)
                    drawKeyBackground(R.drawable.ic_bg_keyboard_delete, canvas, key);


                if (key.label != null) {

                    paint.getTextBounds(key.label.toString(), 0, key.label.toString()
                            .length(), bounds);
                    canvas.drawText(key.label.toString(), key.x + (key.width / 2),
                            (key.y + key.height / 2) + bounds.height() / 2, paint);
                }


            }
        }


    }


    /**
     *
     *
     * @param drawableId
     * @param canvas
     * @param key
     */
    private void drawKeyBackground(int drawableId, Canvas canvas, Keyboard.Key key) {
        if (mContext == null) return;


        Drawable dr = mContext.getResources().getDrawable(drawableId);
        int[] drawableState = key.getCurrentDrawableState();
        if (key.codes[0] != 0) {
            dr.setState(drawableState);
        }
        dr.setBounds(key.x, key.y, key.x + key.width, key.y
                + key.height);
        dr.draw(canvas);
    }


}
