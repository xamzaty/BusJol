package kz.busjol.ui.payment_order_result

import android.os.Bundle
import android.view.View
import androidmads.library.qrgenearator.QRGEncoder
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import kz.busjol.R
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentPaymentOrderResultBinding
import kz.busjol.ext.FragmentExt.navigate
import kz.busjol.ext.FragmentExt.onBackKeyListener
import kz.busjol.ext.FragmentExt.showOnBackKeyAlert
import org.koin.androidx.viewmodel.ext.android.viewModel

class PaymentOrderResultFragment :
    BaseFragment<FragmentPaymentOrderResultBinding>(FragmentPaymentOrderResultBinding::inflate) {

    private val viewModel: PaymentOrderResultViewModel by viewModel()
    private val args: PaymentOrderResultFragmentArgs by navArgs()
    private val resultAdapter = PaymentOrderResultAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        initTextViews()
        initButtons()
        initRv()
    }

    private fun observeViewModel() {
        viewModel.apply {
            qrList.observe(viewLifecycleOwner) {
                resultAdapter.submitList(it)
            }
        }
    }

    private fun initTextViews() {
        binding.apply {
            titlePaymentOrderResult.text = getString(R.string.title_payment_order_result, "1")
            clientName.text = "Khamzat Yerzhanov"
            clientSeats.text = "15/16/17"
            ticketTimeFrom.text = "19:10 10 ноября"
            ticketCityFrom.text = args.journeyData.fromCity?.name
            ticketStationFrom.text = "Автовокзал ${args.journeyData.fromCity?.name}"
            ticketTimeTo.text = "14:20 11 ноября"
            ticketCityTo.text = args.journeyData.toCity?.name
            ticketStationTo.text = "Автовокзал ${args.journeyData.toCity?.name}"
        }
    }

    private fun initButtons() {
        binding.apply {
            onBackKeyListener {
                showOnBackKeyAlert {
                    navigate(R.id.action_paymentOrderResultFragment_to_navigation_search)
                }
            }

            backButton.setOnClickListener {
                showOnBackKeyAlert {
                    navigate(R.id.action_paymentOrderResultFragment_to_navigation_search)
                }
            }

            backToMenuButton.setOnClickListener {
                navigate(R.id.action_paymentOrderResultFragment_to_navigation_search)
            }
        }
    }

    private fun initRv() {
        binding.rv.apply {
            adapter = resultAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            LinearSnapHelper().attachToRecyclerView(this)
        }
    }
}