package kz.busjol.ui.custom_views

import android.content.Context
import android.provider.Settings.Global.getString
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
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

    fun isAllFieldsFilled(): Boolean {
        Log.d("asd", "isAllFieldsFilled: ")
        var iinTextIsFilled = false
        var lastNameIsFilled = false
        var firstNameIsFilled = false
        var dateIsFilled = false

        binding.apply {
            iinTextIsFilled = iinEt.getText().isNotEmpty()
            lastNameIsFilled = lastnameEt.getText().isNotEmpty()
            firstNameIsFilled = firstnameEt.getText().isNotEmpty()
            dateIsFilled = datePicker.getText().isNotEmpty()
        }

        println(iinTextIsFilled && lastNameIsFilled && firstNameIsFilled)
        return iinTextIsFilled && lastNameIsFilled && firstNameIsFilled
    }

    private fun AppCompatEditText.initTextWatcher(body: (text: String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { body(p0!!.toString()) }
            override fun afterTextChanged(p0: Editable?) {}
        })
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
            datePicker.enableDateField()

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

    fun iinText() = binding.iinEt.getText()

    fun lastNameText() = binding.lastnameEt.getText()

    fun firstNameText() = binding.firstnameEt.getText()

    fun birthdateText() = binding.datePicker.getText()
}