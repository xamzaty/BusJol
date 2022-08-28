package kz.busjol.ui.search_journey

import kz.busjol.domain.model.Journey
import kz.busjol.domain.model.JourneyData

sealed class SearchJourneyViewState {
    data class JourneyDataInit(val journeyData: JourneyData): SearchJourneyViewState()
    data class NavigateToJourneyScreen(val journeyData: JourneyData, val journey: List<Journey>): SearchJourneyViewState()
}