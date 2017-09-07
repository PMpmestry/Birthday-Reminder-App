package com.example.prathamesh1.lavanya_makeup;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

/**
 * Created by Prathamesh 1 on 04-05-2017.
 */

public abstract class TextValidator implements TextWatcher {
    private final TextView txt;

    public TextValidator(TextView textView) {
        txt = textView;
    }

    public abstract void validate(TextView textView, String text);

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        /*txt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                }
            }
        });*/
        String text = txt.getText().toString();
        validate(txt, text);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
