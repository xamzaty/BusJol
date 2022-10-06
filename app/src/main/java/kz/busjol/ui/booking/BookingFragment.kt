package kz.busjol.ui.booking

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kz.busjol.R
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentBookingBinding
import kz.busjol.ext.FragmentExt.onBackKeyListener
import kz.busjol.ext.FragmentExt.showIrrevocableAlertDialog
import kz.busjol.ext.FragmentExt.showOnBackKeyAlert
import kz.busjol.utils.formatWithCurrency
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookingFragment :
    BaseFragment<FragmentBookingBinding>(FragmentBookingBinding::inflate),
    BookingAdapter.OnItemClickListener {

    private val viewModel: BookingViewModel by viewModel()
    private val adapter = BookingAdapter(this)
    private val args: BookingFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        initButtons()
        initTextFields()
        initRv()
    }

    override fun onPaymentButtonClicked(payment: Payment) {
        val action = BookingFragmentDirections.actionBookingFragmentToPaymentOrderFragment(payment, args.journeyData)
        findNavController().navigate(action)
    }

    private fun setupObservers() {
        viewModel.apply {

            paymentList.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }

            timeExp.observe(viewLifecycleOwner) {
                binding.bookingExpiresTimer.text = it
            }

            isTimeExpired.observe(viewLifecycleOwner) {
                if (it) showDialog()
            }
        }
    }

    private fun showDialog() {
        showIrrevocableAlertDialog(title = "Time is up!", message = "Exit to continue",
            clickListener = DialogInterface.OnClickListener { _, _ ->
                findNavController().navigate(R.id.action_bookingFragment_to_navigation_search)
            })
    }

    private fun initButtons() {
        binding.apply {

            onBackKeyListener {
                showOnBackKeyAlert {
                    findNavController().navigate(R.id.action_bookingFragment_to_navigation_search)
                }
            }

            backButton.setOnClickListener {
                showOnBackKeyAlert {
                    findNavController().navigate(R.id.action_bookingFragment_to_navigation_search)
                }
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
        binding.rv.adapter = adapter
        binding.rv.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(BookingItemDecoration())
        }
    }
}