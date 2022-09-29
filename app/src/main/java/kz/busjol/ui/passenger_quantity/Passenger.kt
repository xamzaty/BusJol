package kz.busjol.ui.passenger_quantity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Passenger(
    val type: PassengerType? = PassengerType.ADULT,
    val isMale: Boolean? = true,
    val iin: String? = "",
    val lastName: String? = "",
    val firstName: String? = "",
    val birthDate: String? = ""
): Parcelable {

    fun isChild() = type == PassengerType.CHILD

    fun isDisabled() = type == PassengerType.DISABLED

    enum class PassengerType {
        ADULT, CHILD, DISABLED
    }
}
