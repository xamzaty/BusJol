package kz.busjol

import androidx.lifecycle.asLiveData
import kz.busjol.base.BaseViewModel
import kz.busjol.preferences.UserPreferences

class MainViewModel(
    private val userPreferences: UserPreferences
): BaseViewModel() {
    val isDriverStatus = userPreferences.getDriverIsAuthorized.asLiveData()
    val isUserStatus = userPreferences.getUserIsAuthorized.asLiveData()
}