package kz.busjol.ui.driver_scanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.busjol.base.BaseViewModel
import kz.busjol.utils.state.LoadingType
import kz.busjol.utils.state.ViewState
import org.json.JSONObject

class DriverScannerViewModel(
    private val ioContext: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel() {

    private val _viewState = MutableLiveData<ViewState<DriverScannerViewState>>()
    val viewState: LiveData<ViewState<DriverScannerViewState>> = _viewState

    private fun checkQrData(result: JSONObject) {
        _viewState.postValue(ViewState.Loading(LoadingType.Progress))
        viewModelScope.launch(ioContext) {
            qrDataFetched(result)
        }
    }

    private fun qrDataFetched(result: JSONObject) {
        _viewState.postValue(
            ViewState.Data(
                DriverScannerViewState.NavigateToTheNextScreen(result)
            )
        )
    }

    fun onAction(action: DriverScannerAction) {
        when (action) {
            is DriverScannerAction.PassQrData -> checkQrData(action.result)
        }
    }
}