package kz.busjol.data.network

import kz.busjol.data.network.model.CityResponse
import retrofit2.Response
import retrofit2.http.GET

interface CityListDataSource {

    @GET("cities")
    suspend fun getCityList(): Response<List<CityResponse>>
}