package kz.busjol.data.network.repository

import kz.busjol.data.mapper.JourneyListMapper
import kz.busjol.data.network.JourneyDataSource
import kz.busjol.data.network.model.Response
import kz.busjol.domain.model.Journey
import kz.busjol.domain.model.JourneyPost
import kz.busjol.domain.repository.JourneyRepository
import kz.busjol.utils.map

class DefaultJourneyRepository(
    private val dataSource: JourneyDataSource,
    private val mapper: JourneyListMapper
): JourneyRepository {

    override suspend fun getJourneyList(body: JourneyPost): Response<List<Journey>> =
        try {
            val response = dataSource.getJourneyList(body)
            response.map(
                mapSuccess = {
                    Response.Success(mapper.map(it))
                }
            )
        } catch (e: Exception) {
            Response.Failure(e)
        }
}