package kz.busjol.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PassengerData(
    var adultQuantity: Int,
    var childQuantity: Int,
    var disabledQuantity: Int,
): Parcelable {

    fun allPassengersQuantity() = adultQuantity + childQuantity + disabledQuantity
}
