package com.gofishfarm.htkj.utils.editutils;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

import com.hjq.toast.ToastUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author：MrHu
 * @Date ：2019-01-16
 * @Describtion ：
 */
public class charLimite {

    private static final int INPUT_MAX_LENGTH = 8;

    public static void setEditTextInputFilter(EditText editText) {
        editText.setFilters(new InputFilter[]{new Spacefilter(), new specialCharfilter(), new EmojiInputFilter(), new LengthFilter()});
    }

    public static class LengthFilter implements InputFilter {
        @Override
        public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
            int srcLen = charSequence.length();
            int destLen = spanned.length();
            if (srcLen + destLen > INPUT_MAX_LENGTH) {
//                showToast("超过最大长度");
//                setInputCountTv(INPUT_MAX_LENGTH + "/" + INPUT_MAX_LENGTH);
                return charSequence.subSequence(0, INPUT_MAX_LENGTH - destLen);
            }
//            setInputCountTv((srcLen + destLen) + "/" + INPUT_MAX_LENGTH);
            return charSequence;

        }
    }

    /**
     * 禁止EditText输入空格
     */

    public static class Spacefilter implements InputFilter {
        @Override
        public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
            if (charSequence.equals(" ")) return "";
            else return null;

        }
    }


    /**
     * 禁止EditText输入特殊字符
     */
    public static class specialCharfilter implements InputFilter {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“'。，、？.]";
            Pattern pattern = Pattern.compile(speChat);
            Matcher matcher = pattern.matcher(source.toString());
            if (matcher.find()) return "";
            else return null;
        }
    }

    /**
     * 禁止EditText输入表情
     */
    public static class EmojiInputFilter implements InputFilter {
        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                //                    Toast.makeText(MainActivity.this,"不支持输入表情", 0).show();
                ToastUtils.show("不支持输入表情");
                return "";
            }
            return null;
        }
    }

    /**
     * 只能输入汉字,英文，数字
     */
    public static class showTextInputFilter implements InputFilter {

        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_]");

        @Override
        public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
            Matcher matcher = pattern.matcher(charSequence);
            if (!matcher.find()) {
                return null;
            } else {
                ToastUtils.show("只能输入汉字,英文，数字");
                return "";
            }

        }
    }

}
