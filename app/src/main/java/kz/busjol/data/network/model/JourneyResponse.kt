package kz.busjol.data.network.model

class JourneyResponse(
    val status: Int,
    val name: String,
    val departsOn: String,
    val routeId: Int,
    val carrierId: Int,
    val transportId: Int,
    val code: String
)