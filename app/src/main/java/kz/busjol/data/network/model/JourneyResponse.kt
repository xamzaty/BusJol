package kz.busjol.data.network.model

class JourneyResponse(
    val journey: JourneyResponseItem?,
    val departureTime: String?,
    val arrivalTime: String?,
    val amount: Int?,
    val numberOfPlaces: Int?,
    val numberOfFreePlaces: Int?
)

class JourneyResponseItem(
    val id: Int?,
    val created: String?,
    val status: Int?,
    val name: String?,
    val departsOn: String?,
    val routeId: Int?,
    val carrierId: Int?,
    val transportId: Int?,
    val code: String?
)