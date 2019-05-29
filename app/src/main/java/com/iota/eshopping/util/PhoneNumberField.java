package com.iota.eshopping.util;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;

/**
 * @author yeakleang.ay on 6/19/18.
 */

public class PhoneNumberField extends AppCompatEditText implements TextWatcher {

    private static String lastChar = " ";

    private InputFilter inputFilter = (source, start, end, destination, dStart, dEnd) -> {
        for (int i = start; i < end; i++) {
            if (!Character.isDigit(source.charAt(i)) && !Character.isSpaceChar(source.charAt(i))) {
                return "";
            }
        }
        return null;
    };

    private InputFilter inputLengthFilter = new InputFilter.LengthFilter(12);

    /**
     *
     * @param context Context
     */
    public PhoneNumberField(Context context) {
        super(context);
        this.addTextChangedListener(this);
        this.setFilters(new InputFilter[] { inputFilter, inputLengthFilter });
    }

    /**
     *
     * @param context Context
     * @param attrs attrs
     */
    public PhoneNumberField(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.addTextChangedListener(this);
        this.setFilters(new InputFilter[] { inputFilter, inputLengthFilter });
    }

    /**
     *
     * @param context Context
     * @param attrs AttributeSet
     * @param defStyleAttr int
     */
    public PhoneNumberField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.addTextChangedListener(this);
        this.setFilters(new InputFilter[] { inputFilter, inputLengthFilter });
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        int digits = String.valueOf(charSequence).length();
        if (digits > 1)
            lastChar = String.valueOf(charSequence).substring(digits - 1);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        int digits = String.valueOf(charSequence).length();
        if (!lastChar.equals(" ")) {
            if (digits == 3 || digits == 7) {
                this.append(" ");
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

}
