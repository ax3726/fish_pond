package com.gofishfarm.htkj.utils.editutils;

import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.widget.EditText;

public class LimitTextWatcher implements TextWatcher {
    EditText e;
    float max = 1000f;
    float min = 0f;

    public LimitTextWatcher(EditText e) {
        this.e = e;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (start >= 0) {//从一输入就开始判断，
            if (min != -1 && max != -1) {
                try {
                    float num = Float.valueOf(s.toString());
                    if (num > max) {//判断当前edittext中的数字(可能一开始Edittext中有数字)是否大于max
                        s = String.valueOf((int) max);//如果大于max，则内容为max
                        e.setText(s);
                        CharSequence c = e.getText();
                        if (c instanceof Spannable) {//光标定位到最后
                            Spannable spanText = (Spannable) c;
                            Selection.setSelection(spanText, c.length());
                        }
                    } else if (num < min) {
                        s = String.valueOf((int) min);
                        e.setText(s);
                        CharSequence c = e.getText();
                        if (c instanceof Spannable) {//光标定位到最后
                            Spannable spanText = (Spannable) c;
                            Selection.setSelection(spanText, c.length());
                        }
                    }
                } catch (NumberFormatException e) {
                }
                return;
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
