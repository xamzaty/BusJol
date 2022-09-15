package kz.busjol.ui.city_picker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.busjol.base.BaseViewModel
import kz.busjol.data.mapper.CityListMapper
import kz.busjol.data.network.model.Response
import kz.busjol.domain.model.City
import kz.busjol.domain.repository.CityRepository
import kz.busjol.ui.search_journey.SearchJourneyAction
import kz.busjol.utils.state.LoadingType
import kz.busjol.utils.state.ViewState

class CityPickerViewModel(
    private val repository: CityRepository,
    private val ioContext: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel() {

    private val _viewState = MutableLiveData<ViewState<CityListViewState>>(
        ViewState.Loading(
        LoadingType.Progress))
    val viewState: LiveData<ViewState<CityListViewState>> = _viewState

    init {
        getCityList()
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

    private fun cityValueChanged(text: String, cityList: List<City>) {
        val result = ArrayList<City>(cityList)

        if (text.isEmpty()) result.addAll(cityList)
        else result.addAll(searchText(text, cityList))

        onCityListDataFetched(result)
    }

    private fun searchText(text: String, cityList: List<City>) = cityList.filter {
        it.name.lowercase().contains(text.lowercase())
    }

    private fun onCityListDataFetched(city: List<City>) {
        _viewState.postValue(ViewState.Data(CityListViewState.CityListDataInit(city)))
    }

    private fun onError(throwable: Throwable) {
        _viewState.postValue(ViewState.Error(throwable))
    }

    fun onAction(action: CityListAction) {
        when(action) {
            is CityListAction.SearchCity -> cityValueChanged(action.text, action.cityList)
        }
    }
}