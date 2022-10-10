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
import kz.busjol.utils.state.LoadingType
import kz.busjol.utils.state.ViewState

class CityPickerViewModel(
    private val repository: CityRepository,
    private val ioContext: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel() {

    private val cityList = MutableLiveData<List<City>>()
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

    private fun cityValueChanged(text: String) {
        val result = ArrayList<City>()

        if (text.isEmpty()) {
            cityList.value?.let { result.addAll(it) }
        } else {
            cityList.value?.let {
                searchText(
                    text = text,
                    it
                )
            }?.let {
                result.addAll(
                    it
                )
            }
        }

        _viewState.value = ViewState.Data(CityListViewState.CityListDataInit(result))
    }

    private fun searchText(text: String, cityList: List<City>) = cityList.filter {
        it.name.lowercase().contains(text.lowercase())
    }

    private fun onCityListDataFetched(city: List<City>) {
        cityList.postValue(city)
        _viewState.postValue(ViewState.Data(CityListViewState.CityListDataInit(city)))
    }

    private fun onError(throwable: Throwable) {
        _viewState.postValue(ViewState.Error(throwable))
    }

    fun onAction(action: CityListAction) {
        when(action) {
            is CityListAction.SearchCity -> cityValueChanged(action.text)
        }
    }
}
