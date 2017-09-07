package com.example.prathamesh1.lavanya_makeup;

import android.text.method.PasswordTransformationMethod;
import android.view.View;

/**
 * Created by Prathamesh 1 on 22-05-2017.
 */

//Following code is to create custom keyboard
class NumberKeyBoardTransformationMethod extends PasswordTransformationMethod {
    @Override
    public CharSequence getTransformation(CharSequence source, View view) {
        return source;
    }
}
