package kz.busjol.ui.journey

sealed class JourneyAction {
    data class FilterListByTypeButtonClicked(val isAllPlaces: Boolean, val seatType: String = ""): JourneyAction()
}