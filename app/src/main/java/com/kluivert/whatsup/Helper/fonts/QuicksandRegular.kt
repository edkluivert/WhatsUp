package com.kluivert.whatsup.Helper.fonts

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView

/**
 * Created by kluivert 11/9/2019.
 */
@SuppressLint("AppCompatCustomView")
class QuicksandRegular : TextView {

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()

    }

    constructor(context: Context) : super(context) {
        init()

    }

    private fun init() {
        if (!isInEditMode) {
            val tf = Typeface.createFromAsset(context.assets, "Quicksand-Regular.ttf")
            typeface = tf
        }
    }




}