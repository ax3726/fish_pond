package com.gofishfarm.htkj.helper;

import android.app.Activity;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.lang.ref.WeakReference;

/**
 * MrLiu253@163.com
 * @time 2018/1/3
 */

public class ShareHelper extends ShareAction implements Share {

    WeakReference mActivity;

    public ShareHelper(Activity activity) {
        super(activity);
        mActivity = new WeakReference<>(activity);
    }


    @Override
    public void share(SHARE_MEDIA platform, UMImage umImage) {
        if (mActivity.get() == null)
            return;
        Activity mA = (Activity) mActivity.get();
        if (mA != null) {
            this.setPlatform(platform)
                    .withMedia(umImage)
                    .share();
        }
    }
}
