package kz.busjol.ui.payment_order

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kz.busjol.R
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentPaymentOrderBinding

class PaymentOrderFragment : BaseFragment<FragmentPaymentOrderBinding>(FragmentPaymentOrderBinding::inflate) {

    private val args: PaymentOrderFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButtons()
        initTextViews()
    }

    private fun initButtons() {
        binding.apply {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }

            button.apply {
                setTitle("Оплатить")
                onSetClickListener {
                    val action = PaymentOrderFragmentDirections.actionPaymentOrderFragmentToPaymentOrderResultFragment(args.journeyData)
                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun initTextViews() {
        binding.apply {
            title.text = getString(R.string.title_order_payment)
        }
    }
}