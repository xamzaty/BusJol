package kz.busjol.ext

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kz.busjol.BuildConfig
import kz.busjol.R
import kz.busjol.ext.FragmentExt.navigate
import kz.busjol.ext.FragmentExt.showAlertDialog
import java.lang.Exception

object FragmentExt {

    fun Fragment.showSimpleAlertDialog(setTitle: Int, setMessage: Int) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(getString(setTitle))
        builder.setMessage(getString(setMessage))

        builder.setPositiveButton(R.string.alert_ok) { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    fun Fragment.showSnackBar(message: Int) {
        val snackBar = view?.let {
            Snackbar.make(
                it, getString(message),
                Snackbar.LENGTH_SHORT
            )
        }
        snackBar?.view?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue_button))
        snackBar?.show()
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
            .setNegativeButton("Cancel", null)
            .show()
    }

    fun Fragment.showIrrevocableAlertDialog(
        title: String? = null, message: String? = null,
        modal: Boolean = false,
        clickListener: DialogInterface.OnClickListener? = null
    ) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.ok), clickListener)
            .show()
    }

    fun Fragment.showOnBackKeyAlert(clickListener: () -> Unit) {
        showAlertDialog(title = "Exit", message = "Are you sure you want to exit?",
            modal = false, clickListener = { _, _ ->
                clickListener()
            })
    }

    fun Fragment.setVisibility(setVisible: Boolean, views: Array<View>) {
        if (setVisible) {
            views.forEach {
                it.visibility = View.VISIBLE
            }
        } else {
            views.forEach {
                it.visibility = View.GONE
            }
        }
    }

    fun Fragment.navigate(resId: Int) {
        findNavController().navigate(resId)
    }

    fun Fragment.onBackKeyListener(clickListener: () -> Unit) {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    clickListener()
                }
            })
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
}