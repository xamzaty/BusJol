package kz.busjol.ui.tickets

import androidx.lifecycle.asLiveData
import kz.busjol.base.BaseViewModel
import kz.busjol.preferences.UserPreferences

class TicketsViewModel(private val userPreferences: UserPreferences) : BaseViewModel() {
    val isUserAuthorized = userPreferences.getUserIsAuthorized.asLiveData()
}