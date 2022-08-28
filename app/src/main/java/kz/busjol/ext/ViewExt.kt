package kz.busjol.ext

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.util.AttributeSet
import android.util.Log.e
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import kz.busjol.R
import java.text.ParseException
import java.util.logging.Logger

fun AttributeSet?.obtainStyledAttributes(
    context: Context,
    styleable: IntArray,
    block: (TypedArray) -> Unit
) {
    if (this == null) return

    val typedArray = context.obtainStyledAttributes(this, styleable, 0, 0)
    try {
        block(typedArray)
    } finally {
        typedArray.recycle()
    }
}

fun Toolbar.setOnMenuItemSafeClickListener(listener: Toolbar.OnMenuItemClickListener?) {
    var lastClickedTime = 0L
    setOnMenuItemClickListener {
        val clickedTime = System.currentTimeMillis()
        if (clickedTime > lastClickedTime + 600) {
            lastClickedTime = clickedTime
            listener?.onMenuItemClick(it)
        }
        true
    }
}

fun View.setOnSafeClickListener(listener: View.OnClickListener?) {
    var lastClickedTime = 0L
    setOnClickListener {
        val clickedTime = System.currentTimeMillis()
        if (clickedTime > lastClickedTime + 600) {
            lastClickedTime = clickedTime
            listener?.onClick(it)
        }
    }
}

fun View.animateShake() {
    startAnimation(AnimationUtils.loadAnimation(context, R.anim.wrong_ping_shake))
}

fun View.show(show: Boolean = true) {
    if (show) visibility = View.VISIBLE
    else hide()
}

fun View.hide() {
    visibility = View.GONE
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}


fun formatDate(dateToFormat: String, inputFormat: String?, outputFormat: String?): String? {
    try {

        //Update Date
        return SimpleDateFormat(outputFormat)
            .format(
                SimpleDateFormat(inputFormat)
                    .parse(dateToFormat)
            )
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return null
}