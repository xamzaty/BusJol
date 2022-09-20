package kz.busjol.utils

import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kz.busjol.R

object AnimationUtils {
    fun createRotateAnimation(context: Context): Animation =
        AnimationUtils.loadAnimation(context, R.anim.rotate_center).apply {
            fillAfter = true
        }
}
