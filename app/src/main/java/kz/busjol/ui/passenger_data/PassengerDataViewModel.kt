package kz.busjol.ui.passenger_data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.busjol.base.BaseViewModel
import kz.busjol.data.network.model.UserForm
import kz.busjol.ui.passenger_quantity.Passenger

class PassengerDataViewModel : BaseViewModel() {

    private val _passengerList = MutableLiveData<List<Passenger>>()
    val passengerList: LiveData<List<Passenger>> = _passengerList
}