package kz.busjol.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kz.busjol.base.BaseViewModel
import kz.busjol.data.City
import kz.busjol.preferences.PassengerPreferences
import javax.inject.Inject

class SearchViewModel : BaseViewModel() {

    private val cityList = mutableListOf<City>()
    private val citiesLiveData = MutableLiveData<List<City>>()
    private val _fromCityValue = MutableLiveData<String>()
    private val _toCityValue = MutableLiveData<String>()
    private val _passengersQuantity = MutableLiveData<String>()

    val citiesList: LiveData<List<City>> = citiesLiveData
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

    private fun cityValueChanged(queryText: String) {
        val filteredList = ArrayList(cityList.filter {
            it.name.contains(queryText, true)
        }.map { it.copy() })
        citiesLiveData.value = filteredList.map { it }
    }

    fun onAction(action: SearchFragmentAction) {
        when(action) {
            is SearchFragmentAction.SwapCities -> swapCities()
            is SearchFragmentAction.FromCityValue -> _fromCityValue.value = action.city
            is SearchFragmentAction.ToCityValue -> _toCityValue.value = action.city
            is SearchFragmentAction.FillPassengersQuantityValue -> _passengersQuantity.value = action.quantity
            is SearchFragmentAction.SearchCity -> cityValueChanged(action.query)
        }
    }
}