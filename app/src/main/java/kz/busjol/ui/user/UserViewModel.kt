package kz.busjol.ui.user

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.busjol.base.BaseViewModel
import kz.busjol.preferences.UserPreferences

class UserViewModel(
    private val userPreferences: UserPreferences
) : BaseViewModel() {
    val isDriver = userPreferences.getDriverIsAuthorized.asLiveData()
    val isUserAuthorized = userPreferences.getUserIsAuthorized.asLiveData()

    fun exitDriverStatus() = viewModelScope.launch(Dispatchers.IO) {
        userPreferences.setDriverIsAuthorized(false)
    }

    fun exitUserAuthorizedStatus() = viewModelScope.launch(Dispatchers.IO) {
        userPreferences.setUserIsAuthorized(false)
    }
}