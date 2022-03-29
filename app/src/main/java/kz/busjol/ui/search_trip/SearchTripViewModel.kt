package kz.busjol.ui.search_trip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.busjol.base.BaseViewModel
import kz.busjol.data.City
import kz.busjol.preferences.PassengerPreferences
import kz.busjol.repository.CityRepository
import timber.log.Timber

class SearchTripViewModel(
    private val repository: CityRepository,
    private val preferences: PassengerPreferences
) : BaseViewModel() {

    private val citiesLiveData = MutableLiveData<List<City>>()
    private val _fromCityValue = MutableLiveData("Алматы")
    private val _toCityValue = MutableLiveData("Нур-Султан")
    private val _passengersQuantity = MutableLiveData("1 человек")

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
            is CitiesListAction.PassPassengersQuantityData -> {
                val passengersQuantity = action.quantityList
                preferences.setAllPassengersQuantity(passengersQuantity[0])
                preferences.setAdultQuantity(passengersQuantity[1])
                preferences.setChildQuantity(passengersQuantity[2])
                preferences.setDisabledQuantity(passengersQuantity[3])
            }
        }
    }
}