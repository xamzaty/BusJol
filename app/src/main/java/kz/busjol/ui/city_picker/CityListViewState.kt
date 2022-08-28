package kz.busjol.ui.city_picker

import kz.busjol.domain.model.City

sealed class CityListViewState {
    data class CityListDataInit(val city: List<City>): CityListViewState()
}