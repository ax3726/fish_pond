package com.gofishfarm.htkj.image;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;
import com.gofishfarm.htkj.utils.FileUtils;

/**
 * @author：MrHu
 * @Date ：2019-01-06
 * @Describtion ：
 */
@GlideModule
public class MyAppGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        //        设置缓存大小为20mb
        int memoryCacheSizeBytes = 1024 * 1024 * 20; // 20mb
        //        设置内存缓存大小
        builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes));
        //        根据SD卡是否可用选择是在内部缓存还是SD卡缓存
        if (FileUtils.isSdCardAvailable()) {
            builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context, "SFYBImages", memoryCacheSizeBytes));
        } else {
            builder.setDiskCache(new InternalCacheDiskCacheFactory(context, "SFYBImages", memoryCacheSizeBytes));
        }
    }

    //    针对V4用户可以提升速度
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

//磁盘缓存清理（子线程）
//GlideApp.get(context).clearDiskCache();
//内存缓存清理（主线程）
//GlideApp.get(context).clearMemory();
}
