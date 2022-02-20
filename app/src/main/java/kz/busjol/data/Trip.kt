package kz.busjol.data

import java.io.Serializable

data class Trip(
    val time: String,
    val date: String,
    val tripCount: String,
    val type: String,
    val cities: String,
    val freeSeats: String,
    val allSeats: String,
    val carrier: String,
    val amount: String
) : Serializable {
}