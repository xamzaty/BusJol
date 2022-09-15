package kz.busjol.ui.passenger_quantity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.busjol.base.BaseViewModel
import kz.busjol.domain.model.PassengerData
import kz.busjol.utils.state.ViewState

class PassengerQuantityViewModel(val args: PassengerQuantityDialogArgs) : BaseViewModel() {

    private val _adultQuantity = MutableLiveData(args.passengerData!!.adultQuantity)
    private val _childQuantity = MutableLiveData(args.passengerData!!.childQuantity)
    private val _disabledQuantity = MutableLiveData(args.passengerData!!.disabledQuantity)
    private val _allPassengersCount =
        MutableLiveData(_adultQuantity.value!! + _childQuantity.value!! + _disabledQuantity.value!!)

    val adultQuantity: LiveData<Int?> = _adultQuantity
    val childQuantity: LiveData<Int?> = _childQuantity
    val disabledQuantity: LiveData<Int?> = _disabledQuantity

    init {
        _allPassengersCount.postValue(_allPassengersCount.value!!.toInt())
    }

    private fun changePassengerQuantity(isAdd: Boolean, type: PassengerType) {
        when (type) {
            PassengerType.ADULT -> changeAdultQuantity(isAdd)
            PassengerType.CHILD -> changeChildQuantity(isAdd)
            PassengerType.DISABLED -> changeDisabledQuantity(isAdd)
        }
    }

    private fun changeAdultQuantity(isAdd: Boolean) {
        if (isAdd && _adultQuantity.value!! < 50)
            _adultQuantity.value = _adultQuantity.value!! + 1
        if (!isAdd && _adultQuantity.value!! > 1 || (_disabledQuantity.value!! > 0 && _adultQuantity.value!! > 0))
            _adultQuantity.value = _adultQuantity.value!! - 1
    }

    private fun changeChildQuantity(isAdd: Boolean) {
        if (isAdd && _childQuantity.value!! < 50)
            _childQuantity.value = _childQuantity.value!! + 1
        if (!isAdd && _childQuantity.value!! > 0)
            _childQuantity.value = _childQuantity.value!! - 1
    }

    private fun changeDisabledQuantity(isAdd: Boolean) {
        if (isAdd && _disabledQuantity.value!! < 50)
            _disabledQuantity.value = _disabledQuantity.value!! + 1
        if (!isAdd && _disabledQuantity.value!! > 0)
            _disabledQuantity.value = _disabledQuantity.value!! - 1
    }

    fun returnTextType() = when (_allPassengersCount.value) {
        1, in 5..21, in 25..31, in 35..41 ->
            BackStackTextType.FIRST_CASE
        in 2..4, in 22..24, in 32..34, in 42..44 ->
            BackStackTextType.SECOND_CASE
        else -> BackStackTextType.FIRST_CASE
    }

    fun onAction(action: PassengerQuantityAction) {
        when (action) {
            is PassengerQuantityAction.ChangePassengerQuantity ->
                changePassengerQuantity(action.isAdd, action.type)
        }
    }
}