package cn.faury.android.library.view.custom.common;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;


import cn.faury.android.library.common.util.StringUtils;
import cn.faury.android.library.view.custom.R;


/**
 * 输入框监控类
 */

public class CommonTextWatcher implements TextWatcher {
    private Context mContext;
    private CharSequence temp;
    private int tempEnd;
    private int maxLength=Integer.MAX_VALUE;

    private EditText mEditText;

    public CommonTextWatcher(Context context , EditText editText) {
        this.mEditText = editText;
        this.mContext = context;
    }

    public CommonTextWatcher(Context context , EditText editText,int maxLength) {
        this.mEditText = editText;
        this.mContext = context;
        this.maxLength = maxLength;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before,
                              int count) {
        temp = s;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        tempEnd = mEditText.getSelectionEnd();
    }

    @Override
    public void afterTextChanged(Editable s) {
        int selectionStart = mEditText.getSelectionStart();
        int selectionEnd = mEditText.getSelectionEnd();
        if (StringUtils.containsEmoji(temp.toString())) {
            Toast.makeText(mContext, R.string.f_cvl_common_text_watcher_unsupported_character,Toast.LENGTH_SHORT).show();
            s.delete(tempEnd, selectionEnd);
            mEditText.setText(s);
            mEditText.setSelection(selectionStart);// 设置光标在最后
        }
        if (mEditText.getText().length()>maxLength) {
            Toast.makeText(mContext, mContext.getResources().getString(R.string.f_cvl_common_text_watcher_max_length,maxLength),Toast.LENGTH_SHORT).show();
            s.delete(maxLength, s.length());
            mEditText.setText(s);
            mEditText.setSelection(selectionStart);// 设置光标在最后
        }
    }
}
