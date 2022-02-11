package kz.busjol.ui.search

import kz.busjol.base.BaseViewModel
import kz.busjol.preferences.PassengerPreferences

class PassengersQuantityViewModel(
    private val preferences: PassengerPreferences
) : BaseViewModel() {

    fun onAction(action: CitiesListAction) {
        when(action) {
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