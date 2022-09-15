package kz.busjol.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Seats(
    val id: String,
    val created: String,
    val status: Int,
    val name: String,
    val row: Int,
    val column: Int,
    val seatNumber: String,
    val isEmpty: Boolean
): Parcelable
