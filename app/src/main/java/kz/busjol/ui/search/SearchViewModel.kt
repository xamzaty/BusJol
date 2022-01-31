package kz.busjol.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.busjol.base.BaseViewModel

class SearchViewModel : BaseViewModel() {

    private val _fromCityValue = MutableLiveData("Алматы")
    private val _toCityValue = MutableLiveData("НурСултан")
    val fromCityValue: LiveData<String> = _fromCityValue
    val toCityValue: LiveData<String> = _toCityValue

    private fun swapCities() {
        _fromCityValue.postValue(_toCityValue.value)
        _toCityValue.postValue(_fromCityValue.value)
    }

    fun onAction(action: SearchFragmentAction) {
        when(action) {
            is SearchFragmentAction.SwapCities -> swapCities()
        }
    }
}