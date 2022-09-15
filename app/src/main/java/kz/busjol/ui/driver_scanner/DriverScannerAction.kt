package kz.busjol.ui.driver_scanner

import org.json.JSONObject

sealed class DriverScannerAction {
    data class PassQrData(val result: JSONObject): DriverScannerAction()
}