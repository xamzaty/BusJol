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
                name = "asdf",
                departsOn = "sadf",
                routeId = 0,
                carrierId = 0,
                transportId = 0,
                code = "0"
            ),
            departureTime = "asd",
            arrivalTime = "asd",
            amount = 3000,
            numberOfPlaces = 20,
            stopName = "Автобусная Станция",
            numberOfFreePlaces = 2,
            cityFrom = args.journeyData.fromCity?.name,
            cityTo = args.journeyData.toCity?.name,
        ),

        Journey(
            journey = JourneyItem(
                id = 0,
                created = "sdf",
                status = 0,
                name = "asdf",
                departsOn = "sadf",
                routeId = 0,
                carrierId = 0,
                transportId = 0,
                code = "0"
            ),
            departureTime = "asd",
            arrivalTime = "asd",
            amount = 3000,
            numberOfPlaces = 20,
            stopName = "Автобусная Станция",
            numberOfFreePlaces = 2,
            cityFrom = args.journeyData.fromCity?.name,
            cityTo = args.journeyData.toCity?.name,
        ),
    )

    private fun filterListByType(isAllPlaces: Boolean, seatType: String) {
        if (isAllPlaces) filterListByAllType()
        else filterListByType(seatType)
    }

    private fun filterListByAllType() {
        _viewState.postValue(
            ViewState.Data(
                JourneyViewState.JourneyDataInit(
                    args.journeyData
                )
            )
        )
    }

    private fun filterListByType(seatType: String) {
//        _viewState.postValue(
//            ViewState.Data(
//                JourneyViewState.JourneyDataInit(
//                    JourneyData(
//                        journeyList = args.journeyData.journeyList?.filter { it.name.contains(seatType) }
//                    )
//                )
//            )
//        )
    }

    fun onAction(action: JourneyAction) {
        when (action) {
            is JourneyAction.FilterListByTypeButtonClicked -> filterListByType(action.isAllPlaces, action.seatType)
        }
    }
}