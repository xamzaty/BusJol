package kz.busjol.ui.bus_plan

import androidx.lifecycle.MutableLiveData
import kz.busjol.base.BaseViewModel
import kz.busjol.data.BusPlan

class BusPlanViewModel : BaseViewModel() {
    val seatList = MutableLiveData<List<BusPlan>>()
    val listBus = arrayListOf<BusPlan>()

    init {
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(2))
        listBus.add(BusPlan(3))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(11))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(15))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(17))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(18))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))
        listBus.add(BusPlan(1))

        seatList.postValue(listBus)
    }
}