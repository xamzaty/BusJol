package kz.busjol.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Passenger(
    val isChild: Boolean
): Parcelable