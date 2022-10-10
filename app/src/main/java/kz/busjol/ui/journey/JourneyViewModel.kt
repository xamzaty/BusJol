package kz.busjol.ui.journey

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.busjol.base.BaseViewModel
import kz.busjol.domain.model.Journey
import kz.busjol.domain.model.JourneyData
import kz.busjol.domain.model.JourneyItem
import kz.busjol.utils.state.ViewState

class JourneyViewModel(
    private val args: JourneyFragmentArgs
) : BaseViewModel() {

    private val journeyList = MutableLiveData<List<Journey>>()
    private val _viewState = MutableLiveData<ViewState<JourneyViewState>>()
    val viewState: LiveData<ViewState<JourneyViewState>> = _viewState

    init {
        journeyDataFetch()
    }

    private fun journeyDataFetch() {
        _viewState.postValue(
            ViewState.Data(
                JourneyViewState.JourneyDataInit(
                    journeyData = JourneyData(
                        passengerData = args.journeyData.passengerData,
                        passengerListData = args.journeyData.passengerListData,
                        fromCity = args.journeyData.fromCity,
                        toCity = args.journeyData.toCity,
                        journey = null,
                        journeyList = journeyList()
                    )
                )
            )
        )
    }

    private fun journeyList() = listOf(
        Journey(
            journey = JourneyItem(
                id = 0,
                created = "sdf",
                status = 0,
                name = "Лежачий",
                departsOn = "24.02.2022",
                routeId = 0,
                carrierId = 0,
                transportId = 0,
                code = "0"
            ),
            departureTime = "24.02.2022",
            arrivalTime = "asd",
            amount = 3000,
            numberOfPlaces = 20,
            stopName = "Автобусная Станция",
            numberOfFreePlaces = 5,
            cityFrom = args.journeyData.fromCity,
            cityTo = args.journeyData.toCity,
        ),

        Journey(
            journey = JourneyItem(
                id = 0,
                created = "sdf",
                status = 0,
                name = "Сидячий",
                departsOn = "sadf",
                routeId = 0,
                carrierId = 0,
                transportId = 0,
                code = "0"
            ),
            departureTime = "24.02.2022 23:00",
            arrivalTime = "25.02.2022 17:00",
            amount = 3000,
            numberOfPlaces = 20,
            stopName = "Автобусная Станция",
            numberOfFreePlaces = 5,
            cityFrom = args.journeyData.fromCity,
            cityTo = args.journeyData.toCity,
        ),
    )

    private fun filterListByType(isAllPlaces: Boolean, seatType: String) {
        if (isAllPlaces) filterListByAllType()
        else filterListByType(seatType)
    }

    private fun filterListByAllType() {
        val result = ArrayList<Journey>()
        args.journeyData.journeyList?.let { result.addAll(it) }

        _viewState.postValue(
            ViewState.Data(
                JourneyViewState.JourneyDataInit(
                    JourneyData(
                        passengerData = args.journeyData.passengerData,
                        passengerListData = args.journeyData.passengerListData,
                        fromCity = args.journeyData.fromCity,
                        toCity = args.journeyData.toCity,
                        journey = args.journeyData.journey,
                        journeyList = result
                ))
            )
        )
    }

    private fun filterListByType(seatType: String) {
        val result = ArrayList<Journey>()

        if (seatType.isEmpty()) {
            journeyList.value?.let { result.addAll(it) }
        } else {
            journeyList.value?.let {
                searchText(
                    text = seatType,
                    it
                )
            }?.let {
                result.addAll(
                    it
                )
            }
        }

        _viewState.value = ViewState.Data(JourneyViewState.JourneyDataInit(journeyData = JourneyData(
            passengerData = args.journeyData.passengerData,
            passengerListData = args.journeyData.passengerListData,
            fromCity = args.journeyData.fromCity,
            toCity = args.journeyData.toCity,
            journey = args.journeyData.journey,
            journeyList = result
        )
        ))
    }

    private fun searchText(text: String, journeyList: List<Journey>) = journeyList.filter {
        val journey = it.journey?.name?.lowercase().orEmpty()
        journey.contains(text.lowercase()) ?: false
    }

    fun onAction(action: JourneyAction) {
        when (action) {
            is JourneyAction.FilterListByTypeButtonClicked -> filterListByType(action.isAllPlaces, action.seatType)
        }
    }
}