package kz.busjol.ui.custom_views

import android.app.DatePickerDialog
import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import kz.busjol.R
import kz.busjol.databinding.CustomClickableViewBinding
import java.text.SimpleDateFormat
import java.util.*

class CustomClickableView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var _binding: CustomClickableViewBinding? = null
    private val binding get() = _binding!!

    init {
        _binding = CustomClickableViewBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private fun setupCalendar(
        activity: FragmentActivity?,
        isLimitedDate: Boolean
    ) {
        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, day)
            updateCalendarDate(myCalendar)
        }

        if (getText().isEmpty()) {
            getMainField().setText(R.string.date)
        } else {
            setHint(R.string.date)
        }

        binding.layout.setOnClickListener {
            val datePickerDialog = activity?.let { it1 ->
                DatePickerDialog(
                    it1, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH))
            }
            if (isLimitedDate) datePickerDialog?.datePicker?.minDate = System.currentTimeMillis() - 1000
            datePickerDialog?.show()
        }
    }

    private fun updateCalendarDate(myCalendar: Calendar) {
        val format = "dd.MM.yyyy"
        val sdf = SimpleDateFormat(format, Locale.UK)
        binding.text.setText(sdf.format(myCalendar.time))
    }

    fun setTitle(@StringRes titleId: Int) {
        binding.title.setText(titleId)
    }

    fun getText() = binding.text.text.toString()

    fun setHint(@StringRes hintId: Int) {
        binding.text.setHint(hintId)
    }

    fun enableErrorText(@StringRes textId: Int) {
        binding.errorText.visibility = View.VISIBLE
        binding.errorText.setText(textId)
    }

    fun enableCalendarView(
        activity: FragmentActivity?,
        isLimitedDate: Boolean
    ) {
        setupCalendar(activity, isLimitedDate)

        val sdf = SimpleDateFormat("dd.MM.yyyy")
        binding.text.setText(sdf.format(Date()))
    }

    fun onClickListener(onClick: () -> Unit) {
        binding.layout.setOnClickListener {
            onClick()
        }
    }

    fun setImage(@DrawableRes imageId: Int) {
        binding.image.setImageResource(imageId)
    }

    fun getMainField() = binding.text

    fun getClickableView() = binding.clickableView
}