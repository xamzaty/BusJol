package kz.busjol.ui.driver_scanner

import org.json.JSONObject

sealed class DriverScannerViewState {
    data class NavigateToTheNextScreen(val result: JSONObject): DriverScannerViewState()
}