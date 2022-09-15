package kz.busjol.ui.search_journey

import kz.busjol.domain.model.City
import kz.busjol.domain.model.JourneyData
import kz.busjol.domain.model.JourneyPost
import kz.busjol.domain.model.PassengerData

sealed class SearchJourneyAction {
    data class FromCityValueInit(val city: City): SearchJourneyAction()
    data class ToCityValueInit(val city: City): SearchJourneyAction()
    data class PassPassengersData(val passengersData: PassengerData): SearchJourneyAction()
    data class OnSearchJourneyButtonClicked(val journeyPost: JourneyPost): SearchJourneyAction()
    object OnSwapCities: SearchJourneyAction()
    object SimilarCitiesInit: SearchJourneyAction()
}
