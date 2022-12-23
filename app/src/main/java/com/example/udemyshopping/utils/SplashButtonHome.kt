package com.example.udemyshopping.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class SplashButtonHome(context: Context, attributes: AttributeSet)
    : AppCompatButton(context, attributes) {
    init {
        applyFont()
    }
    private fun applyFont() {
        val boldTypeface : Typeface =
            Typeface.createFromAsset(context.assets,"Montserrat-Bold.ttf")
        typeface = boldTypeface
    }

}