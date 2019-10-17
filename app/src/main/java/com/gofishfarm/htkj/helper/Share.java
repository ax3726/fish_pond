package com.gofishfarm.htkj.helper;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * MrLiu253@163.com
 * @time 2018/1/3
 */

public interface Share {
    void share(SHARE_MEDIA platform, UMImage umImage);
}
