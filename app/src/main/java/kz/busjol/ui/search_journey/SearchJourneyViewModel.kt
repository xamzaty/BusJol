package kz.busjol.ui.search_journey

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.busjol.base.BaseViewModel
import kz.busjol.data.network.model.Response
import kz.busjol.domain.model.*
import kz.busjol.domain.repository.CityRepository
import kz.busjol.domain.repository.JourneyRepository
import kz.busjol.ui.passenger_quantity.Passenger
import kz.busjol.utils.state.LoadingType
import kz.busjol.utils.state.ViewState

class SearchJourneyViewModel(
    private val journeyRepository: JourneyRepository,
    private val cityRepository: CityRepository,
    private val ioContext: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel() {

    private val _viewState = MutableLiveData<ViewState<SearchJourneyViewState>>()
    val viewState: LiveData<ViewState<SearchJourneyViewState>> = _viewState

    private val _fromCity = MutableLiveData<City?>()
    private val _toCity = MutableLiveData<City?>()
    private val _passengerData = MutableLiveData(PassengerData(adultQuantity = 1, childQuantity = 0, disabledQuantity = 0))
    private val _passengerList = MutableLiveData(listOf(Passenger(type = Passenger.PassengerType.ADULT)))

    val fromCity: LiveData<City?> = _fromCity
    val toCity: LiveData<City?> = _toCity
    val passengerData: LiveData<PassengerData> = _passengerData
    val passengerList: LiveData<List<Passenger>> = _passengerList

    init {
        initDataWithCities()
    }

    private fun initDataWithCities() {
        viewModelScope.launch(ioContext) {
            when (val response = cityRepository.getCityList()) {
                is Response.Success -> onCityListDataFetched(response.data)
                is Response.Failure -> onError(response.data)
            }
        }
    }

    private fun onCityListDataFetched(city: List<City>) {
        _fromCity.postValue(city[0])
        _toCity.postValue(city[1])
    }

    private fun fromCityInit(city: City) {
        _fromCity.postValue(city)
    }

    private fun toCityInit(city: City) {
        _toCity.postValue(city)
    }

    private fun onSwapCities() {
        _fromCity.postValue(_toCity.value)
        _toCity.postValue(_fromCity.value)
    }

    private fun initPassengerValue(passengerData: PassengerData) {
        _passengerData.postValue(passengerData)
    }

    private fun initPassengerListValue(passengerListData: List<Passenger>) {
        _passengerList.postValue(passengerListData)
    }

    private fun searchJourney(journeyPost: JourneyPost) {
        _viewState.postValue(ViewState.Loading(LoadingType.Progress))
        viewModelScope.launch(ioContext) {
            when (val response = journeyRepository.getJourneyList(journeyPost)) {
                is Response.Success -> onJourneyListDataFetched(response.data)
                is Response.Failure -> onError(response.data)
            }
        }
    }

    private fun onJourneyListDataFetched(journeyList: List<Journey>) {
        val journeyData = JourneyData(
            passengerData = _passengerData.value,
            passengerListData = _passengerList.value,
            fromCity = _fromCity.value,
            toCity = _toCity.value,
            journey = null,
            journeyList = journeyList
        )

        _viewState.postValue(
            ViewState.Data(
                SearchJourneyViewState.NavigateToJourneyScreen(journeyData, journeyList)
            )
        )
    }

    private fun onSimilarCitiesInit() {
        _fromCity.postValue(null)
        _toCity.postValue(null)
    }

    private fun onError(throwable: Throwable) {
        _viewState.postValue(ViewState.Error(throwable))
    }

    fun onAction(action: SearchJourneyAction) {
        when(action) {
            is SearchJourneyAction.FromCityValueInit -> fromCityInit(action.city)
            is SearchJourneyAction.ToCityValueInit -> toCityInit(action.city)
            is SearchJourneyAction.PassPassengersData -> initPassengerValue(action.passengersData)
            is SearchJourneyAction.PassPassengerListData -> initPassengerListValue(action.passengerList)
            is SearchJourneyAction.OnSwapCities -> onSwapCities()
            is SearchJourneyAction.OnSearchJourneyButtonClicked -> searchJourney(action.journeyPost)
            is SearchJourneyAction.SimilarCitiesInit -> onSimilarCitiesInit()
        }
    }
}