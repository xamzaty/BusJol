package kz.busjol.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.busjol.base.BaseViewModel
import kz.busjol.data.City
import kz.busjol.repository.CityRepository
import timber.log.Timber

class SearchViewModel(
    private val repository: CityRepository
) : BaseViewModel() {
    private val citiesLiveData = MutableLiveData<List<City>>()
    private val _fromCityValue = MutableLiveData<String>()
    private val _toCityValue = MutableLiveData<String>()
    private val _passengersQuantity = MutableLiveData<String>()

    val citiesList: LiveData<List<City>> = citiesLiveData
    val fromCityValue: LiveData<String> = _fromCityValue
    val toCityValue: LiveData<String> = _toCityValue
    val passengersQuantity: LiveData<String> = _passengersQuantity

    init {
        fetchCityData()
    }

    private fun fetchCityData() {
        networkRequest(
            request = { repository.getCityList() },
            onSuccess = { cityResponse -> citiesLiveData.value = cityResponse },
            onError = { cityResponse -> Timber.e(cityResponse) }
        )
    }

    private fun swapCities() {
        _fromCityValue.postValue(_toCityValue.value)
        _toCityValue.postValue(_fromCityValue.value)
    }

    fun onAction(action: CitiesListAction) {
        when(action) {
            is CitiesListAction.SwapCities -> swapCities()
            is CitiesListAction.FromCityValue -> _fromCityValue.value = action.city
            is CitiesListAction.ToCityValue -> _toCityValue.value = action.city
            is CitiesListAction.FillPassengersQuantityValue -> _passengersQuantity.value = action.quantity
        }
    }
}