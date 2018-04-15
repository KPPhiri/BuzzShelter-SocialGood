package com.example.philipphiri.homelessapp;

import android.content.Context;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;


/** Spinner extension that callas onItemSelected even when the selection is the same as its previous value */
public class NDSpinner extends AppCompatSpinner {

    /**
     * NDSpinnter constructor
     * @param context context
     */
    public NDSpinner(Context context)
    { super(context); }

    /**
     * ND spinner constructor
     * @param context context
     * @param attrs attrbute set
     */
    public NDSpinner(Context context, AttributeSet attrs)
    { super(context, attrs); }

    /**
     * ND Spinner constructor
     * @param context context
     * @param attrs attribute set
     * @param defStyle defStyle
     */
    public NDSpinner(Context context, AttributeSet attrs, int defStyle)
    { super(context, attrs, defStyle); }

    @Override
    public void setSelection(int position, boolean animate) {
        boolean sameSelected = position == getSelectedItemPosition();
        super.setSelection(position, animate);
        if (sameSelected) {
            // Spinner does not call the OnItemSelectedListener if the same item is selected, so do it manually now
            getOnItemSelectedListener().onItemSelected(this, getSelectedView(), position, getSelectedItemId());
        }
    }

    @Override
    public void setSelection(int position) {
        boolean sameSelected = position == getSelectedItemPosition();
        super.setSelection(position);
        if (sameSelected) {
            // Spinner does not call the OnItemSelectedListener if the same item is selected, so do it manually now
            getOnItemSelectedListener().onItemSelected(this, getSelectedView(), position, getSelectedItemId());
        }
    }

}