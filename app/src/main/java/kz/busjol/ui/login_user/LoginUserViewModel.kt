package kz.busjol.ui.login_user

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.busjol.base.BaseViewModel
import kz.busjol.preferences.UserPreferences

class LoginUserViewModel(private val userPreferences: UserPreferences) : BaseViewModel() {

    fun setDriverStatus(value: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        userPreferences.setDriverIsAuthorized(value)
    }

    fun setUserStatus(value: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        userPreferences.setUserIsAuthorized(value)
    }
}