package kz.busjol.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Seats(
    val name: String,
    val transportId: String,
    val row: String,
    val column: String,
    val seatNumber: String,
    val price: Int
): Parcelable
