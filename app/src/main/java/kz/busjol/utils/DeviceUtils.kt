package kz.busjol.utils

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import kz.busjol.BuildConfig

class DeviceUtils {
    companion object {
        fun vibrate(context: Context) {
            val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            // Vibrate for 300 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                //deprecated in API 26
                v.vibrate(300)
            }
        }

        fun vibrateTick(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                v.vibrate(VibrationEffect.createPredefined( VibrationEffect.EFFECT_TICK))
            }
        }

        fun link(context: Context?, url: String) {
            context?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }

        fun call(context: Context?, phone: String) {
            try {
                context?.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone")))
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(context, "Не удалось найти функцию вызова", Toast.LENGTH_SHORT).show()
            }
        }

        fun share(context: Context?, message: String, subject: String?) {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, message)
                subject?.let { putExtra(Intent.EXTRA_SUBJECT, it) }
            }
            context?.startActivity(Intent.createChooser(intent, message))
        }

        @SuppressLint("DefaultLocale")
        fun getDeviceDescription(): String {
            val manufacturer = Build.MANUFACTURER.orEmpty()
            val model = Build.MODEL.orEmpty()
            val description = if (model.startsWith(manufacturer)) model else "$manufacturer $model"

            return "${description.capitalize()} | Android ${Build.VERSION.RELEASE} | v${BuildConfig.VERSION_NAME}"
        }
    }
}