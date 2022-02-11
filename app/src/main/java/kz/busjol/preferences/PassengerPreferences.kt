package kz.busjol.preferences

import android.content.Context
import com.google.gson.Gson

class PassengerPreferences(
    context: Context
) {
    companion object {
        private const val ALL_PASSENGERS_QUANTITY = "ALL_PASSENGERS_QUANTITY"
        private const val ADULT_QUANTITY = "ADULT_QUANTITY"
        private const val CHILD_QUANTITY = "CHILD_QUANTITY"
        private const val DISABLED_QUANTITY = "DISABLED_QUANTITY"
    }

    private var sp = context.getSharedPreferences("PassengerPreferences", Context.MODE_PRIVATE)
    private var editor = sp.edit()

    fun getAllPassengersQuantity(): Int {
        return sp.getInt(ALL_PASSENGERS_QUANTITY, 0)
    }

    fun setAllPassengersQuantity(value: Int) {
        editor.putInt(ALL_PASSENGERS_QUANTITY, value)
    }

    fun getAdultQuantity(): Int {
        return sp.getInt(ADULT_QUANTITY, 0)
    }

    fun setAdultQuantity(value: Int) {
        editor.putInt(ADULT_QUANTITY, value)
    }

    fun getChildQuantity(): Int {
        return sp.getInt(CHILD_QUANTITY, 0)
    }

    fun setChildQuantity(value: Int) {
        editor.putInt(CHILD_QUANTITY, value)
    }

    fun getDisabledQuantity(): Int {
        return sp.getInt(DISABLED_QUANTITY, 0)
    }

    fun setDisabledQuantity(value: Int) {
        editor.putInt(DISABLED_QUANTITY, value)
    }
}