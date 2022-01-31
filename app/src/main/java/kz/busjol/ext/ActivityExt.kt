package kz.busjol.ext

import android.app.Activity
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat

object ActivityExt {

    fun AppCompatActivity.statusBarColor(@ColorRes color: Int, isLightTheme: Boolean = true) {
       window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = ContextCompat.getColor(applicationContext, color)
    }
}