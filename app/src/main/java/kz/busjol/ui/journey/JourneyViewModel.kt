package kz.busjol.ui.journey

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.busjol.base.BaseViewModel
import kz.busjol.domain.model.Journey
import kz.busjol.domain.model.JourneyItem
import kz.busjol.utils.state.ViewState

class JourneyViewModel(
    private val args: JourneyFragmentArgs
) : BaseViewModel() {

    private val journeyList = MutableLiveData<List<Journey>>()
    private val _viewState = MutableLiveData<ViewState<JourneyViewState>>()
    val viewState: LiveData<ViewState<JourneyViewState>> = _viewState

    init {
        journeyList()
    }

    private fun journeyList() {
        val data = arrayListOf(
            Journey(
                journey = JourneyItem(id = 0, created = "24.12.2022", status = 0, name = "Лежачий", departsOn = "24.12.2022", routeId = 0, carrierId = 0, transportId = 0, code = "0"),
                departureTime = "24.02.2022", arrivalTime = "25.02.2022 17:00", amount = 3000, numberOfPlaces = 20, stopName = "Автобусная Станция", numberOfFreePlaces = 5,
                cityFrom = args.journeyData.fromCity, cityTo = args.journeyData.toCity),

            Journey(
                journey = JourneyItem(id = 0, created = "24.12.2022", status = 0, name = "Сидячий", departsOn = "24.12.2022", routeId = 0, carrierId = 0, transportId = 0, code = "0"),
                departureTime = "24.02.2022 23:00", arrivalTime = "25.02.2022 17:00", amount = 4000, numberOfPlaces = 20, stopName = "Автобусная Станция", numberOfFreePlaces = 5,
                cityFrom = args.journeyData.fromCity, cityTo = args.journeyData.toCity),

            Journey(
                journey = JourneyItem(id = 0, created = "24.12.2022", status = 0, name = "Лежачий", departsOn = "24.12.2022", routeId = 0, carrierId = 0, transportId = 0, code = "0"),
                departureTime = "24.02.2022 23:00", arrivalTime = "25.02.2022 17:00", amount = 4000, numberOfPlaces = 20, stopName = "Автобусная Станция", numberOfFreePlaces = 5,
                cityFrom = args.journeyData.fromCity, cityTo = args.journeyData.toCity),

            Journey(
                journey = JourneyItem(id = 0, created = "24.12.2022", status = 0, name = "Сидячий", departsOn = "24.12.2022", routeId = 0, carrierId = 0, transportId = 0, code = "0"),
                departureTime = "24.02.2022 23:00", arrivalTime = "25.02.2022 17:00", amount = 4000, numberOfPlaces = 20, stopName = "Автобусная Станция", numberOfFreePlaces = 5,
                cityFrom = args.journeyData.fromCity, cityTo = args.journeyData.toCity),

            Journey(
                journey = JourneyItem(id = 0, created = "24.12.2022", status = 0, name = "Сидячий", departsOn = "24.12.2022", routeId = 0, carrierId = 0, transportId = 0, code = "0"),
                departureTime = "24.02.2022 23:00", arrivalTime = "25.02.2022 17:00", amount = 4000, numberOfPlaces = 20, stopName = "Автобусная Станция", numberOfFreePlaces = 5,
                cityFrom = args.journeyData.fromCity, cityTo = args.journeyData.toCity),
        )
        journeyList.postValue(data)
        _viewState.postValue(ViewState.Data(JourneyViewState.JourneyDataInit(data)))
    }

    private fun filterList(seatType: String) {
        val result = ArrayList<Journey>()

        if (seatType.isEmpty()) journeyList.value?.let { result.addAll(it) }
        else journeyList.value
            ?.let { searchText(text = seatType, it) }
            ?.let { result.addAll(it) }

        _viewState.value = ViewState.Data(JourneyViewState.JourneyDataInit(result))
    }

    private fun searchText(text: String, journeyList: List<Journey>) = journeyList.filter {
        val journey = it.journey?.name?.lowercase().orEmpty()
        journey.contains(text.lowercase())
    }

    fun onAction(action: JourneyAction) {
        when (action) {
            is JourneyAction.FilterListByTypeButtonClicked -> filterList(action.seatType)
        }
    }
}
