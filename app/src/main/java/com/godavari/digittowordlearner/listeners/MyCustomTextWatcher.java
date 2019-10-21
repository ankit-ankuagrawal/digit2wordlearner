package com.godavari.digittowordlearner.listeners;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import com.godavari.digittowordlearner.interfaces.DigitUpdateInterface;
import com.godavari.lib.digittowords.DigitToWordUtility;

public class MyCustomTextWatcher implements TextWatcher {

    private DigitUpdateInterface digitUpdateInterface;

    public MyCustomTextWatcher(DigitUpdateInterface digitUpdateInterface) {
        this.digitUpdateInterface = digitUpdateInterface;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        int tempDigit = 0;
        try {
            tempDigit = Integer.valueOf(charSequence.toString());
        } catch (Exception e) {
        }

        if (TextUtils.isEmpty(charSequence.toString())) {
            digitUpdateInterface.digitUpdated(DigitToWordUtility.NOT_SUPPORTING_NUMBERS);
        } else {
            digitUpdateInterface.digitUpdated(tempDigit);
        }


        digitUpdateInterface.noOfDigit(i2);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
