package com.godavari.digittowordlearner.listeners;

import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.godavari.digittowordlearner.R;
import com.godavari.digittowordlearner.interfaces.DigitUpdateInterface;
import com.google.android.material.textfield.TextInputEditText;

public class MyCustomTouchListener implements View.OnTouchListener {

    private DigitUpdateInterface digitUpdateInterface;

    public MyCustomTouchListener(DigitUpdateInterface digitUpdateInterface) {
        this.digitUpdateInterface = digitUpdateInterface;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getRawX() >= (v.getRight() - ((EditText) v).getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                // your action here
                digitUpdateInterface.rightDrawableClicked(v.getId());
                return false;
            }

        }
        return false;
    }
}
