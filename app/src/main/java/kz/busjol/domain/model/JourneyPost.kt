package kz.busjol.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class JourneyPost(
    val cityFrom: Int,
    val cityTo: Int,
    val dateFrom: String,
    val dateTo: String
): Parcelable
