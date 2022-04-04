package kz.busjol.ui.search_trip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.busjol.base.BaseViewModel
import kz.busjol.data.City

class CityPickerViewModel : BaseViewModel() {
    private val _citiesList = MutableLiveData<List<City>>()
    val citiesList: LiveData<List<City>> = _citiesList

    private fun cityValueChanged(queryText: String) {
        val filteredList = ArrayList(_citiesList.value.orEmpty().filter { city ->
            city.name.contains(queryText, true)
        }.map { it.copy() })
        _citiesList.value = filteredList.map { it }
    }

    fun onAction(action: CitiesListAction) {
        when(action) {
            is CitiesListAction.SearchCity -> cityValueChanged(action.query)
            is CitiesListAction.PassCityList -> _citiesList.value = action.cityList
            else -> Unit
        }
    }
}