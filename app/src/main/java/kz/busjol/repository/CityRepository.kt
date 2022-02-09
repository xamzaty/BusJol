package kz.busjol.repository

import kz.busjol.api.CitySelectorAPI
import kz.busjol.data.City

class CityRepository(
    private val api: CitySelectorAPI
): NetworkRepository() {

    suspend fun getCityList(): List<City> {
        return request { api.getCityList() }
    }
}