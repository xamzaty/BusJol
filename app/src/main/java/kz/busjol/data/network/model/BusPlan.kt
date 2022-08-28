package kz.busjol.data.network.model

import java.io.Serializable

class BusPlan(
    val place: Int,
    val isSeatBusy: Boolean
): Serializable