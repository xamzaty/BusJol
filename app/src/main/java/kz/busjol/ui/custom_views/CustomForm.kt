package kz.busjol.ui.custom_views

import android.content.Context
import android.provider.Settings.Global.getString
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.custom_edit_text.view.*
import kz.busjol.R
import kz.busjol.databinding.CustomFormBinding
import kz.busjol.ui.passenger_quantity.Passenger

class CustomForm @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var _binding: CustomFormBinding? = null
    private val binding get() = _binding!!

    init {
        _binding = CustomFormBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setupFields(
        activity: FragmentActivity?,
        isChildType: Boolean = false,
        isDisabled: Boolean = false,
        passengerNumber: Int,
    ) {
        binding.apply {
            passengerNumberText.text = activity?.getString(R.string.passenger, passengerNumber)

            iinEt.enableIinField()

            lastnameEt.title.setText(R.string.last_name)
            lastnameEt.getMainField().setHint(R.string.last_name_hint)

            firstnameEt.title.setText(R.string.first_name)
            firstnameEt.getMainField().setHint(R.string.first_name_hint)

            datePicker.title.setText(R.string.birthday_date)
            datePicker.getMainField().setHint(R.string.birthday_date)
            datePicker.enableCalendarView(activity, isLimitedDate = false)

            if (isChildType) {
                datePicker.visibility = View.VISIBLE
                passengerTypeText.setText(R.string.child)
            } else if(isDisabled) {
                passengerTypeText.setText(R.string.passengers_tariffs_disable_person)
            } else {
                passengerTypeText.setText(R.string.adult)
            }
        }
    }

    fun getIinText() = binding.iinEt.getText()

    fun getLastNameText() = binding.lastnameEt.getText()

    fun getFirstNameText() = binding.firstnameEt.getText()

    fun getBirthdateText() = binding.datePicker.getText()
}