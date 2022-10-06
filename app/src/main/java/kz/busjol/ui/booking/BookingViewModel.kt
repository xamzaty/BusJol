package kz.busjol.ui.booking

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kz.busjol.base.BaseViewModel
import java.util.concurrent.TimeUnit

class BookingViewModel : BaseViewModel() {

    private val _paymentList = MutableLiveData<List<Payment>>()
    private val _timeExp = MutableLiveData<String>()
    private val _isTimeExpired = MutableLiveData(false)

    val paymentList: LiveData<List<Payment>> = _paymentList
    val timeExp: LiveData<String> = _timeExp
    val isTimeExpired: LiveData<Boolean> = _isTimeExpired

    init {
        initPaymentList()
        countDown()
    }

    private fun initPaymentList() {
        val list = listOf(
            Payment(type = Payment.PaymentType.BANK_CARDS, url = "bank_cards"),
            Payment(type = Payment.PaymentType.KASPI, url = "kaspi_cards")
        )
        _paymentList.value = list
    }

    private fun countDown() {
        val countDownTimer = object : CountDownTimer(600000, 1000) {
            override fun onTick(p0: Long) {
                val millis: Long = p0
                val hms = String.format(
                    "%02d:%02d",
                    (TimeUnit.MILLISECONDS.toMinutes(millis) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))),
                    (TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(millis)
                    ))
                )

                _timeExp.value = hms
            }

            override fun onFinish() {
                _isTimeExpired.value = true
            }
        }
        countDownTimer.start()
    }
}