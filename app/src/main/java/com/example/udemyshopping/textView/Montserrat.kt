package com.example.udemyshopping.textView
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class Montserrat(context: Context, attrs: AttributeSet)
    : AppCompatEditText(context, attrs) {
    init {
        applyFont()
    }
    private fun applyFont() {
        val typeface1: Typeface =
            Typeface.createFromAsset(context.assets, "Montserrat-Regular.ttf")
        typeface = typeface1
//        setTypeface(typeface)
    }
}