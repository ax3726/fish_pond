package com.gofishfarm.htkj.presenter.main;

import android.widget.TextView;

import com.gofishfarm.htkj.base.RxPresenter;
import com.gofishfarm.htkj.base.retrofit.RetrofitHelper;
import com.gofishfarm.htkj.view.main.MainView;

import javax.inject.Inject;

/**
 * MrLiu253@163.com
 *
 * @time 2018/12/31
 */
public class MainPresenter extends RxPresenter<MainView> {
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public MainPresenter(RetrofitHelper paramRetrofitHelper) {
        this.mRetrofitHelper = paramRetrofitHelper;
    }

    public void setBrightView(int pos, TextView[] mainBottomFound) {
        if (mainBottomFound[pos].isSelected()) {
            return;
        }
        for (int i = 0; i < mainBottomFound.length; i++) {
            if (i == pos) {
                mainBottomFound[i].setSelected(true);
            } else {
                mainBottomFound[i].setSelected(false);
            }
        }
    }
}
