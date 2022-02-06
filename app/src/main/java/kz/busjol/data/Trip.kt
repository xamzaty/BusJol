package kz.busjol.data

import kz.busjol.utils.DateTimeUtils
import kz.busjol.utils.Money
import kz.busjol.utils.toString
import java.io.Serializable
import java.util.*

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