package kz.busjol.ui.payment_order

import android.os.Bundle
import android.view.View
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentPaymentOrderBinding

class PaymentOrderFragment : BaseFragment<FragmentPaymentOrderBinding>(FragmentPaymentOrderBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButtons()
        initTextViews()
    }

    private fun initButtons() {
        binding.apply {
            backButton.setOnClickListener {

            }

            button.apply {
                setTitle("Оплатить")
                onSetClickListener {

                }
            }
        }
    }

    private fun initTextViews() {

    }
}