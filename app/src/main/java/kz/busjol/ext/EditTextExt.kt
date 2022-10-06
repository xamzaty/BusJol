package kz.busjol.ext

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

object EditTextExt {

    private val phoneMask = MaskImpl(PredefinedSlots.RUS_PHONE_NUMBER, true)
    private val iinMask = UnderscoreDigitSlotsParser().parseSlots("___ ___ ___ ___")

    fun setPhoneMask(et: EditText) {
        val phoneWatcher = MaskFormatWatcher(phoneMask)
        phoneWatcher.installOn(et)
    }

    fun setIinMask(et: EditText) {
        val iinWatcher = MaskFormatWatcher(MaskImpl.createTerminated(iinMask))
        iinWatcher.installOn(et)
    }

    fun AppCompatEditText.initTextWatcher(body: (text: String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { body(p0!!.toString()) }
            override fun afterTextChanged(p0: Editable?) {}
        })
    }
}