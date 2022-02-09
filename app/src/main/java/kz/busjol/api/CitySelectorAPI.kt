package kz.busjol.api

import kz.busjol.data.City
import retrofit2.Response
import retrofit2.http.GET

interface CitySelectorAPI {

    @GET("cities")
    suspend fun getCityList(): Response<List<City>>
}