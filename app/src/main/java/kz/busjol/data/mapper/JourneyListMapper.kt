package kz.busjol.data.mapper

import kz.busjol.data.network.model.JourneyResponse
import kz.busjol.domain.model.Journey
import kz.busjol.domain.model.JourneyItem
import kz.busjol.utils.Mapper

class JourneyListMapper : Mapper<List<JourneyResponse>, List<Journey>>() {

    override fun map(from: List<JourneyResponse>): List<Journey> {
        return from.map { journeyResponse ->
            Journey(
                journey = journeyItem(journeyResponse),
                departureTime = journeyResponse.departureTime,
                arrivalTime = journeyResponse.arrivalTime,
                amount = journeyResponse.amount,
                numberOfPlaces = journeyResponse.numberOfPlaces,
                numberOfFreePlaces = journeyResponse.numberOfFreePlaces,
                stopName = journeyResponse.stopName,
                cityFrom = journeyResponse.cityFrom,
                cityTo = journeyResponse.cityTo
            )
        }
    }

    private fun journeyItem(journeyResponse: JourneyResponse) = JourneyItem(
        id = journeyResponse.journey?.id,
        created = journeyResponse.journey?.created,
        status = journeyResponse.journey?.status,
        name = journeyResponse.journey?.name,
        departsOn = journeyResponse.journey?.departsOn,
        routeId = journeyResponse.journey?.routeId,
        carrierId = journeyResponse.journey?.carrierId,
        transportId = journeyResponse.journey?.transportId,
        code = journeyResponse.journey?.code
    )
}