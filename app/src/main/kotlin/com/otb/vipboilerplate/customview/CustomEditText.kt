package com.otb.vipboilerplate.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText


/**
 * Created by Mohit Rajput on 6/5/19.
 * Customized and common edit text
 */
class CustomEditText : AppCompatEditText {

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements()
    }

    private fun invalidateTextPaintAndMeasurements() {

    }

    override fun setError(error: CharSequence) {
        super.setError(error)
        requestFocus()
    }

    override fun setError(error: CharSequence, icon: Drawable) {
        super.setError(error, icon)
        requestFocus()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(oldw, oldh, oldw, oldh)
    }
}
