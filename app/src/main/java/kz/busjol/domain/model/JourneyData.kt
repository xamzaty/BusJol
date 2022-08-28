package kz.busjol.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class JourneyData(
    val passengerData: PassengerData?,
    val fromCity: City?,
    val toCity: City?
): Parcelable