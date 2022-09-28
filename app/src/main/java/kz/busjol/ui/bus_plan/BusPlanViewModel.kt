package kz.busjol.ui.bus_plan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.busjol.base.BaseViewModel
import kz.busjol.data.network.model.BusPlan

class BusPlanViewModel : BaseViewModel() {
    val seatList = MutableLiveData<List<BusPlan>>()
    val listBus = arrayListOf<BusPlan>()

    init {
        postValue()
    }

    private fun postValue() {
        viewModelScope.launch {
            listBus.add(BusPlan(1, true))
            listBus.add(BusPlan(2, true))
            listBus.add(BusPlan(3, false))
            listBus.add(BusPlan(4, false))
            listBus.add(BusPlan(5, true))
            listBus.add(BusPlan(6, false))
            listBus.add(BusPlan(7, false))
            listBus.add(BusPlan(8, false))
            listBus.add(BusPlan(9, false))
            listBus.add(BusPlan(10, false))
            listBus.add(BusPlan(11, false))
            listBus.add(BusPlan(12, true))
            listBus.add(BusPlan(13, false))
            listBus.add(BusPlan(14, false))
            listBus.add(BusPlan(15, false))
            listBus.add(BusPlan(16, false))
            listBus.add(BusPlan(17, false))
            listBus.add(BusPlan(18, false))
            listBus.add(BusPlan(19, false))
            listBus.add(BusPlan(20, false))
            seatList.postValue(listBus)
        }
    }
}