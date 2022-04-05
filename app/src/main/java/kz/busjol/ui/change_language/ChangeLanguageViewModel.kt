package kz.busjol.ui.change_language

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.busjol.base.BaseViewModel
import kz.busjol.preferences.UserPreferences

class ChangeLanguageViewModel(private val userPreferences: UserPreferences) : BaseViewModel() {
    fun changeLanguage(value: String) = viewModelScope.launch(Dispatchers.IO) {
        userPreferences.setAppLanguage(value)
    }
}