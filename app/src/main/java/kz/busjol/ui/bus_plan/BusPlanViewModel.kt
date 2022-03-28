package kz.busjol.ui.bus_plan

import androidx.lifecycle.MutableLiveData
import kz.busjol.base.BaseViewModel
import kz.busjol.data.BusPlan

class BusPlanViewModel : BaseViewModel() {
    val seatList = MutableLiveData<List<BusPlan>>()
    val listBus = arrayListOf<BusPlan>()

    init {
        for (i in 1..80) {
            listBus.add(BusPlan(i))
        }

        seatList.postValue(listBus)
    }
}