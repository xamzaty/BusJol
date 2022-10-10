package kz.busjol.data.network.model

import kz.busjol.domain.model.City

class JourneyResponse(
    val journey: JourneyResponseItem?,
    val departureTime: String?,
    val arrivalTime: String?,
    val amount: Int?,
    val numberOfPlaces: Int?,
    val numberOfFreePlaces: Int?,
    val stopName: String?,
    val cityFrom: City,
    val cityTo: City
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
