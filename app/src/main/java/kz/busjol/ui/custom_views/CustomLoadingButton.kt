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
}