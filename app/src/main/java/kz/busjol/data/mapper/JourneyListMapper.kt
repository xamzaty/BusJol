package kz.busjol.data.mapper

import kz.busjol.data.network.model.JourneyResponse
import kz.busjol.domain.model.Journey
import kz.busjol.utils.Mapper

class JourneyListMapper : Mapper<List<JourneyResponse>, List<Journey>>() {

    override fun map(from: List<JourneyResponse>): List<Journey> {
        return from.map {
            Journey(
                status = it.status,
                name = it.name,
                departsOn = it.departsOn,
                routeId = it.routeId,
                carrierId = it.carrierId,
                transportId = it.transportId,
                code = it.code
            )
        }
    }

}