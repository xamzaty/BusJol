package kz.busjol.ui.passenger_quantity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Passenger(
    val type: PassengerType? = null,
    val iin: String? = "",
    val lastName: String? = "",
    val firstName: String? = "",
    val birthDate: String? = ""
): Parcelable {
    enum class PassengerType {
        ADULT, CHILD, DISABLED
    }
}
