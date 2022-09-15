package kz.busjol.data.network

import kz.busjol.data.network.model.JourneyResponse
import kz.busjol.domain.model.JourneyPost
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface JourneyDataSource {

    @POST("search")
    suspend fun getJourneyList(
        @Body body: JourneyPost
    ): Response<List<JourneyResponse>>
}