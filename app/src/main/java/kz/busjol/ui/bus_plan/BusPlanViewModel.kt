package kz.busjol.ui.bus_plan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.busjol.base.BaseViewModel
import kz.busjol.data.network.model.BusPlan

class BusPlanViewModel(
    private val args: BusPlanFragmentArgs
) : BaseViewModel() {

    private val listBus = arrayListOf<BusPlan>()
    val seatList = MutableLiveData<List<BusPlan>>()

    private val _selectedSeats = MutableLiveData<MutableSet<Int>>()
    val selectedSeats: LiveData<MutableSet<Int>> = _selectedSeats

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

    private fun addItemsToBusPlanList(item: Int) {
        val set = _selectedSeats.value ?: mutableSetOf()
        set.add(item)
        _selectedSeats.value = set
    }

    private fun removeItemsFromBusPlanList(item: Int) {
        val set = _selectedSeats.value ?: mutableSetOf()
        val iterator = _selectedSeats.value?.iterator()

        while (iterator?.hasNext() == true) {
            if (iterator.next() == item) {
                iterator.remove()
            }
        }
        _selectedSeats.value = set
    }

    fun onAction(action: BusPlanAction) {
        when (action) {
            is BusPlanAction.AddItemsToBusPlanList -> addItemsToBusPlanList(action.item)
            is BusPlanAction.RemoveItemsFromBusPlanList -> removeItemsFromBusPlanList(action.item)
            is BusPlanAction.RemoveAllItemsFromBusPlanList -> _selectedSeats.value?.clear()
        }
    }
}