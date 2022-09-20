package kz.busjol.ui.journey

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.busjol.base.BaseViewModel
import kz.busjol.domain.model.JourneyData
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
                        fromCity = args.journeyData.fromCity,
                        toCity = args.journeyData.toCity,
                        journeyList = args.journeyData.journeyList
                    )
                )
            )
        )
    }

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
        _viewState.postValue(
            ViewState.Data(
                JourneyViewState.JourneyDataInit(
                    JourneyData(
                        passengerData = args.journeyData.passengerData,
                        fromCity = args.journeyData.fromCity,
                        toCity = args.journeyData.toCity,
                        journeyList = args.journeyData.journeyList?.filter { it.journey?.name!!.contains(seatType) }
                    )
                )
            )
        )
    }

    fun onAction(action: JourneyAction) {
        when (action) {
            is JourneyAction.FilterListByTypeButtonClicked -> filterListByType(action.isAllPlaces, action.seatType)
        }
    }
}