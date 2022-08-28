package kz.busjol.domain.use_case

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.busjol.data.network.model.Response
import kz.busjol.domain.model.*
import kz.busjol.domain.repository.CityRepository
import kz.busjol.domain.repository.JourneyRepository
import kz.busjol.ui.search_journey.SearchJourneyViewState
import kz.jysan.business.core.ui.utils.state.LoadingType
import kz.jysan.business.core.ui.utils.state.ViewState

class SearchTripUseCase(
    private val journeyRepository: JourneyRepository,
    private val cityRepository: CityRepository,
    private val ioContext: CoroutineDispatcher = Dispatchers.IO
) {
    val viewState = MutableLiveData<ViewState<SearchJourneyViewState>>()

    fun searchJourney(journeyPost: JourneyPost, journeyData: JourneyData, viewModelScope: CoroutineScope) {
        viewState.postValue(ViewState.Loading(LoadingType.Progress))
        viewModelScope.launch(ioContext) {
            when (val response = journeyRepository.getJourneyList(journeyPost)) {
                is Response.Success -> onJourneyListDataFetched(response.data, journeyData)
                is Response.Failure -> onError(response.data)
            }
        }
    }

    private fun onJourneyListDataFetched(journeyList: List<Journey>, journeyData: JourneyData) {
        viewState.postValue(
            ViewState.Data(
                SearchJourneyViewState.NavigateToJourneyScreen(journeyData, journeyList)
            )
        )
    }

    fun initDataWithCities(viewModelScope: CoroutineScope) {
        viewModelScope.launch(ioContext) {
            when (val response = cityRepository.getCityList()) {
                is Response.Success -> onCityListDataFetched(response.data)
                is Response.Failure -> onError(response.data)
            }
        }
    }

    private fun onCityListDataFetched(city: List<City>) {
        viewState.postValue(
            ViewState.Data(
                SearchJourneyViewState.JourneyDataInit(
                    JourneyData(
                        passengerData = PassengerData(adultQuantity = 1, childQuantity = 0, disabledQuantity = 0),
                        fromCity = city[0],
                        toCity = city[1]
                    )
                )
            )
        )
    }

    fun initCities(journeyData: JourneyData, city: City, isFromInit: Boolean) {
        viewState.postValue(
            ViewState.Data(
                SearchJourneyViewState.JourneyDataInit(
                    JourneyData(
                        passengerData = journeyData.passengerData,
                        fromCity = if (isFromInit) city else journeyData.fromCity,
                        toCity = if (isFromInit) journeyData.toCity else city
                    )
                )
            )
        )
    }

    fun onSwapCities(journeyData: JourneyData) {
        viewState.postValue(
            ViewState.Data(
                SearchJourneyViewState.JourneyDataInit(
                    JourneyData(
                        passengerData = journeyData.passengerData,
                        fromCity = journeyData.toCity,
                        toCity = journeyData.fromCity
                    )
                )
            )
        )
    }

    fun passPassengersQuantity(journeyData: JourneyData) {
        viewState.postValue(
            ViewState.Data(
                SearchJourneyViewState.JourneyDataInit(journeyData)
            )
        )
    }

    fun onJourneyBackDataInit(journeyData: JourneyData) {
        viewState.postValue(
            ViewState.Data(
                SearchJourneyViewState.JourneyDataInit(journeyData)
            )
        )
    }

    fun isCitiesEquals(journeyData: JourneyData) {
        viewState.postValue(
            ViewState.Data(
                SearchJourneyViewState.JourneyDataInit(
                    JourneyData(
                        passengerData = journeyData.passengerData,
                        fromCity = null,
                        toCity = null
                    )
                )
            )
        )
    }

    private fun onError(throwable: Throwable) {
        viewState.postValue(ViewState.Error(throwable))
    }
}