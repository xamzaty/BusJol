package kz.busjol.ui.passenger_data

import android.os.Bundle
import android.view.View
import androidx.core.view.isNotEmpty
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kz.busjol.R
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentPassengerDataBinding
import kz.busjol.ext.EditTextExt.initTextWatcher
import kz.busjol.ext.FragmentExt.showIrrevocableAlertDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PassengerDataFragment :
    BaseFragment<FragmentPassengerDataBinding>(FragmentPassengerDataBinding::inflate),
    PassengerDataAdapter.CheckFields {

    private val viewModel: PassengerDataViewModel by viewModel {
        parametersOf(args)
    }
    private val listAdapter = PassengerDataAdapter(this)
    private val args: PassengerDataFragmentArgs by navArgs()

    private var isAllFieldsFilled = false
    private var isAgreementChecked = false
    private var isContactsLayoutAvailable = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTextViews()
        setupTextFields()
        setupButtons()
        initData()
        initRv()
        listAdapter.getActivity(activity!!)
    }

    override fun checkIfAllFieldsFilled(isAllFilled: Boolean) {
        isAllFieldsFilled = isAllFilled
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

            binding.continueButton.setOnClickListener {
                val a = binding.rv.findViewHolderForAdapterPosition(0)
                if (a is PassengerDataViewHolder) {
                    a.v()
                }
                if (!isAllFieldsFilled) showIrrevocableAlertDialog(title = getString(R.string.alert_not_fields_is_filled))
                else if (!isAgreementChecked) showIrrevocableAlertDialog(title = getString(R.string.alert_check_agreement))
                else navigateToTheNextScreen()
            }

            setupCheckBox()
        }
    }

    private fun isContactFieldsEmpty(): Boolean {
        binding.apply {
            var isEmailFilled = false
            var isPhoneFilled = false

            etEmail.getMainField().initTextWatcher {
                isEmailFilled = it.trim().isNotBlank()
            }

            etPhone.getMainField().initTextWatcher {
                isPhoneFilled = it.trim().isNotBlank()
            }

            return isEmailFilled && isPhoneFilled
        }
    }

    private fun setupCheckBox() {
        binding.apply {
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                isAgreementChecked = isChecked
                println(isChecked)
            }
        }
    }

    private fun navigateToTheNextScreen() {
        val action = PassengerDataFragmentDirections.actionPassengerDataFragmentToBookingFragment(args.journeyData)
        findNavController().navigate(action)
    }

    private fun initData() {
        viewModel.apply {
            passengerList.observe(viewLifecycleOwner) {
                listAdapter.submitList(it)
            }
        }
    }

    private fun initRv() {
        binding.rv.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }
}