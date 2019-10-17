package com.gofishfarm.htkj.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * @author：MrHu
 * @Date ：2019-01-07
 * @Describtion ：
 */
public class GlideUtils {

    public static void initImageWithFileCache(Context context, String url, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .centerCrop()
                .into(imageView);
    }

    public static void initImageNoCache(Context context, String url, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                .skipMemoryCache(true)
                .dontAnimate()
                .centerCrop()
                .into(imageView);
    }

    public static void clearMemoryCache(Context context) {
        GlideApp.get(context).clearMemory();
    }

    public static void clearFileCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                GlideApp.get(context).clearDiskCache();
            }
        }).start();
    }
}
