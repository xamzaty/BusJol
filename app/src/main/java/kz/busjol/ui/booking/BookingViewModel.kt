package kz.busjol.ui.booking

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.busjol.base.BaseViewModel

class BookingViewModel : BaseViewModel() {

    private val _timeExp = MutableLiveData<Long>(10000)
    private val _seconds = MutableLiveData<Long>(1000)
    private val _isTimeExpired = MutableLiveData(false)

    val timeExp: LiveData<Long> = _timeExp
    val isTimeExpired: LiveData<Boolean> = _isTimeExpired

    init {
    }

    private fun countdownTimer() = object : CountDownTimer(_timeExp.value!!, 1000) {
        override fun onTick(p0: Long) {
        }

        override fun onFinish() {
        }

    }
}