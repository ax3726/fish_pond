package com.gofishfarm.htkj.presenter.myinfo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.gofishfarm.htkj.base.BaseSubscriber;
import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.base.retrofit.RxHelper;
import com.gofishfarm.htkj.bean.BaseBean;
import com.gofishfarm.htkj.image.GlideUtils;
import com.gofishfarm.htkj.utils.AppUtils;
import com.gofishfarm.htkj.view.myinfo.SetUpView;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * MrLiu253@163.com
 *
 * @time 2019/1/9
 */
public class SetUpPresenter extends RxPresenter<SetUpView> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public SetUpPresenter(RetrofitHelper paramRetrofitHelper) {
        this.mRetrofitHelper = paramRetrofitHelper;
    }

    public void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
        GlideUtils.clearFileCache(AppUtils.getAppContext());
        GlideUtils.clearMemoryCache(AppUtils.getAppContext());
    }

    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public String packageName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }

    public void Logout(String Authorization) {
        addSubscribe(
                (Disposable) this.mRetrofitHelper.Logout(Authorization)
                        .compose(RxHelper.io_main())
                        .subscribeWith(new BaseSubscriber(getView()) {
                            @Override
                            public void onSuccess(BaseBean param) {
                                if (getView() != null) {
                                    getView().onLogOut(param);
                                }
                            }
                        })
        );
    }
}
