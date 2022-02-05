package kz.busjol.ui.search

sealed class SearchFragmentAction {
    data class FromCityValue(val city: String): SearchFragmentAction()
    data class ToCityValue(val city: String): SearchFragmentAction()
    data class FillPassengersQuantityValue(val quantity: String): SearchFragmentAction()
    object SwapCities: SearchFragmentAction()
}
