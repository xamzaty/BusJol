package kz.busjol.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kz.busjol.utils.formatWithCurrency

@Parcelize
data class Journey(
    val status: Int,
    val name: String,
    val departsOn: String,
    val routeId: Int,
    val carrierId: Int,
    val transportId: Int,
    val code: String
): Parcelable {

//    fun displayAmount() = amount.formatWithCurrency()
}
