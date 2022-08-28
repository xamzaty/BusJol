package kz.busjol.ui.passenger_data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.busjol.base.BaseViewModel
import kz.busjol.data.network.model.UserForm

class PassengerDataViewModel : BaseViewModel() {

    private val _userList = MutableLiveData<List<UserForm>>()
    val userList: LiveData<List<UserForm>> = _userList

    init {
        _userList.postValue(
            listOf(
                UserForm(true),
                UserForm( false),
                UserForm(false)
            )
        )
    }
}