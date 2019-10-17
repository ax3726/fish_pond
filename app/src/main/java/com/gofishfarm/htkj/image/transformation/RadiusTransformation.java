package com.gofishfarm.htkj.image.transformation;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * @author by sunfusheng on 2017/6/6.
 */
public class RadiusTransformation extends BitmapTransformation {

    private float radius = 0f;

    public RadiusTransformation() {
        this(4);
    }

    /**
     * 自定义圆角大小
     *
     * @param dp
     */
    public RadiusTransformation(int dp) {
        super();
        this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform);
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;
        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);
        return result;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }





//    private final String ID = getClass().getName();
//
//    private int radius;
//
//    public RadiusTransformation(Context context, int radius) {
//        this.radius = Utils.dp2px(context, radius);
//    }
//
//    @Override
//    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
//        int width = toTransform.getWidth();
//        int height = toTransform.getHeight();
//
//        Bitmap bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888);
//        bitmap.setHasAlpha(true);
//        Canvas canvas = new Canvas(bitmap);
//        Paint paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setShader(new BitmapShader(toTransform, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
//        canvas.drawRoundRect(new RectF(0, 0, width, height), radius, radius, paint);
//        return bitmap;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (obj instanceof RadiusTransformation) {
//            RadiusTransformation other = (RadiusTransformation) obj;
//            return radius == other.radius;
//        }
//        return false;
//    }
//
//    @Override
//    public int hashCode() {
//        return Util.hashCode(ID.hashCode(), Util.hashCode(radius));
//    }
//
//    @Override
//    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
//        messageDigest.update((ID + radius).getBytes(CHARSET));
//    }

}
