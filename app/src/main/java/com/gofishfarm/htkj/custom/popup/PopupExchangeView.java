package com.gofishfarm.htkj.custom.popup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.gofishfarm.htkj.R;

/**
 * MrLiu253@163.com
 * 选择图片 exchange
 *
 * @time 2018/8/31
 */

public class PopupExchangeView extends BaseWrapContentPickerView implements View.OnClickListener {

    private static final String MINUS = "minus";
    private static final String ADD = "add";
    private static final String CONFIRM = "confirm";
    private Context mContext;
    private TextView mMinusTv, mContentTv, mAddTv, mConfirmTv,mErrorTv,pe_hours_tv;
    private OnAddSubtractClickListener mOnAddSubtractClickListener;

    public PopupExchangeView(Context context) {
        super(context);
        this.mContext = context;
        initView(context);


    }

    private void initView(Context context) {
        initViews();
        initAnim();
        LayoutInflater.from(context).inflate(R.layout.popup_exchange, contentContainer);

        mMinusTv = (TextView) findViewById(R.id.pe_minus_tv);
        mContentTv = (TextView) findViewById(R.id.pe_content_tv);
        mAddTv = (TextView) findViewById(R.id.pe_add_tv);
        mConfirmTv = (TextView) findViewById(R.id.pe_confirm_tv);
        mErrorTv = (TextView) findViewById(R.id.pe_error_tv);
        pe_hours_tv = (TextView) findViewById(R.id.pe_hours_tv);

        mMinusTv.setTag(MINUS);
        mAddTv.setTag(ADD);
        mConfirmTv.setTag(CONFIRM);

        mMinusTv.setOnClickListener(this);
        mAddTv.setOnClickListener(this);
        mConfirmTv.setOnClickListener(this);


    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        switch (tag) {
            case MINUS:
                if (mOnAddSubtractClickListener!=null){
                    mOnAddSubtractClickListener.OnMinusClick(mMinusTv);
                }
                break;
            case ADD:
                if (mOnAddSubtractClickListener!=null){
                    mOnAddSubtractClickListener.OnAddClick(mAddTv);
                }
                break;
            case CONFIRM:
                if (mOnAddSubtractClickListener!=null){
                    mOnAddSubtractClickListener.OnConfirmClick(mConfirmTv);
                }
                break;
            default:
                dismiss();
                break;
        }
    }

    public interface OnAddSubtractClickListener {
        void OnMinusClick(TextView conent);

        void OnAddClick(TextView conent);

        void OnConfirmClick(TextView conent);
    }

    public void setOnAddSubtractClickListener(OnAddSubtractClickListener onAddSubtractClickListener) {
        this.mOnAddSubtractClickListener = onAddSubtractClickListener;
    }
}
