package kz.busjol.domain.repository

import kz.busjol.data.network.model.Response
import kz.busjol.domain.model.City

interface CityRepository {

    suspend fun getCityList(): Response<List<City>>
}