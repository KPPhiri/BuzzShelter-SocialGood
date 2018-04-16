package com.example.philipphiri.homelessapp;

import android.content.Context;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;


/** Spinner extension that callas onItemSelected even when the selection is the same as its
 * previous value */
public class NDSpinner extends AppCompatSpinner {

    /**
     * @param context context
     */
    public NDSpinner(Context context)
    { super(context); }

    /**
     * @param context context
     * @param attrs attributes
     */
    public NDSpinner(Context context, AttributeSet attrs)
    { super(context, attrs); }

    /**
     * @param context context
     * @param attrs attributes
     * @param defStyle default style
     */
    public NDSpinner(Context context, AttributeSet attrs, int defStyle)
    { super(context, attrs, defStyle); }

    @Override
    public void setSelection(int position, boolean animate) {
        boolean sameSelected = position == getSelectedItemPosition();
        super.setSelection(position, animate);
        if (sameSelected) {
            // Spinner does not call the OnItemSelectedListener if the same item is selected,
            // so do it manually now
            if (getOnItemSelectedListener() != null) {
                getOnItemSelectedListener().onItemSelected(this, getSelectedView(),
                        position, getSelectedItemId());
            }
             }
    }

    @Override
    public void setSelection(int position) {
        boolean sameSelected = position == getSelectedItemPosition();
        super.setSelection(position);
        if (sameSelected) {
            // Spinner does not call the OnItemSelectedListener if the same item is selected,
            // so do it manually now
            if (getOnItemSelectedListener() != null) {
                getOnItemSelectedListener().onItemSelected(this, getSelectedView(),
                        position, getSelectedItemId());
            }
               }
    }

}