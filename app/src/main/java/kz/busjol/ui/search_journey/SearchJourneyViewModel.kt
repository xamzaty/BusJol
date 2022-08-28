package kz.busjol.ui.search_journey

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kz.busjol.base.BaseViewModel
import kz.busjol.domain.use_case.SearchTripUseCase
import kz.jysan.business.core.ui.utils.state.ViewState

class SearchJourneyViewModel(private val useCase: SearchTripUseCase) : BaseViewModel() {

    val viewState: LiveData<ViewState<SearchJourneyViewState>> = useCase.viewState

    init {
        useCase.initDataWithCities(viewModelScope)
    }

    fun onAction(action: SearchJourneyAction) {
        when(action) {
            is SearchJourneyAction.InitJourneyData -> Unit
            is SearchJourneyAction.CityValueInit -> useCase.initCities(action.journeyData, action.city, action.isFromInit)
            is SearchJourneyAction.PassPassengersData -> useCase.passPassengersQuantity(action.passengersData)
            is SearchJourneyAction.OnSwapCities -> useCase.onSwapCities(action.journeyData)
            is SearchJourneyAction.OnJourneyBackDataInit -> useCase.onJourneyBackDataInit(action.journeyData)
            is SearchJourneyAction.OnSearchJourneyButtonClicked ->
                useCase.searchJourney(action.journeyPost, action.journeyData, viewModelScope)
            is SearchJourneyAction.SimilarCities -> useCase.isCitiesEquals(action.journeyData)
            else -> Unit
        }
    }
}