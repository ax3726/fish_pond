package com.gofishfarm.htkj.custom.popup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.gofishfarm.htkj.R;
import com.gofishfarm.htkj.utils.RuntimeUtils;
import com.hjq.toast.ToastUtils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.List;

/**
 * MrLiu253@163.com
 * 选择图片
 *
 * @time 2018/8/31
 */

public class PopupPictureView extends BaseWrapContentPickerView implements View.OnClickListener {

    private static final String PICTURES = "pictures";
    private static final String PHOTO = "photo";
    private static final String CANCEL = "cancel";
    private Context mContext;
    private TextView pictures,photo,cancel;
    private OnPicturesClickListener mOnPicturesClickListener;

    public PopupPictureView(Context context) {
        super(context);
        this.mContext = context;
        initView(context);


    }

    private void initView(Context context) {
        initViews();
        initAnim();
        LayoutInflater.from(context).inflate(R.layout.popup_picture, contentContainer);

        pictures = (TextView) findViewById(R.id.dialog_taking_pictures);
        photo = (TextView) findViewById(R.id.dialog_choose_from_the_album);
        cancel = (TextView) findViewById(R.id.dialog_cancel);

        pictures.setTag(PICTURES);
        photo.setTag(PHOTO);
        cancel.setTag(CANCEL);

        pictures.setOnClickListener(this);
        photo.setOnClickListener(this);
        cancel.setOnClickListener(this);


    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        switch (tag) {
            case PICTURES:
                AndPermission.with(mContext).runtime()
                        .permission(Permission.CAMERA)
                        .rationale(new RuntimeUtils())
                        .onGranted(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> data) {//权限允许
                                if (mOnPicturesClickListener != null)
                                    mOnPicturesClickListener.OnPicturesClick();

                            }
                        })
                        .onDenied(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> data) { // 权限拒绝
                                if (AndPermission.hasAlwaysDeniedPermission(mContext, data)) {
                                    ToastUtils.show(mContext.getString(R.string.failed_to_get_permission));
                                }
                            }
                        }).start();
                dismiss();
                break;
            case PHOTO:
                AndPermission.with(mContext).runtime()
                        .permission(Permission.WRITE_EXTERNAL_STORAGE)
                        .rationale(new RuntimeUtils())
                        .onGranted(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> data) {//权限允许
                                if (mOnPicturesClickListener != null)
                                    mOnPicturesClickListener.OnPhotoClick();
                            }
                        })
                        .onDenied(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> data) { // 权限拒绝
                                if (AndPermission.hasAlwaysDeniedPermission(mContext, data)) {
                                    ToastUtils.show(mContext.getString(R.string.failed_to_get_permission));
                                }
                            }
                        }).start();
                dismiss();
                break;
            default:
                dismiss();
                break;
        }
    }

    public interface OnPicturesClickListener {
        void OnPicturesClick();
        void OnPhotoClick();
    }

    public void setOnPicturesClickListener(OnPicturesClickListener onPicturesClickListener){
        this.mOnPicturesClickListener= onPicturesClickListener;
    }
}
