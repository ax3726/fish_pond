package com.gofishfarm.htkj.presenter.webPage;

import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.view.webPage.WebActivityView;

import javax.inject.Inject;

/**
 * @author：MrHu
 * @Date ：2019-01-08
 * @Describtion ：
 */
public class WebActivityPresenter extends RxPresenter<WebActivityView> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public WebActivityPresenter(RetrofitHelper paramRetrofitHelper) {
        this.mRetrofitHelper = paramRetrofitHelper;
    }

}
