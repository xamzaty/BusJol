package kz.busjol.ext

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kz.busjol.BuildConfig
import kz.busjol.R
import java.lang.Exception

object FragmentExt {

    fun Fragment.showAlertDialog(
        @StringRes title: Int = 0, @StringRes message: Int = 0,
        clickListener: DialogInterface.OnClickListener? = null,
        modal: Boolean = false
    ) {
        showAlertDialog(
            if (title == 0) null else getString(title),
            if (message == 0) null else getString(message),
            clickListener,
            modal
        )
    }

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

    fun Fragment.hideKeyboard() {
        try {
            val inputMethodManager: InputMethodManager? =
                context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
            view?.windowToken?.let {
                inputMethodManager?.hideSoftInputFromWindow(it, 0)
            }
        } catch (e: Exception) {
        }
    }

    fun <T> Fragment.observe(liveData: LiveData<T>?, onUpdate: (T) -> Unit) {
        liveData?.observe(viewLifecycleOwner, Observer(onUpdate))
    }

    fun Fragment.showAlertDialog(
        title: String? = null, message: String? = null,
        clickListener: DialogInterface.OnClickListener? = null,
        modal: Boolean = false
    ) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setCancelable(!modal)
            .setPositiveButton(getString(R.string.ok), clickListener)
            .show()
    }

    fun Fragment.observeNavigationResult(
        lifecycleOwner: LifecycleOwner = viewLifecycleOwner,
        key: String = "NavigationResult",
        callback: (Any) -> Unit
    ) = findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Any>(key)
        ?.takeIf { !it.hasObservers() }?.observe(lifecycleOwner, Observer { callback(it) })

    fun Fragment.postNavigationResult(result: Any, key: String = "NavigationResult") {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
    }

    fun Fragment.navigateSafe(directions: NavDirections) {
        /*
            Example:
            Fatal Exception: java.lang.IllegalArgumentException
            Navigation action/destination kz.jysan.business:id/to_statementDetails cannot be found from
            the current destination a(kz.jysan.business:id/home) class=kz.jysan.business.ui.home.HomeFragment
        */
        try {
            findNavController().navigate(directions)
        } catch (ignoredException: IllegalArgumentException) {
            if (BuildConfig.DEBUG) {
                //Чтобы во время разработки не упустить проблемы
                throw ignoredException
            } else {
                ignoredException.printStackTrace()
            }
        }
    }
}