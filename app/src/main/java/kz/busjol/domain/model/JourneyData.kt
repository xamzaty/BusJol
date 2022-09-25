package kz.busjol.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kz.busjol.ui.passenger_quantity.Passenger

@Parcelize
data class JourneyData(
    val passengerData: PassengerData?,
    val passengerListData: List<Passenger>?,
    val fromCity: City?,
    val toCity: City?,
    val journeyList: List<Journey>?
): Parcelable