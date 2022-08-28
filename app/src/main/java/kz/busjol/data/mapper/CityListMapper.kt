package kz.busjol.data.mapper

import kz.busjol.data.network.model.CityResponse
import kz.busjol.domain.model.City
import kz.busjol.utils.Mapper

class CityListMapper : Mapper<List<CityResponse>, List<City>>() {

    override fun map(from: List<CityResponse>): List<City> {
        return from.map {
            City(
                id = it.id,
                name = it.name
            )
        }
    }
}