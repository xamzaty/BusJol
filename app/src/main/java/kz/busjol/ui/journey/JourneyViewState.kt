package kz.busjol.ui.journey

import kz.busjol.domain.model.Journey
import kz.busjol.domain.model.JourneyData

sealed class JourneyViewState {
    data class JourneyDataInit(val journeyList: List<Journey>): JourneyViewState()
    data class JourneyDataPass(val journey: JourneyData): JourneyViewState()
}