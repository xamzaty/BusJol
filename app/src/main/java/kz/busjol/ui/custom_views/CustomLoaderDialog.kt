package kz.busjol.ui.custom_views

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import kz.busjol.databinding.DialogLoaderBinding
import kz.busjol.utils.AnimationUtils

class CustomLoaderDialog(
    context: Context,
    cancelable: Boolean = false,
    onCancelCallback: () -> Unit = {}
) {

    private var dialog: Dialog = Dialog(context)

    private val binding by lazy { DialogLoaderBinding.inflate(LayoutInflater.from(context)) }

    init {
        dialog.setContentView(binding.root)
        dialog.setCancelable(cancelable)
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog.window?.setLayout(width, height)
        dialog.setOnCancelListener {
            onCancelCallback.invoke()
        }
    }

    fun show() {
        dialog.show()
    }

    fun hide() {
        dialog.dismiss()
    }

    fun showOrHide(show: Boolean) {
        when (show) {
            true -> show()
            false -> hide()
        }
    }
}