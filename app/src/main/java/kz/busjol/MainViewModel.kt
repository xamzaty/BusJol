package kz.busjol

import androidx.lifecycle.MutableLiveData
import kz.busjol.base.BaseViewModel
import kz.busjol.preferences.UserPreferences

class MainViewModel(
    private val userPreferences: UserPreferences
): BaseViewModel() {
    val isDriver = MutableLiveData(false)
}