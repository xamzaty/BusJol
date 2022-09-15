package kz.busjol.ext

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import kz.busjol.R
import java.text.ParseException

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


@SuppressLint("SimpleDateFormat")
fun formatDate(dateToFormat: String, inputFormat: String?, outputFormat: String?): String? {
    try {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SimpleDateFormat(outputFormat)
                .format(
                    SimpleDateFormat(inputFormat)
                        .parse(dateToFormat)
                )
        } else {
            TODO("VERSION.SDK_INT < N")
        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return null
}

fun AppCompatTextView.setQuantityButtonAction(
    isPlusButton: Boolean,
    quantityValue: Int,
    textView: AppCompatTextView
) {
    var quantity = quantityValue

    if (isPlusButton) {
        this.setOnClickListener {
            if (quantity < 50) quantity++
            textView.text = quantity.toString()
        }
    } else {
        this.setOnClickListener {
            if (quantity > 0) quantity--
            textView.text = quantity.toString()
        }
    }
}

fun String.reformatDateFromBackend(): String? {
    val inputFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    val outputFormat = "dd.MM.yyyy"

    return formatDate(this, inputFormat, outputFormat)
}

fun String.reformatDateToBackend(isDotFormat: Boolean): String? {
    val inputFormat = if (isDotFormat) "dd.MM.yyyy" else "yyyy-MM-dd"
    val outputFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    return formatDate(this, inputFormat, outputFormat)
}