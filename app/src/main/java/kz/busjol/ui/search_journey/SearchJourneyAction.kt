package kz.busjol.ui.search_journey

import kz.busjol.domain.model.City
import kz.busjol.domain.model.JourneyData
import kz.busjol.domain.model.JourneyPost

sealed class SearchJourneyAction {
    data class InitJourneyData(val journeyData: JourneyData): SearchJourneyAction()
    data class CityValueInit(val journeyData: JourneyData, val city: City, val isFromInit: Boolean): SearchJourneyAction()
    data class SimilarCities(val journeyData: JourneyData): SearchJourneyAction()
    data class PassPassengersData(val passengersData: JourneyData): SearchJourneyAction()
    data class OnSwapCities(val journeyData: JourneyData): SearchJourneyAction()
    data class OnSearchJourneyButtonClicked(val journeyPost: JourneyPost, val journeyData: JourneyData): SearchJourneyAction()
    data class OnJourneyBackDataInit(val journeyData: JourneyData): SearchJourneyAction()
}
