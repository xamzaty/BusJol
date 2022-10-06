package kz.busjol.ui.booking

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Payment(
    val type: PaymentType,
    val url: String
) : Parcelable {

    enum class PaymentType {
        BANK_CARDS, KASPI
    }
}
