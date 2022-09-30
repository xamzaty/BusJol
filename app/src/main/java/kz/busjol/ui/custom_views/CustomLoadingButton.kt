package kz.busjol.ui.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import kz.busjol.databinding.CustomLoadingButtonBinding

class CustomLoadingButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var _binding: CustomLoadingButtonBinding? = null
    private val binding get() = _binding!!

    init {
        _binding = CustomLoadingButtonBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setTitle(@StringRes textId: Int) {
        binding.title.setText(textId)
    }

    fun setTitle(text: String) {
        binding.title.text = text
    }

    fun onSetClickListener(listener: () -> Unit) {
        binding.button.setOnClickListener {
            listener()
        }
    }

    fun onLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                title.visibility = View.GONE
                loader.visibility = View.VISIBLE
            } else {
                title.visibility = View.VISIBLE
                loader.visibility = View.GONE
            }
        }
    }

    fun buttonAvailability(isAvailable: Boolean, listener: () -> Unit): Boolean {
        return if (!isAvailable) {
            binding.button.alpha = 0.6f
            false
        } else {
            binding.button.alpha = 1f
            onSetClickListener { listener() }
            true
        }
    }
}