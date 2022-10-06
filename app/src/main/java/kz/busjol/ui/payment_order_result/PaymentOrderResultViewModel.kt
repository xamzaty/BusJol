package kz.busjol.ui.payment_order_result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.busjol.base.BaseViewModel

class PaymentOrderResultViewModel : BaseViewModel() {
    private val _qrList = MutableLiveData<List<Qr>>()
    val qrList: LiveData<List<Qr>> = _qrList

    init {
        initQr()
    }

    private fun initQr() {
        val list = mutableListOf<Qr>()
        list.add(Qr(url = "https://www.geeksforgeeks.org/generate-qr-code-in-android-using-kotlin/"))
        list.add(Qr(url = "https://www.figma.com/"))
        list.add(Qr(url = "https://www.youtube.com/"))

        _qrList.value = list
    }
}