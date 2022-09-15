package kz.busjol.ui.city_picker

import kz.busjol.domain.model.City

sealed class CityListAction {
    data class SearchCity(val text: String, val cityList: List<City>): CityListAction()
}
