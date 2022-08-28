package kz.busjol.ui.city_picker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.busjol.base.BaseViewModel
import kz.busjol.data.network.model.Response
import kz.busjol.domain.model.City
import kz.busjol.domain.repository.CityRepository
import kz.busjol.ui.search_journey.SearchJourneyAction
import kz.jysan.business.core.ui.utils.state.LoadingType
import kz.jysan.business.core.ui.utils.state.ViewState

class CityPickerViewModel(
    private val repository: CityRepository,
    private val ioContext: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel() {

    private val _viewState = MutableLiveData<ViewState<CityListViewState>>(ViewState.Loading(LoadingType.Progress))
    val viewState: LiveData<ViewState<CityListViewState>> = _viewState

    init {
        getCityList()
    }

    private fun cityValueChanged(queryText: String) {
//        val filteredList = listOf(City()).filter { it.name.contains(queryText, true) }
//        _viewState.postValue(ViewState.Data(CityListViewState.CityListDataInit(city = filteredList.map { it })))
    }

    private fun getCityList() {
        _viewState.postValue(ViewState.Loading(LoadingType.Progress))
        viewModelScope.launch(ioContext) {
            when (val response = repository.getCityList()) {
                is Response.Success -> onCityListDataFetched(response.data)
                is Response.Failure -> onError(response.data)
            }
        }
    }

    private fun onCityListDataFetched(city: List<City>) {
        _viewState.postValue(ViewState.Data(CityListViewState.CityListDataInit(city)))
    }

    private fun onError(throwable: Throwable) {
        _viewState.postValue(ViewState.Error(throwable))
    }

    fun onAction(action: SearchJourneyAction) {
        when(action) {
//            is SearchJourneyAction.SearchCity -> Unit
            else -> Unit
        }
    }
}