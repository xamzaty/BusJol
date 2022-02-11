package kz.busjol.ext

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.fragment.app.Fragment
import kz.busjol.R

object FragmentExt {

    fun Fragment.vibratePhone(duration: Long) {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(duration)
        }
    }

    fun Fragment.showSimpleAlertDialog(setTitle: Int, setMessage: Int) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(getString(setTitle))
        builder.setMessage(getString(setMessage))

        builder.setPositiveButton(R.string.alert_ok) { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }
}