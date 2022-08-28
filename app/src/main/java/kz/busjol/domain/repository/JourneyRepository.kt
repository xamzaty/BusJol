package kz.busjol.domain.repository

import kz.busjol.data.network.model.Response
import kz.busjol.domain.model.Journey
import kz.busjol.domain.model.JourneyPost

interface JourneyRepository {

    suspend fun getJourneyList(body: JourneyPost): Response<List<Journey>>
}