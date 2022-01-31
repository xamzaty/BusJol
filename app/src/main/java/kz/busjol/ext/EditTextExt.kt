package kz.busjol.ext

import android.widget.EditText
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
}