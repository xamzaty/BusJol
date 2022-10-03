package kz.busjol.ui.passenger_data

import android.os.Bundle
import android.view.View
import android.view.View.inflate
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kz.busjol.R
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentPassengerDataBinding
import kz.busjol.ext.FragmentExt.navigate
import org.koin.androidx.viewmodel.ext.android.viewModel

class PassengerDataFragment :
    BaseFragment<FragmentPassengerDataBinding>(FragmentPassengerDataBinding::inflate) {

    private val viewModel: PassengerDataViewModel by viewModel()
    private val listAdapter = PassengerDataAdapter()
    private val args: PassengerDataFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTextViews()
        setupTextFields()
        setupButtons()
        initData()
        initRv()
        setActivityToAdapter()
    }

    private fun setActivityToAdapter() {
        listAdapter.getActivity(activity!!)
    }

    private fun initTextViews() {
        binding.apply {
            tripNumber.text = getString(R.string.trip_number, "1")
            tripDestination.text = "${args.journeyData.fromCity?.name} - ${args.journeyData.toCity?.name}"
            tripDate.text = args.journeyData.journey?.departureTime
        }
    }

    private fun setupTextFields() {
        binding.apply {
            etEmail.enableMailField()
            etPhone.enablePhoneField()
        }
    }

    private fun setupButtons() {
        binding.apply {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }

            tripDetailsButton.setOnClickListener {
                val action = PassengerDataFragmentDirections.actionPassengerDataFragmentToTripDetailsDialog(args.journeyData)
                findNavController().navigate(action)
            }

            continueButton.setOnClickListener {
                val action = PassengerDataFragmentDirections.actionPassengerDataFragmentToBookingFragment(args.journeyData)
                findNavController().navigate(action)
            }
        }
    }

    private fun initData() {
        listAdapter.submitList(args.journeyData.passengerListData)
    }

    private fun initRv() {
        binding.rv.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }
}