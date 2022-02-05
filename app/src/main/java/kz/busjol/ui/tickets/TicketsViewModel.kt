package kz.busjol.ui.tickets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TicketsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Здесь будет фрагмент с билетами"
    }
    val text: LiveData<String> = _text
}