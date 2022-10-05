package kz.busjol.ui.booking

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import kz.busjol.R
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentBookingBinding
import kz.busjol.ext.FragmentExt.navigate
import kz.busjol.ui.passenger_data.PassengerDataAdapter
import kz.busjol.ui.passenger_data.PassengerDataFragmentDirections
import kz.busjol.utils.formatWithCurrency
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookingFragment : BaseFragment<FragmentBookingBinding>(FragmentBookingBinding::inflate), BookingAdapter.OnItemClickListener {

    private var isBankCardsChosen: Boolean = true

    private val viewModel: BookingViewModel by viewModel()
    private val adapter = BookingAdapter(this)
    private val args: BookingFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        initButtons()
        initTextFields()
        initRv()
        adapter.isBankCardChosen(isBankCardsChosen)
    }

    override fun onPaymentButtonClicked(payment: Payment) {
        val action = BookingFragmentDirections.actionBookingFragmentToPaymentOrderFragment(payment, args.journeyData)
        findNavController().navigate(action)
    }

    private fun setupObservers() {
        viewModel.apply {
            timeExp.observe(viewLifecycleOwner) {
                binding.bookingExpiresTimer.text = it
            }
        }
    }

    private fun initButtons() {
        binding.apply {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }

            buttonBookingDetails.setOnClickListener {
                val action = BookingFragmentDirections.actionBookingFragmentToTripDetailsDialog(args.journeyData)
                findNavController().navigate(action)
            }
        }
    }

    private fun initTextFields() {
        val amount = 10000
        binding.apply {
            bookingTripNumber.text = getString(R.string.trip_number, "1")
            routeCities.text = "${args.journeyData.fromCity?.name} - ${args.journeyData.toCity?.name}"
            bookingDate.text = "24.02.2022 09:00"
            bookingAmount.text = amount.formatWithCurrency()
        }
    }

    private fun initRv() {
        val list = listOf(
            Payment(type = Payment.PaymentType.BANK_CARDS, url = "bank_cards", isChosen = isBankCardsChosen),
            Payment(type = Payment.PaymentType.KASPI, url = "kaspi_cards", isChosen = !isBankCardsChosen)
        )

        adapter.submitList(list)
        binding.rv.adapter = adapter
        binding.rv.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(BookingItemDecoration())
        }
    }
}