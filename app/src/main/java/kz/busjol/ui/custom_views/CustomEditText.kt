package kz.busjol.ui.custom_views

import android.app.DatePickerDialog
import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import kz.busjol.R
import kz.busjol.databinding.CustomEditTextBinding
import kz.busjol.ext.setOnSafeClickListener
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import java.text.SimpleDateFormat
import java.util.*

class CustomEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var _binding: CustomEditTextBinding? = null
    private val binding get() = _binding!!

    init {
        _binding = CustomEditTextBinding.inflate(LayoutInflater.from(context), this, true)
        setTextWatcher()
        requestLayoutFocus()
    }

    private fun setTextWatcher() {
        getMainField().addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setEraseButtonAction(p0)
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    private fun setEraseButtonAction(p0: CharSequence?) {
        if (p0?.isEmpty() == true || binding.clickableLayout.visibility == View.VISIBLE || !getMainField().isFocusable) {
            binding.eraseButton.visibility = View.GONE
        } else {
            binding.eraseButton.visibility = View.VISIBLE
            binding.eraseButton.setOnClickListener {
                getMainField().setText("")
            }
        }
    }

    private fun requestLayoutFocus() {
        binding.layout.setOnClickListener {
            getMainField().requestFocus()
        }
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

        getClickableView().setOnClickListener {
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
        setText(sdf.format(myCalendar.time))
    }

    fun setTitle(@StringRes titleId: Int) {
        binding.title.setText(titleId)
    }

    fun setText(value: String?) {
        binding.editText.text = Editable.Factory.getInstance().newEditable(value ?: "")
    }

    fun getText() = binding.editText.text.toString()

    fun setImage(@DrawableRes imageId: Int) {
        binding.image.visibility = View.VISIBLE
        binding.image.setImageResource(imageId ?: 0)
    }

    fun setHint(@StringRes hintId: Int) {
        binding.editText.setHint(hintId)
    }

    fun enableErrorText(@StringRes textId: Int) {
        binding.errorText.visibility = View.VISIBLE
        binding.errorText.setText(textId)
    }

    fun enableClickableView(
        @DrawableRes imageId: Int? = null,
        @StringRes mainHintText: Int? = null,
        onClickListener: () -> Unit
    ) {
        binding.clickableLayout.visibility = View.VISIBLE
        binding.image.visibility = View.VISIBLE
        binding.eraseButton.visibility = View.GONE
        binding.image.setImageResource(imageId ?: 0)
        binding.clickableLayout.setOnSafeClickListener {
            onClickListener()
        }
    }

    fun enableCalendarView(
        activity: FragmentActivity?,
        isLimitedDate: Boolean
    ) {
        binding.clickableLayout.visibility = View.VISIBLE
        binding.image.visibility = View.VISIBLE
        setupCalendar(activity, isLimitedDate)

        val sdf = SimpleDateFormat("dd.MM.yyyy")
        setText(sdf.format(Date()))
    }

    fun enablePhoneField() {
        val mask = MaskImpl(PredefinedSlots.RUS_PHONE_NUMBER, true)
        val formatWatcher = MaskFormatWatcher(mask)
        formatWatcher.installOn(getMainField())

        setTitle(R.string.phone_number)
        setHint(R.string.phone_number_hint)
        getMainField().inputType = InputType.TYPE_CLASS_PHONE
    }

    fun enableIinField() {
        val slots = UnderscoreDigitSlotsParser().parseSlots("___ ___ ___ ___")
        val formatWatcher = MaskFormatWatcher(MaskImpl.createTerminated(slots))
        formatWatcher.installOn(getMainField())

        setTitle(R.string.iin)
        setHint(R.string.iin_hint)
    }

    fun enableMailField() {
        setTitle(R.string.email)
        setHint(R.string.enter_email)
        getMainField().inputType = InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS
    }

    fun enablePasswordField() {
        setTitle(R.string.password)
        setHint(R.string.enter_password)
        getMainField().inputType = InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD
        getMainField().transformationMethod = PasswordTransformationMethod.getInstance()
    }

    fun getMainField() = binding.editText

    fun getClickableView(): View = binding.clickableLayout
}