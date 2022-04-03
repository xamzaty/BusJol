package kz.busjol.ui.trip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.busjol.base.BaseViewModel
import kz.busjol.data.Trip

class TripViewModel : BaseViewModel() {

    private val initTripList = mutableListOf<Trip>()
    private val _tripList = MutableLiveData<List<Trip>>()
    private val _tripLyingPlaces = MutableLiveData<List<Trip>>()
    private val _tripSeatingPlaces = MutableLiveData<List<Trip>>()

    val tripList: LiveData<List<Trip>> = _tripList
    val tripLyingList: LiveData<List<Trip>> = _tripLyingPlaces
    val tripSeatingList: LiveData<List<Trip>> = _tripSeatingPlaces

    init {
        initTripList.add(Trip("Алматы","Нур-Султан","21:00","28.02.2022", "23", "Лежачий", "Алматы - Нурсултан", "7", "20", "Автовокзал Алматы",  2000.0))
        initTripList.add(Trip("Алматы","Нур-Султан","21:00","28.02.2022", "23", "Сидячий", "Алматы - Нурсултан", "7", "20", "Автовокзал Алматы",  5000.0))
        initTripList.add(Trip("Алматы","Нур-Султан","21:00","28.02.2022", "23", "Сидячий", "Алматы - Нурсултан", "7", "20", "Автовокзал Алматы",  5000.0))
        initTripList.add(Trip("Алматы","Нур-Султан","21:00","28.02.2022", "23", "Лежачий", "Алматы - Нурсултан", "7", "20", "Автовокзал Алматы",  5000.0))
        initTripList.add(Trip("Алматы","Нур-Султан","21:00","28.02.2022", "23", "Сидячий", "Алматы - Нурсултан", "7", "20", "Автовокзал Алматы",  5000.0))
        initTripList.add(Trip("Алматы","Нур-Султан","21:00","28.02.2022", "23", "Сидячий", "Алматы - Нурсултан", "7", "20", "Автовокзал Алматы",  5000.0))
        initTripList.add(Trip("Алматы","Нур-Султан","21:00","28.02.2022", "23", "Лежачий", "Алматы - Нурсултан", "7", "20", "Автовокзал Алматы",  5000.0))
        initTripList.add(Trip("Алматы","Нур-Султан","21:00","28.02.2022", "23", "Лежачий", "Алматы - Нурсултан", "7", "20", "Автовокзал Алматы",  5000.0))
        initTripList.add(Trip("Алматы","Нур-Султан","21:00","28.02.2022", "23", "Сидячий", "Алматы - Нурсултан", "7", "20", "Автовокзал Алматы",  5000.0))
        initTripList.add(Trip("Алматы","Нур-Султан","21:00","28.02.2022", "23", "Лежачий", "Алматы - Нурсултан", "7", "20", "Автовокзал Алматы",  5000.0))
        initTripList.add(Trip("Алматы","Нур-Султан","21:00","28.02.2022", "23", "Сидячий", "Алматы - Нурсултан", "7", "20", "Автовокзал Алматы",  5000.0))
        initTripList.add(Trip("Алматы","Нур-Султан","21:00","28.02.2022", "23", "Лежачий", "Алматы - Нурсултан", "7", "20", "Автовокзал Алматы",  5000.0))
        initTripList.add(Trip("Алматы","Нур-Султан","21:00","28.02.2022", "23", "Сидячий", "Алматы - Нурсултан", "7", "20", "Автовокзал Алматы",  5000.0))
        _tripList.value = initTripList
        _tripLyingPlaces.value = initTripList.filter { it.type.contains("Лежачий") }
        _tripSeatingPlaces.value = initTripList.filter { it.type.contains("Сидячий") }
    }

    private fun filterList(isLyingList: Boolean, text: Int) {
        if (isLyingList) _tripLyingPlaces.value = initTripList.filter { it.type.contains("Лежачий") }
        else _tripSeatingPlaces.value = initTripList.filter { it.type.contains("Сидячий") }
    }
}