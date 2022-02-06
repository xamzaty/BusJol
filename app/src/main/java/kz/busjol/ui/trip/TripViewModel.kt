package kz.busjol.ui.trip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.busjol.base.BaseViewModel
import kz.busjol.data.Trip

class TripViewModel : BaseViewModel() {

    private val initTripList = mutableListOf<Trip>()
    private val _tripList = MutableLiveData<List<Trip>>()
    val tripList: LiveData<List<Trip>> = _tripList

    init {
        initTripList.add(Trip("21:00","28.02.2022", "23", "Сидячий", "Алматы - Нурсултан", "7", "20", "Автовокзал Алматы",  "2000 Т"))
        initTripList.add(Trip("21:00","28.02.2022", "23", "Сидячий", "Алматы - Нурсултан", "7", "20", "Автовокзал Алматы",  "2000 Т"))
        _tripList.value = initTripList
    }
}