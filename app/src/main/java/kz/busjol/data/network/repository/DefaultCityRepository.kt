package kz.busjol.data.network.repository

import kz.busjol.data.network.CityListDataSource
import kz.busjol.data.mapper.CityListMapper
import kz.busjol.data.network.model.Response
import kz.busjol.domain.model.City
import kz.busjol.domain.repository.CityRepository
import kz.busjol.utils.map

class DefaultCityRepository(
    private val dataSource: CityListDataSource,
    private val mapper: CityListMapper
): CityRepository {

    override suspend fun getCityList(): Response<List<City>> =
        try {
            val response = dataSource.getCityList()
            response.map(
                mapSuccess = {
                    Response.Success(mapper.map(it))
                }
            )
        } catch (e: Exception) {
            Response.Failure(e)
        }
}