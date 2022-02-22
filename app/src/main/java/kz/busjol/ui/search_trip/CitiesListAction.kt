package kz.busjol.ui.search_trip

import kz.busjol.data.City

sealed class CitiesListAction {
    data class FromCityValue(val city: String): CitiesListAction()
    data class ToCityValue(val city: String): CitiesListAction()
    data class FillPassengersQuantityValue(val quantity: String): CitiesListAction()
    data class SearchCity(val query: String): CitiesListAction()
    data class PassCityList(var cityList: MutableList<City>): CitiesListAction()
    data class PassPassengersQuantityData(val quantityList: MutableList<Int>): CitiesListAction()
    object SwapCities: CitiesListAction()
}
