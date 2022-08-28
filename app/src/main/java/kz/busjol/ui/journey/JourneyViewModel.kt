package kz.busjol.ui.journey

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.busjol.base.BaseViewModel
import kz.busjol.domain.model.JourneyData
import kz.jysan.business.core.ui.utils.state.ViewState

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
                    args.journeyData
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