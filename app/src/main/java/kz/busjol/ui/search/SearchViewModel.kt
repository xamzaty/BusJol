package kz.busjol.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.busjol.base.BaseViewModel
import kz.busjol.data.City

class SearchViewModel : BaseViewModel() {

    private val cityList = mutableListOf<City>()
    private val citiesLiveData = MutableLiveData<List<City>>()
    val citiesList: LiveData<List<City>> = citiesLiveData

    private val _fromCityValue = MutableLiveData<String>()
    private val _toCityValue = MutableLiveData<String>()
    private val _passengersQuantity = MutableLiveData<String>()

    val fromCityValue: LiveData<String> = _fromCityValue
    val toCityValue: LiveData<String> = _toCityValue
    val passengersQuantity: LiveData<String> = _passengersQuantity

    init {
        cityList.add(City("Алматы"))
        cityList.add(City("Нур-Султан"))
        cityList.add(City("Караганда"))
        cityList.add(City("Балхаш"))
        cityList.add(City("Шымкент"))
        cityList.add(City("Тараз"))

        citiesLiveData.value = cityList
    }

    private fun swapCities() {
        _fromCityValue.postValue(_toCityValue.value)
        _toCityValue.postValue(_fromCityValue.value)
    }

    fun onAction(action: SearchFragmentAction) {
        when(action) {
            is SearchFragmentAction.SwapCities -> swapCities()
            is SearchFragmentAction.FromCityValue -> _fromCityValue.value = action.city
            is SearchFragmentAction.ToCityValue -> _toCityValue.value = action.city
            is SearchFragmentAction.FillPassengersQuantityValue -> _passengersQuantity.value = action.quantity
        }
    }
}