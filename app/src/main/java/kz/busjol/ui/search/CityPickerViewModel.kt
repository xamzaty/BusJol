package kz.busjol.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.busjol.base.BaseViewModel
import kz.busjol.data.City

class CityPickerViewModel : BaseViewModel() {
    private val _citiesLiveData = MutableLiveData<List<City>>()
    val citiesList: LiveData<List<City>> = _citiesLiveData

    private fun cityValueChanged(queryText: String) {
        val filteredList = ArrayList(_citiesLiveData.value.orEmpty().filter {
            it.name.contains(queryText, true)
        }.map { it.copy() })
        _citiesLiveData.value = filteredList.map { it }
    }

    fun onAction(action: CitiesListAction) {
        when(action) {
            is CitiesListAction.SearchCity -> cityValueChanged(action.query)
            is CitiesListAction.PassCityList -> _citiesLiveData.value = action.cityList
        }
    }
}