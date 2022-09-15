package kz.busjol.ui.passenger_quantity

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kz.busjol.R
import kz.busjol.base.BaseBottomFragmentDialog
import kz.busjol.databinding.DialogPassengerQuantityBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PassengerQuantityDialog :
    BaseBottomFragmentDialog<DialogPassengerQuantityBinding>(
        DialogPassengerQuantityBinding::inflate,
        false
    ) {

    private val viewModel: PassengerQuantityViewModel by viewModel {
        parametersOf(args)
    }

    private val args: PassengerQuantityDialogArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupButtons()
    }

    private fun setupObservers() {
        viewModel.apply {
            adultQuantity.observe(viewLifecycleOwner) { adultQuantity ->
                binding.textAdultQuantity.text = adultQuantity.toString()
            }

            childQuantity.observe(viewLifecycleOwner) { childQuantity ->
                binding.textChildQuantity.text = childQuantity.toString()
            }

            disabledQuantity.observe(viewLifecycleOwner) { disabledQuantity ->
                    binding.textDisableQuantity.text = disabledQuantity.toString()
            }
        }
    }

    private fun setupButtons() {
         binding.apply {
             buttonPlusAdultTariff.setCountButtonAction(isAdd = true, type = PassengerType.ADULT)
             buttonMinusAdultTariff.setCountButtonAction(isAdd = false, type = PassengerType.ADULT)

             buttonPlusChildTariff.setCountButtonAction(isAdd = true, type = PassengerType.CHILD)
             buttonMinusChildTariff.setCountButtonAction(isAdd = false, type = PassengerType.CHILD)

             buttonPlusDisableTariff.setCountButtonAction(isAdd = true, type = PassengerType.DISABLED)
             buttonMinusDisableTariff.setCountButtonAction(isAdd = false, type = PassengerType.DISABLED)

            continueButton.setOnClickListener {
                passBackstackData()
                dismiss()
            }

            closeButton.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun AppCompatTextView.setCountButtonAction(isAdd: Boolean, type: PassengerType) {
        this.setOnClickListener {
            viewModel.onAction(PassengerQuantityAction.ChangePassengerQuantity(isAdd, type))
        }
    }

    private fun countPassengers(): Int {
        binding.apply {
            val adultQuantity = textAdultQuantity.text.toString().toInt()
            val childQuantity = textChildQuantity.text.toString().toInt()
            val disabledQuantity = textDisableQuantity.text.toString().toInt()

            return adultQuantity + childQuantity + disabledQuantity
        }
    }

    private fun passBackstackData() {
        val allQuantity = "quantity"
        val data = "journeyData"

        if (countPassengers() > 0) {
            backstackData(allQuantity, passengersQuantityText())
            backstackData(data, args.passengerData!!)
        }
    }

    private fun passengersQuantityText() = when (viewModel.returnTextType()) {
            BackStackTextType.FIRST_CASE -> "${countPassengers()} ${getString(R.string.one_person)}"
            BackStackTextType.SECOND_CASE -> "${countPassengers()} ${getString(R.string.two_person)}"
    }
}