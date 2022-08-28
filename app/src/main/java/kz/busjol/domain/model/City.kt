package kz.busjol.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    val id: Int,
    val name: String,
): Parcelable