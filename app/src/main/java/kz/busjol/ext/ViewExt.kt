package kz.busjol.ext

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import kz.busjol.R

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