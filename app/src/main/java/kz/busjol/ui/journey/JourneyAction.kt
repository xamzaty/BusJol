package kz.busjol.ui.journey

sealed class JourneyAction {
    data class FilterListByTypeButtonClicked(val seatType: String): JourneyAction()
}