package com.gofishfarm.htkj.base;

/**
 * MrLiu253@163.com
 * @time 2018/7/30
 */

public interface BaseView {

    void complete(String msg);

    void showError(String paramString, int status_code);
}
